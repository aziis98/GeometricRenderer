package com.aziis98.vrenderer.api.primitives;

@FunctionalInterface
public interface DyNumber {
    double get();

    default int intValue() {
        return (int) (get() + 0.5);
    }

    default DyNumber add(DyNumber other) {
        return () -> get() + other.get();
    }

    default DyNumber sub(DyNumber other) {
        return () -> get() - other.get();
    }

    default DyNumber mul(DyNumber other) {
        return () -> get() * other.get();
    }

    default DyNumber mul(double value) {
        return () -> get() * value;
    }

    default DyNumber div(DyNumber other) {
        return () -> get() / other.get();
    }

    default DyNumber div(double value) {
        return () -> get() / value;
    }

    default DyNumber abs() {
        return () -> Math.abs( get() );
    }

    default DyNumber sqrt() {
        return () -> Math.sqrt( get() );
    }

    default DyNumber pow(DyNumber other) {
        return () -> Math.pow( get(), other.get() );
    }

    default DyNumber pow(double value) {
        return () -> Math.pow( get(), value );
    }

    default DyNumber pow2() {
        return () -> get() * get();
    }

    default DyNumber negate() {
        return () -> -get();
    }

    default DyNumber printout() {
        System.out.println(get());
        return this;
    }

    static DyNumber cost(double value) {
        return () -> value;
    }

    static boolean equals(DyNumber a, DyNumber b) {
        return a.get() == b.get();
    }

    static DyNumber distance(DyNumber a, DyNumber b) {
        return a.mul( a ).add( b.mul( b ) ).sqrt();
    }

    interface DyOperator {
        float apply(float a, float b);
    }
}
