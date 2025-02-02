package manager;

import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {

    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();
    private final HashMap<Integer, Subtask> subtasks = new HashMap<>();
    private int nextId = 1;
    private final HistoryManager historyManager = Managers.getDefaultHistoryManager();

    @Override
    public void addTask(Task task) {
        task.setId(nextId++);
        tasks.put(task.getId(), task);
    }

    @Override
    public void updateTask(Task task) {
        if (tasks.containsKey(task.getId())) {
            tasks.put(task.getId(), task);
        }
    }

    @Override
    public ArrayList<Task> getTasks() {
        return new ArrayList<Task>(tasks.values());
    }

    @Override
    public Task getTaskById(int id){
        final Task task = tasks.get(id);
        historyManager.addTask(task);
        return task;
    }

    @Override
    public void removeTaskById(int id) {
        tasks.remove(id);
    }

    @Override
    public void clearTasks(){
        tasks.clear();
    }

    @Override
    public void addEpic(Epic epic) {
        epic.setId(nextId++);
        epics.put(epic.getId(), epic);
    }

    @Override
    public void updateEpic(Epic epic) {
        if (epics.containsKey(epic.getId())) {
            epics.put(epic.getId(), epic);
        }
    }

    @Override
    public ArrayList<Epic> getEpics() {
        return new ArrayList<Epic>(epics.values());
    }

    @Override
    public Task getEpicById(int id){
        final Epic epic = epics.get(id);
        historyManager.addTask(epic);
        return epic;
    }

    @Override
    public void removeEpicById(int id) {
        final Epic epic = epics.remove(id);
        for (Integer subtaskId : epic.getSubtaskIds()) {
            subtasks.remove(subtaskId);
        }
    }

    @Override
    public void clearEpics() {
        epics.clear();
        subtasks.clear();
    }

    @Override
    public void addSubtask(Subtask subtask) {
        if (epics.containsKey(subtask.getEpicId())) {
            subtask.setId(nextId++);
            subtasks.put(subtask.getId(), subtask);
            epics.get(subtask.getEpicId()).getSubtaskIds().add(subtask.getId());
            updateStatusEpic(epics.get(subtask.getEpicId()));
        }
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        if (subtasks.containsKey(subtask.getId())) {
            subtasks.put(subtask.getId(), subtask);
            updateStatusEpic(epics.get(subtask.getEpicId()));
        }
    }

    @Override
    public ArrayList<Subtask> getSubtasks() {
        return new ArrayList<Subtask>(subtasks.values());
    }

    @Override
    public Subtask getSubtaskById(int id){
        final Subtask subtask = subtasks.get(id);
        historyManager.addTask(subtask);
        return subtask;
    }

    @Override
    public void removeSubtaskById(int id) {
        int epicId = getSubtaskById(id).getEpicId();
        subtasks.remove(id);
        Epic epic = (Epic) getEpicById(epicId);
        epic.getSubtaskIds().remove(Integer.valueOf(id));
        updateStatusEpic(epic);
    }

    @Override
    public void clearSubtasks(){
        subtasks.clear();
        for (Epic epic : epics.values()) {
            epic.getSubtaskIds().clear();
            updateStatusEpic(epic);
        }
    }

    @Override
    public ArrayList<Subtask> getSubtaskByEpic(int epicId) {
        ArrayList<Subtask> newSubtasks = new ArrayList<>();
        ArrayList<Integer> subtaskId = epics.get(epicId).getSubtaskIds();
        for (int subtask : subtaskId) {
            newSubtasks.add(subtasks.get(subtask));
        }
        return newSubtasks;
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    private void updateStatusEpic(Epic epic) {
        Status status;
        int totalStatus = 0;
        int newSubtask = 0;
        int doneSubtask = 0;

        for (int subtaskID : epic.getSubtaskIds()) {//
            status = subtasks.get(subtaskID).getStatus();
            totalStatus++;

            if (status == Status.NEW || totalStatus == 0) {
                newSubtask++;
            } else if (status == Status.DONE) {
                doneSubtask++;
            }

            if (totalStatus == newSubtask || epic.getSubtaskIds().isEmpty()) {
                epic.setStatus(Status.NEW);
            } else if (totalStatus == doneSubtask) {
                epic.setStatus(Status.DONE);
            } else {
                epic.setStatus(Status.IN_PROGRESS);
            }
        }
    }
}
