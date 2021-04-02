# automated-bug-detection
Automated Bug Detection with C++ + Starter Kit

# Getting Started With C++

[C++ QUICK REFERENCE](http://www.hoomanb.com/cs/QuickRef/CppQuickRef.pdf)

# Getting Started With Java

[Java QUICK REFERENCE](https://introcs.cs.princeton.edu/java/11cheatsheet/)

# Building an Automated Bug Detection Tool

* Inferring Likely Invariants for Bug Detection <br/><br/>

`void scope1() {` <br/>
&nbsp;&nbsp;`A(); B(); C(); D();` <br/>
`}` <br/>
`void scope2() {` <br/>
&nbsp;&nbsp;`A(); C(); D();` <br/>
`}` <br/>
`void scope3() {` <br/>
&nbsp;&nbsp;`A(); B(); B();` <br/>
`}` <br/>
`void scope4() {` <br/>
&nbsp;&nbsp;`B(); D(); scope1();` <br/>
`}` <br/>
`void scope5() {` <br/>
&nbsp;&nbsp;`B(); D(); A();` <br/>
`}` <br/>
`void scope6() {` <br/>
&nbsp;&nbsp;`B(); D();` <br/>
`}` <br/><br/>

* Finding and Explaining False Positives
* Inter-Procedural Analysis
* Improving the Solutions

# Using a Static Bug Detection Tool Coverity
* Resolving Bugs in Apache Tomcat
* Analyzing The Code
