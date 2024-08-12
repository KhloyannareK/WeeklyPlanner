package am.aua.hw.core;

import am.aua.hw.exceptions.MalformedStringParameterException;

public class Meeting extends WorkEvent implements Schedulable
{
    private final double latitude;
    private final double longitude;

    public Meeting(String title, double latitude, double longitude) throws MalformedStringParameterException
    {
        this.title = title;
        this.latitude = latitude;
        this.longitude = longitude;
        validateTitle();
    }

    public Meeting(String serializedParameters) throws MalformedStringParameterException
    {
        String[] tokens = serializedParameters.split(PARAMETER_DELIMITER);
        /// Validate tokens
        // tokens number
        if(tokens.length != 4) {
            throw new MalformedStringParameterException("Malformed parameter format for Meeting, tokens count is invalid: \"" + serializedParameters + "\"");
        }

        // type
        if(!"MEETING".equals(tokens[0].toUpperCase())) {
            throw new MalformedStringParameterException("Malformed parameter format for Meeting, TYPE token does not match: \"" + serializedParameters + "\"");
        }

        // title
        this.title = tokens[1];

        // latitude
        try {
            latitude = Double.parseDouble(tokens[2]);
        }
        catch (NumberFormatException e) {
            throw new MalformedStringParameterException("Malformed parameter format for Meeting, latitude is invalid \"" + tokens[2] + "\" is invalid.");
        }

        // longitude
        try {
            longitude = Double.parseDouble(tokens[3]);
        }
        catch (NumberFormatException e) {
            throw new MalformedStringParameterException("Malformed parameter format for Meeting, longitude is invalid \"" + tokens[3] + "\" is invalid.");
        }

        validateTitle();
    }

    public boolean equals(Object other)
    {
        if(other == null || other.getClass() != getClass()) {
            return false;
        }
        Meeting otherMeeting = (Meeting) other;
        return title.equals(otherMeeting.title)
                && latitude == otherMeeting.latitude
                && longitude == otherMeeting.longitude;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getFullDetails() {
        return "Meeting about " + title + " at location(latitude, longitude): " + latitude + "," + longitude;
    }

    @Override
    public String toSaveFileString() {
        return "MEETING" + PARAMETER_DELIMITER + title + PARAMETER_DELIMITER + latitude + PARAMETER_DELIMITER + longitude;
    }

    @Override
    public String getShortDescription() {
        return "About \"" + title + "\" at " + latitude + "," + longitude;
    }

    @Override
    public String getFullDescription() {
        return title + " will be discussed during the meeting.\n" +
                "Location(latitude/longitude): " + latitude + "/" + longitude;
    }
}
