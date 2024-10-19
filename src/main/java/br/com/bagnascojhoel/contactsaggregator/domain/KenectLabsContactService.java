package br.com.bagnascojhoel.contactsaggregator.domain;

public interface KenectLabsContactService {
    Page<Contact> getContactPage(int page);
}
