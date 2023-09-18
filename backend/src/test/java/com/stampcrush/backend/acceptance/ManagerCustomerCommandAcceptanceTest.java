package com.stampcrush.backend.acceptance;

import com.stampcrush.backend.api.manager.customer.request.TemporaryCustomerCreateRequest;
import com.stampcrush.backend.api.manager.customer.response.CustomerFindResponse;
import com.stampcrush.backend.api.manager.customer.response.CustomersFindResponse;
import com.stampcrush.backend.application.manager.customer.dto.CustomerFindDto;
import com.stampcrush.backend.auth.OAuthProvider;
import com.stampcrush.backend.auth.api.request.OAuthRegisterCustomerCreateRequest;
import com.stampcrush.backend.auth.application.util.AuthTokensGenerator;
import com.stampcrush.backend.entity.user.Customer;
import com.stampcrush.backend.entity.user.Owner;
import com.stampcrush.backend.repository.user.CustomerRepository;
import com.stampcrush.backend.repository.user.OwnerRepository;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.stampcrush.backend.acceptance.step.ManagerCustomerFindStep.전화번호로_고객_조회_요청;
import static com.stampcrush.backend.acceptance.step.ManagerJoinStep.OWNER_CREATE_REQUEST;
import static com.stampcrush.backend.acceptance.step.ManagerJoinStep.카페_사장_회원_가입_요청하고_액세스_토큰_반환;
import static com.stampcrush.backend.acceptance.step.VisitorJoinStep.가입_고객_회원_가입_요청하고_액세스_토큰_반환;
import static com.stampcrush.backend.acceptance.step.VisitorJoinStep.임시_고객_회원_가입_요청;
import static com.stampcrush.backend.acceptance.step.VisitorJoinStep.임시_고객_회원_가입_요청하고_아이디_반환;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

public class ManagerCustomerCommandAcceptanceTest extends AcceptanceTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private AuthTokensGenerator authTokensGenerator;

    @Test
    void 전화번호로_가입_고객을_조회한다() {
        // given
        String ownerAccessToken = 카페_사장_회원_가입_요청하고_액세스_토큰_반환(OWNER_CREATE_REQUEST);
        Long ownerId = authTokensGenerator.extractMemberId(ownerAccessToken);
        Owner owner = ownerRepository.findById(ownerId).get();

        Long temporaryCustomerId = 임시_고객_회원_가입_요청하고_아이디_반환(ownerAccessToken, new TemporaryCustomerCreateRequest("01012345678"));
        Customer customer = customerRepository.findById(temporaryCustomerId).get();

        // when
        ExtractableResponse<Response> response = 전화번호로_고객_조회_요청(owner, "01012345678");
        CustomersFindResponse customers = response.body().as(CustomersFindResponse.class);

        // then
        assertSoftly(softAssertions -> {
            softAssertions.assertThat(response.statusCode()).isEqualTo(OK.value());
            softAssertions.assertThat(customers.getCustomer()).containsExactlyInAnyOrder(CustomerFindResponse.from(CustomerFindDto.from(customer)));
        });
    }

    @Test
    void 전화번호로_임시_고객을_조회한다() {
        // given
        String ownerAccessToken = 카페_사장_회원_가입_요청하고_액세스_토큰_반환(OWNER_CREATE_REQUEST);
        Long ownerId = authTokensGenerator.extractMemberId(ownerAccessToken);
        Owner owner = ownerRepository.findById(ownerId).get();

        Long temporaryCustomerId = 임시_고객_회원_가입_요청하고_아이디_반환(ownerAccessToken, new TemporaryCustomerCreateRequest("01012345678"));
        Customer customer = customerRepository.findById(temporaryCustomerId).get();

        // when
        ExtractableResponse<Response> response = 전화번호로_고객_조회_요청(owner, "01012345678");
        CustomersFindResponse customers = response.body().as(CustomersFindResponse.class);

        // then
        assertSoftly(softAssertions -> {
            softAssertions.assertThat(response.statusCode()).isEqualTo(OK.value());
            softAssertions.assertThat(customers.getCustomer()).containsExactly(CustomerFindResponse.from(CustomerFindDto.from(customer)));
        });
    }

    @Test
    void 고객이_존재하지_않는_경우_빈_배열을_반환한다() {
        // given, when
        String ownerAccessToken = 카페_사장_회원_가입_요청하고_액세스_토큰_반환(OWNER_CREATE_REQUEST);
        Long ownerId = authTokensGenerator.extractMemberId(ownerAccessToken);
        Owner owner = ownerRepository.findById(ownerId).get();

        ExtractableResponse<Response> response = 전화번호로_고객_조회_요청(owner, "01012345678");
        CustomersFindResponse customers = response.body().as(CustomersFindResponse.class);

        // then
        assertSoftly(softAssertions -> {
            softAssertions.assertThat(response.statusCode()).isEqualTo(OK.value());
            softAssertions.assertThat(customers.getCustomer().size()).isEqualTo(0);
        });
    }

    @Test
    void 임시_고객을_생성한다() {
        // given
        String ownerAccessToken = 카페_사장_회원_가입_요청하고_액세스_토큰_반환(OWNER_CREATE_REQUEST);
        Long ownerId = authTokensGenerator.extractMemberId(ownerAccessToken);
        Owner owner = ownerRepository.findById(ownerId).get();

        // when
        Long temporaryCustomerId = 임시_고객_회원_가입_요청하고_아이디_반환(ownerAccessToken, new TemporaryCustomerCreateRequest("01012345678"));
        Customer temporaryCustomer = customerRepository.findById(temporaryCustomerId).get();

        // then
        assertSoftly(softly -> {
            softly.assertThat(temporaryCustomer.getNickname()).isEqualTo("5678");
            softly.assertThat(temporaryCustomer.getPhoneNumber()).isEqualTo("01012345678");
        });
    }

    @Test
    void 존재하는_회원의_번호로_고객을_생성하려면_에러를_발생한다() {
        // given
        String ownerAccessToken = 카페_사장_회원_가입_요청하고_액세스_토큰_반환(OWNER_CREATE_REQUEST);
        Long ownerId = authTokensGenerator.extractMemberId(ownerAccessToken);
        Owner owner = ownerRepository.findById(ownerId).get();

        String accessToken = 가입_고객_회원_가입_요청하고_액세스_토큰_반환(new OAuthRegisterCustomerCreateRequest(
                "깃짱", "gitchan@gmail.com", OAuthProvider.KAKAO, 12314124L
        ));
        Long temporaryCustomerId = 임시_고객_회원_가입_요청하고_아이디_반환("01012345678");
        Customer temporaryCustomer = customerRepository.findById(temporaryCustomerId).get();

        ExtractableResponse<Response> response = 임시_고객_회원_가입_요청(ownerAccessToken, new TemporaryCustomerCreateRequest(temporaryCustomer.getPhoneNumber()));

        // when, then
        assertThat(response.statusCode()).isEqualTo(BAD_REQUEST.value());
    }
}
