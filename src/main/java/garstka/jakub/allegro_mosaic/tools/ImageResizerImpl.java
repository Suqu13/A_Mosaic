package garstka.jakub.allegro_mosaic.tools;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageResizerImpl implements  ImageResizer{

    public BufferedImage resizeImage(BufferedImage image, int width, int height){
        BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = newImage.createGraphics();
        graphics2D.drawImage(image, 0, 0, width, height, null);
        graphics2D.dispose();
        return newImage;
    }
}
