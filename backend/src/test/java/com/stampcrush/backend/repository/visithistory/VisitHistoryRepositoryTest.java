package com.stampcrush.backend.repository.visithistory;

import com.stampcrush.backend.common.KorNamingConverter;
import com.stampcrush.backend.entity.cafe.Cafe;
import com.stampcrush.backend.entity.user.Customer;
import com.stampcrush.backend.entity.user.Owner;
import com.stampcrush.backend.entity.visithistory.VisitHistory;
import com.stampcrush.backend.fixture.CustomerFixture;
import com.stampcrush.backend.fixture.OwnerFixture;
import com.stampcrush.backend.repository.cafe.CafeRepository;
import com.stampcrush.backend.repository.user.CustomerRepository;
import com.stampcrush.backend.repository.user.OwnerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@KorNamingConverter
@DataJpaTest
class VisitHistoryRepositoryTest {

    @Autowired
    private VisitHistoryRepository visitHistoryRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private CafeRepository cafeRepository;

    private Customer temporaryCustomer1;
    private Customer temporaryCustomer2;

    @BeforeEach
    void setUp() {
        temporaryCustomer1 = customerRepository.save(Customer.temporaryCustomerBuilder()
                .phoneNumber("01012345678")
                .build());

        temporaryCustomer2 = customerRepository.save(Customer.temporaryCustomerBuilder()
                .phoneNumber("01087654321")
                .build());
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
        Customer registered = Customer.registeredCustomerBuilder()
                .nickname("jena")
                .phoneNumber("01012345678")
                .build();
        Customer customer1 = customerRepository.save(registered);
        Customer customer2 = customerRepository.save(Customer.temporaryCustomerBuilder()
                .phoneNumber("010000011111")
                .build());

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

    @Test
    void 카페_아이디와_고객_아이디로_방문횟수를_조회한다() {
        Customer registered = Customer.registeredCustomerBuilder()
                .nickname("jena")
                .phoneNumber("01012345678")
                .build();
        Customer customer1 = customerRepository.save(registered);

        Customer customer2 = customerRepository.save(Customer.temporaryCustomerBuilder()
                .phoneNumber("010000011111")
                .build());

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

        VisitHistory visitHistory1 = visitHistoryRepository.save(new VisitHistory(cafe1, customer1, 3));
        VisitHistory visitHistory2 = visitHistoryRepository.save(new VisitHistory(cafe1, customer2, 5));

        assertThat(visitHistoryRepository.findByCafeIdAndCustomerId(cafe1.getId(), customer1.getId())).containsExactly(visitHistory1);
    }

    @Test
    void 해당_고객의_방문_이력을_삭제한다() {
        Cafe cafe = createCafe(OwnerFixture.GITCHAN);
        Customer deletedCustomer = customerRepository.save(CustomerFixture.REGISTER_CUSTOMER_GITCHAN);
        Customer noDeletedCustomer = customerRepository.save(CustomerFixture.REGISTER_CUSTOMER_1);

        visitHistoryRepository.save(new VisitHistory(cafe, deletedCustomer, 3));
        visitHistoryRepository.save(new VisitHistory(cafe, deletedCustomer, 1));

        visitHistoryRepository.save(new VisitHistory(cafe, noDeletedCustomer, 3));

        visitHistoryRepository.deleteByCustomer(deletedCustomer.getId());

        assertAll(
                () -> assertThat(visitHistoryRepository.findByCustomer(deletedCustomer)).isEmpty(),
                () -> assertThat(visitHistoryRepository.findByCustomer(noDeletedCustomer)).isNotEmpty()
        );
    }


    private Cafe createCafe(Owner owner) {
        Owner savedOwner = ownerRepository.save(owner);
        return cafeRepository.save(
                new Cafe(
                        "깃짱카페",
                        "서초구",
                        "어쩌고",
                        "0101010101",
                        savedOwner
                )
        );
    }
}
