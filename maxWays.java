import java.util.*;
import java.io.*;
import java.io.IOException;
import java.util.Queue;

import javax.swing.plaf.basic.BasicInternalFrameUI.InternalFramePropertyChangeListener;

import java.lang.Math;

class Aresta{
    private int fluxo;
    private int capacidade = 1;
    private int origem;
    private int destino;
    private int f;

    public Aresta(int fluxo, int destino, int origem){
        this.fluxo = fluxo;
        this.origem = origem;
        this.destino = destino;
    }

    public int getCapacidade(){
        return this.capacidade;
    }

    public int getFluxo(){
        return this.fluxo;
    }

    public int getOrigem(){
        return this.origem;
    }

    public int getDestino(){
        return this.destino;
    }

    public int getF(){
        return this.f;
    }

    public void setFluxo(int fluxo){
        this.fluxo = fluxo;
    }

    public void setCapacidade(int capacidade){
        this.capacidade = capacidade;
    }

    public void setOrigem(int origem){
        this.origem = origem;
    }

    public void setDestino(int destino){
        this.destino = destino;
    }
    
    public void setF(int f){
        this.f = f;
    }

    public boolean fluxoCheio(){
        return this.capacidade == this.fluxo;
    }
}
class buildFork{

    public int vertices;
    public int Arestas;

  
    //limitar o numero de vertices
    int limiteVertices;
    int raio;
    int infinito = Integer.MAX_VALUE;
  
    // definir dados
    Random random = new Random();
    public List<List<Aresta>> grafo;
    
    //construtor
    public buildFork(int qtdVertices, int qtdArestas)
    {
        this.vertices = qtdVertices;
        
        // Instanciando o grafo
        grafo = new ArrayList<>(vertices);
        for (int i = 0; i < vertices; i++){
            grafo.add(new ArrayList<Aresta>());
        }
    }

    // criar arestas entre o par de vertices
    // public void addArestas(int fluxo, int capacidade, int v, int w)
    // {
    //     if(fluxo <= capacidade){

    //         Aresta newAresta = new Aresta(fluxo, capacidade, v, w);
            
    //         grafo.get(v).add( grafo.get(v).size() , newAresta);
    //     }else{
    //         System.out.print("ERRO: Fluxo maior que a capacidade");
    //     }        
    // }

    public int getVerticeSize(){
        return grafo.size();
    }

    public Aresta getAresta(int v, int d){

        Aresta aresta = null;

        for(int i = 0; i < grafo.get(v).size(); i++){

            aresta = grafo.get(v).get(i);

            if(aresta.getDestino() == d)
                return aresta;
        }

        return aresta;
    }

    public int getIndexAresta(Aresta aresta){

        int index = -1;

        for(int i = 0; i < grafo.get(aresta.getOrigem()).size(); i++){

            if(grafo.get(aresta.getOrigem()).get(i).getDestino() == aresta.getDestino()){
                
                index = i;
                return index;
            }
        }

        return index;
    }

    public Aresta getArestaForIndex(int v, int index){

        Aresta aresta = grafo.get(v).get(index);
        
        return aresta;
    }

    public int getArestaSize(int v){
        return grafo.get(v).size();
    }

    public void printFork(){
        for(int i = 0; i < grafo.size(); i++){
            System.out.print("Vertice ("+ i +")");
            for(int j = 0; j< grafo.get(i).size(); j++){
                System.out.print(" arestas( origem: " + getArestaForIndex(i, j).getOrigem() +", destino: "+ getArestaForIndex(i, j).getDestino()+")");
            } 
            System.out.print("\n");
        }
    }

    public List<Aresta> getVertice(int i){
        return grafo.get(i);
    }
}

public class maxWays{
    
    public static void addEdge(buildFork graph, int s, int t, int cap) {
    
        graph.getVertice(s).add(new Aresta(cap, t, graph.getVertice(t).size()));
        graph.getVertice(t).add(new Aresta(0, s, graph.getVertice(s).size() - 1));
    
    }
    
