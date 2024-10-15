import java.util.Date;

public interface Task {

    String getTitle();
    String getDescription();
    Date getDueDate();
    String getStatus();
    int getPriority();
}
