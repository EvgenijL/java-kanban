package manager;

import model.Status;
import model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {

    protected TaskManager taskManager;
    protected Task task1;
    protected Task task2;
    protected Task task3;

    @BeforeEach
    void inputData() {
        taskManager = Managers.getDefaultTaskManager();
        task1 = new Task( "Task1", "Выполнить задачу 1", Status.NEW);
        task2 = new Task("Task2", "Выполнить задачу 2", Status.IN_PROGRESS);
        task3 = new Task( "Task3", "Выполнить задачу 3", Status.DONE);
    }

    @Test
    void shouldAddTask() {
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addTask(task3);

        taskManager.getTaskById(task1.getId());
        taskManager.getTaskById(task2.getId());
        taskManager.getTaskById(task3.getId());

        List<Task> history = taskManager.getHistory();

        assertEquals(3, history.size(), "В историю не добавилась задача(и)");
        assertEquals(task1, history.getFirst(), "Первой должна быть task1");
        assertEquals(task2, history.get(1), "Второй должна быть task2");
        assertEquals(task3, history.getLast(), "Последней должна быть task3");
    }

    @Test
    void shouldRemoveTask() {
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addTask(task3);

        taskManager.getTaskById(task1.getId());
        taskManager.getTaskById(task2.getId());
        taskManager.getTaskById(task3.getId());

        taskManager.removeTaskById(task1.getId());
        taskManager.removeTaskById(task2.getId());

        List<Task> history = taskManager.getHistory();

        assertEquals(task3, history.get(0), "Задачи удалены не правельно");
        assertEquals(1, history.size(), "В истории должна остаться только 1 задача");
    }

    @Test
    void shouldRemovingWorkCorrectly() {
        taskManager.addTask(task1);
        taskManager.getTaskById(task1.getId());
        taskManager.removeTaskById(10000);

        List<Task> history = taskManager.getHistory();

        assertEquals(task1, history.get(0), "Task1 не должен быть удален");
        assertEquals(1, history.size(), "Удаления не должно произойти");
    }

    @Test
    void getHistoryTest() {

        InMemoryHistoryManager historyManager = new InMemoryHistoryManager();

        Task task1 = new Task("Task1", "Description1", Status.NEW);
        Task task2 = new Task("Task2", "Description2", Status.NEW);
        Task task3 = new Task("Task3", "Description3", Status.NEW);
        Task task4 = new Task("Task4", "Description4", Status.NEW);
        Task task5 = new Task("Task5", "Description5", Status.NEW);

        historyManager.addTask(task1);
        historyManager.addTask(task2);
        historyManager.addTask(task3);
        historyManager.addTask(task4);
        historyManager.addTask(task5);

        final List<Task> history = historyManager.getHistory();

        assertNotNull(history, "История пустая.");
    }

    @Test
    void shouldHistoryBeUpdated() {
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addTask(task3);

        taskManager.getTaskById(task1.getId());
        taskManager.getTaskById(task2.getId());
        taskManager.getTaskById(task3.getId());
        taskManager.getTaskById(task2.getId());
        taskManager.getTaskById(task1.getId());

        List<Task> history = taskManager.getHistory();

        assertEquals(3, history.size(), "В истори должно быть 3 задачи");
        assertEquals(task3, history.getFirst(), "Первой должна быть task3");
        assertEquals(task2, history.get(1), "Второй должна быть task2");
        assertEquals(task1, history.getLast(), "Последней должна быть task1");
    }
}