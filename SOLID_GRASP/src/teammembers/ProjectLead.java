package teammembers;

public class ProjectLead extends TeamMember {
    private String description;

    public ProjectLead(String name, String email) {
        super(name, email);
    }

    @Override
    public void completeTask() {
        System.out.println("Task has been approved to be complete.");
    }
}
