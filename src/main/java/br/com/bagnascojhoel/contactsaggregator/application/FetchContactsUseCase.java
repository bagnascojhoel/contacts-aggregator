package br.com.bagnascojhoel.contactsaggregator.application;

import br.com.bagnascojhoel.contactsaggregator.domain.Contact;
import br.com.bagnascojhoel.contactsaggregator.domain.KenectLabsContactService;
import br.com.bagnascojhoel.contactsaggregator.domain.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FetchContactsUseCase {

    private final KenectLabsContactService kenectLabsContactService;

    public List<Contact> execute() {
        Page<Contact> firstPage = kenectLabsContactService.getContactPage(0);
        List<Contact> allContacts = new ArrayList<>(firstPage.getTotalElements());
        allContacts.addAll(firstPage.getContent());

        int page = 1;
        int totalElements = firstPage.getTotalElements();
        Page<Contact> remainingPage;
        while (totalElements > allContacts.size()) {
            remainingPage = kenectLabsContactService.getContactPage(page++);
            allContacts.addAll(remainingPage.getContent());
        }
        return allContacts;
    }

}
