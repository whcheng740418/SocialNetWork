/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nchu.graph;

import java.util.Map;

/**
 *
 * @author whcheng
 */
public class Demo {
    
    public static void main(String argv[]) throws Exception{
        Graph graph = new Graph();
        graph.open("data/com-amazon.ungraph.txt",Graph.UNDIRECTION);
        //System.out.println(graph.getNode(1).getChild().size());
        graph.bfs(graph.getNode(1));
        
        
        int max = 0;
        for(Map.Entry<Integer, Node> entry:graph.getMap().entrySet()){
            //for(Map.Entry<Integer, Node> ent:graph.getMap().entrySet()){
                //graph.resilience(entry.getValue(), ent.getValue());
                //graph.length(graph.getNode(1));
            //}
            for(Map.Entry<Integer,Node> te : graph.getMap().entrySet()){
                Node tn = te.getValue();
                tn.state=State.Unvisited;
            }
            
            int n = graph.bfs(entry.getValue());
            if(max<n) max = n;
            
        }
        System.out.println(max);    
        
        //graph.resilience(graph.getNode(1),graph.getNode(431432));
        
        //graph.bfs(graph.getNode(1));
        //FloydWarshallShortestPaths floydWarshallShortestPaths = new FloydWarshallShortestPaths(graph);
        //floydWarshallShortestPaths.getDiameter();
    }
    
}
