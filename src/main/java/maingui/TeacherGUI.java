/**
 * Tanari szerepkorrel bejelentkezett felhasznaloi GUI
 */

package maingui;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import coursemanagement.*;
import usermanagement.ChangePasswordGUI;

import javax.swing.*;
import java.awt.*;
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

        //az orarend generalasakor felugro ablak
        generateTimetableButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    switch (comboBoxGenerateTimetable.getSelectedIndex()) { //JComboBox-bol lehet kivalasztani, hogy egy tanszek vagy egy eloado alapjan kivan a felhasznalo orarendet generalni
                        case 0:
                            String keresettTanszek = generateTextField.getText();
                            JDialog tanszekDialog = new TimeTable(courseDatabaseManager.searchDatabaseColumn("tanszek", keresettTanszek));
                            break;
                        case 1:
                            String keresettEloado = generateTextField.getText();
                            JDialog eloadoDialog = new TimeTable(courseDatabaseManager.searchDatabaseColumn("eloado", keresettEloado));
                            break;
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
        searchCoursesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                courseList = courseDatabaseManager.loadAllData();
                try {
                    List<Course> list = CourseController.searchCourse(courseList, comboBoxSearchCourse.getSelectedIndex(), searchTextField.getText());
                    JDialog dialog = new CourseTable(list);
                    dialog.setVisible(true);
                } catch (NullPointerException e1) {
                    JOptionPane.showMessageDialog(null, "Nincs beolvasva fajl!", "Beolvasasi hiba", JOptionPane.ERROR_MESSAGE);
                }
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
        panel.setLayout(new GridLayoutManager(7, 4, new Insets(0, 0, 0, 0), -1, -1));
        final Spacer spacer1 = new Spacer();
        panel.add(spacer1, new GridConstraints(5, 0, 1, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, new Dimension(-1, 10), null, null, 0, false));
        listCoursesButton = new JButton();
        listCoursesButton.setText("Listázás");
        panel.add(listCoursesButton, new GridConstraints(1, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        searchCoursesButton = new JButton();
        searchCoursesButton.setText("Keresés");
        panel.add(searchCoursesButton, new GridConstraints(2, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        generateTimetableButton = new JButton();
        generateTimetableButton.setText("Generálás");
        panel.add(generateTimetableButton, new GridConstraints(3, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        newCourseButton = new JButton();
        newCourseButton.setText("Új óra");
        panel.add(newCourseButton, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Új óra hozzáadása");
        panel.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Minden óra listázása");
        panel.add(label2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Óra keresés:");
        panel.add(label3, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        comboBoxSearchCourse = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("felev");
        defaultComboBoxModel1.addElement("kar");
        defaultComboBoxModel1.addElement("szki");
        defaultComboBoxModel1.addElement("ti");
        defaultComboBoxModel1.addElement("tantargy");
        defaultComboBoxModel1.addElement("tanszek");
        defaultComboBoxModel1.addElement("eloado");
        defaultComboBoxModel1.addElement("csoport");
        defaultComboBoxModel1.addElement("fo");
        defaultComboBoxModel1.addElement("kezdes");
        defaultComboBoxModel1.addElement("hossz");
        defaultComboBoxModel1.addElement("terem");
        defaultComboBoxModel1.addElement("nap");
        defaultComboBoxModel1.addElement("tipus");
        comboBoxSearchCourse.setModel(defaultComboBoxModel1);
        panel.add(comboBoxSearchCourse, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        searchTextField = new JTextField();
        panel.add(searchTextField, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("Órarend generálása");
        panel.add(label4, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        comboBoxGenerateTimetable = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel2 = new DefaultComboBoxModel();
        defaultComboBoxModel2.addElement("Tanszék");
        defaultComboBoxModel2.addElement("Előadó");
        comboBoxGenerateTimetable.setModel(defaultComboBoxModel2);
        panel.add(comboBoxGenerateTimetable, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        generateTextField = new JTextField();
        panel.add(generateTextField, new GridConstraints(3, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        cancelButton = new JButton();
        cancelButton.setText("Kilépés");
        panel.add(cancelButton, new GridConstraints(6, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setText("Saját jelszó módosítása:");
        panel.add(label5, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        changePasswordButton = new JButton();
        changePasswordButton.setText("Módosítás");
        panel.add(changePasswordButton, new GridConstraints(4, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel;
    }
}
