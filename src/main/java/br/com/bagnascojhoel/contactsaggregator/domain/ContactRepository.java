package br.com.bagnascojhoel.contactsaggregator.domain;

import java.util.List;
import java.util.Optional;

public interface ContactRepository {
    void saveAll(List<Contact> contacts);

    List<Contact> findBySource(ContactSource contactSource);
}
