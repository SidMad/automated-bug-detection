import java.util.ArrayList;
import java.io.BufferedReader;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.HashSet;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.text.NumberFormat;
import java.math.RoundingMode;
import java.util.Set;

class Pipair {                                                                         
    int tSupport = 3;                                                                   
    float tConfidence = 0.65f;                                                          
    int levels = 0;                                                                     
    ArrayList<String> stringList = new ArrayList<String>();                             
    NumberFormat numberFormat = NumberFormat.getNumberInstance();                       

    public static String getPairName(String one, String two) {                          
        if (!one.compareTo(two) <= 0) {                                                 
            String temp = one; one = two; two = temp;                                   
        }
        return a + ":" + b;                                                            
    }
    
    class SupportGraph {                                                                
        HashSet<String> nameResults = new HashSet<String>();                            
        Hashtable<String,Integer> matchingResults = new Hashtable<String,Integer>();    
        
        private void parseFromCallGraph(Hashtable<String,ArrayList<String>> cg) {       
            Enumeration funcs = cg.elements();
            while (funcs.hasMoreElements()) {
                ArrayList<String> calls = (ArrayList<String>)funcs.nextElement(); 
                calls = removeDuplicateCalls(calls);
                for (int i = 0; i < calls.size(); i++) {
                    nameResults.add(calls.get(i));
                    for (int j = i + 1; j < calls.size(); j++) {
                        String name = Pipair.getPairName(calls.get(i), calls.get(j)); 
                        createOrIncrementSupport(name);
                    }
                    createOrIncrementSupport(calls.get(i));
                }
            }
        }

        private ArrayList<String> removeDuplicateCalls(ArrayList<String> calls) {
            HashSet<String> callSet = new HashSet<String>(calls); 
            calls = new ArrayList<String>(callSet);
            return calls;
        }
        
        private void createOrIncrementSupport(String name) {
            Integer curSing = matchingResults.get(name);
            if (curSing == null) {
                matchingResults.put(name, 1);
            } else {
                matchingResults.put(name, new Integer(curSing + 1));
            }
        }
    }

    public void findAndPrintViolations(Hashtable<String,ArrayList<String>> cg, SupportGraph sg) {
        Enumeration<String> cgKeySet = cg.keys();
        while (cgKeySet.hasMoreElements()) {
            String caller = (String)cgKeySet.nextElement();
            ArrayList<String> callsL = (ArrayList<String>)cg.get(caller);
            HashSet<String> calls = new HashSet<String>(callsL);

            Iterator i = calls.iterator();
            while (i.hasNext()) {
                String f = (String)i.next();
                printInvariantsForFunction(caller, f, sg, calls);
            }
        }
    }
    
    private void printInvariantsForFunction(String caller, String f1, SupportGraph sg, HashSet<String> calls) {
        Iterator<String> i = sg.nameResults.iterator();
        while (i.hasNext()) {
            String f2 = i.next();
            String key = Pipair.getPairName(f1, f2);
            
            if (!sg.matchingResults.containsKey(key) ||
                !sg.matchingResults.containsKey(f1)) {
                continue;
            }

            int pairSupport = sg.matchingResults.get(key).intValue();
            int singleSupport = sg.matchingResults.get(f1).intValue();
            float confidence = (float)pairSupport/singleSupport;

            if (confidence >= tConfidence && pairSupport >= tSupport) {
                if (!calls.contains(f2)) {
                    printViolation(caller, f1, f2, pairSupport,
                                   confidence);
                }
            }
        }
    }

    public void printViolation(String caller, String f1, String f2, int support, float confidence) {
        String pair;
        if (f1.compareTo(f2) > 0) {
            pair = f2 + " " + f1;
        } else {
            pair = f1 + " " + f2;
        }
        stringList.add("bug: " + f1 + " in " + caller + ", " +
                   "pair: (" + pair + "), support: " +
                   support + ", confidence: " +               
                   numberFormat.format(confidence * 100.0) + "%");
    }
    
    public void flushPrint() {
        Collections.sort(stringList);
        for (int i = 0; i < stringList.size(); i++) {
            System.out.println(stringList.get(i));
        }
    }

    public void run(String cgFile) {
        numberFormat.setMaximumFractionDigits(2);
        numberFormat.setMinimumFractionDigits(2);
        numberFormat.setRoundingMode(RoundingMode.HALF_EVEN);

        Hashtable<String,ArrayList<String>> cg = Parser.parseFile(cgFile,levels);
        SupportGraph sg = new SupportGraph();
        sg.parseFromCallGraph(cg);
        findAndPrintViolations(cg, sg);
        flushPrint();
    }
    
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: ./pipair <bitcode file> <T SUPPORT> <T CONFIDENCE>,");
            System.exit(0);
        }

        Pipair prog = new Pipair();
        if (args.length >= 2) {
            prog.tSupport = Integer.parseInt(args[1]);
        }
        if (args.length >= 3) {
            prog.tConfidence = (float)Integer.parseInt(args[2])/100;
        }
        if (args.length >= 4) {
            prog.levels = Integer.parseInt(args[3]);
        }
        prog.run(args[0]);
    }
}
