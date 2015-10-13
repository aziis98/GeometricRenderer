package com.aziis98.vrenderer.api.primitives;

import com.aziis98.vrenderer.api.Canvas.*;
import com.aziis98.vrenderer.api.*;

import java.awt.*;

public class PLine implements ICanvasPainter, IPrimitive {

    // DyNumber slope, intercept;
    DyNumber a, b, c; // ax + by = c --> y=c/b - ax/b

    public PLine(DyNumber a, DyNumber b, DyNumber c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public PLine(double a, double b, double c) {
        this.a = DyNumber.cost( a );
        this.b = DyNumber.cost( b );
        this.c = DyNumber.cost( c );
    }

    public PLine(PPoint a, PPoint b) {
        this.a = b.getY().sub( a.getY() );
        this.b = a.getX().sub( b.getX() );
        this.c = a.getX().mul( b.getY() ).sub( b.getX().mul( a.getY() ) );
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
        DyNumber x = other.b.mul( this.c ).sub( this.b.mul( other.c ) ).div( other.a.mul( this.b ).sub( this.a.mul( other.b ) ) ).negate();
        DyNumber y = this.a.mul( other.c ).sub( other.a.mul( this.c ) ).div( other.a.mul( this.b ).sub(this.a.mul( other.b )) ).negate();

        return new PPoint( x, y );
    }


    public PLine perpendicular(PPoint point) {
        return new PLine( this.b, this.a.negate(), point.getX().mul( b ).sub( point.getY().mul( a ) ) );
    }


    @Override
    public String toString() {
        return "Primitive:Line( (" + a.get() + ")*x + (" + b.get() + ")*y = (" + c.get() + ") )";
    }
}






























