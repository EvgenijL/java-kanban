package model;

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

}