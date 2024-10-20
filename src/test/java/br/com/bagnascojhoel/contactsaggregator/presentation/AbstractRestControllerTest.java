package br.com.bagnascojhoel.contactsaggregator.presentation;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest()
class AbstractRestControllerTest {

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setupRestAssured() {
        RestAssuredMockMvc.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

}