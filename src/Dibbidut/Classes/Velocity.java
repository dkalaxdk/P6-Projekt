package Dibbidut.Classes;


import math.geom2d.Vector2D;

public class Velocity {

    public Vector2D Vec;

    public Velocity(double componentX, double componentY) {
        Vec = new Vector2D(componentX, componentY);
    }

    public Velocity RelativeVelocity(Velocity other) {
        return new Velocity(
                Vec.x() - other.Vec.x(),
                Vec.y() - other.Vec.y()
        );
    }
}
