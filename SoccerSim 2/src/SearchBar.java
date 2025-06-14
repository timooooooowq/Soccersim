import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class SearchBar extends JFrame {
    private ArrayList<String> players = playerList();
    private JTextField searchBar;
    private JList<String> listView;
    private DefaultListModel<String> listModel;
    private Player selectedPlayer;
    private String selectedPlayerPosition;
    private PlayerSelectionListener listener;

    public interface PlayerSelectionListener {
        void onPlayerSelected(Player player);
    }

    public void setPlayerSelectionListener(PlayerSelectionListener listener)
    {
        this.listener = listener;
    }

    public SearchBar() {
        super("Player Search");
        setSize(1280, 680);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Color nice = new Color(8, 0, 63);

        searchBar = new JTextField();
        searchBar.setBackground(nice);
        searchBar.setForeground(Color.WHITE);
        searchBar.setFont(new Font("Arial", Font.PLAIN, 20));
        searchBar.setPreferredSize(new Dimension(searchBar.getPreferredSize().width, 40));
        searchBar.addActionListener(this::search);

        listModel = new DefaultListModel<>();
        listView = new JList<>(listModel);
        listView.setFixedCellHeight(30);
        listView.setBackground(nice);
        listView.setForeground(Color.WHITE);



        JScrollPane scrollPane = styledScrollPane(listView);

        JButton searchButton = new JButton("Search");
        JButton selectButton = new JButton("Select");
        JButton randomizeButton = new JButton("Randomize");

        searchButton.addActionListener(this::search);
        selectButton.addActionListener(this::select);
        randomizeButton.addActionListener(this::randomize);

        searchButton.setBackground(nice);
        selectButton.setBackground(nice);
        randomizeButton.setBackground(nice);
        searchButton.setForeground(Color.WHITE);
        selectButton.setForeground(Color.WHITE);
        randomizeButton.setForeground(Color.WHITE);

        selectButton.setPreferredSize(new Dimension(635, 40));
        randomizeButton.setPreferredSize(new Dimension(635, 40));

        JTextField headerField = new JTextField("INFORMATION KEY: Club,Name,Position,Age,Nationality,ATK,MID,DEF,GK,OVR");
        headerField.setEditable(false);
        headerField.setForeground(Color.WHITE);
        headerField.setBackground(nice);
        headerField.setFont(new Font("Arial", Font.BOLD, 15));

        JPanel topPanel = new JPanel(new GridLayout(2, 1));
        topPanel.add(searchBar);
        topPanel.add(headerField);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.add(searchButton, BorderLayout.SOUTH);
        buttonPanel.add(selectButton, BorderLayout.EAST);
        buttonPanel.add(randomizeButton, BorderLayout.WEST);

        add(buttonPanel, BorderLayout.SOUTH);

        initialize(null, null);

        setVisible(true);
    }

    void search(ActionEvent actionEvent) {
        listModel.clear();
        for (String item : searchList(searchBar.getText(), players)) {
            listModel.addElement(item);
        }
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        for (String player : players) {
            listModel.addElement(player);
        }
    }

    private JScrollPane styledScrollPane(JList<String> list) {
        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(0, 122, 166);
                this.trackColor = new Color(15, 15, 45);
            }
        });
        return scrollPane;
    }


    public void randomize(ActionEvent actionEvent) {
        int itemCount = listModel.size();
        if (itemCount <= 1) {
            System.out.println("No players to randomize from.");
            return;
        }
        int randomIndex = (int) (Math.random() * itemCount);
        listView.setSelectedIndex(randomIndex);
        select(null);
        dispose();
    }

    public Player getSelectedPlayer()
    {
        return selectedPlayer;
    }

    public String getSelectedPlayerPosition()
    {
        return selectedPlayerPosition;
    }

    private void select(ActionEvent actionEvent) {
        String selectedItem = listView.getSelectedValue();
        if (selectedItem != null && !selectedItem.startsWith("Club,")) {
            try {
                int i = selectedItem.indexOf(",");
                String club = selectedItem.substring(0, i);
                selectedItem = selectedItem.substring(i + 1);

                i = selectedItem.indexOf(",");
                String name = selectedItem.substring(0, i);
                selectedItem = selectedItem.substring(i + 1);

                i = selectedItem.indexOf(",");
                String position = selectedItem.substring(0, i);
                selectedItem = selectedItem.substring(i + 1);

                i = selectedItem.indexOf(",");
                int age = Integer.parseInt(selectedItem.substring(0, i));
                selectedItem = selectedItem.substring(i + 1);

                i = selectedItem.indexOf(",");
                String nationality = selectedItem.substring(0, i);
                selectedItem = selectedItem.substring(i + 1);

                i = selectedItem.indexOf(",");
                int atk = Integer.parseInt(selectedItem.substring(0, i));
                selectedItem = selectedItem.substring(i + 1);

                i = selectedItem.indexOf(",");
                int mid = Integer.parseInt(selectedItem.substring(0, i));
                selectedItem = selectedItem.substring(i + 1);

                i = selectedItem.indexOf(",");
                int def = Integer.parseInt(selectedItem.substring(0, i));
                selectedItem = selectedItem.substring(i + 1);

                i = selectedItem.indexOf(",");
                int gk = Integer.parseInt(selectedItem.substring(0, i));
                selectedItem = selectedItem.substring(i + 1);

                int ovr = Integer.parseInt(selectedItem.trim());

                Player player = new Player(club, name, position, age, nationality, atk, mid, def, gk, ovr);
                selectedPlayer = player;

                if (listener != null) {
                    listener.onPlayerSelected(player);
                }

                dispose();
            } catch (Exception e) {
                System.err.println("Error parsing player: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("No one selected");
        }
    }


    public Player getPlayer(int index)
    {
        String selectedItem = listView.getModel().getElementAt(index);
        try {
            int i = selectedItem.indexOf(",");
            String club = selectedItem.substring(0, i);
            selectedItem = selectedItem.substring(i + 1);

            i = selectedItem.indexOf(",");
            String name = selectedItem.substring(0, i);
            selectedItem = selectedItem.substring(i + 1);

            i = selectedItem.indexOf(",");
            String position = selectedItem.substring(0, i);
            selectedItem = selectedItem.substring(i + 1);

            i = selectedItem.indexOf(",");
            int age = Integer.parseInt(selectedItem.substring(0, i));
            selectedItem = selectedItem.substring(i + 1);

            i = selectedItem.indexOf(",");
            String nationality = selectedItem.substring(0, i);
            selectedItem = selectedItem.substring(i + 1);

            i = selectedItem.indexOf(",");
            int atk = Integer.parseInt(selectedItem.substring(0, i));
            selectedItem = selectedItem.substring(i + 1);

            i = selectedItem.indexOf(",");
            int mid = Integer.parseInt(selectedItem.substring(0, i));
            selectedItem = selectedItem.substring(i + 1);

            i = selectedItem.indexOf(",");
            int def = Integer.parseInt(selectedItem.substring(0, i));
            selectedItem = selectedItem.substring(i + 1);

            i = selectedItem.indexOf(",");
            int gk = Integer.parseInt(selectedItem.substring(0, i));
            selectedItem = selectedItem.substring(i + 1);

            int ovr = Integer.parseInt(selectedItem.trim());

            return new Player(club, name, position, age, nationality, atk, mid, def, gk, ovr);

        } catch (Exception e) {
            System.err.println("Failed to parse random player: " + e.getMessage());
            return null;
        }
    }

    public Player getRandomPlayer() {
        int quality = (int) (Math.random() * 5) + 1;
        int itemCount = 0;
        switch (quality)
        {
            case 1: itemCount = 100;
                break;
            case 2: itemCount = 500;
                break;
            case 3: itemCount = 1000;
                break;
            case 4: itemCount = 2000;
                break;
            case 5:; itemCount = 3000;
                break;
        }
        System.out.println(quality);
        if (itemCount <= 1) {
            System.out.println("Not enough players to select from.");
            return null;
        }

        int startIndex = listModel.getElementAt(0).startsWith("Club,") ? 1 : 0;
        int randomIndex = startIndex + (int) (Math.random() * (itemCount - startIndex));
        String selectedItem = listView.getModel().getElementAt(randomIndex);

        try {
            int i = selectedItem.indexOf(",");
            String club = selectedItem.substring(0, i);
            selectedItem = selectedItem.substring(i + 1);

            i = selectedItem.indexOf(",");
            String name = selectedItem.substring(0, i);
            selectedItem = selectedItem.substring(i + 1);

            i = selectedItem.indexOf(",");
            String position = selectedItem.substring(0, i);
            selectedItem = selectedItem.substring(i + 1);

            i = selectedItem.indexOf(",");
            int age = Integer.parseInt(selectedItem.substring(0, i));
            selectedItem = selectedItem.substring(i + 1);

            i = selectedItem.indexOf(",");
            String nationality = selectedItem.substring(0, i);
            selectedItem = selectedItem.substring(i + 1);

            i = selectedItem.indexOf(",");
            int atk = Integer.parseInt(selectedItem.substring(0, i));
            selectedItem = selectedItem.substring(i + 1);

            i = selectedItem.indexOf(",");
            int mid = Integer.parseInt(selectedItem.substring(0, i));
            selectedItem = selectedItem.substring(i + 1);

            i = selectedItem.indexOf(",");
            int def = Integer.parseInt(selectedItem.substring(0, i));
            selectedItem = selectedItem.substring(i + 1);

            i = selectedItem.indexOf(",");
            int gk = Integer.parseInt(selectedItem.substring(0, i));
            selectedItem = selectedItem.substring(i + 1);

            int ovr = Integer.parseInt(selectedItem.trim());

            return new Player(club, name, position, age, nationality, atk, mid, def, gk, ovr);

        } catch (Exception e) {
            System.err.println("Failed to parse random player: " + e.getMessage());
            return null;
        }
    }

    private List<String> searchList(String searchWords, List<String> listOfStrings) {
        List<String> searchWordsArray = Arrays.asList(searchWords.trim().toLowerCase().split("\\s+"));
        return listOfStrings.stream().filter(input -> {
            String lowerInput = input.toLowerCase();
            return searchWordsArray.stream().allMatch(lowerInput::contains);
        }).collect(Collectors.toList());
    }

    public static ArrayList<String> playerList() {
        ArrayList<String> players = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(SearchBar.class.getClassLoader().getResourceAsStream("players.csv")))) {
            String line;
            while ((line = reader.readLine()) != null) {
                players.add(line);
            }
        } catch (IOException | NullPointerException e) {
            System.err.println("Error reading file: " + e.getMessage());
            e.printStackTrace();
        }
        return players;
    }


    public static void main(String[] args)
    {
        new SearchBar();
    }
}
