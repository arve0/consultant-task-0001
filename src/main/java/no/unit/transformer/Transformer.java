package no.unit.transformer;

import picocli.CommandLine;

import java.io.File;

public class Transformer {
    @CommandLine.Option(names = { "--input" }, paramLabel = "INPUT", description = "the input file")
    public File input;

    @CommandLine.Option(names = { "--output" }, paramLabel = "OUTPUT", description = "the output file")
    public File output;

    @CommandLine.Option(names = { "--input-format" }, paramLabel = "INPUT FORMAT", description = "the input-format")
    public FileTypes inputFormat;

    @CommandLine.Option(names = { "--output-format" }, paramLabel = "OUTPUT FORMAT", description = "the output-format")
    public FileTypes outputFormat;
}
