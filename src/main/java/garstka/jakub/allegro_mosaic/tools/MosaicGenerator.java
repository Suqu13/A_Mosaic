package garstka.jakub.allegro_mosaic.tools;

import java.awt.image.BufferedImage;
import java.io.IOException;

public interface MosaicGenerator {
    BufferedImage createMosaic() throws IOException;
}
