package org.example;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.PriorityQueue;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Seleccione implementación:");
        System.out.println("  1) VectorHeap");
        System.out.println("  2) Java Collections Framework");
        int opcion = sc.nextInt();
        sc.nextLine();

        if (opcion == 1) {
            runVectorHeap();
        } else if (opcion == 2) {
            runJCF();
        } else {
            System.out.println("Opción inválida.");
        }
    }

    // Uso de tu VectorHeap
    private static void runVectorHeap() {
        VectorHeap<Paciente> heap = new VectorHeap<>();
        if (!cargarVector(heap)) return;

        System.out.println("\n--- Atendiendo con VectorHeap ---");
        while (!heap.isEmpty()) {
            System.out.println(heap.remove());
        }
    }

    // Uso de la PriorityQueue de JCF
    private static void runJCF() {
        PriorityQueue<Paciente> cola = new PriorityQueue<>();
        if (!cargarJCF(cola)) return;

        System.out.println("\n--- Atendiendo con JCF PriorityQueue ---");
        Paciente p;
        while ((p = cola.poll()) != null) {
            System.out.println(p);
        }
    }

    // Carga pacientes en VectorHeap
    private static boolean cargarVector(VectorHeap<Paciente> heap) {
        try (BufferedReader br = new BufferedReader(new FileReader("pacientes.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] parts = linea.split(",");
                heap.add(new Paciente(parts[0].trim(), parts[1].trim(), parts[2].trim().charAt(0)));
            }
            return true;
        } catch (IOException e) {
            System.err.println("Error al leer pacientes.txt: " + e.getMessage());
            return false;
        }
    }

    // Carga pacientes en PriorityQueue de JCF
    private static boolean cargarJCF(PriorityQueue<Paciente> cola) {
        try (BufferedReader br = new BufferedReader(new FileReader("pacientes.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] parts = linea.split(",");
                cola.offer(new Paciente(parts[0].trim(), parts[1].trim(), parts[2].trim().charAt(0)));
            }
            return true;
        } catch (IOException e) {
            System.err.println("Error al leer pacientes.txt: " + e.getMessage());
            return false;
        }
    }
}