package br.com.bagnascojhoel.contactsaggregator.infrastructure.rest.kenectlabs;

import br.com.bagnascojhoel.contactsaggregator.ContactsAggregatorApplication;
import br.com.bagnascojhoel.contactsaggregator.KenectLabsMockServer;
import br.com.bagnascojhoel.contactsaggregator.domain.Contact;
import br.com.bagnascojhoel.contactsaggregator.domain.Page;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockserver.model.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static br.com.bagnascojhoel.contactsaggregator.domain.ContactFixtures.KENECT_LABS_DOUGLAS;
import static br.com.bagnascojhoel.contactsaggregator.domain.ContactFixtures.KENECT_LABS_ROBERT;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

// ideally this would only load the beans required for this layer
@SpringBootTest(
        classes = ContactsAggregatorApplication.class,
        properties = {
                "rest-integrations.kenect-labs-api.base-url=http://localhost:1080",
                "rest-integrations.kenect-labs-api.token=local"
        }
)
class KenectLabsRestClientTest {

    private final KenectLabsMockServer kenectLabsMockServer = new KenectLabsMockServer();

    private final ContactFactory contactFactory = new ContactFactory();

    private final KenectLabsRestClient restKenectLabsContactService = new KenectLabsRestClient(
            kenectLabsMockServer.getHost(),
            "local",
            contactFactory);

    @Autowired
    private ResourceLoader resourceLoader;

    @BeforeEach
    void setup() {
        this.kenectLabsMockServer.setupServer();
    }

    @AfterEach
    void tearDown() {
        this.kenectLabsMockServer.stopServer();
    }

    public String loadJson(final String filePath) {
        try {
            return new String(
                    resourceLoader.getResource("classpath:" + filePath).getInputStream().readAllBytes(),
                    StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Nested
    class GetContactPage {
        @Test
        void shouldBuildContactPage_whenServerRespondsWith200() {
            // arrange
            kenectLabsMockServer.when(request()
                    .withMethod("GET")
                    .withHeader("authorization", "Bearer local")
            ).respond(response()
                    .withContentType(MediaType.APPLICATION_JSON)
                    .withHeader("current-page", "0")
                    .withHeader("total-count", "2")
                    .withBody(loadJson("rest/kenect-labs/contacts-response.json")));

            // act
            Page<Contact> page = restKenectLabsContactService.getContactsPage(0);

            // assert
            Assertions.assertThat(page.getContent())
                    .usingRecursiveFieldByFieldElementComparator()
                    .containsExactlyInAnyOrder(KENECT_LABS_DOUGLAS, KENECT_LABS_ROBERT);
        }

        @Test
        void shouldUseCachedResponse_whenServerRespondsWith5xx() {
            // TODO: implement this scenarios
        }

        @Test
        void whenRequestTimeouts() {
            // TODO: implement this scenarios
        }
    }

}