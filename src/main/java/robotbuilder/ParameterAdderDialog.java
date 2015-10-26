
package robotbuilder;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import static robotbuilder.ParameterEditorTable.INVALID_COLOR;
import static robotbuilder.ParameterEditorTable.SELECTED_COLOR;
import static robotbuilder.ParameterEditorTable.SELECTED_INVALID_COLOR;
import robotbuilder.data.RobotComponent;
import robotbuilder.data.properties.ParametersProperty;
import robotbuilder.data.properties.ParameterDescriptor;

/**
 * Dialog for adding command parameters.
 *
 * @author Sam Carlberg
 */
public class ParameterAdderDialog extends CenteredDialog {

    /**
     * The parameters property being edited.
     */
    private final ParametersProperty parametersProperty;

    /**
     * Convenience list for changing parameters.
     */
    private final List<ParameterDescriptor> parameterList;

    /**
     * ComboBox for selecting parameter types.
     */
    private final JComboBox<String> typeBox = new JComboBox<>(ParameterDescriptor.SUPPORTED_TYPES);

    private DefaultCellEditor currentCellEditor;

    public ParameterAdderDialog(RobotComponent command, JFrame owner, boolean modal) {
        super(owner, "Add parameters");
        this.parametersProperty = (ParametersProperty) command.getProperty("Parameters");
        initComponents();
        setBackground(Color.WHITE);
        setForeground(Color.WHITE);
        parameterTable.setShowHorizontalLines(true);
        parameterTable.setShowVerticalLines(true);
        parameterTable.setRowHeight(25);
        parameterTable.setBackground(new Color(240, 240, 240));
        parameterTable.setGridColor(Color.BLACK);
        parameterTable.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_BACK_SPACE
                        || e.getKeyChar() == KeyEvent.VK_DELETE) {
                    deleteSelectedRows();
                }
            }

        });
        parameterList = (List<ParameterDescriptor>) parametersProperty.getValue();
        parameterList.stream().forEach(p -> getTableModel().addRow(p.toArray()));
    }

    public List<? extends ParameterDescriptor> showAndGetParameters() {
        setVisible(true);
        return getParameters();
    }

    /**
     * Saves the data in the table to the parameters property. This will clear
     * any data that previously existed in the property.
     */
    private void save() {
        Vector<Vector<Object>> dataVector = getTableModel().getDataVector();
        parameterList.clear();
        dataVector.stream().forEach((dataRow) -> {
            String name = (String) dataRow.get(0);
            String type = (String) dataRow.get(1);
            ParameterDescriptor newParam = new ParameterDescriptor(name, type);
            parameterList.add(newParam);
        });
        parametersProperty.setValueAndUpdate(parameterList); // almost certainly redundant
    }

    /**
     * Deletes the selected rows in the table. This does not effect the
     * parameters property.
     */
    private void deleteSelectedRows() {
        int[] rows = parameterTable.getSelectedRows();
        parameterTable.clearSelection();
        currentCellEditor.cancelCellEditing();
        for (int i = rows.length - 1; i >= 0; i--) {
            if (rows[i] > -1) {
                getTableModel().removeRow(rows[i]);
            }
        }
    }

    /**
     * Checks if the given row is valid. A row is valid if the name of the
     * parameter in the row is unique, i.e. no other parameter in the table has
     * the same name.
     *
     * @param row the row to validate
     * @return true if the row is valid, false otherwise
     */
    private boolean isRowValid(int row) {
        String name = (String) ((Vector) getTableModel().getDataVector().get(row)).get(0);
        int count = ((Vector<Vector>) getTableModel().getDataVector())
                .stream()
                .filter(v -> v.get(0).equals(name))
                .map(i -> 1)
                .reduce(0, Integer::sum);
        return count == 1 && parameterForRow(row).isValid();
    }

    public List<? extends ParameterDescriptor> getParameters() {
        return parametersProperty.getValue();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        parameterTable = new ParameterDeclarationTable();
        addButton = new javax.swing.JButton();
        saveButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        parameterTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Type"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        parameterTable.setDragEnabled(true);
        parameterTable.setShowGrid(true);
        parameterTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(parameterTable);

        addButton.setText("Add Parameter");
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });

        saveButton.setText("Save and close");
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(addButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(saveButton)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 265, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addButton)
                    .addComponent(saveButton)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        ParameterDescriptor p = new ParameterDescriptor(); // defaults to "[change me]", "String"
        getTableModel().addRow(p.toArray());
    }//GEN-LAST:event_addButtonActionPerformed

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        save();
        dispose();
    }//GEN-LAST:event_saveButtonActionPerformed

    /**
     * Helper method to get and cast the table model to avoid casting it
     * everywhere it's used.
     */
    private DefaultTableModel getTableModel() {
        return (DefaultTableModel) parameterTable.getModel();
    }

    private ParameterDescriptor parameterForRow(int row) {
        Vector<Object> rowData = (Vector) getTableModel().getDataVector().get(row);
        String name = (String) rowData.get(0);
        ParameterDescriptor p = parametersProperty.getParameterByName(name);
        if (p == null) {
            p = new ParameterDescriptor(name, (String) rowData.get(1));
        }
        return p;
    }

    private class ParameterDeclarationTable extends JTable {

        public ParameterDeclarationTable() {
            setTransferHandler(new TableRowTransferHandler(this));
        }

        @Override
        public TableCellEditor getCellEditor(int row, int column) {
            DefaultCellEditor editor;
            if (column == 0) {
                editor = new DefaultCellEditor(new JTextField());
            } else if (column == 1) {
                editor = new DefaultCellEditor(typeBox);
            } else {
                editor = (DefaultCellEditor) super.getCellEditor(row, column);
            }
            editor.setClickCountToStart(2);
            currentCellEditor = editor;
            return editor;
        }

        @Override
        public void setValueAt(Object aValue, int row, int column) {
            if (0 <= row && row < this.getRowCount()) {
                super.setValueAt(aValue, row, column);
            }
        }

        @Override
        @SuppressWarnings("Convert2Lambda")
        public TableCellRenderer getCellRenderer(int row, int column) {
            return new ParameterTableRenderer(r -> isRowValid(r));
        }

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable parameterTable;
    private javax.swing.JButton saveButton;
    // End of variables declaration//GEN-END:variables
}
