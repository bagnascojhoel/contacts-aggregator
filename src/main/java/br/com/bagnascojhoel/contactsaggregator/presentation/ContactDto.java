package br.com.bagnascojhoel.contactsaggregator.presentation;

import br.com.bagnascojhoel.contactsaggregator.domain.ContactSource;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@NoArgsConstructor
@Setter
@Getter
@Builder
@AllArgsConstructor
public class ContactDto {
    private Integer id;
    private String name;
    private String email;
    private ContactSource source;
    private Instant createdAt;
    private Instant updatedAt;
}
