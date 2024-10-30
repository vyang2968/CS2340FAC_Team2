package teammembers.factories;

import teammembers.Developer;
import teammembers.TeamMember;

public class DeveloperFactory extends TeamMemberFactory {
    @Override
    public TeamMember createTeamMember(String name, String email) {
        return new Developer(name, email);
    }
}
