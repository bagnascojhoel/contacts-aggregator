package br.com.bagnascojhoel.contactsaggregator.infrastructure.rest.kenectlabs;

import br.com.bagnascojhoel.contactsaggregator.domain.Contact;
import br.com.bagnascojhoel.contactsaggregator.domain.ContactSource;
import br.com.bagnascojhoel.contactsaggregator.domain.Page;
import br.com.bagnascojhoel.contactsaggregator.domain.PartnerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class ContactFactory {
    public Page<Contact> create(ResponseEntity<ContactsResponseDto> response) {
        HttpHeaders headers = response.getHeaders();
        ContactsResponseDto body = response.getBody();
        if (body == null) {
            log.error("kenect labs API returned no body");
            throw new PartnerException();
        }

        if (headers.getFirst("current-page") == null) {
            log.error("kenect labs API did not return 'Current-Page' header");
            throw new PartnerException();
        }

        if (headers.getFirst("total-count") == null) {
            log.error("kenect labs API did not return 'Total-Count' header");
            throw new PartnerException();
        }

        return new Page<>(
                this.mapList(body.getContacts()),
                Integer.parseInt(headers.getFirst("current-page")),
                Integer.parseInt(headers.getFirst("total-count")));
    }

    private List<Contact> mapList(List<ContactsResponseDto.Contact> contactDtos) {
        return contactDtos.stream()
                .map(dto -> Contact.builder()
                        .id(dto.getId())
                        .name(dto.getName())
                        .email(dto.getEmail())
                        .contactSource(ContactSource.KENECT_LABS)
                        .createdAt(dto.getCreatedAt())
                        .updatedAt(dto.getUpdatedAt())
                        .build())
                .toList();

    }
}
