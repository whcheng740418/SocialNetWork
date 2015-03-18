/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nchu.graph;


import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 *
 * @author whcheng
 */
public class Graph {

    private Set<Integer> ver = new HashSet<Integer>();
    private HashMap<Integer, Node> map = new HashMap<Integer, Node>();
    private ArrayList<Integer> list = new ArrayList();
    private HashMap<Integer,Integer> path_map = new HashMap();
    
    public static int DIRECTION = 0;
    public static int UNDIRECTION = 1;

    public Graph() {

    }
    
    

    public void open(String filename,int type) throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String str = "";
        while ((str = reader.readLine()) != null) {
            String[] s = str.split("\t");
            int n1 = Integer.valueOf(s[0]);
            int n2 = Integer.valueOf(s[1]);
            ver.add(n1);
            ver.add(n2);
            switch(type){
                case 0:
                    Node root = map.get(n1);
                    if (root == null) {
                        root = new Node(n1, "");
                        root.state = State.Unvisited;
                        map.put(n1, root);
                    }
                    root.addChildNode(n2);

                    Node root_2 = map.get(n2);
                    if (root_2 == null) {
                        root_2 = new Node(n2, "");
                        root_2.state = State.Unvisited;
                        map.put(n2, root_2);
                    }
                break;
                case 1:
                    root = map.get(n1);
                    if (root == null) {
                        root = new Node(n1, "");
                        root.state = State.Unvisited;
                        map.put(n1, root);
                    }
                    root.addChildNode(n2);

                    root_2 = map.get(n2);
                    if (root_2 == null) {
                        root_2 = new Node(n2, "");
                        root_2.state = State.Unvisited;
                        map.put(n2, root_2);
                    }
                    root_2.addChildNode(n1);
                break;
            }
            
        }
    }

    public Node getNode(int id) {
        return map.get(id);
    }
    
    private double loop = 0;
    private ArrayList<List<Integer>> path_list = new ArrayList();
    public void resilience(Node source, Node des){
        list = new ArrayList();
        path_map = new HashMap();
        path_list = new ArrayList();
        findAllPath(list,source.getId());
        loop = 0;
        ArrayList<Integer> in = new ArrayList<Integer>();
        ArrayList<Integer> out = new ArrayList<Integer>();
        
        for(List<Integer> l :path_list){
            if(l.contains(des.getId())){
                loop++;
                //System.out.println(l);
                for(int i=0;i<l.size();i++){
                    if(i+1<l.size()){
                        in.add(l.get(i));
                        out.add(l.get(i+1));
                    }
                    //System.out.println(l.get(i)+"\t"+l.get(i+1));
                    int n = path_map.get(l.get(i))==null?0:path_map.get(l.get(i));
//                        System.out.println(i+"  "+n);
                        path_map.put(l.get(i), n+1);
                    if(l.get(i)==des.getId()){
                        break;
                    }
                }
            }
        }
        
        //from=c("Bob", "Cecil", "Cecil", "David", "David", "Esmeralda"),
        //to=c("Alice", "Bob", "Alice", "Alice", "Bob", "Alice"),
        //weight=c(4,5,5,2,1,1)
        
        String str = "from=c(";
        for(int i:in){
            str = str+i+",";
        }
        str = str.substring(0, str.length()-1)+"),";
        System.out.println(str);
        
        str = "to=c(";
        for(int i:out){
            str = str+i+",";
        }
        str = str.substring(0, str.length()-1)+"),";
        System.out.println(str);
        
        str = "weight=c(";
        for(int i:out){
            str = str+"1,";
        }
        str = str.substring(0, str.length()-1)+"))";
        System.out.println(str);
        
        if(!path_map.isEmpty()){
            System.out.println("path count : "+loop);
            System.out.println("-------------"+source.getId()+">>"+des.getId()+"----------");
            for(Map.Entry<Integer, Integer> entry:path_map.entrySet()){
                System.out.println(entry.getKey()+" = "+(entry.getValue()/loop));
            }
        }
    }
    
    
    /*
    public void visitNode(List<Integer> path, int root_id, int targe_id) {
        //Avoid infinite loops
        //System.out.println("-----------"+root.getId()+"   "+root.getChildCount());

        //System.out.print(root.getId() + "\t");
        Node root = getNode(root_id);
        path.add(root_id);
        if(root_id==targe_id) {
            for(Integer i:path){
                int n = path_map.get(i)==null?0:path_map.get(i);
                System.out.println(i+"  "+n);
                path_map.put(i, n+1);
            }
            loop++;
            System.out.println("LOOP    "+loop);
            
        }else if (root.state == State.Unvisited) {
            root.state = State.Visited;
            //for every child
            for (Integer childerid : root.getChild()) {
                //if childs state is not visited then recurse
                visitNode(path,childerid,targe_id);
            }
        }
    }
    */
    
    public void findAllPath(List<Integer> path, int root_id) {
        //Avoid infinite loops
        //System.out.println("-----------"+root.getId()+"   "+root.getChildCount());

        //System.out.print(root.getId() + "\t");
        Node root = getNode(root_id);
        //System.out.println(root_id);
        
//        if (root.state == State.Unvisited) {
            path.add(root_id);
//            root.state = State.Visited;
            //for every child
            for (Integer childerid : root.getChild()) {
                //if childs state is not visited then recurse
                ArrayList<Integer> array = new ArrayList<Integer>(path);
                findAllPath(array,childerid);
                //System.out.println(array);
                path_list.add(array);
//                for(int i = path.size()-1 ;i>=0;i--){
//                    //System.out.println("i : "+i);
//                    if(getNode(path.get(i)).getChildCount()==0){
//                        path.remove(i);
//                        //System.out.println("--"+path);
//                    }else{
//                        break;
//                    }
//                }
            }
//        }
    }
    
    
    public void dfs(Node root) {
        //Avoid infinite loops
        //System.out.println("-----------"+root.getId()+"   "+root.getChildCount());

        //System.out.print(root.getId() + "\t");
        
        list.add(root.getId());

        if (root.state == State.Unvisited) {
            //for every child
            root.state = State.Visited;
            for (Integer childerid : root.getChild()) {
                //if childs state is not visited then recurse
                Node childrennode = getNode(childerid);
                dfs(childrennode);
            }
        }
    }
    
    public double length(Node root){
        list = new ArrayList();
        diameter(root,0);
        int max = 0;
        for(Integer n :list){
            if(n>max){
                max = n;
            }
        }
        System.out.println(max);
        return max;
    }
    
    
    public void diameter(Node root,int length) {
        
        //System.out.println("-----------"+root.getId()+"   "+root.getChildCount());

        //System.out.print(root.getId() + "\t");

        if (root.state == State.Unvisited) {
            //for every child
            root.state = State.Visited;
            length++;
            for (int childerid : root.getChild()) {
                //if childs state is not visited then recurse
                Node childrennode = getNode(childerid);
                diameter(childrennode,length);
                list.add(length);
                //System.out.println(length);
            }
        }
    }

    
    public int bfs(Node root) {
        //Since queue is a interface
        Queue<Node> queue = new LinkedList<Node>();
        int count = 0;

        root.state = State.Visited;
        //Adds to end of queue
        queue.add(root);

        while (!queue.isEmpty()) {
            //removes from front of queue
            Node r = queue.remove();
            //System.out.print(r.getId() + "\t");
            count++;

            for (Integer childerid : r.getChild()) {
                Node childrennode = getNode(childerid);
                if(childrennode.state == State.Unvisited){
                    queue.add(childrennode);
                    childrennode.state = State.Visited;
                }
            }
        }
        
        return count;

    }

    public Set<Integer> getVer() {
        return ver;
    }

    public void setVer(Set<Integer> ver) {
        this.ver = ver;
    }

    public HashMap<Integer, Node> getMap() {
        return map;
    }

    public void setMap(HashMap<Integer, Node> map) {
        this.map = map;
    }
    
    
}
