package garstka.jakub.allegro_mosaic.tools;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class ImageMeneger {
    public BufferedImage loadImage(String url) {
        try {
            return ImageIO.read(new URL(url));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public BufferedImage createMosaic(List<String> imagesUrls) {
        List<BufferedImage> images = imagesUrls.stream().map(url -> loadImage(url)).collect(Collectors.toList());
        int numberOfElementInRow = images.size() < 6 ? 2 : 3;

        BufferedImage concatImage = concatIntoMosaic(resizeImages(images, numberOfElementInRow), numberOfElementInRow);

        try {
            ImageIO.write(concatImage, "jpg", new File("nowe.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return concatImage;
    }

    private BufferedImage concatIntoMosaic(List<BufferedImage> images, int numberOfElementInRow) {
        List<BufferedImage> imagesTemporary = new ArrayList<>();
        boolean evenNumberOfImages = images.size() % numberOfElementInRow == 0;
        int i = numberOfElementInRow;
        while (i <= images.size()) {
                imagesTemporary.add(concatVertically(images.subList(i - numberOfElementInRow, i)));
            i += numberOfElementInRow;
        }
        if (evenNumberOfImages) {
            return concatHorizontally(imagesTemporary);
        } else {
            imagesTemporary.add(concatVertically(images.subList(i - numberOfElementInRow, images.size())));
            return concatHorizontally(imagesTemporary);
        }
    }

    private BufferedImage concatHorizontally(List<BufferedImage> images) {
        int currentHeight = 0;
        int maxHeight = images.stream().map(BufferedImage::getHeight).reduce(0, Integer::sum);
        BufferedImage concatImage = new BufferedImage(images.get(0).getWidth(), maxHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = concatImage.createGraphics();
        for(int j = 0; j < images.size(); j++) {
            g2d.drawImage(images.get(j), 0, currentHeight, null);
            currentHeight += images.get(j).getHeight();
        }
        g2d.dispose();
        return concatImage;
    }

    private BufferedImage concatVertically(List<BufferedImage> images) {
        int currentWidth = 0;
        int maxWidth = images.stream().map(BufferedImage::getWidth).reduce(0, Integer::sum);
        BufferedImage concatImage = new BufferedImage(maxWidth, images.get(0).getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = concatImage.createGraphics();
        for(int j = 0; j < images.size(); j++) {
            g2d.drawImage(images.get(j), currentWidth, 0, null);
            currentWidth += images.get(0).getWidth();
        }
        g2d.dispose();
        return concatImage;
    }

    private List<BufferedImage> resizeImages(List<BufferedImage> images, int numberOfElementInRow) {
        int[] size = findRightSize(2048, 2048, images.size(), numberOfElementInRow);
        return createResizedImagesList(size, images, numberOfElementInRow);
    }

    //TODO zwraca poprawnie
    private int[] findRightSize(int maxH, int maxW, int numberOfElementsInList, int numberOfElementInRow) {
        int picH;
        int picW;
        int lastRowW = 0;

        int fullRows = (int) Math.floor(numberOfElementsInList / numberOfElementInRow);
        if (fullRows == 0) {
            picH = maxH;
            picW = maxW;
        } else {
            picW = maxW / numberOfElementInRow;
            int numberOfElementInLastRow = numberOfElementsInList % numberOfElementInRow;
            if (numberOfElementInLastRow != 0) {
                picH = maxH / (fullRows + 1);
                lastRowW = maxW / numberOfElementInLastRow;
            } else {
                picH = maxH / fullRows;
            }
        }
        return new int[]{picH, picW, lastRowW};
    }

    private List<BufferedImage> createResizedImagesList(int[] size, List<BufferedImage> images, int numberOfElementInRow) {
        List<BufferedImage> newImages = new ArrayList<>();
        for (int i = 0; i < images.size(); i++) {
            if (i > images.size() - numberOfElementInRow && size[2] != 0) {
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
