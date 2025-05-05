package manager;

import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {

    private static final String FILE_NAME = "java-kanban/resources/storage.csv";

    public static void main(String[] args) {

        File fileStorage = createFile(FILE_NAME);

        System.out.println("Поехали!");

        try {
            TaskManager taskManager = Managers.getDefaultTaskManager(fileStorage);
        // создаем задачи
        Task task1 = new Task("Задача1 (ID=1)", "Выполнить задачу 1", Status.DONE);
        taskManager.addTask(task1); // добавили эпик (id 1)
        Task task2 = new Task("Задача2 (ID=2)", "Выполнить задачу 2", Status.IN_PROGRESS);
        taskManager.addTask(task2);

        // создаем эпики
        Epic epic1 = new Epic("Эпик1 (ID=3)", "Выполнить эпик 1");
        taskManager.addEpic(epic1);
        Epic epic2 = new Epic("Эпик2 (ID=4)", "Выполнить эпик 2");
        taskManager.addEpic(epic2);

        // создаем сабтаски
        Subtask subtask1 = new Subtask("Сабтаск1 (ID=5)", "Выполнить сабтаск 1", Status.IN_PROGRESS, 3);
        taskManager.addSubtask(subtask1);
        Subtask subtask2 = new Subtask("Сабтаск2 (ID=6)", "Выполнить сабтаск 2", Status.DONE, 3);
        taskManager.addSubtask(subtask2);
        Subtask subtask3 = new Subtask("Сабтаск3 (ID=7)", "Выполнить сабтаск 3", Status.DONE, 4);
        taskManager.addSubtask(subtask3);
        Subtask subtask4 = new Subtask("Сабтаск4 (ID=8)", "Выполнить сабтаск 4", Status.DONE, 4);
        taskManager.addSubtask(subtask4);

        // история после добавления
        System.out.println("Статистика после добавления");
        printAllTasks(taskManager);

        // запросы просмотров
        taskManager.getTaskById(task1.getId());
        taskManager.getTaskById(task2.getId());
        taskManager.getEpicById(epic1.getId());
        taskManager.getEpicById(epic2.getId());
        taskManager.getSubtaskById(subtask1.getId());
        taskManager.getSubtaskById(subtask2.getId());
        taskManager.getSubtaskById(subtask3.getId());
        taskManager.getSubtaskById(subtask4.getId());

        System.out.println();
        System.out.println("Статистика после просмотров");
        printAllTasks(taskManager);

        // удаляем
        taskManager.removeTaskById(task1.getId());
        taskManager.removeEpicById(epic1.getId());
        taskManager.removeSubtaskById(subtask3.getId());

        System.out.println();
        System.out.println("Статистика после удаления");
        printAllTasks(taskManager);

        taskManager.getSubtaskById(subtask4.getId());
        taskManager.getTaskById(task2.getId());

        System.out.println();
        System.out.println("Статистика после второго просмотра");
        printAllTasks(taskManager);

        taskManager.clearSubtasks();

        System.out.println();
        System.out.println("Статистика после метода clear");
        printAllTasks(taskManager);
        } catch (ManagerSaveException exception) {
            System.out.println("Перезапустите программу");
        }
    }

    private static File createFile(String fileName) {
        try {
            return Files.createFile(Paths.get(fileName)).toFile();
        } catch (IOException exception) {
            System.out.println("Ошибка, файл не создан" + exception.getMessage());
        }
        throw new UnsupportedOperationException("Файл не создан");
    }

    private static void printAllTasks(TaskManager taskManager) {
        System.out.println("Задачи:");
        for (Task task : taskManager.getTasks()) {
            System.out.println(task);
        }
        System.out.println("Эпики:");
        for (Epic epic : taskManager.getEpics()) {
            System.out.println(epic);

            for (Task task : taskManager.getSubtaskByEpic(epic.getId())) {
                System.out.println("--> " + task);
            }
        }
        System.out.println("Подзадачи:");
        for (Subtask newSubtask : taskManager.getSubtasks()) {
            System.out.println(newSubtask);
        }

        System.out.println("История:");
        for (Task task : taskManager.getHistory()) {
            System.out.println(task);
        }
    }
}
