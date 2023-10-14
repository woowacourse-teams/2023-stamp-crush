package com.stampcrush.backend.api.visitor.profile;

import com.stampcrush.backend.api.ControllerSliceTest;
import com.stampcrush.backend.application.visitor.profile.dto.VisitorProfileFindByPhoneNumberResultDto;
import com.stampcrush.backend.entity.user.Customer;
import com.stampcrush.backend.fixture.CustomerFixture;
import org.junit.jupiter.api.Test;

import static com.stampcrush.backend.entity.user.CustomerType.TEMPORARY;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class VisitorProfilesFindApiControllerTest extends ControllerSliceTest {

    @Test
    void 전화번호로_고객을_조회한다() throws Exception {
        Customer customer = CustomerFixture.TEMPORARY_CUSTOMER_1;
        when(visitorProfilesFindService.findCustomerProfileByNumber(anyString()))
                .thenReturn(
                        VisitorProfileFindByPhoneNumberResultDto.from(customer)
                );

        mockMvc.perform(
                        get("/api/profiles/search")
                                .param("phone-number", "01038626099")
                                .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("customers[0].id").value(customer.getId()))
                .andExpect(jsonPath("customers[0].nickname").value(customer.getNickname()))
                .andExpect(jsonPath("customers[0].phoneNumber").value(customer.getPhoneNumber()))
                .andExpect(jsonPath("customers[0].registerType").value(TEMPORARY.name().toLowerCase()));
    }
}
