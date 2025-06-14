import java.util.Random;

public class Match {
    public Team team1;
    public Team team2;
    private int teamOneAtk;
    private int teamTwoAtk;
    private int teamOneMid;
    private int teamTwoMid;
    private int teamOneDef;
    private int teamTwoDef;
    private int teamOneGk;
    private int teamTwoGk;
    private double team1xg;
    private double team2xg;

    public Match(Team t1, Team t2) {
        team1 = t1;
        team2 = t2;
        values();
    }

    public double getTeam1xg()
    {
        return team1xg;
    }
    public double getTeam2xg()
    {
        return team2xg;
    }

    public void values() {
        teamOneAtk = team1.calcAtkOVR();
        teamOneDef = team1.calcDefOVR();
        teamOneMid = team1.calcMidOVR();
        teamOneGk = team1.calcGkOVR();
        teamTwoAtk = team2.calcAtkOVR();
        teamTwoDef = team2.calcDefOVR();
        teamTwoMid = team2.calcMidOVR();
        teamTwoGk = team2.calcGkOVR();
    }

    public int calculateTeamOneScore() {
        double raw = Math.pow((teamOneAtk + teamOneMid) / 2.0, 1.15);
        double def = Math.pow((teamTwoDef + teamTwoGk) / 2.0, 1.05);
        double xG = (raw / def) * 1.5;

        double stdDev = 1.0;
        Random rand = new Random();
        int value = (int) (xG + stdDev * rand.nextGaussian());
        if (value < 0) value = 0;

        System.out.println("Team One Score: " + value);
        System.out.println("xG: " + xG);
        team1.setXG(xG);
        return value;
    }

    public int calculateTeamTwoScore() {
        double raw = Math.pow((teamTwoAtk + teamTwoMid) / 2.0, 1.15);
        double def = Math.pow((teamOneDef + teamOneGk) / 2.0, 1.05);
        double xG = (raw / def) * 1.5;

        double stdDev = 1.0;
        Random rand = new Random();
        int value = (int) (xG + stdDev * rand.nextGaussian());
        if (value < 0) value = 0;

        System.out.println("Team Two Score: " + value);
        System.out.println("xG: " + xG);
        team2.setXG(xG);
        return value;
    }
}