package GUI; // Khai báo package của chương trình

// Import thư viện FlatLaf để áp dụng giao diện Flat Atom One Light
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatAtomOneLightIJTheme; 
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

/**
 * Lớp LogIn tạo một giao diện đăng nhập đơn giản bằng Java Swing
 */
public class LogIn extends javax.swing.JFrame
{
    // Khai báo panel chứa tiêu đề, có ảnh nền
    JPanel headerPanel = new JPanel(){
        BufferedImage img; // Biến lưu trữ ảnh

        @Override
        protected void paintComponent(Graphics g) { // Ghi đè phương thức để vẽ ảnh lên panel
            super.paintComponent(g);
            try {
                img = ImageIO.read(new File("D:\\PizzaStore\\src\\main\\resources\\PizzaHut.png")); // Đọc ảnh từ đường dẫn
                g.drawImage(img, 0, 0, getWidth(), getHeight(), this); // Vẽ ảnh lên panel, co giãn theo kích thước
            } catch (IOException e) {
                e.printStackTrace(); // In lỗi nếu không tải được ảnh
            }
        }
    };

    // Khai báo các panel chứa nội dung khác nhau của giao diện
    JPanel bodyPanel = new JPanel();   // Panel chứa các trường nhập liệu
    JPanel bottomPanel = new JPanel(); // Panel chứa nút bấm

    // Khai báo các thành phần giao diện
    JLabel lb2 = new JLabel("username"); // Nhãn cho trường nhập username
    JLabel lb3 = new JLabel("password"); // Nhãn cho trường nhập password
    JButton bt1 = new JButton("Đăng Nhập"); // Nút đăng nhập
    JTextField tf1 = new JTextField("Nhập username"); // Trường nhập username
    JPasswordField pf1 = new JPasswordField("Nhập password"); // Trường nhập password

    /**
     * Constructor của lớp LogIn, thiết lập giao diện đăng nhập
     */
    public LogIn()
    {
        setTitle("Trang Đăng Nhập"); // Đặt tiêu đề cửa sổ
        setSize(300,340); // Thiết lập kích thước cửa sổ
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // Đóng chương trình khi nhấn nút đóng
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS)); // Sắp xếp layout theo chiều dọc

        // Thiết lập headerPanel (chứa ảnh nền)
        BoxLayout boxLayout = new BoxLayout(headerPanel, BoxLayout.X_AXIS); // Layout ngang
        headerPanel.setPreferredSize(new Dimension(200,200)); // Kích thước ưu tiên
        headerPanel.setLayout(boxLayout); 
        headerPanel.setBorder(new EmptyBorder(new Insets(0,100,0,100))); // Căn lề

        // Thiết lập bodyPanel (chứa username và password)
        GridBagLayout gridBagLayout = new GridBagLayout(); // Dùng GridBagLayout để sắp xếp linh hoạt
        bodyPanel.setLayout(gridBagLayout);
        GridBagConstraints gbc = new GridBagConstraints(); // Thiết lập ràng buộc cho layout
        gbc.fill = GridBagConstraints.HORIZONTAL; // Lấp đầy theo chiều ngang

        // Thêm nhãn "username" vào bodyPanel
        gbc.gridx = 0; // Cột đầu tiên
        gbc.gridy = 0; // Hàng đầu tiên
        gbc.weightx = 0;
        gbc.ipadx = 16;
        bodyPanel.add(lb2, gbc);

        // Thêm trường nhập username
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.ipadx = 100;
        gbc.gridwidth = GridBagConstraints.REMAINDER; // Mở rộng hết hàng
        bodyPanel.add(tf1, gbc);

        // Thêm nhãn "password"
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        gbc.ipadx = 16;
        bodyPanel.add(lb3, gbc);

        // Thêm trường nhập password
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 0;
        gbc.ipadx = 100;
        bodyPanel.add(pf1, gbc);

        // Thiết lập bottomPanel (chứa nút đăng nhập)
        BoxLayout boxLayout1 = new BoxLayout(bottomPanel, BoxLayout.X_AXIS);
        bottomPanel.setLayout(boxLayout1);
        bottomPanel.add(bt1); // Thêm nút đăng nhập vào panel
        bottomPanel.setBorder(new EmptyBorder(new Insets(10, 20, 10, 20))); // Căn lề cho panel

        // Thêm các panel vào cửa sổ chính
        add(headerPanel);
        add(bodyPanel);
        add(bottomPanel);

        setVisible(true); // Hiển thị cửa sổ
    }

    /**
     * Phương thức main để chạy chương trình
     */
    public static void main(String args[])
    {
        try
        {
            // Cài đặt giao diện Flat Atom One Light
            UIManager.setLookAndFeel(new FlatAtomOneLightIJTheme());
        } catch (Exception e)
        {
            System.out.println("Có Lỗi"); // In lỗi nếu không thể cài đặt giao diện
        }
        LogIn a = new LogIn(); // Khởi tạo cửa sổ đăng nhập
    }
}
