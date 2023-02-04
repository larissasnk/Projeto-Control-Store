package com.projeto.gestaoloja.util.tableModel;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class CellRenderer extends DefaultTableCellRenderer {

    Color cor = new Color(176,196,222);
    Color color = new Color(70,130,180);
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        //muda as cores na tabela
        if (row % 2 == 0) {
            setBackground(cor);
        } else {
            setBackground(null);
        }

        if (isSelected) {
            setBackground(color);
        }

        // alinha o conteúdo da tabela no centro
        this.setHorizontalAlignment(CENTER);
        table.getColumnModel().getColumn(0).setMaxWidth(50);

        return this;
    }
}
