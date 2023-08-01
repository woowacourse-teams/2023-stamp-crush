package com.stampcrush.backend.application.manager.cafe;

import com.stampcrush.backend.application.manager.cafe.dto.CafeFindResultDto;
import com.stampcrush.backend.entity.cafe.Cafe;
import com.stampcrush.backend.repository.cafe.CafeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ManagerCafeFindService {

    private final CafeRepository cafeRepository;

    public List<CafeFindResultDto> findCafesByOwner(Long ownerId) {
        List<Cafe> cafes = cafeRepository.findAllByOwnerId(ownerId);
        return cafes.stream()
                .map(CafeFindResultDto::from)
                .toList();
    }
}
