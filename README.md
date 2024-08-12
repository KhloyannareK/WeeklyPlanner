## WeeklyPlanner
A weekly planner application with several type of events possible to schedule during working days at the morning or afternoon.
Supports UI mode as well as shell mode. It is written in Java using SWING library.

## Goal
The project was made for fun and serves as a practise during learning Java.

## R&D details
The source consists of several packages (cli, core, exceptions, ui, utils)
# core
"core" packages includes all the event type class hierarchy and some enum utility classes.
The event types that may be scheduled are all derived classes of WorkEvent, namely VideoCall, Meeting, HorseTennis, PonyPingPong, all implement
"Schedulable" interface.
WorkEvent event object has title, full and short description about the event, short description is used to show event on the Schedule.
Each work event type has own additional parameters, VideoCall has peer email addresses for example.
Each event has function to serialize its data or be constructed from a serialized string.
Additionally, the package has WorkWeek class for representing a week with the events added to the workweek.
# exceptions
"exceptions" package is used to introduce exception classes.
# utils
"utils" package will be used by different modules to keep utility classes/methods. 
FileUtils, for example, is used to write strings to file or read them from file.
# cli and ui
Possible actions by the user are:
Add event, remove event, print event details(the full description), load schedule from file, save schedule to file, quit.
User may perform these actions either in console mode(-cli) or in ui mode(-ui) which is the default mode.
"cli" package implements the console mode. The class for the console mode is SchedularConsole.
Infinite loop is written and in each iteration user is requested to input its command and corressponding paraeters.
In each iteration the schedule and command list is printed to the standart output.
"ui" package consists of the mainGUI class with a method to run ui mode. The GUI consists of the MainWindow with three pannels, Heading panel(label),
Command list(buttons) and Schedule(the weekly calendar). for each type of command there is separate window(except for quit).

## good code?
The core part is written with good coding practise and attention to details. however, The structure not the best, but is not bad as well.
The ui part was written at the end, the start was good, but after I just wanted to finalize the project and some details may be left
(like refactoring the code, issuing an error/warning window to the user when such cases occure, etc.) 

