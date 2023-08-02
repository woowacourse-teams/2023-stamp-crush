package com.stampcrush.backend.api.manager.sample;

import com.stampcrush.backend.repository.user.OwnerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ManagerSampleCouponFindApiController.class)
class ManagerSampleCouponFindApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OwnerRepository ownerRepository;

    @Test
    void 샘플_쿠폰_조회_요청_시_인증_헤더_정보가_없으면_401코드를_반환한다() throws Exception {
        mockMvc.perform(
                        get("/api/admin/coupon-samples")
                                .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isUnauthorized());
    }
}
