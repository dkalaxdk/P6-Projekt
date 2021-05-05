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

    public GUI(CASystem system) {
        this.system = system;
        this.setIgnoreRepaint(true);

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setAlignmentY(Component.TOP_ALIGNMENT);

        simulationTimeLabel = new JLabel("Simulation time: " + system.inputSimulator.currentTime.toLocalTime(), JLabel.LEFT);
        simulationTimeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(simulationTimeLabel);

        timeFactorLabel = new JLabel("Time factor: " + system.inputSimulator.GetTimeFactor());
        timeFactorLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(timeFactorLabel);

        timeFactorSlider = createSlider(0, 120, 1);
        add(timeFactorSlider);

        timeFrameLabel = new JLabel("Time frame: " + system.timeFrame);
        timeFrameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(timeFrameLabel);

        timeFrameSlider = createSlider(0, 100, 1);
        add(timeFrameSlider);

        lookAheadLabel = new JLabel("Lookahead: " + system.lookAhead);
        lookAheadLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(lookAheadLabel);

        lookAheadValues = new JLabel("Seconds: " + system.timeFrame * system.lookAhead +
                ", Minutes: " + (system.timeFrame * system.lookAhead) / 60);
        lookAheadValues.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(lookAheadValues);

        lookAheadSlider = createSlider(0, 100, 1);
        add(lookAheadSlider);

        zoomSlider = createSlider(1, 10, 1);
        add(zoomSlider);

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

            if (slider == this.timeFactorSlider) {
                float value = (float) timeFactorSlider.getValue();
                system.inputSimulator.SetTimeFactor(value);
                timeFactorLabel.setText("Time factor: " + value);
            }
            else if (slider == this.zoomSlider) {
                system.display.zoom = zoomSlider.getValue();
            }
            else if (slider == this.timeFrameSlider) {

                float value = timeFrameSlider.getValue();
                system.timeFrame = (value == 0) ? 1 : value;
                timeFrameLabel.setText("Time frame: " + value);
            }
            else if (slider == this.lookAheadSlider) {
                float value = lookAheadSlider.getValue();
                system.lookAhead = (value == 0) ? 1 : value;
                lookAheadLabel.setText("Lookahead: " + value);
            }

            double seconds = Utility.roundToTwoDecimals(system.timeFrame * system.lookAhead);
            double minutes = Utility.roundToTwoDecimals((system.timeFrame * system.lookAhead) / 60);
            double hours = Utility.roundToTwoDecimals((system.timeFrame * system.lookAhead) / 3600);

            lookAheadValues.setText("Seconds: " + seconds + ", Minutes: " + minutes + ", Hours: " + hours);

            system.dirty = true;
        }
    }
}
