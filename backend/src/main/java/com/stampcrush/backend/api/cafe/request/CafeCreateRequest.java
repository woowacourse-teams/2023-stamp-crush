package com.stampcrush.backend.api.cafe.request;

public record CafeCreateRequest(String name, String roadAddress, String detailAddress,
                                String businessRegistrationNumber) {
}
