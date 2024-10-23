package br.com.bagnascojhoel.contactsaggregator.infrastructure.rest.kenectlabs;

import br.com.bagnascojhoel.contactsaggregator.domain.PartnerException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@AllArgsConstructor
public class KenectLabsException extends PartnerException {
    private final HttpStatusCode httpStatus;
}
