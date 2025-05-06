package model;

import java.util.ArrayList;

public class Epic extends Task {

    private ArrayList<Integer> subtaskIds = new ArrayList<>();

    public Epic(int id, String name, String description) {
        super(id, name, description, Status.NEW);
    }

    public Epic(int id, String name, String description, Status status) {
        super(id,name,description,status);
    }

    public Epic(String name, String description) {
        super(name, description, Status.NEW);
    }

    public ArrayList<Integer> getSubtaskIds() {
        return subtaskIds;
    }

    public void setSubtaskIds(ArrayList<Integer> subtaskIds) {
        this.subtaskIds = subtaskIds;
    }

    public void addSubtaskId(int subTaskId) {
        subtaskIds.add(subTaskId);

    }

    @Override
    public TypesTasks getType() {
        return TypesTasks.EPIC;
    }
}

