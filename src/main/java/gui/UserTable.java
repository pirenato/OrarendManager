/**
 * Tablazat amiben az adatbazisbol kiolvasott felhasznalok adatai szerepelnek.
 * Egy cella modositasaval egyidoben az adatbazisban is modosul az adat.
 */
package gui;

import usermanagement.UserDatabaseManager;
import usermanagement.Users;
import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class UserTable extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JPanel tablePanel;
    private JTable table;

    public UserTable(List<Users> userlist) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        //JTable fejlecek hozzaadasa
        DefaultTableModel model = new DefaultTableModel(new Object[]{"Teljes név", "Felhasználónév", "Email", "Típus"},0);
        table.setAutoCreateRowSorter(true);
        table.setFillsViewportHeight(true);
        table.setPreferredScrollableViewportSize(new Dimension(550, 200));


        //tablazat feltoltese
        for (int i = 0; i < userlist.size(); i++) {
            model.addRow(new Object[]{userlist.get(i).getFullName(), userlist.get(i).getUsername(), userlist.get(i).getEmail(), userlist.get(i).getRole()});
        }
        table.setModel(model);

        //a szerepkor oszlophoz JComboBox beallitasa
        setupRoleColumn(table, table.getColumnModel().getColumn(3));

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });


        /**
         * Modositott cellaertek adatbazisba irasa
         */
        model.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if (e.getType() == TableModelEvent.UPDATE) {
                    int column = e.getColumn(); //elmenti a modositott cella indexet
                    DefaultTableModel defaultTableModel = (DefaultTableModel) table.getModel();
                    String updatedCellData = String.valueOf(defaultTableModel.getValueAt(table.getSelectedRow(), table.getSelectedColumn())); //a cellaba beirt uj ertek
                    String username = defaultTableModel.getValueAt(table.getSelectedRow(), 1).toString(); //a kivalasztott sor 1. oszlopa az id / username, ami alapján megkeresi az adatbazisban a modositott rekordot

                    UserDatabaseManager.update(username, updatedCellData, column); //a meghivott metodus modositja az adatbazisban a rekordot
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

        setMinimumSize(new Dimension(550, 200));
        pack();

    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    /**
     * Beallitja a szerepkor oszlopban a JComboBox-ot.
     * Csak a JComboBox-ban szereplo ertekekre lehet modositani
     * @param table
     * @param roleColumn
     */
    private void setupRoleColumn(JTable table, TableColumn roleColumn) {
        JComboBox comboBox = new JComboBox();
        comboBox.addItem("admin");
        comboBox.addItem("tanar");
        comboBox.addItem("hallgato");
        roleColumn.setCellEditor(new DefaultCellEditor(comboBox));

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setToolTipText("szerepkor");
        roleColumn.setCellRenderer(renderer);
    }

    private Class getColumnClass(int c) {
        return table.getValueAt(0, c).getClass();
    }
}
