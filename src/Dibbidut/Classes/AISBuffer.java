package Dibbidut.Classes;

import Dibbidut.Interfaces.IAISBuffer;
import Dibbidut.Interfaces.IDataInput;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class AISBuffer implements IAISBuffer {
    private Queue<AISData> buffer;

    public AISBuffer() {
        buffer = new LinkedList<>();
    }

    @Override
    public AISData Pop() {
        return null;
    }

    @Override
    public void Push(AISData data) {

    }
}
