package manager;

import model.Epic;
import model.Subtask;
import model.Task;

import java.util.ArrayList;
import java.util.List;

public interface TaskManager {

    void addTask(Task task);

    void updateTask(Task task);

    ArrayList<Task> getTasks();

    Task getTaskById(int id);

    void removeTaskById(int id);

    void clearTasks();

    void addEpic(Epic epic);

    void updateEpic(Epic epic);

    ArrayList<Epic> getEpics();

    Task getEpicById(int id);

    void removeEpicById(int id);

    void clearEpics();

    void addSubtask(Subtask subtask);

    void updateSubtask(Subtask subtask);

    ArrayList<Subtask> getSubtasks();

    Subtask getSubtaskById(int id);

    void removeSubtaskById(int id);

    void clearSubtasks();

    ArrayList<Subtask> getSubtaskByEpic(int epicId);

    List<Task> getHistory();

}
