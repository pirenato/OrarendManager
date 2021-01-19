/**
 * A generalt orarend felhasznaloi felulete
 */

package coursemanagement;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import coursemanagement.Course;
import coursemanagement.CourseController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class TimeTable extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton backButton;
    private JPanel tablePanel;
    private JTable table;
    private JComboBox comboBox1;

    public TimeTable(List<Course> list) {
        setContentPane(contentPane);
        setTitle("Generált órarend");
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        /**
         * A sok egyidoben levo ora miatt nagyon "szeles" lenne a tablazat, ezert az orarendet kulon napokra kell feloszatni
         */
        List<Course> monday = new ArrayList<Course>();
        List<Course> tuesday = new ArrayList<Course>();
        List<Course> wednesday = new ArrayList<Course>();
        List<Course> thursday = new ArrayList<Course>();
        List<Course> friday = new ArrayList<Course>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getNap().toLowerCase().equals("h"))
                monday.add(list.get(i));
            if (list.get(i).getNap().toLowerCase().equals("k"))
                tuesday.add(list.get(i));
            if (list.get(i).getNap().toLowerCase().equals("sz"))
                wednesday.add(list.get(i));
            if (list.get(i).getNap().toLowerCase().equals("cs"))
                thursday.add(list.get(i));
            if (list.get(i).getNap().toLowerCase().equals("p"))
                friday.add(list.get(i));
        }

        /**
         * Sok ora "duplikalva" van, egy idopontban, egy teremben van, es ugyanaz a tanar tartja,
         * csak tobb csoportnak kulon van kiirva, orarend generalasahoz eleg ezek kozul csak egy ora,
         * a tobbit ez a metodus eltavolitja a listakbol
         */
        CourseController.removeDuplicateCourses(monday);
        CourseController.removeDuplicateCourses(tuesday);
        CourseController.removeDuplicateCourses(wednesday);
        CourseController.removeDuplicateCourses(thursday);
        CourseController.removeDuplicateCourses(friday);

        //alapertelmezettkent a hetfoi napon levo targyakat listazza
        selectDay(monday, "Hetfo");

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);


        //a JComboBox-ban kivalasztott nap orainak megjelenitese, ha nem ures
        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (comboBox1.getSelectedIndex()) {
                    case 0:
                        if (monday.size() == 0)
                            JOptionPane.showMessageDialog(null, "Ezen a napon nincs megjelenitheto ora");
                        else
                            selectDay(monday, "Hetfo");
                        break;
                    case 1:
                        if (tuesday.size() == 0)
                            JOptionPane.showMessageDialog(null, "Ezen a napon nincs megjelenitheto ora");
                        else
                            selectDay(tuesday, "Kedd");
                        break;
                    case 2:
                        if (wednesday.size() == 0)
                            JOptionPane.showMessageDialog(null, "Ezen a napon nincs megjelenitheto ora");
                        else
                            selectDay(wednesday, "Szerda");
                        break;
                    case 3:
                        if (thursday.size() == 0)
                            JOptionPane.showMessageDialog(null, "Ezen a napon nincs megjelenitheto ora");
                        else
                            selectDay(thursday, "Csutortok");
                        break;
                    case 4:
                        if (friday.size() == 0)
                            JOptionPane.showMessageDialog(null, "Ezen a napon nincs megjelenitheto ora");
                        else
                            selectDay(friday, "Pentek");
                        break;
                }
            }
        });

        setMinimumSize(new Dimension(600, 300));
        pack();
        setVisible(true);
    }

    private void onOK() {
        dispose();
    }

    private void onCancel() {
        dispose();
    }

    /**
     * Orarend generalasahoz szukseges tablazat letrehozasa.
     * A tablazat mindig ket oszloppal indul, az elso az orak kezdesenek idopontjat tartalmazza, a masodik oszlopba
     * szurja be az adott oraban kezdodo tantargy nevet. Ha utkozes van egy uj oszlopot ad hozza, oda irja be a targy nevet.
     *
     * @param dayList   a targyakat tartalmazo lista kulon napokra bontva
     * @param dayColumn ha nem ures cellaba akarunk irni (orautkozes) az ujonnan hozzaadott oszlop neve
     */
    private void selectDay(List<Course> dayList, String dayColumn) {

        //fejlec hozzaadasa
        DefaultTableModel model = new DefaultTableModel(new Object[]{"Kezdés", dayColumn}, 0);
        table.setAutoCreateRowSorter(true);
        table.setFillsViewportHeight(true);
        table.setPreferredScrollableViewportSize(new Dimension(800, 200));

        //elso oszlop feltoltese az orak lehetseges kezdesenek idopontjaval
        model.addRow(new Object[]{"08:00"});
        model.addRow(new Object[]{"09:00"});
        model.addRow(new Object[]{"10:00"});
        model.addRow(new Object[]{"11:00"});
        model.addRow(new Object[]{"12:00"});
        model.addRow(new Object[]{"13:00"});
        model.addRow(new Object[]{"14:00"});
        model.addRow(new Object[]{"15:00"});
        model.addRow(new Object[]{"16:00"});
        model.addRow(new Object[]{"17:00"});
        model.addRow(new Object[]{"18:00"});
        model.addRow(new Object[]{"19:00"});

        //a tablazat feltoltese az adott nap targyaival
        for (int i = 0; i < dayList.size(); i++) {
            int rowIndex;
            int columnIndex = 1; //a 0. oszlop az idopont, mindig 1-rol kell indulnia

            //sor beallitasa az ora kezdese alapjan, az elso ora 8-kor kezdodik, ekkor a sort 0-ra kell beallitani
            switch (dayList.get(i).getKezdes()) {
                case 8:
                    rowIndex = 0;
                    break;
                case 9:
                    rowIndex = 1;
                    break;
                case 10:
                    rowIndex = 2;
                    break;
                case 11:
                    rowIndex = 3;
                    break;
                case 12:
                    rowIndex = 4;
                    break;
                case 13:
                    rowIndex = 5;
                    break;
                case 14:
                    rowIndex = 6;
                    break;
                case 15:
                    rowIndex = 7;
                    break;
                case 16:
                    rowIndex = 8;
                    break;
                case 17:
                    rowIndex = 9;
                    break;
                case 18:
                    rowIndex = 10;
                    break;
                default:
                    continue;
                    //throw new IllegalStateException("Unexpected value: " + list.get(i).getKezdes());
            }

            int rowCount = rowIndex + dayList.get(i).getHossz();
            Object cellValue = model.getValueAt(rowIndex, columnIndex); //valtozo a cella uressegenek vizsgalatara
            if (cellValue == null) { //ha a cella ures a tantargy neve beirasra kerul a cellaba
                while (rowIndex < rowCount) {
                    model.setValueAt(dayList.get(i).getTantargy(), rowIndex, columnIndex);
                    rowIndex++;
                }
            } else {
                int columnCount = model.getColumnCount() - 1; //a tablazat oszlopainak szama az adott iteracioban

                if (columnIndex == columnCount) { //annak vizsgalata, hogy az iteracioban az aktualis oszlop az utolso-e a tablazatban
                    model.addColumn(dayColumn); //ha az utolso oszlop volt es nem ures az adott cella, akkor uj oszlopot kell hozzaadni
                    columnIndex++;
                    while (rowIndex < rowCount) {
                        model.setValueAt(dayList.get(i).getTantargy(), rowIndex, columnIndex);
                        rowIndex++;
                    }
                   // model.setValueAt(dayList.get(i).getId() + ", " + dayList.get(i).getTantargy(), rowIndex, columnIndex); //a megnovelt columnIndex valtozoval mar az uj oszlopba szurja be az uj targyat


                } else { //ha az iteracioban szereplo oszlop nem az utolso oszlop a tablazatban
                    while (columnIndex < columnCount) { //vegig kell mennie a a tablazat oszlopain, es minden oszlopban megvizsgalni van-e ures cella a megfelelo sorban
                        columnIndex++;
                        cellValue = model.getValueAt(rowIndex, columnIndex);
                        if (cellValue == null) {
                            while (rowIndex < rowCount) {
                                model.setValueAt(dayList.get(i).getTantargy(), rowIndex, columnIndex);
                                rowIndex++;
                            }
                            break;
                        } else if (columnIndex == columnCount && cellValue != null) { //ha az osszes oszlopon atment a ciklus es nem talalt ures oszlopot
                            model.addColumn(dayColumn);
                            columnIndex++;
                            while (rowIndex < rowCount) {
                                model.setValueAt(dayList.get(i).getTantargy(), rowIndex, columnIndex);
                                rowIndex++;
                            }
                        }
                    }
                }
            }
        }
        table.setModel(model);
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        contentPane = new JPanel();
        contentPane.setLayout(new GridLayoutManager(2, 2, new Insets(10, 10, 10, 10), -1, -1));
        tablePanel = new JPanel();
        tablePanel.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(tablePanel, new GridConstraints(1, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        tablePanel.add(scrollPane1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        table = new JTable();
        scrollPane1.setViewportView(table);
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        tablePanel.add(panel1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel1.add(spacer1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel2, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        backButton = new JButton();
        backButton.setText("Vissza");
        panel2.add(backButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Válaszd ki melyik nap órarendjét akarod megtekinteni:");
        contentPane.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        comboBox1 = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("Hétfő");
        defaultComboBoxModel1.addElement("Kedd");
        defaultComboBoxModel1.addElement("Szerda");
        defaultComboBoxModel1.addElement("Csütörtök");
        defaultComboBoxModel1.addElement("Péntek");
        comboBox1.setModel(defaultComboBoxModel1);
        contentPane.add(comboBox1, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
    }
}
