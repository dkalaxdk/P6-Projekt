package Dibbidut.Classes.InputManagement;

import Dibbidut.Interfaces.IAISBuffer;
//import jdk.jshell.spi.ExecutionControl;

import java.util.LinkedList;
import java.util.Queue;

public class AISBuffer implements IAISBuffer {
    private Queue<AISData> buffer;

    public AISBuffer() {
        buffer = new LinkedList<>();
    }
    // todo: implementer blocking queue med særlig push (se anden todo)
    @Override
    public AISData Pop() {
        return null;
    }

    @Override
    public void Push(AISData data) {
        // todo: Put kun data i for skibe der er tæt nok på til at være relevante
    }

    @Override
    public int size() {
        return 0;
    }
}
