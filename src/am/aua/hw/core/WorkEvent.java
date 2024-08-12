package am.aua.hw.core;

import am.aua.hw.exceptions.MalformedStringParameterException;

public abstract class WorkEvent implements Schedulable
{
    public static final String PARAMETER_DELIMITER = "%%";
    protected String title;

    public abstract String getTitle();
    public abstract String getFullDetails();
    public abstract String toSaveFileString();

    // this may become public and be in a child class or child classes in the future if needed
    protected void validateTitle() throws MalformedStringParameterException
    {
        if(title.contains(PARAMETER_DELIMITER))
            throw new MalformedStringParameterException(title + " contains delimiter(\"" + PARAMETER_DELIMITER + "\").");
    }

    @Override
    public abstract String getShortDescription();

    @Override
    public abstract String getFullDescription();
}
