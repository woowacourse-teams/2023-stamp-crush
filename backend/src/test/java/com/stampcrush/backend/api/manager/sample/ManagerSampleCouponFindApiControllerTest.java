package com.stampcrush.backend.api.manager.sample;

import com.stampcrush.backend.application.manager.sample.ManagerSampleCouponFindService;
import com.stampcrush.backend.entity.user.Owner;
import com.stampcrush.backend.fixture.OwnerFixture;
import com.stampcrush.backend.repository.user.OwnerRepository;
import com.stampcrush.backend.repository.user.RegisterCustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Base64;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ManagerSampleCouponFindApiController.class)
class ManagerSampleCouponFindApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OwnerRepository ownerRepository;

    @MockBean
    private RegisterCustomerRepository registerCustomerRepository;

    @MockBean
    private ManagerSampleCouponFindService managerSampleCouponFindService;

    @Test
    void 샘플_쿠폰_조회_요청_시_인증_헤더_정보가_없으면_401코드를_반환한다() throws Exception {
        mockMvc.perform(
                        get("/api/admin/coupon-samples")
                                .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isUnauthorized());
    }

    @Test
    void 샘플_쿠폰_조회_요청_시_인증이_안되면_401코드를_반환한다() throws Exception {
        OwnerAuthorization ownerAuthorization = createOwnerAuthorization(OwnerFixture.GITCHAN);

        when(ownerRepository.findByLoginId(ownerAuthorization.gitchan().getLoginId()))
                .thenReturn(Optional.empty());

        mockMvc.perform(
                        get("/api/admin/coupon-samples")
                                .contentType(APPLICATION_JSON)
                                .header(AUTHORIZATION, ownerAuthorization.basicAuthHeader())
                )
                .andExpect(status().isUnauthorized());
    }

    private static OwnerAuthorization createOwnerAuthorization(Owner owner) {
        String loginId = owner.getLoginId();
        String encryptedPassword = owner.getEncryptedPassword();
        String basicAuthHeader = "Basic " + Base64.getEncoder().encodeToString((loginId + ":" + encryptedPassword).getBytes());

        return new OwnerAuthorization(owner, basicAuthHeader);
    }

    private static final class OwnerAuthorization {
        private final Owner owner;
        private final String basicAuthHeader;

        private OwnerAuthorization(Owner owner, String basicAuthHeader) {
            this.owner = owner;
            this.basicAuthHeader = basicAuthHeader;
        }

        public Owner gitchan() {
            return owner;
        }

        public String basicAuthHeader() {
            return basicAuthHeader;
        }
    }
}
