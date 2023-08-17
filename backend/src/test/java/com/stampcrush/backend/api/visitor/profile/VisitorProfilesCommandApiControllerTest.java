package com.stampcrush.backend.api.visitor.profile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stampcrush.backend.api.ControllerSliceTest;
import com.stampcrush.backend.api.visitor.profile.request.VisitorProfilesPhoneNumberUpdateRequest;
import com.stampcrush.backend.application.visitor.profile.VisitorProfilesCommandService;
import com.stampcrush.backend.config.WebMvcConfig;
import com.stampcrush.backend.exception.CustomerBadRequestException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
        value = VisitorProfilesCommandApiController.class,
        excludeFilters =
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebMvcConfig.class)
)
class VisitorProfilesCommandApiControllerTest extends ControllerSliceTest {

    @MockBean
    private VisitorProfilesCommandService visitorProfilesCommandService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void 전화번호를_정상_저장하면_200_상태코드를_반환한다() throws Exception {
        doNothing()
                .when(visitorProfilesCommandService)
                .registerPhoneNumber(anyLong(), anyString());

        VisitorProfilesPhoneNumberUpdateRequest request = new VisitorProfilesPhoneNumberUpdateRequest("01012345678");

        mockMvc.perform(
                        post("/api/profiles/phone-number")
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isOk());
    }

    @Test
    @Disabled
    void 전화번호가_기존과_중복되면_BAD_REQUEST_상태코드를_반환한다() throws Exception {
        doThrow(CustomerBadRequestException.class)
                .when(visitorProfilesCommandService)
                .registerPhoneNumber(null, null);

        mockMvc.perform(
                        post("/api/profiles/phone-number")
                                .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest());
    }
}
