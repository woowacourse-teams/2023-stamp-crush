package com.stampcrush.backend.api.visitor.profile.response;

import com.stampcrush.backend.application.visitor.profile.dto.VisitorProfileFindByPhoneNumberResultDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VisitorProfilesFindByPhoneNumberResponse {

    private List<VisitorProfileFindByPhoneNumberResponse> customers;

    public static VisitorProfilesFindByPhoneNumberResponse from(VisitorProfileFindByPhoneNumberResultDto dto) {
        if (dto == null) {
            return new VisitorProfilesFindByPhoneNumberResponse(List.of());
        }
        return new VisitorProfilesFindByPhoneNumberResponse(
                List.of(VisitorProfileFindByPhoneNumberResponse.from(dto))
        );
    }

    @Getter
    @RequiredArgsConstructor
    private static class VisitorProfileFindByPhoneNumberResponse {

        private final Long id;
        private final String nickname;
        private final String phoneNumber;
        private final String registerType;

        public static VisitorProfileFindByPhoneNumberResponse from(VisitorProfileFindByPhoneNumberResultDto dto) {
            return new VisitorProfileFindByPhoneNumberResponse(
                    dto.getId(),
                    dto.getNickname(),
                    dto.getPhoneNumber(),
                    dto.getRegisterType()
            );
        }
    }
}
