package garstka.jakub.allegro_mosaic.tools;

import org.junit.Test;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

public class ImageLoaderImplTest {
    private final ImageLoader tester = new ImageLoaderImpl();

    @Test(expected = IOException.class)
    public void loadImagesToListForInvalidUrls() throws IOException {
        tester.loadImagesToList(List.of("cos"));
    }

    @Test
    public void loadImagesToListForValidUrls() throws IOException {
        List<BufferedImage> sample = tester.loadImagesToList(List.of("https://avatars0.githubusercontent.com/u/37179062?s=460&v=4",
                "https://avatars0.githubusercontent.com/u/37179062?s=460&v=4")
        );
        assertEquals(2, sample.size());
    }

    @Test
    public void loadImagesToListForUrlWithoutImages() throws IOException {
        List<BufferedImage> sample = tester.loadImagesToList(List.of("http://blank.org/", "http://blank.org/"));
        assertEquals(0, sample.size());
    }

}