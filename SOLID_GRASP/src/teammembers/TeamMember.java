package teammembers;

import java.util.List;
import java.util.ArrayList;
import project.Project;

public abstract class TeamMember implements ContactInfo, ProjectMembership, TaskCompletion {
    private String name;
    private String email;
    private List<Project> projects;

    protected TeamMember(String name, String email) {
        this.name = name;
        this.email = email;
        this.projects = new ArrayList<>();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void joinProject(Project project) {
        projects.add(project);
    }

    @Override
    public void leaveProject(Project project) {
        projects.remove(project);
    }

    @Override
    public abstract void completeTask();
}
