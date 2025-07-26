package pl.edu.pja.s33398;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GraphicsHelper {
    private static Map<String, Image> images = new HashMap<>();
    private static Map<String, ImageIcon> scaledImages = new HashMap<>();

    public static ImageIcon getImageIcon(String path, int width, int height) throws IOException {
        ImageIcon icon = scaledImages.get(getScaledImageHash(path, width, height));
        if (icon == null) {
            Image image = images.get(path);
            if (image == null) {
                image = ImageIO.read(new File(path));
                images.put(path, image);
            }
            icon = new ImageIcon(image.getScaledInstance(width, height, Image.SCALE_SMOOTH));
            scaledImages.put(getScaledImageHash(path, width, height), icon);
        }

        return icon;
    }

    private static String getScaledImageHash(String path, int width, int height) {
        return path + "_" + width + "_" + height;
    }

}
