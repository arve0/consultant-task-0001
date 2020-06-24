package no.unit.transformer;

import no.unit.transformer.features.TestWiring;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TransformerTest {
    public static final String SINGLE_OBJECT_JSON = "single_object.json";

    @DisplayName("Transformer exists")
    @Test
    void transformerExists() {
        new Transformer();
    }

    @DisplayName("Converts JSON array")
    @Test
    void transformsJsonArray() throws URISyntaxException, IOException {
        Transformer transformer = new Transformer();
        transformer.input = TestWiring.getFileFromResources(SINGLE_OBJECT_JSON);
        transformer.output = TestWiring.getTempFileWithoutCreatingEmptyFile();
        transformer.inputFormat = FileTypes.json;
        transformer.outputFormat = FileTypes.json;

        int exitCode = transformer.call();
        assertEquals(0, exitCode);
        assertTrue(Files.exists(transformer.output));
    }

}
