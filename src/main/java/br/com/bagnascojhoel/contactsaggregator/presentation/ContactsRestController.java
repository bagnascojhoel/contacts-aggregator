package br.com.bagnascojhoel.contactsaggregator.presentation;

import br.com.bagnascojhoel.contactsaggregator.application.FetchContactsUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/contacts")
@RequiredArgsConstructor
public class ContactsRestController implements ContactsApi {

    private final FetchContactsUseCase fetchContactsUseCase;
    private final ContactRestFactory contactRestFactory;

    @Override
    @GetMapping
    public List<ContactDto> getContacts() {
        // can maybe use parallel stream to speed it up
        return fetchContactsUseCase.execute().stream()
                .map(contactRestFactory::create)
                .toList();
    }
}
