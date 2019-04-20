package garstka.jakub.allegro_mosaic.tools;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MosaicGeneratorImpl implements MosaicGenerator {

    private final ImageResizerImpl imageResizerImpl = new ImageResizerImpl();
    private final ImageConcatenatorImpl imageConcatenatorImpl = new ImageConcatenatorImpl();
    private final ImageLoaderImpl imageLoaderImpl = new ImageLoaderImpl();

    private int maxMosaicWidth;
    private int maxMosaicHeight;
    private List<String> imagesUrls;

    public MosaicGeneratorImpl(int maxMosaicWidth, int maxMosaicHeight, List<String> imagesUrls) {
        this.maxMosaicWidth = maxMosaicWidth;
        this.maxMosaicHeight = maxMosaicHeight;
        this.imagesUrls = imagesUrls;
    }

    public BufferedImage createMosaic() throws IOException {
        List<BufferedImage> images = imageLoaderImpl.loadImagesToList(imagesUrls);
        int numberOfElementInRow = (int) Math.round(Math.sqrt(images.size()));

        BufferedImage concatImage = concatenateIntoMosaic(resizePreparedImagesList(images, numberOfElementInRow), numberOfElementInRow);
        concatImage = imageResizerImpl.resizeImage(concatImage, maxMosaicWidth, maxMosaicHeight);
        return concatImage;
    }

    private BufferedImage concatenateIntoMosaic(List<BufferedImage> images, int numberOfElementInRow) {
        List<BufferedImage> temporaryImages = new ArrayList<>();
        int rowIndex = 0;
        while (rowIndex < images.size() - numberOfElementInRow) {
            temporaryImages.add(imageConcatenatorImpl.concatenateHorizontally(images.subList(rowIndex, rowIndex + numberOfElementInRow)));
            rowIndex += numberOfElementInRow;
        }
        if (images.size() == rowIndex) {
            return imageConcatenatorImpl.concatenateVertically(temporaryImages);
        } else {
            temporaryImages.add(imageConcatenatorImpl.concatenateHorizontally(images.subList(rowIndex, images.size())));
            return imageConcatenatorImpl.concatenateVertically(temporaryImages);
        }
    }

    private List<BufferedImage> resizePreparedImagesList(List<BufferedImage> images, int numberOfElementInRow) {
        int[] size = findRightSize(images.size(), numberOfElementInRow);
        return createResizedImagesList(size, images, numberOfElementInRow);
    }

    private int[] findRightSize( int numberOfElementsInList, int numberOfElementInRow) {
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
            if (i >= IndexOfLastElementInLastFullRow) {
                newImages.add(imageResizerImpl.resizeImage(images.get(i), size[2], size[0]));
            } else {
                newImages.add(imageResizerImpl.resizeImage(images.get(i), size[1], size[0]));
            }
        }
        return newImages;
    }

}
