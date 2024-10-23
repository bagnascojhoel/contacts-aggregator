package br.com.bagnascojhoel.contactsaggregator.application;

import br.com.bagnascojhoel.contactsaggregator.domain.Contact;
import br.com.bagnascojhoel.contactsaggregator.domain.ContactRepository;
import br.com.bagnascojhoel.contactsaggregator.domain.ContactSource;
import br.com.bagnascojhoel.contactsaggregator.domain.KenectLabsContactsFindService;
import br.com.bagnascojhoel.contactsaggregator.domain.Page;
import br.com.bagnascojhoel.contactsaggregator.domain.PartnerException;
import io.github.resilience4j.timelimiter.TimeLimiter;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import io.github.resilience4j.timelimiter.TimeLimiterRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
@Slf4j
public class FetchContactsUseCase {

    private final KenectLabsContactsFindService kenectLabsContactsFindService;
    private final ContactRepository contactRepository;
    private final TimeLimiter timeLimiter;

    public FetchContactsUseCase(
            final KenectLabsContactsFindService kenectLabsContactsFindService,
            final ContactRepository contactRepository,
            @Qualifier("timeLimiterContacts") final TimeLimiter timeLimiter
    ) {
        this.kenectLabsContactsFindService = kenectLabsContactsFindService;
        this.contactRepository = contactRepository;
        this.timeLimiter = timeLimiter;
    }

    public List<Contact> execute() {
        List<Contact> kenectLabsContacts;
        try {
            kenectLabsContacts = timeLimiter.executeFutureSupplier(() ->
                    CompletableFuture.supplyAsync(this.kenectLabsContactsFindService::getContacts));
        } catch (Exception partnerException) {
            log.error("error while trying to fetch kenect-labs contacts", partnerException);
            kenectLabsContacts = contactRepository.findBySource(ContactSource.KENECT_LABS);
            if (kenectLabsContacts.isEmpty()) {
                log.error("there were not stored contacts from kenect-labs to use");
                throw new PartnerException();
            }
            return kenectLabsContacts;
        }

        contactRepository.saveAll(kenectLabsContacts);

        return kenectLabsContacts;
    }

}
