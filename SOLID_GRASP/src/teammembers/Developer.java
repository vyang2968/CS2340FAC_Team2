package teammembers;

public class Developer extends TeamMember {
    private String description;

    public Developer(String name, String email) {
        super(name, email);
    }

    @Override
    public void setDescription() {
        this.description = "Works on project features";
    }
}
