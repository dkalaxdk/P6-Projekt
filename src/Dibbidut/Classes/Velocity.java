package Dibbidut.Classes;

import Dibbidut.utils.Vector;

public class Velocity {

    public Vector Vec;

    public Velocity(double componentX, double componentY) {
        Vec = new Vector(componentX, componentY);
    }

    public Velocity RelativeVelocity(Velocity other) {
        return new Velocity(
                Vec.X - other.Vec.X,
                Vec.Y - other.Vec.Y
        );
    }
}
