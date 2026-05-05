package com.grupo4;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class TaskManagerTest {

    private TaskManager manager;

    @BeforeEach
    void setUp() {
        manager = new TaskManager();
    }

    // ── SPRINT 1: Crear tarea ──────────────────────────────────────────

    @Test
    void testCrearTareaExitosamente() {
        Task task = manager.createTask("AB123", "Diseñar login", "Crear pantalla", "ALTA", "2025-05-15");
        assertNotNull(task);
        assertEquals("AB123", task.getId());
        assertEquals("ALTA", task.getPriority());
        assertEquals("pendiente", task.getStatus());
    }

    @Test
    void testCrearTareaConIdInvalidoLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () ->
                manager.createTask("12345", "Tarea", "Desc", "MEDIA", "2025-06-01")
        );
    }

    @Test
    void testCrearTareaConIdFormatoIncorrectoLanzaExcepcion() {
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
                manager.createTask("CD456", "Título", "", "MEDIA", "2025-05-20")
        );
    }

    @Test
    void testCrearTareaConPrioridadInvalidaLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () ->
                manager.createTask("EF789", "Tarea", "Desc", "URGENTE", "2025-05-20")
        );
    }

    @Test
    void testCrearTareaConFechaInvalidaLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () ->
                manager.createTask("EF789", "Tarea", "Desc", "ALTA", "15-05-2025")
        );
    }

    @Test
    void testCrearTareaConFechaVaciaLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () ->
                manager.createTask("EF789", "Tarea", "Desc", "ALTA", "")
        );
    }

    @Test
    void testIdSeGuardaEnMayusculas() {
        Task task = manager.createTask("ab123", "Tarea", "Desc", "BAJA", "2025-06-01");
        assertEquals("AB123", task.getId());
    }

    // ── SPRINT 1: Buscar tarea ─────────────────────────────────────────

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
        assertEquals(2, results.size());
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

    // ── SPRINT 2: Actualizar estado ────────────────────────────────────

    @Test
    void testActualizarEstadoDePendienteAEnProgreso() {
        manager.createTask("AB123", "Tarea", "Desc", "ALTA", "2025-06-01");
        Task task = manager.updateStatus("AB123", "en progreso");
        assertEquals("en progreso", task.getStatus());
    }

    @Test
    void testActualizarEstadoDeEnProgresoACompletada() {
        manager.createTask("AB123", "Tarea", "Desc", "ALTA", "2025-06-01");
        manager.updateStatus("AB123", "en progreso");
        Task task = manager.updateStatus("AB123", "completada");
        assertEquals("completada", task.getStatus());
    }

    @Test
    void testNoSePuedeRetrocederEstadoLanzaExcepcion() {
        manager.createTask("AB123", "Tarea", "Desc", "ALTA", "2025-06-01");
        manager.updateStatus("AB123", "en progreso");
        assertThrows(IllegalArgumentException.class, () ->
                manager.updateStatus("AB123", "pendiente")
        );
    }

    @Test
    void testNoSePuedeSaltarEstadoLanzaExcepcion() {
        manager.createTask("AB123", "Tarea", "Desc", "ALTA", "2025-06-01");
        assertThrows(IllegalArgumentException.class, () ->
                manager.updateStatus("AB123", "completada")
        );
    }

    @Test
    void testActualizarEstadoTareaInexistenteLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () ->
                manager.updateStatus("ZZ999", "en progreso")
        );
    }

    // ── SPRINT 2: Listar por prioridad ─────────────────────────────────

    @Test
    void testListarTareasPorPrioridadAlta() {
        manager.createTask("AB001", "Tarea 1", "Desc", "ALTA", "2026-06-01");
        manager.createTask("AB002", "Tarea 2", "Desc", "ALTA", "2026-06-10");
        manager.createTask("AB003", "Tarea 3", "Desc", "MEDIA", "2026-06-05");
        List<Task> results = manager.listByPriority("ALTA");
        assertEquals(2, results.size());
        assertTrue(results.stream().allMatch(t -> t.getPriority().equals("ALTA")));
    }

    @Test
    void testListarPorPrioridadOrdenDescendentePorFecha() {
        manager.createTask("AB001", "Tarea 1", "Desc", "ALTA", "2026-06-01");
        manager.createTask("AB002", "Tarea 2", "Desc", "ALTA", "2026-06-10");
        List<Task> results = manager.listByPriority("ALTA");
        assertTrue(results.get(0).getDueDate().compareTo(results.get(1).getDueDate()) > 0);
    }

    @Test
    void testListarPorPrioridadSinResultadosLanzaExcepcion() {
        manager.createTask("AB001", "Tarea", "Desc", "MEDIA", "2026-06-01");
        assertThrows(IllegalArgumentException.class, () ->
                manager.listByPriority("ALTA")
        );
    }

    @Test
    void testListarPorPrioridadInvalidaLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () ->
                manager.listByPriority("URGENTE")
        );
    }

    // ── SPRINT 2: Próximas a vencer ────────────────────────────────────

    @Test
    void testListarTareasProximasAVencer() {
        String hoy = LocalDate.now().toString();
        String en5dias = LocalDate.now().plusDays(5).toString();
        String en10dias = LocalDate.now().plusDays(10).toString();
        manager.createTask("AB001", "Tarea hoy", "Desc", "ALTA", hoy);
        manager.createTask("AB002", "Tarea 5 días", "Desc", "MEDIA", en5dias);
        manager.createTask("AB003", "Tarea 10 días", "Desc", "BAJA", en10dias);
        List<Task> results = manager.listUpcoming();
        assertEquals(2, results.size()); // hoy y en 5 días, no en 10
    }

    @Test
    void testNoHayTareasProximasAVencerLanzaExcepcion() {
        manager.createTask("AB001", "Tarea lejana", "Desc", "BAJA",
                LocalDate.now().plusDays(30).toString());
        assertThrows(IllegalArgumentException.class, () ->
                manager.listUpcoming()
        );
    }

    @Test
    void testTareaVencidaNoAparece() {
        manager.createTask("AB001", "Tarea vencida", "Desc", "ALTA",
                LocalDate.now().minusDays(1).toString());
        assertThrows(IllegalArgumentException.class, () ->
                manager.listUpcoming()
        );
    }
}