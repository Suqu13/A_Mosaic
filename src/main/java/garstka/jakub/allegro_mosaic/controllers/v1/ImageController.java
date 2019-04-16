package garstka.jakub.allegro_mosaic.controllers.v1;


import garstka.jakub.allegro_mosaic.api.v1.model.ImageListDTO;
import garstka.jakub.allegro_mosaic.services.ImageListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletConfig;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = ImageController.BASE_URL, method = RequestMethod.GET)
@SessionAttributes("mozaika")
public class ImageController {

    public static final String BASE_URL = "/mozaika";
    private final ImageListService imageListService;

    @Autowired
    ServletConfig config;

    public ImageController(ImageListService imageListService) {
        this.imageListService = imageListService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ImageListDTO getDecodedImageListDTO(@RequestParam("losowo") Optional<Integer> random,
                                               @RequestParam(name = "rozdzielczosc", required = false, defaultValue = "2048x2048")
                                                       String resolutionXxY,
                                               @RequestParam("zdjecia") String imagesUrls) {

        boolean toShuffle = random.isPresent() && random.get() == 1;
        List<Integer> resolutionToScreen = Arrays.stream(resolutionXxY.split("x")).map(Integer::parseInt).collect(Collectors.toList());

        return imageListService.getDecodedImageListDTO(toShuffle, resolutionToScreen.get(0), resolutionToScreen.get(1),
                Arrays.asList(imagesUrls.split(",")));
    }
}
