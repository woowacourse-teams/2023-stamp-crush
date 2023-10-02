package com.stampcrush.backend.application.visitor.profile;

import com.stampcrush.backend.application.visitor.profile.dto.VisitorProfilesLinkDataDto;
import com.stampcrush.backend.auth.OAuthProvider;
import com.stampcrush.backend.entity.user.Customer;
import com.stampcrush.backend.exception.BadRequestException;
import com.stampcrush.backend.exception.CustomerNotFoundException;
import com.stampcrush.backend.repository.user.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class VisitorProfilesCommandService {

    private final CustomerRepository customerRepository;

    public void registerPhoneNumber(Long customerId, String phoneNumber) {
        validateDuplicatePhoneNumber(phoneNumber);
        Customer customer = findExistingCustomer(customerId);
        customer.registerPhoneNumber(phoneNumber);
    }

    private void validateDuplicatePhoneNumber(String phoneNumber) {
        List<Customer> findCustomer = customerRepository.findByPhoneNumber(phoneNumber);
        if (!findCustomer.isEmpty()) {
            throw new BadRequestException("해당 전화번호 " + phoneNumber + "는 이미 존재하는 회원과 중복임!");
        }
    }

    public void linkData(Long customerId, VisitorProfilesLinkDataDto dto) {
        Customer registerCustomer = findExistingCustomer(customerId);
        Customer temporaryCustomer = findExistingCustomer(dto.getPreviousTemporaryCustomerId());

        String nickname = registerCustomer.getNickname();
        String loginId = registerCustomer.getLoginId();
        String encryptedPassword = registerCustomer.getEncryptedPassword();
        String email = registerCustomer.getEmail();
        OAuthProvider oAuthProvider = registerCustomer.getOAuthProvider();
        Long oAuthId = registerCustomer.getOAuthId();

        temporaryCustomer.toRegisterCustomer();
        temporaryCustomer.setNickname(nickname);
        temporaryCustomer.setLoginId(loginId);
        temporaryCustomer.setEncryptedPassword(encryptedPassword);
        temporaryCustomer.setEmail(email);
        temporaryCustomer.setoAuthProvider(oAuthProvider);
        temporaryCustomer.setoAuthId(oAuthId);

        customerRepository.delete(registerCustomer);
    }

    private Customer findExistingCustomer(Long customerId) {
        Optional<Customer> findCustomer = customerRepository.findById(customerId);

        if (findCustomer.isEmpty()) {
            throw new CustomerNotFoundException("해당 아이디의 고객을 찾을 수 없습니다.");
        }

        return findCustomer.get();
    }
}
