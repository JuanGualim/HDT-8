package org.example;

import java.util.Vector;

public class VectorHeap<E extends Comparable<E>> implements PriorityQueue<E> {
    protected Vector<E> data;

    public VectorHeap() {
        data = new Vector<E>();
    }

    public VectorHeap(Vector<E> v) {
        data = new Vector<E>(v);
        for (int i = data.size() / 2 - 1; i >= 0; i--) {
            percolateDown(i);
        }
    }

    protected int parent(int i) { return (i - 1) / 2; }
    protected int left(int i) { return 2 * i + 1; }
    protected int right(int i) { return 2 * i + 2; }

    protected void percolateUp(int leaf) {
        int parent = parent(leaf);
        E value = data.get(leaf);
        while (leaf > 0 && value.compareTo(data.get(parent)) < 0) {
            data.set(leaf, data.get(parent));
            leaf = parent;
            parent = parent(leaf);
        }
        data.set(leaf, value);
    }

    protected void percolateDown(int root) {
        int child = left(root);
        E value = data.get(root);
        while (child < data.size()) {
            int right = right(root);
            if (right < data.size() && data.get(right).compareTo(data.get(child)) < 0)
                child = right;
            if (data.get(child).compareTo(value) < 0) {
                data.set(root, data.get(child));
                root = child;
                child = left(root);
            } else break;
        }
        data.set(root, value);
    }

    public void add(E value) {
        data.add(value);
        percolateUp(data.size() - 1);
    }

    public E remove() {
        if (data.size() == 0) return null;
        E minVal = data.get(0);
        E lastVal = data.remove(data.size() - 1);
        if (data.size() > 0) {
            data.set(0, lastVal);
            percolateDown(0);
        }
        return minVal;
    }

    public boolean isEmpty() {
        return data.isEmpty();
    }
}