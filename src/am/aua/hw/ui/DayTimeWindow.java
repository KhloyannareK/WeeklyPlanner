package am.aua.hw.ui;

import am.aua.hw.core.Days;
import am.aua.hw.core.Times;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DayTimeWindow extends JFrame
{
    // Constants
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int TEXT_FONT = Font.BOLD;
    private static final int TEXT_SIZE = 20;
    private static final int LABEL_TEXT_LIMIT = 60;
    private static final Color TEXT_COLOR = Color.WHITE;
    private static final Color BUTTON_BACKGROUND = new Color(0,104,132);;
    private static final Color BACKGROUND_COLOR = Color.BLACK;

    private JLabel label = null;
    private JButton addButton = null;
    private JMenu dayMenu = null;
    private JMenu timeMenu = null;
    private MenuItemHandler menuItemHandler = null;

    private Days day = null;;
    private Times time = null;

    public Days getDay()
    {
        return day;
    }

    public Times getTime()
    {
        return time;
    }

    public DayTimeWindow(ActionListener buttonListener)
    {
        // Panel specific initialization
        super();
        setLayout(new GridLayout(3,1));
        setBackground(BACKGROUND_COLOR);
        setSize(WIDTH, HEIGHT);

        // text label
        label = new JLabel("Day/Time:", SwingConstants.CENTER);
        label.setBackground(BACKGROUND_COLOR);
        label.setForeground(TEXT_COLOR);
        label.setFont(new Font(label.getText(), TEXT_FONT, TEXT_SIZE));
        label.setOpaque(true);
        add(label);

        // menu items
        JMenuBar bar = new JMenuBar();
        bar.setBackground(BACKGROUND_COLOR);
        bar.setForeground(TEXT_COLOR);
        bar.setFont(new Font("Day/Time", TEXT_FONT, TEXT_SIZE));

        // day menua
        menuItemHandler = new MenuItemHandler(this);
        dayMenu = new JMenu("Day >");
        dayMenu.setBackground(BACKGROUND_COLOR);
        dayMenu.setForeground(TEXT_COLOR);
        dayMenu.setFont(new Font(dayMenu.getText(), TEXT_FONT, TEXT_SIZE));
        dayMenu.setPopupMenuVisible(true);
        dayMenu.setHorizontalAlignment(SwingConstants.CENTER);
        JMenuItem[] dayMenuItems = {new JMenuItem("MONDAY"), new JMenuItem("TUESDAY"),
                new JMenuItem("WEDNESDAY"), new JMenuItem("THURSDAY"), new JMenuItem("FRIDAY")};
        for (JMenuItem menuItem : dayMenuItems) {
            menuItem.setBackground(BACKGROUND_COLOR);
            menuItem.setForeground(TEXT_COLOR);
            menuItem.setFont(new Font(menuItem.getText(), TEXT_FONT, TEXT_SIZE));
            menuItem.addActionListener(menuItemHandler);
            dayMenu.add(menuItem);
        }
        bar.add(dayMenu);

        // Time menua
        timeMenu = new JMenu("Time >");
        timeMenu.setBackground(BACKGROUND_COLOR);
        timeMenu.setForeground(TEXT_COLOR);
        timeMenu.setFont(new Font(dayMenu.getText(), TEXT_FONT, TEXT_SIZE));
        timeMenu.setPopupMenuVisible(true);
        timeMenu.setHorizontalAlignment(SwingConstants.CENTER);
        JMenuItem[] timeMenuItem = {new JMenuItem("MORNING"), new JMenuItem("AFTERNOON")};
        for (JMenuItem menuItem : timeMenuItem) {
            menuItem.setBackground(BACKGROUND_COLOR);
            menuItem.setForeground(TEXT_COLOR);
            menuItem.setFont(new Font(menuItem.getText(), TEXT_FONT, TEXT_SIZE));
            menuItem.addActionListener(menuItemHandler);
            timeMenu.add(menuItem);
        }
        bar.add(timeMenu);
        add(bar);

        // button
        addButton = new JButton("Done");
        addButton.setForeground(BUTTON_BACKGROUND);
        addButton.setBackground(TEXT_COLOR);
        addButton.setFont(new Font(addButton.getText(), TEXT_FONT, TEXT_SIZE));
        addButton.addActionListener(buttonListener);
        add(addButton);
    }

    public static class MenuItemHandler implements ActionListener
    {
        DayTimeWindow window = null;
        MenuItemHandler(DayTimeWindow dayTimeWindow)
        {
            window = dayTimeWindow;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            switch(e.getActionCommand())
            {
                case "MONDAY":
                {
                    window.day = Days.MONDAY;
                    break;
                }
                case "TUESDAY":
                {
                    window.day = Days.TUESDAY;
                    break;
                }
                case "WEDNESDAY":
                {
                    window.day = Days.WEDNESDAY;
                    break;
                }
                case "THURSDAY":
                {
                    window.day = Days.THURSDAY;
                    break;
                }
                case "FRIDAY":
                {
                    window.day = Days.FRIDAY;
                    break;
                }
                case "MORNING":
                {
                    window.time = Times.MORNING;
                    window.timeMenu.setText("MORNING");
                    return;
                }
                case "AFTERNOON":
                {
                    window.time = Times.AFTERNOON;
                    window.timeMenu.setText("AFTERNOON");
                    return;
                }
                default:
                {
                    return;
                }
            }
            window.dayMenu.setText(e.getActionCommand());
        }
    }
}
