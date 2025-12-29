# TwoFourTree (2–3–4 Tree)

This repository contains a full **Java implementation of a 2–3–4 tree** (also called a **2–4 tree**), a self-balancing search tree that maintains sorted data and guarantees logarithmic time complexity for insert, search, and delete operations.

The project also includes a driver program used to **validate correctness** and **benchmark performance** against Java’s built-in `TreeSet`.

---

## Features

- Insert, search, and delete operations
- Automatic balancing using node **splitting** and **merging**
- Supports 2-nodes, 3-nodes, and 4-nodes
- In-order traversal printing for validation
- Performance comparison against Java `TreeSet`

---

## Requirements

```text
Java 8 or newer
```

---

## Compilation & Execution

### Compile

```bash
javac TwoFourTree.java App.java
```

### Run

```bash
java App
```

---

## Program Behavior

### Static Correctness Test

`App.java` first inserts a sequence of prime numbers into the tree, prints the tree in sorted (in-order) form, deletes selected values, and prints the tree again to verify correctness.

### Performance Benchmark

The benchmark performs the following steps:

```text
1. Generate random integers
2. Insert values into TwoFourTree
3. Perform search (find) operations
4. Delete roughly half of the values
5. Repeat all operations using TreeSet
6. Display timing results
```

Benchmark sizes range from hundreds to millions of elements.

---

## Example Output (abridged)

```text
Static test: first few prime numbers:
2
3
5
7
...

CASE:      100 integers,       20 finds,       10 removals.
 TwoFourTree add:      1ms find:      0ms del:      0ms find:      0ms
 TreeSet     add:      0ms find:      0ms del:      0ms find:      0ms
```

---

## Notes

- Large benchmark cases (millions of elements) may require significant memory and runtime.
- You may comment out the largest test cases in `main()` if needed.
- Timing results vary based on hardware and JVM.
  
