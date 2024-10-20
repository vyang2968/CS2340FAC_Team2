package teammembers;

public class QualityAssurer extends TeamMember {

    public QualityAssurer(String name, String email) {
        super(name, email);
    }

    @Override
    public void completeTask() {
        System.out.println("Task has been completed up to company standards.");
    }
}
