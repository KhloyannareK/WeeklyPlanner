package am.aua.hw.core;

import am.aua.hw.exceptions.MalformedStringParameterException;
import am.aua.hw.exceptions.WorkWeekException;

import java.sql.Time;
import java.util.Arrays;

public class WorkWeek implements Comparable<WorkWeek>
{
    public static final int NUMBER_OF_DAYS = 5;
    public static final int NUMBER_OF_TIME_SLOTS = 2;
    private final Schedulable[][] workWeek; // null event means empty slot

    public WorkWeek()
    {
        workWeek = new Schedulable[NUMBER_OF_TIME_SLOTS][NUMBER_OF_DAYS];
    }

    public void addToSchedule(Schedulable event, Days day, Times time) throws WorkWeekException
    {
        if(!isEmpty(day, time)) {
            throw new WorkWeekException("Adding event failed as thw slot " + day.toString() + ',' + time.toString() + " is busy.");
        }
        if(event.getClass() == VideoCall.class) {
            // we need deep copy as after adding removeParticipant to Videocall, it became mutable
            workWeek[time.ordinal()][day.ordinal()] = ((VideoCall)event).clone();
        }
        else {
            workWeek[time.ordinal()][day.ordinal()] = event;
        }
    }

    public void removeFromSchedule(Days day, Times time) throws WorkWeekException
    {
        if(isEmpty(day, time)) {
            throw new WorkWeekException("Removing event failed aw the slot " + day.toString() + ',' + time.toString() + " is empty.");
        }

        workWeek[time.ordinal()][day.ordinal()] = null;
    }

    public boolean isEmpty(Days day, Times time)
    {
        return workWeek[time.ordinal()][day.ordinal()] == null;
    }

    public String getToSaveFileStringAt(Days day, Times time)
    {
        if(isEmpty(day, time)) {
            return "TYPE=NULL"; // AMIGO add to documentation and report as well
        }

        // Do not know why we need to hold Schedulable variables instead of WorkEvent,
        // as there will be need of cast in cases when the interface does not contain the functions in the abstract class.
        // Or the interface should not be limited only to those two methods and all methods from the WorkEvent be moved to the interface.
        // Just doing what stated in the exercise(maybe exercise assumes to do more, but not sure).
        return ((WorkEvent)workWeek[time.ordinal()][day.ordinal()]).toSaveFileString();
    }

    public String getTitleAt(Days day, Times time) throws WorkWeekException
    {
        if(isEmpty(day, time)) {
            throw new WorkWeekException("Retrieving event title failed aw the slot " + day.toString() + ',' + time.toString() + " is empty.");
        }

        return ((WorkEvent)workWeek[time.ordinal()][day.ordinal()]).getTitle();
    }

    public String getTitleAtUnchecked(Days day, Times time)
    {
        if(isEmpty(day,time)) {
            return "";
        }

        return ((WorkEvent)workWeek[time.ordinal()][day.ordinal()]).getTitle();
    }

    public String getFullDetailsAtUnchecked(Days day, Times time)
    {
        if(isEmpty(day, time)) {
            return null;
        }
        return ((WorkEvent)workWeek[time.ordinal()][day.ordinal()]).getFullDetails();
    }

    public String getFullDetailsAt(Days day, Times time) throws WorkWeekException
    {
        if(isEmpty(day, time)) {
            throw new WorkWeekException("Retrieving event details failed aw the slot " + day.toString() + ',' + time.toString() + " is empty.");
        }

        return ((WorkEvent)workWeek[time.ordinal()][day.ordinal()]).getFullDetails();
    }

    public static WorkWeek generateWorkweekFromStrings(String[] serializedEvents) throws WorkWeekException, MalformedStringParameterException
    {
        // AMIGO add to documented comment that the string TYPE=NULL means null event
        if(serializedEvents.length != NUMBER_OF_TIME_SLOTS * NUMBER_OF_DAYS) {
            throw new WorkWeekException("Invalid number of work week events(" + serializedEvents.length + ") is provided for work week construction.");
        }

        WorkWeek newWorkWeek = new WorkWeek();
        for(int eventIdx = 0; eventIdx < serializedEvents.length; ++eventIdx) {
            Schedulable event = createEventFromStringParameter(serializedEvents[eventIdx]);
            // AMIGO document this as weel to point out that the order is from Monday Morning to Friday Afternoon
            int row = eventIdx % NUMBER_OF_TIME_SLOTS;
            int col = eventIdx % NUMBER_OF_DAYS;
            if(event != null) {
                newWorkWeek.addToSchedule(event, Days.toDays(col), Times.toTimes(row));
            }
        }

        return newWorkWeek;
    }

