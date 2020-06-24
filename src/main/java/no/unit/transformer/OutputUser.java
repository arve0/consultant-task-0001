package no.unit.transformer;

public class OutputUser implements Comparable<OutputUser> {
    public Integer id;
    public Identity identity;
    public String role;

    public OutputUser() {
    }

    /**
     * Transform InputUser to a User.
     */
    public OutputUser(InputUser user) {
        id = Integer.parseInt(user.sequence);
        identity = new Identity(user.name);
        role = user.role;
    }

    @Override
    public int compareTo(OutputUser other) {
        if (id == null || other.id == null) {
            return 0;
        }
        return id.compareTo(other.id);
    }

    public class Identity {
        public String given;
        public String family;

        public Identity() {
        }

        /**
         * <p>Splits name into 'given' and 'family' name.</p>
         *
         * <p>Example: 'Olsen, Jon' → family: 'Olsen', given: 'Jon'</p>
         */
        public Identity(String name) {
            String[] names = name.split(", ");
            assert names.length == 2;
            family = names[0];
            given = names[1];
        }
    }
}
