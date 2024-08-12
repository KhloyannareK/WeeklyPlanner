package am.aua.hw.ui;

import am.aua.hw.core.Days;
import am.aua.hw.core.Schedulable;
import am.aua.hw.core.Times;
import am.aua.hw.core.WorkWeek;
import am.aua.hw.exceptions.MalformedStringParameterException;
import am.aua.hw.exceptions.WorkWeekException;
import am.aua.hw.utils.FileUtil;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class MainWindow extends JFrame implements CommandsPanelUser
{
    // Constants
    private static final int WINDOW_WIDTH = 1920, WINDOW_HEIGHT = 1080;

    private static WorkWeek workWeek = new WorkWeek();

    private HeadingPanel headingPanel = null;
    private CommandsPanel commandsPanel = null;
    private SchedulePanel schedulePanel = null;
    public MainWindow()
    {
        // Main Window specific initialization
        super("Weekly Planner");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        getContentPane().setBackground(Color.CYAN);
        setLayout(new GridLayout(3, 1));

        // Heading
        headingPanel = new HeadingPanel();
        add(headingPanel);

        // Commands panel
        commandsPanel = new CommandsPanel(this);
        add(commandsPanel);

        // Schedule panel
        schedulePanel = new SchedulePanel();
        add(schedulePanel);
    }

    @Override
    public void executeAddCommand(String serializedParameters, Days day, Times time) {
        System.out.println("Adding event... " + serializedParameters + " " + day + " " + time);
        Schedulable event = null;
        try {
            event = WorkWeek.createEventFromStringParameter(serializedParameters);
            workWeek.addToSchedule(event, day, time);
        }
        catch (MalformedStringParameterException e) {
            System.out.println(e.getMessage());
            // AMIGO TODO
            return;
        }
        catch (WorkWeekException e) {
            System.out.println(e.getMessage());
            // AMIGO TODO
            System.out.println(); // just to ignore IDE warning
            return;
        }
        schedulePanel.updateSlot(day, time, event.getFullDescription(), event.getShortDescription());
    }

    @Override
    public void executeRemoveCommand(Days day, Times time) {
        System.out.println("Removing event... " + day + " " + time);
        try {
            workWeek.removeFromSchedule(day, time);
        }
        catch (WorkWeekException e) {
            System.out.println(e.getMessage());
            // AMIGO TODO
            return;
        }
        schedulePanel.updateSlot(day, time, "", "");
    }

    @Override
    public void executePrintDetailsCommand(Days day, Times time) {
        System.out.println("Printing details... " + day.toString() + " " + time.toString());

        JLabel label = new JLabel("");
        if(workWeek.isEmpty(day, time)) {
            label.setText("EMPTY");
        }
        else {
            label.setText(workWeek.getFullDetailsAtUnchecked(day, time));
        }
        label.setBackground(Color.BLACK);
        label.setForeground(Color.WHITE);
        label.setFont(new Font(label.getText(), Font.PLAIN, 32));

        JFrame window = new JFrame();
        window.setSize(1200, 600);
        window.getContentPane().setBackground(Color.BLACK);
        window.add(label);
        window.setVisible(true);
    }

    @Override
    public void executeLoadCommand(String path) {
        System.out.println("Loading work week... " + path);
        String[] serializedEvents;
        try {
            serializedEvents = FileUtil.loadStringsFromFile(path);
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
            // AMIGO TODO
            return;
        }
        try {
            WorkWeek nweWorkweek = WorkWeek.generateWorkweekFromStrings(serializedEvents);
            workWeek = nweWorkweek;
        }
        catch (WorkWeekException | MalformedStringParameterException e) {
            System.out.println(e.getMessage());
            // AMIGO TODO
            return;
        }
        schedulePanel.updateScheduleByWorkweek(workWeek);
    }

    @Override
    public void executeSaveCommand(String path) {
        System.out.println("Saving work week... " + path);
        String[] serializedEvents = new String[WorkWeek.NUMBER_OF_TIME_SLOTS * WorkWeek.NUMBER_OF_DAYS];
        for(int i = 0; i < WorkWeek.NUMBER_OF_TIME_SLOTS * WorkWeek.NUMBER_OF_DAYS; ++i) {
            Days day = Days.toDays(i % WorkWeek.NUMBER_OF_DAYS);
            Times time = Times.toTimes(i / WorkWeek.NUMBER_OF_DAYS);
            serializedEvents[i] = workWeek.getToSaveFileStringAt(day, time);
        }
        try {
            FileUtil.saveStringsToFile(serializedEvents, path);

        }
        catch (IOException e) {
            System.out.println(e.getMessage());
            // AMIGO TODO
        }
    }

    @Override
    public void executeQuitCommand() {
        System.out.println("Quiting...");
        System.exit(0);
    }
}
