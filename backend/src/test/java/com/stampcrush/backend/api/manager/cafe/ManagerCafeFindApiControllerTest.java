package com.stampcrush.backend.api.manager.cafe;

import com.stampcrush.backend.api.ControllerSliceTest;
import com.stampcrush.backend.application.manager.cafe.ManagerCafeFindService;
import com.stampcrush.backend.entity.user.Owner;
import com.stampcrush.backend.helper.BearerAuthHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.Base64;
import java.util.Optional;

import static com.stampcrush.backend.fixture.OwnerFixture.OWNER3;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ManagerCafeFindApiController.class)
class ManagerCafeFindApiControllerTest extends ControllerSliceTest {

    @MockBean
    private ManagerCafeFindService managerCafeFindService;

    private Owner owner;
    private String basicAuthHeader;
    private String bearerAuthHeader;

    @BeforeEach
    void setUp() {
        owner = OWNER3;

        String username = owner.getLoginId();
        String password = owner.getEncryptedPassword();
        basicAuthHeader = "Basic " + Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
        bearerAuthHeader = "Bearer " + BearerAuthHelper.generateToken(owner.getId());
    }

    @Test
    void 카페_조회_요청_시_인증_헤더_정보가_없으면_401코드_반환() throws Exception {
        // ownerId 로 넣어둔 1은 없어질거라 우선 매직넘버로 넣어둠
        mockMvc.perform(get("/api/admin/cafes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void 카페_조회_요청_시_사장_인증이_안되면_401코드_반환() throws Exception {
        // given
        when(ownerRepository.findByLoginId(owner.getLoginId())).thenReturn(Optional.empty());

        // when, then
        mockMvc.perform(get("/api/admin/cafes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, bearerAuthHeader))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void 카페_조회_요청_시_사장_인증_되면_200코드_반환() throws Exception {
        // given
        when(ownerRepository.findById(owner.getId())).thenReturn(Optional.of(owner));
        when(ownerRepository.findByLoginId(owner.getLoginId())).thenReturn(Optional.of(owner));

        // when, then
        mockMvc.perform(
                        get("/api/admin/cafes")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header(HttpHeaders.AUTHORIZATION, bearerAuthHeader)
                )
                .andExpect(status().isOk());
    }

    @Test
    void 카페_조회_요청_시_비밀번호가_틀리면_401코드_반환() throws Exception {
        // given
        when(ownerRepository.findByLoginId(owner.getLoginId()))
                .thenReturn(Optional.of(new Owner("jena", "jenaId", "jnpw123", "01098765432")));

        // when, then
        mockMvc.perform(get("/api/admin/cafes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, bearerAuthHeader))
                .andExpect(status().isUnauthorized());
    }
}
