package garstka.jakub.allegro_mosaic.api.v1.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ImageListDTO {
    private List<ImageDTO> imageDTOList;
}
