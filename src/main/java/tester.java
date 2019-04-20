import garstka.jakub.allegro_mosaic.tools.ImageMeneger;

import javax.swing.*;
import java.util.List;

public class tester {
    public static void main(String[] args) {
        String url = "http://static.asiawebdirect.com/m/kl/portals/penang-ws/homepage/penang-attractions/beach/pagePropertiesImage/penang-beaches-and-islands.jpg.jpg";
        ImageMeneger imageMeneger = new ImageMeneger();
        JFrame frame = new JFrame();
        frame.setSize(400, 400);
        JLabel label = new JLabel(new ImageIcon(imageMeneger.createMosaic(List.of(url,url, url, url, url, url, url, url))));
        frame.add(label);
        frame.setVisible(true);
    }
}
