package com.aziis98.vrenderer.api.primitives;

@FunctionalInterface
public interface DNumber {
    double get();

    default int intValue() {
        return (int) (get() + 0.5);
    }

    default DNumber add(DNumber other) {
        return () -> get() + other.get();
    }

    default DNumber sub(DNumber other) {
        return () -> get() - other.get();
    }

    default DNumber mul(DNumber other) {
        return () -> get() * other.get();
    }

    default DNumber mul(double value) {
        return () -> get() * value;
    }

    default DNumber div(DNumber other) {
        return () -> get() / other.get();
    }

    default DNumber div(double value) {
        return () -> get() / value;
    }

    default DNumber abs() {
        return () -> Math.abs( get() );
    }

    default DNumber sqrt() {
        return () -> Math.sqrt( get() );
    }

    default DNumber pow(DNumber other) {
        return () -> Math.pow( get(), other.get() );
    }

    default DNumber pow(double value) {
        return () -> Math.pow( get(), value );
    }

    default DNumber pow2() {
        return () -> get() * get();
    }

    default DNumber negate() {
        return () -> -get();
    }

    default DNumber printout() {
        System.out.println(get());
        return this;
    }

    static DNumber cost(double value) {
        return () -> value;
    }

    static boolean equals(DNumber a, DNumber b) {
        return a.get() == b.get();
    }

    static DNumber distance(DNumber a, DNumber b) {
        return a.mul( a ).add( b.mul( b ) ).sqrt();
    }

    interface DyOperator {
        float apply(float a, float b);
    }
}
