package com.aziis98.vrenderer.api;

import java.awt.image.*;

public class Canvas {


    public BufferedImage renderImage(int width, int height) {
        Config config = new Config();
        config.width = width;
        config.height = height;
        return renderImage( config );
    }

    private BufferedImage renderImage(Config config) {
        return null;
    }

    public static class Config {
        private int width, height;
        // ... render options ...
    }
}
