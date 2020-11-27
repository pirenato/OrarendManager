/**
 * Tanari szerepkorrel bejelentkezett felhasznaloi GUI
 */

package maingui;

import coursemanagement.*;
import usermanagement.ChangePasswordGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class TeacherGUI {
    private JPanel panel;
    private JButton newCourseButton;
    private JButton listCoursesButton;
    private JButton searchCoursesButton;
    private JComboBox comboBoxSearchCourse;
    private JTextField searchTextField;
    private JButton generateTimetableButton;
    private JComboBox comboBoxGenerateTimetable;
    private JTextField generateTextField;
    private JButton cancelButton;
    private JButton changePasswordButton;
    private List<Course> courseList;
    private CourseController courseController;


    public TeacherGUI() {

        CourseDatabaseManager courseDatabaseManager = new CourseDatabaseManager();
        courseList = courseDatabaseManager.loadAllData(); //az adatbazisbol az osszes targy beolvasasa

        //uj ora hozzaadasakor felugro JDialog
        newCourseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    JDialog dialog = new AddCourseGUI();
                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(null, "Hiba tortent", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        //osszes ora listazasakor felugro JDialog
        listCoursesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    courseList = courseDatabaseManager.loadAllData();
                    JDialog dialog = new CourseTable(courseList);
                    dialog.setVisible(true);
                } catch (NullPointerException e1) {
                    JOptionPane.showMessageDialog(null, "Nincs beolvasva adat!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        //orakban kereseskor felugro ablak
        searchCoursesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Course> list = null;
                try {
                    switch (comboBoxSearchCourse.getSelectedIndex()) { //a JComboBox-ban kivalasztott oszlopban keresi a felhasznalo altal JTextBox-ba beirt erteket
                        case 0:
                            list = courseController.searchList(courseList, Course::getFelev, searchTextField.getText());
                            break;
                        case 1:
                            list = courseController.searchList(courseList, Course::getKar, searchTextField.getText());
                            break;
                        case 2:
                            list = courseController.searchList(courseList, Course::getSzki, searchTextField.getText());
                            break;
                        case 3:
                            list = courseController.searchList(courseList, Course::getTi, searchTextField.getText());
                            break;
                        case 4:
                            list = courseController.searchList(courseList, Course::getTantargy, searchTextField.getText());
                            break;
                        case 5:
                            list = courseController.searchList(courseList, Course::getTanszek, searchTextField.getText());
                            break;
                        case 6:
                            list = courseController.searchList(courseList, Course::getEloado, searchTextField.getText());
                            break;
                        case 7:
                            list = courseController.searchList(courseList, Course::getCsoport, searchTextField.getText());
                            break;
                        case 8:
                            list = courseController.searchList(courseList, Course::getFo, searchTextField.getText());
                            break;
                        case 9:
                            list = courseController.searchList(courseList, Course::getKezdes, searchTextField.getText());
                            break;
                        case 10:
                            list = courseController.searchList(courseList, Course::getHossz, searchTextField.getText());
                            break;
                        case 11:
                            list = courseController.searchList(courseList, Course::getTerem, searchTextField.getText());
                            break;
                        case 12:
                            list = courseController.searchList(courseList, Course::getNap, searchTextField.getText());
                            break;
                        case 13:
                            list = courseController.searchList(courseList, Course::getTipus, searchTextField.getText());
                            break;
                    }

                    JDialog dialog = new CourseTable(list); //a felugro tablazat bemenetkent megkapja azt a listat, amiben csak a keresett Course objektumok szerepelnek
                    dialog.setVisible(true);
                } catch (NullPointerException e1) {
                    JOptionPane.showMessageDialog(null, "Nincs kivalasztva, hogy melyik mezoben akarsz keresni!", "Keresesi hiba", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        //az orarend generalasakor felugro ablak
        generateTimetableButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    switch (comboBoxGenerateTimetable.getSelectedIndex()) { //JComboBox-bol lehet kivalasztani, hogy egy tanszek vagy egy eloado alapjan kivan a felhasznalo orarendet generalni
                        case 0:
                            String keresettTanszek = generateTextField.getText();
                            JDialog tanszekDialog = new TimeTable(courseDatabaseManager.searchDatabaseColumn("tanszek", keresettTanszek));
                            tanszekDialog.setVisible(true);
                        case 1:
                            String keresettEloado = generateTextField.getText();
                            JDialog eloadoDialog = new TimeTable(courseDatabaseManager.searchDatabaseColumn("eloado", keresettEloado));
                            eloadoDialog.setVisible(true);
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
        changePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChangePasswordGUI changePasswordGUI = new ChangePasswordGUI();
                changePasswordGUI.setVisible(true);
            }
        });
    }

    public void TanarPage() {
        JFrame frame = new JFrame("GUI");
        frame.setContentPane(new TeacherGUI().panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Tanári felület");
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}
