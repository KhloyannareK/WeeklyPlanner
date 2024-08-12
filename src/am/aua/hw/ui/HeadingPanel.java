package am.aua.hw.ui;

import javax.swing.*;
import java.awt.*;

/**
 * Class will represent The upper side of the main window of the Planner.
 * It may contain menu bar, some labels, etc.
 */
public class HeadingPanel extends JPanel
{
    // Constants
    private static final int LABEL_FONT_SIZE = 40;
    private static final int LABEL_FONT_TYPE = Font.BOLD;
    private static final Color LABEL_BACKGROUND = new Color(0,50,132);
    private static final Color LABEL_FOREGROUND = Color.WHITE;

    private JLabel headingLabel = null;

    public HeadingPanel()
    {
        // Panel specific initialization
        super();
        setLayout(new GridLayout(1, 1));
        setBackground(LABEL_BACKGROUND);

        // Heading label
        headingLabel = new JLabel("Your Weekly Planner");
        headingLabel.setFont(new Font(headingLabel.getText(), LABEL_FONT_TYPE, LABEL_FONT_SIZE));
        headingLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headingLabel.setVerticalAlignment(SwingConstants.CENTER);
        headingLabel.setBackground(LABEL_BACKGROUND);
        headingLabel.setForeground(LABEL_FOREGROUND);
        add(headingLabel);
    }
}
