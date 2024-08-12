package am.aua.hw.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SaveToFileCommandWindow extends JFrame implements ActionListener
{
    private CommandsPanelUser commandsPanelUser = null;

    // Constants
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    private static final int TEXT_FONT = Font.BOLD;
    private static final int TEXT_SIZE = 20;
    private static final Color TEXT_COLOR = Color.WHITE;
    private static final Color BUTTON_BACKGROUND = new Color(0,104,132);;
    private static final Color BACKGROUND_COLOR = Color.BLACK;
    private static final String DEFAULT_PATH = "schedule.txt";

    private JLabel saveFileLabel = null;
    private JButton saveButton = null;
    private JTextField pathText = null;

    public SaveToFileCommandWindow(CommandsPanelUser commandsPanelUser)
    {
        // self initialization
        super("Save Command");
        setBackground(BACKGROUND_COLOR);
        setLayout(new GridLayout(2,1));
        setSize(WIDTH, HEIGHT);
        this.commandsPanelUser = commandsPanelUser;

        // label + text field panel
        JPanel labelTextFiledPanel = new JPanel();
        labelTextFiledPanel.setLayout(new GridLayout(1, 2));
        labelTextFiledPanel.setBackground(BACKGROUND_COLOR);

        // text label
        saveFileLabel = new JLabel("File path:", SwingConstants.CENTER);
        saveFileLabel.setForeground(TEXT_COLOR);
        saveFileLabel.setFont(new Font(saveFileLabel.getText(), TEXT_FONT, TEXT_SIZE));
        labelTextFiledPanel.add(saveFileLabel);

        // text field
        pathText = new JTextField(DEFAULT_PATH);
        pathText.setForeground(TEXT_COLOR);
        pathText.setBackground(BACKGROUND_COLOR);
        pathText.setFont(new Font(pathText.getText(), TEXT_FONT, TEXT_SIZE));

        labelTextFiledPanel.add(saveFileLabel);
        labelTextFiledPanel.add(pathText);

        // load button
        saveButton = new JButton("Save");
        saveButton.setForeground(BUTTON_BACKGROUND);
        saveButton.setBackground(TEXT_COLOR);
        saveButton.setFont(new Font(saveButton.getText(), TEXT_FONT, TEXT_SIZE));
        saveButton.addActionListener(this);

        add(labelTextFiledPanel);
        add(saveButton);
    }

    public void actionPerformed(ActionEvent e)
    {
        commandsPanelUser.executeSaveCommand(pathText.getText());
    }
}
