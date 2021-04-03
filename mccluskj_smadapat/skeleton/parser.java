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

class Parser {
    public static Hashtable<String,ArrayList<String>> parseFile(String fileName, int levels) {
        Hashtable<String, ArrayList<String>> table = new Hashtable<String,ArrayList<String>>();
        Runtime rt = Runtime.getRuntime();
  
        try {
            Process pr = rt.exec("opt -print-callgraph -disable-output " + fileName);
            InputStream st = pr.getErrorStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(st));
            String line = null;
            int state = 0;
            String current = null;
            
            while ((line = in.readLine()) != null) { 
              switch (state) {
                case(1):
                  if (line.matches("(.*)CS<0x[0-9a-f]*> calls function(.*)")) {
                    String[] slist = line.split("\'");
                    String func = slist[1];
                    ArrayList<String> curList = table.get(current);
                    if (!curList.contains(func)) { 
                      curList.add(func);
                    }
                    break;
                  }
                case(0):
                  if (line.startsWith("Call graph node for function")) {
                    String[] slist = line.split("\'");
                    current = slist[1];
                    ArrayList<String> nlist = new ArrayList<String>();
                    table.put(current,nlist);
                    state = 1;
                    break;
                  }
                default:
                  if (line.length() == 0) { 
                    state = 0;
                  }
                  break;
              }
            }
            
            for (int l = 0; l < levels; l++) {
              Hashtable<String,ArrayList<String>> staticTable = deepCopy(table);
              Enumeration funcs = table.elements();
                
              while (funcs.hasMoreElements()) {
                ArrayList<String> calls = (ArrayList<String>)funcs.nextElement();
                ArrayList<String> originalCalls = (ArrayList<String>)calls.clone();

                for (int i = 0; i < originalCalls.size(); i++) {
                  String expandFunc = originalCalls.get(i);
                  ArrayList<String> funcsToBeAdded = staticTable.get(expandFunc);

                  if (funcsToBeAdded.size() > 0) {
                    calls.remove(expandFunc);
                  }

                  for (int j = 0; j < funcsToBeAdded.size(); j++) {
                    String funcToBeAdded = (String)funcsToBeAdded.get(j);
                    if (!calls.contains(funcToBeAdded)) {
                      calls.add(funcToBeAdded);
                    }
                  }
                }
              }
            }
        } catch (IOException e) {}
        return table;
    }

    public static Hashtable<String,ArrayList<String>> deepCopy(Hashtable<String,ArrayList<String>> iniTable) {
      Hashtable<String,ArrayList<String>> nTable = new Hashtable<String,ArrayList<String>>();
      Enumeration funcs = iniTable.keys();
      
      while (funcs.hasMoreElements()) { 
        String func = (String)funcs.nextElement();
        ArrayList<String> iniCalls = iniTable.get(func); 
        ArrayList<String> nCalls = (ArrayList<String>)iniCalls.clone();
        nTable.put(func, nCalls);
      }
      
      return nTable;
    }
}
