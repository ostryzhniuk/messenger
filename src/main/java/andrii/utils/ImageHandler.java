package andrii.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static andrii.utils.Base64Handler.decodeBASE64;

public class ImageHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageHandler.class);

    public static BufferedImage decodeBase64Image(String sourceData) {

        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(decodeBASE64(sourceData))){
            return ImageIO.read(inputStream);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public static void save(BufferedImage bufferedImage, Path path) {
        try {
            if (Files.exists(path)) {
                Files.delete(path);
            } else {
                Files.createDirectories(path.getParent());
            }
            ImageIO.write(bufferedImage, "jpg", path.toFile());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public static String loadEncodedImage(Path path) {

        try (InputStream inputStream = new FileInputStream(path.toString())){

            byte[] binaryData = IOUtils.toByteArray(inputStream);
            return new String(Base64.encodeBase64(binaryData));

        }catch (IOException e){
            LOGGER.warn("Category or product photo not found.", e);
            return null;
        }
    }

}
