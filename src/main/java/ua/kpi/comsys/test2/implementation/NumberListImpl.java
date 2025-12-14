/*
 * Copyright (c) 2014, NTUU KPI, Computer systems department and/or its affiliates. All rights reserved.
 * NTUU KPI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 */

package ua.kpi.comsys.test2.implementation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
    private int base = 16;

    /**
     * Default constructor. Returns empty <tt>NumberListImpl</tt>
     */
    public NumberListImpl() {
        head = null;
        size = 0;
        base = 16;
    }

    /**
     * Private constructor for creating list with specific base.
     */
    private NumberListImpl(int base) {
        head = null;
        size = 0;
        this.base = base;
    }


    /**
     * Constructs new <tt>NumberListImpl</tt> by <b>decimal</b> number
     * from file, defined in string format.
     *
     * @param file - file where number is stored.
     */
    public NumberListImpl(File file) {
        this();
        if (file == null || !file.exists()) {
            return;
        }
        
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine();
            if (line == null || line.isEmpty()) {
                return;
            }
            
            if (line.startsWith("-") || !line.matches("\\d+")) {
                return;
            }
            
            BigInteger decimalValue = new BigInteger(line);
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
        } catch (IOException | NumberFormatException e) {
            // Return empty list on error
        }
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
        if (file == null) {
            return;
        }
        
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write(toDecimalString());
        } catch (IOException e) {
            // Silently fail on error
        }
    }


    /**
     * Returns student's record book number, which has 4 decimal digits.
     *
     * @return student's record book number.
     */
    public static int getRecordBookNumber() {
        return 4214;
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
        // C5=4 means hex (base 16), additional scale = (C5+1) mod 5 = 0 = binary (base 2)
        int targetBase = 2;
        
        NumberListImpl result = new NumberListImpl(targetBase);
        
        if (isEmpty()) {
            result.addInternal((byte) 0);
            return result;
        }
        
        BigInteger decimalValue = new BigInteger(toDecimalString());
        
        if (decimalValue.equals(BigInteger.ZERO)) {
            result.addInternal((byte) 0);
        } else {
            while (decimalValue.compareTo(BigInteger.ZERO) > 0) {
                BigInteger[] divRem = decimalValue.divideAndRemainder(BigInteger.valueOf(targetBase));
                result.addInternal(divRem[1].byteValue());
                decimalValue = divRem[0];
            }
        }
        
        return result;
    }

    /**
     * Internal add method that bypasses the hex digit validation.
     * Used for creating lists in different bases.
     */
    private void addInternal(Byte element) {
        if (element == null) {
            throw new NullPointerException();
        }
        
        Node newNode = new Node(element);
        
        if (size == 0) {
            head = newNode;
            size = 1;
        } else {
            Node last = head.prev;
            newNode.next = head;
            newNode.prev = last;
            last.next = newNode;
            head.prev = newNode;
            size++;
        }
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
        // C7=0 means addition
        if (arg == null) {
            return null;
        }
        
        NumberListImpl other = (NumberListImpl) arg;
        
        BigInteger thisValue = new BigInteger(this.toDecimalString());
        BigInteger otherValue = new BigInteger(other.toDecimalString());
        
        BigInteger sum = thisValue.add(otherValue);
        
        NumberListImpl result = new NumberListImpl(this.base);
        
        if (sum.equals(BigInteger.ZERO)) {
            result.addInternal((byte) 0);
        } else {
            while (sum.compareTo(BigInteger.ZERO) > 0) {
                BigInteger[] divRem = sum.divideAndRemainder(BigInteger.valueOf(this.base));
                result.addInternal(divRem[1].byteValue());
                sum = divRem[0];
            }
        }
        
        return result;
    }


    /**
     * Returns string representation of number, stored in the list
     * in <b>decimal</b> scale of notation.
     *
     * @return string representation in <b>decimal</b> scale.
     */
    public String toDecimalString() {
        if (isEmpty()) {
            return "0";
        }
        
        BigInteger result = BigInteger.ZERO;
        BigInteger baseValue = BigInteger.valueOf(base);
        BigInteger multiplier = BigInteger.ONE;
        
        Node current = head;
        for (int i = 0; i < size; i++) {
            result = result.add(BigInteger.valueOf(current.value).multiply(multiplier));
            multiplier = multiplier.multiply(baseValue);
            current = current.next;
        }
        
        return result.toString();
    }


    @Override
    public String toString() {
        if (isEmpty()) {
            return "0";
        }
        
        StringBuilder sb = new StringBuilder();
        Node current = head.prev;
        
        for (int i = size - 1; i >= 0; i--) {
            byte digit = current.value;
            if (digit < 10) {
                sb.append((char) ('0' + digit));
            } else {
                sb.append((char) ('A' + (digit - 10)));
            }
            current = current.prev;
        }
        
        return sb.toString();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NumberListImpl)) {
            return false;
        }
        
        NumberListImpl other = (NumberListImpl) o;
        
        if (this.size != other.size) {
            return false;
        }
        
        if (this.isEmpty() && other.isEmpty()) {
            return true;
        }
        
        Node thisCurrent = this.head;
        Node otherCurrent = other.head;
        
        for (int i = 0; i < size; i++) {
            if (!thisCurrent.value.equals(otherCurrent.value)) {
                return false;
            }
            thisCurrent = thisCurrent.next;
            otherCurrent = otherCurrent.next;
        }
        
        return true;
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
        return listIterator();
    }


    @Override
    public Object[] toArray() {
        Object[] array = new Object[size];
        int i = 0;
        Node current = head;
        for (int j = 0; j < size; j++) {
            array[i++] = current.value;
            current = current.next;
        }
        return array;
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
        if (c == null) {
            throw new NullPointerException();
        }
        if (c.isEmpty()) {
            return true;
        }
        for (Object o : c) {
            if (!contains(o)) {
                return false;
            }
        }
        return true;
    }


    @Override
    public boolean addAll(Collection<? extends Byte> c) {
        if (c == null) {
            throw new NullPointerException();
        }
        if (c.isEmpty()) {
            return false;
        }
        boolean modified = false;
        for (Byte e : c) {
            if (add(e)) {
                modified = true;
            }
        }
        return modified;
    }


    @Override
    public boolean addAll(int index, Collection<? extends Byte> c) {
        if (c == null) {
            throw new NullPointerException();
        }
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        if (c.isEmpty()) {
            return false;
        }
        
        int i = index;
        for (Byte e : c) {
            add(i, e);
            i++;
        }
        return true;
    }


    @Override
    public boolean removeAll(Collection<?> c) {
        if (c == null) {
            throw new NullPointerException();
        }
        if (c.isEmpty()) {
            return false;
        }
        boolean modified = false;
        Iterator<?> it = iterator();
        while (it.hasNext()) {
            if (c.contains(it.next())) {
                it.remove();
                modified = true;
            }
        }
        return modified;
    }


    @Override
    public boolean retainAll(Collection<?> c) {
        if (c == null) {
            throw new NullPointerException();
        }
        boolean modified = false;
        Iterator<Byte> it = iterator();
        while (it.hasNext()) {
            if (!c.contains(it.next())) {
                it.remove();
                modified = true;
            }
        }
        return modified;
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
        return listIterator(0);
    }


    @Override
    public ListIterator<Byte> listIterator(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        return new NumberListIterator(index);
    }

    private class NumberListIterator implements ListIterator<Byte> {
        private Node nextNode;
        private Node lastReturned;
        private int nextIndex;

        NumberListIterator(int index) {
            if (index == size) {
                nextNode = head;
                nextIndex = size;
            } else {
                nextNode = getNode(index);
                nextIndex = index;
            }
        }

        @Override
        public boolean hasNext() {
            return nextIndex < size;
        }

        @Override
        public Byte next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            lastReturned = nextNode;
            nextNode = nextNode.next;
            nextIndex++;
            return lastReturned.value;
        }

        @Override
        public boolean hasPrevious() {
            return nextIndex > 0;
        }

        @Override
        public Byte previous() {
            if (!hasPrevious()) {
                throw new java.util.NoSuchElementException();
            }
            nextNode = nextNode.prev;
            nextIndex--;
            lastReturned = nextNode;
            return lastReturned.value;
        }

        @Override
        public int nextIndex() {
            return nextIndex;
        }

        @Override
        public int previousIndex() {
            return nextIndex - 1;
        }

        @Override
        public void remove() {
            if (lastReturned == null) {
                throw new IllegalStateException();
            }
            Node prev = lastReturned.prev;
            Node next = lastReturned.next;
            
            prev.next = next;
            next.prev = prev;
            
            if (lastReturned == head) {
                head = next;
            }
            
            if (nextNode == lastReturned) {
                nextNode = next;
            } else {
                nextIndex--;
            }
            
            size--;
            lastReturned = null;
        }

        @Override
        public void set(Byte e) {
            if (lastReturned == null) {
                throw new IllegalStateException();
            }
            if (e == null) {
                throw new NullPointerException();
            }
            if (e < 0 || e > 15) {
                throw new IllegalArgumentException("Digit must be in range 0-15 for hexadecimal");
            }
            lastReturned.value = e;
        }

        @Override
        public void add(Byte e) {
            if (e == null) {
                throw new NullPointerException();
            }
            if (e < 0 || e > 15) {
                throw new IllegalArgumentException("Digit must be in range 0-15 for hexadecimal");
            }
            
            lastReturned = null;
            
            if (nextNode == null || size == 0) {
                Node newNode = new Node(e);
                head = newNode;
                nextNode = newNode;
                size = 1;
            } else {
                Node newNode = new Node(e);
                Node prev = nextNode.prev;
                
                newNode.next = nextNode;
                newNode.prev = prev;
                prev.next = newNode;
                nextNode.prev = newNode;
                
                if (nextNode == head) {
                    head = newNode;
                }
                size++;
            }
            nextIndex++;
        }
    }


    @Override
    public List<Byte> subList(int fromIndex, int toIndex) {
        if (fromIndex < 0 || toIndex > size || fromIndex > toIndex) {
            throw new IndexOutOfBoundsException();
        }
        
        NumberListImpl subList = new NumberListImpl();
        for (int i = fromIndex; i < toIndex; i++) {
            subList.add(get(i));
        }
        return subList;
    }


    @Override
    public boolean swap(int index1, int index2) {
        if (index1 < 0 || index1 >= size || index2 < 0 || index2 >= size) {
            return false;
        }
        if (index1 == index2) {
            return true;
        }
        
        Node node1 = getNode(index1);
        Node node2 = getNode(index2);
        
        Byte temp = node1.value;
        node1.value = node2.value;
        node2.value = temp;
        
        return true;
    }


    @Override
    public void sortAscending() {
        if (size <= 1) {
            return;
        }
        
        boolean swapped;
        do {
            swapped = false;
            Node current = head;
            for (int i = 0; i < size - 1; i++) {
                if (current.value > current.next.value) {
                    Byte temp = current.value;
                    current.value = current.next.value;
                    current.next.value = temp;
                    swapped = true;
                }
                current = current.next;
            }
        } while (swapped);
    }


    @Override
    public void sortDescending() {
        if (size <= 1) {
            return;
        }
        
        boolean swapped;
        do {
            swapped = false;
            Node current = head;
            for (int i = 0; i < size - 1; i++) {
                if (current.value < current.next.value) {
                    Byte temp = current.value;
                    current.value = current.next.value;
                    current.next.value = temp;
                    swapped = true;
                }
                current = current.next;
            }
        } while (swapped);
    }


    @Override
    public void shiftLeft() {
        if (size <= 1) {
            return;
        }

        head = head.next;
    }


    @Override
    public void shiftRight() {
        if (size <= 1) {
            return;
        }

        head = head.prev;
    }
}
