package project;
import tasks.Task;
import teammembers.TeamMember;
import java.util.List;

public interface Project {
    List<Task> getTasks();
    void addTask(Task task);
    void removeTask(Task task);
    void addMember(TeamMember member);
    void removeMember(TeamMember member);
}
