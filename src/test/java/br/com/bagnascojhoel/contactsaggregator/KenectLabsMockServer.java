package br.com.bagnascojhoel.contactsaggregator;

import lombok.Getter;
import org.mockserver.client.ForwardChainExpectation;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.RequestDefinition;

public class KenectLabsMockServer {

    @Getter
    private ClientAndServer clientAndServer;

    public String getHost() {
        return "http://localhost:1080";
    }

    public void setupServer() {
        this.clientAndServer = ClientAndServer.startClientAndServer(1080);
    }

    public void stopServer() {
        this.clientAndServer.stop();
    }

    public ForwardChainExpectation when(RequestDefinition requestDefinition) {
        return this.clientAndServer.when(requestDefinition);
    }

}
