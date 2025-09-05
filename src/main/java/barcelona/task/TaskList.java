package barcelona.task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents a collection of {@link Task} objects.
 * <p>
 * The {@code TaskList} class provides a wrapper around an
 * {@link ArrayList} of tasks, offering convenient methods for
 * adding, removing, retrieving, and managing tasks.
 * </p>
 */
public class TaskList {
    private final ArrayList<Task> taskList;

    public TaskList(ArrayList<Task> taskList) {
        this.taskList = taskList;
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
        return listAsString(this.taskList);
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
     * @param tasks - List of tasks to be added
     * @return Size of new Tasklist
     */
    public int add(Task... tasks) {
        this.taskList.addAll(Arrays.asList(tasks));
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

    /**
     * Filter tasklist by keyword
     * @param keyword - search keyword for tasklist
     * @return filtered list as string
     */
    public String filter(String keyword) {
        return listAsString(this.taskList.stream()
                .filter((Task a) -> a.description.contains(keyword)).toList());
    }

    /**
     * converts tasklist to exportable format
     * @param list - tasklist object
     * @return - tasklist as string
     */
    public String listAsString(List<Task> list) {
        StringBuilder string = new StringBuilder();
        for (int j = 1; j <= list.size(); j++) {
            string.append(j).append(". ").append(list.get(j - 1)).append("\n");
        }
        return string.toString();
    }
}
