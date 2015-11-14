package com.aziis98.vrenderer;

import com.aziis98.vrenderer.api.*;
import com.aziis98.vrenderer.api.parser.*;
import com.aziis98.vrenderer.api.parser.Dictionary;
import com.aziis98.vrenderer.api.primitives.*;

import java.io.*;
import java.lang.reflect.*;
import java.nio.file.*;
import java.util.*;

import static com.aziis98.vrenderer.api.parser.ScannerUtils.*;

/**
 * Arguments pattern: <br>
 * java -jar ... "res/vector.vec" --size=1920x1080 --format=PNG
 */
public class GeometricRenderer {

    public static PreviewWindow previewWindow;

    public static void main(String[] args) throws IOException {
        Path         path  = Paths.get( args[0] );
        List<String> lines = Files.readAllLines( path );

        if ( args.length > 1 )
        {
            for (int i = 1; i < args.length; i++)
            {
                System.out.println("Option: '" + args[i] + "'");

                String[] keyValue = args[i].split( "=" );

                if ( keyValue.length == 1 )
                {
                    Settings.configuration.set( keyValue[0] );
                }
                else
                {
                    String key = keyValue[0];
                    String value = keyValue[1];

                    Settings.configuration.set( key, value );
                }
            }
        }


        if ( Settings.configuration.has( "--preview" ) )
        {
            int[] sizes = Settings.configuration.getSize( "--size" );

            if ( sizes.length != 2 )
            {
                sizes[0] = 800;
                sizes[1] = 600;
            }

            GeometricCanvas geometricCanvas = loadGeometricCanvas( lines );
            previewWindow = new PreviewWindow( geometricCanvas, geometricCanvas.renderImage( sizes[0], sizes[1] ) );

            if ( Settings.configuration.has( "--watch" ) )
            {
                new WatchFileService( path, () -> {
                    System.out.println("The file changed, rendering...");
                    previewWindow.updateGeometricCanvas( loadGeometricCanvas( Files.readAllLines( path ) ) );
                }).start();
            }
        }

    }

    private static GeometricCanvas loadGeometricCanvas(List<String> lines) {Dictionary env             = new Dictionary();
        GeometricCanvas                                                                geometricCanvas = new GeometricCanvas();

        for (String line : lines)
        {
            line = line.replaceAll( "//(.*)", "" ).trim();
            if ( line.isEmpty() )
            {
                continue;
            }

            Scanner scanner = new Scanner( line ).useLocale( Locale.US );
            routePrimitive( scanner.next(), scanner, env, geometricCanvas );
        }

        System.out.println("Loaded " + env.size() + " primitives");
        return geometricCanvas;
    }

    //region Renderer
    // Scanner line, Dictionary env, LinkedList<ICanvasPainter> painters


    /// point <name> <x> <y>
    public static void parse_point(Scanner line, Dictionary env, GeometricCanvas geometricCanvas) {
        String refName = nextPatternGroup( line, "'(.*?)'", 1 );

        env.add( refName, new PPoint( line.nextDouble(), line.nextDouble() ) );
    }

    public static void parse_line(Scanner line, Dictionary env, GeometricCanvas geometricCanvas) {
        String refName = nextPatternGroup( line, "'(.*?)'", 1 );

        PPoint pointA = env.<PPoint>get( line.next() );
        PPoint pointB = env.<PPoint>get( line.next() );

        env.add( refName, new PLine( pointA, pointB ) );
    }

    public static void parse_line_perpendicular(Scanner line, Dictionary env, GeometricCanvas geometricCanvas) {
        String refName = nextPatternGroup( line, "'(.*?)'", 1 );

        PLine  theLine = env.<PLine>get( line.next() );
        PPoint thePoint = env.<PPoint>get( line.next() );

        env.add( refName, theLine.perpendicular( thePoint ) );
    }

    public static void parse_color(Scanner line, Dictionary env, GeometricCanvas geometricCanvas) {
        geometricCanvas.setColor( nextHexColor( line ) );
    }

    public static void parse_background(Scanner line, Dictionary env, GeometricCanvas geometricCanvas) {
        geometricCanvas.setBackground( nextHexColor( line ) );
    }

    public static void parse_draw(Scanner line, Dictionary env, GeometricCanvas geometricCanvas) {
        geometricCanvas.draw( env.<ICanvasPainter>get( line.next() ) );
    }

    //endregion

    public static void routePrimitive(String command, Scanner line, Dictionary env, GeometricCanvas geometricCanvas) {
        try
        {
            String methodName = "parse_" + command.toLowerCase().replace( '-', '_' );

            GeometricRenderer.class
                    .getMethod( methodName, Scanner.class, Dictionary.class, GeometricCanvas.class )
                    .invoke( null, line, env, geometricCanvas );
        }
        catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e)
        {
            e.printStackTrace();
        }
    }

}
