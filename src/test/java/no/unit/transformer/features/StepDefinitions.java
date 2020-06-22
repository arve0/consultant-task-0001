package no.unit.transformer.features;

import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import no.unit.transformer.Transformer;
import picocli.CommandLine;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StepDefinitions extends TestWiring {

    public static final String SINGLE_OBJECT_JSON = "single_object.json";
    public static final String OUTPUT_JSON = "output.json";

    @Given("^the user has an application \"Transformer\" that has a command line interface$")
    public void theUserHasAnApplicationThatHasACommandLineInterface() {
        new Transformer();
    }

    @And("\"Transformer\" has a flag \"--input\" that takes a single argument that is a filename")
    public void hasAFlagInputThatTakesASingleArgumentThatIsAFilename() {
        Transformer application = new Transformer();
        new CommandLine(application).parseArgs("--input", SINGLE_OBJECT_JSON);

        assertEquals(application.input, new File(SINGLE_OBJECT_JSON));
    }

    @And("\"Transformer\" has a flag \"--output\" that takes a single argument that is a filename")
    public void hasAFlagOutputThatTakesASingleArgumentThatIsAFilename() {
        Transformer application = new Transformer();
        new CommandLine(application).parseArgs("--output", OUTPUT_JSON);
        assertEquals(application.output, new File(OUTPUT_JSON));
    }

    @And("\"Transformer\" has a flag \"--input-format\" that takes a single argument \"xml\" or \"json\"")
    public void theTransformerHasAFlagInputFormatThatTakesASingleArgumentXmlOrJson() {
        Transformer application = new Transformer();
        new CommandLine(application).parseArgs("--input-format", "json");
        assertEquals(application.inputFormat, "json");

        application = new Transformer();
        new CommandLine(application).parseArgs("--input-format", "xml");
        assertEquals(application.inputFormat, "xml");
    }

    @And("\"Transformer\" has a flag \"--output-format\" that takes a single argument \"xml\" or \"json\"")
    public void theTransformerHasAFlagOutputFormatThatTakesASingleArgumentXmlOrJson() {
        Transformer application = new Transformer();
        new CommandLine(application).parseArgs("--output-format", "json");
        assertEquals(application.outputFormat, "json");

        application = new Transformer();
        new CommandLine(application).parseArgs("--output-format", "xml");
        assertEquals(application.outputFormat, "xml");
    }

    @Given("the user has a file {string}")
    public void theUserHasAFile(String filename) {
        File actual = getFileFromResources(filename);
        assertTrue(actual.exists());
    }

    @Given("the user has an input file that contains an array that contains a single object")
    public void theUserHasAnInputFileThatContainsAnArrayThatContainsASingleObject() {
        File actual = getFileFromResources(SINGLE_OBJECT_JSON);
        assertTrue(actual.exists());
    }

    @And("the object has field {string} with string value {string}")
    public void theObjectHasFieldWithStringValue(String field, String value) {
        throw new PendingException();
    }

    @When("the user transforms the data")
    public void theUserTransformsTheData() {
        throw new PendingException();
    }

    @Then("the user sees that the output file contains an array that contains a single object")
    public void theUserSeesThatTheOutputFileContainsAnArrayThatContainsASingleObject() {
        throw new PendingException();
    }

    @And("the object has a field {string} with an integer value {string}")
    public void theObjectHasAFieldWithAnIntegerValue(String field, String integer) {
        throw new PendingException();
    }

    @And("the object has a field {string}")
    public void theObjectHasAField(String field) {
        throw new PendingException();
    }

    @And("the field \"identity\" contains an object with the fields \"given\" and \"family\"")
    public void theFieldContainsAnObjectWithTheFieldsAnd() {
        throw new PendingException();
    }

    @And("the field {string} contains string value {string}")
    public void theFieldContainsStringValue(String field, String value) {
        throw new PendingException();
    }

    @And("the data is formatted correctly")
    public void theDataIsFormattedCorrectly() {
        throw new PendingException();
    }

    @When("the user transforms the file from (.*) to (.*)")
    public void theUserTransformsTheFileFromSerializationAToSerializationB(String serializationA,
                                                                           String serializatiionB) {
        throw new PendingException();
    }

    @And("they open the file")
    public void theyOpenTheFile() {
        throw new PendingException();
    }

    @Then("they see that the data is transformed to (.*)")
    public void theySeeThatTheDataIsTransformedToSerializationB(String serialization) {
        throw new PendingException();
    }

    @And("that the elements in \"users\" section of the file are ordered by element \"sequence\"")
    public void thatTheElementsInSectionOfTheFileAreOrderedByElement() {
        throw new PendingException();
    }

    @Given("the user has a (.*) in (.*)")
    public void theUserHasAFileInSerialization(String filename, String serialization) {
        throw new PendingException();
    }

    @When("they transform the file without specifying the output format flag")
    public void theyTransformTheFileWithoutSpecifyingTheOutputFormatFlag() {
        throw new PendingException();
    }

    @And("they open the transformed file")
    public void theyOpenTheTransformedFile() {
        throw new PendingException();
    }

    @Then("the file is transformed to serialization")
    public void theFileIsTransformedToSerialization() {
        throw new PendingException();
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
