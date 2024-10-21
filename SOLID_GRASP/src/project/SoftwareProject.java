package project;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import tasks.Task;
import teammembers.TeamMember;
import teammembers.ProjectMemberManager;

public class SoftwareProject implements Project {

    private String name;
    private Date startDate;
    private Date endDate;
    private String description;
    private List<Task> tasks;
    private ProjectMemberManager projectMemberManager;

    public SoftwareProject(String name, Date startDate, Date endDate) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = "A project focused on the development, testing, and deployment of a software product.";
        this.tasks = new ArrayList<>();
        this.projectMemberManager = new ProjectMemberManager();
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
    @Override
    public void addMember(TeamMember member) {
        projectMemberManager.joinProject(member, this);
    }
    @Override
    public void removeMember(TeamMember member) {
        projectMemberManager.leaveProject(member, this);
    }
    public String getName() {
        return name;
    }
    public void setName(String newName) {
        name = newName;
    }
    public Date getStartDate() {
        return startDate;
    }
    public void setStartDate(Date newStartDate) {
        startDate = newStartDate;
    }
    public Date getEndDate() {
        return endDate;
    }
    public void setEndDate(Date newEndDate) {
        endDate = newEndDate;
    }
    public String getDescription() {
        return description;
    }
}