package am.aua.hw.ui;

import am.aua.hw.core.Days;
import am.aua.hw.core.Times;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PrintDetailsWindow
{
    private CommandsPanelUser commandsPanelUser = null;

    private DayTimeWindow dayTimeWindow = null;

    public PrintDetailsWindow(CommandsPanelUser commandsPanelUser)
    {
        dayTimeWindow = new DayTimeWindow(new printDetailsDayTimeListener(this));
        dayTimeWindow.setVisible(true);

        this.commandsPanelUser = commandsPanelUser;
    }

    private void fetchDayAndTimeAndExecutePrintDetails()
    {
        commandsPanelUser.executePrintDetailsCommand(dayTimeWindow.getDay(), dayTimeWindow.getTime());
    }

    public static class printDetailsDayTimeListener implements ActionListener
    {
        PrintDetailsWindow window = null;
        public printDetailsDayTimeListener(PrintDetailsWindow window)
        {
            this.window = window;
        }
        @Override
        public void actionPerformed(ActionEvent e)
        {
            window.fetchDayAndTimeAndExecutePrintDetails();
        }
    }
}
