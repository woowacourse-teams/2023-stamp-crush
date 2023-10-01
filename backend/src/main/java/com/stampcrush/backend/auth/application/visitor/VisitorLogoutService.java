package com.stampcrush.backend.auth.application.visitor;

import com.stampcrush.backend.auth.entity.BlackList;
import com.stampcrush.backend.auth.repository.BlackListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class VisitorLogoutService {

    private final BlackListRepository blackListRepository;

    public void logout(String refreshToken) {
        blackListRepository.save(new BlackList(refreshToken));
    }
}
