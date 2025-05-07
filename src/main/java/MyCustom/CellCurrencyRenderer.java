/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MyCustom;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.text.NumberFormat;

public class CellCurrencyRenderer extends DefaultTableCellRenderer {
    private final NumberFormat currencyFormat;

    public CellCurrencyRenderer(NumberFormat currencyFormat) {
        this.currencyFormat = currencyFormat;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus,
                                                   int row, int column) {

        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (value instanceof Number) {
            setText(currencyFormat.format(value));
        }
        setHorizontalAlignment(SwingConstants.RIGHT);
        return c;
    }
}

