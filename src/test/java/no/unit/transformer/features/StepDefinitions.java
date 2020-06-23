package no.unit.transformer.features;

import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.messages.internal.com.google.common.base.CaseFormat;
import no.unit.transformer.Transformer;
import picocli.CommandLine;

import java.io.File;

import no.unit.transformer.FileTypes;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StepDefinitions extends TestWiring {

    public static final String SINGLE_OBJECT_JSON = "single_object.json";

    private Transformer transformer;
    private CommandLine application;

    @Given("^the user has an application \"Transformer\" that has a command line interface$")
    public void theUserHasAnApplicationThatHasACommandLineInterface() {
        transformer = new Transformer();
        application = new CommandLine(transformer);
    }

    @And("\"Transformer\" has a flag {string} that takes a single argument that is a filename")
    public void hasAFlagInputThatTakesASingleArgumentThatIsAFilename(String flag) throws NoSuchFieldException {
        assertTrue(applicationHasFlagAsOption(flag));
        assertEquals(File.class, getTransformerFlagType(flag));
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
