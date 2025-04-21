package org.example;

public class Paciente implements Comparable<Paciente> {
    private String nombre;
    private String sintoma;
    private char prioridad; // A es la más urgente, E la menos urgente

    public Paciente(String nombre, String sintoma, char prioridad) {
        this.nombre = nombre;
        this.sintoma = sintoma;
        this.prioridad = prioridad;
    }

    public String toString() {
        return nombre + ", " + sintoma + ", " + prioridad;
    }

    @Override
    public int compareTo(Paciente otro) {
        return Character.compare(this.prioridad, otro.prioridad);
    }
}