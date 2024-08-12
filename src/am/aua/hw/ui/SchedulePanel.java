package am.aua.hw.ui;

import am.aua.hw.core.Days;
import am.aua.hw.core.Times;
import am.aua.hw.core.WorkEvent;
import am.aua.hw.core.WorkWeek;

import javax.swing.*;
import java.awt.*;

public class SchedulePanel extends JPanel
{
    // Constants
    private static final int NUMBER_OF_SLOTS_IN_ROW = WorkWeek.NUMBER_OF_TIME_SLOTS + 1;
    private static final int NUMBER_OF_SLOTS_IN_COL = WorkWeek.NUMBER_OF_DAYS + 1;
    private static final Color PANEL_BACKGROUND = Color.BLACK;

    private Slot[][] slots = new Slot[NUMBER_OF_SLOTS_IN_ROW][NUMBER_OF_SLOTS_IN_COL];

    public SchedulePanel()
    {
        // Panel specific initialization
        super();
        setLayout(new GridLayout(NUMBER_OF_SLOTS_IN_ROW, NUMBER_OF_SLOTS_IN_COL));
        setBackground(PANEL_BACKGROUND);

        // Initialize top most row
        slots[0][0] = new Slot("-", "");
        add(slots[0][0]);
        for(int dayIdx = 0; dayIdx < NUMBER_OF_SLOTS_IN_COL - 1; ++dayIdx) {
            String dayStr = Days.toDays(dayIdx).toString();
            slots[0][dayIdx] = new Slot(dayStr, dayStr + " column.");
            add(slots[0][dayIdx]);
        }

        // initialize time and event slots
        for(int row = 1; row < NUMBER_OF_SLOTS_IN_ROW; ++row) {
            // time slots
            if(row == 1) slots[row][0] = new Slot("MORNING", "MORNING column.");
            else slots[row][0] = new Slot("AFTERNOON", "AFTERNOON column.");
            add(slots[row][0]);
            // event slots
            for(int col = 1; col < NUMBER_OF_SLOTS_IN_COL; ++col) {
                slots[row][col] = new Slot();
                slots[row][col].setOpaque(true);
                add(slots[row][col]);
            }
        }
    }

    public void updateScheduleByWorkweek(WorkWeek workWeek)
    {
        if(workWeek == null) return;
        for(int dayIdx = 0; dayIdx < WorkWeek.NUMBER_OF_DAYS; ++dayIdx) {
            for(int timeIdx = 0; timeIdx < WorkWeek.NUMBER_OF_TIME_SLOTS; ++timeIdx) {
                if(workWeek.isEmpty(Days.toDays(dayIdx), Times.toTimes(timeIdx))) {
                    slots[timeIdx+1][dayIdx+1].setFullDescription("");
                    slots[timeIdx+1][dayIdx+1].setShortDescription("");
                }
                else {
                    slots[timeIdx+1][dayIdx+1].setFullDescription(workWeek.getFullDetailsAtUnchecked(Days.toDays(dayIdx), Times.toTimes(timeIdx)));
                    slots[timeIdx+1][dayIdx+1].setShortDescription(workWeek.getTitleAtUnchecked(Days.toDays(dayIdx), Times.toTimes(timeIdx)));
                }
            }
        }
    }

    public void updateSlot(Days day, Times time, String fullDescription, String shortDescription)
    {
        slots[time.ordinal()+1][day.ordinal()+1].setFullDescription(fullDescription);
        slots[time.ordinal()+1][day.ordinal()+1].setShortDescription(shortDescription);
    }

    public static class Slot extends JLabel
    {
        // Constants
        private static final int LABEL_FONT_SIZE = 30;
        private static final int LABEL_FONT_TYPE = Font.BOLD;
        private static final Color DEFAULT_BACKGROUND = Color.WHITE;
        private static final Color DEFAULT_FOREGROUND = new Color( 0x33,0xB8,0x64);
        private static final Color BUSY_BACKGROUND = DEFAULT_FOREGROUND;
        private static final Color BUSY_FOREGROUND = DEFAULT_BACKGROUND;
        private static final String DEFAULT_EMPTY_STRING = "EMPTY";

        private String shortDescription = DEFAULT_EMPTY_STRING;
        private String fullDescription = "";

        public Slot()
        {
            super();
            checkAndSetSlotState();
        }

        public Slot(String shortDescription, String fullDescription)
        {
            super();
            this.shortDescription = shortDescription;
            this.fullDescription = fullDescription;
            checkAndSetSlotState();
        }

        public void setShortDescription(String shortDescription)
        {
            this.shortDescription = shortDescription;
            checkAndSetSlotState();
        }

        public void setFullDescription(String fullDescription)
        {
            this.fullDescription = fullDescription;
        }

        private void checkAndSetSlotState()
        {
            if(shortDescription == null || shortDescription.isEmpty() || shortDescription.equals(DEFAULT_EMPTY_STRING)) {
                setDefault();
            }
            else {
                setBusy();
            }
            setHorizontalAlignment(CENTER);
            setHorizontalAlignment(CENTER);
        }

        private void setDefault() {
            this.shortDescription = DEFAULT_EMPTY_STRING;
            this.fullDescription = "";
            setBackground(DEFAULT_BACKGROUND);
            setForeground(DEFAULT_FOREGROUND);
            setText(shortDescription);
        }

        private void setBusy()
        {
            setBackground(BUSY_BACKGROUND);
            setForeground(BUSY_FOREGROUND);
            setText(shortDescription);
            // full description for the hint
        }


    }
}
