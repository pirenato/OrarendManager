/**
 * Az admin felulethez tartozo GUI
 */

package maingui;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import coursemanagement.*;
import usermanagement.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class AdminGUI extends JFrame {
    private JPanel panel;
    private JButton readFileButton;
    private JButton changePasswordButton;
    private JComboBox comboBoxSearchCourses;
    private JTextField searchCoursesTextField;
    private JButton searchCoursesButton;
    private JButton listCoursesButton;
    private JButton addCourseButton;
    private JButton generateTimetableButton;
    private JTextField generateTimetableTextField;
    private JComboBox comboBoxGenerate;
    private JTextField searchUsersTextField;
    private JButton searchUsersButton;
    private JButton listUsersButton;
    private JButton exitButton;
    private File inputFile;
    private String filePath;
    private List<Course> subjects;
    private CourseController courseController;
    private List<Users> userList;
    private List<Course> courseList;


    public AdminGUI() {
        CourseDatabaseManager courseDatabaseManager = new CourseDatabaseManager();
        UserDatabaseManager userDatabaseManager = new UserDatabaseManager();
        userList = userDatabaseManager.loadAllUserData(); //adatbazisbol az osszes felhasznalo beolvasasa
        courseList = courseDatabaseManager.loadAllData(); //adatbazisbol az osszes targy beolvasasa

        /**
         * FileDialog az elozetes orarend fajl beolvasasara.
         * A fajl beolvasasa utan automatikusan letrehozza az adatbazist is.
         */
        readFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileDialog fileDialog = new FileDialog(new Frame(), " ", FileDialog.LOAD); //fajl kivalasztasara felugro ablak
                fileDialog.setFile("*.doc");
                fileDialog.setVisible(true);

                if (fileDialog.getFile() != null) {
                    inputFile = new File(fileDialog.getDirectory(), fileDialog.getFile()); //file beolvasasa
                    filePath = inputFile.toString();
                    List<String> orarendDoc = null; //
                    try {
                        orarendDoc = CourseController.docToArrayList(CourseController.readDocFile(filePath)); //a fajlbol elkesziti a String-eket tartalmazo ArrayList-et
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    subjects = CourseController.stringListToCourseList(orarendDoc); //a String-kent tarolt targyokbol Course objektumokat keszit
                    JOptionPane.showMessageDialog(null, "Fajl beolvasva!\n " + filePath, "Beolvasas", JOptionPane.PLAIN_MESSAGE);

                    CourseDatabaseManager.initializeCourseTable(subjects); //a kesz Course objektumokat tartalmazo ArrayList-et adatbazisba irja
                }
            }
        });


        /**
         * Osszes ora megjelenitese egy tablazatban
         */
        listCoursesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    courseList = courseDatabaseManager.loadAllData();
                    JDialog dialog = new CourseTable(courseList);
                    dialog.setVisible(true);
                } catch (NullPointerException e1) {
                    JOptionPane.showMessageDialog(null, "Nincs beolvasva adat!", "Error", JOptionPane.ERROR_MESSAGE);
                    e1.printStackTrace();
                }

            }
        });

        /**
         * A kivalasztott oszlop alapjan kereses a targyakban
         */
        searchCoursesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                courseList = courseDatabaseManager.loadAllData();
                try {
                    List<Course> list = CourseController.searchCourse(courseList, comboBoxSearchCourses.getSelectedIndex(), searchCoursesTextField.getText());

                    JDialog dialog = new CourseTable(list);
                    dialog.setVisible(true);
                } catch (NullPointerException e1) {
                    JOptionPane.showMessageDialog(null, "Nincs beolvasva fajl!", "Beolvasasi hiba", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        /**
         * uj ora hozzaadasa az adatbazisba.
         * A felugro JDialogba lehet megadni az uj ora adatait.
         */
        addCourseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    JDialog dialog = new AddCourseGUI();
                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(null, "Hiba tortent", "Error", JOptionPane.ERROR_MESSAGE);
                }

            }
        });


        //Felhasznalok listazasa
        listUsersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    userList = userDatabaseManager.loadAllUserData();
                    JDialog dialog = new UserTable(userList);
                    dialog.setVisible(true);
                } catch (NullPointerException e1) {
                    JOptionPane.showMessageDialog(null, "Nincs beolvasva adat!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        /**
         * Orarend generalasa, a felhasznalo valaszthatja ki, hogy a beirt tanszek vagy eloado oraibol akar generalni
         */
        generateTimetableButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    switch (comboBoxGenerate.getSelectedIndex()) {
                        case 0:
                            String keresettTanszek = generateTimetableTextField.getText();
                            JDialog tanszekDialog = new TimeTable(courseDatabaseManager.searchDatabaseColumn("tanszek", keresettTanszek));
                            break;
                        case 1:
                            String keresettEloado = generateTimetableTextField.getText();
                            JDialog eloadoDialog = new TimeTable(courseDatabaseManager.searchDatabaseColumn("eloado", keresettEloado));
                            break;
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });

        searchUsersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    List<Users> users = new ArrayList<>();
                    users.add(UserDatabaseManager.read(searchUsersTextField.getText()));
                    JDialog userDialog = new UserTable(users);
                    userDialog.setVisible(true);
                } catch (NullPointerException e1) {
                    JOptionPane.showMessageDialog(null, "Nincs ilyen felhasznalo az adatbazisban", "Nem letezo felhasznalo", JOptionPane.WARNING_MESSAGE);
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
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    /**
     * GUI letrehozasa
     */
    public void AdminPage() {
        JFrame frame = new JFrame("Admin felulet");
        frame.setContentPane(new AdminGUI().panel);
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
        panel.setLayout(new GridLayoutManager(20, 5, new Insets(0, 0, 0, 0), -1, -1));
        final JLabel label1 = new JLabel();
        label1.setText("Órarend fájl beolvasása: ");
        panel.add(label1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        changePasswordButton = new JButton();
        changePasswordButton.setText("Módosít");
        panel.add(changePasswordButton, new GridConstraints(18, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        readFileButton = new JButton();
        readFileButton.setText("Beolvas");
        panel.add(readFileButton, new GridConstraints(1, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        searchCoursesButton = new JButton();
        searchCoursesButton.setText("Keres");
        panel.add(searchCoursesButton, new GridConstraints(8, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        listCoursesButton = new JButton();
        listCoursesButton.setText("Listáz");
        panel.add(listCoursesButton, new GridConstraints(5, 4, 2, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Órák listázása:");
        panel.add(label2, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        addCourseButton = new JButton();
        addCourseButton.setText("Új óra");
        panel.add(addCourseButton, new GridConstraints(3, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Új óra hozzáadása");
        panel.add(label3, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel.add(spacer1, new GridConstraints(7, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, new Dimension(-1, 10), null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        panel.add(spacer2, new GridConstraints(9, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, new Dimension(-1, 10), null, null, 0, false));
        generateTimetableButton = new JButton();
        generateTimetableButton.setText("General");
        panel.add(generateTimetableButton, new GridConstraints(10, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        generateTimetableTextField = new JTextField();
        panel.add(generateTimetableTextField, new GridConstraints(10, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        searchCoursesTextField = new JTextField();
        panel.add(searchCoursesTextField, new GridConstraints(8, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final Spacer spacer3 = new Spacer();
        panel.add(spacer3, new GridConstraints(4, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, new Dimension(-1, 10), null, null, 0, false));
        final Spacer spacer4 = new Spacer();
        panel.add(spacer4, new GridConstraints(2, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, new Dimension(-1, 10), null, null, 0, false));
        comboBoxGenerate = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("Tanszék");
        defaultComboBoxModel1.addElement("Előadó");
        comboBoxGenerate.setModel(defaultComboBoxModel1);
        panel.add(comboBoxGenerate, new GridConstraints(10, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        comboBoxSearchCourses = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel2 = new DefaultComboBoxModel();
        defaultComboBoxModel2.addElement("felev");
        defaultComboBoxModel2.addElement("kar");
        defaultComboBoxModel2.addElement("szki");
        defaultComboBoxModel2.addElement("ti");
        defaultComboBoxModel2.addElement("tantargy");
        defaultComboBoxModel2.addElement("tanszek");
        defaultComboBoxModel2.addElement("eloado");
        defaultComboBoxModel2.addElement("csoport");
        defaultComboBoxModel2.addElement("fo");
        defaultComboBoxModel2.addElement("kezdes");
        defaultComboBoxModel2.addElement("hossz");
        defaultComboBoxModel2.addElement("terem");
        defaultComboBoxModel2.addElement("nap");
        defaultComboBoxModel2.addElement("tipus");
        comboBoxSearchCourses.setModel(defaultComboBoxModel2);
        panel.add(comboBoxSearchCourses, new GridConstraints(8, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("Óra keresés:");
        panel.add(label4, new GridConstraints(8, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setText("Órarend generálása:");
        panel.add(label5, new GridConstraints(10, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer5 = new Spacer();
        panel.add(spacer5, new GridConstraints(11, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JLabel label6 = new JLabel();
        label6.setText("Felhasználók kezelése");
        panel.add(label6, new GridConstraints(12, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer6 = new Spacer();
        panel.add(spacer6, new GridConstraints(13, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JLabel label7 = new JLabel();
        label7.setText("Keresés:");
        panel.add(label7, new GridConstraints(16, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        searchUsersTextField = new JTextField();
        panel.add(searchUsersTextField, new GridConstraints(16, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        searchUsersButton = new JButton();
        searchUsersButton.setText("Keres");
        panel.add(searchUsersButton, new GridConstraints(16, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label8 = new JLabel();
        label8.setText("Felhasználók listázása");
        panel.add(label8, new GridConstraints(14, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        listUsersButton = new JButton();
        listUsersButton.setText("Listáz");
        panel.add(listUsersButton, new GridConstraints(14, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer7 = new Spacer();
        panel.add(spacer7, new GridConstraints(15, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer8 = new Spacer();
        panel.add(spacer8, new GridConstraints(17, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JLabel label9 = new JLabel();
        label9.setText("Saját jelszó módosítása:");
        panel.add(label9, new GridConstraints(18, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer9 = new Spacer();
        panel.add(spacer9, new GridConstraints(11, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, new Dimension(-1, 30), null, null, 0, false));
        final JLabel label10 = new JLabel();
        label10.setText("Tantárgyak kezelése");
        panel.add(label10, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        exitButton = new JButton();
        exitButton.setText("Kilépés");
        panel.add(exitButton, new GridConstraints(19, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel;
    }

}