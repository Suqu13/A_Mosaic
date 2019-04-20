package garstka.jakub.allegro_mosaic.tools;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


public class ImageManager {

    private BufferedImage loadImage(String url) throws IOException {
            return ImageIO.read(new URL(url));
        }

    public BufferedImage createMosaic(List<String> imagesUrls, int maxMosaicWidth, int maxMosaicHeight) throws IOException {
        List<BufferedImage> images = new ArrayList<>();
        for (String imagesUrl : imagesUrls) {
            BufferedImage bufferedImage = loadImage(imagesUrl);
            images.add(bufferedImage);
        }
        int numberOfElementInRow = images.size() < 6 ? 2 : 3;
        BufferedImage concatImage = concatIntoMosaic(resizeImages(images, numberOfElementInRow, maxMosaicWidth, maxMosaicHeight), numberOfElementInRow);
        concatImage = resizeImage(concatImage, maxMosaicWidth, maxMosaicHeight);
        return concatImage;
    }

    private BufferedImage concatIntoMosaic(List<BufferedImage> images, int numberOfElementInRow) {
        List<BufferedImage> temporaryImages = new ArrayList<>();
        int firstRowIndex = 0;
        // Concatenate vertically images only for full rows
        while (firstRowIndex < images.size() - numberOfElementInRow) {
            int lastRowIndex = firstRowIndex + numberOfElementInRow;
            temporaryImages.add(concatenateVertically(images.subList(firstRowIndex, lastRowIndex)));
            firstRowIndex += numberOfElementInRow;
        }
        // If only full rows exist then concatenate horizontally list of temporary images which are after vertical concatenation.
        // Else concatenate vertically images from not full row and add these to rest of vertically concatenated. After that
        // also concatenate horizontally list of temporary images.
        if (images.size() == firstRowIndex) {
            return concatenateHorizontally(temporaryImages);
        } else {
            temporaryImages.add(concatenateVertically(images.subList(firstRowIndex, images.size())));
            return concatenateHorizontally(temporaryImages);
        }
    }

    private BufferedImage concatenateHorizontally(List<BufferedImage> images) {
        int maxHeight = images.stream().map(BufferedImage::getHeight).reduce(0, Integer::sum);
        
        BufferedImage concatenatedImage = new BufferedImage(images.get(0).getWidth(), maxHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = concatenatedImage.createGraphics();
        AtomicInteger currentHeight = new AtomicInteger(0);
        images.forEach(image ->  {
            g2d.drawImage(image, 0, currentHeight.get(), null);
            currentHeight.addAndGet(image.getHeight());
        });
        g2d.dispose();
        return concatenatedImage;
    }

    private BufferedImage concatenateVertically(List<BufferedImage> images) {
        int maxWidth = images.stream().map(BufferedImage::getWidth).reduce(0, Integer::sum);
        
        BufferedImage concatenatedImage = new BufferedImage(maxWidth, images.get(0).getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = concatenatedImage.createGraphics();
        AtomicInteger currentWidth = new AtomicInteger(0);
        images.forEach(image ->  {
            g2d.drawImage(image,  currentWidth.get(), 0, null);
            currentWidth.addAndGet(image.getWidth());
        });
        g2d.dispose();
        return concatenatedImage;
    }

    private List<BufferedImage> resizeImages(List<BufferedImage> images, int numberOfElementInRow, int maxMosaicWidth, int maxMosaicHeight) {
        int[] size = findRightSize(maxMosaicWidth, maxMosaicHeight, images.size(), numberOfElementInRow);
        return createResizedImagesList(size, images, numberOfElementInRow);
    }

    private int[] findRightSize(int maxMosaicWidth, int maxMosaicHeight, int numberOfElementsInList, int numberOfElementInRow) {
        int imageHeight;
        int imageWidth;
        int lastRowWidth = maxMosaicWidth;

        int fullRows = (int) Math.floor(numberOfElementsInList / numberOfElementInRow);
        if (fullRows == 0) {
            imageHeight = maxMosaicHeight;
            imageWidth = maxMosaicWidth;
        } else {
            imageWidth = maxMosaicWidth / numberOfElementInRow;
            int numberOfElementInLastRow = numberOfElementsInList % numberOfElementInRow;
            if (numberOfElementInLastRow != 0) {
                imageHeight = maxMosaicHeight / (fullRows + 1);
                lastRowWidth = maxMosaicWidth / numberOfElementInLastRow;
            } else {
                imageHeight = maxMosaicHeight / fullRows;
            }
        }
        return new int[]{imageHeight, imageWidth, lastRowWidth};
    }

    private List<BufferedImage> createResizedImagesList(int[] size, List<BufferedImage> images, int numberOfElementInRow) {
        List<BufferedImage> newImages = new ArrayList<>();
        int indexesOfElementsInLastRow = images.size() - (images.size() % numberOfElementInRow);
        for (int i = 0; i < images.size(); i++) {
                if (i >=  indexesOfElementsInLastRow) {
                    newImages.add(resizeImage(images.get(i), size[2], size[0]));
                } else {
                    newImages.add(resizeImage(images.get(i), size[1], size[0]));
                }
        }
        return newImages;
    }

    private  BufferedImage resizeImage(BufferedImage image, int width, int height){
        BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = newImage.createGraphics();
        graphics2D.drawImage(image, 0, 0, width, height, null);
        graphics2D.dispose();
        return newImage;
    }
}
