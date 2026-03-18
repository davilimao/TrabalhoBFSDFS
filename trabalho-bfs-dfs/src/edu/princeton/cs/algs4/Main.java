package edu.princeton.cs.algs4;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    private static Map<String, Integer> estadoParaIndice = new HashMap<>();
    private static String[] indiceParaEstado;

    public static void main(String[] args) {

        // Estados do Nordeste
        String[] estados = {"MA","PI","CE","RN","PB","PE","AL","SE","BA"};
        indiceParaEstado = estados;

        for (int i = 0; i < estados.length; i++) {
            estadoParaIndice.put(estados[i], i);
        }

        // Leitura do arquivo
        In in = new In("dados/nordeste.txt");
        Graph G = new Graph(estados.length);

        while (!in.isEmpty()) {
            String e1 = in.readString().trim().toUpperCase();
            String e2 = in.readString().trim().toUpperCase();

            if (!estadoParaIndice.containsKey(e1) || !estadoParaIndice.containsKey(e2)) {
                System.out.println("Erro no arquivo: " + e1 + " " + e2);
                continue;
            }

            G.addEdge(estadoParaIndice.get(e1), estadoParaIndice.get(e2));
        }

        Scanner sc = new Scanner(System.in);
        System.out.println("Estados do Nordeste: ");
        for(int i = 0; i < 9; i++) {
            System.out.print(estados[i] + " ");

        }
        System.out.println();
        System.out.print("Estado origem: ");
        String origem = sc.next().toUpperCase();

        System.out.print("Estado destino: ");
        String destino = sc.next().toUpperCase();

        if (!estadoParaIndice.containsKey(origem) || !estadoParaIndice.containsKey(destino)) {
            System.out.println("Estado inválido.");
            return;
        }

        int s = estadoParaIndice.get(origem);
        int t = estadoParaIndice.get(destino);

        // DFS e BFS
        DepthFirstPaths dfs = new DepthFirstPaths(G, s);
        BreadthFirstPaths bfs = new BreadthFirstPaths(G, s);

        System.out.println("\n===== RESULTADOS =====");

        //Conectividade
        System.out.println("\n1. É possível chegar?");
        System.out.println(dfs.hasPathTo(t) ? "SIM" : "NAO");

        //Caminho DFS
        System.out.println("\n2. Caminho DFS:");
        if (dfs.hasPathTo(t)) {
            printPath(dfs.pathTo(t));
        } else {
            System.out.println("Sem caminho");
        }

        //Caminho BFS
        System.out.println("\n3. Caminho BFS:");
        if (bfs.hasPathTo(t)) {
            printPath(bfs.pathTo(t));
        } else {
            System.out.println("Sem caminho");
        }

        //Alcançáveis
        System.out.println("\n4. Estados alcançáveis a partir de " + origem + ":");
        int count = 0;
        for (int v = 0; v < G.V(); v++) {
            if (dfs.hasPathTo(v)) count++;
        }
        System.out.println(count);

        //Ordem DFS
        System.out.println("\n5. Ordem de visita DFS:");
        boolean[] visitedDFS = new boolean[G.V()];
        dfsOrder(G, s, visitedDFS);
        System.out.println();

        //Ordem BFS
        System.out.println("\n6. Ordem de visita BFS:");
        bfsOrder(G, s);

        sc.close();
    }

    // =========================
    // Funções auxiliares
    // =========================

    private static void printPath(Iterable<Integer> path) {
        for (int v : path) {
            System.out.print(indiceParaEstado[v] + " ");
        }
        System.out.println();
    }

    private static void dfsOrder(Graph G, int v, boolean[] visited) {
        visited[v] = true;
        System.out.print(indiceParaEstado[v] + " ");

        for (int w : G.adj(v)) {
            if (!visited[w]) {
                dfsOrder(G, w, visited);
            }
        }
    }

    private static void bfsOrder(Graph G, int s) {
        boolean[] visited = new boolean[G.V()];
        Queue<Integer> queue = new Queue<>();

        visited[s] = true;
        queue.enqueue(s);

        while (!queue.isEmpty()) {
            int v = queue.dequeue();
            System.out.print(indiceParaEstado[v] + " ");

            for (int w : G.adj(v)) {
                if (!visited[w]) {
                    visited[w] = true;
                    queue.enqueue(w);
                }
            }
        }
        System.out.println();
    }
}