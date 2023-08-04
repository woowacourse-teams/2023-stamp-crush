package com.stampcrush.backend.api.manager.sample;

import com.stampcrush.backend.application.manager.sample.ManagerSampleCouponFindService;
import com.stampcrush.backend.application.manager.sample.dto.SampleCouponsFindResultDto;
import com.stampcrush.backend.entity.user.Owner;
import com.stampcrush.backend.fixture.OwnerFixture;
import com.stampcrush.backend.fixture.SampleCouponFixture;
import com.stampcrush.backend.repository.sample.SampleBackImageRepository;
import com.stampcrush.backend.repository.sample.SampleFrontImageRepository;
import com.stampcrush.backend.repository.sample.SampleStampCoordinateRepository;
import com.stampcrush.backend.repository.sample.SampleStampImageRepository;
import com.stampcrush.backend.repository.user.OwnerRepository;
import com.stampcrush.backend.repository.user.RegisterCustomerRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Base64;
import java.util.List;
import java.util.Optional;

import static com.stampcrush.backend.fixture.SampleCouponFixture.*;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(
        {
                SampleFrontImageRepository.class,
                SampleBackImageRepository.class,
                SampleStampCoordinateRepository.class,
                SampleStampImageRepository.class
        }
)
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

    @Autowired
    private SampleFrontImageRepository sampleFrontImageRepository;

    @Autowired
    private SampleBackImageRepository sampleBackImageRepository;

    @Autowired
    private SampleStampCoordinateRepository sampleStampCoordinateRepository;

    @Autowired
    private SampleStampImageRepository sampleStampImageRepository;

    @Test
    void 샘플_쿠폰_조회_요청_시_인증_헤더_정보가_없으면_401_상태코드를_반환한다() throws Exception {
        mockMvc.perform(
                        get("/api/admin/coupon-samples")
                                .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isUnauthorized());
    }

    @Test
    void 샘플_쿠폰_조회_요청_시_인증이_안되면_401_상태코드를_반환한다() throws Exception {
        OwnerAuthorization ownerAuthorization = createOwnerAuthorization(OwnerFixture.GITCHAN);

        when(ownerRepository.findByLoginId(ownerAuthorization.owner.getLoginId()))
                .thenReturn(Optional.empty());

        mockMvc.perform(
                        get("/api/admin/coupon-samples")
                                .contentType(APPLICATION_JSON)
                                .header(AUTHORIZATION, ownerAuthorization.getBasicAuthHeader())
                )
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Disabled
    void 샘플_쿠폰_조회_요청_시_인증이_되면_200_상태코드와_응답을_반환한다() throws Exception {
        OwnerAuthorization ownerAuthorization = createOwnerAuthorization(OwnerFixture.GITCHAN);
        int maxStampCount = 8;

        when(ownerRepository.findByLoginId(ownerAuthorization.owner.getLoginId()))
                .thenReturn(Optional.of(OwnerFixture.GITCHAN));

        when(managerSampleCouponFindService.findSampleCouponsByMaxStampCount(maxStampCount))
                .thenReturn(
                        SampleCouponsFindResultDto.of(
                                List.of(sampleFrontImageRepository.save(SAMPLE_FRONT_IMAGE_SAVED)),
                                List.of(sampleBackImageRepository.save(SAMPLE_BACK_IMAGE_SAVED)),
                                SampleCouponFixture.SAMPLE_COORDINATES_SIZE_EIGHT.stream()
                                        .map(it -> sampleStampCoordinateRepository.save(it))
                                        .toList(),
                                List.of(sampleStampImageRepository.save(SAMPLE_STAMP_IMAGE_SAVED))
                        )
                );

        mockMvc.perform(
                        get("/api/admin/coupon-samples?max-stamp-count=" + maxStampCount)
                                .contentType(APPLICATION_JSON)
                                .header(AUTHORIZATION, ownerAuthorization.getBasicAuthHeader())
                )
                .andExpect(status().isOk());
    }

    private OwnerAuthorization createOwnerAuthorization(Owner owner) {
        String loginId = owner.getLoginId();
        String encryptedPassword = owner.getEncryptedPassword();
        String basicAuthHeader = "Basic " + Base64.getEncoder().encodeToString((loginId + ":" + encryptedPassword).getBytes());

        return new OwnerAuthorization(owner, basicAuthHeader);
    }

    private final class OwnerAuthorization {
        private final Owner owner;
        private final String basicAuthHeader;

        private OwnerAuthorization(Owner owner, String basicAuthHeader) {
            this.owner = owner;
            this.basicAuthHeader = basicAuthHeader;
        }

        public Owner getOwner() {
            return owner;
        }

        public String getBasicAuthHeader() {
            return basicAuthHeader;
        }
    }
}
