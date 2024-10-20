package tasks;

import java.util.Date;

public class HighPriorityTask extends BaseTask {
    public HighPriorityTask(String title, String description, Date dueDate, String status) {
        super(title, description, dueDate, status, Priority.HIGH);
    }
}
