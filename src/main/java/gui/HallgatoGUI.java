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
    private JButton listButton;
    private JTextField tankorTextField;
    private JButton generalButton;
    private JButton searchButton;
    private JTextField searchTextField;
    private JComboBox comboBox;
    private List<Course> courseList;


    public HallgatoGUI() {
        CourseDatabaseManager courseDatabaseManager = new CourseDatabaseManager();
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                courseList = courseDatabaseManager.loadAllData();
                try {
                    List<Course> list = CourseController.searchCourse(courseList, comboBox.getSelectedIndex(), searchTextField.getText());

                    JDialog dialog = new CourseTableStudent(list);
                    dialog.setVisible(true);
                } catch (NullPointerException e1) {
                    JOptionPane.showMessageDialog(null, "Nincs beolvasva fajl!", "Beolvasasi hiba", JOptionPane.ERROR_MESSAGE);
                }

            }
        });


        generalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                            String queryTankor = tankorTextField.getText();
                            JDialog tankorDialog = new TimeTable(courseDatabaseManager.search("csoport", queryTankor));
                            tankorDialog.setVisible(true);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });

        listButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                courseList = courseDatabaseManager.loadAllData();

                try {
                    JDialog dialog = new CourseTableStudent(courseList);
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
