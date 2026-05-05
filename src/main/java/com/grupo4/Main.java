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
                case 0 -> System.out.println("Saliendo del sistema. ¡Hasta luego!");
                default -> System.out.println("Opción no válida. Intente de nuevo.");
            }

        } while (opcion != 0);

        scanner.close();
    }

    private static void crearTarea(TaskManager manager, Scanner scanner) {
        System.out.println("\n--- CREAR TAREA ---");

        // ID
        System.out.print("ID (2 letras + 3 dígitos, ej. AB123): ");
        String id = scanner.nextLine().trim();
        if (!id.matches("[A-Za-z]{2}\\d{3}")) {
            System.out.println("Error: ID inválido. Formato requerido: 2 letras y 3 dígitos (ej. AB123).");
            return;
        }

        // Título
        System.out.print("Título: ");
        String title = scanner.nextLine().trim();
        if (title.isBlank()) {
            System.out.println("Error: El título es obligatorio.");
            return;
        }

        // Descripción
        System.out.print("Descripción: ");
        String description = scanner.nextLine().trim();
        if (description.isBlank()) {
            System.out.println("Error: La descripción es obligatoria.");
            return;
        }

        // Prioridad
        System.out.print("Prioridad (ALTA / MEDIA / BAJA): ");
        String priority = scanner.nextLine().trim();
        if (priority.isBlank()) {
            System.out.println("Error: La prioridad es obligatoria.");
            return;
        }
        if (!priority.equalsIgnoreCase("ALTA") &&
                !priority.equalsIgnoreCase("MEDIA") &&
                !priority.equalsIgnoreCase("BAJA")) {
            System.out.println("Error: Prioridad inválida. Use: ALTA, MEDIA o BAJA.");
            return;
        }

        // Fecha
        System.out.print("Fecha de vencimiento (yyyy-MM-dd): ");
        String dueDate = scanner.nextLine().trim();
        if (dueDate.isBlank()) {
            System.out.println("Error: La fecha de vencimiento es obligatoria.");
            return;
        }
        if (!dueDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
            System.out.println("Error: Fecha inválida. Formato requerido: yyyy-MM-dd (ej. 2025-12-31).");
            return;
        }

        // Crear
        try {
            Task task = manager.createTask(id, title, description, priority, dueDate);
            System.out.println("\nTarea creada exitosamente:");
            System.out.println(task);
        } catch (IllegalArgumentException e) {
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
}