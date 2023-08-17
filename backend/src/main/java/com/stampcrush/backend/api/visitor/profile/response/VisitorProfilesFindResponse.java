package com.stampcrush.backend.api.visitor.profile.response;

import com.stampcrush.backend.application.visitor.profile.dto.VisitorProfileFindResultDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class VisitorProfilesFindResponse {

    private final List<VisitorProfileFindResponse> profileResponses;

    public static VisitorProfilesFindResponse from(VisitorProfileFindResultDto dto) {
        return new VisitorProfilesFindResponse(List.of(VisitorProfileFindResponse.from(dto)));
    }

    @Getter
    @RequiredArgsConstructor
    public static class VisitorProfileFindResponse {

        private final Long id;
        private final String nickName;
        private final String phoneNumber;
        private final String email;

        public static VisitorProfilesFindResponse.VisitorProfileFindResponse from(VisitorProfileFindResultDto dto) {
            return new VisitorProfilesFindResponse.VisitorProfileFindResponse(
                    dto.getId(),
                    dto.getNickName(),
                    dto.getPhoneNumber(),
                    dto.getEmail());
        }
    }
}
