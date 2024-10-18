package teammembers.factories;

import teammembers.ProjectLead;
import teammembers.TeamMember;

public class ProjectLeadFactory extends TeamMemberFactory {
    @Override
    public TeamMember createTeamMember(String email, String name) {
        return new ProjectLead(name, email);
    }
}
