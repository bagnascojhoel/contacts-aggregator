package br.com.bagnascojhoel.contactsaggregator.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.Instant;

@NoArgsConstructor(access = AccessLevel.NONE)
public final class ContactFixtures {
    public static Contact KENECT_LABS_DOUGLAS = Contact.builder()
            .id(1)
            .name("Douglas Fonseca")
            .email("douglas@domain.com")
            .contactSource(ContactSource.KENECT_LABS)
            .createdAt(Instant.parse("2024-01-01T00:00:00.00Z"))
            .updatedAt(Instant.parse("2024-01-01T05:00:00.00Z"))
            .build();

    public static Contact KENECT_LABS_ROBERT = Contact.builder()
            .id(4)
            .name("Robert Kusckesat")
            .email("robert@domain.com")
            .contactSource(ContactSource.KENECT_LABS)
            .createdAt(Instant.parse("2023-01-01T00:00:00.00Z"))
            .updatedAt(Instant.parse("2023-01-01T05:30:10.00Z"))
            .build();
}
