package com.stampcrush.backend.acceptance;

import com.stampcrush.backend.api.manager.coupon.request.CouponCreateRequest;
import com.stampcrush.backend.api.manager.coupon.request.StampCreateRequest;
import com.stampcrush.backend.api.manager.coupon.response.CafeCustomerFindResponse;
import com.stampcrush.backend.api.manager.coupon.response.CafeCustomersFindResponse;
import com.stampcrush.backend.entity.user.Owner;
import com.stampcrush.backend.entity.user.RegisterCustomer;
import com.stampcrush.backend.repository.user.OwnerRepository;
import com.stampcrush.backend.repository.user.RegisterCustomerRepository;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.stampcrush.backend.acceptance.step.ManagerCafeCreateStep.CAFE_CREATE_REQUEST;
import static com.stampcrush.backend.acceptance.step.ManagerCafeCreateStep.카페_생성_요청하고_아이디_반환;
import static com.stampcrush.backend.acceptance.step.ManagerCouponCreateStep.쿠폰_생성_요청하고_아이디_반환;
import static com.stampcrush.backend.acceptance.step.ManagerCustomerFindStep.고객_목록_조회_요청;
import static com.stampcrush.backend.acceptance.step.ManagerStampCreateStep.쿠폰에_스탬프를_적립_요청;
import static com.stampcrush.backend.fixture.CustomerFixture.REGISTER_CUSTOMER_GITCHAN;
import static com.stampcrush.backend.fixture.CustomerFixture.REGISTER_CUSTOMER_YOUNGHO;
import static org.assertj.core.api.Assertions.assertThat;

public class ManagerCouponFindAcceptanceTest extends AcceptanceTest {

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private RegisterCustomerRepository registerCustomerRepository;

    @Test
    void 고객_목록을_조회한다() {
        // given
        Owner owner = ownerRepository.save(new Owner("owner", "id", "pw", "phone"));
        Long savedCafeId = 카페_생성_요청하고_아이디_반환(owner, CAFE_CREATE_REQUEST);
        RegisterCustomer youngho = registerCustomerRepository.save(REGISTER_CUSTOMER_YOUNGHO);
        RegisterCustomer gitchan = registerCustomerRepository.save(REGISTER_CUSTOMER_GITCHAN);
        CouponCreateRequest request = new CouponCreateRequest(savedCafeId);


        Long couponId1 = 쿠폰_생성_요청하고_아이디_반환(owner, request, youngho.getId());
        Long couponId2 = 쿠폰_생성_요청하고_아이디_반환(owner, request, gitchan.getId());

        StampCreateRequest stampCreateRequest1 = new StampCreateRequest(7);
        StampCreateRequest stampCreateRequest2 = new StampCreateRequest(5);

        쿠폰에_스탬프를_적립_요청(owner, youngho, couponId1, stampCreateRequest1);
        쿠폰에_스탬프를_적립_요청(owner, youngho, couponId1, stampCreateRequest2);
        쿠폰에_스탬프를_적립_요청(owner, gitchan, couponId2, stampCreateRequest1);

        String firstVisitDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy:MM:dd"));


        // when
        List<CafeCustomerFindResponse> expected =
                List.of(new CafeCustomerFindResponse(1L, youngho.getNickname(), 2, 1, 2, 10, firstVisitDate, true),
                        new CafeCustomerFindResponse(2L, gitchan.getNickname(), 7, 0, 1, 10, firstVisitDate, true));

        ExtractableResponse<Response> response = 고객_목록_조회_요청(owner, savedCafeId);

        CafeCustomersFindResponse actual = response.body().as(CafeCustomersFindResponse.class);

        // then
        assertThat(actual.getCustomers()).usingRecursiveFieldByFieldElementComparator().isEqualTo(expected);
    }
}
