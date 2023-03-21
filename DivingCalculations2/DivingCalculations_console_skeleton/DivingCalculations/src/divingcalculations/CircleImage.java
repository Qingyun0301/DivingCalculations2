package divingcalculations;

import javax.swing.*;
import java.awt.*;


/**
 * This Class is aim to draw the circle image
 * Reference for Graphics2D comes from this video:
 * https://www.youtube.com/watch?v=KcEvHq8Pqs0
 */
public class CircleImage extends JPanel {
    public CircleImage() {
        setPreferredSize(new Dimension(100, 100));

    }

    /**
     * Drawing the circle and lines by using paint method
     *
     * @param g the graphics context to use for painting
     */
    public void paint(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;

        // circle
        g2.setPaint(Color.ORANGE);
        g2.fillOval(0, 0, 200, 200);

        // lines
        g2.setPaint(Color.black);
        g2.setStroke(new BasicStroke(4));
        g2.drawLine(0, 100, 200, 100);
        g2.drawLine(100, 100, 100, 200);

        // strings on the circle
        g2.setFont(g2.getFont().deriveFont(15f));
        g2.drawString("Pg", 90, 50);
        g2.drawString("Fg", 50, 130);
        g2.drawString("D(m)", 130, 130);

    }
}
