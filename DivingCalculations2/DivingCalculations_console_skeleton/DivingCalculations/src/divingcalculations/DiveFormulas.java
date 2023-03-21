package divingcalculations;

import java.text.DecimalFormat;
import java.util.Arrays;

/**
 * DiveFormulas Class includes all the calculation methods that
 * help user to calculate MOD,SMOD,BM,PP and EAD.
 * It also provides method to generate EAD table and PP table.
 *
 * @QingyunChen
 * @9/21/2022
 */
public class DiveFormulas {

    public double calculateMOD(double pg, double oxy) {
        double fg = oxy / 100.0;
        double depth_ata = pg / fg;
        int depth_meter = (int) ((depth_ata - 1) * 10);
        return depth_meter;
    }

    public double calculateSMOD(double oxy) {
        double fg = oxy / 100.0;
        double depth_ata = 1.4 / fg;
        int depth_meter = (int) ((depth_ata - 1) * 10);
        return depth_meter;
    }

    public int calculateBM(double pg_ata, int depth) {
        double depth_ata;
        depth_ata = depth / 10.0 + 1;
        return (int) ((pg_ata / depth_ata) * 100);
    }

    public double calculatePP(int depth, double oxy) {
        double fg = oxy / 100.0;
        double depth_ata = depth / 10.0 + 1;
        DecimalFormat df = new DecimalFormat("0.00");
        double pg = fg * depth_ata;
        return Double.parseDouble(df.format(pg));
    }

    public double calculateEAD(double depth_meter, double oxy) {
        double fg = oxy / 100.0;
        double depth_ata = depth_meter / 10.0 + 1;
        DecimalFormat df = new DecimalFormat("0.00");
        double ead_ata = ((1 - fg) * depth_ata) / 0.79;
        ead_ata = Double.parseDouble(df.format(ead_ata));
        int ead_meter = (int) Math.round((ead_ata - 1) * 10);
        return ead_meter;
    }


    public void print_PPTable(int start_oxy, int end_oxy, int start_depth, int end_depth) {
        String oxy_line = "\\\t\t";
        String line = "________";
        for (int i = start_oxy; i <= end_oxy; i++) {
            oxy_line += i + "\t\t";
            line += "________";
        }
        System.out.println(oxy_line);
        System.out.println(line);
        for (int i = start_depth; i < end_depth; i += 3) {
            String ppLine = i + "\t\t";
            for (int j = start_oxy; j <= end_oxy; j++) {
                double pp = calculatePP(i, j);
                if (pp <= 1.6) {
                    ppLine += String.format("%.2f", pp) + "\t";
                } else {
                    ppLine += "\t";
                }
            }
            System.out.println(ppLine);
        }
    }

    public void print_EadtTable(int start_oxy, int end_oxy, int start_depth, int end_depth) {
        String oxy_line = "\\\t";
        String line = "________";
        for (int i = start_oxy; i <= end_oxy; i++) {
            oxy_line += i + "\t";
            line += "________";
        }
        System.out.println(oxy_line);
        System.out.println(line);
        for (int i = start_depth; i < end_depth; i += 3) {
            String eadLine = i + "\t";
            for (int j = start_oxy; j <= end_oxy; j++) {
                int ead = (int) calculateEAD(i, j);
                eadLine += ead + "\t";
            }
            System.out.println(eadLine);
        }
    }
}
