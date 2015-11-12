package com.aziis98.vrenderer.api.parser;

import java.util.*;
import java.util.Map.*;

public class Dictionary {

    private HashMap<String, Object> values = new HashMap<>();

    public <V> void set(String key, V value) {
        values.put( key, value );
    }

    public <V> void add(String key, V value) {
        set( key, value );
    }

    @SuppressWarnings("unchecked")
    public <V> V get(String key) {
        return (V) values.get( key );
    }

    public <V> V get(String key, Class<V> valueType) {
        return get( key );
    }

    public int size() {
        return values.size();
    }


    public String toNiceString() {
        StringBuilder sb = new StringBuilder( "Dictionary(\n" );

        for (Entry<String, Object> stringObjectEntry : values.entrySet())
        {
            sb.append( "    " ).append( stringObjectEntry.getKey() ).append( " = " ).append( stringObjectEntry.getValue() ).append( "\n" );
        }

        sb.append( ")" );

        return sb.toString();
    }
}
