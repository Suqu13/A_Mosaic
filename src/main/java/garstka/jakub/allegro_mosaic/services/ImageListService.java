package garstka.jakub.allegro_mosaic.services;

import garstka.jakub.allegro_mosaic.api.v1.model.ImageListDTO;

import java.util.List;

public interface ImageListService {
    ImageListDTO getDecodedImageListDTO(boolean random, Integer resolutionX, Integer resolutionY, List<String> imagesUrls);
}
