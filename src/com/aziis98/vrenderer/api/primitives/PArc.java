package com.aziis98.vrenderer.api.primitives;

import com.aziis98.vrenderer.api.*;
import com.aziis98.vrenderer.api.GeometricCanvas.*;

import java.awt.*;

import static com.aziis98.vrenderer.api.GraphicsHelper.drawArc;
import static com.aziis98.vrenderer.api.GraphicsHelper.fillArc;

public class PArc implements ICanvasPainter, IPrimitive {

    PPoint  center;
    DNumber rx, ry, start, end;

    public PArc(PPoint center, DNumber rx, DNumber ry, DNumber start, DNumber end) {
        this.center = center;
        this.rx = rx;
        this.ry = ry;
        this.start = start;
        this.end = end;
    }

    public PArc(PCircle circle, DNumber start, DNumber end) {
        this( circle.center, circle.radius, circle.radius, start, end );
    }

    public PArc getOtherSide() {
        return new PArc( center, rx, ry, end, start );
    }

    @Override
    public void draw(Graphics2D g, Config config) {
        drawArc( g, center, rx, ry, start, end );
    }

    @Override
    public void fill(Graphics2D g, Config config) {
        fillArc( g, center, rx, ry, start, end );
    }

}
