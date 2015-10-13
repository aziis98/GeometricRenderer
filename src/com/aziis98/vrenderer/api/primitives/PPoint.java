package com.aziis98.vrenderer.api.primitives;

import com.aziis98.vrenderer.api.Canvas.*;
import com.aziis98.vrenderer.api.*;

import java.awt.*;

public class PPoint implements ICanvasPainter {

    public static final int DOT_SIZE = 4;

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
        g.fillOval( x.intValue() - DOT_SIZE / 2, y.intValue() - DOT_SIZE / 2, DOT_SIZE, DOT_SIZE);
    }
}
