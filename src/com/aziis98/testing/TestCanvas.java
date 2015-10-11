package com.aziis98.testing;

import com.aziis98.vrenderer.api.*;

import javax.imageio.*;
import java.io.*;

public class TestCanvas {

    public static void main(String[] args) {

        Canvas canvas = new Canvas();

        ImageIO.write( canvas.renderImage( 800, 600 ), "png", new File( "res/TestResult.png" ) );

    }

}
