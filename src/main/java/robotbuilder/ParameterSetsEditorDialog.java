
package robotbuilder;

import java.awt.Frame;

import java.util.List;
import java.util.Optional;
import java.util.Vector;

import javax.swing.JDialog;

import lombok.NonNull;

import robotbuilder.data.RobotComponent;
import robotbuilder.data.properties.ParameterSet;
import robotbuilder.data.properties.ParameterSetProperty;
import robotbuilder.data.properties.ParametersProperty;
import robotbuilder.data.properties.ValuedParameterDescriptor;
import robotbuilder.utils.UniqueList;

/**
 *
 * @author Sam Carlberg
 */
public class ParameterSetsEditorDialog extends JDialog {

    private RobotComponent command;
    private ParameterSetProperty prop;
    private List<ParameterSet> sets;
    private ParametersProperty paramProp;

    /**
     * Creates new form ParameterSetsEditorDialog
     */
    public ParameterSetsEditorDialog(@NonNull RobotComponent command, Frame parent, boolean modal) {
        super(parent, modal);
        this.command = command;
        this.paramProp = (ParametersProperty) command.getProperty("Parameters");
        this.prop = (ParameterSetProperty) command.getProperty("Parameter presets");
        this.sets = prop.getValue();
        initComponents();
        setsTable.generateFrom(command);
    }

    public void save() {
        Vector<Vector<Object>> tableData = setsTable.getModel().getDataVector();
        sets.clear();
        tableData.stream()
                .map(this::generateSet)
                .forEach(sets::add);
    }

    private ParameterSet generateSet(Vector<Object> row) {
        String setName = (String) row.get(0);
        UniqueList<ValuedParameterDescriptor> data = new UniqueList<>(row.size() - 1);
        for (int colNum = 1; colNum < row.size(); colNum++) {
            String paramName = setsTable.getColumnName(colNum);
            String type = paramProp.getValue().get(colNum - 1).getType();
            String value = (String) row.get(colNum);
            data.add(new ValuedParameterDescriptor(paramName, type, value));
        }
        return new ParameterSet(setName, data);
    }

    public List<ParameterSet> showAndGet() {
        setVisible(true);
        return sets;
    }

    private void addSet() {
        Optional<ParameterSet> p = ParameterSet.from("", command);
        if (!p.isPresent()) {
            return;
        }
        ParameterSet set = p.get();
        setsTable.getModel().addRow(set.toArray());
        setsTable.editName(set);
    }

    private void saveAndClose() {
        save();
        dispose();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        saveButton = new javax.swing.JButton();
        addButton = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        setsTable = new robotbuilder.ParameterSetsTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        saveButton.setText("Save and close");
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

        addButton.setText("Add parameter set");
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });

        setsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title"
            }
        ));
        setsTable.setRowHeight(20);
        setsTable.setRowMargin(2);
        jScrollPane3.setViewportView(setsTable);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 87, Short.MAX_VALUE)
                        .addComponent(addButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(saveButton))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 297, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(saveButton)
                    .addComponent(addButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        addSet();
    }//GEN-LAST:event_addButtonActionPerformed

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        saveAndClose();
    }//GEN-LAST:event_saveButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JButton saveButton;
    private robotbuilder.ParameterSetsTable setsTable;
    // End of variables declaration//GEN-END:variables
}
