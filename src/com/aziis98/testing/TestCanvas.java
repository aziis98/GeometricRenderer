package com.aziis98.testing;

import com.aziis98.vrenderer.api.*;
import com.aziis98.vrenderer.api.primitives.*;

import javax.imageio.*;
import java.io.*;

public class TestCanvas {

    public static void main(String[] args) throws IOException {

        Canvas canvas = new Canvas();

        PPoint p1 = new PPoint();

        ImageIO.write( canvas.renderImage( 800, 600 ), "png", new File( "res/TestResult.png" ) );

    }

}
