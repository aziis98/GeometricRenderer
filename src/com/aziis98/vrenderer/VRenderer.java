package com.aziis98.vrenderer;

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
public class VRenderer {

    public static void main(String[] args) throws IOException {
        Path         path  = Paths.get( args[0] );
        List<String> lines = Files.readAllLines( path );

        if ( args.length > 1 )
        {
            for (int i = 1; i < args.length; i++)
            {
                String[] keyValue = args[i].split( "=" );

                String key = keyValue[0];
                String value = keyValue[1];

                Settings.configuration.set( key, value );
            }
        }

        Dictionary env = new Dictionary();
        for (String line : lines)
        {
            line = line.replaceAll( "#(.*)", "" ).trim();
            if ( line.isEmpty() )
            {
                continue;
            }

            Scanner scanner = new Scanner( line ).useLocale( Locale.US );
            routePrimitive( scanner.next(), scanner, env );
        }
        System.out.println(env.toNiceString());

    }

    //region Renderer

    /// point <name> <x> <y>
    public static void parse_point(Scanner line, Dictionary env) {
        String refName = nextPatternGroup( line, "'(.*?)'", 1 );

        env.add( refName, new PPoint( line.nextDouble(), line.nextDouble() ) );
    }

    public static void parse_line(Scanner line, Dictionary env) {
        String refName = nextPatternGroup( line, "'(.*?)'", 1 );

        PPoint pointA = env.<PPoint>get( line.next() );
        PPoint pointB = env.<PPoint>get( line.next() );

        env.add( refName, new PLine( pointA, pointB ) );
    }

    public static void parse_line_perpendicular(Scanner line, Dictionary env) {
        String refName = nextPatternGroup( line, "'(.*?)'", 1 );

        PLine  theLine = env.<PLine>get( line.next() );
        PPoint thePoint = env.<PPoint>get( line.next() );

        env.add( refName, theLine.perpendicular( thePoint ) );
    }

    public static void parse_draw(Scanner line, Dictionary env) {

    }

    //endregion

    public static void routePrimitive(String command, Scanner line, Dictionary env) {
        try
        {
            VRenderer.class.getMethod( "parse_" + command.toLowerCase().replace( '-', '_' ), Scanner.class, Dictionary.class ).invoke( null, line, env );
        }
        catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e)
        {
            e.printStackTrace();
        }
    }

}
