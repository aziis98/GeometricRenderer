package com.aziis98.vrenderer.api.primitives;

import com.aziis98.vrenderer.api.Canvas.*;
import com.aziis98.vrenderer.api.*;

import java.awt.*;

import static com.aziis98.vrenderer.api.GraphicsHelper.*;

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

    public PPoint[] intersect(PLine line) {
        /*
        PSegment segment = line.toSegmentForm();

        DyNumber dx = segment.getDx();
        DyNumber dy = segment.getDy();
        DyNumber d  = distance( dx, dy );
        DyNumber D  = segment.p1.getX().mul( segment.p2.getY() ).sub( segment.p2.getX().mul( segment.p1.getY() ) );

        PPoint i1 = new PPoint(
                D.mul( dy ).add( sgn( dy ).mul( dx ).mul( radius.pow2().mul( d.pow2() ).sub( D.pow2() ).sqrt() ) ).div( d.pow2() ),
                  D.mul( dx ).negate().add( dy.abs().mul( radius.pow2().mul( d.pow2() ).sub( D.pow2() ).sqrt() ) ).div( d.pow2() )
        );

        PPoint i2 = new PPoint(
                D.mul( dy ).sub( sgn( dy ).mul( dx ).mul( radius.pow2().mul( d.pow2() ).sub( D.pow2() ).sqrt() ) ).div( d.pow2() ),
                  D.mul( dx ).negate().sub( dy.abs().mul( radius.pow2().mul( d.pow2() ).sub( D.pow2() ).sqrt() ) ).div( d.pow2() )
        );

        return new PPoint[] { i1, i2 };
        */

        return null;
    }

    private static DyNumber sgn(DyNumber number) {
        return () -> number.get() < 0 ? -1 : 1;
    }

}






















