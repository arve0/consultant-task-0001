package no.unit.transformer.features;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.messages.internal.com.google.common.base.CaseFormat;
import no.unit.transformer.FileTypes;
import no.unit.transformer.Transformer;
import picocli.CommandLine;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestWiring {
    public static Path getFileFromResources(String filename){
        try {
            return Paths.get(ClassLoader.getSystemResource(filename).toURI());
        } catch (Exception error) {
            return Paths.get(filename);
        }
    }

    protected boolean hasFlagAsOption(CommandLine application, String flag) {
        return application.getCommandSpec().findOption(flag).isOption();
    }

    protected Class<?> getFlagType(Object obj, String flag) throws NoSuchFieldException {
        assertTrue(flag.startsWith("--"));
        String fieldName = CaseFormat.LOWER_HYPHEN.to(CaseFormat.LOWER_CAMEL, flag.substring(2));
        return obj.getClass().getField(fieldName).getType();
    }

    protected JsonNode getOnlyObjectInArray(JsonNode array) {
        assertTrue(array.isArray());
        assertEquals(1, array.size());
        return array.get(0);
    }

    protected JsonNode readObjectFromFile(Path file) throws IOException {
        assertTrue(Files.exists(file));
        String content = Files.readString(file);
        return new ObjectMapper().readValue(content, JsonNode.class);
    }

    protected int getInt(JsonNode object, String field) {
        assertTrue(object.has(field));
        JsonNode value = object.get(field);
        assertTrue(value.isInt());
        return value.asInt();
    }

    protected Object getString(JsonNode object, String field) {
        assertTrue(object.has(field));
        JsonNode value = object.get(field);
        assertTrue(value.isTextual());
        return value.asText();
    }

    public static Path getTempFileWithoutCreatingEmptyFile() throws IOException {
        Path outputDirectory = Files.createTempDirectory("transformer-output");
        return outputDirectory.resolve("output.json");
    }

    public Transformer getTransformer(String inputFile, FileTypes outputType) throws URISyntaxException, IOException {
        Transformer transformer = new Transformer();
        transformer.input = TestWiring.getFileFromResources(inputFile);
        transformer.output = TestWiring.getTempFileWithoutCreatingEmptyFile();
        transformer.inputFormat = inputFile.endsWith(".xml")
            ? FileTypes.xml
            : FileTypes.json;
        transformer.outputFormat = outputType;
        return transformer;
    }
}
