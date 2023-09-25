package com.stampcrush.backend.entity.visithistory;

import com.stampcrush.backend.common.KorNamingConverter;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

@KorNamingConverter
class VisitHistoriesTest {

    @Test
    void 첫_방문_일자를_계산한다() {
        // given
        LocalDateTime firstVisitDate = LocalDateTime.of(2023, 5, 12, 4, 30);
        LocalDateTime secondVisitDate = LocalDateTime.of(2023, 6, 12, 4, 30);
        VisitHistory firstVisitHistory = new VisitHistory(firstVisitDate, null, null, null, 1);
        VisitHistory secondVisitHistory = new VisitHistory(secondVisitDate, null, null, null, 1);

        // when
        VisitHistories visitHistories = new VisitHistories(List.of(firstVisitHistory, secondVisitHistory));
        LocalDateTime firstVisitDateResult = visitHistories.getFirstVisitDate();

        // then
        LocalDateTime expected = LocalDateTime.of(2023, 5, 12, 4, 30);
        Assertions.assertThat(firstVisitDateResult).isEqualTo(expected);
    }
}
