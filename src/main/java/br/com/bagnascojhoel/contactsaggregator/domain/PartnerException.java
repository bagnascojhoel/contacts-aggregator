package br.com.bagnascojhoel.contactsaggregator.domain;

public class PartnerException extends BaseException {
    // ideally this would come from message properties
    public PartnerException() {
        super("our partner is not available right now");
    }
}
