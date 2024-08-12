package am.aua.hw.ui;

import am.aua.hw.core.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddCommandWindow extends JFrame
{
    private CommandsPanelUser commandsPanelUser = null;

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    private String panelTypeToAdd = null;

    public AddCommandWindow(CommandsPanelUser commandsPanel)
    {
        super("Add Command");
        setBackground(EventTypePanel.BACKGROUND_COLOR);
        setLayout(new GridLayout(2,1));
        setSize(WIDTH, HEIGHT);
        this.commandsPanelUser = commandsPanel;

        add(new EventTypePanel(this));
    }

    private void addMeetingPanel()
    {
        add(new MeetingPanel());
    }

    private void addVideoCallPanel()
    {
        add(new VideoCallPanel());
    }

    private void addHorseTennisPanel()
    {
        add(new HorseTennisPanel());
    }

    private void addPonyPingPong()
    {
        add(new PonyPingPongPanel());
    }

    private static class EventTypePanel extends JPanel
    {
        // Constants
        private static final int TEXT_FONT = Font.BOLD;
        private static final int TEXT_SIZE = 20;
        private static final int LABEL_TEXT_LIMIT = 60;
        private static final Color TEXT_COLOR = Color.WHITE;
        private static final Color BUTTON_BACKGROUND = new Color(0,104,132);;
        private static final Color BACKGROUND_COLOR = Color.BLACK;

        private JLabel eventTypeLabel = null;
        private JButton nextButton = null;
        private JMenu menu = null;
        private MenuItemHandler menuItemHandler = null;

        public EventTypePanel(AddCommandWindow addCommandWindow)
        {
            // Panel specific initialization
            super();
            setLayout(new GridLayout(3,1));
            setBackground(BACKGROUND_COLOR);

            // text label
            eventTypeLabel = new JLabel("Event Type:", SwingConstants.CENTER);
            eventTypeLabel.setForeground(TEXT_COLOR);
            eventTypeLabel.setFont(new Font(eventTypeLabel.getText(), TEXT_FONT, TEXT_SIZE));
            add(eventTypeLabel);

            // menu item
            menuItemHandler = new MenuItemHandler(addCommandWindow);
            menu = new JMenu("Event >");
            menu.setBackground(BACKGROUND_COLOR);
            menu.setForeground(TEXT_COLOR);
            menu.setFont(new Font(menu.getText(), TEXT_FONT, TEXT_SIZE));
            menu.setPopupMenuVisible(true);
            menu.setHorizontalAlignment(SwingConstants.CENTER);
            JMenuItem[] menuItems = {new JMenuItem("Meeting"), new JMenuItem("VideoCall"),
                    new JMenuItem("HorseTennis"), new JMenuItem("PonyPingPong")};
            for (JMenuItem menuItem : menuItems) {
                menuItem.setBackground(BACKGROUND_COLOR);
                menuItem.setForeground(TEXT_COLOR);
                menuItem.setFont(new Font(menuItem.getText(), TEXT_FONT, TEXT_SIZE));
                menuItem.addActionListener(menuItemHandler);
                menu.add(menuItem);
            }
            JMenuBar bar = new JMenuBar();
            bar.add(menu);
            bar.setBackground(BACKGROUND_COLOR);
            bar.setForeground(TEXT_COLOR);
            bar.setFont(new Font(menu.getText(), TEXT_FONT, TEXT_SIZE));
            add(bar);

            // button
            nextButton = new JButton("Next");
            nextButton.setForeground(BUTTON_BACKGROUND);
            nextButton.setBackground(TEXT_COLOR);
            nextButton.setFont(new Font(nextButton.getText(), TEXT_FONT, TEXT_SIZE));
            nextButton.addActionListener(new MenuItemButtonListener(addCommandWindow));
            add(nextButton);
        }

        public class MenuItemButtonListener implements ActionListener
        {
            private AddCommandWindow window = null;
            public MenuItemButtonListener(AddCommandWindow window)
            {
                this.window = window;
            }
            @Override
            public void actionPerformed(ActionEvent e) {
                if(window.panelTypeToAdd != null) {
                    switch (window.panelTypeToAdd) {
                        case "Meeting": {
                            window.addMeetingPanel();
                            break;
                        }
                        case "VideoCall": {
                            window.addVideoCallPanel();
                            break;
                        }
                        case "HorseTennis": {
                            window.addHorseTennisPanel();
                            break;
                        }
                        case "PonyPingPong": {
                            window.addPonyPingPong();
                            break;
                        }
                    }
                }
            }
        }

        public static class MenuItemHandler implements ActionListener
        {
            private AddCommandWindow window = null;
            public MenuItemHandler(AddCommandWindow window)
            {
                this.window = window;
            }
            @Override
            public void actionPerformed(ActionEvent e) {
                window.panelTypeToAdd = e.getActionCommand();
            }
        }
    }

    private abstract class AddStepPanel extends JPanel implements ActionListener
    {
        // Constants
        protected static final int TEXT_FONT = Font.BOLD;
        protected static final int TEXT_SIZE = 20;
        protected static final Color TEXT_COLOR = Color.WHITE;
        protected static final Color BUTTON_BACKGROUND = new Color(0,104,132);;
        protected static final Color BACKGROUND_COLOR = Color.BLACK;

        protected JLabel label = null;
        protected JButton addButton = null;
        protected JLabel titleLabel = null;
        protected JTextField titleTextField = null;
        private DayTimeWindow dayTimeWindow = null;

        public AddStepPanel(String labelText)
        {
            super();
            setBackground(BACKGROUND_COLOR);

            // add button
            addButton = new JButton("Add event");
            addButton.setForeground(BUTTON_BACKGROUND);
            addButton.setBackground(TEXT_COLOR);
            addButton.setFont(new Font(addButton.getText(), TEXT_FONT, TEXT_SIZE));
            addButton.addActionListener(new AddEventFinalStepHandler(this));

            // text label
            label = new JLabel(labelText, SwingConstants.CENTER);
            label.setForeground(TEXT_COLOR);
            label.setFont(new Font(label.getText(), TEXT_FONT, TEXT_SIZE));

            // title label
            titleLabel = new JLabel("Title:", SwingConstants.CENTER);
            titleLabel.setForeground(TEXT_COLOR);
            titleLabel.setFont(new Font("Title:", TEXT_FONT, TEXT_SIZE));
            add(titleLabel);

            // title text field
            titleTextField = new JTextField("Title here!");
            titleTextField.setForeground(TEXT_COLOR);
            titleTextField.setBackground(BACKGROUND_COLOR);
            titleTextField.setFont(new Font(titleTextField.getText(), TEXT_FONT, TEXT_SIZE));
            add(titleTextField);
        }

        public abstract String getSerializedParameters();

        public void openDayTimeWindow()
        {
            dayTimeWindow = new DayTimeWindow(this);
            dayTimeWindow.setVisible(true);
        }

        public void actionPerformed(ActionEvent e) {
            Days day = dayTimeWindow.getDay();
            Times time = dayTimeWindow.getTime();
            String serializedParameters = getSerializedParameters();
            commandsPanelUser.executeAddCommand(serializedParameters, day, time);
        }

        private static class AddEventFinalStepHandler implements ActionListener
        {
            AddStepPanel panel = null;
            public AddEventFinalStepHandler(AddStepPanel panel)
            {
                this.panel = panel;
            }
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.openDayTimeWindow();
            }
        }



    }

    private class VideoCallPanel extends AddStepPanel
    {
        private static final int MAX_EMAIL_COUNT = 5;
        private static final int MAX_EMAIL_LENGTH = 30;

        private static JTextArea videoCallTextArea = null;

        public VideoCallPanel()
        {
            super("Peer email address(es):");
            setLayout(new GridLayout(4,1));

            // label
            add(label);

            // text area
            videoCallTextArea = new JTextArea("emails here!", MAX_EMAIL_COUNT, MAX_EMAIL_LENGTH);
            videoCallTextArea.setFont(new Font(videoCallTextArea.getText(), TEXT_FONT, TEXT_SIZE));
            videoCallTextArea.setBackground(BACKGROUND_COLOR);
            videoCallTextArea.setForeground(TEXT_COLOR);
            add(videoCallTextArea);

            // button
            add(addButton);
        }

        public String getSerializedParameters()
        {
            // note validating further assume it is all in new line and contains no space
            String[] addresses = videoCallTextArea.getText().split("\n", 10);
            String joinedAddresses = String.join(WorkEvent.PARAMETER_DELIMITER, addresses);
            return "VIDEOCALL" + WorkEvent.PARAMETER_DELIMITER + titleTextField.getText() + WorkEvent.PARAMETER_DELIMITER + joinedAddresses;
        }
    }

    private class MeetingPanel extends AddStepPanel
    {
        private static JTextField latitudeTextField = null;
        private static JTextField longitudeTextField = null;

        public MeetingPanel()
        {
            super("latitude/longitude:");
            setLayout(new GridLayout(6,1));

            // label
            add(label);

            // text fields
            latitudeTextField = new JTextField("latitude here!");
            latitudeTextField.setFont(new Font(latitudeTextField.getText(), TEXT_FONT, TEXT_SIZE));
            latitudeTextField.setBackground(BACKGROUND_COLOR);
            latitudeTextField.setForeground(TEXT_COLOR);
            add(latitudeTextField);
            longitudeTextField = new JTextField("longitude here!");
            longitudeTextField.setFont(new Font(longitudeTextField.getText(), TEXT_FONT, TEXT_SIZE));
            longitudeTextField.setBackground(BACKGROUND_COLOR);
            longitudeTextField.setForeground(TEXT_COLOR);
            add(longitudeTextField);

            // button
            add(addButton);
        }

        @Override
        public String getSerializedParameters() {
            return "MEETING" +  WorkEvent.PARAMETER_DELIMITER + titleTextField.getText() + WorkEvent.PARAMETER_DELIMITER +
                    latitudeTextField.getText() + WorkEvent.PARAMETER_DELIMITER + longitudeTextField.getText();
        }
    }

    private class HorseTennisPanel extends AddStepPanel
    {
        public HorseTennisPanel()
        {
            super("HorseTennis type has no more parameters");
            setLayout(new GridLayout(4,1));

            // label
            add(label);

            // button
            add(addButton);
        }

        @Override
        public String getSerializedParameters() {
            return "HORSETENNIS" + WorkEvent.PARAMETER_DELIMITER + titleTextField.getText() + WorkEvent.PARAMETER_DELIMITER + "Whatever";
        }
    }

    private class PonyPingPongPanel extends AddStepPanel
    {
        public PonyPingPongPanel()
        {
            super("PonyPingPong type has no more parameters");
            setLayout(new GridLayout(4,1));

            // label
            add(label);

            // button
            add(addButton);
        }

        @Override
        public String getSerializedParameters() {
            return "PONYPINGPONG" + WorkEvent.PARAMETER_DELIMITER + titleTextField.getText() + WorkEvent.PARAMETER_DELIMITER + "Whatever";
        }
    }
}
