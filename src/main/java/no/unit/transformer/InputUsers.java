package no.unit.transformer;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

@JacksonXmlRootElement(localName = "users")
public class InputUsers {
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "user")
    public List<InputUser> users;

    public InputUsers() {
    }

    public InputUsers(List<InputUser> users) {
        this.users = users;
    }
}
