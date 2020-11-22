/**
 * A generalt orarend felhasznaloi felulete
 */

package gui;

import all.Course;
import all.CourseController;

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
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        /**
         * A sok egy idoben levo ora miatt nagyon "szeles" lenne a tablazat, ezert az orarendet kulon napokra kell feloszatni
         */
        List<Course> hetfo = new ArrayList<Course>();
        List<Course> kedd = new ArrayList<Course>();
        List<Course> szerda = new ArrayList<Course>();
        List<Course> csutortok = new ArrayList<Course>();
        List<Course> pentek = new ArrayList<Course>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getNap().toLowerCase().equals("h"))
                hetfo.add(list.get(i));
            if (list.get(i).getNap().toLowerCase().equals("k"))
                kedd.add(list.get(i));
            if (list.get(i).getNap().toLowerCase().equals("sz"))
                szerda.add(list.get(i));
            if (list.get(i).getNap().toLowerCase().equals("cs"))
                csutortok.add(list.get(i));
            if (list.get(i).getNap().toLowerCase().equals("p"))
                pentek.add(list.get(i));
        }

        /**
         * Sok ora "duplikalva" van, egy idopontban, egy teremben van, es ugyanaz a tanar tartja,
         * csak tobb csoportnak kulon van kiirva, orarend generalasahoz eleg ezek kozul csak egy ora,
         * a tobbit ez a metodus eltavolitja a listakbol
         */
        CourseController.removeDuplicateCourses(hetfo);
        CourseController.removeDuplicateCourses(kedd);
        CourseController.removeDuplicateCourses(szerda);
        CourseController.removeDuplicateCourses(csutortok);
        CourseController.removeDuplicateCourses(pentek);

        //alapertelmezettkent a hetfoi napon levo targyakat listazza
        selectDay(hetfo, "Hetfo");

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


        //a JComboBox-ban kivalasztott nap orainak megjelenitese
        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (comboBox1.getSelectedIndex()) {
                    case 0:
                        if(hetfo.size() == 0)
                            JOptionPane.showMessageDialog(null, "Ezen a napon nincs megjelenitheto ora");
                        else
                            selectDay(hetfo, "Hetfo");
                        break;
                    case 1:
                        if(kedd.size() == 0)
                            JOptionPane.showMessageDialog(null, "Ezen a napon nincs megjelenitheto ora");
                        else
                        selectDay(kedd, "Kedd");
                        break;
                    case 2:
                        if(szerda.size() == 0)
                            JOptionPane.showMessageDialog(null, "Ezen a napon nincs megjelenitheto ora");
                        else
                            selectDay(szerda, "Szerda");
                        break;
                    case 3:
                        if(csutortok.size() == 0)
                            JOptionPane.showMessageDialog(null, "Ezen a napon nincs megjelenitheto ora");
                        else
                            selectDay(csutortok, "Csutortok");
                        break;
                    case 4:
                        if(pentek.size() == 0)
                            JOptionPane.showMessageDialog(null, "Ezen a napon nincs megjelenitheto ora");
                        else
                            selectDay(pentek, "Pentek");
                        break;
                }
            }
        });

        setMinimumSize(new Dimension(1000, 400));
        pack();
    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    /**
     * Orarend generalasahoz szukseges tablazat letrehozasa.
     * A tablazat mindig ket oszloppal indul, az elso az orak kezdesenek idopontjat tartalmazza, a masodik oszlopba
     * szurja be az adott oraban kezdodo tantargy nevet. Ha utkozes van egy uj oszlopot ad hozza, oda irja be a targy nevet.
     * @param dayList a targyakat tartalmazo lista kulon napokra bontva
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
            int row;
            int column = 1; //a 0. oszlop az idopont, mindig 1-rol kell indulnia

            //sor beallitasa az ora kezdese alapjan, az elso ora 8-kor kezdodik, ekkor a sort 0-ra kell beallitani
            switch (dayList.get(i).getKezdes()) {
                case 8:
                    row = 0;
                    break;
                case 9:
                    row = 1;
                    break;
                case 10:
                    row = 2;
                    break;
                case 11:
                    row = 3;
                    break;
                case 12:
                    row = 4;
                    break;
                case 13:
                    row = 5;
                    break;
                case 14:
                    row = 6;
                    break;
                case 15:
                    row = 7;
                    break;
                case 16:
                    row = 8;
                    break;
                case 17:
                    row = 9;
                    break;
                case 18:
                    row = 10;
                    break;
                default:
                    continue;
                    //throw new IllegalStateException("Unexpected value: " + list.get(i).getKezdes());
            }

            Object cellValue = model.getValueAt(row, column); //valtozo a cella uressegenek vizsgalatara
            if(cellValue == null) { //ha a cella ures a tantargy neve beirasra kerul a cellaba
                model.setValueAt(dayList.get(i).getId() + ", " + dayList.get(i).getTantargy(), row, column);
            } else {
                int columnCount = model.getColumnCount() -1; //a tablazat oszlopainak szama az adott iteracioban
                if (column == columnCount) { //annak vizsgalata, hogy az iteracioban az aktualis oszlop az utolso-e a tablazatban
                    model.addColumn(dayColumn); //ha az utolso oszlop volt es nem ures az adott cella, akkor uj oszlopot kell hozzaadni
                    column++;
                    model.setValueAt(dayList.get(i).getId() + ", " + dayList.get(i).getTantargy(), row, column); //a megnovelt column valtozoval mar az uj oszlopba szurja be az uj targyat
                } else { //ha az iteracioban szereplo oszlop nem az utolso oszlop a tablazatban
                    while (column < columnCount) { //vegig kell mennie a a tablazat oszlopain, es minden oszlopban megvizsgalni van-e ures cella a megfelelo sorban
                        column++;
                        cellValue = model.getValueAt(row, column);
                        if (cellValue == null) {
                            model.setValueAt(dayList.get(i).getId() + ", " + dayList.get(i).getTantargy(), row, column); //ha volt ures cella a targy beszurhato
                            break;
                        } else if (column == columnCount && cellValue != null) { //ha az osszes oszlopon atment a ciklus es nem talalt ures oszlopot
                            model.addColumn(dayColumn);
                            column++;
                            model.setValueAt(dayList.get(i).getId() + ", " + dayList.get(i).getTantargy(), row, column); //targy beszurasa az ujonnan hozzaadott oszlopba
                        }
                    }
                }
            }
        }
        table.setModel(model);
    }
}
