package com.aziis98.vrenderer.api.primitives;

import com.aziis98.vrenderer.api.*;
import com.aziis98.vrenderer.api.Canvas.*;

import java.awt.*;

public class PSegment extends PLine {

    PPoint p1, p2;

    public PSegment(PPoint p1, PPoint p2) {
        super( p1, p2 );
        this.p1 = p1;
        this.p2 = p2;
    }

    @Override
    public void draw(Graphics2D g, Config config) {
        GraphicsHelper.drawLine( g, p1.getX().get(), p1.getY().get(), p2.getX().get(), p2.getY().get() );
    }

    public DNumber getDx() {
        return p2.getX().sub( p1.getX() );
    }

    public DNumber getDy() {
        return p2.getY().sub( p1.getY() );
    }

    public PPoint getMidpoint() {
        return PPoint.centroid( p1, p2 );
    }

    public PPoint intersect(PSegment segment) {
        return super.intersect( segment );
    }

}
