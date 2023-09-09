package com.stampcrush.backend.api.visitor.profile;

import lombok.Getter;

@Getter
public class VisitorProfilesLinkDataRequest {

    private Long id;

    public VisitorProfilesLinkDataRequest(Long id) {
        this.id = id;
    }

    public VisitorProfilesLinkDataRequest() {
    }

    public VisitorProfilesLinkDataDto toDto() {
        return new VisitorProfilesLinkDataDto(id);
    }
}
