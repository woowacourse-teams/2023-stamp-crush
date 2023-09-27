package com.stampcrush.backend.api;

import com.stampcrush.backend.auth.application.util.AuthTokensGenerator;
import com.stampcrush.backend.auth.application.util.JwtTokenProvider;
import com.stampcrush.backend.auth.repository.BlackListRepository;
import com.stampcrush.backend.common.KorNamingConverter;
import com.stampcrush.backend.repository.cafe.CafeRepository;
import com.stampcrush.backend.repository.user.CustomerRepository;
import com.stampcrush.backend.repository.user.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.web.servlet.MockMvc;

@KorNamingConverter
@WebMvcTest
public abstract class ControllerSliceTest {

    @Autowired
    public MockMvc mockMvc;

    @MockBean
    public BlackListRepository blackListRepository;

    @MockBean
    public OwnerRepository ownerRepository;

    @MockBean
    public CustomerRepository customerRepository;

    @MockBean
    public CafeRepository cafeRepository;

    @SpyBean
    public AuthTokensGenerator authTokensGenerator;

    @SpyBean
    public JwtTokenProvider jwtTokenProvider;
}
