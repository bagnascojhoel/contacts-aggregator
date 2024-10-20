package br.com.bagnascojhoel.contactsaggregator.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(name = "Contacts")
public interface ContactsApi {

    @Operation(summary = "Gets the contacts from all sources supported")
    List<ContactDto> getContacts();
}
