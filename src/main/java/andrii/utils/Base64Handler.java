package andrii.utils;

import org.apache.commons.codec.binary.Base64;

public class Base64Handler {

    public static byte[] decodeBASE64 (String sourceData) {
        String formattedData = sourceData.split(",")[1];
        Base64 decoder = new Base64();
        return decoder.decode(formattedData);
    }

}
