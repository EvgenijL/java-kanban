package manager;

import model.Task;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ManagersTest {

    @Test
    public void shouldReturnNonNullTaskManager() {
        TaskManager taskManager = Managers.getDefaultTaskManager();

        assertNotNull(taskManager, "Экземпляр TaskManager не должен возвращать null");
    }

    @Test
    public void shouldReturnNewTaskManagerInstanceEachTime() {
        TaskManager taskManager1 = Managers.getDefaultTaskManager();
        TaskManager taskManager2 = Managers.getDefaultTaskManager();

        assertNotSame(taskManager1, taskManager2, "Каждый вызов getDefaultTaskManager должен возвращать новый экземпляр HistoryManager");
    }

    @Test
    public void taskManagerShouldBeFunctional() {
        TaskManager taskManager = Managers.getDefaultTaskManager();
        Task task = new Task("Task", "Description");
        taskManager.addTask(task);

        assertEquals(1, taskManager.getTasks().size(), "Диспетчер задач должен корректно хранить задачи");
        assertEquals(task, taskManager.getTasks().get(0), "Диспетчер задач должен вернуть правильную задачу");
    }

    @Test
    public void shouldReturnNonNullHistoryManager() {
        HistoryManager historyManager = Managers.getDefaultHistoryManager();

        assertNotNull(historyManager, "Экземпляр TaskManager не должен возвращать null");
    }

    @Test
    public void shouldReturnNewHistoryManagerInstanceEachTime() {
        HistoryManager historyManager1 = Managers.getDefaultHistoryManager();
        HistoryManager historyManager2 = Managers.getDefaultHistoryManager();

        assertNotSame(historyManager1, historyManager2, "Каждый вызов getDefaultHistoryManager должен возвращать новый экземпляр HistoryManager");
    }

    @Test
    public void historyManagerShouldBeFunctional() {
        HistoryManager historyManager = Managers.getDefaultHistoryManager();
        Task task = new Task("Task", "Description");
        task.setId(1);
        historyManager.addTask(task);
        var history = historyManager.getHistory();

        assertNotNull(history, "history не должна возвращать null");
        assertEquals(1, history.size(), "HistoryManager должен корректно хранить задачи");
        assertEquals(task, history.get(0), "HistoryManager должен возвращать правильную задачу из истории");
    }
}