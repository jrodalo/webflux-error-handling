package es.leanmind.errorhandling;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Tag;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureWebTestClient
@Tag("integration-test")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class IntegrationTest {
}
