package cs5060.project.dragonmaze;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Final Project: CS 5060
 * DragonMaze Project : Queue.java
 * The Queue class represents a generic Queue, implemented as a Linked List
 * 
 * @author Subhadeep A01631525
 * @source http://introcs.cs.princeton.edu/java/43stack/
 *
 * @param <Item>
 */
public class Queue<Item> implements Iterable<Item>
{
    private int size;         
    private Node first;    
    
    private Node last;     
    
    /**
     * Node class to represent a standard linked list
     * @author Deep
     *
     */
    private class Node
    {
        private Item data;
        private Node next;
    }

   /**
     * Constructor to create an empty queue.
     */
    public Queue()
    {
        first = null;
        last  = null;
        size = 0;  
    }

   /**
     * Method to determine if the queue is empty or not
     */
    public boolean isEmpty()
    {
        return first == null;
    }

   /**
     * Add the item to the queue.
     */
    public void add(Item item)
    {
        Node previous = last;
        last = new Node();
        last.data = item;
        last.next = null;
        if (isEmpty())
        	first = last;
        else           
        	previous.next = last;
        size++;
        
    }

   /**
     * Remove and return the item on the queue
     * @throws java.util.NoSuchElementException if queue is empty.
     */
    public Item remove()
    {
        if (isEmpty())
        	throw new NoSuchElementException("Queue underflow");
        Item item = first.data;
        first = first.next;
        size--;
        if (isEmpty())
        	last = null;   
        
        return item;
    }

   
    /**
     * Return an iterator that iterates over the items on the queue in FIFO order.
     */
    public Iterator<Item> iterator() 
    {
        return new ListIterator();  
    }


    private class ListIterator implements Iterator<Item>
    {
        private Node current = first;

        public boolean hasNext()
        { 
        	return current != null;                     
        }
        public void remove()
        {
        	throw new UnsupportedOperationException();
        }

        public Item next()
        {
            if (!hasNext())
            	throw new NoSuchElementException();
            Item item = current.data;
            current = current.next; 
            return item;
        }
    }
}