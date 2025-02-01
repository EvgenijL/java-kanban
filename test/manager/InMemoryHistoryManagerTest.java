package manager;

import model.Status;
import model.Task;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {
    @Test
    void getHistoryTest() {

        InMemoryHistoryManager historyManager = new InMemoryHistoryManager();

        Task task1 = new Task("Task1", "Description1", Status.NEW);
        Task task2 = new Task("Task2", "Description2", Status.NEW);
        Task task3 = new Task("Task3", "Description3", Status.NEW);
        Task task4 = new Task("Task4", "Description4", Status.NEW);
        Task task5 = new Task("Task5", "Description5", Status.NEW);
        Task task6 = new Task("Task6", "Description6", Status.NEW);
        Task task7 = new Task("Task7", "Description7", Status.NEW);
        Task task8 = new Task("Task8", "Description8", Status.NEW);
        Task task9 = new Task("Task9", "Description9", Status.NEW);
        Task task10 = new Task("Task10", "Description10", Status.NEW);
        Task task11 = new Task("Task11", "Description11", Status.NEW);

        historyManager.addTask(task1);
        historyManager.addTask(task2);
        historyManager.addTask(task3);
        historyManager.addTask(task4);
        historyManager.addTask(task5);
        historyManager.addTask(task6);
        historyManager.addTask(task7);
        historyManager.addTask(task8);
        historyManager.addTask(task9);
        historyManager.addTask(task10);
        historyManager.addTask(task11);

        final List<Task> history = historyManager.getHistory();
        final List<Task> newHistory = new ArrayList<>();
        newHistory.add(task2);
        newHistory.add(task3);
        newHistory.add(task4);
        newHistory.add(task5);
        newHistory.add(task6);
        newHistory.add(task7);
        newHistory.add(task8);
        newHistory.add(task9);
        newHistory.add(task10);
        newHistory.add(task11);
        assertNotNull(history, "История не пустая.");
        assertArrayEquals(newHistory.toArray(), history.toArray());
    }

}