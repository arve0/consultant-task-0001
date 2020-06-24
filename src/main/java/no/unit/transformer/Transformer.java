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
import java.util.concurrent.Callable;
import java.util.stream.Collectors;


@SuppressWarnings("PMD.DoNotCallSystemExit")
public class Transformer implements Callable<Integer> {
    @CommandLine.Option(names = { "--input" }, paramLabel = "INPUT", description = "the input file", required = true)
    public Path input;

    @CommandLine.Option(names = { "--output" }, paramLabel = "OUTPUT", description = "the output file", required = true)
    public Path output;

    @CommandLine.Option(names = { "--input-format" }, paramLabel = "INPUT FORMAT", description = "the input-format", required = true)
    public FileTypes inputFormat;

    @CommandLine.Option(names = { "--output-format" }, paramLabel = "OUTPUT FORMAT", description = "the output-format")
    public FileTypes outputFormat;

    private boolean inputDoesHaveRootObject = true;
    private String errorMessage = "";

    public static void main(String... args) {
        int exitCode = new CommandLine(new Transformer()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public Integer call() throws IOException {
        BufferedWriter outputBuffer = null;
        try {
            List<OutputUser> users = getInputUsers()
                    .stream()
                    .map(OutputUser::new)
                    .sorted()
                    .collect(Collectors.toList());

            outputBuffer = Files.newBufferedWriter(output);

            if (outputFormat == null) {
                outputFormat = inputFormat;
            }

            ObjectMapper outputMapper = outputFormat.equals(FileTypes.xml)
                    ? new XmlMapper()
                    : new ObjectMapper();

            if (inputDoesHaveRootObject) {
                outputMapper.writeValue(outputBuffer, new OutputUsers(users));
            } else {
                outputMapper.writeValue(outputBuffer, users);
            }
            return 0;
        } catch (MismatchedInputException error) {
            errorMessage = "Unable to transform input data, unexpected format.";
            return 1;
        } catch (Exception error) {
            errorMessage = "Unexpected error: " + error.getMessage();
            return 2;
        } finally {
            if (outputBuffer != null) {
                outputBuffer.close();
            }
        }
    }

    private List<InputUser> getInputUsers() throws Exception {
        String content;
        try {
            content = Files.readString(input);
        } catch (IOException error) {
            throw new Exception(String.format("Unable to open file '%s'", input), error);
        }

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
        if ("".equals(errorMessage)) {
            return Optional.empty();
        } else {
            return Optional.of(errorMessage);
        }
    }
}
