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

    /**
     * Get String listing of all tasks in the tasklist
     * @return String to be displayed to user in console
     */
    public String list() {
        StringBuilder list = new StringBuilder();
        for (int j = 1; j <= tasklist.size(); j++) {
            list.append(j).append(". ").append(tasklist.get(j - 1)).append("\n");
        }
        return list.toString();
    }

    /**
     * Marks a task as done
     * @param index - Index of task in tasklist
     */
    public void markDone(int index) {
        this.tasklist.get(index).markAsDone();
    }

    /**
     * Marks a task as undone
     * @param index - Index of task in tasklist
     */
    public void markUndone(int index) {
        this.tasklist.get(index).markAsUndone();
    }

    public Task getTask(int index) {
        return this.tasklist.get(index);
    }

    /**
     * Adds a task to tasklist
     * @param task - Task to be added
     * @return Size of new Tasklist
     */
    public int add(Task task) {
        this.tasklist.add(task);
        return tasklist.size();
    }

    /**
     * Removes a task from tasklist
     * @param toRemove - task to be removed
     * @return Size of new Tasklist
     */
    public int remove(Task toRemove) {
        this.tasklist.remove(toRemove);
        return this.tasklist.size();
    }
}
