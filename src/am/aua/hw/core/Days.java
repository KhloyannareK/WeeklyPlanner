package am.aua.hw.core;

public enum Days
{
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY;

    static public Days toDays(int ordinal)
    {
        switch (ordinal)
        {
            case 0: return MONDAY;
            case 1: return TUESDAY;
            case 2: return WEDNESDAY;
            case 3: return THURSDAY;
            case 4: return FRIDAY;
            default: throw new IllegalArgumentException("Invalid ordinal: " + ordinal);
        }
    }
}
