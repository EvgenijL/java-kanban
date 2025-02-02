package manager;

import model.Task;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;



public class InMemoryHistoryManager implements HistoryManager {

    private final List<Task> historyStorage = new LinkedList<>();
    private static final int SIZE_HISTORY_STORAGE = 10;

    @Override
    public List<Task> getHistory() {
        List<Task> newHistoryStorage = List.copyOf(historyStorage);
        return newHistoryStorage;
    }

    @Override
    public void addTask(Task task) {
        if(Objects.isNull(task)) {
            return;
        }

        historyStorage.add(new Task(task.getId(), task.getName(), task.getDescription(), task.getStatus()));

        if (historyStorage.size() > SIZE_HISTORY_STORAGE) {
            historyStorage.removeFirst();
        }
    }
}
