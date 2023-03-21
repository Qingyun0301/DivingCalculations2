package divingcalculations;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;


/**
 * This Diving application helps users to do some calculations as well as generating tables.
 * Users can calculate different items through modifying the data of the application.
 * <p>
 * The Diving Panel includes four main panel < mainPanel1,mainPanel2,mainPanel3,mainPanel4>,
 * each mainPanel has several sub-panels.
 */
public class DivingPanel extends JPanel {
    private int CALC_WIDTH = 1070;
    private int CALC_HEIGHT = 720;
    private JRadioButton modButton, smodButton, bmButton, ppButton, eadButton, eadTableButton, ppTableButton;
    private JButton doCal, createTable;
    private ButtonGroup buttonGroup, tableButtonGroup;
    private JSpinner pgSpinner, fgSpinner, depthSpinner, startO2Spinner, endO2Spinner, startDepthSpinner, endDepthSpinner;
    private String result = "";
    private JTextArea textArea;
    private JSlider o2Slider, depthSlider;
    private JLabel standardTitle, tabularTitle, space, logoLabel, o2Label, n2Label, o2UnitLabel, depthUnitLabel, startO2, endO2, startDepth, endDepth;
    private JProgressBar bar;
    private JTable table;
    private JScrollPane scroll, tableScroll;
    private JPanel buttonPanel, circlePanel, scrollPanel, standardPanel, mainPanel1, o2Panel, depthPanel, whichPanel, leftPanel, rightPanel,
            tabularPanel, mainPanel2, imagePanel, sliderPanel, unitPanel, mainPanel3, mainPanel4;
    private ImageIcon logo;
    private String calOption, whichTable;
    private double pg, fg, ppResult, eadResult;
    private int depth, depth_meter, ead, startOxy, endOxy, startDep, endDep, columnNumber, rows;
    private DecimalFormat format;
    private String[] column;
    private String[][] data;


    // Constructor for GUI design

    public DivingPanel() {


        //---------------------------------------------------------------------------------
        // The first panel contains a standard calculation title and a standard panel,
        // the standard panel includes a button panel, a circle panel and a scroll panel.
        //---------------------------------------------------------------------------------


        // Set the size of the whole panel
        setPreferredSize(new Dimension(CALC_WIDTH, CALC_HEIGHT));

        // Set up a standard calculations title
        standardTitle = new JLabel("Standard Calculations", SwingConstants.CENTER);
        standardTitle.setFont(new Font("Helvetica", Font.BOLD, 18));

        // Set up five calculation buttons
        modButton = new JRadioButton("Maximum Operating Depth");
        smodButton = new JRadioButton("Standard Maximum Operating Depth");
        bmButton = new JRadioButton("Best Mix");
        ppButton = new JRadioButton("Partial Pressure");
        eadButton = new JRadioButton("Equivalent Air Depth");

        // Group all the radio buttons
        buttonGroup = new ButtonGroup();
        buttonGroup.add(modButton);
        buttonGroup.add(smodButton);
        buttonGroup.add(bmButton);
        buttonGroup.add(ppButton);
        buttonGroup.add(eadButton);

        // Set up a button panel
        buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(250, 200));
        buttonPanel.setBorder(BorderFactory.createTitledBorder("Calculation"));

