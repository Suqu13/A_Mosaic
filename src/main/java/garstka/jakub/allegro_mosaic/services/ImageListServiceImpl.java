package garstka.jakub.allegro_mosaic.services;

import garstka.jakub.allegro_mosaic.tools.ImageManager;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

@Service
public class ImageListServiceImpl implements ImageListService {

    @Override
    public byte[] getMosaic(boolean random, Integer resolutionX, Integer resolutionY, List<String> imagesUrls) throws IOException {

        if (imagesUrls.isEmpty() || imagesUrls.size() > 8) throw new IllegalArgumentException();

        if (random) Collections.shuffle(imagesUrls);

        BufferedImage mosaic = new ImageManager().createMosaic(imagesUrls, resolutionX, resolutionY);
        ByteArrayOutputStream toUpload = new ByteArrayOutputStream();

        ImageIO.write(mosaic, "jpeg", toUpload);
        InputStream byteArrayToUpload = new ByteArrayInputStream(toUpload.toByteArray());
        return IOUtils.toByteArray(byteArrayToUpload);
    }
}
