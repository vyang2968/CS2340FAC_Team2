package teammembers;

public abstract class TeamMember {
    private String name;
    private String email;

    protected TeamMember(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public abstract void setDescription();
}
