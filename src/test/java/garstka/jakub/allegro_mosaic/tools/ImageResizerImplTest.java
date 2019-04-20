package garstka.jakub.allegro_mosaic.tools;

import org.junit.Test;

import java.awt.image.BufferedImage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class ImageResizerImplTest {

    private final ImageResizer tester = new ImageResizerImpl();
    private BufferedImage bufferedImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);

    @Test(expected = IllegalArgumentException.class)
    public void invalidWidthGiven(){
        tester.resizeImage(bufferedImage, -1, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidHeightGiven(){
        tester.resizeImage(bufferedImage, 1, -1);
    }

    @Test
    public void checkIfNotResized() {
        BufferedImage sample = tester.resizeImage(bufferedImage, 100, 100);
        assertEquals(bufferedImage.getWidth(), sample.getWidth());
        assertEquals(bufferedImage.getHeight(), sample.getHeight());
    }

    @Test
    public void checkIfResized() {
        BufferedImage sample = tester.resizeImage(bufferedImage, 200, 100);
        assertNotEquals(bufferedImage.getWidth(), sample.getWidth());
        assertEquals(bufferedImage.getHeight(), sample.getHeight());
    }

    @Test
    public void checkIfResizedTwice() {
        BufferedImage sample = tester.resizeImage(bufferedImage, 200, 200);
        assertEquals(bufferedImage.getWidth() * 2, sample.getWidth());
        assertEquals(bufferedImage.getHeight() * 2, sample.getHeight());
    }
}