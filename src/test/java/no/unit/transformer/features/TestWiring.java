package no.unit.transformer.features;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TestWiring {
    protected Path getFileFromResources(String filename) throws URISyntaxException {
        return Paths.get(ClassLoader.getSystemResource(filename).toURI());
    }
}
