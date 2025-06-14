public class Player {
    private String name;
    private String club;
    private String sugPos;
    private String nationality;
    private int age;
    private int atk;
    private int def;
    private int mid;
    private int ovr;
    private int gk;
    public Player()
    {
        club = "";
        sugPos = "";
        nationality = "";
        age = 0;
        atk = 0;
        def = 0;
        mid = 0;
        ovr = 0;
        gk = 0;
    }
    public Player (String c, String n, String sug, int ag, String nat, int a, int m, int d, int g, int o)
    {
        club = c;
        name = n;
        sugPos = sug.toUpperCase();
        nationality = nat;
        age = ag;
        atk = a;
        def = d;
        mid = m;
        ovr = o;
        gk = g;
    }

    // SETTER METHODS   SETTER METHODS   SETTER METHODS   SETTER METHODS
    private void setClub(String c)
    {
        club = c;
    }

    private void setName(String n)
    {
        name = n;
    }

    private void setSugPos(String s)
    {
        sugPos = s;
    }

    private void setNationality(String n)
    {
        nationality = n;
    }

    private void setAge(int a)
    {
        age = a;
    }

    private void setAtk(int a)
    {
        atk = a;
    }

    public String print()
    {
        return "[Club: " + club + ", Name: " + name + ", Position: " + sugPos + ", Age: " + age + ", Nationality: " + nationality + ", ATK: " + atk + ", DEF: " + def + ", MID: " + mid + ", GK: " + gk + ", OVR: " + ovr + "]";
    }

    private void setDef(int d)
    {
        def = d;
    }
    private void setMid(int m)
    {
        mid = m;
    }
    private void setOvr(int o)
    {
        ovr = o;
    }
    private  void setGk(int g)
    {
        gk = g;
    }

    // GETTER METHODS   GETTER METHODS   GETTER METHODS   GETTER METHODS
    public  String getClub()
    {
        return club;
    }
    public  String getName()
    {
        return name;
    }
    public  String getSugPos()
    {
        return sugPos;
    }
    public  String getNationality()
    {
        return nationality;
    }
    public  int getAge()
    {
        return age;
    }
    public  int getAtk()
    {
        return atk;
    }
    public  int getDef()
    {
        return def;
    }
    public  int getMid()
    {
        return mid;
    }
    public  int getOvr()
    {
        return ovr;
    }
    public int getGk()
    {
        return gk;
    }
}
