# BFS

## What is the process used by the Breadth First Search (BFS) algorithm, what are its applications and what optimizations can be made to improve its performance?

### Introduction

There are many different algorithms that can be used to find the shortest distance between two things.
Algorithms like Dijkstra, A*, Depth First Search (DFS), and Breadth First Search (BFS) can all be used [1].
In this article, we will focus on the Breadth First Search algorithm.
The BFS algorithm is primarily used to find the shortest path between two nodes in an unweighted graph [2], [3].

### The Breadth First Search Algorithm

The Breadth First Search algorithm is a graph traversal algorithm that starts at a given node and
explores all the neighbor nodes at the present depth before moving on to the nodes at the next depth
level [1], [3], [4].

### Pseudocode

To explain the process of the BFS algorithm, I'll be using pseudocode. Pseudocode is a way of writing the steps an
algorithm takes in a way that can easily be translated into code. It is not a programming language, but it
follows the structure of one [1], [4].

```
BFS(start_node):
    create a queue
    add start_node to the queue
    mark start_node as visited
    while the queue is not empty:
        current_node = queue.pop()
        for each neighbor of current_node:
            if neighbor is not visited:
                add neighbor to the queue
                mark neighbor as visited
```

### A Java Example

To demonstrate the BFS algorithm, I'll be using a simple graph implementation in Java [1], [4]. I have created a couple of
classes that help with the implementation such as `Node`, `Graph`, `GraphTraversable`, and `GenerateGraph`. To keep the
example simple, I only show the `BFS` class. I have used generics to make the classes more flexible.

```java
package org.example.bfs;

import org.example.graph.Graph;
import org.example.graph.GraphTraversable;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BFS<E extends GraphTraversable<E, V>, V> {
    private Queue<E> queue;
    private List<E> visited;

    public BFS() {}

    public void traverse(Graph<E, V> graph, E startNode) {
        visited = new ArrayList<>();
        queue = new LinkedList<>();
        queue.add(startNode);
        visited.add(startNode);

        while (!queue.isEmpty()) {
            var node = queue.poll();
            visit(node);
        }
    }

    private void visit(E node) {
        System.out.println(node.getValue());
        node.getNeighbors().forEach(neighbor -> {
            if (!visited.contains(neighbor)) {
                queue.add(neighbor);
                visited.add(neighbor);
            }
        });
    }
}
```

But this implementation is not complete. The graph gets traversed using the BFS algorithm, but nothing is done with the nodes.
A path is often needed, so with the following modifications, the path is stored and returned when searching for a node [6]:

```java
package org.example.bfs;

import org.example.graph.Graph;
import org.example.graph.GraphTraversable;

import java.util.*;

public class BFS<E extends GraphTraversable<E, V>, V> {
    private Queue<E> queue;
    private List<E> visited;
    private Map<E, E> parent;

    public BFS() {}

    public void traverse(Graph<E, V> graph, E startNode) {
        visited = new ArrayList<>();
        queue = new LinkedList<>();
        queue.add(startNode);
        visited.add(startNode);

        while (!queue.isEmpty()) {
            var node = queue.poll();
            visit(node);
        }
    }

    public List<E> findPath(Graph<E, V> graph, E startNode, E endNode) {
        visited = new ArrayList<>();
        queue = new LinkedList<>();
        parent = new HashMap<>();

        parent.put(startNode, null);
        queue.add(startNode);
        visited.add(startNode);

        while (!queue.isEmpty()) {
            var node = queue.poll();
            if (node.equals(endNode)) {
                return returnPath(endNode);
            }
            visit(node);
        }
        return null;
    }

    private List<E> returnPath(E endNode) {
        var path = new ArrayList<E>();
        var node = endNode;
        while (node != null) {
            path.add(node);
            node = parent.get(node);
        }
        Collections.reverse(path);
        parent = null;
        return path;
    }

    public void printPath(List<E> path) {
        if (path == null) {
            return;
        }
        path.forEach(node -> System.out.println(node.getValue()));
    }

    private void visit(E node) {
        node.getNeighbors().forEach(neighbor -> {
            if (!visited.contains(neighbor)) {
                if (parent != null) parent.put(neighbor, node);
                queue.add(neighbor);
                visited.add(neighbor);
            }
        });
    }
}
```

### Optimizations

#### Bottom up


### Sources

1. A. Bhargava, Grokking Algorithms, Second Edition, 2nd ed. Manning Publications, 2024. [O’Reilly Media]. Available: https://learning-oreilly-com.rps.hva.nl/library/view/grokking-algorithms-second/9781633438538/
2. GeeksforGeeks, “Applications, Advantages and Disadvantages of Breadth First Search (BFS),” GeeksforGeeks, Dec. 20, 2023. https://www.geeksforgeeks.org/applications-of-breadth-first-traversal/
3. “Breadth First Search: Algorithm & use cases | StudySmarter,” StudySmarter UK. https://www.studysmarter.co.uk/explanations/computer-science/algorithms-in-computer-science/breadth-first-search/
4. “Breadth First Search Tutorials & Notes | Algorithms | HackerEarth,” HackerEarth. https://www.hackerearth.com/practice/algorithms/graphs/breadth-first-search/tutorial/
5. S. Beamer, K. Asanović, and D. Patterson, “Direction-Optimizing Breadth-First search,” DOAJ (DOAJ: Directory of Open Access Journals), Jan. 2013.
6. M. Krimgen and M. Krimgen, “A guide to Java HashMap | Baeldung,” Baeldung, May 11, 2024. https://www.baeldung.com/java-hashmap
7. M. Kurant, A. Markopoulou, and P. Thiran, “On the bias of BFS (Breadth First Search),” IEEE, pp. 1–8, Sep. 2010, doi: 10.1109/itc.2010.5608727. Available: https://citeseerx.ist.psu.edu/document?repid=rep1&type=pdf&doi=1a42cf0636bf722d3ea8a767977b1c52bdcf42c3
