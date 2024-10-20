package br.com.bagnascojhoel.contactsaggregator.infrastructure.rest.kenectlabs;

import br.com.bagnascojhoel.contactsaggregator.domain.Contact;
import br.com.bagnascojhoel.contactsaggregator.domain.KenectLabsContactService;
import br.com.bagnascojhoel.contactsaggregator.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class RestKenectLabsContactService implements KenectLabsContactService {
    private static final String CONTACTS = "/api/v1/contacts";

    private final RestClient restClient;
    private final ContactFactory contactFactory;

    public RestKenectLabsContactService(
            @Value("${rest-integrations.kenect-labs-api.base-url}") final String baseUrl,
            @Value("${rest-integrations.kenect-labs-api.token}") final String token,
            @Autowired final ContactFactory contactFactory
    ) {
        this.restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("authorization", "Bearer " .concat(token))
                .defaultStatusHandler(new KenectLabsResponseErrorHandler())
                .build();
        this.contactFactory = contactFactory;
    }

    @Override
    public Page<Contact> getContactPage(int page) {
        String uri = UriComponentsBuilder.fromUriString(CONTACTS)
                .queryParam("page", page)
                .build()
                .toUriString();
        ResponseEntity<ContactsResponseDto> response = restClient.get()
                .uri(uri)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(ContactsResponseDto.class);
        return contactFactory.create(response);
    }
}