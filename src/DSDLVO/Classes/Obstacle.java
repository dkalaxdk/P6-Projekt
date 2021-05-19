package DSDLVO.Classes;

import DSDLVO.Classes.Geometry.HPoint;

import java.util.ArrayList;

public abstract class Obstacle {
    public HPoint position;
    public HPoint translatedPosition;
    public HPoint velocity;
    public HPoint scaledVelocity;
    public ArrayList<HPoint> trajectory;

    public Obstacle(HPoint position, HPoint velocity) {
        this.position = position;
        this.velocity = velocity;
        this.scaledVelocity = velocity;
    }

    public void setScaledVelocity(double scale) {
        scaledVelocity = velocity.copy();
        scaledVelocity.scale(scale);
    }

    public HPoint getScaledVelocity() {
        return scaledVelocity;
    }

    public void setTranslatedPosition(HPoint point) {
        translatedPosition = position.copy();
        translatedPosition.translate(-point.getX(), -point.getY());
    }

    public HPoint getTranslatedPosition() {
        return translatedPosition;
    }
}
