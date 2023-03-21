package divingcalculations;

import javax.swing.*;

/*
 * Driver Class for the DivingCalculation interface
 *
 * @Qingyunchen
 */
public class DivingPanelDriver {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("Diving Calculation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        DivingPanel panel = new DivingPanel();
        frame.getContentPane().add(panel);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.pack();
    }
}

