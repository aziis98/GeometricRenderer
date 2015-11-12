package com.aziis98.vrenderer.api.parser;

import java.util.*;

public class ScannerUtils {

    public static String nextPatternGroup(Scanner scanner, String pattern, int groupID) {
        return scanner.next( pattern ).replaceAll( pattern, "$" + groupID );
    }

    public static int nextHexColor(Scanner scanner) {
        return Integer.parseInt(nextPatternGroup( scanner, "#([0-9A-Fa-f]+)", 1 ), 16);
    }

}
