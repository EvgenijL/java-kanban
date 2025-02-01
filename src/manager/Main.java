package manager;

import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;

public class Main {

    public static void main(String[] args) {
        System.out.println("Поехали!");
        InMemoryTaskManager taskManager = new InMemoryTaskManager();

        Task task1 = new Task("Задача1", "Выполнить задание", Status.DONE);
        taskManager.addTask(task1);
        taskManager.getTaskById(1);

        Task task2 = new Task("Задача2", "Сдатьзадание", Status.IN_PROGRESS);
        taskManager.addTask(task2);
        taskManager.getTaskById(2);

        Epic epic1 = new Epic("Новая Эпик задача", "");
        taskManager.addEpic(epic1);
        taskManager.getEpicById(3);

        Subtask subtask = new Subtask("Новая подзадача для эпика", "Добавить подзадачу в эпик", Status.DONE, 3);
        taskManager.addSubtask(subtask);

        Subtask subtask2 = new Subtask("Еще одна подзадача для эпика", "Добавить ЕЩЕ одну подзадачу в эпик", Status.DONE, 3);
        taskManager.addSubtask(subtask2);

        Epic epic2 = new Epic("эпик 2", "");
        taskManager.addEpic(epic2);

        Subtask subtask3 = new Subtask("подзадача 1 эпика 2", "Описание подзадачи 1 эпика 2", Status.DONE, 6);
        taskManager.addSubtask(subtask3);

//        System.out.println(taskManager);

        taskManager.removeTaskById(1);
        taskManager.removeEpicById(3);

        System.out.println(taskManager);

//        System.out.println("История:");
//        System.out.println(taskManager.getHistory());

        taskManager.printAllTasks(taskManager);

    }
}
