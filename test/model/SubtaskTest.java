package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SubtaskTest {

    @Test
    void newSubTaskTest() {
        Subtask subtask = new Subtask("subtask", "Description", Status.NEW, 1);
        assertEquals("subtask", subtask.getName(), "Name не соответствует");
        assertEquals("Description", subtask.getDescription(), "Description не соответствует");
        assertEquals(Status.NEW, subtask.getStatus(), "Status не соответствует");
    }
}