package am.aua.hw.ui;

import am.aua.hw.core.Days;
import am.aua.hw.core.Times;

public interface CommandsPanelUser
{
    void executeAddCommand(String serializedParameters,  Days day, Times time);
    void executeRemoveCommand(Days day, Times time);
    void executePrintDetailsCommand(Days day, Times time);
    void executeLoadCommand(String path);
    void executeSaveCommand(String path);
    void executeQuitCommand();
}