    public static buildFork lerArquivoTxt(String path) throws IOException{

        BufferedReader buffRead = new BufferedReader(new FileReader(path));
        String linha = "";

        //Construir grafo inicial com distancias infinitas
        buildFork grafo;
        linha = buffRead.readLine();
        String[] cabecalho = linha.split(" ");
        grafo = new buildFork(Integer.parseInt(cabecalho[0]), 10);

        boolean finish = false;

        while (!finish) {

            linha = buffRead.readLine();

            if(linha == null){
                finish = true;
            }
            else{
                String[] aresta = linha.split(" ");
                addEdge(grafo,  Integer.parseInt(aresta[0]), Integer.parseInt(aresta[1]), Integer.parseInt(aresta[2]));
            }
        }
        //fechar arquivo após leitura
        buffRead.close();
        return grafo;
    }

    static boolean dinicBfs(buildFork graph, int src, int dest, int[] dist) {
        Arrays.fill(dist, -1);
        dist[src] = 0;
        int[] Q = new int[graph.getVerticeSize()];
        int sizeQ = 0;
        Q[sizeQ++] = src;
        for (int i = 0; i < sizeQ; i++) {
          int u = Q[i];
          for (Aresta e : graph.getVertice(u)) {
            if (dist[e.getDestino()] < 0 && e.getF() < e.getFluxo()) {
              dist[e.getDestino()] = dist[u] + 1;
              Q[sizeQ++] = e.getDestino();
            }
          }
        }
        return dist[dest] >= 0;
      }
    
    //   static boolean dinicBfs(List<Edge>[] graph, int src, int dest, int[] dist) {
    //     Arrays.fill(dist, -1);
    //     dist[src] = 0;
    //     int[] Q = new int[graph.length];
    //     int sizeQ = 0;
    //     Q[sizeQ++] = src;
    //     for (int i = 0; i < sizeQ; i++) {
    //       int u = Q[i];
    //       for (Edge e : graph[u]) {
    //         if (dist[e.t] < 0 && e.f < e.cap) {
    //           dist[e.t] = dist[u] + 1;
    //           Q[sizeQ++] = e.t;
    //         }
    //       }
    //     }
    //     return dist[dest] >= 0;
    //   }
    

      static int dinicDfs(buildFork graph, int[] ptr, int[] dist, int dest, int u, int f) {
        if (u == dest)
          return f;
        for (; ptr[u] < graph.getVertice(u).size(); ++ptr[u]) {
          Aresta e = graph.getVertice(u).get(ptr[u]);
          if (dist[e.getDestino()] == dist[u] + 1 && e.getF() < e.getFluxo()) {
            int df = dinicDfs(graph, ptr, dist, dest, e.getDestino(), Math.min(f, e.getFluxo() - e.getF()));
            if (df > 0) {
              e.setF(e.getF() + df);
              graph.getAresta(e.getDestino(), e.getFluxo()).setF(graph.getAresta(e.getDestino(), e.getFluxo()).getF() - df);
              return df;
            }
          }
        }
        return 0;
      }
    
      public static int maxFlow(buildFork graph, int src, int dest) {
        int flow = 0;
        int[] dist = new int[graph.getVerticeSize()];
        while (dinicBfs(graph, src, dest, dist)) {
          int[] ptr = new int[graph.getVerticeSize()];
          while (true) {
            int df = dinicDfs(graph, ptr, dist, dest, src, Integer.MAX_VALUE);
            if (df == 0)
              break;
            flow += df;
          }
        }
        return flow;
      }
    
    public static void main (String args[]) throws IOException {
        
        String namePathFork = "./grafo.txt";
        //construir grafo
        buildFork fork = lerArquivoTxt(namePathFork);
        fork.printFork();

        // buildFork graph = new buildFork(3, 10);
        // addEdge(graph, 0, 1, 1);
        // addEdge(graph, 0, 2, 1);
        // addEdge(graph, 1, 2, 1);
        // System.out.println(maxFlow(graph, 0, 2));

        System.out.println(maxFlow(fork, 0, 3));
    }
}