package no.unit.transformer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import picocli.CommandLine;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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

    private boolean inputDoesHaveRootObject = true;
    private String errorMessage = "";

    public void transform() throws IOException {
        try {
            List<User> users = getInputUsers()
                    .stream()
                    .map(User::new)
                    .sorted()
                    .collect(Collectors.toList());

            BufferedWriter outputBuffer = Files.newBufferedWriter(output);

            if (outputFormat == null) {
                outputFormat = inputFormat;
            }

            ObjectMapper outputMapper = outputFormat.equals(FileTypes.xml)
                    ? new XmlMapper()
                    : new ObjectMapper();

            if (inputDoesHaveRootObject) {
                outputMapper.writeValue(outputBuffer, new Users(users));
            } else {
                outputMapper.writeValue(outputBuffer, users);
            }
        } catch (MismatchedInputException error) {
            errorMessage = "Unable to transform input data, unexpected format.";
        } catch (Exception error) {
            errorMessage = "Unexpected error: " + error.getMessage();
        }
    }

    private List<InputUser> getInputUsers() throws IOException {
        String content = Files.readString(input);

        if (inputFormat.equals(FileTypes.xml)) {
            InputUsers inputUsers = new XmlMapper().readValue(content, InputUsers.class);
            return inputUsers.users;
        }

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode root = objectMapper.readValue(content, JsonNode.class);

        inputDoesHaveRootObject = root.has("users");
        JsonNode objectWithUsers = inputDoesHaveRootObject
                ? root.get("users")
                : root;

        return Arrays.asList(objectMapper.treeToValue(objectWithUsers, InputUser[].class));
    }

    public Optional<String> getErrorMessage() {
        if (errorMessage.equals("")) {
            return Optional.empty();
        } else {
            return Optional.of(errorMessage);
        }
    }
}
