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

@RunWith(SpringRunner.class)
@WebMvcTest(MosaicController.class)
public class MosaicControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MosaicService mosaicService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void invalidUrls() {
//        when(mosaicService.)
    }

}