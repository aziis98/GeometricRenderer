package com.aziis98.testing;

import com.aziis98.vrenderer.api.*;
import com.aziis98.vrenderer.api.primitives.*;

import javax.imageio.*;
import java.io.*;
import java.util.*;

import static java.awt.Color.*;

public class TestCanvas {

    public static void main(String[] args) throws IOException {

        GeometricCanvas geometricCanvas = new GeometricCanvas();

        // geometricCanvas.traslateBy( 300, 100 );

        PPoint p1 = new PPoint(200, 200);
        PPoint p2 = new PPoint(600, 460);

        PPoint p3 = new PPoint(800, 300);
        PPoint p4 = new PPoint(180, 400);

        PLine l1 = new PLine( p1, p2 );
        PLine l2 = new PLine( p3, p4 );

        PPoint p5 = l1.intersect( l2 );
        PPoint p6 = PPoint.centroid( p1, p4, p5 );

        PLine l3 = l1.perpendicular( p6 );

        // PPoint p7 = PPoint.orthocenter( p1, p2, p3 );
        PCircle c1 = new PCircle( p1, p4, p5 );
        PCircle c2 = new PCircle( p6, p3, p2 );

        PPoint[] ps1 = c1.intersect( l3 );
        PPoint[] ps2 = c1.intersect( c2 );

        System.out.println( Arrays.toString( ps2 ) );

        geometricCanvas.setBackground( WHITE );

        geometricCanvas.setColor( RED );
        geometricCanvas.draw( l1 );
        geometricCanvas.draw( l2 );
        geometricCanvas.draw( l3 );

        geometricCanvas.setColor( BLACK );
        geometricCanvas.draw( p1 );
        geometricCanvas.draw( p2 );
        geometricCanvas.draw( p3 );
        geometricCanvas.draw( p4 );

        geometricCanvas.draw( c1 );
        geometricCanvas.draw( c2 );

        geometricCanvas.setColor( BLUE );
        geometricCanvas.draw( p5 );
        geometricCanvas.draw( p6 );
        geometricCanvas.draw( ps1 );

        geometricCanvas.setColor( GREEN );
        geometricCanvas.draw( ps2 );

        ImageIO.write( geometricCanvas.renderImage( 1920, 1080 ), "png", new File( "res/TestResult.png" ) );

    }

}
