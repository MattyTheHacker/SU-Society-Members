import java.time.LocalDateTime;

public record Member(String name, int id, LocalDateTime joinDate, LocalDateTime expireDate) {

    @Override
    public String toString() {
        return "Member{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", joinDate=" + joinDate +
                ", expireDate=" + expireDate +
                '}';
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
