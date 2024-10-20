package tasks;

import java.util.Date;

public class RecurringTask extends BaseTask {
    public RecurringTask (String title, String Description, Date dueDate, Priority priority) {
        super(title, Description, dueDate, "Recurring", priority);
    }
}
