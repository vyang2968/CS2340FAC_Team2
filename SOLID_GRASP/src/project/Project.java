package project;
import java.util.List;
import tasks.Task;
import teammembers.TeamMember;

public abstract class Project {

    abstract List<Task> getTasks();
    abstract void addTask(Task task);
    abstract void removeTask(Task task);
    
    void addMember(TeamMember member) {
        member.joinProject(this);
    }
    void removeMember(TeamMember member) {
        member.leaveProject(this);
    }
}
