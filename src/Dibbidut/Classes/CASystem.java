package Dibbidut.Classes;

import Dibbidut.Interfaces.*;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class CASystem {

    public ArrayList<Ship> shipsInRange;
    public IAISBuffer buffer;

    public IOwnShip OS;
    public Ship ownShip;

    public IDisplay display;
    public IVelocityObstacle MVO;

    private boolean running;


    public CASystem() {
        shipsInRange = new ArrayList<Ship>();
        buffer = new AISBuffer();


        running = true;
    }

    public void Start() {

        long start = 0;
        long end = 0;
        long duration = 0;

        while(running) {

            if (buffer.size() > 0) {

                start = System.nanoTime();

                UpdateShipList();
                UpdateVelocityObstacles();
                UpdateDisplay();

                end = System.nanoTime();

                duration = TimeUnit.MILLISECONDS.convert(end - start, TimeUnit.NANOSECONDS);
            }

            try {
                TimeUnit.MILLISECONDS.sleep(10000 - duration);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            start = 0;
            end = 0;
            duration = 0;
        }
    }

    public void UpdateOwnShip() {

    }

    public void UpdateShipList() {
        while (buffer.size() > 0) {
            AISData data = buffer.Pop();
        }
    }

    public void UpdateVelocityObstacles() {

    }

    public void UpdateDisplay() {

    }
}
