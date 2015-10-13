package com.aziis98.vrenderer.api.primitives;

import com.aziis98.vrenderer.api.*;
import com.aziis98.vrenderer.api.Canvas.*;

import java.awt.*;

import static com.aziis98.vrenderer.api.GraphicsHelper.drawCircle;

public class PCircle implements ICanvasPainter, IPrimitive {

    private PPoint   center;
    private DyNumber radius;

    public PCircle(PPoint a, PPoint b, PPoint c) {
        this.center = PPoint.orthocenter( a, b, c );
        this.radius = PPoint.distance( this.center, a );
    }

    public PCircle(PPoint center, DyNumber radius) {
        this.center = center;
        this.radius = radius;
    }

    public PPoint getCenter() {
        return center;
    }

    public DyNumber getRadius() {
        return radius;
    }

    @Override
    public void draw(Graphics2D g, Config config) {
        drawCircle( g, center.getX().get(), center.getY().get(), radius.get() );
    }

}
