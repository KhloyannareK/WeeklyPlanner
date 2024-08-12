package am.aua.hw.cli;

import am.aua.hw.core.*;
import am.aua.hw.exceptions.MalformedStringParameterException;
import am.aua.hw.exceptions.WorkWeekException;
import am.aua.hw.utils.FileUtil;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.Time;
import java.util.Scanner;

public class SchedulerConsole
{
    private static String DEFAULT_SCHEDULE_FILE_PATH = "WorkweekSchedule.txt";

    public static void run()
    {
        Scanner keyboard = new Scanner(System.in);
        WorkWeek workWeek = new WorkWeek();
        whileCommandLabel:
        while(true)
        {
            promptCommandList();
            workWeek.printWorkWeek();
            char commandChar = keyboard.next().charAt(0);
            Command command = parseCommand(commandChar);
            switchCommandLabel:
            switch(command)
            {
                case ADD_EVENT:
                {
                    System.out.println("Event type(VideoCall/Meeting/HorseTennis/PonyPingPong): ");
                    String typeStr = keyboard.next();
                    System.out.println("Event title: ");
                    String title = keyboard.next();
                    Days day = Days.MONDAY;
                    Times time = Times.MORNING;
                    Schedulable newEvent = null;
                    switch(typeStr)
                    {
                        case "VideoCall":
                        {
                            System.out.println("Peer email address: ");
                            String email = keyboard.next();

                            boolean succeed = promptAndParseDayTime(keyboard, day, time, "Ignoring add command");
                            if(!succeed) continue;

                            try {
                                newEvent = new VideoCall(title, email);
                            }
                            catch (MalformedStringParameterException e) {
                                System.out.println(e.getMessage());
                                newEvent = null;
                            }
                            break;
                        }
                        case "Meeting":
                        {
                            double latitude = 0.0, longitude = 0.0;

                            System.out.println("Meeting latitude: ");
                            String latitudeStr = keyboard.next();
                            try { latitude = Double.parseDouble(latitudeStr); }
                            catch (NumberFormatException e) {
                                System.out.println("Invalid latitude specified, ignoring add command.");
                                break;
                            }
                            System.out.println("Meeting longitude: ");
                            String longitudeStr = keyboard.next();
                            try { longitude = Double.parseDouble(latitudeStr); }
                            catch (NumberFormatException e) {
                                System.out.println("Invalid longitude specified, ignoring add command.");
                                break;
                            }

                            boolean succeed = promptAndParseDayTime(keyboard, day, time, "Ignoring add command");
                            if(!succeed) continue;

                            try {
                                newEvent = new Meeting(title, latitude, longitude);
                            }
                            catch (MalformedStringParameterException e) {
                                System.out.println(e.getMessage());
                                newEvent = null;
                            }
                            break;
                        }
                        case "HorseTennis":
                        {
                            // Do not want to implement more details about horse matches
                            // Currently want to just have work events fully supported
                            newEvent = new HorseTennis("dummyValue");
                            break;
                        }
                        case "PonyPingPong":
                        {
                            // Do not want to implement more details about horse matches
                            // Currently want to just have work events
                            newEvent = new HorseTennis.PonyPingPong("dummyValue");
                            break;
                        }
                        default:
                        {
                            System.out.println("Invalid event type, ignoring add command.");
                            break;
                        }
                    }

                    if(null != newEvent) {
                        try {
                            workWeek.addToSchedule(newEvent, day, time);
                        } catch (WorkWeekException e) {
                            System.out.println(e.getMessage());
                            System.out.println("Ignoring add command.");
                        }
                    }

                    break;
                }
                case REMOVE_EVENT:
                {
                    Days day = Days.MONDAY;
                    Times time = Times.MORNING;
                    boolean succeed = promptAndParseDayTime(keyboard, day, time, "Ignoring remove command");
                    if(!succeed) continue;

                    try {
                        workWeek.removeFromSchedule(day, time);
                    }
                    catch (WorkWeekException e) {
                        System.out.println(e.getMessage());
                        System.out.println("Ignoring remove command.");
                    }

                    break;
                }
                case PRINT_DETAILS:
                {
                    Days day = Days.MONDAY;
                    Times time = Times.MORNING;
                    boolean succeed = promptAndParseDayTime(keyboard, day, time, "Ignoring print details command"); // AMIGO day/time are not passsed by ref
                    if(!succeed) continue;

                    if(workWeek.isEmpty(day, time)) {
                        System.out.println("No event scheduled for " + day.toString() + "," + time.toString());
                    }
                    else {
                        String details = workWeek.getFullDetailsAtUnchecked(day,time);
                        System.out.println(details);
                    }

                    break;
                }
                case LOAD_FROM_FILE:
                {
                    System.out.println("Loading schedule from file. Do you wish to use the following path(Y/N)?: " + DEFAULT_SCHEDULE_FILE_PATH);
                    String answer = keyboard.next();
                    if(answer.length() != 1
                            || Character.toUpperCase(answer.charAt(0)) != 'Y'
                            || Character.toUpperCase(answer.charAt(0)) != 'N')
                    {
                        System.out.println("Invalid answer, ignoring load from file command.");
                        break;
                    }
                    String path = DEFAULT_SCHEDULE_FILE_PATH;
                    String[] serializedEvents = {};
                    boolean isFirstIteration = true;
                    while(true) {
                        if(isFirstIteration || Character.toUpperCase(answer.charAt(0)) == 'N') {
                            System.out.println("Path:");
                            path = keyboard.next();
                            isFirstIteration = false;
                        }

                        try {
                            serializedEvents = FileUtil.loadStringsFromFile(path);
                            break;
                        }
                        catch (IOException e) {
                            System.out.println(e.getMessage());
                        }
                        finally {
                            System.out.println("Would you like to ignore load from file command? (Y/N): ");
                            answer = keyboard.next();
                            if(answer.length() != 1
                                    || Character.toUpperCase(answer.charAt(0)) != 'Y'
                                    || Character.toUpperCase(answer.charAt(0)) != 'N')
                            {
                                System.out.println("Invalid answer, ignoring load from file command.");
                                break switchCommandLabel;
                            }
                        }
                    }
                    try {
                        workWeek = WorkWeek.generateWorkweekFromStrings(serializedEvents);
                        break whileCommandLabel;
                    }
                    catch (WorkWeekException | MalformedStringParameterException e) {
                        System.out.println(e.getMessage());
                        System.out.println("Ignoring load from file command.");
                    }

                    break;
                }
                case SAVE_TO_FILE:
                {
                    System.out.println("Saving schedule to file. Do you wish to use the following path(Y/N)?: " + DEFAULT_SCHEDULE_FILE_PATH);
                    String answer = keyboard.next();
                    if(answer.length() != 1
                            || Character.toUpperCase(answer.charAt(0)) != 'Y'
                            || Character.toUpperCase(answer.charAt(0)) != 'N')
                    {
                        System.out.println("Invalid answer, ignoring save file command.");
                        break;
                    }
                    String[] serializedEvents = new String[WorkWeek.NUMBER_OF_TIME_SLOTS * WorkWeek.NUMBER_OF_DAYS];
                    for(int i = 0; i < WorkWeek.NUMBER_OF_TIME_SLOTS * WorkWeek.NUMBER_OF_DAYS; ++i) {
                        Days day = Days.toDays(i % WorkWeek.NUMBER_OF_DAYS);
                        Times time = Times.toTimes(i / WorkWeek.NUMBER_OF_DAYS);
                        serializedEvents[i] = workWeek.getToSaveFileStringAt(day, time);
                    }
                    String path = DEFAULT_SCHEDULE_FILE_PATH;
                    boolean isFirstIteration = true;
                    while(true) {
                        if(!isFirstIteration || Character.toUpperCase(answer.charAt(0)) == 'N') {
                            System.out.println("Path:");
                            path = keyboard.next();
                            isFirstIteration = false;
                        }

                        try {
                            FileUtil.saveStringsToFile(serializedEvents, path);
                            break;
                        }
                        catch (IOException e) {
                            System.out.println(e.getMessage());
                        }
                        finally {
                            System.out.println("Would you like to ignore save to file command? (Y/N): ");
                            answer = keyboard.next();
                            if(answer.length() != 1
                                    || Character.toUpperCase(answer.charAt(0)) != 'Y'
                                    || Character.toUpperCase(answer.charAt(0)) != 'N')
                            {
                                System.out.println("Invalid answer, ignoring save to file command.");
                                break switchCommandLabel;
                            }
                        }
                    }

                    break;
                }
                case QUIT:
                {
                    System.out.println("Exiting...");
                    System.exit(0);
                    break;
                }
                case INVALID:
                {
                    System.out.println("Invalid command '" + commandChar + "', ignored.");
                    break;
                }
            }
        }
    }

