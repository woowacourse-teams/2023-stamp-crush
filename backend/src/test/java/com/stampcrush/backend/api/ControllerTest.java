package com.stampcrush.backend.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stampcrush.backend.api.manager.cafe.ManagerCafeFindApiController;
import com.stampcrush.backend.api.manager.customer.ManagerCustomerCommandApiController;
import com.stampcrush.backend.api.manager.customer.ManagerCustomerFindApiController;
import com.stampcrush.backend.api.visitor.cafe.VisitorCafeFindApiController;
import com.stampcrush.backend.application.manager.cafe.ManagerCafeFindService;
import com.stampcrush.backend.application.manager.customer.ManagerCustomerCommandService;
import com.stampcrush.backend.application.manager.customer.ManagerCustomerFindService;
import com.stampcrush.backend.application.visitor.cafe.VisitorCafeFindService;
import com.stampcrush.backend.repository.user.OwnerRepository;
import com.stampcrush.backend.repository.user.RegisterCustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
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

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@WebMvcTest({ManagerCafeFindApiController.class,
        VisitorCafeFindApiController.class,
        ManagerCustomerFindApiController.class,
        ManagerCustomerCommandApiController.class
})
@ExtendWith({RestDocumentationExtension.class})
public abstract class ControllerTest {

    protected MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext ctx;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected ManagerCafeFindService managerCafeFindService;

    @MockBean
    protected OwnerRepository ownerRepository;

    @MockBean
    protected RegisterCustomerRepository customerRepository;

    @MockBean
    protected VisitorCafeFindService visitorCafeFindService;

    @MockBean
    protected ManagerCustomerFindService managerCustomerFindService;

    @MockBean
    protected ManagerCustomerCommandService managerCustomerCommandService;

    @BeforeEach
    void setUp(final RestDocumentationContextProvider restDocumentation) {
        mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
                .apply(documentationConfiguration(restDocumentation))
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(print())
                .build();
    }
}
