package com.stampcrush.backend.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stampcrush.backend.api.manager.cafe.ManagerCafeCouponSettingCommandApiController;
import com.stampcrush.backend.api.manager.cafe.ManagerCafeCouponSettingFindApiController;
import com.stampcrush.backend.api.manager.cafe.ManagerCafeFindApiController;
import com.stampcrush.backend.api.manager.coupon.ManagerCouponCommandApiController;
import com.stampcrush.backend.api.manager.coupon.ManagerCouponFindApiController;
import com.stampcrush.backend.api.manager.owner.ManagerCommandApiController;
import com.stampcrush.backend.api.manager.reward.ManagerRewardCommandApiController;
import com.stampcrush.backend.api.manager.reward.ManagerRewardFindApiController;
import com.stampcrush.backend.api.manager.sample.ManagerSampleCouponFindApiController;
import com.stampcrush.backend.api.visitor.cafe.VisitorCafeFindApiController;
import com.stampcrush.backend.api.visitor.coupon.VisitorCouponCommandApiController;
import com.stampcrush.backend.api.visitor.coupon.VisitorCouponFindApiController;
import com.stampcrush.backend.api.visitor.favorites.VisitorFavoritesCommandApiController;
import com.stampcrush.backend.api.visitor.profile.VisitorProfilesCommandApiController;
import com.stampcrush.backend.api.visitor.profile.VisitorProfilesFindApiController;
import com.stampcrush.backend.api.visitor.reward.VisitorRewardsFindController;
import com.stampcrush.backend.api.visitor.visithistory.VisitorVisitHistoryFindApiController;
import com.stampcrush.backend.application.manager.cafe.ManagerCafeCouponSettingCommandService;
import com.stampcrush.backend.application.manager.cafe.ManagerCafeCouponSettingFindService;
import com.stampcrush.backend.application.manager.cafe.ManagerCafeFindService;
import com.stampcrush.backend.application.manager.coupon.ManagerCouponCommandService;
import com.stampcrush.backend.application.manager.coupon.ManagerCouponFindService;
import com.stampcrush.backend.application.manager.owner.ManagerCommandService;
import com.stampcrush.backend.application.manager.reward.ManagerRewardCommandService;
import com.stampcrush.backend.application.manager.reward.ManagerRewardFindService;
import com.stampcrush.backend.application.manager.sample.ManagerSampleCouponFindService;
import com.stampcrush.backend.application.visitor.cafe.VisitorCafeFindService;
import com.stampcrush.backend.application.visitor.coupon.VisitorCouponCommandService;
import com.stampcrush.backend.application.visitor.coupon.VisitorCouponFindService;
import com.stampcrush.backend.application.visitor.favorites.VisitorFavoritesCommandService;
import com.stampcrush.backend.application.visitor.profile.VisitorProfilesCommandService;
import com.stampcrush.backend.application.visitor.profile.VisitorProfilesFindService;
import com.stampcrush.backend.application.visitor.reward.VisitorRewardsFindService;
import com.stampcrush.backend.application.visitor.visithistory.VisitorVisitHistoryFindService;
import com.stampcrush.backend.auth.application.util.AuthTokensGenerator;
import com.stampcrush.backend.auth.application.util.JwtTokenProvider;
import com.stampcrush.backend.common.KorNamingConverter;
import com.stampcrush.backend.config.WebMvcConfig;
import com.stampcrush.backend.repository.cafe.CafeRepository;
import com.stampcrush.backend.repository.user.CustomerRepository;
import com.stampcrush.backend.repository.user.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.web.servlet.MockMvc;

@KorNamingConverter
@WebMvcTest(value = {ManagerCafeCouponSettingCommandApiController.class,
        ManagerCafeCouponSettingFindApiController.class,
        ManagerCafeFindApiController.class,
        ManagerCouponCommandApiController.class,
        ManagerCouponFindApiController.class,
        ManagerCommandApiController.class,
        ManagerRewardFindApiController.class,
        ManagerRewardCommandApiController.class,
        ManagerSampleCouponFindApiController.class,
        VisitorCafeFindApiController.class,
        VisitorCouponCommandApiController.class,
        VisitorCouponFindApiController.class,
        VisitorFavoritesCommandApiController.class,
        VisitorProfilesCommandApiController.class,
        VisitorProfilesFindApiController.class,
        VisitorRewardsFindController.class,
        VisitorVisitHistoryFindApiController.class
},
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebMvcConfig.class))
public abstract class ControllerSliceTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected ManagerCafeCouponSettingCommandService managerCafeCouponSettingCommandService;

    @MockBean
    protected ManagerCafeCouponSettingFindService cafeCouponSettingFindService;

    @MockBean
    protected ManagerCafeFindService managerCafeFindService;

    @MockBean
    protected ManagerCouponCommandService managerCouponCommandService;

    @MockBean
    protected ManagerCouponFindService managerCouponFindService;

    @MockBean
    protected ManagerCommandService managerCommandService;

    @MockBean
    protected ManagerRewardFindService managerRewardFindService;

    @MockBean
    protected ManagerRewardCommandService managerRewardCommandService;

    @MockBean
    protected ManagerSampleCouponFindService managerSampleCouponFindService;

    @MockBean
    protected VisitorCafeFindService visitorCafeFindService;

    @MockBean
    protected VisitorCouponCommandService visitorCouponCommandService;

    @MockBean
    protected VisitorCouponFindService visitorCouponFindService;

    @MockBean
    protected VisitorFavoritesCommandService visitorFavoritesCommandService;

    @MockBean
    protected VisitorProfilesFindService visitorProfilesFindService;

    @MockBean
    protected VisitorProfilesCommandService visitorProfilesCommandService;

    @MockBean
    protected VisitorRewardsFindService visitorRewardsFindService;

    @MockBean
    protected VisitorVisitHistoryFindService visitorVisitHistoryFindService;

    @MockBean
    private OwnerRepository ownerRepository;

    @MockBean
    private CustomerRepository customerRepository;

    @MockBean
    private CafeRepository cafeRepository;

    @SpyBean
    private AuthTokensGenerator authTokensGenerator;

    @SpyBean
    private JwtTokenProvider jwtTokenProvider;
}
