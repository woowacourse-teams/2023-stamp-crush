package com.stampcrush.backend.api.visitor.profile;

import com.stampcrush.backend.api.ControllerSliceTest;
import com.stampcrush.backend.api.visitor.profile.request.VisitorProfilesLinkDataRequest;
import com.stampcrush.backend.api.visitor.profile.request.VisitorProfilesPhoneNumberUpdateRequest;
import com.stampcrush.backend.exception.CustomerBadRequestException;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class VisitorProfilesCommandApiControllerTest extends ControllerSliceTest {

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

    @Test
    void 데이터_연동에_성공하면_200_상태코드를_반환한다() throws Exception {
        doNothing()
                .when(visitorProfilesCommandService)
                .linkData(eq(null), any());

        VisitorProfilesLinkDataRequest request = new VisitorProfilesLinkDataRequest(1L);

        mockMvc.perform(
                        post("/api/profiles/link-data")
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isOk());
    }
}
