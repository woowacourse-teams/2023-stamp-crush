package com.stampcrush.backend.application.visitor.cafe;

import com.stampcrush.backend.application.visitor.cafe.dto.CafeInfoFindByCustomerResultDto;
import com.stampcrush.backend.entity.cafe.Cafe;
import com.stampcrush.backend.exception.CafeNotFoundException;
import com.stampcrush.backend.repository.cafe.CafeRepository;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.Optional;

import static com.stampcrush.backend.fixture.OwnerFixture.OWNER3;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.when;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ExtendWith(MockitoExtension.class)
class VisitorCafeFindServiceTest {

    @InjectMocks
    private VisitorCafeFindService visitorCafeFindService;

    @Mock
    private CafeRepository cafeRepository;

    @Test
    void 고객이_카페_정보를_조회한다() {
        // given
        Long cafeId = 1L;
        Cafe cafe = new Cafe("우아한카페",
                LocalTime.of(12, 30),
                LocalTime.of(18, 30),
                "0211111111",
                "http://www.cafeImage.com",
                "안녕하세요",
                "잠실동12길",
                "14층",
                "11111111",
                OWNER3);
        when(cafeRepository.findById(cafeId)).thenReturn(Optional.of(cafe));

        // when
        CafeInfoFindByCustomerResultDto findCafe = visitorCafeFindService.findCafeById(cafeId);

        // then
        assertThat(findCafe).usingRecursiveComparison().isEqualTo(cafe);
    }

    @Test
    void 존재하지_않는_카페_정보_조회_요청_시_에러_발생한다() {
        // given
        Long notExistcafeId = 1L;
        when(cafeRepository.findById(notExistcafeId)).thenReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() -> visitorCafeFindService.findCafeById(notExistcafeId))
                .isInstanceOf(CafeNotFoundException.class);
    }
}
