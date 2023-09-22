package com.stampcrush.backend.api.visitor.profile;

import com.stampcrush.backend.application.visitor.profile.dto.VisitorProfilesLinkDataDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VisitorProfilesLinkDataRequest {

    private Long id;

    public VisitorProfilesLinkDataDto toDto() {
        return new VisitorProfilesLinkDataDto(id);
    }
}
