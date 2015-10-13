package com.aziis98.vrenderer.api.primitives;

import com.aziis98.vrenderer.api.*;
import com.aziis98.vrenderer.api.Canvas.*;

import java.awt.*;

public class PSegment extends PLine {

    private PPoint a, b;

    public PSegment(PPoint a, PPoint b) {
        super( a, b );
        this.a = a;
        this.b = b;
    }

    @Override
    public void draw(Graphics2D g, Config config) {
        GraphicsHelper.drawLine( g, a.getX().get(), a.getY().get(), b.getX().get(), b.getY().get() );
    }

    public PPoint getMidpoint() {
        return PPoint.centroid( a, b );
    }

    public PPoint intersect(PSegment segment) {
        return super.intersect( segment );
    }

}
