package com.aziis98.vrenderer.api;

import java.awt.*;

public class GraphicsHelper {

    public static void drawLine(Graphics2D g, double x1, double y1, double x2, double y2) {
        g.drawLine( (int) x1, (int) y1, (int) x2, (int) y2 );
    }

    public static void drawCircle(Graphics2D g, double cx, double cy, double r) {
        g.drawOval( (int) (cx - r), (int) (cy - r), (int) (r * 2), (int) (r * 2) );
    }
}
