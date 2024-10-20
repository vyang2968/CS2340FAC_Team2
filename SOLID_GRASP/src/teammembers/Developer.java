package teammembers;

public class Developer extends TeamMember {

    public Developer(String name, String email) {
        super(name, email);
    }

    @Override
    public void completeTask() {
        System.out.println("Task has been developed");
    }
}
