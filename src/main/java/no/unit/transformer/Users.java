package no.unit.transformer;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

@JacksonXmlRootElement(localName = "users")
public class Users implements Iterable<User> {
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "user")
    public List<User> users;

    public Users() {
    }

    public Users(List<User> users) {
        this.users = users;
    }

    @Override
    public Iterator<User> iterator() {
        return users.iterator();
    }

    @Override
    public void forEach(Consumer<? super User> action) {
        users.forEach(action);
    }

    @Override
    public Spliterator<User> spliterator() {
        return users.spliterator();
    }
}
