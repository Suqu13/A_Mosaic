package garstka.jakub.allegro_mosaic.controllers.v1;

import garstka.jakub.allegro_mosaic.services.MosaicService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(MosaicController.class)
public class MosaicControllerTest {

    private final String URL = "https://maps.wikime321dia.org/osm-intl/11/1121/752.png";
    private final String INVALID_PARAMETER = "Invalid parameter provided";
    private final String INVALID_URL = "Invalid URL provided";
    private final String INVALID_RES = "Too big resolution provided";
    private final String ONLY_ONE_RES = "Two values of resolution have to be provided";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MosaicService mosaicService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void lackOfParameters() throws Exception {
        mockMvc.perform(get("/mozaika"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void invalidRandomValue() throws Exception {
        mockMvc.perform(get("/mozaika?losowo=invalid&zdjecia=" + URL + URL))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", equalTo(INVALID_PARAMETER)));
    }

    @Test
    public void onlyOneResolutionValue() throws Exception {
        mockMvc.perform(get("/mozaika?rozdzielczosc=13131&zdjecia=" + URL + URL))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", equalTo(ONLY_ONE_RES)));
    }

    @Test
    public void invalidResolutionValue() throws Exception {
        when(mosaicService.getMosaic(anyBoolean(), anyInt(), anyInt(), any())).thenThrow(OutOfMemoryError.class);
        mockMvc.perform(get("/mozaika?rozdzielczosc=43214214x13131&zdjecia=" + URL + URL))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", equalTo(INVALID_RES)));
    }

    @Test
    public void invalidUrl() throws Exception {
        when(mosaicService.getMosaic(anyBoolean(), anyInt(), anyInt(), any())).thenThrow(IOException.class);
        mockMvc.perform(get("/mozaika?zdjecia=invalid" + URL))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", equalTo(INVALID_URL)));
    }

    @Test
    public void lackOfUrl() throws Exception {
        when(mosaicService.getMosaic(anyBoolean(), anyInt(), anyInt(), any())).thenThrow(IllegalArgumentException.class);
        mockMvc.perform(get("/mozaika?zdjecia="))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", equalTo(INVALID_PARAMETER)));
    }

    @Test
    public void tooManyUrls() throws Exception {
        when(mosaicService.getMosaic(anyBoolean(), anyInt(), anyInt(), any())).thenThrow(IllegalArgumentException.class);
        mockMvc.perform(get("/mozaika?zdjecia=" + URL + URL + URL + URL + URL + URL + URL + URL + URL))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", equalTo(INVALID_PARAMETER)));
    }


    @Test
    public void providedRequiredParameters() throws Exception {
        mockMvc.perform(get("/mozaika?zdjecia=" + URL + URL + URL + URL + URL + URL + URL))
                .andExpect(status().isOk());
    }

    @Test
    public void providedAllParameters() throws Exception {
        mockMvc.perform(get("/mozaika?losowo=1&rozdzielczosc=100x100&zdjecia=" + URL + URL + URL + URL + URL + URL + URL))
                .andExpect(status().isOk());
    }
}