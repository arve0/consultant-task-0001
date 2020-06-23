package no.unit.transformer;

public class User implements Comparable<User> {
    public Integer id;
    public Identity identity;
    public String role;

    public User() {}

    public User(InputPerson person) {
        id = Integer.parseInt(person.sequence);
        identity = new Identity(person.name);
        role = person.role;
    }

    @Override
    public int compareTo(User other) {
        if (id == null || other.id == null) {
            return 0;
        }
        return id.compareTo(other.id);
    }

    public class Identity {
        public String given;
        public String family;

        public Identity() {}

        public Identity(String name) {
            String[] names = name.split(", ");
            assert names.length == 2;
            family = names[0];
            given = names[1];
        }
    }
}
