package com.stampcrush.backend.api.visitor.favorites;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stampcrush.backend.api.ControllerSliceTest;
import com.stampcrush.backend.api.visitor.favorites.request.FavoritesUpdateRequest;
import com.stampcrush.backend.application.visitor.favorites.VisitorFavoritesCommandService;
import com.stampcrush.backend.config.WebMvcConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = VisitorFavoritesCommandApiController.class,
        excludeFilters =
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebMvcConfig.class))
class VisitorFavoritesCommandApiControllerTest extends ControllerSliceTest {

    @Test
    void 즐겨찾기를_등록한다() throws Exception {
        // given
        FavoritesUpdateRequest request = new FavoritesUpdateRequest(true);

        doNothing().when(visitorFavoritesCommandService).changeFavorites(any(), any(), any());


        // when, then
        mockMvc.perform(
                        post("/api/cafes/1/favorites")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void 변경하려는_favorite_값이_null_이면_상태코드가_400_이다() throws Exception {
        // given
        FavoritesUpdateRequest request = new FavoritesUpdateRequest(null);

        // when, then
        mockMvc.perform(
                        post("/api/cafes/1/favorites")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
