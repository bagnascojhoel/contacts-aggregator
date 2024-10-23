package br.com.bagnascojhoel.contactsaggregator.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.time.Instant;


@Getter
@Builder
@ToString
public class Contact implements Serializable {
    private final Integer id;
    private final String name;
    private final String email;
    private final ContactSource contactSource;
    private final Instant createdAt;
    private final Instant updatedAt;

    public Contact(
            Integer id,
            String name,
            String email,
            ContactSource contactSource,
            Instant createdAt,
            Instant updatedAt
    ) {
        Assert.hasLength(name, "name cannot be empty");
        Assert.hasLength(email, "email cannot be empty");
        Assert.notNull(id, "id is required");
        Assert.notNull(contactSource, "contactSource is required");
        Assert.notNull(createdAt, "createdAt is required");
        Assert.notNull(updatedAt, "updatedAt is required");
        this.id = id;
        this.name = name;
        this.email = email;
        this.contactSource = contactSource;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

}
