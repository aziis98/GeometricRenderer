package com.aziis98.vrenderer.api.primitives;

import com.aziis98.vrenderer.api.Canvas.*;
import com.aziis98.vrenderer.api.*;

import java.awt.*;

public class PPoint implements ICanvasPainter, IPrimitive {

    public static final double DOT_PERCENT = 0.005;

    private DyNumber x, y;


    public PPoint(double x, double y) {
        this.x = DyNumber.cost( x );
        this.y = DyNumber.cost( y );
    }

    public PPoint(DyNumber x, DyNumber y) {
        this.x = x;
        this.y = y;
    }


    public DyNumber getX() {
        return x;
    }

    public DyNumber getY() {
        return y;
    }


    @Override
    public void draw(Graphics2D g, Config config) {
        int dotSize = (int) (config.height * DOT_PERCENT);

        g.fillOval( x.intValue() - dotSize / 2, y.intValue() - dotSize / 2, dotSize, dotSize);
    }

    public static PPoint centroid(PPoint... array) {
        DyNumber cx = array[0].getX();
        DyNumber cy = array[0].getY();

        for (int i = 1; i < array.length; i++)
        {
            cx = cx.add( array[i].getX() );
            cy = cy.add( array[i].getY() );
        }

        return new PPoint( cx.div( array.length ), cy.div( array.length ) );
    }

    public static PPoint orthocenter(PPoint a, PPoint b, PPoint c) {
        PSegment s1 = new PSegment( a, b );
        PSegment s2 = new PSegment( b, c );

        PLine l1 = s1.perpendicular(s1.getMidpoint());
        PLine l2 = s2.perpendicular(s2.getMidpoint());

        return l1.intersect( l2 );
    }

    public static DyNumber distance(PPoint a, PPoint b) {
        DyNumber dx = b.getX().sub( a.getX() );
        DyNumber dy = b.getY().sub( a.getY() );

        return dx.mul( dx ).add( dy.mul( dy ) ).sqrt();
    }

    @Override
    public boolean equals(Object o) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;

        PPoint other = (PPoint) o;

        return x.get() == other.x.get() && y.get() == other.y.get();
    }

    @Override
    public int hashCode() {
        int result = x != null ? x.hashCode() : 0;
        result = 31 * result + (y != null ? y.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Primitive:Point(" + x.get() + ", " + y.get() + ")";
    }
}
























