package garstka.jakub.allegro_mosaic.services;

import java.io.IOException;
import java.util.List;

public interface ImageListService {
    byte[] getMosaic(boolean random, Integer resolutionX, Integer resolutionY, List<String> imagesUrls) throws IOException;
}
