package net.inventorymanagement.inventorymanagementwebservice.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Path;
import javax.imageio.ImageIO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import static net.inventorymanagement.inventorymanagementwebservice.utils.ThumbnailGenerator.MAX_HEIGHT;
import static net.inventorymanagement.inventorymanagementwebservice.utils.ThumbnailGenerator.MAX_WIDTH;
import static org.junit.jupiter.api.Assertions.*;

public class ThumbnailGeneratorTest {

    @TempDir
    Path temporaryThumbnailPath;

    @Test
    public void shouldCreateThumbnailFromJpg() throws Exception {
        String thumbnailPath = processFile("Bild1.jpg");
        assertNotNull(thumbnailPath);
        File thumb = new File(thumbnailPath);
        assertTrue(thumb.exists());
        assertImageIsPortrait(thumb);
    }


    @Test
    public void shouldCreateThumbnailFromJpeg() throws Exception {
        String thumbnailPath = processFile("Bild2.jpeg");
        assertNotNull(thumbnailPath);
        File thumb = new File(thumbnailPath);
        assertTrue(thumb.exists());
        assertImageIsLandscape(thumb);
    }

    @Test
    public void shouldCreateThumbnailFromPng() throws Exception {
        String thumbnailPath = processFile("Bild3.png");
        assertNotNull(thumbnailPath);
        File thumb = new File(thumbnailPath);
        assertTrue(thumb.exists());
        assertImageIsLandscape(thumb);
    }

    @Test
    public void shouldCreateThumbnailFromGif() throws Exception {
        String thumbnailPath = processFile("Bild4.gif");
        assertNotNull(thumbnailPath);
        File thumb = new File(thumbnailPath);
        assertTrue(thumb.exists());
        assertImageIsLandscape(thumb);
    }

    @Test
    public void shouldCreateThumbnailFromBmp() throws Exception {
        String thumbnailPath = processFile("Bild5.bmp");
        assertNotNull(thumbnailPath);
        File thumb = new File(thumbnailPath);
        assertTrue(thumb.exists());
        assertImageIsLandscape(thumb);
    }

    @Test
    public void whenImageDoesNotExceedThumbnailDimensionsShouldNotCreateThumbnail()
        throws Exception {
        String thumbnailPath = processFile("thumb.jpg");

        assertNull(thumbnailPath);
    }

    @Test
    public void whenTryingToGenerateThumbnailOfAFileThatIsNoImageShouldDoNothing()
        throws Exception {

        String thumbnailPath = processFile("text.txt");

        assertNull(thumbnailPath);
    }

    private void assertImageIsPortrait(File thumb) throws Exception {
        BufferedImage created = ImageIO.read(thumb);
        assertEquals(MAX_HEIGHT, created.getHeight());
        assertTrue(created.getWidth() < MAX_HEIGHT);
    }

    private void assertImageIsLandscape(File thumb) throws Exception{
        BufferedImage created = ImageIO.read(thumb);
        assertEquals(MAX_WIDTH, created.getWidth());
        assertTrue(created.getHeight() < MAX_WIDTH);
    }

    private String processFile(String filename) throws URISyntaxException {
        File file = new File(getClass().getResource(filename).toURI());

        return new ThumbnailGenerator().generateThumbnail(file, temporaryThumbnailPath.toString());
    }

}