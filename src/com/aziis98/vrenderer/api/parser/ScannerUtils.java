package com.aziis98.vrenderer.api.parser;

import java.util.*;

public class ScannerUtils {

    public static String nextPatternGroup(Scanner scanner, String pattern, int groupID) {
        return scanner.next( pattern ).replaceAll( pattern, "$" + groupID );
    }

}
