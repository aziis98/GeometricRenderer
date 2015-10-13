package com.aziis98.vrenderer.api.primitives;

@FunctionalInterface
public interface DyNumber {
    double get();

    default DyNumber add(DyNumber other) {
        return () -> get() + other.get();
    }

    default DyNumber sub(DyNumber other) {
        return () -> get() - other.get();
    }

    default DyNumber mul(DyNumber other) {
        return () -> get() * other.get();
    }

    default DyNumber div(DyNumber other) {
        return () -> get() / other.get();
    }

    default DyNumber mod(DyNumber other) {
        return () -> get() % other.get();
    }

    default DyNumber sqrt() {
        return () -> Math.sqrt( get() );
    }

    default DyNumber pow(DyNumber other) {
        return () -> Math.pow( get(), other.get() );
    }


    static DyNumber cost(double value) {
        return () -> value;
    }


    interface DyOperator {
        float apply(float a, float b);
    }
}
