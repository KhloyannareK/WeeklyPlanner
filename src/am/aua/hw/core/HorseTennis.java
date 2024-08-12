package am.aua.hw.core;

// The problem I always extend WorkEvent for workweek is that there are some methods of Workevent used in WorkWeek not existing in the interface
// I would like to move those abstract methods to the interface, but am not sure if it is allowed or not.
public class HorseTennis extends WorkEvent /* otherwise I should add all the three abstract methods to the interface*/ implements Schedulable
{
    public static class Team
    {
        private String horseName;
        private String riderName;
        Team(String horseName, String riderName)
        {
            this.horseName = horseName;
            this.riderName = riderName;
        }
        public String getHorseName()
        {
            return horseName;
        }
        public String getRiderName()
        {
            return riderName;
        }
    }

    private static final Team dummyTeam = new Team("DUMMY", "DUMMY");
    private Team teamA;
    private Team teamB;

    public HorseTennis(Team teamA, Team teamB)
    {
        this.teamA = teamA;
        this.teamB = teamB;
    }

    public HorseTennis(String serializedParameters)
    {
        this.teamA = this.teamB = dummyTeam;
        this.title = "Sport match";
    }

    public Team getTeamA()
    {
        return teamA;
    }

    public Team getTeamB()
    {
        return teamB;
    }


    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getFullDetails() {
        return getTeamA().getHorseName();
    }

    @Override
    public String toSaveFileString() {
        return "HORSETENNIS" + PARAMETER_DELIMITER + title + PARAMETER_DELIMITER + "Whatever";
    }

    @Override
    public String getShortDescription() {
        return "Sport match";
    }

    @Override
    public String getFullDescription() {
        return "HorseTennis: " + teamA.getRiderName() + " - " + teamA.getHorseName() + " vs. " + teamB.getRiderName() + " - " + teamB.getHorseName();
    }

    public static class PonyPingPong extends WorkEvent implements Schedulable
    {
        private static final HorseTennis dummyHorseTennis = new HorseTennis(new Team("dummy", "dummy"), new Team("dummy", "dummy"));
        private final HorseTennis horseTennisMatch;

        public PonyPingPong(HorseTennis horseTennisMatch)
        {
            this.title = "Sport match";
            this.horseTennisMatch = horseTennisMatch;
        }

        public PonyPingPong(String serializedParameters)
        {
            horseTennisMatch = dummyHorseTennis;
            this.title = "Sport match";
        }

        @Override
        public String getTitle() {
            return title;
        }

        @Override
        public String getFullDetails() {
            return getFullDescription();
        }

        @Override
        public String toSaveFileString() {
            return "PONYPINGPONG" + PARAMETER_DELIMITER + title + PARAMETER_DELIMITER + "Whatever";
        }

        @Override
        public String getShortDescription() {
            return "Sport match";
        }

        @Override
        public String getFullDescription() {
            return "PonyPingPong: " + horseTennisMatch.teamA.getRiderName() + " - " + horseTennisMatch.teamA.getHorseName() +
                    " vs. " + horseTennisMatch.teamB.getRiderName() + " - " + horseTennisMatch.teamB.getHorseName();
        }

    }
}
