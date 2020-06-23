package no.unit.transformer;

import com.fasterxml.jackson.databind.ObjectMapper;
import picocli.CommandLine;

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
        List<OutputPerson> persons = Arrays.asList(objectMapper.readValue(json, InputPerson[].class))
                .stream()
                .map(OutputPerson::new)
                .collect(Collectors.toList());

        objectMapper.writeValue(Files.newBufferedWriter(output), persons);
    }
}
