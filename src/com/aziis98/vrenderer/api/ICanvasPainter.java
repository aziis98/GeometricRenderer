package com.aziis98.vrenderer.api;

import com.aziis98.vrenderer.api.GeometricCanvas.*;

import java.awt.*;

public interface ICanvasPainter {

    void draw(Graphics2D g, Config config);

    default void fill(Graphics2D g, Config config) { }

}
