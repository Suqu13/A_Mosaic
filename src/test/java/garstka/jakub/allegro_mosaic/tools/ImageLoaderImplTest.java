package garstka.jakub.allegro_mosaic.tools;

import org.junit.Test;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

public class ImageLoaderImplTest {
    private final ImageLoader tester = new ImageLoaderImpl();
    private final String INVALID_URL = "invalid";
    private final String VALID_URL = "https://avatars0.githubusercontent.com/u/37179062?s=460&v=4";
    private final String VALID_BLANK_URL = "http://blank.org/";


    @Test(expected = IOException.class)
    public void loadImagesToListForInvalidUrls() throws IOException {
        tester.loadImagesToList(List.of(INVALID_URL));
    }

    @Test
    public void loadImagesToListForValidUrls() throws IOException {
        List<BufferedImage> sample = tester.loadImagesToList(List.of(VALID_URL,VALID_URL));
        assertEquals(2, sample.size());
    }

    @Test
    public void loadImagesToListForUrlWithoutImages() throws IOException {
        List<BufferedImage> sample = tester.loadImagesToList(List.of(VALID_BLANK_URL, VALID_BLANK_URL));
        assertEquals(0, sample.size());
    }

}