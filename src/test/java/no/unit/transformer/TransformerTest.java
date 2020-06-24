package no.unit.transformer;

import no.unit.transformer.features.TestWiring;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TransformerTest extends TestWiring {
    public static final String SINGLE_OBJECT_JSON = "single_object.json";
    public static final String JSON_INPUT = "input.json";
    public static final String XML_INPUT = "input.xml";
    public static final String BAD_INPUT = "badly_formatted.json";

    @DisplayName("Transformer exists")
    @Test
    void transformerExists() {
        new Transformer();
    }

    @DisplayName("Converts JSON array")
    @Test
    void transformsJsonArray() throws URISyntaxException, IOException {
        testConvertion(SINGLE_OBJECT_JSON, FileTypes.json);
    }

    private void testConvertion(String from, FileTypes to) throws URISyntaxException, IOException {
        Transformer transformer = getTransformer(from, to);

        int exitCode = transformer.call();
        assertEquals(0, exitCode);
        assertTrue(transformer.getErrorMessage().isEmpty());
        assertTrue(Files.exists(transformer.output));
    }

    @DisplayName("Converts JSON to JSON")
    @Test
    void transformsJsonToJson() throws URISyntaxException, IOException {
        testConvertion(JSON_INPUT, FileTypes.json);
    }

    @DisplayName("Converts JSON to XML")
    @Test
    void transformsJsonToXml() throws URISyntaxException, IOException {
        testConvertion(JSON_INPUT, FileTypes.xml);
    }

    @DisplayName("Converts XML to XML")
    @Test
    void transformsXmlToXml() throws URISyntaxException, IOException {
        testConvertion(XML_INPUT, FileTypes.xml);
    }

    @DisplayName("Should give error on bad input")
    @Test
    public void shouldGiveErrorOnBadInput() throws Exception {
        Transformer transformer = getTransformer(BAD_INPUT, FileTypes.xml);

        int exitCode = transformer.call();
        assertEquals(1, exitCode);
        assertTrue(transformer.getErrorMessage().isPresent());
    }
}
