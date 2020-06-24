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
    public static final String NORMAL_JSON = "input.json";
    public static final String NORMAL_XML = "input.xml";

    @DisplayName("Transformer exists")
    @Test
    void transformerExists() {
        new Transformer();
    }

    @DisplayName("Converts JSON array")
    @Test
    void transformsJsonArray() throws URISyntaxException, IOException {
        Transformer transformer = getTransformer(SINGLE_OBJECT_JSON, FileTypes.json);

        int exitCode = transformer.call();
        assertEquals(0, exitCode);
        assertTrue(Files.exists(transformer.output));
    }

    @DisplayName("Converts JSON to JSON")
    @Test
    void transformsJsonToJson() throws URISyntaxException, IOException {
        Transformer transformer = getTransformer(NORMAL_JSON, FileTypes.json);

        int exitCode = transformer.call();
        assertEquals(0, exitCode);
        assertTrue(Files.exists(transformer.output));
    }

    @DisplayName("Converts JSON to XML")
    @Test
    void transformsJsonToXml() throws URISyntaxException, IOException {
        Transformer transformer = getTransformer(NORMAL_JSON, FileTypes.xml);

        int exitCode = transformer.call();
        assertEquals(0, exitCode);
        assertTrue(Files.exists(transformer.output));
    }
}
