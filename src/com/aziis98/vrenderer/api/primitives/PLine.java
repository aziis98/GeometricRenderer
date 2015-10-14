package com.aziis98.vrenderer.api.primitives;

import com.aziis98.vrenderer.api.Canvas.*;
import com.aziis98.vrenderer.api.*;

import java.awt.*;

public class PLine implements ICanvasPainter, IPrimitive {

    // DNumber slope, intercept;
    DNumber a, b, c; // ax + by = c --> y=c/b - ax/b

    public PLine(DNumber a, DNumber b, DNumber c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public PLine(double a, double b, double c) {
        this.a = DNumber.cost( a );
        this.b = DNumber.cost( b );
        this.c = DNumber.cost( c );
    }

    public PLine(PPoint a, PPoint b) {
        this.a = b.getY().sub( a.getY() );
        this.b = a.getX().sub( b.getX() );
        this.c = a.getX().mul( b.getY() ).sub( b.getX().mul( a.getY() ) );
    }

    public DNumber getA() {
        return a;
    }

    public DNumber getB() {
        return b;
    }

    public DNumber getC() {
        return c;
    }

    public PSegment toSegmentForm() {
        PPoint p1 = new PPoint( DNumber.cost( 0 ), c.sub( a.mul( 0 ) ).div( b ) );
        PPoint p2 = new PPoint( DNumber.cost( 500 ), c.sub( a.mul( 500 ) ).div( b ) );

        return new PSegment(p1, p2);
    }

    @Override
    public void draw(Graphics2D g, Config config) {
        double ty = -g.getTransform().getTranslateY();

        double a = this.a.get();
        double b = this.b.get();
        double c = this.c.get();

        double x1 = (c - b * ty) / a;
        double x2 = (c - b * (ty + config.height)) / a;

        GraphicsHelper.drawLine( g, x1, ty, x2, ty + config.height);
    }

    public PPoint intersect(PLine other) {
        DNumber x = other.b.mul( this.c ).sub( this.b.mul( other.c ) ).div( other.a.mul( this.b ).sub( this.a.mul( other.b ) ) ).negate();
        DNumber y = this.a.mul( other.c ).sub( other.a.mul( this.c ) ).div( other.a.mul( this.b ).sub(this.a.mul( other.b )) ).negate();

        return new PPoint( x, y );
    }

    public PLine move(DNumber dx, DNumber dy) {
        return new PLine( a, b, c.sub( a.mul( dx ) ).sub( b.mul( dy ) ) );
    }

    public PLine perpendicular(PPoint point) {
        return new PLine( this.b, this.a.negate(), point.getX().mul( b ).sub( point.getY().mul( a ) ) );
    }

    public DNumber distance(PPoint point) {
        PPoint p1 = perpendicular( point ).intersect( this );
        return PPoint.distance( point, p1 );
    }

    @Override
    public String toString() {
        return "Primitive:Line( (" + a.get() + ")*x + (" + b.get() + ")*y = (" + c.get() + ") )";
    }
}






























