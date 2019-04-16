package garstka.jakub.allegro_mosaic.api.v1.model;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class ImageDTO {
    private String url;
    private int resolutionX;
    private int resolutionY;
}
