package teammembers;

import java.util.List;
import project.Project;

public abstract class TeamMember {
    private String name;
    private String email;
    private List<Project> projects;

    protected TeamMember(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void joinProject(Project project) {
        projects.add(project);
    }

    public void leaveProject(Project project) {
        projects.remove(project);
    }

    public abstract void completeTask();
}
