package garstka.jakub.allegro_mosaic.tools;

import java.awt.image.BufferedImage;

public interface ImageResizer {
    BufferedImage resizeImage(BufferedImage image, int width, int height);
}
