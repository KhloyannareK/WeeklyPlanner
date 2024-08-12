package am.aua.hw;

import am.aua.hw.cli.SchedulerConsole;
import am.aua.hw.ui.MainGUI;

public class Main
{
    public static void main(String[] args)
    {
        if(args.length > 1) {
            System.err.println("Usage: MainGUI [(-ui) | -cli]");
            System.exit(1);
        }
        if(args.length == 0 || args[0].equals("-ui")) {
            MainGUI.run();
        } else if (args[0].equals("-cli")){
            SchedulerConsole.run();
        }
    }
}
