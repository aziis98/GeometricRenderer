package com.aziis98.vrenderer;

import java.awt.*;
import java.util.*;

public class Settings {

    public static final Settings configuration = new Settings();

    static
    {
        configuration.set( "--size", "800x600" );
        configuration.set( "--format", "png" );

        // runtime.set( "background", "0xFFFFFF");
        // runtime.set( "fill", "0xFFFFFF");
        // runtime.set( "border", "0xFFFFFF");
        // runtime.set( "border-width", "1.0");
    }



    private HashMap<String, String> properties = new HashMap<>();

    public void set(String key) {
        set( key, "true" );
    }

    public void set(String key, String value) {
        properties.put( key, value );
    }

    public String getValue(String key) {
        return properties.get( key );
    }

    public int[] getSize(String key) {
        if ( !has( key ) )
        {
            return new int[0];
        }

        String[] spSize = getValue( key ).split( "x" );

        if ( spSize.length != 2 )
        {
            return new int[0];
        }

        int w = Integer.parseInt( spSize[0] );
        int h = Integer.parseInt( spSize[1] );

        return new int[] { w, h };
    }

    public Color getColor(String key) {
        String color = getValue( key ).replace( " ", "" ).replace( "_", "" );

        if ( color.length() == 8 )              // 0x12_34_56
        {
            return Color.decode( color );
        }
        else                                    // 0xAA_123456
        {
            return new Color( Integer.decode( getValue( key ) ), true );
        }
    }

    public boolean has(String key) {
        return properties.containsKey( key );
    }

    public double getDouble(String key) {
        return Double.parseDouble( getValue( key ) );
    }

    public boolean getBoolean(String key) {
        return Boolean.parseBoolean( getValue( key ) );
    }

    /*

    public Color getColorOpaque(String key) {
        return Color.decode( getValue( key ) );
    }
    public Color getColorAlpha(String key) {
        return new Color( Integer.decode( getValue( key ) ) , true );
    }

     */

}
