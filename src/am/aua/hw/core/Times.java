package am.aua.hw.core;

public enum Times
{
    MORNING,
    AFTERNOON;

    static public Times toTimes(int ordinal)
    {
        switch (ordinal)
        {
            case 0: return MORNING;
            case 1: return AFTERNOON;
            default: throw new IllegalArgumentException("Invalid ordinal: " + ordinal);
        }
    }
}
