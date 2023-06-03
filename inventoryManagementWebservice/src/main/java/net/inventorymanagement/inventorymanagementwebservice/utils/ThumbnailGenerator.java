package net.inventorymanagement.inventorymanagementwebservice.utils;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.imageio.ImageIO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThumbnailGenerator {

    static final int MAX_WIDTH = 100;
    static final int MAX_HEIGHT = 100;

    public String generateThumbnail(File file, String destinationFolder) {
        BufferedImage sourceImage;
        String in = file.getName();
        try {
            // load image
            sourceImage = ImageIO.read(file);
            if (sourceImage != null) {
                if (sourceImage.getWidth() <= MAX_WIDTH && sourceImage.getHeight() <= MAX_HEIGHT) {
                    // no need to create thumbnail of an image that is already smaller
                    return null;
                }
                BufferedImage bi = createThumb(
                    sourceImage,
                    MAX_WIDTH, MAX_HEIGHT);
                String ext = in.substring(in.lastIndexOf('.') + 1);
                if (!ext.equalsIgnoreCase("jpg")) {
                    ext = "jpg";
                }
                String out = in.replaceFirst("(?i).([a-z0-9]+)$", "_thumb." + ext);
                log.debug("Creating thumbnail " + in + " --> " + out);
                Path destinationPath = Paths.get(destinationFolder, out);
                try (OutputStream os = Files.newOutputStream(
                    destinationPath)) {
                    ImageIO.write(bi, ext, os);
                    return destinationPath.toString();
                } catch (Exception ex) {
                    throw new RuntimeException("Failed to write thumbnail file for " + out, ex);
                }
            } else {
                log.warn("Unable to load image for Thumbnail creation: {}", file.getName());
            }
        } catch (IOException | RuntimeException ex) {
            log.error("Failed to generate thumbnail from " + file.getName(), ex);
        }
        return null;
    }

    private BufferedImage createThumb(BufferedImage in, int w, int h) {
        // scale w, h to keep aspect constant
        double outputAspect = 1.0 * w / h;
        double inputAspect = 1.0 * in.getWidth() / in.getHeight();
        if (outputAspect < inputAspect) {
            // width is limiting factor; adjust height to keep aspect
            h = (int) (w / inputAspect);
        } else {
            // height is limiting factor; adjust width to keep aspect
            w = (int) (h * inputAspect);
        }
        BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = bi.createGraphics();
        g2.drawImage(in.getScaledInstance(w, h, Image.SCALE_SMOOTH), 0, 0, w, h, null);
        g2.dispose();
        return bi;
    }
}
