/**
 * Az admin felülethez tartozó GUI
 */

package all;

import usermanagement.UserDatabaseManager;
import usermanagement.Users;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.List;


public class AdminGUI extends JFrame {
    private JPanel panel;
    private JButton beolvasButton;
    private JButton kilepesButton;
    private JComboBox comboBoxKeres;
    private JTextField textFieldKeres;
    private JButton keresButton;
    private JButton listazButton;
    private JButton ujOraButton;
    private JButton generalButton;
    private JTextField textFieldGeneral;
    private JComboBox comboBox1;
    private JTextField textFieldUserKeres;
    private JButton userKeresButton;
    private JButton userListazButton;
    private File inputFile;
    private String filePath;
    private List<Course> subjects;
    private CourseController courseController;
    private List<Users> userList;
    private List<Course> courseList;


    public AdminGUI() {
        CourseDatabaseManager courseDatabaseManager = new CourseDatabaseManager();
        UserDatabaseManager userDatabaseManager = new UserDatabaseManager();
        userList = userDatabaseManager.loadAllUserData();
        courseList = courseDatabaseManager.loadAllData(); //az adatbázisból az összes tárgy beolvasása

        /**
         * FileDialog az előzetes órarend fájl beolvasására.
         * A fájl beolvasása után automatikusan létrehozza az adatbázist is.
         */
        beolvasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileDialog fileDialog = new FileDialog(new Frame(), " ", FileDialog.LOAD);
                fileDialog.setFile("*.doc");
                fileDialog.setVisible(true);

                if (fileDialog.getFile() != null) {
                    inputFile = new File(fileDialog.getDirectory(), fileDialog.getFile());
                    filePath = inputFile.toString();
                    List<String> orarendDoc = null; //
                    try {
                        orarendDoc = CourseController.docToArrayList(CourseController.readDoc(filePath));
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    CourseController.removeUnnecessaryLines(orarendDoc);
                    subjects = CourseController.stringListToCourseList(orarendDoc);
                    JOptionPane.showMessageDialog(null, "Fájl beolvasva!\n " + filePath, "Beolvasás", JOptionPane.PLAIN_MESSAGE);

                    CourseController.createDatabase(subjects);
                }
            }
        });


        /**
         * Összes óra megjelenítése egy táblázatban
         */
        listazButton.addActionListener(new ActionListener() {
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
         * A kiválasztott oszlop alapján keresés a tárgyakban
         */
        keresButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Course> list = null;
                courseList = courseDatabaseManager.loadAllData();
                try {
                    switch (comboBoxKeres.getSelectedIndex()) {
                        case 0:
                            list = CourseController.searchList(courseList, Course::getFelev, textFieldKeres.getText());
                            break;
                        case 1:
                            list = CourseController.searchList(courseList, Course::getKar, textFieldKeres.getText());
                            break;
                        case 2:
                            list = CourseController.searchList(courseList, Course::getSzki, textFieldKeres.getText());
                            break;
                        case 3:
                            list = CourseController.searchList(courseList, Course::getTi, textFieldKeres.getText());
                            break;
                        case 4:
                            list = CourseController.searchList(courseList, Course::getTantargy, textFieldKeres.getText());
                            break;
                        case 5:
                            list = CourseController.searchList(courseList, Course::getTanszek, textFieldKeres.getText());
                            break;
                        case 6:
                            list = CourseController.searchList(courseList, Course::getEloado, textFieldKeres.getText());
                            break;
                        case 7:
                            list = CourseController.searchList(courseList, Course::getCsoport, textFieldKeres.getText());
                            break;
                        case 8:
                            list = CourseController.searchList(courseList, Course::getFo, textFieldKeres.getText());
                            break;
                        case 9:
                            list = CourseController.searchList(courseList, Course::getKezdes, textFieldKeres.getText());
                            break;
                        case 10:
                            list = CourseController.searchList(courseList, Course::getHossz, textFieldKeres.getText());
                            break;
                        case 11:
                            list = CourseController.searchList(courseList, Course::getTerem, textFieldKeres.getText());
                            break;
                        case 12:
                            list = CourseController.searchList(courseList, Course::getNap, textFieldKeres.getText());
                            break;
                        case 13:
                            list = CourseController.searchList(courseList, Course::getTipus, textFieldKeres.getText());
                            break;
                    }

                    JDialog dialog = new CourseTable(list);
                    dialog.setVisible(true);
                } catch (NullPointerException e1) {
                    JOptionPane.showMessageDialog(null, "Nincs beolvasva fájl!", "Beolvasási hiba", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        /**
         * Új óra hozzáadása az adatbázisba.
         * A felugró JDialogba lehet megadni a szükséges adatokat.
         */
        ujOraButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    JDialog dialog = new AddCourse();
                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(null, "Hiba történt", "Error", JOptionPane.ERROR_MESSAGE);
                }

            }
        });

        /**
         * Program bezárása
         */
        kilepesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        //Felhasználók listázása
        userListazButton.addActionListener(new ActionListener() {
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
    }

    /**
     * GUI létrehozása
     */
    public void AdminPage() {
        JFrame frame = new JFrame("GUI");
        frame.setContentPane(new AdminGUI().panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}