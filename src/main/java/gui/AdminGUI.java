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
    private JButton readFileButton;
    private JButton cancelButton;
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
                        orarendDoc = CourseController.docToArrayList(CourseController.readDoc(filePath)); //a fajlbol elkesziti a String-eket tartalmazo ArrayList-et
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    CourseController.removeUnnecessaryLines(orarendDoc); //az irrelevans (nem tantargyakat tartalmazo) sorokat torli az ArrayList-bol
                    subjects = CourseController.stringListToCourseList(orarendDoc); //a String-kent tarolt targyokbol Course objektumokat keszit
                    JOptionPane.showMessageDialog(null, "Fajl beolvasva!\n " + filePath, "Beolvasas", JOptionPane.PLAIN_MESSAGE);

                    CourseDatabaseManager.createDatabase(subjects); //a kesz Course objektumokat tartalmazo ArrayList-et adatbazisba irja
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
                    //List<Course> list = courseDatabaseManager.searchDatabaseColumn(comboBoxKeres.getSelectedItem().toString(), textFieldKeres.getText());

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

        /**
         * Program bezarasa
         */
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
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
                            tanszekDialog.setVisible(true);
                        case 1:
                            String keresettEloado = generateTimetableTextField.getText();
                            JDialog eloadoDialog = new TimeTable(courseDatabaseManager.searchDatabaseColumn("eloado", keresettEloado));
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

}