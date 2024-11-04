package teammembers;

import project.Project;

public interface ProjectMembership {
    void joinProject(Project project);
    void leaveProject(Project project);
}