        // The buttons will be aligned vertically
        // Reference for BoxLayout:
        // https://docs.oracle.com/javase/tutorial/uiswing/layout/box.html
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 10)));
        buttonPanel.add(modButton);
        buttonPanel.add(smodButton);
        buttonPanel.add(bmButton);
        buttonPanel.add(ppButton);
        buttonPanel.add(eadButton);

        // Add actionListener for the buttons
        modButton.addActionListener(new RadioButtonListener());
        modButton.setActionCommand("mod");
        smodButton.addActionListener(new RadioButtonListener());
        smodButton.setActionCommand("smod");
        bmButton.addActionListener(new RadioButtonListener());
        bmButton.setActionCommand("bm");
        ppButton.addActionListener(new RadioButtonListener());
        ppButton.setActionCommand("pp");
        eadButton.addActionListener(new RadioButtonListener());
        eadButton.setActionCommand("ead");

        // Set up three JSpinners for Pg, Fg and depth, and set the range for them
        pgSpinner = new JSpinner(new SpinnerNumberModel(1.4, 1.1, 1.6, 0.1));
        pgSpinner.setBounds(100, 60, 40, 18);
        fgSpinner = new JSpinner(new SpinnerNumberModel(24, 18, 50, 1));
        fgSpinner.setBounds(55, 140, 40, 18);
        depthSpinner = new JSpinner(new SpinnerNumberModel(43, 30, 70, 3));
        depthSpinner.setBounds(140, 140, 40, 18);

        // Creates a CircleImage object
        CircleImage circleImage = new CircleImage();
        circleImage.setBounds(20, 0, 200, 200);

        // Set up a circle panel
        circlePanel = new JPanel();
        circlePanel.setPreferredSize(new Dimension(230, 200));
        circlePanel.setLayout(null);
        circlePanel.add(pgSpinner);
        circlePanel.add(fgSpinner);
        circlePanel.add(depthSpinner);
        circlePanel.add(circleImage);

        // Set up a textArea to display calculation results
        textArea = new JTextArea(11, 20);
        textArea.setBackground(Color.white);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);

        // Set up a JScrollPane to contain textArea
        scroll = new JScrollPane(textArea,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Set up a do calculation button
        doCal = new JButton("Do Calculation");
        doCal.addActionListener(new DoCalculationListener());

        // Set up a scroll panel
        scrollPanel = new JPanel();
        scrollPanel.setLayout(new BorderLayout());
        scrollPanel.add(scroll, BorderLayout.NORTH);
        scrollPanel.add(doCal, BorderLayout.SOUTH);

        // Set up a standard panel
        standardPanel = new JPanel();
        standardPanel.setBorder(BorderFactory.createEtchedBorder());
        // Reference for the FlowLayout:
        // https://www.javatpoint.com/FlowLayout;
        standardPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 8, 5));
        standardPanel.add(buttonPanel);
        standardPanel.add(circlePanel);
        standardPanel.add(scrollPanel);

        // Set up the first main panel
        mainPanel1 = new JPanel();
        mainPanel1.setLayout(new BorderLayout());
        mainPanel1.add(standardTitle, BorderLayout.NORTH);
        mainPanel1.add(standardPanel, BorderLayout.CENTER);


        //---------------------------------------------------------------------------------
        // The second panel contains a tabular calculations title and a tabular panel,
        // the tabular panel includes a left panel and a right panel.
        //---------------------------------------------------------------------------------


        // Set up a tabular calculations title
        tabularTitle = new JLabel("Tabular Calculations", SwingConstants.CENTER);
        tabularTitle.setFont(new Font("Helvetica", Font.BOLD, 18));

        // Set up labels and spinners for o2 panel
        startO2 = new JLabel("StartO2:   ");
        startO2.setFont(new Font("Helvetica", Font.BOLD, 12));
        space = new JLabel("            ");
        endO2 = new JLabel("EndO2:   ");
        endO2.setFont(new Font("Helvetica", Font.BOLD, 12));
        // Reference for the SpinnerNumberModel:
        // https://docs.oracle.com/javase/7/docs/api/javax/swing/SpinnerNumberModel.html
        startO2Spinner = new JSpinner(new SpinnerNumberModel(21, 18, 50, 1));
        endO2Spinner = new JSpinner(new SpinnerNumberModel(39, 18, 50, 1));

        // Set up a o2 panel
        o2Panel = new JPanel();
        o2Panel.setPreferredSize(new Dimension(300, 60));
        o2Panel.setBorder(BorderFactory.createTitledBorder("O2 Percentage"));
        o2Panel.add(startO2);
        o2Panel.add(startO2Spinner);
        o2Panel.add(space);
        o2Panel.add(endO2);
        o2Panel.add(endO2Spinner);

        // Set up labels and spinners for depth panel
        startDepth = new JLabel("Start Depth: ");
        startDepth.setFont(new Font("Helvetica", Font.BOLD, 12));
        space = new JLabel("        ");
        endDepth = new JLabel("End Depth: ");
        endDepth.setFont(new Font("Helvetica", Font.BOLD, 12));
        startDepthSpinner = new JSpinner(new SpinnerNumberModel(3, 3, 70, 3));
        endDepthSpinner = new JSpinner(new SpinnerNumberModel(60, 3, 70, 3));

        // Set up a depth panel
        depthPanel = new JPanel();
        depthPanel.setPreferredSize(new Dimension(300, 60));
        depthPanel.setBorder(BorderFactory.createTitledBorder("Depths"));
        depthPanel.add(startDepth);
        depthPanel.add(startDepthSpinner);
        depthPanel.add(space);
        depthPanel.add(endDepth);
        depthPanel.add(endDepthSpinner);

        // Set up radio buttons for which panel
        eadTableButton = new JRadioButton("EAD Table");
        eadTableButton.setActionCommand("eadTable");
        ppTableButton = new JRadioButton("PP Table");
        ppTableButton.setActionCommand("ppTable");

        // Group the radio buttons
        tableButtonGroup = new ButtonGroup();
        tableButtonGroup.add(eadTableButton);
        tableButtonGroup.add(ppTableButton);

        // Set up a which panel
        whichPanel = new JPanel();
        whichPanel.setPreferredSize(new Dimension(190, 70));
        whichPanel.setBorder(BorderFactory.createTitledBorder("Which Table"));
        whichPanel.add(eadTableButton);
        whichPanel.add(ppTableButton);

        // Set up a create table button
        createTable = new JButton("Create Table");
        createTable.addActionListener(new CreateTableListener());
        createTable.setPreferredSize(new Dimension(190, 40));

        // Set up a left panel
        leftPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(530, 160));
        leftPanel.setBorder(BorderFactory.createEtchedBorder());
        leftPanel.setLayout(new FlowLayout());
        leftPanel.add(o2Panel);
        leftPanel.add(whichPanel);
        leftPanel.add(depthPanel);
        leftPanel.add(createTable);

        // Set up a right panel
        rightPanel = new JPanel();
        rightPanel.setPreferredSize(new Dimension(208, 130));
        rightPanel.setBorder(BorderFactory.createEtchedBorder());
        logo = new ImageIcon("DivingCalculations_console_skeleton/DivingCalculations/resources/sdc_logo.png");
        logoLabel = new JLabel(logo);
        rightPanel.add(logoLabel);

        // Set up a tabular panel
        tabularPanel = new JPanel();
        tabularPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        tabularPanel.add(leftPanel);
        tabularPanel.add(rightPanel);

        // Set up the second main panel
        mainPanel2 = new JPanel();
        mainPanel2.setLayout(new BorderLayout());
        mainPanel2.add(tabularTitle, BorderLayout.NORTH);
        mainPanel2.add(tabularPanel, BorderLayout.CENTER);


        //---------------------------------------------------------------------------------
        // The third panel contains a slider panel and a unit panel,
        // the slider panel includes a o2 slider, a depth slider and an image panel.
        //---------------------------------------------------------------------------------


        // Set up a o2 slider
        o2Slider = new JSlider(JSlider.VERTICAL, 18, 100, 20);
        o2Slider.setPreferredSize(new Dimension(50, 380));
        o2Slider.setPaintTicks(true);
        o2Slider.setPaintLabels(true);
        o2Slider.setMinorTickSpacing(1);
        o2Slider.setMajorTickSpacing(10);
        o2Slider.addChangeListener(new SliderListener());

        // Set up a depth slider
        depthSlider = new JSlider(JSlider.VERTICAL, 0, 70, 40);
        depthSlider.setPreferredSize(new Dimension(50, 380));
        depthSlider.setPaintTicks(true);
        depthSlider.setPaintLabels(true);
        depthSlider.setMinorTickSpacing(1);
        depthSlider.setMajorTickSpacing(10);
        depthSlider.addChangeListener(new SliderListener());

        // Set up a progress bar
        bar = new JProgressBar(SwingConstants.VERTICAL);
        bar.setPreferredSize(new Dimension(100, 300));
        bar.setForeground(Color.green);
        bar.setBackground(Color.yellow);

        // Set up labels to display the percentage of O2 and N2
        o2Label = new JLabel("        % O2");
        o2Label.setFont(new Font("", Font.BOLD, 20));
        n2Label = new JLabel("        % N2");
        n2Label.setFont(new Font("", Font.BOLD, 20));

        // Set up an image panel
        imagePanel = new JPanel();
        imagePanel.setLayout(new BorderLayout());
        imagePanel.add(o2Label, BorderLayout.SOUTH);
        imagePanel.add(n2Label, BorderLayout.NORTH);
        imagePanel.add(bar, BorderLayout.CENTER);

        // Set up a slider panel
        sliderPanel = new JPanel();
        sliderPanel.setPreferredSize(new Dimension(280, 400));
        sliderPanel.setBorder(new EtchedBorder());
        sliderPanel.setLayout(new BorderLayout());
        sliderPanel.add(o2Slider, BorderLayout.WEST);
        sliderPanel.add(depthSlider, BorderLayout.EAST);
        sliderPanel.add(imagePanel, BorderLayout.CENTER);

        // Set up two unit labels for sliders
        o2UnitLabel = new JLabel("% O2");
        depthUnitLabel = new JLabel("Depth(m)");
        unitPanel = new JPanel();
        unitPanel.setPreferredSize(new Dimension(265, 15));
        unitPanel.setLayout(new BorderLayout());
        unitPanel.add(o2UnitLabel, BorderLayout.WEST);
        unitPanel.add(depthUnitLabel, BorderLayout.EAST);

        // Set up the third panel
        mainPanel3 = new JPanel();
        mainPanel3.add(sliderPanel);
        mainPanel3.add(unitPanel);


        //---------------------------------------------------------------------------------
        // The fourth panel is used to display JTable contents.
        //---------------------------------------------------------------------------------


        // Set up the fourth panel to contain JTable
        mainPanel4 = new JPanel();

        // Set bounds for four main panels and add them to the Diving Panel
        setLayout(null);
        mainPanel1.setBounds(10, 10, 750, 245);
        mainPanel2.setBounds(5, 270, 750, 190);
        mainPanel3.setBounds(780, 28, 280, 430);
        mainPanel4.setBounds(10, 465, 1050, 250);

        add(mainPanel1);
        add(mainPanel2);
        add(mainPanel3);
        add(mainPanel4);
    }


    // Create a DiveFormulas object to call the related methods
    DiveFormulas df = new DiveFormulas();



    /**
     * Represents the Listener for calculation radio buttons
     */

    private class RadioButtonListener implements ActionListener {
        /**
         * Making the spinner unable depending on which radio button
         * is pressed
         *
         * @param e1 the event to be processed
         */
        @Override
        public void actionPerformed(ActionEvent e1) {

            switch (e1.getActionCommand()) {

                case "mod" -> {
                    depthSpinner.setEnabled(false);
                    pgSpinner.setEnabled(true);
                    fgSpinner.setEnabled(true);
                }
                case "smod" -> {
                    pgSpinner.setEnabled(false);
                    depthSpinner.setEnabled(false);
                    fgSpinner.setEnabled(true);
                }
                case "bm" -> {
                    fgSpinner.setEnabled(false);
                    depthSpinner.setEnabled(true);
                    pgSpinner.setEnabled(true);
                }
                case "pp", "ead" -> {
                    pgSpinner.setEnabled(false);
                    fgSpinner.setEnabled(true);
                    depthSpinner.setEnabled(true);
                }
            }
        }
    }

    /**
     * Represents the Listener for Do Calculation button
     */
    private class DoCalculationListener implements ActionListener {
        /**
         * Doing calculations when Do Calculation button is pressed
         *
         * @param e2 the event to be processed
         */
        @Override
        public void actionPerformed(ActionEvent e2) {

            if (e2.getActionCommand() == "Do Calculation") {

                calOption = buttonGroup.getSelection().getActionCommand();
                pg = Double.parseDouble(pgSpinner.getValue().toString());
                fg = Double.parseDouble(fgSpinner.getValue().toString());
                depth = Integer.parseInt(depthSpinner.getValue().toString());

                switch (calOption) {
                    /**
                     * Perform the calculations on <i>pg</i>, <i>fg</i> and <i>depth</i> depending on
                     * which radio button is selected
                     *
                     * Displaying the results on the textArea and the value of o2 and depth
                     * will also be displayed on the spinners and progress bar
                     *
                     * @param pg    the value on the pg spinner
                     * @param fg    the value on the fg spinner
                     * @param depth the value on the depth spinner
                     */
                    case "mod" -> {

                        depth_meter = (int) df.calculateMOD(pg, fg);
                        format = new DecimalFormat("0.0");
                        result += "Maximum operating depth (MOD) for a dive with " + fg + "% O2 and a partial pressure of " + format.format(pg)
                                + " is " + depth_meter + " metres";
                        result += "\n\n===========================\n";
                        textArea.setText(result);
                        o2Slider.setValue(Integer.parseInt((fgSpinner.getValue().toString())));
                        depthSlider.setValue(depth_meter);
                        bar.setValue(o2Slider.getValue());
                        bar.setString(o2Slider.getValue() + "% O2");
                    }

                    case "smod" -> {
                        depth_meter = (int) df.calculateSMOD(fg);
                        result += "Maximum operating depth (MOD) for a dive with " + fg + "% O2 and a partial pressure of 1.4"
                                + " is " + depth_meter + " metres";
                        result += "\n\n===========================\n";
                        textArea.setText(result);
                        o2Slider.setValue(Integer.parseInt(fgSpinner.getValue().toString()));
                        depthSlider.setValue(depth_meter);
                        bar.setValue(o2Slider.getValue());
                        bar.setString(o2Slider.getValue() + "% O2");
                    }

                    case "bm" -> {
                        fg = df.calculateBM(pg, depth);
                        result += "Best mix for a drive to " + (double) depth + " metres with a partial pressure of " + pg
                                + " is " + fg + "% O2";
                        result += "\n\n===========================\n";
                        textArea.setText(result);
                        o2Slider.setValue((int) fg);
                        depthSlider.setValue(Integer.parseInt(depthSpinner.getValue().toString()));
                        bar.setValue(o2Slider.getValue());
                        bar.setString(o2Slider.getValue() + "% O2");
                    }

                    case "pp" -> {
                        pg = df.calculatePP(depth, fg);
                        result += "The partial pressure of Oxygen for a dive to " + (double) depth + " with a percentage of Oxygen of "
                                + fg + "% is " + pg + " ata";
                        result += "\n\n===========================\n";
                        textArea.setText(result);
                        o2Slider.setValue(Integer.parseInt(fgSpinner.getValue().toString()));
                        depthSlider.setValue(Integer.parseInt(depthSpinner.getValue().toString()));
                        bar.setValue(o2Slider.getValue());
                        bar.setString(o2Slider.getValue() + "% O2");
                    }

                    case "ead" -> {
                        ead = (int) (df.calculateEAD(depth, fg));
                        result += "Equivalent Air Depth for a dive with " + fg + "% O2 to a depth of "
                                + (double) depth + " metres is " + ead + " metres";
                        result += "\n\n===========================\n";
                        textArea.setText(result);
                        o2Slider.setValue(Integer.parseInt(fgSpinner.getValue().toString()));
                        depthSlider.setValue(Integer.parseInt(depthSpinner.getValue().toString()));
                        bar.setValue(o2Slider.getValue());
                        bar.setString(o2Slider.getValue() + "% O2");
                    }
                }
            }
        }
    }

    /**
     * Represents the Listener for Create Table button
     */
    private class CreateTableListener implements ActionListener {
        /**
         * Generating tables when Create Table button is pressed
         *
         * @param e3 the event to be processed
         */
        @Override
        public void actionPerformed(ActionEvent e3) {

            if (e3.getActionCommand() == "Create Table") {

                whichTable = tableButtonGroup.getSelection().getActionCommand();
                startOxy = Integer.parseInt(startO2Spinner.getValue().toString());
                endOxy = Integer.parseInt(endO2Spinner.getValue().toString());
                startDep = Integer.parseInt(startDepthSpinner.getValue().toString());
                endDep = Integer.parseInt(endDepthSpinner.getValue().toString());

                // Set table rows and columns
                columnNumber = (endOxy - startOxy) + 2;
                column = new String[columnNumber];
                column[0] = "";
                for (int i = 1; i < columnNumber; i++) {
                    column[i] = String.valueOf(startOxy);
                    startOxy++;
                }
                rows = (endDep - startDep) / 3 + 1;
                data = new String[rows][columnNumber];

                /**
                 * Perform the calculations and generating tables depending on
                 * which table radio button is pressed
                 *
                 * Displaying the table on the scroll panel
                 *
                 * @param startDep the value on the start depth spinner
                 * @param startOxy the value on the start O2 spinner
                 */
                switch (whichTable) {
                    case "ppTable" -> {
                        for (int i = 0; i < rows; i++) {
                            data[i][0] = String.valueOf(startDep);
                            startOxy = Integer.parseInt(startO2Spinner.getValue().toString());
                            for (int j = 1; j < columnNumber; j++) {
                                ppResult = df.calculatePP(startDep, startOxy);
                                data[i][j] = String.valueOf(ppResult);
                                if (ppResult <= 1.6) {
                                    data[i][j] = String.format("%.2f", ppResult);
                                } else {
                                    data[i][j] = "";
                                }
                                startOxy += 1;
                            }
                            startDep += 3;
                        }
                        table = new JTable(data, column);
                    }

                    case "eadTable" -> {
                        for (int i = 0; i < rows; i++) {
                            data[i][0] = String.valueOf(startDep);
                            startOxy = Integer.parseInt(startO2Spinner.getValue().toString());
                            for (int j = 1; j < columnNumber; j++) {
                                eadResult = df.calculateEAD(startDep, startOxy);
                                data[i][j] = String.valueOf(eadResult);
                                startOxy += 1;
                            }
                            startDep += 3;
                        }
                        table = new JTable(data, column);
                    }
                }

                table.setGridColor(Color.blue);
                tableScroll = new JScrollPane(table);
                mainPanel4.setLayout(new BorderLayout());
                mainPanel4.add(tableScroll, BorderLayout.CENTER);
                mainPanel4.revalidate();
            }
        }
    }

    /**
     * Represents the Listener for o2 slider and depth slider
     */
    private class SliderListener implements ChangeListener {
        /**
         * Setting the o2 label and n2 label with corresponding value to explicitly
         * demonstrate the percentage of air composition
         *
         * @param e4 the event to be processed
         */
        @Override
        public void stateChanged(ChangeEvent e4) {
            o2Label.setText("        " + o2Slider.getValue() + "% O2");
            n2Label.setText("        " + (100 - o2Slider.getValue()) + "% N2");
        }
    }
}


