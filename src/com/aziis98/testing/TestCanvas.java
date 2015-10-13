package com.aziis98.testing;

import com.aziis98.vrenderer.api.*;

import javax.imageio.*;
import java.io.*;

public class TestCanvas {

    public static void main(String[] args) throws IOException {

        Canvas canvas = new Canvas();

        canvas.traslateToCenter();

        ImageIO.write( canvas.renderImage( 800, 600 ), "png", new File( "res/TestResult.png" ) );

    }

}
