package no.unit.transformer;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

@JacksonXmlRootElement(localName = "users")
public class OutputUsers implements Iterable<OutputUser> {
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "user")
    public List<OutputUser> users;

    public OutputUsers() {
    }

    public OutputUsers(List<OutputUser> users) {
        this.users = users;
    }

    @Override
    public Iterator<OutputUser> iterator() {
        return users.iterator();
    }

    @Override
    public void forEach(Consumer<? super OutputUser> action) {
        users.forEach(action);
    }

    @Override
    public Spliterator<OutputUser> spliterator() {
        return users.spliterator();
    }
}
