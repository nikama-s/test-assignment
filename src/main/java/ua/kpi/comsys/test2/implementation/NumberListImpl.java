/*
 * Copyright (c) 2014, NTUU KPI, Computer systems department and/or its affiliates. All rights reserved.
 * NTUU KPI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 */

package ua.kpi.comsys.test2.implementation;

import java.io.File;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import ua.kpi.comsys.test2.NumberList;

/**
 * Custom implementation of INumberList interface.
 * Has to be implemented by each student independently.
 * 
 * @author Масло Вероніка, ІМ-33, № студентки в списку групи 14
 *
 */
public class NumberListImpl implements NumberList {

    private static class Node {
        Byte value;
        Node prev;
        Node next;

        Node(Byte value) {
            this.value = value;
            this.prev = this;
            this.next = this;
        }
    }

    private Node head;
    private int size;

    /**
     * Default constructor. Returns empty <tt>NumberListImpl</tt>
     */
    public NumberListImpl() {
        head = null;
        size = 0;
    }


    /**
     * Constructs new <tt>NumberListImpl</tt> by <b>decimal</b> number
     * from file, defined in string format.
     *
     * @param file - file where number is stored.
     */
    public NumberListImpl(File file) {
        // TODO Auto-generated method stub
    }


    /**
     * Constructs new <tt>NumberListImpl</tt> by <b>decimal</b> number
     * in string notation.
     *
     * @param value - number in string notation.
     */
    public NumberListImpl(String value) {
        this();
        if (value == null || value.isEmpty()) {
            return;
        }
        
        try {
            if (value.startsWith("-") || !value.matches("\\d+")) {
                return;
            }
            
            BigInteger decimalValue = new BigInteger(value);
            if (decimalValue.compareTo(BigInteger.ZERO) < 0) {
                return;
            }
            
            if (decimalValue.equals(BigInteger.ZERO)) {
                add((byte) 0);
            } else {
                while (decimalValue.compareTo(BigInteger.ZERO) > 0) {
                    BigInteger[] divRem = decimalValue.divideAndRemainder(BigInteger.valueOf(16));
                    add(divRem[1].byteValue());
                    decimalValue = divRem[0];
                }
            }
        } catch (NumberFormatException e) {
        }
    }


    /**
     * Saves the number, stored in the list, into specified file
     * in <b>decimal</b> scale of notation.
     *
     * @param file - file where number has to be stored.
     */
    public void saveList(File file) {
        // TODO Auto-generated method stub
    }


    /**
     * Returns student's record book number, which has 4 decimal digits.
     *
     * @return student's record book number.
     */
    public static int getRecordBookNumber() {
        return 14;
    }


    /**
     * Returns new <tt>NumberListImpl</tt> which represents the same number
     * in other scale of notation, defined by personal test assignment.<p>
     *
     * Does not impact the original list.
     *
     * @return <tt>NumberListImpl</tt> in other scale of notation.
     */
    public NumberListImpl changeScale() {
        // TODO Auto-generated method stub
        return null;
    }


    /**
     * Returns new <tt>NumberListImpl</tt> which represents the result of
     * additional operation, defined by personal test assignment.<p>
     *
     * Does not impact the original list.
     *
     * @param arg - second argument of additional operation
     *
     * @return result of additional operation.
     */
    public NumberListImpl additionalOperation(NumberList arg) {
        // TODO Auto-generated method stub
        return null;
    }


