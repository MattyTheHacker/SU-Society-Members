import java.util.Date;

public class Member {
    private final String name;
    private final int id;
    private final Date joinDate;
    private final Date expireDate;

    public Member(String name, int id, Date joinDate, Date expireDate) {
        this.name = name;
        this.id = id;
        this.joinDate = joinDate;
        this.expireDate = expireDate;
    }

    @Override
    public String toString() {
        return "Member{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", joinDate=" + joinDate +
                ", expireDate=" + expireDate +
                '}';
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public String getFirstName() {
        // name format: surname, first name
        String[] nameParts = name.split(",");
        return nameParts[1].trim();
    }

    public String getLastName() {
        // name format: surname, first name
        String[] nameParts = name.split(",");
        return nameParts[0].trim();
    }
}
