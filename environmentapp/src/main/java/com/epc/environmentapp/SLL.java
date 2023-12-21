/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.epc.environmentapp;

class Node <T>{
    int index;
    T data;
    Node<T> next;
    public Node(T data){
        index = 0;
        this.data = data;
        next = null;
    }
    public Node(){
        index = 0;
        data = null;
        next = null;
    }

}

public class SLL<T> {
    Node<T> current;
    Node<T> head;
    int currentIndex = -1;
    
    public SLL(T d){
        head = new Node<>(d);
        current = head;
        currentIndex += 1;
        current.index = currentIndex;
    }
    
    public SLL(){
        head = null;
        current = head;
    }
    
    public void add(T d){
        currentIndex += 1;
        if (head == null){
            head = new Node(d);
            current = head;
            current.index = currentIndex;
        } else {
            Node<T> temp = new Node(d);
            current.next = temp;
            temp.index = currentIndex;
            current = temp;
        }
    }
    
    public int size(){
        return currentIndex+1;
    }
    
    public T getData(int i){ //keep in mind this doesn't get the node, it gets the DATA
        Node<T> temp = head;
        while (temp.index != i){
            temp = temp.next; 
        }
        return temp.data;
    }
    
//    public binRecordAll getModelTable2(int i){
//        Node temp = head;
//        while (temp.index != i){
//            temp = temp.next; 
//        }
//        return (binRecordAll)temp.data;
//    }
    
    public Node getNode(int i){
        Node<T> temp = head;
        while (temp.index != i){
            temp = temp.next; 
        }
        return temp;
    }
    
    public boolean contains(T d){ //Checks if inputted data exists in any node in SLL
        boolean exists = false;
        Node<T> temp = head;
        for (int i = 0; i<this.size(); i++){
            if (temp.data.equals(d)){
                exists = true;
                break;
            }
            temp = temp.next;
        }
        return exists;
    }
    
    public int getIndex(T d){
        int index = 0;
        Node<T> temp = head;
        for (int i = 0; i<this.size(); i++){
            if (temp.data.equals(d)){
                index = i;
                break;
            }
            temp = temp.next;
        }
        return index;
    }
    
    public void clear(){
        head = null; //No longer pointers to any other nodes
        currentIndex = -1;
    }
    
    public void remove(int index){
        Node temp = this.getNode(index); //Node to be deleted
        Node temp2 = temp;
        for (int i = temp.index; i<this.size(); i++){
            temp2.index -= 1;
            temp2 = temp2.next;
        }
        int prevNodeIndex = temp.index-1;
        Node prevNode = this.getNode(prevNodeIndex);
        prevNode.next = temp.next;
        temp = null;
    }
    
    public void switchData(int i, int j){ //Switches data between 2 nodes
        if (i > currentIndex || i < 0 || j > currentIndex || j < 0) {
            System.out.println("Invalid node indexes");
            return;
        }
        Node n1 = this.getNode(i);
        Node n2 = this.getNode(j);
        Object t1 = n1.data;
        Object t2 = n2.data;
        n1.data = t2;
        n2.data = t1;
    }
    
    public void display(){
        Node<T> temp = head;
        System.out.println("----------");
        for (int i = 0; i<this.size(); i++){
            System.out.println(temp.data.toString()+" Index: "+temp.index);
            temp = temp.next;
        }
    }
    
    
}


