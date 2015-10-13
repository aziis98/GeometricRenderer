package com.aziis98.vrenderer;

import java.awt.*;

public class Utils {

    public static Color decodeColor(int color) {
        return new Color( color, color > 0xFFFFFF );
    }

}
