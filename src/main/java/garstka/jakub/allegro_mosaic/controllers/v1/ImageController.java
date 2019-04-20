package garstka.jakub.allegro_mosaic.controllers.v1;


import garstka.jakub.allegro_mosaic.services.ImageListService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = ImageController.BASE_URL, method = RequestMethod.GET)
public class ImageController {

    static final String BASE_URL = "/mozaika";
    private final ImageListService imageListService;

    public ImageController(ImageListService imageListService) {
        this.imageListService = imageListService;
    }

    @GetMapping(produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getMosaic(@RequestParam(name = "losowo", required = false, defaultValue = "0") Integer random,
                                          @RequestParam(name = "rozdzielczosc", required = false, defaultValue = "2048x2048")
                                                       String resolutionXxY,
                                          @RequestParam("zdjecia") String imagesUrls) throws IOException {
        boolean toShuffle = random == 1;

        List<Integer> resolutionToScreen = Arrays.stream(resolutionXxY.split("x")).map(Integer::parseInt).collect(Collectors.toList());
        return imageListService.getMosaic(toShuffle, resolutionToScreen.get(0), resolutionToScreen.get(1), Arrays.asList(imagesUrls.split(",")));
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<String> invalidUrl() {
        return new ResponseEntity<>("{\"error\": \"Invalid URL provided\"}", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OutOfMemoryError.class)
    public ResponseEntity<String> toBigResolution() {
        return new ResponseEntity<>("{\"error\": \"Too big resolution provided\"}", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> invalidParameter() {
        return new ResponseEntity<>("{\"error\": \"Invalid parameter provided\"}", HttpStatus.BAD_REQUEST);
    }
}
