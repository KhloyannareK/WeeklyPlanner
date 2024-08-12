package am.aua.hw.core;

import java.util.ArrayList;

import am.aua.hw.exceptions.MalformedStringParameterException;

public class VideoCall extends WorkEvent implements Schedulable, Cloneable
{
    private ArrayList<String> peerEmails;

    public VideoCall(String title, String peerEmail) throws MalformedStringParameterException
    {
        this.title = title;
        validateTitle();
        if( null == peerEmail || !peerEmail.contains("@") ) {
            throw new MalformedStringParameterException("Invalid email address: " + peerEmail + ".");
        }
        peerEmails = new ArrayList<>(1);
        peerEmails.add(peerEmail);
    }

    public VideoCall(String title, ArrayList<String> peerEmails) throws MalformedStringParameterException
    {
        this.title = title;
        validateTitle();
        if(peerEmails == null || peerEmails.isEmpty()) throw new NullPointerException("At least one email address must be specified.");
        this.peerEmails = new ArrayList<String>(peerEmails.size());
        for(String peerEmail : peerEmails) {
            if( null == peerEmail || !peerEmail.contains("@") ) {
                throw new MalformedStringParameterException("Invalid email address: " + peerEmail + ".");
            }
            this.peerEmails.add(peerEmail);
        }
    }

    public VideoCall(String serializedParameters) throws MalformedStringParameterException
    {
        String[] tokens = serializedParameters.split(PARAMETER_DELIMITER);
        if(tokens.length < 3) {
            throw new MalformedStringParameterException("Malformed parameter format for VideoCall, tokens count is invalid: \"" + serializedParameters + "\"");
        }
        if(!"VIDEOCALL".equals(tokens[0].toUpperCase())) {
            throw new MalformedStringParameterException("Malformed parameter format for VideoCall, TYPE token does not match: \"" + serializedParameters + "\"");
        }
        if(!tokens[2].contains("@")) {
            throw new MalformedStringParameterException("Malformed parameter format for VideoCall, Email \"" + tokens[2] + "\" is invalid.");
        }

        this.title = tokens[1];
        validateTitle();

        this.peerEmails = new ArrayList<String>(tokens.length - 2);
        for(int i = 2; i < tokens.length; ++i) {
            if( !tokens[i].contains("@") ) {
                throw new MalformedStringParameterException("Invalid email address: " + tokens[i] + ".");
            }
            this.peerEmails.add(tokens[i]);
        }
    }

    public boolean equals(Object other)
    {
        if(other == null || other.getClass() != getClass()) {
            return false;
        }
        VideoCall otherVideoCall = (VideoCall) other;

        return title.equals(otherVideoCall.title)
                && this.peerEmails.equals(otherVideoCall.peerEmails); // Let`s assume the order of emails is important
    }

    public void removeParticipant(String emailToRemove)
    {
        if(peerEmails.size() > 1) {
            peerEmails.remove(emailToRemove);
        }
    }

    @Override
    public String getTitle() { // why not to move to workEvent?
        return title;
    }

    @Override
    public String getFullDetails() {
        return "Video call with " + peerEmails.toString() + " topic: " + title;
    }

    @Override
    public String toSaveFileString() {
        String result = "VIDEOCALL" + PARAMETER_DELIMITER + title;
        for(String peerEmail : peerEmails) {
            result += PARAMETER_DELIMITER + peerEmail;
        }
        return result;
    }

    @Override
    public String getShortDescription() {
        return "Call about \"" + title + "\", " + peerEmails.getFirst() + "...";
    }

    @Override
    public String getFullDescription() {
        return title + " will be discussed during the video call.\n" +
                " Participants: " + peerEmails.toString();
    }

    public VideoCall clone()
    {
        try {
            VideoCall copy = (VideoCall) super.clone();
            copy.peerEmails = (ArrayList<String>)peerEmails.clone();
            return copy;
        }
        catch (CloneNotSupportedException e) {
            System.out.println("Could not clone VideoCall.");
            return null;
        }
    }
}
