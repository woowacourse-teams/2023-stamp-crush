package com.stampcrush.backend.api.docs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stampcrush.backend.api.manager.cafe.ManagerCafeCommandApiController;
import com.stampcrush.backend.api.manager.cafe.ManagerCafeCouponSettingCommandApiController;
import com.stampcrush.backend.api.manager.cafe.ManagerCafeCouponSettingFindApiController;
import com.stampcrush.backend.api.manager.cafe.ManagerCafeFindApiController;
import com.stampcrush.backend.api.manager.coupon.ManagerCouponCommandApiController;
import com.stampcrush.backend.api.manager.coupon.ManagerCouponFindApiController;
import com.stampcrush.backend.api.manager.customer.ManagerCustomerCommandApiController;
import com.stampcrush.backend.api.manager.customer.ManagerCustomerFindApiController;
import com.stampcrush.backend.api.manager.image.ManagerImageCommandApiController;
import com.stampcrush.backend.api.manager.reward.ManagerRewardCommandApiController;
import com.stampcrush.backend.api.manager.reward.ManagerRewardFindApiController;
import com.stampcrush.backend.api.manager.sample.ManagerSampleCouponFindApiController;
import com.stampcrush.backend.api.visitor.cafe.VisitorCafeFindApiController;
import com.stampcrush.backend.api.visitor.coupon.VisitorCouponCommandApiController;
import com.stampcrush.backend.api.visitor.coupon.VisitorCouponFindApiController;
import com.stampcrush.backend.api.visitor.favorites.VisitorFavoritesCommandApiController;
import com.stampcrush.backend.api.visitor.profile.VisitorCancelMembershipCommandApiController;
import com.stampcrush.backend.api.visitor.profile.VisitorProfilesCommandApiController;
import com.stampcrush.backend.api.visitor.profile.VisitorProfilesFindApiController;
import com.stampcrush.backend.api.visitor.reward.VisitorRewardsFindController;
import com.stampcrush.backend.api.visitor.visithistory.VisitorVisitHistoryFindApiController;
import com.stampcrush.backend.application.manager.cafe.ManagerCafeCommandService;
import com.stampcrush.backend.application.manager.cafe.ManagerCafeCouponSettingCommandService;
import com.stampcrush.backend.application.manager.cafe.ManagerCafeCouponSettingFindService;
import com.stampcrush.backend.application.manager.cafe.ManagerCafeFindService;
import com.stampcrush.backend.application.manager.coupon.ManagerCouponCommandService;
import com.stampcrush.backend.application.manager.coupon.ManagerCouponFindService;
import com.stampcrush.backend.application.manager.customer.ManagerCustomerCommandService;
import com.stampcrush.backend.application.manager.customer.ManagerCustomerFindService;
import com.stampcrush.backend.application.manager.image.ManagerImageCommandService;
import com.stampcrush.backend.application.manager.reward.ManagerRewardCommandService;
import com.stampcrush.backend.application.manager.reward.ManagerRewardFindService;
import com.stampcrush.backend.application.manager.sample.ManagerSampleCouponFindService;
import com.stampcrush.backend.application.visitor.cafe.VisitorCafeFindService;
import com.stampcrush.backend.application.visitor.coupon.VisitorCouponCommandService;
import com.stampcrush.backend.application.visitor.coupon.VisitorCouponFindService;
import com.stampcrush.backend.application.visitor.favorites.VisitorFavoritesCommandService;
import com.stampcrush.backend.application.visitor.profile.VisitorCancelMembershipCommandService;
import com.stampcrush.backend.application.visitor.profile.VisitorProfilesCommandService;
import com.stampcrush.backend.application.visitor.profile.VisitorProfilesFindService;
import com.stampcrush.backend.application.visitor.reward.VisitorRewardsFindService;
import com.stampcrush.backend.application.visitor.visithistory.VisitorVisitHistoryFindService;
import com.stampcrush.backend.auth.api.ManagerOAuthController;
import com.stampcrush.backend.auth.application.manager.ManagerOAuthLoginService;
import com.stampcrush.backend.auth.application.manager.ManagerOAuthService;
import com.stampcrush.backend.auth.application.util.AuthTokensGenerator;
import com.stampcrush.backend.common.KorNamingConverter;
import com.stampcrush.backend.entity.cafe.Cafe;
import com.stampcrush.backend.entity.user.Customer;
import com.stampcrush.backend.entity.user.Owner;
import com.stampcrush.backend.helper.BearerAuthHelper;
import com.stampcrush.backend.repository.cafe.CafeRepository;
import com.stampcrush.backend.repository.user.CustomerRepository;
import com.stampcrush.backend.repository.user.OwnerRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.time.LocalTime;
import java.util.Base64;

