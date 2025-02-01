package manager;

import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {

    private TaskManager taskManager;

    @BeforeEach
    public void NewTaskManager() {
        taskManager = new InMemoryTaskManager();
    }

    @Test
    public void shouldAddAndGetTaskById() {
        Task task = new Task("Task", "Description");
        taskManager.addTask(task);
        Task getTask = taskManager.getTaskById(task.getId());

        assertNotNull(getTask, "Задача не должна быть null");
        assertEquals(task, getTask, "Извлеченная задача должна соответствовать добавленной задаче");
    }

    @Test
    public void shouldAddAndRemoveEpicById() {
        Epic epic = new Epic("Epic", "Description");
        taskManager.addEpic(epic);
        Task removeEpic = taskManager.getEpicById(epic.getId());

        assertNotNull(removeEpic, "Значение эпика не должно быть null");
        assertEquals(epic, removeEpic, "Извлеченный эпик должен соответствовать добавленному эпику");
    }

    @Test
    public void shouldAddAndGetSubtaskById() {
        Epic epic = new Epic("Epic", "Description");
        taskManager.addEpic(epic);
        Subtask subtask = new Subtask("Subtask", "Subtask Description", Status.NEW, epic.getId());
        taskManager.addSubtask(subtask);
        Subtask getSubTask = taskManager.getSubtaskById(subtask.getId());

        assertNotNull(getSubTask, "Subtask не должен быть null");
        assertEquals(subtask, getSubTask, "Подзадачи должны быть равны");
    }

    @Test
    public void shouldAddSubtaskAndUpdateEpicStatus() {
        Epic epic = new Epic("Epic", "Description");
        taskManager.addEpic(epic);
        Subtask subtask = new Subtask("Subtask", "Subtask Description", Status.NEW, epic.getId());
        taskManager.addSubtask(subtask);
        List<Subtask> subtasks = taskManager.getSubtaskByEpic(epic.getId());

        assertNotNull(subtasks, "Список подзадач не должен быть пустым");
        assertEquals(1, subtasks.size(), "Epic должен содержать одну подзадачу");
        assertEquals(subtask, subtasks.get(0), "Подзадача должна соответствовать добавленной подзадаче");
    }

    @Test
    public void shouldNotChangeTaskDataWhenAddedToManagers() {
        Task task = new Task(1,"Task", "Description");
        taskManager.addTask(task);
        Task newTask = taskManager.getTaskById(task.getId());

        assertEquals(task.getName(), newTask.getName(), "Название не должно меняться");
        assertEquals(task.getDescription(), newTask.getDescription(), "Описание не должно изменяться");
        assertEquals(task.getStatus(), newTask.getStatus(), "Статус не должен меняться");
        assertEquals(task.getId(), newTask.getId(), "ID не должен меняться");
    }

    @Test
    public void shouldNotConflictBetweenGivenIdAndGeneratedId() {
        Task taskGivenId = new Task(1, "Task1", "Description1");
        taskManager.addTask(taskGivenId);
        Task taskGeneratedId = new Task("Task2", "Description2");
        taskManager.addTask(taskGeneratedId);
        Task newTaskGivenId = taskManager.getTaskById(taskGivenId.getId());
        Task newTaskGeneratedId = taskManager.getTaskById(taskGeneratedId.getId());

        assertNotNull(newTaskGivenId, "Задача с указанным ID должна быть доступна");
        assertNotNull(newTaskGeneratedId, "Задача со сгенерированным ID должна быть доступна");
        assertNotEquals(newTaskGivenId, newTaskGeneratedId, "Конфликт задач с заданным и сгенерированным ID");
    }

    @Test
    public void shouldShowPreviousVersionTask() {
        Task task = new Task(1,"Task", "Description");
        taskManager.addTask(task);
        taskManager.getTaskById(task.getId());
        List<Task> history = taskManager.getHistory();

        assertNotNull(history, "История не должна быть пустой");
        assertEquals(1, history.size(), "История должна содержать одну задачу");
        assertEquals(task, history.get(0), "Задача в истории должна соответствовать добавленной задаче");
        assertEquals("Task", history.get(0).getName(), "Название задачи в истории должно совпадать с исходным названием задачи");
        assertEquals("Description", history.get(0).getDescription(), "Описание задачи в истории должно соответствовать исходному описанию");
    }

}