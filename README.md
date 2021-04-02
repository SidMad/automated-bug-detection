# automated-bug-detection
Automated Bug Detection with C++ + Starter Kit

# Getting Started With C++

[C++ QUICK REFERENCE](http://www.hoomanb.com/cs/QuickRef/CppQuickRef.pdf)

# Getting Started With Java

[Java QUICK REFERENCE](https://introcs.cs.princeton.edu/java/11cheatsheet/)

# Building an Automated Bug Detection Tool

* Inferring Likely Invariants for Bug Detection <br/><br/>

`void scope1() {
  A(); B(); C(); D();
} <br/>
void scope2() {
  A(); C(); D();
}
void scope3() {
  A(); B(); B();
}
void scope4() {
  B(); D(); scope1();
}
void scope5() {
  B(); D(); A();
}
void scope6() {
B(); D(); 
}`

* Finding and Explaining False Positives
* Inter-Procedural Analysis
* Improving the Solutions

# Using a Static Bug Detection Tool Coverity
* Resolving Bugs in Apache Tomcat
* Analyzing The Code
