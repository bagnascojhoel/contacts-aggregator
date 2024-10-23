package br.com.bagnascojhoel.contactsaggregator.infrastructure.rest.kenectlabs;

import br.com.bagnascojhoel.contactsaggregator.domain.Contact;
import br.com.bagnascojhoel.contactsaggregator.domain.KenectLabsContactsFindService;
import br.com.bagnascojhoel.contactsaggregator.domain.Page;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
class RestKenectLabsContactsFindService implements KenectLabsContactsFindService {
    private static final int KENECT_LABS_FIRST_PAGE = 1;

    private final KenectLabsRestClient kenectLabsRestClient;

    @Override
    public List<Contact> getContacts() {
        Page<Contact> firstPage = kenectLabsRestClient.getContactsPage(KENECT_LABS_FIRST_PAGE);
        List<Contact> allContacts = new ArrayList<>(firstPage.getTotalElements());
        allContacts.addAll(firstPage.getContent());

        int page = KENECT_LABS_FIRST_PAGE + 1;
        int totalElements = firstPage.getTotalElements();
        Page<Contact> remainingPage;
        while (totalElements > allContacts.size()) {
            remainingPage = kenectLabsRestClient.getContactsPage(page++);
            allContacts.addAll(remainingPage.getContent());
        }
        return allContacts;
    }
}
