package teammembers;

public class QualityAssurer extends TeamMember {
    private String description;

    public QualityAssurer(String name, String email) {
        super(name, email);
    }

    @Override
    public void setDescription() {
        this.description = "Verifies that project is up to company standards";
    }
}
