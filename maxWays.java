import java.util.*;
import java.io.*;
import java.io.IOException;
import java.util.Queue;

import javax.swing.plaf.basic.BasicInternalFrameUI.InternalFramePropertyChangeListener;

import java.lang.Math;

class Aresta{
    private int fluxo;
    private int capacidade;
    private int origem;
    private int destino;

    public Aresta(int fluxo, int capacidade, int origem, int destino){
        this.fluxo = fluxo;
        this.capacidade = capacidade;
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

        //Construindo o grafo
        for(int i = 0; i < this.vertices; i++){

            for(int j = 0; j < this.vertices; j++){

                if(i != j){
                    
                    //Adicionar aresta nos vertices
                    addArestas(0, 1, i, j);
                    
                }
            }

        }
    }

    public void construirGrafo(){

    }

    // criar arestas entre o par de vertices
    public void addArestas(int fluxo, int capacidade, int v, int w)
    {

        if(fluxo <= capacidade){

            Aresta newAresta = new Aresta(fluxo, capacidade, v, w);
            
            grafo.get(v).add( grafo.get(v).size() , newAresta);
        }else{
            System.out.print("ERRO: Fluxo maior que a capacidade");
        }

        
    }

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
}

public class maxWays{
    public static void main(String args[]){
        
        //construir grafo
        buildFork build = new buildFork(3,3);
        build.printFork();
    }
}