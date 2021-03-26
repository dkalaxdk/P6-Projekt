package utils;

import Dibbidut.utils.Vector;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class VectorTest {

    @Test
    public void vector_constructorWithNoArgumentsReturnsZeroVector() {
        Vector vec = new Vector();

        assertEquals(0, vec.X);
        assertEquals(0, vec.Y);
    }

    @Test
    public void vector_constructorWithArgumentsSetsComponentValues() {
        Vector vec = new Vector(1, 2);

        assertEquals(1, vec.X);
        assertEquals(2, vec.Y);
    }

    @Test
    public void vector_acceptsParametersOfTypeDouble() {
        Vector vec = new Vector(2.5, 2.5);

        assertEquals(2.5, vec.X);
        assertEquals(2.5, vec.Y);
    }

    @Nested
    @DisplayName("Vector.Equals Tests")
    class EqualsTests {
        @Test
        public void equals_TrueWithSameVectorComponents() {
            Vector vec1 = new Vector(1, 1);
            Vector vec2 = new Vector(1, 1);

        /* IntelliJ suggest simplifying this assertion
         However I think this assertion best explains what is tested */
            assertTrue(vec1.equals(vec2));
        }

        @Test
        public void equals_FalseWithDifferentVectorComponents() {
            Vector vec1 = new Vector(1, 1);
            Vector vec2 = new Vector(2, 2);

            assertFalse(vec1.equals(vec2));
        }

        @Test
        public void equals_FalseWithDifferentX() {
            Vector vec1 = new Vector(1, 1);
            Vector vec2 = new Vector(2, 1);

            assertFalse(vec1.equals(vec2));
        }
        @Test
        public void equals_FalseWithDifferentY() {
            Vector vec1 = new Vector(1, 1);
            Vector vec2 = new Vector(1, 2);

            assertFalse(vec1.equals(vec2));
        }
    }
}
