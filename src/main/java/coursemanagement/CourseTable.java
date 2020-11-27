/**
 * Admin es tanari jogosultsaggal rendelkezo felhasznaloknak megjeleno, orakat tartalmazo tablazat.
 * Ebben lehetseges adatot modositani, uj orakat hozzaadni, orakat torolni.
 */

package coursemanagement;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class CourseTable extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTable table;
    private JPanel tablePanel;
    private JScrollPane jscrollpane;
    private JButton torlesButton;


    public CourseTable(List<Course> list) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setTitle("Tárgyak listázása");

        //tablazat fejlecenek beallitasa
        DefaultTableModel model = new DefaultTableModel(new Object[]{"ID", "Felev", "Kar", "Szki", "Ti", "Targynev", "Tanszek", "Eloado", "Csoport", "Fo", "Kezdes", "Hossz", "Terem", "Nap", "Tipus", "Torles"}, 0) {
            @Override
            //a torles oszlopban megjeleno JCheckBox beallitasa
            public Class getColumnClass(int columnIndex) {
                if (columnIndex == 0 || columnIndex == 1 || columnIndex == 9 || columnIndex == 10 || columnIndex == 11)
                    return Integer.class;
                if (columnIndex == 15)
                    return Boolean.class;
                return String.class;
            }
        };

        table.setAutoCreateRowSorter(true);
        table.setFillsViewportHeight(true);
        table.setPreferredScrollableViewportSize(new Dimension(800, 200));

        //tabla feltoltese az adatbazisbol
        for (int i = 0; i < list.size(); i++) {
            model.addRow(new Object[]{list.get(i).getId(), list.get(i).getFelev(), list.get(i).getKar(), list.get(i).getSzki(), list.get(i).getTi(),
                    list.get(i).getTantargy(), list.get(i).getTanszek(), list.get(i).getEloado(), list.get(i).getCsoport(),
                    list.get(i).getFo(), list.get(i).getKezdes(), list.get(i).getHossz(), list.get(i).getTerem(), list.get(i).getNap(), list.get(i).getTipus(), Boolean.FALSE
            });
        }
        table.setModel(model);

        /**
         * Adatmodositas, a kilistazott targyak kozul megvizsgalja melyik sor es melyik oszlop van kivalasztva,
         * majd az adatbazisba modositja az erintett rekordot
         */
        model.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if (e.getType() == TableModelEvent.UPDATE) {
                    int column = e.getColumn();

                    DefaultTableModel defaultTableModel = (DefaultTableModel) table.getModel();

                    String data = String.valueOf(defaultTableModel.getValueAt(table.getSelectedRow(), table.getSelectedColumn())); //a cellaba beirt uj ertek
                    String stringId = defaultTableModel.getValueAt(table.getSelectedRow(), 0).toString(); //a kivalasztott sor 1. oszlopa az id, ami alapjan megkeresi az adatbazisban a modositando rekordot
                    long id = Long.parseLong(stringId); //a Course objektum id adattagja long tipusu, konverzio szukseges

                    CourseDatabaseManager.update(id, data, column); //ez a metodus vegzi el az adatbazisban az adatmodositast
                }

            }
        });


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


        //oszlopszelesseg beallitasa
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


        //kijelolt sorok torlese
        torlesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < model.getRowCount(); i++) {
                    Boolean isChecked = (Boolean) model.getValueAt(i, 15); //15. oszlop a checkbox
                    String stringId = model.getValueAt(i, 0).toString(); //a kijelolt oszlopnak tarolja az azonositojat, ez alapjan torli az adatbazisbol a rekordot

                    if (isChecked) {
                        long id = Long.parseLong(stringId); //a Course objektum id adattagja long tipusu, konverzio szukseges
                        CourseDatabaseManager.delete(id); //torli a kijelolt targyat az adatbazisbol
                        model.removeRow(i); //torli a kijelolt targyat a megjelenitett JTable-bol
                        i--; //torlesnel a sorok szama is csokken egyel, ezert a szamlalot is csokkenteni kell
                    }
                }
            }
        });

        setMinimumSize(new Dimension(1000, 400));
        pack();
    }


    private void onCancel() {
        dispose();
    }

}
