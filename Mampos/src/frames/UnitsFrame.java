package frames;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import mampos.Utils;



public class UnitsFrame extends javax.swing.JFrame implements TableModelListener {

    private final DefaultTableModel model;
    private TableColumn tColumn;
    private final Object[] columns = new Object[]{"Descripción",
                                            "Unidad de longitud",
                                            "Unidad de fuerza",
                                            "Unidad de temperatura",
                                            "Etiqueta de unidad",
                                            "Decimales"};
    
    public UnitsFrame() {
        initComponents();
        model = (DefaultTableModel) tablaDeUnidades.getModel();
        model.addTableModelListener(this);
        Utils.setFrameInMiddle(this, 500);
        updateTable();
    }
  
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scrollDeTabla = new javax.swing.JScrollPane();
        tablaDeUnidades = new javax.swing.JTable(){
            public boolean isCellEditable(int row, int column) {
                if(row == 0) return false;
                if(column == 0 || column == 4) return false;
                if(row == 1 && (column == 2 || column == 3)) return false;
                if(row == 2 && (column == 2 || column == 3)) return false;
                if(row == 3 && (column == 2 || column == 3)) return false;
                return true;
            };
        };

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Selector de unidades");
        setAlwaysOnTop(true);
        setBackground(java.awt.SystemColor.activeCaption);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        tablaDeUnidades.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"Unidades globales", null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Descripción", "Unidad de longitud", "Unidad de fuerza", "Unidad de temperatura", "Etiqueta de unidad", "Decimales"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, true, true, true, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaDeUnidades.setShowGrid(true);
        tablaDeUnidades.getTableHeader().setReorderingAllowed(false);
        scrollDeTabla.setViewportView(tablaDeUnidades);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollDeTabla, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollDeTabla, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane scrollDeTabla;
    private javax.swing.JTable tablaDeUnidades;
    // End of variables declaration//GEN-END:variables

    @Override
    public void tableChanged(TableModelEvent e) {
  } 
    public void updateTable(){
        
        Object[][] data = new Object[50][6]; 
        data[0][0] = "Unidades del mundo";
        data[1][0] = "Distancia";
        data[2][0] = "Área";
        data[3][0] = "Ángulo";
        
        model.setDataVector(data, columns);

        tablaDeUnidades.getColumnModel().getColumn(0).setCellRenderer(new ColorCellRenderer());
        tablaDeUnidades.getColumnModel().getColumn(1).setCellRenderer(new ColorCellRenderer());
        tablaDeUnidades.getColumnModel().getColumn(2).setCellRenderer(new ColorCellRenderer());
        tablaDeUnidades.getColumnModel().getColumn(3).setCellRenderer(new ColorCellRenderer());
        tablaDeUnidades.getColumnModel().getColumn(4).setCellRenderer(new ColorCellRenderer());
        tablaDeUnidades.getColumnModel().getColumn(5).setCellRenderer(new ColorCellRenderer());

    }
}

class ColorCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            
            cell.setBackground(Color.WHITE);
            cell.setForeground(Color.BLACK);
            if(row == 0 || row == 4){
                cell.setBackground(Color.GRAY);
                cell.setForeground(Color.WHITE);
            }
            if(column == 0 || column == 4){
                cell.setBackground(Color.GRAY);
                cell.setForeground(Color.WHITE);
            }
            if(column == 2){
                if(row == 1 || row == 2 || row == 3){
                    cell.setBackground(Color.GRAY);
                    cell.setForeground(Color.WHITE);
                }
            }
            if(column == 3){
                if(row == 1 || row == 2 || row == 3){
                    cell.setBackground(Color.GRAY);
                    cell.setForeground(Color.WHITE);
                }
            }
           
            
            return cell;
        }
    }