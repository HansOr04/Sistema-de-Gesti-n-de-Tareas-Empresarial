package com.grupo4;

public class Task {
    private final String id;
    private String title;
    private String description;
    private String priority;
    private String dueDate;
    private String status;

    public Task(String id, String title, String description, String priority, String dueDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.dueDate = dueDate;
        this.status = "pendiente";
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getPriority() { return priority; }
    public String getDueDate() { return dueDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return "ID: " + id + " | Título: " + title + " | Prioridad: " + priority +
                " | Vence: " + dueDate + " | Estado: " + status +
                "\nDescripción: " + description;
    }
}