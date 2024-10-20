package teammembers.factories;

import teammembers.QualityAssurer;
import teammembers.TeamMember;

public class QualityAssurerFactory extends TeamMemberFactory {

    @Override
    public TeamMember createTeamMember(String name, String email) {
        return new QualityAssurer(name, email);
    }
}
