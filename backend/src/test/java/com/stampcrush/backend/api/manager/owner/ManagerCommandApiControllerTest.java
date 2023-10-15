package com.stampcrush.backend.api.manager.owner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stampcrush.backend.api.ControllerSliceTest;
import com.stampcrush.backend.api.manager.owner.request.OwnerCreateRequest;
import com.stampcrush.backend.api.manager.owner.request.OwnerLoginRequest;
import com.stampcrush.backend.application.manager.owner.dto.OwnerCreateDto;
import com.stampcrush.backend.application.manager.owner.dto.OwnerLoginDto;
import com.stampcrush.backend.auth.api.response.AuthTokensResponse;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ManagerCommandApiControllerTest extends ControllerSliceTest {

    @Test
    void 회원가입_요청이_정상적일_경우_201_코드를_반환한다() throws Exception {
        // given
        OwnerCreateRequest ownerCreateRequest = new OwnerCreateRequest("loginID", "password123");
        when(managerCommandService.register(OwnerCreateDto.from(ownerCreateRequest)))
                .thenReturn(1L);
        String requestBody = new ObjectMapper().writeValueAsString(ownerCreateRequest);

        // when, then
        mockMvc.perform(
                        post("/api/admin/owners")
                                .contentType(APPLICATION_JSON)
                                .content(requestBody)
                )
                .andExpect(status().isCreated());
    }

    @Test
    void 로그인_요청이_정상적일_경우_200_코드를_반환한다() throws Exception {
        // given
        OwnerLoginRequest ownerLoginRequest = new OwnerLoginRequest("loginID", "password123");
        when(managerCommandService.login(OwnerLoginDto.from(ownerLoginRequest)))
                .thenReturn(new AuthTokensResponse("token", "token", "bearer", 1L));
        String requestBody = new ObjectMapper().writeValueAsString(ownerLoginRequest);

        // when, then
        mockMvc.perform(
                        post("/api/admin/login")
                                .contentType(APPLICATION_JSON)
                                .content(requestBody)
                )
                .andExpect(status().isOk());
    }
}
