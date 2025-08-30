package Barcelona.Task;

import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> tasklist;

    public TaskList(ArrayList<Task> arr) {
        this.tasklist = arr;
    }

    public TaskList() {
        this.tasklist = new ArrayList<>();
    }

    public ArrayList<Task> getList() {
        return this.tasklist;
    }

    public String list() {
        StringBuilder list = new StringBuilder();
        for (int j = 1; j <= tasklist.size(); j++) {
            list.append(j).append(". ").append(tasklist.get(j - 1)).append("\n");
        }
        return list.toString();
    }

    public void markDone(int index) {
        this.tasklist.get(index).markAsDone();
    }

    public void markUndone(int index) {
        this.tasklist.get(index).markAsUndone();
    }

    public Task getTask(int index) {
        return this.tasklist.get(index);
    }

    public int add(Task task) {
        this.tasklist.add(task);
        return tasklist.size();
    }

    public int remove(Task toRemove) {
        this.tasklist.remove(toRemove);
        return this.tasklist.size();
    }
}
