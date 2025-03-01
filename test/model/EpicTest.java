package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {

    @Test
    void EpicSetIdTest(){
        Epic epic = new Epic("Epic", "Description");
        epic.setId(111);
        assertEquals(111, epic.getId(), "setId работает не корректно");
    }

    @Test
    void checkSetDescription() {
        Task task1 = new Task("Задача таска 1", "Решить задачу 1", Status.NEW);
        Task task2 = new Task("Задача таска 2", "Решить задачу 2", Status.NEW);
        task2.setDescription(task1.getDescription());

        assertEquals(task1.getDescription(), task2.getDescription(), "Сеттер работает не корректно");
    }
}