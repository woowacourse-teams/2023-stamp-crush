package com.stampcrush.backend.repository.visithistory;

import com.stampcrush.backend.common.KorNamingConverter;
import com.stampcrush.backend.entity.cafe.Cafe;
import com.stampcrush.backend.entity.user.Customer;
import com.stampcrush.backend.entity.user.Owner;
import com.stampcrush.backend.entity.user.RegisterCustomer;
import com.stampcrush.backend.entity.user.TemporaryCustomer;
import com.stampcrush.backend.entity.visithistory.VisitHistory;
import com.stampcrush.backend.repository.cafe.CafeRepository;
import com.stampcrush.backend.repository.user.OwnerRepository;
import com.stampcrush.backend.repository.user.RegisterCustomerRepository;
import com.stampcrush.backend.repository.user.TemporaryCustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@KorNamingConverter
@DataJpaTest
class VisitHistoryRepositoryTest {

    @Autowired
    private VisitHistoryRepository visitHistoryRepository;

    @Autowired
    private RegisterCustomerRepository registerCustomerRepository;

    @Autowired
    private TemporaryCustomerRepository temporaryCustomerRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private CafeRepository cafeRepository;

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

    @Test
    void 특정_카페에_대한_특정_고객의_방문_이력을_조회한다() {
        // given
        Customer customer1 = registerCustomerRepository.save(new RegisterCustomer("jena", "01012345678", "jenaId", "jenaPw"));
        TemporaryCustomer customer2 = temporaryCustomerRepository.save(TemporaryCustomer.from("010000011111"));

        Owner cafe1Owner = ownerRepository.save(new Owner("owner1", "owner1Id", "owner1Pw", "01076532456"));
        Cafe cafe1 = cafeRepository.save(new Cafe("우아한카페",
                LocalTime.NOON,
                LocalTime.MIDNIGHT,
                "01012345678",
                "cafeImageUrl",
                "introduction",
                "roadAddress",
                "detailAddress",
                "buisnessRegistrationNumber",
                cafe1Owner
        ));

        Owner cafe2Owner = ownerRepository.save(new Owner("owner2", "owner2Id", "owner2Pw", "01084735242"));
        Cafe cafe2 = cafeRepository.save(new Cafe(
                "안우아한카페",
                LocalTime.NOON,
                LocalTime.MIDNIGHT,
                "01087352412",
                "cafeImageUrl",
                "introduction",
                "roadAddress",
                "detailAddress",
                "buisnessRegistrationNumber",
                cafe2Owner
        ));

        VisitHistory cafe1Customer1History1 = visitHistoryRepository.save(new VisitHistory(cafe1, customer1, 3));
        VisitHistory cafe1Customer1History2 = visitHistoryRepository.save(new VisitHistory(cafe1, customer1, 5));
        VisitHistory cafe2Customer1History = visitHistoryRepository.save(new VisitHistory(cafe2, customer1, 1));
        VisitHistory cafe1Customer2History = visitHistoryRepository.save(new VisitHistory(cafe1, customer2, 2));
        VisitHistory cafe2Customer2History = visitHistoryRepository.save(new VisitHistory(cafe2, customer2, 7));

        // when
        List<VisitHistory> expected1 = List.of(cafe1Customer1History1, cafe1Customer1History2);
        List<VisitHistory> expected2 = List.of(cafe2Customer2History);

        List<VisitHistory> customer1Cafe1VisitHistory = visitHistoryRepository.findByCafeAndCustomer(cafe1, customer1);
        List<VisitHistory> customer2Cafe2VisitHistory = visitHistoryRepository.findByCafeAndCustomer(cafe2, customer2);

        // then
        assertThat(customer1Cafe1VisitHistory).usingRecursiveComparison().isEqualTo(expected1);
        assertThat(customer2Cafe2VisitHistory).usingRecursiveComparison().isEqualTo(expected2);
    }
}