import static com.stampcrush.backend.fixture.CustomerFixture.REGISTER_CUSTOMER_GITCHAN_SAVED;
import static com.stampcrush.backend.fixture.OwnerFixture.OWNER3;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@KorNamingConverter
@AutoConfigureRestDocs
@AutoConfigureMockMvc
@Execution(ExecutionMode.SAME_THREAD)
@WebMvcTest({
        ManagerCafeFindApiController.class,
        VisitorCafeFindApiController.class,
        ManagerCustomerFindApiController.class,
        ManagerCustomerCommandApiController.class,
        VisitorCouponFindApiController.class,
        VisitorFavoritesCommandApiController.class,
        ManagerCafeCommandApiController.class,
        ManagerCafeCouponSettingCommandApiController.class,
        ManagerSampleCouponFindApiController.class,
        ManagerCouponCommandApiController.class,
        ManagerCouponFindApiController.class,
        ManagerRewardCommandApiController.class,
        ManagerRewardFindApiController.class,
        VisitorCouponCommandApiController.class,
        VisitorRewardsFindController.class,
        VisitorVisitHistoryFindApiController.class,
        VisitorProfilesCommandApiController.class,
        VisitorCancelMembershipCommandApiController.class,
        VisitorProfilesFindApiController.class,
        ManagerCafeCouponSettingFindApiController.class,
        ManagerImageCommandApiController.class,
        ManagerOAuthController.class
})
@ExtendWith({RestDocumentationExtension.class})
public abstract class DocsControllerTest {

    protected static final Long CAFE_ID = 1L;
    protected static final Owner OWNER = OWNER3;
    protected static final Cafe CAFE = new Cafe(CAFE_ID, "cafe", LocalTime.NOON, LocalTime.MIDNIGHT,
            "010123432445", "imageUrl", "intro", "road", "detail", "1234", OWNER);
    protected static final Customer CUSTOMER = REGISTER_CUSTOMER_GITCHAN_SAVED;

    protected static String OWNER_BEARER_HEADER;
    protected static String CUSTOMER_BEARER_HEADER;

    protected MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext ctx;
    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected CafeRepository cafeRepository;
    @MockBean
    protected OwnerRepository ownerRepository;
    @MockBean
    protected CustomerRepository customerRepository;
    @MockBean
    protected AuthTokensGenerator authTokensGenerator;

    @MockBean
    protected ManagerCafeFindService managerCafeFindService;
    @MockBean
    protected VisitorCafeFindService visitorCafeFindService;
    @MockBean
    protected ManagerCustomerFindService managerCustomerFindService;
    @MockBean
    protected ManagerCustomerCommandService managerCustomerCommandService;
    @MockBean
    protected VisitorCouponFindService visitorCouponFindService;
    @MockBean
    protected VisitorFavoritesCommandService visitorFavoritesCommandService;
    @MockBean
    protected ManagerCafeCommandService managerCafeCommandService;
    @MockBean
    protected ManagerCafeCouponSettingCommandService managerCafeCouponSettingCommandService;
    @MockBean
    protected ManagerSampleCouponFindService managerSampleCouponFindService;
    @MockBean
    protected ManagerCouponCommandService managerCouponCommandService;
    @MockBean
    protected ManagerCouponFindService managerCouponFindService;
    @MockBean
    protected ManagerRewardCommandService managerRewardCommandService;
    @MockBean
    protected ManagerRewardFindService managerRewardFindService;
    @MockBean
    protected VisitorCouponCommandService visitorCouponCommandService;
    @MockBean
    protected VisitorRewardsFindService visitorRewardsFindService;
    @MockBean
    protected VisitorVisitHistoryFindService visitorVisitHistoryFindService;
    @MockBean
    protected VisitorProfilesCommandService visitorProfilesCommandService;
    @MockBean
    protected VisitorCancelMembershipCommandService visitorCancelMembershipCommandService;
    @MockBean
    protected VisitorProfilesFindService visitorProfilesFindService;
    @MockBean
    protected ManagerCafeCouponSettingFindService managerCafeCouponSettingFindService;
    @MockBean
    protected ManagerImageCommandService managerImageCommandService;
    @MockBean
    protected ManagerOAuthService managerOAuthService;
    @MockBean
    protected ManagerOAuthLoginService managerOAuthLoginService;

    @BeforeAll
    static void setUpAuth() {
        OWNER_BEARER_HEADER = "Bearer " + BearerAuthHelper.generateToken(OWNER.getId());
        CUSTOMER_BEARER_HEADER = "Bearer " + BearerAuthHelper.generateToken(CUSTOMER.getId());
    }

    @BeforeEach
    void setUp(final RestDocumentationContextProvider restDocumentation) {
        mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
                .apply(documentationConfiguration(restDocumentation))
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(print())
                .build();
    }
}
