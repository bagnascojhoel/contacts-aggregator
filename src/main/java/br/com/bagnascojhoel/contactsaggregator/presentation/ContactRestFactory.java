package br.com.bagnascojhoel.contactsaggregator.presentation;

import br.com.bagnascojhoel.contactsaggregator.domain.Contact;
import org.springframework.stereotype.Component;

@Component
public class ContactRestFactory {
    public ContactDto create(Contact contact) {
        return ContactDto.builder()
                .id(contact.getId())
                .name(contact.getName())
                .email(contact.getEmail())
                .source(contact.getContactSource())
                .createdAt(contact.getCreatedAt())
                .updatedAt(contact.getUpdatedAt())
                .build();
    }
}
