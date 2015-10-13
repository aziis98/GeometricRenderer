package com.aziis98.testing;

import com.aziis98.vrenderer.api.Canvas;
import com.aziis98.vrenderer.api.primitives.*;

import javax.imageio.*;
import java.awt.*;
import java.io.*;

public class TestCanvas {

    public static void main(String[] args) throws IOException {

        Canvas canvas = new Canvas();

        PPoint p1 = new PPoint(100, 100);
        PPoint p2 = new PPoint(300, 230);

        PLine l1 = new PLine( p1, p2 );

        canvas.setColor( Color.RED );
        canvas.draw(l1);

        canvas.setColor( Color.BLACK );
        canvas.draw(p1);
        canvas.draw(p2);

        ImageIO.write( canvas.renderImage( 800, 600 ), "png", new File( "res/TestResult.png" ) );

    }

}
