package com.stampcrush.backend.api.visitor.profile.response;

import com.stampcrush.backend.application.visitor.profile.dto.VisitorProfileFindResultDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VisitorProfilesFindResponse {

    private List<VisitorProfileFindResponse> profile;

    public static VisitorProfilesFindResponse from(VisitorProfileFindResultDto dto) {
        return new VisitorProfilesFindResponse(List.of(VisitorProfileFindResponse.from(dto)));
    }

    @Getter
    @RequiredArgsConstructor
    public static class VisitorProfileFindResponse {

        private final Long id;
        private final String nickname;
        private final String phoneNumber;
        private final String email;

        public static VisitorProfilesFindResponse.VisitorProfileFindResponse from(VisitorProfileFindResultDto dto) {
            return new VisitorProfilesFindResponse.VisitorProfileFindResponse(
                    dto.getId(),
                    dto.getNickname(),
                    dto.getPhoneNumber(),
                    dto.getEmail());
        }
    }
}
