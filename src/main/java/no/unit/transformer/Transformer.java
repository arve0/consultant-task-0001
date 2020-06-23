package no.unit.transformer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import picocli.CommandLine;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Transformer {
    @CommandLine.Option(names = { "--input" }, paramLabel = "INPUT", description = "the input file")
    public Path input;

    @CommandLine.Option(names = { "--output" }, paramLabel = "OUTPUT", description = "the output file")
    public Path output;

    @CommandLine.Option(names = { "--input-format" }, paramLabel = "INPUT FORMAT", description = "the input-format")
    public FileTypes inputFormat;

    @CommandLine.Option(names = { "--output-format" }, paramLabel = "OUTPUT FORMAT", description = "the output-format")
    public FileTypes outputFormat;

    private ObjectMapper objectMapper = new ObjectMapper();

    public void transform() throws IOException {
        String json = Files.readString(input);
        JsonNode object = objectMapper.readValue(json, JsonNode.class);

        boolean isObjectWithUsers = object.has("users");
        JsonNode users = isObjectWithUsers
                ? object.get("users")
                : object;

        List<User> transformed = Arrays.asList(objectMapper.treeToValue(users, InputPerson[].class))
                .stream()
                .map(User::new)
                .sorted()
                .collect(Collectors.toList());

        BufferedWriter outputBuffer = Files.newBufferedWriter(output);
        ObjectMapper outputMapper = outputFormat.equals(FileTypes.xml)
                ? new XmlMapper()
                : objectMapper;

        if (isObjectWithUsers) {
            outputMapper.writeValue(outputBuffer, new Users(transformed));
        } else {
            outputMapper.writeValue(outputBuffer, transformed);
        }
    }
}
