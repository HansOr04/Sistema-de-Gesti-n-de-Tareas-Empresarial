package com.grupo4;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        TaskManager manager = new TaskManager();
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\nSISTEMA DE GESTIÓN DE TAREAS");
            System.out.println("1. Crear tarea");
            System.out.println("2. Buscar tarea por ID");
            System.out.println("3. Actualizar estado de tarea");
            System.out.println("4. Listar tareas por prioridad");
            System.out.println("5. Listar tareas próximas a vencer");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");

            String input = scanner.nextLine().trim();
            try {
                opcion = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Opción no válida. Ingrese un número.");
                opcion = -1;
                continue;
            }

            switch (opcion) {
                case 1 -> crearTarea(manager, scanner);
                case 2 -> buscarTarea(manager, scanner);
                case 3 -> actualizarEstado(manager, scanner);
                case 4 -> listarPorPrioridad(manager, scanner);
                case 5 -> listarProximasAVencer(manager);
                case 0 -> System.out.println("Saliendo del sistema. ¡Hasta luego!");
                default -> System.out.println("Opción no válida. Intente de nuevo.");
            }

        } while (opcion != 0);

        scanner.close();
    }

    // ── Helpers para leer campos con reintento ─────────────────────────

    private static String leerId(Scanner scanner) {
        while (true) {
            System.out.print("ID (2 letras + 3 dígitos, ej. AB123): ");
            String id = scanner.nextLine().trim();
            if (id.matches("[A-Za-z]{2}\\d{3}")) return id.toUpperCase();
            System.out.println("  Error: ID inválido. Formato requerido: 2 letras y 3 dígitos (ej. AB123).");
        }
    }

    private static String leerCampoObligatorio(Scanner scanner, String label) {
        while (true) {
            System.out.print(label + ": ");
            String valor = scanner.nextLine().trim();
            if (!valor.isBlank()) return valor;
            System.out.println("  Error: " + label + " es obligatorio.");
        }
    }

    private static String leerPrioridad(Scanner scanner) {
        while (true) {
            System.out.print("Prioridad (ALTA / MEDIA / BAJA): ");
            String p = scanner.nextLine().trim();
            if (p.equalsIgnoreCase("ALTA") || p.equalsIgnoreCase("MEDIA") || p.equalsIgnoreCase("BAJA"))
                return p.toUpperCase();
            System.out.println("  Error: Prioridad inválida. Use: ALTA, MEDIA o BAJA.");
        }
    }

    private static String leerFecha(Scanner scanner) {
        while (true) {
            System.out.print("Fecha de vencimiento (yyyy-MM-dd): ");
            String fecha = scanner.nextLine().trim();
            if (fecha.matches("\\d{4}-\\d{2}-\\d{2}")) return fecha;
            System.out.println("  Error: Fecha inválida. Formato requerido: yyyy-MM-dd (ej. 2025-12-31).");
        }
    }

    private static String leerEstado(Scanner scanner) {
        while (true) {
            System.out.print("Nuevo estado (pendiente / en progreso / completada): ");
            String estado = scanner.nextLine().trim().toLowerCase();
            if (estado.equals("pendiente") || estado.equals("en progreso") || estado.equals("completada"))
                return estado;
            System.out.println("  Error: Estado inválido. Use: pendiente, en progreso o completada.");
        }
    }

    // ── Funcionalidades ────────────────────────────────────────────────

    private static void crearTarea(TaskManager manager, Scanner scanner) {
        System.out.println("\n--- CREAR TAREA ---");

        String id = leerId(scanner);
        String title = leerCampoObligatorio(scanner, "Título");
        String description = leerCampoObligatorio(scanner, "Descripción");
        String priority = leerPrioridad(scanner);
        String dueDate = leerFecha(scanner);

        try {
            Task task = manager.createTask(id, title, description, priority, dueDate);
            System.out.println("\nTarea creada exitosamente:");
            System.out.println(task);
        } catch (IllegalArgumentException e) {
            // Solo puede llegar aquí si el ID ya existe
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void buscarTarea(TaskManager manager, Scanner scanner) {
        System.out.println("\n--- BUSCAR TAREA POR ID ---");

        System.out.print("Ingrese ID o parte del ID a buscar: ");
        String id = scanner.nextLine().trim();
        if (id.isBlank()) {
            System.out.println("Error: El ID de búsqueda no puede estar vacío.");
            return;
        }

        try {
            List<Task> results = manager.findById(id);
            System.out.println("\nTareas encontradas (" + results.size() + "):");
            System.out.println("─────────────────────────────────────");
            for (Task task : results) {
                System.out.println(task);
                System.out.println("─────────────────────────────────────");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void actualizarEstado(TaskManager manager, Scanner scanner) {
        System.out.println("\n--- ACTUALIZAR ESTADO DE TAREA ---");

        String id = leerId(scanner);
        String newStatus = leerEstado(scanner);

        try {
            Task task = manager.updateStatus(id, newStatus);
            System.out.println("\nEstado actualizado exitosamente:");
            System.out.println(task);
        } catch (IllegalArgumentException e) {
            // Solo puede llegar aquí si la tarea no existe o la transición es inválida
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void listarPorPrioridad(TaskManager manager, Scanner scanner) {
        System.out.println("\n--- LISTAR TAREAS POR PRIORIDAD ---");

        String priority = leerPrioridad(scanner);

        try {
            List<Task> results = manager.listByPriority(priority);
            System.out.println("\nTareas con prioridad " + priority + " (" + results.size() + ") — ordenadas por fecha descendente:");
            System.out.println("─────────────────────────────────────");
            for (Task task : results) {
                System.out.println(task);
                System.out.println("─────────────────────────────────────");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void listarProximasAVencer(TaskManager manager) {
        System.out.println("\n--- TAREAS PRÓXIMAS A VENCER (próximos 7 días) ---");

        try {
            List<Task> results = manager.listUpcoming();
            System.out.println("\nTareas próximas a vencer (" + results.size() + "):");
            System.out.println("─────────────────────────────────────");
            for (Task task : results) {
                System.out.println(task);
                System.out.println("─────────────────────────────────────");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}