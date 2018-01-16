package com.dbbest.linkedlistimpl;

import android.util.Log;

import java.util.Iterator;
import java.util.NoSuchElementException;

class LinkedListImpl<E> {

    private Node header;
    private Node tail;
    private int size;


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

    void add(int index, E element) throws IndexOutOfBoundsException {
        if (size > 0) {
            try {
                if (index > (size >> 1)) {
                    Node tmp = getNode(index);
                    Node entry = new Node(element, tmp.next, tmp);
                    entry.prev.next = entry;
                    if (entry.next != null) {
                        entry.next.prev = entry;
                    }

                } else {
                    Node tmp = (index == 0 ? header : getNode(index));
                    Node entry = new Node(element, tmp, tmp.prev);

                    if (entry.prev != null) {
                        entry.prev.next = entry;
                    } else {
                        addFirst((E) entry.element);
                    }
                    if (entry.next != null) {
                        entry.next.prev = entry;
                    }
                }
                size++;
                Log.i("LinkedListImpl", "Added element " + element.toString() + " index: " + index);
            } catch (IndexOutOfBoundsException e) {
                Log.e("LinkedListImpl", "Error index ");
            }
        } else {
            addFirst(element);
        }
    }

    void remove(int index) {

        Node node = getNode(index);
        Log.i("LinkedListImpl", " Remove element " + node.element);

        if (node.prev == null & node.next == null) {
            header = null;
            tail = null;

        } else {
            if (node.prev != null) {
                Log.i("LinkedListImpl", "prev " + node.prev.element);
                node.prev.next = node.next;
            } else {
                header = node.next;
                header.prev = null;


            }
            if (node.next != null) {
                Log.i("LinkedListImpl", "next " + node.next.element);
                node.next.prev = node.prev;
            } else {
                tail = node.prev;
                tail.next = null;
            }
        }
        size--;

    }

    E get(int index) {
        try {
            return (E) getNode(index).element;
        } catch (NullPointerException e) {
            Log.e("LinkedListImpl", "Error while get element by index ");
        }
        return null;
    }

    Node getNode(int index) throws IndexOutOfBoundsException {

        try {
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
            return entry;
        } catch (IndexOutOfBoundsException e) {
            Log.e("LinkedListImpl", "Error index ");
        }
        return null;

    }

    Iterator<E> iterator() {
        return new LinkedListIterator();
    }

    private class LinkedListIterator implements Iterator<E> {
        private Node nextNode;

        private LinkedListIterator() {
            nextNode = header;
        }

        @Override
        public boolean hasNext() {
            return nextNode != null;
        }

        public E next() {
            if (!hasNext()) throw new NoSuchElementException();
            E res = (E) nextNode.element;
            nextNode = nextNode.next;
            return res;
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
