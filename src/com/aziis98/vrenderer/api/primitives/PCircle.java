package com.aziis98.vrenderer.api.primitives;

import com.aziis98.vrenderer.api.GeometricCanvas.*;
import com.aziis98.vrenderer.api.*;

import java.awt.*;

import static com.aziis98.vrenderer.api.GraphicsHelper.*;

public class PCircle implements ICanvasPainter, IPrimitive {

    PPoint  center;
    DNumber radius;

    public PCircle(PPoint a, PPoint b, PPoint c) {
        this.center = PPoint.orthocenter( a, b, c );
        this.radius = PPoint.distance( this.center, a );
    }

    public PCircle(PPoint center, DNumber radius) {
        this.center = center;
        this.radius = radius;
    }

    public PPoint getCenter() {
        return center;
    }

    public DNumber getRadius() {
        return radius;
    }

    @Override
    public void draw(Graphics2D g, Config config) {
        drawCircle( g, center, radius);
    }

    @Override
    public void fill(Graphics2D g, Config config) {
        fillCircle( g, center, radius );
    }

    public PPoint[] intersect(PLine line) {
        /*
        PSegment segment = line.toSegmentForm();

        DyNumber dx = segment.getDx();
        DyNumber dy = segment.getDy();
        DyNumber d  = distance( dx, dy );
        DyNumber D  = segment.p1.getX().mul( segment.p2.getY() ).sub( segment.p2.getX().mul( segment.p1.getY() ) );

        D.mul( dy ).add( sgn( dy ).mul( dx ).mul( radius.pow2().mul( d.pow2() ).sub( D.pow2() ).sqrt() ) ).div( d.pow2() ),
                  D.mul( dx ).negate().add( dy.abs().mul( radius.pow2().mul( d.pow2() ).sub( D.pow2() ).sqrt() ) ).div( d.pow2() )

                  D.mul( dy ).sub( sgn( dy ).mul( dx ).mul( radius.pow2().mul( d.pow2() ).sub( D.pow2() ).sqrt() ) ).div( d.pow2() ),
                  D.mul( dx ).negate().sub( dy.abs().mul( radius.pow2().mul( d.pow2() ).sub( D.pow2() ).sqrt() ) ).div( d.pow2() )
        */

        PLine tline = line.move( center.getX(), center.getY() );

        PPoint i1 = new PPoint(
                tline.a.mul( tline.c ).add( tline.b.mul( radius.pow2().mul( tline.a.pow2().add( tline.b.pow2() ) ).sub( tline.c.pow2() ).sqrt() ) ).div( tline.a.pow2().add( tline.b.pow2() ) ).add( center.getX() ),
                tline.b.mul( tline.c ).sub( tline.a.mul( radius.pow2().mul( tline.a.pow2().add( tline.b.pow2() ) ).sub( tline.c.pow2() ).sqrt() ) ).div( tline.a.pow2().add( tline.b.pow2() ) ).add( center.getY() )
        );

        PPoint i2 = new PPoint(
                tline.a.mul( tline.c ).sub( tline.b.mul( radius.pow2().mul( tline.a.pow2().add( tline.b.pow2() ) ).sub( tline.c.pow2() ).sqrt() ) ).div( tline.a.pow2().add( tline.b.pow2() ) ).add( center.getX() ),
                tline.b.mul( tline.c ).add( tline.a.mul( radius.pow2().mul( tline.a.pow2().add( tline.b.pow2() ) ).sub( tline.c.pow2() ).sqrt() ) ).div( tline.a.pow2().add( tline.b.pow2() ) ).add( center.getY() )
        );

        return new PPoint[] { i1, i2 };
    }

    public PPoint[] intersect(PCircle circle) {
        PLine line = new PLine(
                circle.center.getX().sub( center.getX() ).mul( 2 ),
                circle.center.getY().sub( center.getY() ).mul( 2 ),
                radius.pow2().sub( circle.radius.pow2() ).sub( center.getX().pow2() ).sub( center.getY().pow2() ).add( circle.center.getX().pow2() ).add( circle.center.getY().pow2() )
        );

        return intersect( line );
    }

    private static DNumber sgn(DNumber number) {
        return () -> number.get() < 0 ? -1 : 1;
    }

}






















