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

        int numberOfElementInRow = (int) Math.round(Math.sqrt(images.size()));

        BufferedImage concatImage = concatenateIntoMosaic(resizeImages(images, numberOfElementInRow, maxMosaicWidth, maxMosaicHeight), numberOfElementInRow);
        concatImage = resizeImage(concatImage, maxMosaicWidth, maxMosaicHeight);
        return concatImage;
    }


    private BufferedImage concatenateIntoMosaic(List<BufferedImage> images, int numberOfElementInRow) {
        List<BufferedImage> temporaryImages = new ArrayList<>();
        int rowIndex = 0;

        while (rowIndex < images.size() - numberOfElementInRow) {
            temporaryImages.add(concatenateHorizontally(images.subList(rowIndex, rowIndex + numberOfElementInRow)));
            rowIndex += numberOfElementInRow;
        }

        if (images.size() == rowIndex) {
            return concatenateVertically(temporaryImages);
        } else {
            temporaryImages.add(concatenateHorizontally(images.subList(rowIndex, images.size())));
            return concatenateVertically(temporaryImages);
        }
    }


    private BufferedImage concatenateVertically(List<BufferedImage> images) {
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


    private BufferedImage concatenateHorizontally(List<BufferedImage> images) {
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
        int imageHeight = maxMosaicHeight;
        int imageWidth = maxMosaicWidth;
        int lastRowWidth = maxMosaicWidth;

        int fullRows = numberOfElementsInList / numberOfElementInRow;
        int numberOfElementInLastRow = numberOfElementsInList % numberOfElementInRow;

        imageWidth /= numberOfElementInRow;
        if (numberOfElementInLastRow != 0) {
            imageHeight /= (fullRows + 1);
            lastRowWidth /= numberOfElementInLastRow;
        } else {
            imageHeight /= fullRows;
        }

        return new int[]{imageHeight, imageWidth, lastRowWidth};
    }


    private List<BufferedImage> createResizedImagesList(int[] size, List<BufferedImage> images, int numberOfElementInRow) {
        List<BufferedImage> newImages = new ArrayList<>();
        int IndexOfLastElementInLastFullRow  = images.size() - (images.size() % numberOfElementInRow);
        for (int i = 0; i < images.size(); i++) {
                if (i >=  IndexOfLastElementInLastFullRow ) {
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
