package com.melloProj.Mello.utils;

import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class BufferedImageToMultipartFileConverter {

    public static MultipartFile convert(BufferedImage bufferedImage, String formatName) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, formatName, outputStream);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());

        return new MultipartFile() {
            @Override
            public String getName() {
                return "buffered-image." + formatName;
            }

            @Override
            public String getOriginalFilename() {
                return getName();
            }

            @Override
            public String getContentType() {
                return "image/" + formatName;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public long getSize() {
                return outputStream.size();
            }

            @Override
            public byte[] getBytes() throws IOException {
                return outputStream.toByteArray();
            }

            @Override
            public InputStream getInputStream() throws IOException {
                return inputStream;
            }

            @Override
            public void transferTo(File file) throws IOException, IllegalStateException {
                throw new UnsupportedOperationException();
            }
        };
    }
}