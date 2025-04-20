package MyCustom;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

public class TableCustomizer {

    public static void customize(JTable table) {
        // Tùy chỉnh tiêu đề bảng
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBackground(new Color(63, 81, 181));
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(header.getWidth(), 30));

        // Tùy chỉnh dòng dữ liệu
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setRowHeight(28);
        table.setGridColor(new Color(224, 224, 224));
        table.setShowGrid(true);
        table.setDefaultEditor(Object.class, null); // Không cho chỉnh sửa

        // Renderer để đổi màu khi chọn dòng và xen kẽ màu dòng
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {

                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                if (isSelected) {
                    c.setBackground(new Color(144, 202, 249));
                    c.setForeground(Color.BLACK);
                } else {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(245, 245, 245));
                    c.setForeground(Color.BLACK);
                }

                setHorizontalAlignment(CENTER); // Căn giữa (hoặc LEFT/RIGHT tuỳ bạn)
                return c;
            }
        };

        // Gán renderer cho tất cả cột
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
        }
    }
}
