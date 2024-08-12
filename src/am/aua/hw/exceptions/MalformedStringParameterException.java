package am.aua.hw.exceptions;

public class MalformedStringParameterException extends Exception
{
    public MalformedStringParameterException()
    {
        super("Invalid string parameter");
    }

    public MalformedStringParameterException(String message)
    {
        super(message);
    }
}
