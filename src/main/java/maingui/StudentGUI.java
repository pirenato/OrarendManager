/**
 * A hallgato szerepkorrel bejelentkezo felhasznaloi felulet
 */

package maingui;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import coursemanagement.*;
import usermanagement.ChangePasswordGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class StudentGUI {
    private JPanel panel;
    private JButton listCoursesButton;
    private JTextField generateByTankorTextField;
    private JButton generateTimetableButton;
    private JButton searchCoursesButton;
    private JTextField searchCoursesTextField;
    private JComboBox comboBox;
    private JButton changePasswordButton;
    private JButton exitButton;
    private List<Course> courseList;


    public StudentGUI() {
        CourseDatabaseManager courseDatabaseManager = new CourseDatabaseManager();
/*
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

 */


        //orarend generalasa csoport (tankor) alapjan
        generateTimetableButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String queryTankor = generateByTankorTextField.getText();
                    JDialog tankorDialog = new TimeTable(courseDatabaseManager.searchDatabaseColumn("csoport", queryTankor));
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
        changePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChangePasswordGUI changePasswordGUI = new ChangePasswordGUI();
                changePasswordGUI.setVisible(true);
            }
        });
        searchCoursesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                courseList = courseDatabaseManager.loadAllData();
                try {
                    List<Course> list = CourseController.searchCourse(courseList, comboBox.getSelectedIndex(), searchCoursesTextField.getText());
                    JDialog dialog = new CourseTable(list);
                    dialog.setVisible(true);
                } catch (NullPointerException e1) {
                    JOptionPane.showMessageDialog(null, "Nincs beolvasva fajl!", "Beolvasasi hiba", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    public void HallgatoPage() {
        JFrame frame = new JFrame("Hallgatói felület");
        frame.setContentPane(new StudentGUI().panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
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
        panel = new JPanel();
        panel.setLayout(new GridLayoutManager(5, 6, new Insets(0, 0, 0, 0), -1, -1));
        final JLabel label1 = new JLabel();
        label1.setText("Minden óra listázása: ");
        panel.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Keresés:");
        panel.add(label2, new GridConstraints(1, 0, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Órarend generálása:");
        panel.add(label3, new GridConstraints(2, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        listCoursesButton = new JButton();
        listCoursesButton.setText("Listázás");
        panel.add(listCoursesButton, new GridConstraints(0, 5, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        generateByTankorTextField = new JTextField();
        panel.add(generateByTankorTextField, new GridConstraints(2, 2, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        generateTimetableButton = new JButton();
        generateTimetableButton.setText("Generálás");
        panel.add(generateTimetableButton, new GridConstraints(2, 5, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        searchCoursesButton = new JButton();
        searchCoursesButton.setText("Keresés");
        panel.add(searchCoursesButton, new GridConstraints(1, 5, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        searchCoursesTextField = new JTextField();
        panel.add(searchCoursesTextField, new GridConstraints(1, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        comboBox = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("Félév");
        defaultComboBoxModel1.addElement("Kar");
        defaultComboBoxModel1.addElement("Szki");
        defaultComboBoxModel1.addElement("Ti");
        defaultComboBoxModel1.addElement("Tantárgy");
        defaultComboBoxModel1.addElement("Tanszék");
        defaultComboBoxModel1.addElement("Előadó");
        defaultComboBoxModel1.addElement("Csoport");
        defaultComboBoxModel1.addElement("Fő");
        defaultComboBoxModel1.addElement("Kezdés");
        defaultComboBoxModel1.addElement("Hossz");
        defaultComboBoxModel1.addElement("Terem");
        defaultComboBoxModel1.addElement("Nap");
        defaultComboBoxModel1.addElement("Típus");
        comboBox.setModel(defaultComboBoxModel1);
        panel.add(comboBox, new GridConstraints(1, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("Saját jelszó módosítása:");
        panel.add(label4, new GridConstraints(3, 0, 2, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        changePasswordButton = new JButton();
        changePasswordButton.setText("Módosítás");
        panel.add(changePasswordButton, new GridConstraints(3, 5, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        exitButton = new JButton();
        exitButton.setText("Kilépés");
        panel.add(exitButton, new GridConstraints(4, 5, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel;
    }

}
