package am.aua.hw.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoadFromFileCommandWindow extends JFrame implements ActionListener
{
    private CommandsPanelUser commandsPanelUser = null;

    // Constants
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    private static final int TEXT_FONT = Font.BOLD;
    private static final int TEXT_SIZE = 20;
    private static final int LABEL_TEXT_LIMIT = 60;
    private static final Color TEXT_COLOR = Color.WHITE;
    private static final Color BUTTON_BACKGROUND = new Color(0,104,132);;
    private static final Color BACKGROUND_COLOR = Color.BLACK;
    private static final String DEFAULT_PATH = "schedule.txt";

    private JLabel loadFileLabel = null;
    private JButton loadButton = null;
    private JTextField pathText = null;

    public LoadFromFileCommandWindow(CommandsPanelUser commandsPanelUser)
    {
        // self initialization
        super("Load Command");
        setBackground(BACKGROUND_COLOR);
        setLayout(new GridLayout(2,1));
        setSize(WIDTH, HEIGHT);
        this.commandsPanelUser = commandsPanelUser;

        // label + text field panel
        JPanel labelTextFiledPanel = new JPanel();
        labelTextFiledPanel.setLayout(new GridLayout(1, 2));
        labelTextFiledPanel.setBackground(BACKGROUND_COLOR);

        // text label
        loadFileLabel = new JLabel("File path:", SwingConstants.CENTER);
        loadFileLabel.setForeground(TEXT_COLOR);
        loadFileLabel.setFont(new Font(loadFileLabel.getText(), TEXT_FONT, TEXT_SIZE));
        labelTextFiledPanel.add(loadFileLabel);

        // text field
        pathText = new JTextField(DEFAULT_PATH);
        pathText.setForeground(TEXT_COLOR);
        pathText.setBackground(BACKGROUND_COLOR);
        pathText.setFont(new Font(pathText.getText(), TEXT_FONT, TEXT_SIZE));

        labelTextFiledPanel.add(loadFileLabel);
        labelTextFiledPanel.add(pathText);

        // load button
        loadButton = new JButton("Load");
        loadButton.setForeground(BUTTON_BACKGROUND);
        loadButton.setBackground(TEXT_COLOR);
        loadButton.setFont(new Font(loadButton.getText(), TEXT_FONT, TEXT_SIZE));
        loadButton.addActionListener(this);

        add(labelTextFiledPanel);
        add(loadButton);
    }

    public void actionPerformed(ActionEvent e)
    {
        commandsPanelUser.executeLoadCommand(pathText.getText());
    }
}
