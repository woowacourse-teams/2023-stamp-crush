package com.stampcrush.backend.api.customer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stampcrush.backend.api.customer.request.TemporaryCustomerCreateRequest;
import com.stampcrush.backend.application.customer.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.when;

@WebMvcTest(CustomerApiController.class)
@AutoConfigureMockMvc
class CustomerApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CustomerService customerService;

    @Test
    void 임시_가입_고객을_생성한다() throws Exception {
        TemporaryCustomerCreateRequest request = new TemporaryCustomerCreateRequest("01012345678");
        Long customerId = 1L;

        when(customerService.createTemporaryCustomer(request.getPhoneNumber()))
                .thenReturn(customerId);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/temporary-customers")
                                .content(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().string("Location", "/customers/" + customerId));
    }
}
