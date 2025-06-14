import java.util.ArrayList;

public class Team {
    private ArrayList<Player> players = new ArrayList<Player>();
    private String teamName;
    private Player gk;
    private Player def1;
    private Player def2;
    private Player def3;
    private Player def4;
    private Player mid1;
    private Player mid2;
    private Player mid3;
    private Player atk1;
    private Player atk2;
    private Player atk3;
    private int defovr;
    private int midovr;
    private int atkovr;
    private int gkovr;
    private double xg;

    public Team(String n) {
        teamName = n;
    }

    public void setXG(double xg) {
        this.xg = xg;
    }
    public double getXG()
    {
        return xg;
    }

    public int calcDefOVR() {
        defovr = (def1.getDef() + def2.getDef() + def3.getDef() + def4.getDef());
        return defovr;
    }

    public int calcMidOVR() {
        midovr = (mid1.getMid() + mid2.getMid() + mid3.getMid());
        return midovr;
    }

    public int calcAtkOVR() {
        atkovr = (atk1.getAtk() + atk2.getAtk() + atk3.getAtk());
        return atkovr;
    }

    public int calcGkOVR() {
        gkovr = gk.getGk();
        return gkovr;
    }



    public Team(String n, Player g, Player d1, Player d2, Player d3, Player d4, Player m1, Player m2, Player m3, Player a1, Player a2, Player a3) {
        teamName = n;
        gk = g;
        def1 = d1;
        def2 = d2;
        def3 = d3;
        def4 = d4;
        mid1 = m1;
        mid2 = m2;
        mid3 = m3;
        atk1 = a1;
        atk2 = a2;
        atk3 = a3;
        players.add(gk);
        players.add(def1);
        players.add(def2);
        players.add(def3);
        players.add(def4);
        players.add(mid1);
        players.add(mid2);
        players.add(mid3);
        players.add(atk1);
        players.add(atk2);
        players.add(atk3);
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public String getName() {
        return teamName;
    }

    public void print() {
        for (Player p : players) {
            System.out.println("Name: " + p.getName() + ", OVR: " + p.getOvr());
        }
    }
    private void updatePlayerList() {
        players.clear();
        if (gk != null)
        {
            players.add(gk);
        }
        if (def1 != null)
        {
            players.add(def1);
        }
        if (def2 != null)
        {
            players.add(def2);
        }
        if (def3 != null)
        {
            players.add(def3);
        }
        if (def4 != null)
        {
            players.add(def4);
        }
        if (mid1 != null)
        {
            players.add(mid1);
        }
        if (mid2 != null)
        {
            players.add(mid2);
        }
        if (mid3 != null)
        {
            players.add(mid3);
        }
        if (atk1 != null)
        {
            players.add(atk1);
        }
        if (atk2 != null)
        {
            players.add(atk2);
        }
        if (atk3 != null)
        {
            players.add(atk3);
        }
    }

    public String removePlayer(String name, int ovr)
    {
        for (int i = 0; i < players.size(); i++)
        {
            Player p = players.get(i);
            System.out.println("Checking: " + p.getName() + " (" + p.getOvr() + ") vs " + name + " (" + ovr + ")");

            if (p.getName().equalsIgnoreCase(name.trim()) && p.getOvr() == ovr)
            {
                if (p == gk)
                {
                    gk = null;
                }
                else if (p == def1)
                {
                    def1 = null;
                }
                else if (p == def2)
                {
                    def2 = null;
                }
                else if (p == def3)
                {
                    def3 = null;
                }
                else if (p == def4)
                {
                    def4 = null;
                }
                else if (p == mid1)
                {
                    mid1 = null;
                }
                else if (p == mid2)
                {
                    mid2 = null;
                }
                else if (p == mid3)
                {
                    mid3 = null;
                }
                else if (p == atk1)
                {
                    atk1 = null;
                }
                else if (p == atk2)
                {
                    atk2 = null;
                }
                else if (p == atk3)
                {
                    atk3 = null;
                }

                updatePlayerList();
                return "Player Found and Removed";
            }
        }

        return "No Player Found; \nCheck OVR and Name";
    }


    public String addPlayer(Player p, String pos)
    {
        if (pos.equalsIgnoreCase("DEF"))
        {
            if (def1 == null)
            {
                def1 = p;
            }
            else if (def2 == null)
            {
                def2 = p;
            }
            else if (def3 == null)
            {
                def3 = p;
            }
            else if (def4 == null)
            {
                def4 = p;
            }
            else
            {
                return "All DEF positions are filled";
            }

            updatePlayerList();
            return "Player Added";
        }

        if (pos.equalsIgnoreCase("MID"))
        {
            if (mid1 == null)
            {
                mid1 = p;
            }
            else if (mid2 == null)
            {
                mid2 = p;
            }
            else if (mid3 == null)
            {
                mid3 = p;
            }
            else
            {
                return "All MID positions are filled";
            }

            updatePlayerList();
            return "Player Added";
        }

        if (pos.equalsIgnoreCase("ATK"))
        {
            if (atk1 == null)
            {
                atk1 = p;
            }
            else if (atk2 == null)
            {
                atk2 = p;
            }
            else if (atk3 == null)
            {
                atk3 = p;
            }
            else
            {
                return "All ATK positions are filled";
            }

            updatePlayerList();
            return "Player Added";
        }

        if (pos.equalsIgnoreCase("GK"))
        {
            if (gk == null)
            {
                gk = p;
                updatePlayerList();
                return "Player Added";
            }
            else
            {
                System.out.println(gk);
                return "Can't add GK. Reason- Slot Filled.";
            }
        }

        return "Unable to add player";
    }

    public boolean isFull() {
        return (gk != null) && (def1 != null) && (def2 != null) && (def3 != null) && (def4 != null) && (mid1 != null) && (mid2 != null) && (mid3 != null) && (atk1 != null) && (atk2 != null) && (atk3 != null);
    }
}
