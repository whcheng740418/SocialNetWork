/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nchu.graph;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author whcheng
 */
public class Node {

    private int id;
    private List<Integer> child;
    private int childCount;
    private String vertex;
    public State state;

    public Node(int id, String vertex) {
        this.id = id;
        this.vertex = vertex;
        child = new ArrayList();
    }

    public void addChildNode(int id) {
        if(!child.contains(id))
        child.add(id);
    }

    public List<Integer> getChild() {
        return child;
    }

    public int getChildCount() {
        return child.size();
    }

    public String getVertex() {
        return vertex;
    }

    public void setVertex(String vertex) {
        this.vertex = vertex;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
