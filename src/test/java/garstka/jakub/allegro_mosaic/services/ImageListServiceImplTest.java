package garstka.jakub.allegro_mosaic.services;

import garstka.jakub.allegro_mosaic.api.v1.model.ImageDTO;
import garstka.jakub.allegro_mosaic.api.v1.model.ImageListDTO;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.*;

public class ImageListServiceImplTest {

    private List<String> testingUrls = Arrays.asList("image_0",
            "image_1",
            "image_2",
            "image_3",
            "image_4",
            "image_5");

    private ImageDTO[] tester = {new ImageDTO("image_0", 1, 2),
            new ImageDTO("image_1", 1, 2),
            new ImageDTO("image_2", 1, 2),
            new ImageDTO("image_3", 1, 2),
            new ImageDTO("image_4", 1, 2),
            new ImageDTO("image_5", 1, 2)};

    private ImageListService imageListService = new ImageListServiceImpl();

    @Test
    public void getDecodedImageListDTOTestWithShuffle() {
        ImageListDTO imageListDTO = imageListService.getDecodedImageListDTO(false, 1, 2, testingUrls);
        assertThat(imageListDTO.getImageDTOList(), contains(tester));
        assertEquals(imageListDTO.getImageDTOList(), Arrays.asList(tester));

    }

    @Test
    public void getDecodedImageListDTOTestWithoutShuffle() {
        ImageListDTO imageListDTO = imageListService.getDecodedImageListDTO(true, 1, 2, testingUrls);
        assertThat(imageListDTO.getImageDTOList(), containsInAnyOrder(tester));
        assertNotEquals(imageListDTO.getImageDTOList(), Arrays.asList(tester));
    }
}