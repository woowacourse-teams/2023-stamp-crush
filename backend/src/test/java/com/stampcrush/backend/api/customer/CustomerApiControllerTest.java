package com.stampcrush.backend.api.customer;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.stampcrush.backend.api.BaseControllerTest;
import com.stampcrush.backend.api.customer.request.TemporaryCustomerCreateRequest;
import com.stampcrush.backend.application.customer.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;

@WebMvcTest(CustomerApiController.class)
@MockBean(JpaMetamodelMappingContext.class)
class CustomerApiControllerTest extends BaseControllerTest {

    @MockBean
    private CustomerService customerService;

    @Test
    void 임시_가입_고객을_생성한다() throws Exception {
        TemporaryCustomerCreateRequest request = new TemporaryCustomerCreateRequest("01012345678");
        Long customerId = 1L;

        when(customerService.createTemporaryCustomer(request.getPhoneNumber()))
                .thenReturn(customerId);

        ResultActions resultActions = mockMvc.perform(
                        post("/api/temporary-customers")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(
                        MockMvcRestDocumentationWrapper.document("customer docs",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                resource(
                                        ResourceSnippetParameters.builder()
                                                .description("임시고객 생성")
                                                .requestFields(fieldWithPath("phoneNumber").description("전화번호"))
                                                .build()
                                )
                        )
                );
    }
}
