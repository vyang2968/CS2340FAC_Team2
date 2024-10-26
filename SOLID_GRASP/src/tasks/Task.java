package tasks;

import java.util.Date;

public interface Task {
    String getTitle();
    String getDescription();
    Date getDueDate();
    String getStatus();
    Priority getPriority();
    void setStatus(String status);
    void setDueDate(Date dueDate);

    void completeTask();
}
