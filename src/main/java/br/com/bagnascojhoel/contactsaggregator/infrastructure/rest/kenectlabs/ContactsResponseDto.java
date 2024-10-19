package br.com.bagnascojhoel.contactsaggregator.infrastructure.rest.kenectlabs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
public class ContactsResponseDto {
    private List<Contact> contacts;

    @Data
    public static class Contact {
        private int id;
        private String name;
        private String email;
        @JsonProperty("created_at")
        private Instant createdAt;
        @JsonProperty("updated_at")
        private Instant updatedAt;
    }
}
