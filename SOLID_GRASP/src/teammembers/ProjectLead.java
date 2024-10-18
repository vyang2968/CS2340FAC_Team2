package teammembers;

public class ProjectLead extends TeamMember {
    private String description;

    public ProjectLead(String name, String email) {
        super(name, email);
    }

    @Override
    public void setDescription() {
        this.description = "Oversees project and ensures its completion";
    }
}
