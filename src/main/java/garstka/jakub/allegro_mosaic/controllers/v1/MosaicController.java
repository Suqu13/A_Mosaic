package garstka.jakub.allegro_mosaic.controllers.v1;


import garstka.jakub.allegro_mosaic.services.MosaicService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = MosaicController.BASE_URL, method = RequestMethod.GET)
public class MosaicController {

    static final String BASE_URL = "/mozaika";
    private final MosaicService mosaicService;

    public MosaicController(MosaicService mosaicService) {
        this.mosaicService = mosaicService;
    }

    @GetMapping(produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getMosaic(@RequestParam(name = "losowo", required = false, defaultValue = "0") Integer random,
                                          @RequestParam(name = "rozdzielczosc", required = false, defaultValue = "2048x2048")
                                                       String resolutionXxY,
                                          @RequestParam("zdjecia") String imagesUrls) throws IOException {
        boolean toShuffle = random == 1;

        List<Integer> resolutionToScreen = Arrays.stream(resolutionXxY.split("x")).map(Integer::parseInt).collect(Collectors.toList());
        return mosaicService.getMosaic(toShuffle, resolutionToScreen.get(0), resolutionToScreen.get(1), Arrays.asList(imagesUrls.split(",")));
    }

    @ExceptionHandler(IndexOutOfBoundsException.class)
    public ResponseEntity<String> oneResolutionValue() {
        return new ResponseEntity<>("{\"error\": \"Two values of resolution have to be provided\"}", HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(IOException.class)
    public ResponseEntity<String> invalidUrl() {
        return new ResponseEntity<>("{\"error\": \"Invalid URL provided\"}", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OutOfMemoryError.class)
    public ResponseEntity<String> toBigResolution() {
        return new ResponseEntity<>("{\"error\": \"Too big resolution provided\"}", HttpStatus.BAD_REQUEST);
    }


    //TODO NIE DZIAŁA
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> invalidParameter() {
        return new ResponseEntity<>("{\"error\": \"Invalid parameter provided\"}", HttpStatus.BAD_REQUEST);
    }
}
