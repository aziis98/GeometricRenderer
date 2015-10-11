package com.aziis98.vrenderer;

import java.io.*;
import java.nio.file.*;
import java.util.*;

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
    }

    //region Renderer



    //endregion

}
