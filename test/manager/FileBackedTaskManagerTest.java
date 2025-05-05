package manager;

import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class FileBackedTaskManagerTest {

    @Test
    void savingAndUploadingEmptyFile() throws IOException {
        File newFile = File.createTempFile("TestFile", ".csv");
        FileBackedTaskManager taskManager = new FileBackedTaskManager(newFile);

        taskManager.save();

        FileBackedTaskManager newTaskManager = FileBackedTaskManager.loadFromFile(newFile);
        assertTrue(newTaskManager.getTasks().isEmpty(), "Список задач должен быть пустым");
        assertTrue(newTaskManager.getEpics().isEmpty(), "Список эпиков должен быть пустым");
        assertTrue(newTaskManager.getSubtasks().isEmpty(), "Список подзадач должен быть пустым");
    }

    @Test
    void savingAndUploadingMultipleTasks() throws IOException {
        File newFile = File.createTempFile("TestFile", ".csv");
        FileBackedTaskManager taskManager1 = new FileBackedTaskManager(newFile);

        Task task = new Task(1, "Задача", "Описание задачи", Status.NEW);
        taskManager1.addTask(task);
        Epic epic = new Epic(2, "Эпик", "Описание эпика", Status.NEW);
        taskManager1.addEpic(epic);
        Subtask subtask = new Subtask(3, "Подзадача", "Описание задачи", Status.NEW, 2);
        taskManager1.addSubtask(subtask);
        taskManager1.save();

        FileBackedTaskManager taskManager2 = FileBackedTaskManager.loadFromFile(newFile);

        assertEquals(1, taskManager2.getTasks().size(), "Задача должны быть одна");
        Task newTask = taskManager2.getTaskById(1);
        assertEquals("Задача", newTask.getName(), "Название задача должно совпадать");
        assertEquals(Status.NEW, newTask.getStatus(), "Статус должен совпадать");

        assertEquals(1, taskManager2.getEpics().size(), "Эпик должен быть один");
        Epic newEpic = taskManager2.getEpics().getFirst();
        assertEquals("Эпик", newEpic.getName(), "Название эпика должно совпадать");
        assertEquals(Status.NEW, newEpic.getStatus(), "Статус должен совпадать");

        assertEquals(1, taskManager2.getSubtasks().size(), "Подзадача должна быть одна");
        Subtask newSubtask = taskManager2.getSubtasks().getFirst();
        assertEquals("Подзадача", newSubtask.getName(), "Название подзадачи должно совпадать");
        assertEquals(Status.NEW, newSubtask.getStatus(), "Статус должен совпадать");
    }
}