    private static Command parseCommand(char command)
    {
        switch(command)
        {
            case '1', 'a', 'A': return Command.ADD_EVENT;
            case '2', 'r', 'R': return Command.REMOVE_EVENT;
            case '3', 'p', 'P': return Command.PRINT_DETAILS;
            case '4', 'l', 'L': return Command.LOAD_FROM_FILE;
            case '5', 's', 'S': return Command.SAVE_TO_FILE;
            case '6', 'q', 'Q': return Command.QUIT;
            default: return Command.INVALID;
        }
    }

    private static void promptCommandList()
    {
        System.out.println("> Perform action by number or letter in ()");
        System.out.print("1. (A) Add an event.\n" +
                         "2. (R) Remove an event.\n" +
                         "3. (P) Print details.\n" +
                         "4. (L) Load schedule from file.\n" +
                         "5. (S) Save schedule to file.\n" +
                         "6. (Q) Quit.");
    }

    private static boolean promptAndParseDayTime(Scanner scanner, Days outDay, Times outTime, String errorMessage)
    {
        try {
            System.out.println("Day: ");
            String dayStr = scanner.next();
            outDay = Days.valueOf(dayStr.toUpperCase());
        }
        catch(IllegalArgumentException e)
        {
            System.out.println("Invalid day, ignoring add command.");
            return false;
        }

        try {
            System.out.println("Time: ");
            String timeStr = scanner.next();
            outTime = Times.valueOf(timeStr.toUpperCase());
        }
        catch(IllegalArgumentException e)
        {
            System.out.println("Invalid time." + errorMessage + '.');
            return false;
        }
        return true;
    }

    public enum Command
    {
        ADD_EVENT,
        REMOVE_EVENT,
        PRINT_DETAILS,
        LOAD_FROM_FILE,
        SAVE_TO_FILE,
        QUIT,
        INVALID
    }
}
