package Dibbidut.Classes;

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

    public GUI(CASystem system) {
        this.system = system;
        this.setIgnoreRepaint(true);

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setAlignmentY(Component.TOP_ALIGNMENT);


        simulationTimeLabel = new JLabel("Simulation time: " + system.inputSimulator.currentTime.toLocalTime(), JLabel.LEFT);
        simulationTimeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        simulationTimeLabel.setIgnoreRepaint(true);
        add(simulationTimeLabel);

        timeFactorLabel = new JLabel("Time factor: " + system.inputSimulator.GetTimeFactor());
        timeFactorLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        timeFactorLabel.setIgnoreRepaint(true);
        add(timeFactorLabel);

        timeFactorSlider = createSlider(0, 120, 1);
        add(timeFactorSlider);

        timeFrameSlider = createSlider(1, 100, 1);
        add(timeFrameSlider);

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
                float a = (float) timeFactorSlider.getValue();
                system.inputSimulator.SetTimeFactor(a);
                timeFactorLabel.setText("Time factor: " + system.inputSimulator.GetTimeFactor());
            }
            else if (slider == this.zoomSlider) {
                system.display.zoom = zoomSlider.getValue();
            }
            else if (slider == this.timeFrameSlider) {
                system.timeFrame = timeFrameSlider.getValue();
            }
        }
    }
}
