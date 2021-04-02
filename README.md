# automated-bug-detection
Automated Bug Detection with C++ + Starter Kit

# Getting Started With C++

[C++ QUICK REFERENCE](http://www.hoomanb.com/cs/QuickRef/CppQuickRef.pdf)

# Getting Started With Java

[Java QUICK REFERENCE](https://introcs.cs.princeton.edu/java/11cheatsheet/)

# Building an Automated Bug Detection Tool

* Inferring Likely Invariants for Bug Detection <br/><br/>

void scope1() { <br/>
  A(); B(); C(); D(); <br/>
} <br/>
void scope2() { <br/>
  A(); C(); D(); <br/>
} <br/>
void scope3() { <br/>
  A(); B(); B(); <br/>
} <br/>
void scope4() { <br/>
  B(); D(); scope1(); <br/>
} <br/>
void scope5() { <br/>
  B(); D(); A(); <br/>
} <br/>
void scope6() { <br/>
B(); D(); } <br/><br/>

* Finding and Explaining False Positives
* Inter-Procedural Analysis
* Improving the Solutions

# Using a Static Bug Detection Tool Coverity
* Resolving Bugs in Apache Tomcat
* Analyzing The Code
