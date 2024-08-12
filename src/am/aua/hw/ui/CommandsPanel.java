package am.aua.hw.ui;

/*
        ADD_EVENT,
        REMOVE_EVENT,
        PRINT_DETAILS,
        LOAD_FROM_FILE,
        SAVE_TO_FILE,
        QUIT,
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CommandsPanel extends JPanel
{
    private CommandsPanelUser commandsUser = null;

    // Constants
    private static final int BUTTON_FONT_SIZE = 30;
    private static final int BUTTON_FONT_TYPE = Font.BOLD;
    private static final Color BUTTON_BACKGROUND = new Color(0,104,132);
    private static final Color BUTTON_FOREGROUND = Color.WHITE;

    private JButton addButton = new JButton("Add event"), removeButton = new JButton("Remove event");
    private JButton printButton = new JButton("Print event details"),  loadButton = new JButton("Load events from file");
    private JButton saveButton = new JButton("Save events to file"),  quitButton = new JButton("Quit");

    public CommandsPanel(CommandsPanelUser commandsUser)
    {
        super();
        setLayout(new GridLayout(2,3));
        connectListeners();
        initializeAndAddButtonsToPanel();
        this.commandsUser = commandsUser;
    }

    private void connectListeners()
    {
        addButton.addActionListener(new addButtonListener());
        removeButton.addActionListener(new removeButtonListener());
        printButton.addActionListener(new printButtonListener());
        loadButton.addActionListener(new loadButtonListener());
        saveButton.addActionListener(new saveButtonListener());
        quitButton.addActionListener(new quitButtonListener());
    }

    private void initializeAndAddButtonsToPanel()
    {
        addButton.setBackground(BUTTON_BACKGROUND); addButton.setForeground(BUTTON_FOREGROUND);
        addButton.setFont(new Font(addButton.getText(), BUTTON_FONT_TYPE, BUTTON_FONT_SIZE));
        removeButton.setBackground(BUTTON_BACKGROUND); removeButton.setForeground(BUTTON_FOREGROUND);
        removeButton.setFont(new Font(removeButton.getText(), BUTTON_FONT_TYPE, BUTTON_FONT_SIZE));
        printButton.setBackground(BUTTON_BACKGROUND); printButton.setForeground(BUTTON_FOREGROUND);
        printButton.setFont(new Font(printButton.getText(), BUTTON_FONT_TYPE, BUTTON_FONT_SIZE));
        loadButton.setBackground(BUTTON_BACKGROUND); loadButton.setForeground(BUTTON_FOREGROUND);
        loadButton.setFont(new Font(loadButton.getText(), BUTTON_FONT_TYPE, BUTTON_FONT_SIZE));
        saveButton.setBackground(BUTTON_BACKGROUND); saveButton.setForeground(BUTTON_FOREGROUND);
        saveButton.setFont(new Font(saveButton.getText(), BUTTON_FONT_TYPE, BUTTON_FONT_SIZE));
        quitButton.setBackground(BUTTON_BACKGROUND); quitButton.setForeground(BUTTON_FOREGROUND);
        quitButton.setFont(new Font(quitButton.getText(), BUTTON_FONT_TYPE, BUTTON_FONT_SIZE));
        add(addButton);
        add(removeButton);
        add(printButton);
        add(loadButton);
        add(saveButton);
        add(quitButton);
    }

    private class addButtonListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            AddCommandWindow addCommandWindow = new AddCommandWindow(commandsUser);
            addCommandWindow.setVisible(true);
        }
    }

    private class removeButtonListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            RemoveCommandWindow removeCommandWindow = new RemoveCommandWindow(commandsUser);
        }
    }

    private class printButtonListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            PrintDetailsWindow window = new PrintDetailsWindow(commandsUser);
        }
    }

    private class loadButtonListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            LoadFromFileCommandWindow window = new LoadFromFileCommandWindow(commandsUser);
            window.setVisible(true);
        }
    }

    private class saveButtonListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            SaveToFileCommandWindow window = new SaveToFileCommandWindow(commandsUser);
            window.setVisible(true);
        }
    }

    private class quitButtonListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            commandsUser.executeQuitCommand();
        }
    }
}
