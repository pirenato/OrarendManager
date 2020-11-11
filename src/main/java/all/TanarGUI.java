package all;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class TanarGUI {
    private JPanel panel;
    private JButton újÓraButton;
    private JButton szerkesztésButton;
    private JButton törlésButton;
    private JButton listázásButton;
    private JButton keresesButton;
    private JComboBox comboBoxKeres;
    private JTextField textFieldKeres;
    private JButton generálásButton;
    private JComboBox comboBox2;
    private JTextField textField2;
    private JButton kilépésButton;
    private List<Course> courseList;
    private CourseController courseController;


    public TanarGUI() {
        CourseDatabaseManager courseDatabaseManager = new CourseDatabaseManager();
        courseList = courseDatabaseManager.loadAllData(); //az adatbázisból az összes tárgy beolvasása

        újÓraButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    JDialog dialog = new AddCourse();
                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(null, "Hiba történt", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        listázásButton.addActionListener(new ActionListener() {
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
        kilépésButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        keresesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Course> list = null;
                try {
                    switch (comboBoxKeres.getSelectedIndex()) {
                        case 0:
                            list = courseController.searchList(courseList, Course::getFelev, textFieldKeres.getText());
                            break;
                        case 1:
                            list = courseController.searchList(courseList, Course::getKar, textFieldKeres.getText());
                            break;
                        case 2:
                            list = courseController.searchList(courseList, Course::getSzki, textFieldKeres.getText());
                            break;
                        case 3:
                            list = courseController.searchList(courseList, Course::getTi, textFieldKeres.getText());
                            break;
                        case 4:
                            list = courseController.searchList(courseList, Course::getTantargy, textFieldKeres.getText());
                            break;
                        case 5:
                            list = courseController.searchList(courseList, Course::getTanszek, textFieldKeres.getText());
                            break;
                        case 6:
                            list = courseController.searchList(courseList, Course::getEloado, textFieldKeres.getText());
                            break;
                        case 7:
                            list = courseController.searchList(courseList, Course::getCsoport, textFieldKeres.getText());
                            break;
                        case 8:
                            list = courseController.searchList(courseList, Course::getFo, textFieldKeres.getText());
                            break;
                        case 9:
                            list = courseController.searchList(courseList, Course::getKezdes, textFieldKeres.getText());
                            break;
                        case 10:
                            list = courseController.searchList(courseList, Course::getHossz, textFieldKeres.getText());
                            break;
                        case 11:
                            list = courseController.searchList(courseList, Course::getTerem, textFieldKeres.getText());
                            break;
                        case 12:
                            list = courseController.searchList(courseList, Course::getNap, textFieldKeres.getText());
                            break;
                        case 13:
                            list = courseController.searchList(courseList, Course::getTipus, textFieldKeres.getText());
                            break;
                    }

                    JDialog dialog = new CourseTable(list);
                    dialog.setVisible(true);
                } catch (NullPointerException e1) {
                    JOptionPane.showMessageDialog(null, "Nincs kiválasztva, hogy melyik mezőben akarsz keresni!", "Keresési hiba", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public void TanarPage() {
        JFrame frame = new JFrame("GUI");
        frame.setContentPane(new TanarGUI().panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}
