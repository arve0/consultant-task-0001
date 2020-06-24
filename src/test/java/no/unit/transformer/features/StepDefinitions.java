package no.unit.transformer.features;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.messages.internal.com.google.common.base.CaseFormat;
import no.unit.transformer.FileTypes;
import no.unit.transformer.InputUser;
import no.unit.transformer.InputUsers;
import no.unit.transformer.Transformer;
import no.unit.transformer.User;
import no.unit.transformer.Users;
import picocli.CommandLine;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StepDefinitions extends TestWiring {

    public static final String SINGLE_OBJECT_JSON = "single_object.json";

    private Transformer transformer;
    private CommandLine application;
    private Path inputFile;
    private Path outputFile;
    private JsonNode objectUnderAssertion;
    private String outputContent;
    private Users deserializedUsers;
    private String inputSerialization = "json";

    @Given("^the user has an application \"Transformer\" that has a command line interface$")
    public void theUserHasAnApplicationThatHasACommandLineInterface() {
        transformer = new Transformer();
        application = new CommandLine(transformer);
    }

    @And("\"Transformer\" has a flag {string} that takes a single argument that is a filename")
    public void hasAFlagInputThatTakesASingleArgumentThatIsAFilename(String flag) throws NoSuchFieldException {
        assertTrue(applicationHasFlagAsOption(flag));
        assertEquals(Path.class, getTransformerFlagType(flag));
    }

    private boolean applicationHasFlagAsOption(String flag) {
        return application.getCommandSpec().findOption(flag).isOption();
    }

    private Class<?> getTransformerFlagType(String flag) throws NoSuchFieldException {
        assertTrue(flag.startsWith("--"));
        String fieldName = CaseFormat.LOWER_HYPHEN.to(CaseFormat.LOWER_CAMEL, flag.substring(2));
        return transformer.getClass().getField(fieldName).getType();
    }

    @And("\"Transformer\" has a flag {string} that takes a single argument \"xml\" or \"json\"")
    public void theTransformerHasAFlagInputFormatThatTakesASingleArgumentXmlOrJson(String flag) throws NoSuchFieldException {
        assertTrue(applicationHasFlagAsOption(flag));
        assertEquals(FileTypes.class, getTransformerFlagType(flag));
    }

    @Given("the user has a file {string} in {string}")
    public void theUserHasAFileInSerialization(String filename, String serialization) throws URISyntaxException {
        inputFile = getFileFromResources(filename);
        inputSerialization = serialization;
    }

    @Given("the user has an input file that contains an array that contains a single object")
    public void theUserHasAnInputFileThatContainsAnArrayThatContainsASingleObject() throws URISyntaxException, IOException {
        inputFile = getFileFromResources(SINGLE_OBJECT_JSON);
        objectUnderAssertion = getOnlyObjectInArray(readObjectFromFile(inputFile));
    }

    private JsonNode readObjectFromFile(Path file) throws IOException {
        assertTrue(Files.exists(file));
        String content = Files.readString(file);
        return new ObjectMapper().readValue(content, JsonNode.class);
    }

    private JsonNode getOnlyObjectInArray(JsonNode array) {
        assertTrue(array.isArray());
        assertEquals(1, array.size());
        return array.get(0);
    }

    @And("the object has field {string} with string value {string}")
    public void theObjectHasFieldWithStringValue(String field, String value) {
        assertEquals(value, getString(objectUnderAssertion, field));
    }

    private Object getString(JsonNode object, String field) {
        assertTrue(object.has(field));
        JsonNode value = object.get(field);
        assertTrue(value.isTextual());
        return value.asText();
    }

    @When("the user transforms the data")
    public void theUserTransformsTheData() throws IOException {
        outputFile = getTempFileWithoutCreatingEmptyFile();
        application.parseArgs(
                "--input", inputFile.toString(),
                "--output", outputFile.toString(),
                "--input-format", "json",
                "--output-format", "json"
        );
        transformer.transform();
    }

    private Path getTempFileWithoutCreatingEmptyFile() throws IOException {
        Path outputDirectory = Files.createTempDirectory("transformer-output");
        return outputDirectory.resolve("output.json");
    }

    @Then("the user sees that the output file contains an array that contains a single object")
    public void theUserSeesThatTheOutputFileContainsAnArrayThatContainsASingleObject() throws IOException {
        objectUnderAssertion = getOnlyObjectInArray(readObjectFromFile(outputFile));
    }

    @And("the object has a field {string} with an integer value {int}")
    public void theObjectHasAFieldWithAnIntegerValue(String field, int value) {
        assertEquals(value, getInt(objectUnderAssertion, field));
    }

    private int getInt(JsonNode object, String field) {
        assertTrue(object.has(field));
        JsonNode value = object.get(field);
        assertTrue(value.isInt());
        return value.asInt();
    }

    @And("the object has a field {string}")
    public void theObjectHasAField(String field) {
        assertTrue(objectUnderAssertion.has(field));
    }

    @And("the field \"identity\" contains an object with the fields \"given\" and \"family\"")
    public void theFieldContainsAnObjectWithTheFieldsAnd() {
        objectUnderAssertion = objectUnderAssertion.get("identity");
        assertTrue(objectUnderAssertion.has("given"));
        assertTrue(objectUnderAssertion.has("family"));
    }

    @And("the field {string} contains string value {string}")
    public void theFieldContainsStringValue(String field, String value) {
        assertEquals(value, getString(objectUnderAssertion, field));
    }

    @And("the data is formatted correctly")
    public void theDataIsFormattedCorrectly() throws IOException {
        ObjectMapper objectMapper = inputFile.toString().endsWith(".xml")
                ? new XmlMapper()
                : new ObjectMapper();
        objectMapper.readValue(Files.readString(inputFile), InputUsers.class);
    }

    @When("the user transforms the file from {string} to {string}")
    public void theUserTransformsTheFileFromSerializationAToSerializationB(String inputSerialization,
                                                                           String outputSerialization) throws IOException {
        outputFile = getTempFileWithoutCreatingEmptyFile();
        application.parseArgs(
                "--input", inputFile.toString(),
                "--output", outputFile.toString(),
                "--input-format", inputSerialization,
                "--output-format", outputSerialization
        );
        transformer.transform();
    }

    @And("they open the file")
    public void theyOpenTheFile() throws IOException {
        outputContent = Files.readString(outputFile);
    }

    @Then("they see that the data is transformed to {string}")
    public void theySeeThatTheDataIsTransformedToSerialization(String serialization) throws JsonProcessingException {
        String expected = serialization.equals("xml")
            ? "<users><user>"
            : "{\"users\":[";
        assertEquals(expected, outputContent.substring(0, expected.length()));

        deserializedUsers = serialization.equals("xml")
                ? new XmlMapper().readValue(outputContent, Users.class)
                : new ObjectMapper().readValue(outputContent, Users.class);
    }

    @And("that the elements in \"users\" section of the file are ordered by element \"sequence\"")
    public void thatTheElementsInSectionOfTheFileAreOrderedByElement() {
        Integer prev = Integer.MIN_VALUE;
        for (User user : deserializedUsers) {
            assertTrue(user.id > prev);
            prev = user.id;
        }
    }

    @When("they transform the file without specifying the output format flag")
    public void theyTransformTheFileWithoutSpecifyingTheOutputFormatFlag() throws IOException {
        outputFile = getTempFileWithoutCreatingEmptyFile();
        application.parseArgs(
                "--input", inputFile.toString(),
                "--output", outputFile.toString(),
                "--input-format", inputSerialization
        );
        transformer.transform();
    }

    @Then("the file is transformed with same serialization")
    public void theFileIsTransformedWithSameSerialization() throws JsonProcessingException {
        theySeeThatTheDataIsTransformedToSerialization(inputSerialization);
    }

    @And("the data is formatted badly")
    public void theDataIsFormattedBadly() {
        throw new PendingException();
    }

    @When("the user attempts to transform the file")
    public void theUserAttemptsToTransformTheFile() {
        throw new PendingException();
    }

    @Then("they see an error message telling them that the input file is badly formatted")
    public void theySeeAnErrorMessageTellingThemThatTheInputFileIsBadlyFormatted() {
        throw new PendingException();
    }
}
