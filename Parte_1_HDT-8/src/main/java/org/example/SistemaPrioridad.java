package org.example;

import java.util.PriorityQueue;

public class SistemaPrioridad {
    public static void main(String[] args) {
        PriorityQueue<Paciente> cola = new PriorityQueue<>();

        cola.add(new Paciente("Juan Perez", "fractura de pierna", 'C'));
        cola.add(new Paciente("Maria Ramirez", "apendicitis", 'A'));
        cola.add(new Paciente("Lorenzo Toledo", "chikunguya", 'E'));
        cola.add(new Paciente("Carmen Sarmientos", "dolores de parto", 'B'));

        while (!cola.isEmpty()) {
            System.out.println(cola.poll());
        }
    }
}