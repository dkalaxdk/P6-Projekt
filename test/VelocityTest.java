import DSDLVO.Classes.Velocity;
import math.geom2d.Vector2D;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class VelocityTest {

    @Test
    public void velocity_SetsValuesOfComponents() {
        Velocity vel = new Velocity(2.5, 1.5);

        assertEquals(new Vector2D(2.5, 1.5), vel.Vec);
    }

    @Test
    public void relativeVelocity_CalculatesRelativeVelocity() {
        Velocity v1 = new Velocity(2, 1);
        Velocity v2 = new Velocity(-1, 2);

        Velocity actual = v1.RelativeVelocity(v2);

        assertEquals(3, actual.Vec.x());
        assertEquals(-1, actual.Vec.y());
    }
}
