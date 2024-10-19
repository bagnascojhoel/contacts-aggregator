package br.com.bagnascojhoel.contactsaggregator.domain;

public class BaseException extends RuntimeException {
    public BaseException(final String code) {
        super(code);
    }
}
