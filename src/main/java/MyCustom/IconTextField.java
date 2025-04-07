package MyCustom;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

// Giả sử bạn dùng thư viện jiconfont-swing và fontawesome5
// Thay thế bằng import thực tế của thư viện icon bạn dùng
import org.kordamp.ikonli.swing.FontIcon;
import org.kordamp.ikonli.Ikon; // Interface chung cho các icon literal

public class IconTextField extends JTextField {

    private FontIcon icon;
    private Insets dummyInsets; // Để tính toán lề gốc

    public IconTextField(int columns) {
        super(columns);
        init();
    }

    public IconTextField(String text) {
        super(text);
        init();
    }

    public IconTextField(String text, int columns) {
        super(text, columns);
        init();
    }

    public IconTextField() {
        super();
        init();
    }

    private void init() {
        // Lấy Border gốc để tính toán Insets (lề) gốc
        Border border = getBorder();
        if (border != null) {
            this.dummyInsets = border.getBorderInsets(this);
        } else {
            // Giá trị mặc định nếu không có border
            this.dummyInsets = new Insets(2, 2, 2, 2);
        }
    }

    /**
     * Thiết lập FontIcon để hiển thị bên trái trường văn bản.
     *
     * @param iconLiteral Icon từ thư viện (ví dụ: FontAwesomeSolid.USER)
     * @param iconSize    Kích thước icon
     * @param iconColor   Màu icon
     */
    public void setIcon(Ikon iconLiteral, int iconSize, Color iconColor) {
        if (iconLiteral != null) {
            this.icon = FontIcon.of(iconLiteral, iconSize, iconColor);
            updateMargin();
        } else {
            this.icon = null;
            // Reset lề về mặc định nếu không có icon
            setMargin(dummyInsets != null ? dummyInsets : new Insets(2, 2, 2, 2));
        }
        repaint(); // Vẽ lại component để hiển thị icon
    }

    /**
     * Cập nhật lề (margin) của JTextField để tạo không gian cho icon.
     */
    private void updateMargin() {
        if (icon == null || dummyInsets == null) {
            return; // Không cần làm gì nếu không có icon hoặc lề gốc
        }
        // Tính toán lề mới: lề trái = độ rộng icon + khoảng đệm + lề gốc
        int iconWidth = icon.getIconWidth();
        int padding = 5; // Khoảng đệm giữa icon và text
        Insets currentInsets = getMargin();

        // Chỉ cập nhật lề trái, giữ nguyên các lề khác từ lề gốc (dummyInsets)
        // Hoặc từ lề hiện tại nếu đã được set trước đó (ít phổ biến hơn)
        int top = (currentInsets != null && currentInsets != dummyInsets) ? currentInsets.top : dummyInsets.top;
        int bottom = (currentInsets != null && currentInsets != dummyInsets) ? currentInsets.bottom : dummyInsets.bottom;
        int right = (currentInsets != null && currentInsets != dummyInsets) ? currentInsets.right : dummyInsets.right;

        setMargin(new Insets(top, iconWidth + padding + dummyInsets.left, bottom, right));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Quan trọng: Vẽ phần JTextField gốc trước

        if (icon != null) {
            // Lấy lề hiện tại (đã bao gồm không gian cho icon)
            Insets currentInsets = getInsets(); // getInsets() bao gồm cả border và margin

            // Tính toán vị trí vẽ icon
            // Vẽ icon ở góc trên bên trái, trong phạm vi của lề (margin) đã tạo
            // Vị trí x: Dựa vào lề trái gốc (dummyInsets)
            // Vị trí y: Căn giữa theo chiều dọc
            int x = dummyInsets != null ? dummyInsets.left : 2; // Vị trí bắt đầu vẽ icon (trong vùng border/margin)
            int y = (getHeight() - icon.getIconHeight()) / 2;

            // Vẽ icon lên component tại vị trí đã tính
            icon.paintIcon(this, g, x, y);
        }
    }

    // (Tùy chọn) Thêm các constructor khác nếu cần
    public IconTextField(Ikon iconLiteral, int iconSize, Color iconColor, int columns) {
        this(columns); // Gọi constructor khác của lớp này
        setIcon(iconLiteral, iconSize, iconColor);
    }
}
