package manager;

import model.Task;
import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {

    private final HashMap<Integer, Node> nodeHashMap = new HashMap<>();
    private Node head;
    private Node tail;

    @Override
    public void remove(int id) {
        Node node = nodeHashMap.get(id);
        if (node != null) {
            removeNode(node);
        }
    }

    @Override
    public List<Task> getHistory() {
        List<Task> result = new ArrayList<>();
        Node node = head;
        while (Objects.nonNull(node)) {
            result.add(node.task);
            node = node.next;
        }
        return result;
    }

    @Override
    public void addTask(Task task) {
        if (Objects.isNull(task)) {
            return;
        }
        if (nodeHashMap.containsKey(task.getId())) {
            removeNode(nodeHashMap.get(task.getId()));
        }
        linkLast(task);
    }

    public static class Node {
        private Task task;
        private Node next;
        private Node prev;

        public Node(Task task) {
            this.task = task;
        }
    }

    private void linkLast(Task task) {
        Node newNode = new Node(task);
        if (tail == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        nodeHashMap.put(task.getId(), newNode);
    }

    private void removeNode(Node node) {
        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            head = node.next;
        }

        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            tail = node.prev;
        }
        nodeHashMap.remove(node.task.getId());
    }
}
