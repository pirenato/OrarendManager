/**
 * Osztály az összes tantárgy listázására
 */

package gui;


import all.Course;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class CourseTableStudent extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton backButton;
    private JTable table;
    private JPanel tablePanel;
    private JScrollPane jscrollpane;


    public CourseTableStudent(List<Course> list) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        DefaultTableModel model = new DefaultTableModel(new Object[]{"ID", "Félév", "Kar", "Szki", "Ti", "Tárgynév", "Tanszék", "Előadó", "Csoport", "Fő", "Kezdés", "Hossz", "Terem", "Nap", "Típus"}, 0)
        {
            public boolean isCellEditable(int rowIndex, int mColIndex) {
                return false;
            }
        };

        table.setAutoCreateRowSorter(true);
        table.setFillsViewportHeight(true);
        table.setPreferredScrollableViewportSize(new Dimension(800, 200));

        //tábla feltöltése az adatbázisból
        for (int i = 0; i < list.size(); i++) {
            model.addRow(new Object[]{list.get(i).getId(), list.get(i).getFelev(), list.get(i).getKar(), list.get(i).getSzki(), list.get(i).getTi(),
                    list.get(i).getTantargy(), list.get(i).getTanszek(), list.get(i).getEloado(), list.get(i).getCsoport(),
                    list.get(i).getFo(), list.get(i).getKezdes(), list.get(i).getHossz(), list.get(i).getTerem(), list.get(i).getNap(), list.get(i).getTipus()
            });
        }

        table.setModel(model);


        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);


        //oszlopszélesség beállítása
        TableColumn tableColumn = null;
        for (int i = 0; i < table.getColumnCount(); i++) {
            tableColumn = table.getColumnModel().getColumn(i);
            if (i == 5)
                tableColumn.setMinWidth(250);
            else if (i == 6 || i == 7)
                tableColumn.setMinWidth(80);
            else
                tableColumn.setMinWidth(40);
        }



        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onCancel();

            }
        });

        setMinimumSize(new Dimension(1000, 400));
        pack();
    }

    private void onCancel() {
        dispose();
    }

}
