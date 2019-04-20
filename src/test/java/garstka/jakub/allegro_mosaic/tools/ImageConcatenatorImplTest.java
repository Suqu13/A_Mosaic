package garstka.jakub.allegro_mosaic.tools;

import org.junit.Test;

import java.awt.image.BufferedImage;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class ImageConcatenatorImplTest {

    private ImageConcatenator imageConcatenator = new ImageConcatenatorImpl();

    private List<BufferedImage> tester = List.of(
            new BufferedImage(180, 200, BufferedImage.TYPE_INT_RGB),
            new BufferedImage(190, 210, BufferedImage.TYPE_INT_RGB),
            new BufferedImage(200, 220, BufferedImage.TYPE_INT_RGB)
    );


    @Test
    public void checkHeightCorrectnessForVerticalConcatenation() {
        int totalHeight = tester.stream().map(BufferedImage::getHeight).reduce(0, Integer::sum);
        BufferedImage sample = imageConcatenator.concatenateVertically(tester);
        assertEquals(totalHeight, sample.getHeight());
    }

    //NotEquals because this method works only for images with the same size, final size depends on the size of the first one
    @Test
    public void checkWidthCorrectnessForVerticalConcatenation() {
        int totalWidth = tester.stream().map(BufferedImage::getTileWidth).reduce(0, Integer::sum);
        BufferedImage sample = imageConcatenator.concatenateVertically(tester);
        assertNotEquals(totalWidth, sample.getWidth());
        assertEquals(tester.get(0).getWidth(), sample.getWidth());
    }

    //NotEquals because this method works only for images with the same size, final size depends on the size of the first one
    @Test
    public void checkHeightCorrectnessForHorizontalConcatenation() {
        int totalHeight = tester.stream().map(BufferedImage::getHeight).reduce(0, Integer::sum);
        BufferedImage sample = imageConcatenator.concatenateHorizontally(tester);
        assertNotEquals(totalHeight, sample.getHeight());
        assertEquals(tester.get(0).getHeight(), sample.getHeight());
    }

    @Test
    public void checkWidthCorrectnessForHorizontalConcatenation() {
        int totalWidth = tester.stream().map(BufferedImage::getTileWidth).reduce(0, Integer::sum);
        BufferedImage sample = imageConcatenator.concatenateHorizontally(tester);
        assertEquals(totalWidth, sample.getWidth());
    }

    @Test
    public void checkTypeCorrectness() {
        BufferedImage sample = imageConcatenator.concatenateHorizontally(tester);
        assertEquals(BufferedImage.TYPE_INT_RGB, sample.getType());
    }

}