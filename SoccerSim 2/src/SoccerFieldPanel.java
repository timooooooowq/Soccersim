import javax.swing.*;
import java.awt.*;

class SoccerFieldPanel extends JPanel {
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(3));

        int width = getWidth();
        int height = getHeight();

        int boxWidth = width / 5;
        int boxHeight = height / 8;
        g2.drawRect((width - boxWidth) / 2, height - boxHeight, boxWidth, boxHeight);


    }
}

