package garstka.jakub.allegro_mosaic.tools;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ImageConcatenatorImpl implements ImageConcatenator {

    //Both function works great for images with exactly the same size

    public BufferedImage concatenateVertically(List<BufferedImage> images) {
        int maxHeight = images.stream().map(BufferedImage::getHeight).reduce(0, Integer::sum);

        BufferedImage concatenatedImage = new BufferedImage(images.get(0).getWidth(), maxHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = concatenatedImage.createGraphics();
        AtomicInteger currentHeight = new AtomicInteger(0);
        images.forEach(image ->  {
            g2d.drawImage(image, 0, currentHeight.get(), null);
            currentHeight.addAndGet(image.getHeight());
        });
        g2d.dispose();
        return concatenatedImage;
    }


    public BufferedImage concatenateHorizontally(List<BufferedImage> images) {
        int maxWidth = images.stream().map(BufferedImage::getWidth).reduce(0, Integer::sum);

        BufferedImage concatenatedImage = new BufferedImage(maxWidth, images.get(0).getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = concatenatedImage.createGraphics();
        AtomicInteger currentWidth = new AtomicInteger(0);
        images.forEach(image ->  {
            g2d.drawImage(image,  currentWidth.get(), 0, null);
            currentWidth.addAndGet(image.getWidth());
        });
        g2d.dispose();
        return concatenatedImage;
    }
}
