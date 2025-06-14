import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class TeamSelection extends JFrame {

    private static final String[] POSITIONS = {"GK", "DEF", "DEF", "DEF", "DEF", "MID", "MID", "MID", "ATK", "ATK", "ATK"};
    private JButton[] positionButtons = new JButton[11];
    private JButton[] removeButtons = new JButton[11];
    private Team currentTeam;
    private static Team teamOneFinal;
    private static Team teamTwoFinal;
    private int teamCount;
    private String teamName;
    private Color nice = new Color(8, 0, 63);

    public TeamSelection() {
        this("Team 1", null);
    }

    public TeamSelection(String name, Team previousTeam) {
        super("Team Selection - " + name);
        this.teamName = name;
        this.currentTeam = new Team(name);
        this.teamCount = name.equals("Team 1") ? 1 : 2;

        setSize(1200, 800);
        setLayout(new BorderLayout());

        JPanel fieldPanel = new SoccerFieldPanel();
        fieldPanel.setLayout(new GridLayout(8, 5, 75, 0));
        Color field = new Color(79, 164, 79);
        fieldPanel.setBackground(field);

        int[][] positions =
        {
            {8, 3},
            {6, 1},
            {7, 2},
            {7, 4},
            {6, 5},
            {5, 3},
            {4, 2},
            {4, 4},
            {3, 1},
            {3, 5},
            {2, 3}
        };

        Map<String, Integer> positionMap = new HashMap<>();
        for (int i = 0; i < positions.length; i++) {
            positionMap.put(positions[i][0] + "-" + positions[i][1], i);
        }

        int i = 0;
        for (int row = 1; row <= 8; row++) {
            for (int col = 1; col <= 5; col++) {
                String key = row + "-" + col;
                if (positionMap.containsKey(key)) {
                    int index = positionMap.get(key);
                    JPanel playerPanel = new JPanel(new BorderLayout());

                    JButton positionButton = new JButton(POSITIONS[index]);
                    positionButton.setOpaque(true);
                    positionButton.setBorderPainted(false);
                    positionButton.setForeground(Color.WHITE);

                    JButton removeButton = new JButton("X");
                    removeButton.setOpaque(true);
                    removeButton.setBorderPainted(false);
                    removeButton.setPreferredSize(new Dimension(20, 20));
                    removeButton.setForeground(Color.BLACK);
                    removeButton.setVisible(false);

                    // Color groups
                    Color red = new Color(176, 1, 1);
                    Color blue = new Color(1, 30, 145);
                    Color green = new Color(166, 16, 208);
                    Color gold = new Color(209, 168, 2);

                    if (i <= 2) {
                        positionButton.setBackground(red);
                        removeButton.setBackground(red);
                    } else if (i <= 5) {
                        positionButton.setBackground(blue);
                        removeButton.setBackground(blue);
                    } else if (i <= 9) {
                        positionButton.setBackground(green);
                        removeButton.setBackground(green);
                    } else {
                        positionButton.setBackground(gold);
                        positionButton.setForeground(Color.BLACK);
                        removeButton.setBackground(gold);
                    }

                    int finalIndex = index;
                    positionButton.addActionListener(e -> openSearch(finalIndex));
                    removeButton.addActionListener(e -> removePlayer(finalIndex));

                    playerPanel.add(positionButton, BorderLayout.CENTER);
                    playerPanel.add(removeButton, BorderLayout.EAST);

                    fieldPanel.add(playerPanel);
                    positionButtons[index] = positionButton;
                    removeButtons[index] = removeButton;
                    i++;
                } else {
                    JPanel emptyCell = new JPanel();
                    emptyCell.setBackground(field);
                    fieldPanel.add(emptyCell);
                }
            }
        }

        fieldPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 20, true));
        add(fieldPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(nice);

        JButton finalizeButton = new JButton("Finalize");
        JButton randomizeButton = new JButton("Randomize");
        JButton kavin = new JButton("Penaldo");
        JButton vasist = new JButton("Messi");

        for (JButton btn : new JButton[]{finalizeButton, randomizeButton, kavin, vasist})
        {
            btn.setBackground(Color.LIGHT_GRAY);
            buttonPanel.add(btn);
        }

        finalizeButton.addActionListener(e -> finalizeTeam());
        randomizeButton.addActionListener(e -> randomizeTeam());
        kavin.addActionListener(e -> kavin());
        vasist.addActionListener(e -> vasist());

        add(buttonPanel, BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void openSearch(int index) {
        SearchBar searchBar = new SearchBar();
        searchBar.setPlayerSelectionListener(selectedPlayer -> {
            String position = POSITIONS[index];
            String result = currentTeam.addPlayer(selectedPlayer, position);
            if (result.contains("Player Added")) {
                positionButtons[index].setText(selectedPlayer.getName() + " (" + selectedPlayer.getOvr() + ")");
                removeButtons[index].setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, result);
            }
        });
        searchBar.setVisible(true);
    }

    private void removePlayer(int index) {
        String label = positionButtons[index].getText();
        if (!label.equals(POSITIONS[index])) {
            String name = label.substring(0, label.lastIndexOf(" ("));
            int ovr = Integer.parseInt(label.replaceAll("[^0-9]", ""));
            String result = currentTeam.removePlayer(name, ovr);
            positionButtons[index].setText(POSITIONS[index]);
            removeButtons[index].setVisible(false);
            JOptionPane.showMessageDialog(this, result);
        }
    }

    private void randomizeTeam() {
        SearchBar searchBar = new SearchBar();
        for (int i = 0; i < 11; i++) {
            String position = POSITIONS[i];
            boolean added = false;
            for (int attempts = 0; attempts < 20 && !added; attempts++) {
                Player p = searchBar.getRandomPlayer();
                if (p == null) continue;
                if (currentTeam.addPlayer(p, position).contains("Player Added")) {
                    positionButtons[i].setText(p.getName() + " (" + p.getOvr() + ")");
                    removeButtons[i].setVisible(true);
                    added = true;
                }
            }
            if (!added) {
                JOptionPane.showMessageDialog(this, "Could not fill position " + position);
            }
        }
        searchBar.dispose();
    }

    private void kavin() {
        fillPresetTeam(0);
    }

    private void vasist()
    {
        fillPresetTeam(1);
    }

    private void fillPresetTeam(int presetIndex) {
        SearchBar searchBar = new SearchBar();
        for (int i = 0; i < 11; i++) {
            String position = POSITIONS[i];
            boolean added = false;
            for (int attempts = 0; attempts < 20 && !added; attempts++) {
                Player p = searchBar.getPlayer(presetIndex);
                if (p == null) continue;
                if (currentTeam.addPlayer(p, position).contains("Player Added")) {
                    positionButtons[i].setText(p.getName() + " (" + p.getOvr() + ")");
                    removeButtons[i].setVisible(true);
                    added = true;
                }
            }
            if (!added) {
                JOptionPane.showMessageDialog(this, "Could not fill position " + position);
            }
        }
        searchBar.dispose();
    }

    private void finalizeTeam() {
        if (!currentTeam.isFull())
        {
            JOptionPane.showMessageDialog(this, "You must fill all 11 positions.");
            return;
        }

        JOptionPane.showMessageDialog(this, currentTeam.getName() + " finalized!");

        if (teamCount == 1) {
            teamOneFinal = currentTeam;
            dispose();
            new TeamSelection("Team 2", currentTeam);
        } else {
            teamTwoFinal = currentTeam;
            dispose();

            Match match = new Match(teamOneFinal, teamTwoFinal);
            int teamOneScore = match.calculateTeamOneScore();
            int teamTwoScore = match.calculateTeamTwoScore();

            JOptionPane.showMessageDialog(null,
                teamOneFinal.getName() + " |" + teamOneScore + " - " + teamTwoScore + "| " + teamTwoFinal.getName(),
                "Match Result", JOptionPane.INFORMATION_MESSAGE);

            JOptionPane.showMessageDialog(null, "AI MATCH ANALYSIS LOADING...\nPLEASE WAIT 25 SECONDS\nCLICK OK TO START DEEPSEEK ANALYSIS");

            DeepSeekApiTest deepSeek = new DeepSeekApiTest(teamOneFinal.getName(), teamOneScore, teamOneFinal.getPlayers(), teamTwoFinal.getName(), teamTwoScore, teamTwoFinal.getPlayers(), teamOneFinal.getXG(), teamTwoFinal.getXG());

            String analysis = deepSeek.getSummary();

            JTextPane textPane = new JTextPane();
            textPane.setContentType("text/plain");
            textPane.setText(analysis);
            textPane.setEditable(false);
            textPane.setCaretPosition(0);
            textPane.setBackground(nice);
            textPane.setForeground(Color.WHITE);

            JScrollPane scrollPane = new JScrollPane(textPane);
            scrollPane.setPreferredSize(new Dimension(600, 400));

            JDialog dialog = new JDialog(this, "AI Match Analysis", true);
            dialog.getContentPane().add(scrollPane);
            dialog.pack();
            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        new TeamSelection();
    }
}
