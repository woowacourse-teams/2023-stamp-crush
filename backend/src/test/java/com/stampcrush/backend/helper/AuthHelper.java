package com.stampcrush.backend.helper;

import com.stampcrush.backend.entity.user.Owner;
import com.stampcrush.backend.entity.user.RegisterCustomer;

import java.util.Base64;

public class AuthHelper {

    public static OwnerAuthorization createOwnerAuthorization(Owner owner) {
        String loginId = owner.getLoginId();
        String encryptedPassword = owner.getEncryptedPassword();
        String basicAuthHeader = "Basic " + Base64.getEncoder().encodeToString((loginId + ":" + encryptedPassword).getBytes());

        return new OwnerAuthorization(owner, basicAuthHeader);
    }

    public static final class OwnerAuthorization {

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

    public static CustomerAuthorization createCustomerAuthorization(RegisterCustomer customer) {
        String loginId = customer.getLoginId();
        String encryptedPassword = customer.getEncryptedPassword();
        String basicAuthHeader = "Basic " + Base64.getEncoder().encodeToString((loginId + ":" + encryptedPassword).getBytes());
        return new CustomerAuthorization(loginId, basicAuthHeader);
    }

    public static final class CustomerAuthorization {

        private final String loginId;
        private final String basicAuthHeader;

        private CustomerAuthorization(String loginId, String basicAuthHeader) {
            this.loginId = loginId;
            this.basicAuthHeader = basicAuthHeader;
        }

        public String loginId() {
            return loginId;
        }

        public String basicAuthHeader() {
            return basicAuthHeader;
        }
    }
}
