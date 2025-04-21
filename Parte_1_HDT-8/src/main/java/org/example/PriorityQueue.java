package org.example;

/**
 * Interfaz mínima para una cola con prioridad basada en Comparable.
 */
public interface PriorityQueue<E extends Comparable<E>> {
    /**
     * Inserta un elemento en la cola.
     * @param value el elemento a encolar
     */
    void add(E value);

    /**
     * Remueve y devuelve el elemento de mayor prioridad (el "menor" según compareTo).
     * @return el elemento removido, o null si la cola está vacía
     */
    E remove();

    /**
     * Indica si la cola está vacía.
     * @return true si no hay elementos
     */
    boolean isEmpty();
}