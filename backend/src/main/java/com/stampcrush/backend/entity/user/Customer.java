package com.stampcrush.backend.entity.user;

import com.stampcrush.backend.auth.OAuthProvider;
import com.stampcrush.backend.exception.CustomerBadRequestException;
import com.stampcrush.backend.exception.CustomerUnAuthorizationException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import static com.stampcrush.backend.entity.user.CustomerType.REGISTER;
import static com.stampcrush.backend.entity.user.CustomerType.TEMPORARY;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@SQLDelete(sql = "UPDATE customer SET deleted = true WHERE customer_id = ?")
@Where(clause = "deleted = false")
@Getter
@Entity
public class Customer {

    private static final int NICKNAME_LENGTH = 4;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "customer_id")
    private Long id;
    private String nickname;

    private String phoneNumber;

    private String loginId;
    private String encryptedPassword;

    private String email;

    @Enumerated(STRING)
    @Column(name = "oauth_provider")
    private OAuthProvider oAuthProvider;

    @Column(name = "oauth_id")
    private Long oAuthId;

    @Enumerated(STRING)
    @Column(name = "customer_type")
    private CustomerType customerType;

    private Boolean deleted = Boolean.FALSE;

    @Builder(builderMethodName = "registeredCustomerBuilder", builderClassName = "RegisterCustomer")
    public Customer(
            Long id,
            String phoneNumber,
            String nickname,
            String email,
            String loginId,
            String encryptedPassword,
            OAuthProvider oAuthProvider,
            Long oAuthId
    ) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.nickname = nickname;
        this.email = email;
        this.loginId = loginId;
        this.encryptedPassword = encryptedPassword;
        this.oAuthProvider = oAuthProvider;
        this.oAuthId = oAuthId;
        this.customerType = REGISTER;
    }

    @Builder(builderMethodName = "temporaryCustomerBuilder")
    public Customer(
            Long id,
            String phoneNumber
    ) {
        this.id = id;
        this.nickname = formatNickname(phoneNumber);
        this.phoneNumber = phoneNumber;
        this.customerType = TEMPORARY;
    }

    public boolean isTemporaryCustomer() {
        return customerType == TEMPORARY;
    }

    public void registerPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void registerLoginId(String loginId) {
        this.loginId = loginId;
    }

    public void registerEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    private static String formatNickname(String phoneNumber) {
        if (phoneNumber.length() < NICKNAME_LENGTH) {
            throw new CustomerBadRequestException("임시 닉네임을 사용하려면 4글자 이상의 전화번호가 필요합니다");
        }
        return phoneNumber.substring(phoneNumber.length() - NICKNAME_LENGTH);
    }

    public boolean isRegistered() {
        return customerType == REGISTER;
    }

    public void checkPassword(String encryptedPassword) {
        if (!this.encryptedPassword.equals(encryptedPassword)) {
            throw new CustomerUnAuthorizationException("아이디와 패스워드를 다시 확인 후 로그인해주세요.");
        }
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setoAuthProvider(OAuthProvider oAuthProvider) {
        this.oAuthProvider = oAuthProvider;
    }

    public void setoAuthId(Long oAuthId) {
        this.oAuthId = oAuthId;
    }

    public void toRegisterCustomer() {
        this.customerType = REGISTER;
    }
}
