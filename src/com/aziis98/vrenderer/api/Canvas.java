package com.aziis98.vrenderer.api;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.util.*;

public class Canvas {

    private LinkedList<ICanvasPainter> paintStack = new LinkedList<>();

    public BufferedImage renderImage(int width, int height) {
        Config config = new Config();
        config.width = width;
        config.height = height;
        return renderImage( config );
    }

    private BufferedImage renderImage(Config config) {
        BufferedImage bufferedImage = new BufferedImage( config.width, config.height, BufferedImage.TYPE_INT_ARGB );
        Graphics2D graphics2D = (Graphics2D) bufferedImage.getGraphics();

        paintStack.forEach( painter -> painter.draw( graphics2D, config ) );

        return bufferedImage;
    }

    public void traslateToCenter() {
        pushDrawer( (g, config) -> g.setTransform( AffineTransform.getTranslateInstance( config.width / 2, config.height / 2 ) ) );
    }

    public void traslateBy(int dx, int dy) {
        pushDrawer( (g, config) -> g.translate( dx, dy ) );
    }

    public void pushDrawer(ICanvasPainter iCanvasPainter) {
        paintStack.add( iCanvasPainter );
    }

    public static class Config {
        private int width, height;
    }
}
