package com.stampcrush.backend.application.visitor.cafe;

import com.stampcrush.backend.application.ServiceSliceTest;
import com.stampcrush.backend.application.visitor.cafe.dto.CafeInfoFindByCustomerResultDto;
import com.stampcrush.backend.entity.cafe.Cafe;
import com.stampcrush.backend.exception.CafeNotFoundException;
import com.stampcrush.backend.repository.cafe.CafeRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static com.stampcrush.backend.fixture.CafeFixture.GITCHAN_CAFE;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ServiceSliceTest
class VisitorCafeFindServiceTest {

    @InjectMocks
    private VisitorCafeFindService visitorCafeFindService;

    @Mock
    private CafeRepository cafeRepository;

    @Test
    void 고객이_카페_정보를_조회한다() {
        // given
        Long cafeId = 1L;
        Cafe cafe = GITCHAN_CAFE;
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
