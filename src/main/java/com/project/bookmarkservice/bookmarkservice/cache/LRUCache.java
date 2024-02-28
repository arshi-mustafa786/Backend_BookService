package com.project.bookmarkservice.bookmarkservice.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class LRUCache implements Cache{

    // Size denotes the current size of
    // the List while capacity is the
    // maximum size list is allowed to take.
    public int size;

    // Head and tail are the dummy nodes, to
    // implement the queue.
    Node head, tail;

    // 'map' is the Hash that will map
    // the 'key' to 'Nodes'.
    Map<Integer, Node> map;

    // Function to initialize the values.
    public LRUCache(int capacity) {
        // Defining dummy head and tail nodes.
        head = new Node(-1, -1);
        tail = new Node(-1, -1);
        head.next = tail;
        tail.prev = head;

        // Initial size is 0.
        this.size = 0;
        this.capacity = capacity;

        // Initializing the 'map'.
        map=new HashMap<>();

        //initialize the lock
        this.lock = new ReentrantReadWriteLock();
    }
    public static void main(String[] args) {
        LRUCache cache = new LRUCache(5);

        cache.put(1,1);
        //cache.print();
        cache.put(2,2);
        cache.put(3,3);
        cache.put(4,4);
        cache.get(2);
        //cache.print();
        cache.put(5,5);
        cache.put(6,6);

        cache.print();

    }
    private final int capacity;
    private final ReentrantReadWriteLock lock;

    // Node class that denotes the node
    // of doubly linked list.
    static class Node{
        // key and val store the
        // Key-Value pair.
        int key, val;
        // Next and prev are the address
        // of next and previous nodes.
        Node next, prev;

        // Constructor to initialize the
        // key and val.
        Node(int key, int val){
            this.key = key;
            this.val = val;
        }
    }

    public void print(){
        Node temp = head;

        while(temp != null){
            System.out.print(temp.key + "->");
            temp = temp.next;
        }
        System.out.println();
    }

    // Function to add the node
    // next to the head of the List.
     private void addNode(Node node){
        // Assigning the address of head
        // to node's previous pointer.
        node.prev = head;
        // Assigning the address of head's next
        // to node's next pointer.
        node.next = head.next;
        // Now making node to be head's next.
        head.next = node;
        // Then, make node's next's
        // previous to be node.
        node.next.prev = node;
    }

    // Function to remove the 'node' from the list.
     private void removeNode(Node node){
        // Changing address of previous and
        // next pointer.
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    // Function to move 'node' to head
    // of the List.
     private void moveToHead(Node node){
            // Remove it from it current position.
            removeNode(node);

            // Add it to head.
            addNode(node);

    }

    // Function to remove node at
    // the tail of the List.
     private Node popTail(){
        // Store the result in 'ret'.
        Node ret = tail.prev;
        // Remove 'ret'
        removeNode(ret);
        // Return 'ret'.
        return ret;

    }



    // Function to get value with
    // Key as 'key'.
     public int get(int key) {
         this.lock.readLock().lock();

         try {
             // Checking in the 'map' for
             // the 'node' with Key as 'key'.
             Node node = map.get(key);

             // If no such node exists in 'map'
             // Return -1.
             if (node == null)
                 return -1;

             /// Otherwise move it to the head.
             moveToHead(node);

             // Returning the value associated with 'node'.
             return node.val;
         }finally {
             this.lock.readLock().unlock();
         }
    }

    // Function to put a Key-Value pair in Cache.
     public void put(int key, int value) {

         //using re-enterent lock for multithreading
        this.lock.writeLock().lock();

        try {
            // Checking if 'map' already contains a
            // node with Key as 'key'.
            Node node = map.get(key);

            // If it do not exists.
            if (node == null) {
                // Defining a new node that will be
                // inserted in the cache.
                Node newNode = new Node(key, value);
                // Putting in 'map'.
                map.put(key, newNode);
                // Adding it to head of list.
                addNode(newNode);

                // Increasing the size of the list.
                size++;

                // If after adding, 'size' exceeds the
                // capacity.
                if (size > capacity) {
                    // Remove the node at tail, because
                    // it is the least recently used.
                    Node temp = popTail();
                    map.remove(temp.key);

                    // Reducing the size by 1.
                    size--;
                }
            }
            // Otherwise if it exists.
            else {
                // Update the value.
                node.val = value;

                // Move the node to head of the list.
                moveToHead(node);
            }
        }finally {
            this.lock.writeLock().unlock();
        }
    }

}