    /**
     * Returns string representation of number, stored in the list
     * in <b>decimal</b> scale of notation.
     *
     * @return string representation in <b>decimal</b> scale.
     */
    public String toDecimalString() {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public boolean equals(Object o) {
        // TODO Auto-generated method stub
        return false;
    }


    @Override
    public int size() {
        return size;
    }


    @Override
    public boolean isEmpty() {
        return size == 0;
    }


    @Override
    public boolean contains(Object o) {
        return indexOf(o) >= 0;
    }


    @Override
    public Iterator<Byte> iterator() {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public Object[] toArray() {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public <T> T[] toArray(T[] a) {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public boolean add(Byte e) {
        if (e == null) {
            throw new NullPointerException();
        }
        if (e < 0 || e > 15) {
            throw new IllegalArgumentException("Digit must be in range 0-15 for hexadecimal");
        }
        
        add(size, e);
        return true;
    }


    @Override
    public boolean remove(Object o) {
        int index = indexOf(o);
        if (index >= 0) {
            remove(index);
            return true;
        }
        return false;
    }


    @Override
    public boolean containsAll(Collection<?> c) {
        // TODO Auto-generated method stub
        return false;
    }


    @Override
    public boolean addAll(Collection<? extends Byte> c) {
        // TODO Auto-generated method stub
        return false;
    }


    @Override
    public boolean addAll(int index, Collection<? extends Byte> c) {
        // TODO Auto-generated method stub
        return false;
    }


    @Override
    public boolean removeAll(Collection<?> c) {
        // TODO Auto-generated method stub
        return false;
    }


    @Override
    public boolean retainAll(Collection<?> c) {
        // TODO Auto-generated method stub
        return false;
    }


    @Override
    public void clear() {
        head = null;
        size = 0;
    }

    private Node getNode(int index) {
        if (index < 0 || index >= size) {
        throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        
        if (head == null) {    
            return null;
        }
        
        Node current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    @Override
    public Byte get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        Node node = getNode(index);
        return node != null ? node.value : null;
    }


    @Override
    public Byte set(int index, Byte element) {
        if (element == null) {
            throw new NullPointerException();
        }
        if (element < 0 || element > 15) {
            throw new IllegalArgumentException("Digit must be in range 0-15 for hexadecimal");
        }
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        
        Node node = getNode(index);
        Byte oldValue = node.value;
        node.value = element;
        return oldValue;
    }


    @Override
    public void add(int index, Byte element) {
        if (element == null) {
            throw new NullPointerException();
        }
        if (element < 0 || element > 15) {
            throw new IllegalArgumentException("Digit must be in range 0-15 for hexadecimal");
        }
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        
        Node newNode = new Node(element);
        
        if (size == 0) {
            head = newNode;
            size = 1;
        } else if (index == size) {
            Node last = head.prev;
            newNode.next = head;
            newNode.prev = last;
            last.next = newNode;
            head.prev = newNode;
            size++;
        } else {
            Node current = getNode(index);
            Node prevNode = current.prev;
            
            newNode.next = current;
            newNode.prev = prevNode;
            prevNode.next = newNode;
            current.prev = newNode;
            
            if (index == 0) {
                head = newNode;
            }
            size++;
        }
    }


    @Override
    public Byte remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        
        Node nodeToRemove = getNode(index);
        Byte value = nodeToRemove.value;
        
        if (size == 1) {
            head = null;
            size = 0;
        } else {
            Node prevNode = nodeToRemove.prev;
            Node nextNode = nodeToRemove.next;
            
            prevNode.next = nextNode;
            nextNode.prev = prevNode;
            
            if (index == 0) {
                head = nextNode;
            }
            size--;
        }
        
        return value;
    }


    @Override
    public int indexOf(Object o) {
        if (o == null || head == null) {
            return -1;
        }
        
        if (!(o instanceof Byte)) {
            return -1;
        }
        
        Byte target = (Byte) o;
        Node current = head;
        
        for (int i = 0; i < size; i++) {
            if (target.equals(current.value)) {
                return i;
            }
            current = current.next;
        }
        
        return -1;
    }


    @Override
    public int lastIndexOf(Object o) {
        if (o == null || head == null) {
            return -1;
        }
        
        if (!(o instanceof Byte)) {
            return -1;
        }
        
        Byte target = (Byte) o;
        Node current = head.prev;
        
        for (int i = size - 1; i >= 0; i--) {
            if (target.equals(current.value)) {
                return i;
            }
            current = current.prev;
        }
        
        return -1;
    }


    @Override
    public ListIterator<Byte> listIterator() {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public ListIterator<Byte> listIterator(int index) {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public List<Byte> subList(int fromIndex, int toIndex) {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public boolean swap(int index1, int index2) {
        // TODO Auto-generated method stub
        return false;
    }


    @Override
    public void sortAscending() {
        // TODO Auto-generated method stub
    }


    @Override
    public void sortDescending() {
        // TODO Auto-generated method stub
    }


    @Override
    public void shiftLeft() {
        // TODO Auto-generated method stub

    }


    @Override
    public void shiftRight() {
        // TODO Auto-generated method stub

    }
}
