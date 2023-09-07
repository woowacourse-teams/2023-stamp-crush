package com.stampcrush.backend.acceptance;

import com.stampcrush.backend.api.visitor.cafe.response.CafeInfoFindByCustomerResponse;
import com.stampcrush.backend.api.visitor.cafe.response.CafeInfoFindResponse;
import com.stampcrush.backend.application.visitor.cafe.dto.CafeInfoFindByCustomerResultDto;
import com.stampcrush.backend.entity.cafe.Cafe;
import com.stampcrush.backend.entity.user.Customer;
import com.stampcrush.backend.fixture.CustomerFixture;
import com.stampcrush.backend.fixture.OwnerFixture;
import com.stampcrush.backend.repository.cafe.CafeRepository;
import com.stampcrush.backend.repository.user.CustomerRepository;
import com.stampcrush.backend.repository.user.OwnerRepository;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.stampcrush.backend.acceptance.step.VisitorCafeFindStep.고객의_카페_정보_조회_요청;
import static com.stampcrush.backend.fixture.CafeFixture.cafeOfSavedOwner;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public class VisitorCafeFindAcceptanceTest extends AcceptanceTest {

    @Autowired
    private CafeRepository cafeRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void 고객이_카페정보를_조회한다() {
        // given
        Customer customer = customerRepository.save(CustomerFixture.REGISTER_CUSTOMER_JENA);
        Cafe savedCafe = cafeRepository.save(cafeOfSavedOwner(ownerRepository.save(OwnerFixture.GITCHAN))
        );

        // when
        ExtractableResponse<Response> response = 고객의_카페_정보_조회_요청(customer, savedCafe.getId());
        CafeInfoFindByCustomerResponse cafeInfoFindByCustomerResponse = response.body().as(CafeInfoFindByCustomerResponse.class);

        // then
        assertThat(cafeInfoFindByCustomerResponse.getCafe())
                .usingRecursiveComparison()
                .isEqualTo(CafeInfoFindResponse.from(CafeInfoFindByCustomerResultDto.from(savedCafe)));
    }

    @Test
    void 고객이_존재하지_않는_카페를_조회하면_에러가_발생한다() {
        Customer customer = customerRepository.save(CustomerFixture.REGISTER_CUSTOMER_JENA);

        long NOT_EXIST_CAFE_ID = 1L;
        ExtractableResponse<Response> response = 고객의_카페_정보_조회_요청(customer, NOT_EXIST_CAFE_ID);

        assertThat(response.statusCode()).isEqualTo(NOT_FOUND.value());
    }
}
