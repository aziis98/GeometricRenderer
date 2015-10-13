package com.aziis98.testing;

import com.aziis98.vrenderer.api.*;
import com.aziis98.vrenderer.api.primitives.*;

import javax.imageio.*;
import java.io.*;

import static java.awt.Color.*;

public class TestCanvas {

    public static void main(String[] args) throws IOException {

        Canvas canvas = new Canvas();

        PPoint p1 = new PPoint(100, 100);
        PPoint p2 = new PPoint(300, 230);

        PLine l1 = new PLine( p1, p2 );

        canvas.setBackground( WHITE );

        canvas.setColor( RED );
        canvas.draw(l1);

        canvas.setColor( BLACK );
        canvas.draw(p1);
        canvas.draw(p2);

        ImageIO.write( canvas.renderImage( 800, 600 ), "png", new File( "res/TestResult.png" ) );

    }

}
