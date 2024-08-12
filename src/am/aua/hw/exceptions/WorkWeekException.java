package am.aua.hw.exceptions;

public class WorkWeekException extends Exception
{
    public WorkWeekException()
    {
        super("Error during workweek processing");
    }

    public WorkWeekException(String message)
    {
        super(message);
    }
}
