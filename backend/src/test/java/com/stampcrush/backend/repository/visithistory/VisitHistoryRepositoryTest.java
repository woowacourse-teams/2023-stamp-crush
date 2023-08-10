package com.stampcrush.backend.repository.visithistory;

import com.stampcrush.backend.entity.user.TemporaryCustomer;
import com.stampcrush.backend.entity.visithistory.VisitHistory;
import com.stampcrush.backend.repository.user.TemporaryCustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class VisitHistoryRepositoryTest {

    @Autowired
    private VisitHistoryRepository visitHistoryRepository;

    @Autowired
    private TemporaryCustomerRepository temporaryCustomerRepository;

    private TemporaryCustomer temporaryCustomer1;
    private TemporaryCustomer temporaryCustomer2;

    @BeforeEach
    void setUp() {
        temporaryCustomer1 = temporaryCustomerRepository.save(TemporaryCustomer.from("1234"));
        temporaryCustomer2 = temporaryCustomerRepository.save(TemporaryCustomer.from("5678"));
    }

    @Test
    void 고객별_스탬프_적립_내역을_조회한다() {
        // given
        VisitHistory visitHistory1 = new VisitHistory(null, temporaryCustomer1, 3);
        VisitHistory visitHistory2 = new VisitHistory(null, temporaryCustomer1, 2);
        VisitHistory visitHistory3 = new VisitHistory(null, temporaryCustomer2, 6);

        // when
        visitHistoryRepository.save(visitHistory1);
        visitHistoryRepository.save(visitHistory2);
        visitHistoryRepository.save(visitHistory3);
        List<VisitHistory> visitHistoriesByCustomer = visitHistoryRepository.findVisitHistoriesByCustomer(temporaryCustomer1);

        // then
        assertThat(visitHistoriesByCustomer).hasSize(2);
    }
}
