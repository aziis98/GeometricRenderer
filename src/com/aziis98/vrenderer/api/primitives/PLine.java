package com.aziis98.vrenderer.api.primitives;

import com.aziis98.vrenderer.api.Canvas.*;
import com.aziis98.vrenderer.api.*;

import java.awt.*;

public class PLine implements ICanvasPainter {

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
        double a = this.a.get();
        double b = this.b.get();
        double c = this.c.get();

        double x1 = (c - b * 0) / a;
        double x2 = (c - b * config.height) / a;

        GraphicsHelper.drawLine( g, x1, 0, x2, config.height );
    }
}
