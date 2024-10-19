package br.com.bagnascojhoel.contactsaggregator.application;

import br.com.bagnascojhoel.contactsaggregator.domain.Contact;
import br.com.bagnascojhoel.contactsaggregator.domain.ContactFixtures;
import br.com.bagnascojhoel.contactsaggregator.domain.KenectLabsContactService;
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
class FetchContactsUseCaseTest {

    @InjectMocks
    private FetchContactsUseCase fetchContactsUseCase;

    @Mock
    private KenectLabsContactService kenectLabsContactService;

    @Test
    void shouldFetchReturnAllKenectLabsContactsWhenThereIsASinglePage() {
        // arrange
        int totalElements = 2;
        var firstPage = new Page<Contact>(
                List.of(ContactFixtures.KENECT_LABS_DOUGLAS, ContactFixtures.KENECT_LABS_ROBERT),
                0,
                totalElements);

        Mockito.when(kenectLabsContactService.getContactPage(0)).thenReturn(firstPage);

        // act
        List<Contact> contacts = fetchContactsUseCase.execute();

        // assert
        Assertions.assertThat(contacts).containsExactlyInAnyOrder(
                ContactFixtures.KENECT_LABS_DOUGLAS,
                ContactFixtures.KENECT_LABS_ROBERT);
        Mockito.verify(kenectLabsContactService).getContactPage(0);
    }

    @Test
    void shouldFetchReturnAllKenectLabsContactsWhenThereAreMultiplePages() {
        // arrange
        int totalElements = 500;
        var firstPage = new Page<Contact>(List.of(Mockito.mock(Contact.class)), 0, totalElements);

        Mockito.when(kenectLabsContactService.getContactPage(0)).thenReturn(firstPage);
        Mockito.when(kenectLabsContactService.getContactPage(Mockito.anyInt()))
                .thenAnswer((invocation) -> new Page<>(List.of(
                        Mockito.mock(Contact.class)),
                        invocation.getArgument(0),
                        totalElements));

        // act
        List<Contact> contacts = fetchContactsUseCase.execute();

        // assert
        Assertions.assertThat(contacts).hasSize(totalElements);
    }

}