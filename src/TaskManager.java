import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {

    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();
    private final HashMap<Integer, Subtask> subtasks = new HashMap<>();
    private int nextId = 1;

    public void addTask(Task task) {
        task.id = nextId++;
        tasks.put(task.id, task);
    }

    public void updateTask(Task task) {
        if (tasks.containsKey(task.id)) {
            tasks.put(task.id, task);
        }
    }

    public ArrayList<Task> getListTask() {
        return new ArrayList<Task>(tasks.values());
    }

    public Task getTaskById(int id){
        return tasks.get(id);
    }

    public void removeTaskById(int id) {
        tasks.remove(id);
    }

    public void clearTask(){
        tasks.clear();
    }

    public void addEpic(Epic epic) {
        epic.setId(nextId++);
        epics.put(epic.getId(), epic);
        epic.setStatus(Status.NEW);

    }

    public void updateEpic(Epic epic) {
        if (epics.containsKey(epic.getId())) {
            epics.put(epic.getId(), epic);
            epic.setStatus(Status.NEW);
            updateStatusEpic(epic);
        }
    }

    public ArrayList<Epic> getListEpics() {
        return new ArrayList<Epic>(epics.values());
    }

    public Task getEpicById(int id){
        return epics.get(id);
    }

    public void removeEpicById(int id) {
        ArrayList<Integer> subtaskId = epics.get(id).getSubtaskIds();
        for (int subtask : subtaskId) {
            removeSubtaskById(subtask);
        }
        epics.remove(id);
    }

    public void clearEpic() {
        epics.clear();
        subtasks.clear();
    }

    public void addSubtask(Subtask subtask) {
        if (epics.containsKey(subtask.getEpicId())) {
            subtask.setId(nextId++);
            subtasks.put(subtask.getId(), subtask);
            epics.get(subtask.getEpicId()).getSubtaskIds().add(subtask.getId());
            updateStatusEpic(epics.get(subtask.getEpicId()));
        }
    }

    public void updateSubtask(Subtask subtask) {
        if (subtasks.containsKey(subtask.id)) {
            subtasks.put(subtask.id, subtask);
            updateStatusEpic(epics.get(subtask.getEpicId()));
        }
    }

    public ArrayList<Subtask> getListSubtask() {
        return new ArrayList<Subtask>(subtasks.values());
    }

    public Subtask getSubtaskById(int id){
        return subtasks.get(id);
    }

    public void removeSubtaskById(int id) {
        int epicId = getSubtaskById(id).getEpicId();
        subtasks.remove(id);
        Epic epic = (Epic) getEpicById(epicId);
        epic.getSubtaskIds().remove(Integer.valueOf(id));
        updateStatusEpic(epic);

    }

    public void clearSubtask(){
        for (Epic epic : epics.values()) {
            epic.setStatus(Status.NEW);
        }
        subtasks.clear();
    }

    public ArrayList<Subtask> getSubtaskByEpic(int epicId) {
        ArrayList<Subtask> newSubtasks = new ArrayList<>();
        ArrayList<Integer> subtaskId = epics.get(epicId).getSubtaskIds();
        for (int subtask : subtaskId) {
            newSubtasks.add(subtasks.get(subtask));
        }
        return newSubtasks;
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

    @Override
    public String toString() {
        return "TaskManager{" +
                "tasks=" + tasks +
                ", epics=" + epics +
                ", subtasks=" + subtasks +
                '}';
    }

}
