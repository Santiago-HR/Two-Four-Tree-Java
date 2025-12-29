TwoFourTree (2–3–4 Tree)

This repository contains a full Java implementation of a 2–3–4 tree (also called a 2–4 tree), a self-balancing search tree that maintains sorted data and guarantees logarithmic time complexity for insert, search, and delete operations.

The project also includes a driver program used to validate correctness and benchmark performance against Java’s built-in TreeSet.

📁 Repository Contents
.
├── TwoFourTree.java   # Core 2–3–4 tree implementation
└── App.java           # Test and benchmark driver

✨ Features

Supports insert, search, and delete operations

Maintains balance using node splitting and merging

Handles 2-nodes, 3-nodes, and 4-nodes

In-order traversal printing for debugging and verification

Performance comparison with Java TreeSet

✅ Requirements

Java 8 or newer

🛠 Compilation & Execution
Compile both source files
javac TwoFourTree.java App.java

Run the driver program
java App

🔍 What the Program Does
Static Correctness Test

App.java first:

Inserts a sequence of prime numbers into the tree

Prints the tree in sorted (in-order) form

Deletes selected values

Prints the tree again to verify correctness

Performance Benchmark

The program then runs several benchmark cases that:

Generate large sets of random integers

Insert them into a TwoFourTree

Perform random search (“find”) operations

Delete roughly half of the values

Repeat the same operations using Java’s TreeSet

Print timing results for comparison

Benchmark sizes range from small (100 elements) to very large (millions of elements).

📝 Notes

Large benchmark cases (millions of elements) may require significant time and memory

You may comment out the largest test cases in main() if needed

Output timings depend on hardware, JVM, and system load

📊 Example Output (abridged)
Static test: first few prime numbers:
2
3
5
7
...

CASE:    100 integers,     20 finds,     10 removals.
 TwoFourTree add:      1ms find:      0ms del:      0ms find:      0ms
 TreeSet     add:      0ms find:      0ms del:      0ms find:      0ms
