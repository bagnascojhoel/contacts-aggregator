package br.com.bagnascojhoel.contactsaggregator.application;

import br.com.bagnascojhoel.contactsaggregator.domain.Contact;
import br.com.bagnascojhoel.contactsaggregator.domain.ContactFixtures;
import br.com.bagnascojhoel.contactsaggregator.domain.ContactRepository;
import br.com.bagnascojhoel.contactsaggregator.domain.KenectLabsContactsFindService;
import br.com.bagnascojhoel.contactsaggregator.domain.Page;
import io.github.resilience4j.timelimiter.TimeLimiter;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class FetchContactsUseCaseTest {

    @InjectMocks
    private FetchContactsUseCase fetchContactsUseCase;

    @Mock
    private KenectLabsContactsFindService kenectLabsContactsFindService;

    @Mock
    private ContactRepository contactRepository;

    @Spy
    private TimeLimiter timeLimiter = TimeLimiter.ofDefaults();

    @Test
    void shouldReturnAllKenectLabsContactsWhenServiceReturnsThem() {
        // arrange
        List<Contact> kenectContacts = List.of(
                ContactFixtures.KENECT_LABS_DOUGLAS,
                ContactFixtures.KENECT_LABS_ROBERT);

        Mockito.when(kenectLabsContactsFindService.getContacts()).thenReturn(kenectContacts);

        // act
        List<Contact> contacts = fetchContactsUseCase.execute();

        // assert
        Assertions.assertThat(contacts).containsExactlyInAnyOrder(
                ContactFixtures.KENECT_LABS_DOUGLAS,
                ContactFixtures.KENECT_LABS_ROBERT);
        Mockito.verify(kenectLabsContactsFindService).getContacts();
    }

    @Test
    void shouldSaveAllKenectLabsContactsWhenServiceReturnsThemSuccessfully() {
        // arrange
        List<Contact> kenectContacts = List.of(
                ContactFixtures.KENECT_LABS_DOUGLAS,
                ContactFixtures.KENECT_LABS_ROBERT);

        Mockito.when(kenectLabsContactsFindService.getContacts()).thenReturn(kenectContacts);

        // act
        fetchContactsUseCase.execute();

        // assert
        Mockito.verify(contactRepository).saveAll(kenectContacts);
    }

    @Test
    void shouldReturnStoredKenectLabsContactsWhenServiceFails() {
        // TODO: implement this scenario
    }

    @Test
    void shouldThrowWhenServiceFailsAndThereAreNoFallbackContactsStored() {
        // TODO: implement this scenario
    }

    @Test
    void shouldDropWaitingForTheServiceResponseWhenWaitingTimeHasRanOut() {
        // TODO: implement this scenario
    }

}