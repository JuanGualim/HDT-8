package org.example;
import static org.junit.Assert.*;
import java.util.Vector;
import org.junit.Before;
import org.junit.Test;

public class VectorHeapTest {

    private VectorHeap<Paciente> heap;

    @Before
    public void setUp() {
        heap = new VectorHeap<>();
    }

    @Test
    public void testAddAndRemoveOrder() {
        heap.add(new Paciente("Juan Perez", "fractura de pierna", 'C'));
        heap.add(new Paciente("Maria Ramirez", "apendicitis", 'A'));
        heap.add(new Paciente("Carmen Sarmientos", "dolores de parto", 'B'));

        // La primera eliminación debe ser la prioridad A
        assertEquals("Maria Ramirez, apendicitis, A", heap.remove().toString());
        // Luego la prioridad B
        assertEquals("Carmen Sarmientos, dolores de parto, B", heap.remove().toString());
        // Luego la prioridad C
        assertEquals("Juan Perez, fractura de pierna, C", heap.remove().toString());
        // Y finalmente vacía
        assertTrue(heap.isEmpty());
    }

    @Test
    public void testRemoveOnEmptyReturnsNull() {
        assertNull("Remove en heap vacío debe devolver null", heap.remove());
        assertTrue("Heap debe seguir vacío", heap.isEmpty());
    }

    @Test
    public void testHeapifyConstructor() {

        Vector<Paciente> list = new Vector<>();
        list.add(new Paciente("P1", "S1", 'C'));
        list.add(new Paciente("P2", "S2", 'A'));
        list.add(new Paciente("P3", "S3", 'B'));

        VectorHeap<Paciente> heap2 = new VectorHeap<>(list);

        assertEquals("P2, S2, A", heap2.remove().toString());
        assertEquals("P3, S3, B", heap2.remove().toString());
        assertEquals("P1, S1, C", heap2.remove().toString());
    }
}