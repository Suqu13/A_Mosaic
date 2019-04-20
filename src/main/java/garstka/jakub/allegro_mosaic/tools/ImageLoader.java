package garstka.jakub.allegro_mosaic.tools;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

public interface ImageLoader {
    List<BufferedImage> loadImagesToList(List<String> imagesUrls) throws IOException;
}
