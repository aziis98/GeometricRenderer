package com.aziis98.vrenderer.api;

import com.aziis98.vrenderer.api.primitives.*;

import java.awt.*;

public class GraphicsHelper {

    public static void drawLine(Graphics2D g, double x1, double y1, double x2, double y2) {
        g.drawLine( (int) x1, (int) y1, (int) x2, (int) y2 );
    }

    public static void drawLine(Graphics2D g, DNumber x1, DNumber y1, DNumber x2,  DNumber y2) {
        drawLine( g, x1.get(), y1.get(), x2.get(), y2.get() );
    }

    public static void drawLine(Graphics2D g, PPoint p1, PPoint p2) {
        drawLine( g, p1.getX(), p1.getY(), p2.getX(), p2.getY() );
    }


    public static void drawCircle(Graphics2D g, DNumber cx, DNumber cy, DNumber r ) {
        double radius = r.get();
        g.drawOval( (int) (cx.get() - radius), (int) (cy.get() - radius), (int) (radius * 2), (int) (radius * 2) );
    }

    public static void drawCircle(Graphics2D g, PPoint center, DNumber radius ) {
        drawCircle( g, center.getX(), center.getY(), radius );
    }


    public static void fillCircle(Graphics2D g, DNumber cx, DNumber cy, DNumber r ) {
        double radius = r.get();
        g.fillOval( (int) (cx.get() - radius), (int) (cy.get() - radius), (int) (radius * 2), (int) (radius * 2) );
    }

    public static void fillCircle(Graphics2D g, PPoint center, DNumber radius ) {
        fillCircle( g, center.getX(), center.getY(), radius );
    }


    public static void drawArc(Graphics2D g, DNumber cx, DNumber cy, DNumber rx, DNumber ry, DNumber start, DNumber end) {
        g.drawArc( cx.intValue(), cy.intValue(), rx.intValue() * 2, ry.intValue() * 2, start.intValue(), end.intValue() );
    }

    public static void drawArc(Graphics2D g, PPoint center, DNumber rx, DNumber ry, DNumber start, DNumber end) {
        drawArc( g, center.getX(), center.getY(), rx, ry, start, end );
    }


    public static void fillArc(Graphics2D g, DNumber cx, DNumber cy, DNumber rx, DNumber ry, DNumber start, DNumber end) {
        g.fillArc( cx.intValue(), cy.intValue(), rx.intValue() * 2, ry.intValue() * 2, start.intValue(), end.intValue() );
    }

    public static void fillArc(Graphics2D g, PPoint center, DNumber rx, DNumber ry, DNumber start, DNumber end) {
        fillArc( g, center.getX(), center.getY(), rx, ry, start, end );
    }

}






























