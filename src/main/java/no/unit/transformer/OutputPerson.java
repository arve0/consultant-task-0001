package no.unit.transformer;

public class OutputPerson {
    public Integer id;
    public Identity identity;
    public String role;

    public OutputPerson(InputPerson person) {
        id = Integer.parseInt(person.sequence);
        identity = new Identity(person.name);
        role = person.role;
    }

    public class Identity {
        public String given;
        public String family;

        public Identity(String name) {
            String[] names = name.split(", ");
            assert names.length == 2;
            family = names[0];
            given = names[1];
        }
    }
}
