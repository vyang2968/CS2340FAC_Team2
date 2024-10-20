package tasks;

import java.util.Date;

public class BaseTask implements Task {
    protected String title;
    protected String description;
    protected Date dueDate;
    protected String status;
    protected Priority priority;

    public BaseTask(String title, String description, Date dueDate, String status, Priority priority) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.status = status;
        this.priority = priority;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public Date getDueDate() {
        return dueDate;
    }

    @Override
    public String getStatus() {
        return status;
    }

    @Override
    public Priority getPriority() {
        return priority;
    }

}
