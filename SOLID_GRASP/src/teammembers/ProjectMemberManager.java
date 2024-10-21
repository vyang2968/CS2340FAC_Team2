package teammembers;

import java.util.List;
import java.util.ArrayList;

import project.Project;

public class ProjectMemberManager {
    private List<Project> projects;

    public ProjectMemberManager() {
        this.projects = new ArrayList<>();
    }

    public void joinProject(TeamMember member, Project project) {
        projects.add(project);
    }

    public void leaveProject(TeamMember member, Project project) {
        projects.remove(project);
    }
}