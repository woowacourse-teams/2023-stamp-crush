package com.stampcrush.backend.application.manager.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class StampCreateEvent {

    private final String cafeName;
    private final String userPhone;
    private final int stampCount;
}
