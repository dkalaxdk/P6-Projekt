package Dibbidut.Interfaces;

import Dibbidut.Classes.AISData;

public interface IAISBuffer {
    public AISData Pop();
    public void Push(AISData data);
    public int size();
}
