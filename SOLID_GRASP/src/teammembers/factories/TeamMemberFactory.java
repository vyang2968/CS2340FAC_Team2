package teammembers.factories;

import teammembers.TeamMember;

public abstract class TeamMemberFactory {
    public abstract TeamMember createTeamMember(String name, String email);
}
