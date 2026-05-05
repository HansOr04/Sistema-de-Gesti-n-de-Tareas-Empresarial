package com.grupo4;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        TaskManager manager = new TaskManager();
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {

            System.out.println("SISTEMA DE GESTIÓN DE TAREAS");
            System.out.println("1. Crear tarea");
            System.out.println("2. Buscar tarea por ID");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");

            opcion = scanner.nextInt();
            scanner.nextLine();

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
        try {
            System.out.print("ID (2 letras + 3 dígitos, ej. AB123): ");
            String id = scanner.nextLine().trim();

            System.out.print("Título: ");
            String title = scanner.nextLine().trim();

            System.out.print("Descripción: ");
            String description = scanner.nextLine().trim();

            System.out.print("Prioridad (ALTA / MEDIA / BAJA): ");
            String priority = scanner.nextLine().trim();

            System.out.print("Fecha de vencimiento (yyyy-MM-dd): ");
            String dueDate = scanner.nextLine().trim();

            Task task = manager.createTask(id, title, description, priority, dueDate);
            System.out.println("\nTarea creada exitosamente:");
            System.out.println(task);

        } catch (IllegalArgumentException e) {
            System.out.println("\nError: " + e.getMessage());
        }
    }

    private static void buscarTarea(TaskManager manager, Scanner scanner) {
        System.out.println("\n--- BUSCAR TAREA POR ID ---");
        try {
            System.out.print("Ingrese ID o parte del ID a buscar: ");
            String id = scanner.nextLine().trim();

            List<Task> results = manager.findById(id);
            System.out.println("\nTareas encontradas (" + results.size() + "):");
            System.out.println("─────────────────────────────────────");
            for (Task task : results) {
                System.out.println(task);
                System.out.println("─────────────────────────────────────");
            }

        } catch (IllegalArgumentException e) {
            System.out.println("\nError: " + e.getMessage());
        }
    }
}
