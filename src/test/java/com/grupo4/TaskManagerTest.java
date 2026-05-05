package com.grupo4;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskManagerTest {

    private TaskManager manager;

    @BeforeEach
    void setUp() {
        manager = new TaskManager();
    }

    // --- Crear tarea ---

    @Test
    void testCrearTareaExitosamente() {
        Task task = manager.createTask("AB123", "Diseñar login", "Crear pantalla de login", "ALTA", "2025-05-15");
        assertNotNull(task);
        assertEquals("AB123", task.getId());
        assertEquals("Diseñar login", task.getTitle());
        assertEquals("ALTA", task.getPriority());
        assertEquals("pendiente", task.getStatus());
    }

    @Test
    void testCrearTareaConIdInvalidoLanzaExcepcion() {
        // Solo números, sin letras
        assertThrows(IllegalArgumentException.class, () ->
                manager.createTask("12345", "Tarea", "Desc", "MEDIA", "2025-06-01")
        );
    }

    @Test
    void testCrearTareaConIdFormatoIncorrectoLanzaExcepcion() {
        // 3 letras + 2 dígitos — no cumple el formato
        assertThrows(IllegalArgumentException.class, () ->
                manager.createTask("ABC12", "Tarea", "Desc", "BAJA", "2025-06-01")
        );
    }

    @Test
    void testCrearTareaConIdDuplicadoLanzaExcepcion() {
        manager.createTask("AB123", "Tarea 1", "Desc 1", "MEDIA", "2025-06-01");
        assertThrows(IllegalArgumentException.class, () ->
                manager.createTask("AB123", "Tarea 2", "Desc 2", "BAJA", "2025-06-01")
        );
    }

    @Test
    void testCrearTareaConTituloVacioLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () ->
                manager.createTask("CD456", "", "Sin título", "MEDIA", "2025-05-20")
        );
    }

    @Test
    void testCrearTareaConDescripcionVaciaLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () ->
                manager.createTask("CD456", "Título válido", "", "MEDIA", "2025-05-20")
        );
    }

    @Test
    void testCrearTareaConFechaVaciaLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () ->
                manager.createTask("CD456", "Título válido", "Desc válida", "MEDIA", "")
        );
    }

    @Test
    void testCrearTareaConPrioridadInvalidaLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () ->
                manager.createTask("EF789", "Revisar código", "Revisión", "URGENTE", "2025-05-20")
        );
    }

    @Test
    void testIdSeGuardaEnMayusculas() {
        Task task = manager.createTask("ab123", "Tarea", "Desc", "BAJA", "2025-06-01");
        assertEquals("AB123", task.getId());
    }

    // --- Buscar tarea por ID ---

    @Test
    void testBuscarTareaPorIdExacto() {
        manager.createTask("GH001", "Reunión", "Reunión de equipo", "MEDIA", "2025-05-10");
        List<Task> results = manager.findById("GH001");
        assertEquals(1, results.size());
        assertEquals("GH001", results.get(0).getId());
    }

    @Test
    void testBuscarTareaPorIdParcial() {
        manager.createTask("AB001", "Tarea A", "Desc A", "ALTA", "2025-06-01");
        manager.createTask("AB002", "Tarea B", "Desc B", "BAJA", "2025-06-02");
        manager.createTask("CD001", "Tarea C", "Desc C", "MEDIA", "2025-06-03");
        List<Task> results = manager.findById("AB");
        assertEquals(2, results.size()); // Solo las que contienen "AB"
    }

    @Test
    void testBuscarTareaInexistenteLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () ->
                manager.findById("ZZ999")
        );
    }

    @Test
    void testBuscarTareaConIdVacioLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () ->
                manager.findById("")
        );
    }
}
