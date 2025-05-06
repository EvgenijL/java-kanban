package manager;

import model.Epic;
import model.Subtask;
import model.Task;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;

public class FileBackedTaskManager extends InMemoryTaskManager {

    private final File file;

    public FileBackedTaskManager(File file) {
        this.file = file;
    }

    public void save() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(TaskCsvFormatHandler.getHeader());
            writer.newLine();

            for (Map.Entry<Integer, Task> entry : this.tasks.entrySet()) {
                Task task = entry.getValue();
                writer.write(TaskCsvFormatHandler.toString(task));
                writer.newLine();
            }

            for (Map.Entry<Integer, Epic> entry : this.epics.entrySet()) {
                Epic epic = entry.getValue();
                writer.write(TaskCsvFormatHandler.toString(epic));
                writer.newLine();
            }

            for (Map.Entry<Integer, Subtask> entry : this.subtasks.entrySet()) {
                Subtask subtask = entry.getValue();
                writer.write(TaskCsvFormatHandler.toString(subtask));
                writer.newLine();
            }
        } catch (IOException exception) {
            throw new ManagerSaveException("Работа с файлом остановлена из-за ошибки");
        }
    }

    public static FileBackedTaskManager loadFromFile(File file) {
        final FileBackedTaskManager result = new FileBackedTaskManager(file);

        try {
            List<String> lines = Files.readAllLines(file.toPath());
            if (lines.isEmpty()) {
                return result;
            }

            int maxId = 0;
            for (int i = 1; i < lines.size(); i++) {
                String line = lines.get(i).trim();
                if (!line.isEmpty()) {
                    String[] parts = line.split(",");
                    int id = Integer.parseInt(parts[0]);
                    if (id > maxId) {
                        maxId = id;
                    }
                }
            }

            setNextId(maxId);

            for (int i = 1; i < lines.size(); i++) {
                String line = lines.get(i).trim();
                if (!line.isEmpty()) {
                    Task task = TaskCsvFormatHandler.fromString(line);
                    if (!(task instanceof Epic || task instanceof Subtask)) {
                        result.tasks.put(task.getId(), task);
                    } else if (task instanceof Epic epic) {
                        result.epics.put(epic.getId(), epic);
                    } else {
                        Subtask subtask = (Subtask) task;
                        result.subtasks.put(subtask.getId(), subtask);
                        Epic epic = result.epics.get(subtask.getEpicId());
                        if (epic != null) {
                            epic.addSubtaskId(subtask.getId());
                            result.updateStatusEpic(epic);
                        }
                    }
                }
            }
        } catch (IOException exception) {
            throw new ManagerSaveException("Ошибка при работе с файлом");
        }
        return result;
    }

    @Override
    public void addTask(Task task) {
        super.addTask(task);
        save();
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public Task getTaskById(int id) {
        final Task task = super.getTaskById(id);
        save();
        return task;
    }

    @Override
    public void removeTaskById(int id) {
        super.removeTaskById(id);
        save();
    }

    @Override
    public void clearTasks() {
        super.clearTasks();
        save();
    }

    @Override
    public void addEpic(Epic epic) {
        super.addEpic(epic);
        save();
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }

    @Override
    public Task getEpicById(int id) {
        final Task epic = super.getEpicById(id);
        save();
        return epic;
    }

    @Override
    public void removeEpicById(int id) {
        super.removeEpicById(id);
        save();
    }

    @Override
    public void clearEpics() {
        super.clearEpics();
        save();
    }

    @Override
    public void addSubtask(Subtask subtask) {
        super.addSubtask(subtask);
        save();
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        super.updateSubtask(subtask);
        save();
    }

    @Override
    public Subtask getSubtaskById(int id) {
        final Subtask subtask = super.getSubtaskById(id);
        save();
        return subtask;
    }

    @Override
    public void removeSubtaskById(int id) {
        super.removeSubtaskById(id);
        save();
    }

    @Override
    public void clearSubtasks() {
        super.clearSubtasks();
        save();
    }
}
