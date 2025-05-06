package manager;

import model.*;

public class TaskCsvFormatHandler {

    public static final String DELIMITER = ",";

    public static String getHeader() {
        return "id,type,name,status,description,epic";
    }

    public static String toString(Task task) {
        //1,TASK,Task1,NEW,Description task1,
        if (!(task instanceof Epic || task instanceof Subtask)) {
            return task.getId() + DELIMITER
                    + task.getType() + DELIMITER
                    + task.getName() + DELIMITER
                    + task.getStatus() + DELIMITER
                    + task.getDescription() + DELIMITER;
        } else if (task instanceof Epic epic) {
            return epic.getId() + DELIMITER
                    + epic.getType() + DELIMITER
                    + epic.getName() + DELIMITER
                    + epic.getStatus() + DELIMITER
                    + epic.getDescription() + DELIMITER;
        } else {
            Subtask subtask = (Subtask) task;
            return subtask.getId() + DELIMITER
                    + subtask.getType() + DELIMITER
                    + subtask.getName() + DELIMITER
                    + subtask.getStatus() + DELIMITER
                    + subtask.getDescription() + DELIMITER
                    + subtask.getEpicId();
        }
    }

    public static Task fromString(String value) {
        String[] partsString = value.split(DELIMITER, 6);

        int id = Integer.parseInt(partsString[0]);
        TypesTasks type = TypesTasks.valueOf(partsString[1]);
        String name = partsString[2];
        Status status = Status.valueOf(partsString[3]);
        String description = partsString[4];
        String epicIdValue = partsString.length > 5 ? partsString[5].trim() : null;

        return switch (type) {
            case TASK -> new Task(id, name, description, status);
            case EPIC -> new Epic(id, name, description, status);
            case SUBTASK -> {
                if (epicIdValue == null || epicIdValue.isEmpty()) {
                    throw new IllegalArgumentException("Для подзадачи не указан ID эпика.");
                }
                int epicId = Integer.parseInt(epicIdValue);
                yield new Subtask(id, name, description, status, epicId);
            }
        };
    }
}
