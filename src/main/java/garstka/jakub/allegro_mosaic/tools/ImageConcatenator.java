package garstka.jakub.allegro_mosaic.tools;

import java.awt.image.BufferedImage;
import java.util.List;

public interface ImageConcatenator {
    BufferedImage concatenateVertically(List<BufferedImage> images);
    BufferedImage concatenateHorizontally(List<BufferedImage> images);
}
