# automated-bug-detection
Automated Bug Detection with C++ + Starter Kit

# Getting Started With C++

[C++ QUICK REFERENCE](http://www.hoomanb.com/cs/QuickRef/CppQuickRef.pdf)

# Getting Started With Java

[Java QUICK REFERENCE](https://introcs.cs.princeton.edu/java/11cheatsheet/)

# Building an Automated Bug Detection Tool

* Inferring Likely Invariants for Bug Detection <br/>

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

We can learn that **function *A*** and **function *B*** are called together three times in **function *scope1*, *scope3*, and *scope5***. And **function *A*** is called four times in **function *scope1*, *scope2*, *scope3*, and *scope5***. We infer that the one time that **function *A*** is called without **function *B*** in ***scope2*** is a bug, as **function *A* and *B*** are called together 3 times. We only count ***B*** once in ***scope3*** although ***scope3*** calls B two times. <br/><br/> 

We define support as the number of times a pair of functions appears together. Therefore, support({A,B}) support({A,B}) 3
is 3. We define confidence({A,B},{A}) as support({A}) , which is 4. We set the threshold for support and confidence to be T SUPPORT and T CONFIDENCE, whose default values are 3 and 65%. You only print bugs with confidence T CONFIDENCE or more and with pair support T SUPPORT times or more. For example, function B is called five times in function scope1, scope3, scope4, scope5, and scope6. The two times that
function B is called alone are not printed as a bug as the confidence is only 60% 􏰀support(A,B) = 3􏰁, lower than support(B) 5
the T THRESHOLD, which is 65%. Please note that support(A,B) and support(B,A) are equal, and both 3.



* Finding and Explaining False Positives
* Inter-Procedural Analysis
* Improving the Solutions

# Using a Static Bug Detection Tool Coverity
* Resolving Bugs in Apache Tomcat
* Analyzing The Code
