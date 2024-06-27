package org.example;

import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        // Create nodes
        Birey A = new Birey("A");
        Birey B = new Birey("B");
        Birey C = new Birey("C");
        Birey D = new Birey("D");
        Birey E = new Birey("E");
        Birey F = new Birey("F");

        // Create edges
        A.komsuEkle(E);
        C.komsuEkle(F);
        E.komsuEkle(B);
        E.komsuEkle(D);
        B.komsuEkle(F);



        // Perform DFS starting from nodeA
        Set<Birey> visited = new HashSet<>();
        dfs(A, visited);
    }

    public static void dfs(Birey node, Set<Birey> visited) {
        if (node == null || visited.contains(node)) {
            return;
        }

        // Mark the current node as visited
        visited.add(node);
        //System.out.print(node.getAd()+" ");
        System.out.println(node.getAd()+": "+node.getKomsulari());
        // Recur for all the neighbors
        for (Birey neighbor : node.getKomsulari()) {
            dfs(neighbor, visited);
        }
    }
}
