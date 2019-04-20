package garstka.jakub.allegro_mosaic.services;

import garstka.jakub.allegro_mosaic.api.v1.model.ImageDTO;
import garstka.jakub.allegro_mosaic.api.v1.model.ImageListDTO;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ImageListServiceImpl implements ImageListService {

    @Override
    public ImageListDTO getDecodedImageListDTO(boolean random, Integer resolutionX, Integer resolutionY, List<String> imagesUrls) {
        List<ImageDTO> toReturn = imagesUrls.stream().map(url -> new ImageDTO(url, resolutionX, resolutionY)).collect(Collectors.toList());
        if (random) Collections.shuffle(toReturn);
        return new ImageListDTO(toReturn.size() > 8 ? toReturn.subList(0, 8) : toReturn);
    }
}
