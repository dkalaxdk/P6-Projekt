package Dibbidut.Classes.UI;

import Dibbidut.Classes.CASystem;
import Dibbidut.Classes.Utility;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.time.LocalTime;
import java.time.temporal.TemporalAmount;

public class GUI extends JPanel implements ActionListener, WindowListener, ChangeListener {

    private final CASystem system;

    Timer timer;
    int delay;

    JSlider timeFactorSlider;
    JLabel timeFactorLabel;

    JLabel simulationTimeLabel;

    JSlider zoomSlider;

    JSlider timeFrameSlider;
    JLabel timeFrameLabel;

    JSlider lookAheadSlider;
    JLabel lookAheadLabel;

    JLabel lookAheadValues;

    JSlider xOffsetSlider;
    JSlider yOffsetSlider;

    public GUI(CASystem system) {
        this.system = system;
        this.setIgnoreRepaint(true);

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setAlignmentY(Component.TOP_ALIGNMENT);

        Font font = new Font(null, Font.PLAIN, 24);

        simulationTimeLabel = new JLabel("Simulation time: " + system.inputSimulator.currentTime.toLocalTime(), JLabel.LEFT);
        simulationTimeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        simulationTimeLabel.setFont(font);
        add(simulationTimeLabel);

        timeFactorLabel = new JLabel("Time factor: " + system.inputSimulator.GetTimeFactor());
        timeFactorLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        timeFactorLabel.setFont(font);
        add(timeFactorLabel);

        timeFactorSlider = createSlider(0, 120, 0);
        add(timeFactorSlider);

        timeFrameLabel = new JLabel("Time frame: " + system.timeFrame);
        timeFrameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        timeFrameLabel.setFont(font);
        add(timeFrameLabel);

//        lookAheadValues = new JLabel("00:00:00");
//        lookAheadValues.setAlignmentX(Component.LEFT_ALIGNMENT);
//        add(lookAheadValues);

//        timeFrameSlider = createSimpleSlider(0, 7200, 1);
//        add(timeFrameSlider);

        timeFrameSlider = createSlider(0, 120, 0);
        add(timeFrameSlider);

//        lookAheadLabel = new JLabel("Lookahead: " + system.lookAhead);
//        lookAheadLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
//        add(lookAheadLabel);

//        lookAheadSlider = createSimpleSlider(0, 4000, 1);
//        add(lookAheadSlider);

        zoomSlider = createSimpleSlider(-1000, 1000, 1);
        add(zoomSlider);

//        xOffsetSlider = createSlider(-1000, 1000, 0);
//        add(xOffsetSlider);
//
//        yOffsetSlider = createSlider(-1000, 1000, 0);
//        add(yOffsetSlider);

        setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));

        delay = 100;
        timer = new Timer(delay, this);
        timer.setInitialDelay(0);
        timer.setCoalesce(true);
        timer.start();
    }

    private JSlider createSlider(int min, int max, int current) {
        JSlider slider = new JSlider(JSlider.HORIZONTAL, min, max, current);

        slider.addChangeListener(this);

        slider.setMajorTickSpacing(10);
        slider.setMinorTickSpacing(1);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        Font font = new Font("Serif", Font.ITALIC, 15);
        slider.setFont(font);
        slider.setAlignmentX(Component.CENTER_ALIGNMENT);
        slider.setAlignmentY(Component.CENTER_ALIGNMENT);

        slider.setIgnoreRepaint(true);
        return slider;
    }

    private JSlider createSimpleSlider(int min, int max, int current) {
        JSlider slider = new JSlider(JSlider.HORIZONTAL, min, max, current);

        slider.addChangeListener(this);
        slider.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        slider.setAlignmentX(Component.CENTER_ALIGNMENT);
        slider.setAlignmentY(Component.CENTER_ALIGNMENT);
        slider.setIgnoreRepaint(true);
        return slider;
    }

    public Dimension getPreferredSize() {
        return new Dimension(500,500);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        simulationTimeLabel.setText("Simulation time: " + system.inputSimulator.currentTime.toLocalTime());
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {

    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if (e.getSource().getClass() == JSlider.class) {
            JSlider slider = (JSlider) e.getSource();

            boolean timeChange = false;

            if (slider == this.timeFactorSlider) {
                float value = (float) timeFactorSlider.getValue();
                system.inputSimulator.SetTimeFactor(value);
                timeFactorLabel.setText("Time factor: " + value);
            }
            else if (slider == this.zoomSlider) {
                double value = zoomSlider.getValue();

                value = (value == 0) ? 1 : value;

                value = value / 100;

                if (value < 0) {
                    value = 1 / (Math.abs(value));
                }

                system.display.zoom = value;
            }
            else if (slider == this.timeFrameSlider) {

                int value = timeFrameSlider.getValue();
                system.timeFrame = (value == 0) ? 1 : value * 60;
                timeFrameLabel.setText("Time frame: " + (value) + " minutes");

                timeChange = true;
            }
            else if (slider == this.lookAheadSlider) {
                float value = lookAheadSlider.getValue();
                system.lookAhead = (value == 0) ? 1 : value;
                lookAheadLabel.setText("Lookahead: " + value);

                timeChange = true;
            }
            else if (slider == this.xOffsetSlider) {
                system.display.xOffset = -xOffsetSlider.getValue();
            }
            else if (slider == this.yOffsetSlider) {
                system.display.yOffset = -yOffsetSlider.getValue();
            }


//            if (timeChange) {
//
//                int value = (int) (system.timeFrame * (system.lookAhead));
//
//                int hours = value / 3600;
//                int minutes = (value % 3600) / 60;
//                int seconds = (value % 3600) % 60;
//
//                LocalTime timeTo = LocalTime.of(hours, minutes, seconds);
//                LocalTime timeOf = system.inputSimulator.currentTime.toLocalTime().plusSeconds(value);
//
//                lookAheadValues.setText(timeTo + " \uD83E\uDC26 " + timeOf.toString() + "                 (" + value + " seconds)");
//            }

            system.dirty = true;
        }
    }
}
