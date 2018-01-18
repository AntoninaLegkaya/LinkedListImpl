package com.dbbest.linkedlistimpl;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

class LinkedListImpl<E> implements List<E> {

    private Node header;
    private Node tail;
    private int size;

    public E get(int index) {
        return (E) node(index).element;
    }

    private Node node(int index) throws IndexOutOfBoundsException {

        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);

        Node entry = header;

        if (index < (size >> 1)) {
            for (int i = 0; i < index; i++) {
                entry = entry.next;
            }
        } else {
            entry = tail;
            for (int i = size; i > index; i--) {
                entry = entry.prev;
            }
            if (entry != null) {
                entry = entry.next;
            } else {
                entry = tail = header;
            }
        }

        Log.i("LinkedListImpl", "Get node:  " + entry.element + "" +
                " index: " + index);
        return entry;


    }

    void addFirst(E element) {
        Node newElement = new Node(element, header, null);
        if (header != null) {
            header.prev = newElement;
        }
        header = newElement;
        if (tail == null) {
            tail = newElement;
        }
        size++;
    }

    void addLast(E element) {

        Node newElement = new Node(element, null, tail);

        if (tail != null) {

            tail.next = newElement;
        }
        tail = newElement;
        if (header == null) {
            header = newElement;
        }
        size++;

    }


    @Override
    public E remove(int index) {
        Node node = node(index);
        Log.i("LinkedListImpl", " Remove element " + node.element);
        if (node.prev == null & node.next == null) {
            header = null;
            tail = null;
        } else {
            if (node.prev != null) {
//                Log.i("LinkedListImpl", "prev " + node.prev.element);
                node.prev.next = node.next;
            } else {
                header = node.next;
                header.prev = null;
            }
            if (node.next != null) {
//                Log.i("LinkedListImpl", "next " + node.next.element);
                node.next.prev = node.prev;
            } else {
                tail = node.prev;
                tail.next = null;
            }
        }
        size--;
        return (E) node.element;
    }

    @NonNull
    @Override
    public ListIterator<E> listIterator() {
        return new LinkedListIterator();
    }

    /**
     * Replaces the element at the specified position in this list with the
     * specified element (optional operation).
     */
    @Override
    public E set(int index, E element) {
        if (index > (size >> 1)) {
            Node tmp = node(index);
            Node entry = new Node(element, tmp.next, tmp.prev);
            entry.prev.next = entry;
            if (entry.next != null) {
                entry.next.prev = entry;
            } else {
                tail = entry;
            }

        } else {
            Node tmp = (index == 0 ? header : node(index));
            Node entry = new Node(element, tmp.next, tmp.prev);

            if (entry.prev != null) {
                entry.prev.next = entry;
            } else {
                header = entry;
            }
            entry.next.prev = entry;

        }

        Log.i("LinkedListImpl", "Replace element " + element.toString() + " index: " +
                index);

        return get(index);
    }

    /**
     * Inserts the specified element at the specified position in this list
     * (optional operation).  Shifts the element currently at that position
     * (if any) and any subsequent elements to the right (adds one to their
     * indices).
     */

    public void add(int index, E element) throws IndexOutOfBoundsException {
        if (size > 0) {
            if (index > (size >> 1)) {
                Log.i("LinkedListImpl", "Added element from tail " + element.toString() + " " +
                        "index: " + index);
                Node tmp = node(index);
                Node entry = new Node(element, tmp, tmp.prev);
                entry.prev.next = entry;
                entry.next.prev = entry;


            } else {
                Log.i("LinkedListImpl", "Added element from header " + element.toString() + " " +
                        "index: " + index);
                Node tmp = (index == 0 ? header : node(index));
                Node entry = new Node(element, tmp, tmp.prev);

                if (entry.prev != null) {
                    entry.prev.next = entry;
                    entry.next.prev = entry;
                } else {
                    header.prev = entry;
                    header = entry;
                }

            }
            size++;


        } else {
            addFirst(element);
        }
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
    public boolean add(E e) {
        addLast(e);
        return size > 0;
    }


    @NonNull
    @Override
    public Iterator<E> iterator() {
        return new LinkedListIterator();
    }

    @Override
    public int indexOf(Object o) {
        return 0;
    }

    @Override
    public int lastIndexOf(Object o) {
        return 0;
    }

    @NonNull
    @Override
    public ListIterator<E> listIterator(int index) {
        return null;
    }

    @NonNull
    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return null;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @NonNull
    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @NonNull
    @Override
    public <T> T[] toArray(@NonNull T[] a) {
        return null;
    }

    @Override
    public boolean remove(Object o) {

        Node entry = header;

        for (int i = 0; i < size; i++) {

            if (entry.element.equals(o)) {

                return (remove(i) != null);

            }
            entry = entry.next;
        }
        return false;
    }

    @Override
    public boolean containsAll(@NonNull Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(@NonNull Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean addAll(int index, @NonNull Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean removeAll(@NonNull Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(@NonNull Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }


    private class LinkedListIterator implements ListIterator<E> {

        private Node nextNode;
        private int index;

        private LinkedListIterator() {
            nextNode = header;
            index = 0;
        }

        @Override
        public boolean hasNext() {
            return nextNode != null;
        }

        public E next() {
            if (!hasNext()) throw new NoSuchElementException();
            E res = (E) nextNode.element;
            nextNode = nextNode.next;
            index++;
            return res;
        }

        @Override
        public boolean hasPrevious() {
            return (nextNode != null && (nextNode.prev != null));
        }

        @Override
        public E previous() {
            return (E) nextNode.prev.element;
        }

        @Override
        public int nextIndex() {
            return index + 1;
        }

        @Override
        public int previousIndex() {
            return index - 1;
        }

        @Override
        public void remove() {
            if (size > 0) {
                LinkedListImpl.this.remove(size - 1);
            }
        }

        @Override
        public void set(E e) {
            LinkedListImpl.this.add(index, e);
        }

        @Override
        public void add(E e) {
            addLast(e);
        }
    }

    private class Node<E> {

        private E element;
        private Node next;
        private Node prev;

        Node(E element, Node next, Node prev) {
            this.element = element;
            this.next = next;
            this.prev = prev;
        }
    }
}
