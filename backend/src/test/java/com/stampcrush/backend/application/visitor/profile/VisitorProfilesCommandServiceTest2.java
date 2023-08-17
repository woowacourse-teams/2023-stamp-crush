package com.stampcrush.backend.application.visitor.profile;

import com.stampcrush.backend.auth.OAuthProvider;
import com.stampcrush.backend.entity.user.Customer;
import com.stampcrush.backend.entity.user.RegisterCustomer;
import com.stampcrush.backend.repository.user.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class VisitorProfilesCommandServiceTest2 {

    @Autowired
    private VisitorProfilesCommandService visitorProfilesCommandService;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void 전화번호_등록한다() {
        RegisterCustomer gitchan = customerRepository.save(RegisterCustomer.builder()
                .nickname("깃짱")
                .email("gitchan@naver.com")
                .oAuthId(1L)
                .oAuthProvider(OAuthProvider.KAKAO)
                .build()
        );

        String phoneNumber = "01012345678";
        visitorProfilesCommandService.registerPhoneNumber(gitchan.getId(), phoneNumber);

        Customer findGitchan = customerRepository.findById(gitchan.getId()).get();
        assertThat(findGitchan.getPhoneNumber()).isEqualTo(phoneNumber);
    }
}
