package com.stampcrush.backend.acceptance;

import com.stampcrush.backend.api.customer.request.TemporaryCustomerCreateRequest;
import com.stampcrush.backend.entity.user.Owner;
import com.stampcrush.backend.repository.user.OwnerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.stampcrush.backend.acceptance.step.TemporaryCustomerCreateStep.전화번호로_임시_고객_등록_요청하고_아이디_반환;
import static com.stampcrush.backend.fixture.OwnerFixture.GITCHAN;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CouponFindAcceptanceTest extends AcceptanceTest {

    @Autowired
    private OwnerRepository ownerRepository;

    @Test
    void 쿠폰을_조회할_수_있다() {
        // TODO: Owner에 대한 회원가입 로직이 생기면 요청으로 대체한다.
        Owner savedOwner = ownerRepository.save(GITCHAN);
        assertThat(savedOwner.getId()).isNotNull();
        assertThat(savedOwner.getName()).isEqualTo(GITCHAN.getName());

        Long customerId = 전화번호로_임시_고객_등록_요청하고_아이디_반환(new TemporaryCustomerCreateRequest("01012345678"));
        assertThat(customerId).isNotNull();
    }
}
