package no.unit.transformer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class InputPerson {
    public String name;
    public String sequence;
    public String role;
}
