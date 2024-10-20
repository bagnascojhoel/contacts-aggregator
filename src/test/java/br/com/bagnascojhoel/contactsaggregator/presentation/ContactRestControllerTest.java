package br.com.bagnascojhoel.contactsaggregator.presentation;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

@Import({ContactsRestController.class})
public class ContactRestControllerTest extends AbstractRestControllerTest {


}
