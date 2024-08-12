package am.aua.hw.ui;

import am.aua.hw.core.Days;
import am.aua.hw.core.Times;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RemoveCommandWindow
{
    private CommandsPanelUser commandsPanelUser = null;

    private DayTimeWindow dayTimeWindow = null;

    public RemoveCommandWindow(CommandsPanelUser commandsPanelUser)
    {
        dayTimeWindow = new DayTimeWindow(new removeDayTimeListener(this));
        dayTimeWindow.setVisible(true);

        this.commandsPanelUser = commandsPanelUser;
    }

    private void fetchDayAndTimeAndExecuteRemove()
    {
        commandsPanelUser.executeRemoveCommand(dayTimeWindow.getDay(), dayTimeWindow.getTime());
    }

    public static class removeDayTimeListener implements ActionListener
    {
        RemoveCommandWindow window = null;
        public removeDayTimeListener(RemoveCommandWindow window)
        {
            this.window = window;
        }
        @Override
        public void actionPerformed(ActionEvent e)
        {
            window.fetchDayAndTimeAndExecuteRemove();
        }
    }
}
