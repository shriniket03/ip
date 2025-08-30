package Barcelona.Task;

import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> taskList;

    public TaskList(ArrayList<Task> arr) {
        this.taskList = arr;
    }

    public TaskList() {
        this.taskList = new ArrayList<>();
    }

    public ArrayList<Task> getList() {
        return this.taskList;
    }

    /**
     * Get String listing of all tasks in the tasklist
     * @return String to be displayed to user in console
     */
    public String list() {
        StringBuilder list = new StringBuilder();
        for (int j = 1; j <= taskList.size(); j++) {
            list.append(j).append(". ").append(taskList.get(j - 1)).append("\n");
        }
        return list.toString();
    }

    /**
     * Marks a task as done
     * @param index - Index of task in tasklist
     */
    public void markDone(int index) {
        this.taskList.get(index).markAsDone();
    }

    /**
     * Marks a task as undone
     * @param index - Index of task in tasklist
     */
    public void markUndone(int index) {
        this.taskList.get(index).markAsUndone();
    }

    public Task getTask(int index) {
        return this.taskList.get(index);
    }

    /**
     * Adds a task to tasklist
     * @param task - Task to be added
     * @return Size of new Tasklist
     */
    public int add(Task task) {
        this.taskList.add(task);
        return taskList.size();
    }

    /**
     * Removes a task from tasklist
     * @param toRemove - task to be removed
     * @return Size of new Tasklist
     */
    public int remove(Task toRemove) {
        this.taskList.remove(toRemove);
        return this.taskList.size();
    }
}
