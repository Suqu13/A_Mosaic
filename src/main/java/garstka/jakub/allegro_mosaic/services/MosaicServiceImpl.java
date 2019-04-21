package garstka.jakub.allegro_mosaic.services;

import garstka.jakub.allegro_mosaic.tools.MosaicGeneratorImpl;
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
public class MosaicServiceImpl implements MosaicService {


    @Override
    public byte[] getMosaic(boolean random, Integer resolutionX, Integer resolutionY, List<String> imagesUrls) throws IOException {
        if (imagesUrls.get(0).equals("") || imagesUrls.size() > 8 || resolutionX <= 0 || resolutionY <= 0) throw new IllegalArgumentException();
        if (random) Collections.shuffle(imagesUrls);
        BufferedImage mosaic = new MosaicGeneratorImpl(resolutionX, resolutionY, imagesUrls).createMosaic();
        return convertToByteArray(mosaic);
    }

    private byte [] convertToByteArray(BufferedImage image) throws IOException {
        ByteArrayOutputStream toUpload = new ByteArrayOutputStream();
        ImageIO.write(image, "jpeg", toUpload);
        InputStream byteArrayToUpload = new ByteArrayInputStream(toUpload.toByteArray());
        return IOUtils.toByteArray(byteArrayToUpload);
    }
}
