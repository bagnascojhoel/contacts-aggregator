package br.com.bagnascojhoel.contactsaggregator.infrastructure.rest.kenectlabs;

import br.com.bagnascojhoel.contactsaggregator.domain.Contact;
import br.com.bagnascojhoel.contactsaggregator.domain.ContactFixtures;
import br.com.bagnascojhoel.contactsaggregator.domain.Page;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class RestKenectLabsContactsFindServiceTest {
    @InjectMocks
    private RestKenectLabsContactsFindService restKenectLabsContactsFindService;

    @Mock
    private KenectLabsRestClient kenectLabsRestClient;

    @Test
    void shouldFetchReturnAllKenectLabsContactsWhenThereIsASinglePage() {
        // arrange
        int totalElements = 2;
        var firstPage = new Page<Contact>(
                List.of(ContactFixtures.KENECT_LABS_DOUGLAS, ContactFixtures.KENECT_LABS_ROBERT),
                0,
                totalElements);

        Mockito.when(kenectLabsRestClient.getContactsPage(1)).thenReturn(firstPage);

        // act
        List<Contact> contacts = restKenectLabsContactsFindService.getContacts();

        // assert
        Assertions.assertThat(contacts).containsExactlyInAnyOrder(
                ContactFixtures.KENECT_LABS_DOUGLAS,
                ContactFixtures.KENECT_LABS_ROBERT);
        Mockito.verify(kenectLabsRestClient).getContactsPage(1);
    }

    @Test
    void shouldFetchReturnAllKenectLabsContactsWhenThereAreMultiplePages() {
        // arrange
        int totalElements = 500;
        var firstPage = new Page<Contact>(List.of(Mockito.mock(Contact.class)), 0, totalElements);

        Mockito.when(kenectLabsRestClient.getContactsPage(0)).thenReturn(firstPage);
        Mockito.when(kenectLabsRestClient.getContactsPage(Mockito.anyInt()))
                .thenAnswer((invocation) -> new Page<>(List.of(
                        Mockito.mock(Contact.class)),
                        invocation.getArgument(0),
                        totalElements));

        // act
        List<Contact> contacts = restKenectLabsContactsFindService.getContacts();

        // assert
        Assertions.assertThat(contacts).hasSize(totalElements);
    }
}