    public static Schedulable createEventFromStringParameter(String serialzedString) throws MalformedStringParameterException
    {
        // validate string without tokenizing
        if(null == serialzedString || serialzedString.isEmpty()) {
            throw new MalformedStringParameterException("Event string parameter is empty.");
        }
        // tokenize and validate
        String[] tokens = serialzedString.split(WorkEvent.PARAMETER_DELIMITER);
        if(tokens.length == 0) {
            // may contain only delimiter
            throw new MalformedStringParameterException("Event string parameter(\"" + serialzedString + "\") contains only delimiter(s).");
        }

        // validate and create events
        Schedulable event = null;
        switch(tokens[0]) {
            case "VIDEOCALL":
            {
                event = new VideoCall(serialzedString);
                break;
            }
            case "MEETING":
            {
                event = new Meeting(serialzedString);
                break;
            }
            case "HORSETENNIS":
            {
                event = new HorseTennis("dummyParameter");
                break;
            }
            case "PONYPINGPONG":
            {
                event = new HorseTennis.PonyPingPong("dummyParameter");
                break;
            }
            case "TYPE=NULL":
            {
                break;
            }
            default:
            {
                throw new MalformedStringParameterException("Event string parameter contains unknown TYPE token(\"" + tokens[0] + "\").");
            }
        }
        return event;
    }

    public void printWorkWeek()
    {
        System.out.println(toString());
    }

    public String toString()
    {
        System.out.println("Work week schedule");
        final int TABULATION = 20; // todo: to format later
        String result = "";
        for(int i = 0; i < TABULATION; ++i) {
            result += " ";
        }
        for(int dayIdx = 0; dayIdx < NUMBER_OF_DAYS; ++dayIdx) {
            String dayStr = Days.toDays(dayIdx).toString();
            int spaceCount = TABULATION - dayStr.length();
            result += dayStr;
            for(int i = 0; i < spaceCount; ++i) {
                result += " ";
            }
        }
        result += '\n';
        for(int timeIdx = 0; timeIdx < NUMBER_OF_TIME_SLOTS; ++timeIdx) {
            Times time = Times.toTimes(timeIdx);
            String timeStr = time.toString();
            result += timeStr;
            for(int i = 0; i < TABULATION - timeStr.length(); ++i) {
                result += " ";
            }
            for(int dayIdx = 0; dayIdx < NUMBER_OF_DAYS; ++dayIdx) {
                Days day = Days.toDays(dayIdx);
                String title = getTitleAtUnchecked(day, time);
                if(title.isEmpty()) title = "EMPTY";
                result += title;
                int spaceCount = TABULATION - title.length();
                for(int i = 0; i < spaceCount; ++i) {
                    result += " ";
                }
            }
            result += '\n';
        }
        return result;
    }



    public boolean equals(Object other)
    {
        if(null == other || getClass() != other.getClass()) return false;
        if(this == other) return true;

        return workWeek == ((WorkWeek)other).workWeek;
    }

    public int workLoad()
    {
        int result = 0;
        for(int row = 0; row < NUMBER_OF_TIME_SLOTS; ++row) {
            for(int col = 0; col < NUMBER_OF_DAYS; ++col) {
                if(workWeek[row][col] != null) {
                    ++result;
                }
            }
        }
        return result;
    }

    @Override
    public int compareTo(WorkWeek other) {
        if(other == null) throw new NullPointerException();
        return workLoad() - other.workLoad();
    }

    public static WorkWeek getTheLeastBusyWorkweek(WorkWeek[] workWeeks)
    {
        // could just do in O(n), but exercise hints to practise Array.sort() and compareTo method - to also test the "compareTo"
        if(workWeeks == null) return null;
        Arrays.sort(workWeeks);
        return workWeeks[0];
    }

    // AMIGO add comments everywhere
    // AMIGO write the report

    // AMIGO to stude:
     // why interface but not abstract class
}
