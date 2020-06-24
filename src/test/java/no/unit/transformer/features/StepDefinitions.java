package no.unit.transformer.features;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import no.unit.transformer.FileTypes;
import no.unit.transformer.InputUsers;
import no.unit.transformer.Transformer;
import no.unit.transformer.User;
import no.unit.transformer.Users;
import picocli.CommandLine;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
        assertTrue(hasFlagAsOption(application, flag));
        assertEquals(Path.class, getFlagType(transformer, flag));
    }

    @And("\"Transformer\" has a flag {string} that takes a single argument \"xml\" or \"json\"")
    public void theTransformerHasAFlagInputFormatThatTakesASingleArgumentXmlOrJson(String flag)
            throws NoSuchFieldException {
        assertTrue(hasFlagAsOption(application, flag));
        assertEquals(FileTypes.class, getFlagType(transformer, flag));
    }

    @Given("the user has a file {string} in {string}")
    public void theUserHasAFileInSerialization(String filename, String serialization) throws URISyntaxException {
        inputFile = getFileFromResources(filename);
        inputSerialization = serialization;
    }

    @Given("the user has an input file that contains an array that contains a single object")
    public void theUserHasAnInputFileThatContainsAnArrayThatContainsASingleObject()
            throws URISyntaxException, IOException {
        inputFile = getFileFromResources(SINGLE_OBJECT_JSON);
        objectUnderAssertion = getOnlyObjectInArray(readObjectFromFile(inputFile));
    }

    @And("the object has field {string} with string value {string}")
    public void theObjectHasFieldWithStringValue(String field, String value) {
        assertEquals(value, getString(objectUnderAssertion, field));
    }

    @When("the user transforms the data")
    public void theUserTransformsTheData() throws IOException {
        outputFile = getTempFileWithoutCreatingEmptyFile();
        application.execute(
                "--input", inputFile.toString(),
                "--output", outputFile.toString(),
                "--input-format", "json",
                "--output-format", "json"
        );
    }

    @Then("the user sees that the output file contains an array that contains a single object")
    public void theUserSeesThatTheOutputFileContainsAnArrayThatContainsASingleObject() throws IOException {
        objectUnderAssertion = getOnlyObjectInArray(readObjectFromFile(outputFile));
    }

    @And("the object has a field {string} with an integer value {int}")
    public void theObjectHasAFieldWithAnIntegerValue(String field, int value) {
        assertEquals(value, getInt(objectUnderAssertion, field));
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
    public void theUserTransformsTheFileFromSerializationAToSerializationB(
            String inputSerialization,
            String outputSerialization) throws IOException {
        outputFile = getTempFileWithoutCreatingEmptyFile();
        application.execute(
                "--input", inputFile.toString(),
                "--output", outputFile.toString(),
                "--input-format", inputSerialization,
                "--output-format", outputSerialization
        );
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
        application.execute(
                "--input", inputFile.toString(),
                "--output", outputFile.toString(),
                "--input-format", inputSerialization
        );
    }

    @Then("the file is transformed with same serialization")
    public void theFileIsTransformedWithSameSerialization() throws JsonProcessingException {
        theySeeThatTheDataIsTransformedToSerialization(inputSerialization);
    }

    @And("the data is formatted badly")
    public void theDataIsFormattedBadly() {
        assertThrows(UnrecognizedPropertyException.class, () -> {
            theDataIsFormattedCorrectly();
        });
    }

    @When("the user attempts to transform the file")
    public void theUserAttemptsToTransformTheFile() throws IOException {
        theyTransformTheFileWithoutSpecifyingTheOutputFormatFlag();
    }

    @Then("they see an error message telling them that the input file is badly formatted")
    public void theySeeAnErrorMessageTellingThemThatTheInputFileIsBadlyFormatted() throws Exception {
        Optional<String> errorMessage = transformer.getErrorMessage();
        assertTrue(errorMessage.isPresent());

        String error = errorMessage.get();
        assertTrue(error.contains("unexpected format"));
    }
}
