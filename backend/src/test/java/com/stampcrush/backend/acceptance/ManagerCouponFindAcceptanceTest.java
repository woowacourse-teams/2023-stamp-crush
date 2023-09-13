package com.stampcrush.backend.acceptance;

import com.stampcrush.backend.api.manager.coupon.request.CouponCreateRequest;
import com.stampcrush.backend.api.manager.coupon.request.StampCreateRequest;
import com.stampcrush.backend.api.manager.coupon.response.CafeCustomerFindResponse;
import com.stampcrush.backend.api.manager.coupon.response.CafeCustomersFindResponse;
import com.stampcrush.backend.api.manager.coupon.response.CustomerAccumulatingCouponFindResponse;
import com.stampcrush.backend.entity.user.Customer;
import com.stampcrush.backend.entity.user.Owner;
import com.stampcrush.backend.repository.user.CustomerRepository;
import com.stampcrush.backend.repository.user.OwnerRepository;
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
import static com.stampcrush.backend.acceptance.step.ManagerCouponFindStep.고객의_쿠폰_조회_요청;
import static com.stampcrush.backend.acceptance.step.ManagerCouponFindStep.고객의_쿠폰_조회하고_결과_반환;
import static com.stampcrush.backend.acceptance.step.ManagerCustomerFindStep.고객_목록_조회_요청;
import static com.stampcrush.backend.acceptance.step.ManagerStampCreateStep.쿠폰에_스탬프를_적립_요청;
import static com.stampcrush.backend.fixture.CustomerFixture.REGISTER_CUSTOMER_GITCHAN;
import static com.stampcrush.backend.fixture.OwnerFixture.JENA;
import static org.assertj.core.api.Assertions.assertThat;

public class ManagerCouponFindAcceptanceTest extends AcceptanceTest {

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void 고객_목록을_조회한다() {
        // given
        Owner owner = ownerRepository.save(JENA);

        Long savedCafeId = 카페_생성_요청하고_아이디_반환(owner, CAFE_CREATE_REQUEST);
        Customer youngho = customerRepository.save(Customer.registeredCustomerBuilder().nickname("youngho").build());
        Customer gitchan = customerRepository.save(Customer.registeredCustomerBuilder().nickname("gitchan").build());

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
        CafeCustomerFindResponse expected1 = new CafeCustomerFindResponse(1L, youngho.getNickname(), 2, 1, 2, 10, firstVisitDate, true);
        CafeCustomerFindResponse expected2 = new CafeCustomerFindResponse(2L, gitchan.getNickname(), 7, 0, 1, 10, firstVisitDate, true);

        ExtractableResponse<Response> response = 고객_목록_조회_요청(owner, savedCafeId);

        CafeCustomersFindResponse actual = response.body().as(CafeCustomersFindResponse.class);

        // then
        assertThat(actual.getCustomers()).containsExactlyInAnyOrder(expected1, expected2);
    }

    @Test
    void 내_카페가_아닌_고객의_고객목록_조회_불가능() {
        // given
        Owner owner = ownerRepository.save(JENA);
        Owner notOwner = ownerRepository.save(new Owner("notOwner", "id", "pw", "01029384726"));

        Long savedCafeId = 카페_생성_요청하고_아이디_반환(owner, CAFE_CREATE_REQUEST);
        Customer gitchan = customerRepository.save(REGISTER_CUSTOMER_GITCHAN);

        CouponCreateRequest request = new CouponCreateRequest(savedCafeId);

        Long couponId2 = 쿠폰_생성_요청하고_아이디_반환(owner, request, gitchan.getId());

        // when
        ExtractableResponse<Response> response = 고객_목록_조회_요청(notOwner, savedCafeId);

        // then
        assertThat(response.statusCode()).isEqualTo(401);
    }

    @Test
    void 고객의_쿠폰을_조회한다() {
        // given
        Owner owner = ownerRepository.save(JENA);

        Long savedCafeId = 카페_생성_요청하고_아이디_반환(owner, CAFE_CREATE_REQUEST);
        Customer youngho = customerRepository.save(Customer.registeredCustomerBuilder().nickname("youngho").build());

        CouponCreateRequest request = new CouponCreateRequest(savedCafeId);

        Long couponId = 쿠폰_생성_요청하고_아이디_반환(owner, request, youngho.getId());

        StampCreateRequest stampCreateRequest1 = new StampCreateRequest(7);
        StampCreateRequest stampCreateRequest2 = new StampCreateRequest(5);

        쿠폰에_스탬프를_적립_요청(owner, youngho, couponId, stampCreateRequest1);
        쿠폰에_스탬프를_적립_요청(owner, youngho, couponId, stampCreateRequest2);

        // when
        String expireDate = LocalDate.now().plusMonths(6).format(DateTimeFormatter.ofPattern("yyyy:MM:dd"));
        CustomerAccumulatingCouponFindResponse expected = new CustomerAccumulatingCouponFindResponse(couponId + 1, youngho.getId(), youngho.getNickname(), 2, expireDate, Boolean.FALSE, 10);
        List<CustomerAccumulatingCouponFindResponse> response = 고객의_쿠폰_조회하고_결과_반환(owner, savedCafeId, youngho);

        // then
        assertThat(response).containsExactlyInAnyOrder(expected);
    }

    @Test
    void 내_카페가_아닌_카페의_고객의_쿠폰은_조회_불가능() {
        // given
        Owner owner = ownerRepository.save(JENA);
        Owner notOwner = ownerRepository.save(new Owner("notOwner", "id", "pw", "01029384726"));

        Long savedCafeId = 카페_생성_요청하고_아이디_반환(owner, CAFE_CREATE_REQUEST);
        Customer youngho = customerRepository.save(Customer.registeredCustomerBuilder().nickname("youngho").build());

        CouponCreateRequest request = new CouponCreateRequest(savedCafeId);
        Long couponId = 쿠폰_생성_요청하고_아이디_반환(owner, request, youngho.getId());

        StampCreateRequest stampCreateRequest1 = new StampCreateRequest(7);
        쿠폰에_스탬프를_적립_요청(owner, youngho, couponId, stampCreateRequest1);

        // when
        ExtractableResponse<Response> response = 고객의_쿠폰_조회_요청(savedCafeId, notOwner, youngho);

        // then
        assertThat(response.statusCode()).isEqualTo(401);
    }
}
