/**
 * Az admin felulethez tartozo GUI
 */

package gui;

import all.Course;
import all.CourseController;
import all.CourseDatabaseManager;
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
    private JComboBox comboBoxGeneral;
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
        courseList = courseDatabaseManager.loadAllData(); //az adatbazisbol az összes targy beolvasasa

        /**
         * FileDialog az elozetes orarend fajl beolvasasara.
         * A fajl beolvasasa utan automatikusan letrehozza az adatbazist is.
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
                    JOptionPane.showMessageDialog(null, "Fajl beolvasva!\n " + filePath, "Beolvasas", JOptionPane.PLAIN_MESSAGE);

                    CourseDatabaseManager.createDatabase(subjects);
                }
            }
        });


        /**
         * Osszes ora megjelenitese egy tablazatban
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
         * A kivalasztott oszlop alapjan kereses a targyakban
         */
        keresButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                courseList = courseDatabaseManager.loadAllData();
                try {
                    List<Course> list = CourseController.searchCourse(courseList, comboBoxKeres.getSelectedIndex(), textFieldKeres.getText());

                    JDialog dialog = new CourseTable(list);
                    dialog.setVisible(true);
                } catch (NullPointerException e1) {
                    JOptionPane.showMessageDialog(null, "Nincs beolvasva fajl!", "Beolvasasi hiba", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        /**
         * uj ora hozzaadasa az adatbazisba.
         * A felugro JDialogba lehet megadni a szukseges adatokat.
         */
        ujOraButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    JDialog dialog = new AddCourse();
                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(null, "Hiba tortent", "Error", JOptionPane.ERROR_MESSAGE);
                }

            }
        });

        /**
         * Program bezarasa
         */
        kilepesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logout();
                //System.exit(0);
            }
        });

        //Felhasznalok listazasa
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


        generalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    switch (comboBoxGeneral.getSelectedIndex()) {
                        case 0:
                            String keresettTanszek = textFieldGeneral.getText();
                            JDialog tanszekDialog = new TimeTable(courseDatabaseManager.search("tanszek", keresettTanszek));
                            tanszekDialog.setVisible(true);
                        case 1:
                            String keresettEloado = textFieldGeneral.getText();
                            JDialog eloadoDialog = new TimeTable(courseDatabaseManager.search("eloado", keresettEloado));
                            eloadoDialog.setVisible(true);
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
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

    public void logout() {
        this.setVisible(false);
        dispose();
    }
}