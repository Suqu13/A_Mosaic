package garstka.jakub.allegro_mosaic.tools;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ImageLoaderImpl implements ImageLoader {

    public List<BufferedImage> loadImagesToList(List<String> imagesUrls) throws IOException {
        List<BufferedImage> loadedImages = new ArrayList<>();
        for (String imagesUrl : imagesUrls) {
            BufferedImage bufferedImage = ImageIO.read(new URL(imagesUrl));
            if (bufferedImage!= null)
                loadedImages.add(bufferedImage);
        }
        return loadedImages;
    }

}
