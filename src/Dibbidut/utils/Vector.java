package Dibbidut.utils;

import java.util.Objects;

public class Vector {
    public double X;
    public double Y;

    public Vector () { this(0, 0); }

    public Vector(double x, double y) {
        X = x;
        Y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Vector vector = (Vector) o;

        return X == vector.X &&
                Y == vector.Y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(X, Y);
    }
}
