package model;

import manager.TaskManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    @Test
    void TasksEqualIfTheirIdEquals() {
        Task task = new Task("Task", "Description", Status.NEW);
        task.setId(1);
        Task task2  = new Task("Task", "Description", Status.NEW);
        task2.setId(1);
        assertEquals(task.getId(), task2.getId(), "ID разные");
        assertEquals(task, task2, "Объекты разные");
    }

    @Test
    void checkSetName() {
        Task task1 = new Task("Задача таска 1", "Решить задачу", Status.NEW);
        Task task2 = new Task("Задача таска 2", "Решить задачу 2", Status.NEW);
        task2.setName("Задача таска 1");

        assertEquals(task1.getName(), task2.getName(), "Сеттер работает не корректно");
    }
}