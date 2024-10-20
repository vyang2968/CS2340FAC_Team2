package project;

import java.util.Date;
import java.util.List;
import tasks.Task;

public class SoftwareProject extends Project{

    private List<Task> tasks;
    String name;
    String description;
    Date startDate;
    Date endDate;

    public SoftwareProject(String name, Date startDate, Date endDate) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = "A project focused on the development, testing, and deployement of a software product.";
    }

    @Override
    public List<Task> getTasks() {
        return tasks;
    }
    @Override
    public void addTask(Task task) {
        tasks.add(task);
    }
    @Override
    public void removeTask(Task task) {
        tasks.remove(task);
    }
}
