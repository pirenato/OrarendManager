/**
 * A hallgato szerepkorrel bejelentkezo felhasznaloi felulet
 */

package gui;

import all.Course;
import all.CourseController;
import all.CourseDatabaseManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class HallgatoGUI {
    private JPanel panel;
    private JButton listCoursesButton;
    private JTextField generateByTankorTextField;
    private JButton generateTimetableButton;
    private JButton searchCoursesButton;
    private JTextField searchCoursesTextField;
    private JComboBox comboBox;
    private List<Course> courseList;


    public HallgatoGUI() {
        CourseDatabaseManager courseDatabaseManager = new CourseDatabaseManager();

        //kereses az orakban
        searchCoursesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                courseList = courseDatabaseManager.loadAllData();
                try {
                    List<Course> list = CourseController.searchCourse(courseList, comboBox.getSelectedIndex(), searchCoursesTextField.getText()); //a kivalasztott oszlopban keresi a beirt erteket
                    JDialog dialog = new CourseTableStudent(list);
                    dialog.setVisible(true);
                } catch (NullPointerException e1) {
                    JOptionPane.showMessageDialog(null, "Nincs beolvasva fajl!", "Beolvasasi hiba", JOptionPane.ERROR_MESSAGE);
                }

            }
        });


        //orarend generalasa csoport (tankor) alapjan
        generateTimetableButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                            String queryTankor = generateByTankorTextField.getText();
                            JDialog tankorDialog = new TimeTable(courseDatabaseManager.searchDatabaseColumn("csoport", queryTankor));
                            tankorDialog.setVisible(true);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });

        //osszes orat tartalmazo tablazat megjelenitese
        listCoursesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                courseList = courseDatabaseManager.loadAllData();

                try {
                    JDialog dialog = new CourseTableStudent(courseList); //a hallgato altal elerheto tablazatban nem lehet adatmodositast vegezni
                    dialog.setVisible(true);
                } catch (NullPointerException e1) {
                    JOptionPane.showMessageDialog(null, "Nincs beolvasva fajl!", "Beolvasasi hiba", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }


    public void HallgatoPage() {
        JFrame frame = new JFrame("GUI");
        frame.setContentPane(new HallgatoGUI().panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
