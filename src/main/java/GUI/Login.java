
package GUI;

import BUS.LoginBUS;
import DAO.TaiKhoanDAO;
import DTO.TaiKhoanDTO;
import MyCustom.ImagePanel;
import java.util.ArrayList;
import javax.swing.JOptionPane;




public class Login extends javax.swing.JFrame {

  
    public Login() {
        initComponents();
    }

 
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        jPanel1 = new javax.swing.JPanel();
        Left = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        tfUsername = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        pfPassword = new javax.swing.JPasswordField();
        BtnLogin = new javax.swing.JButton();
        jPanel2 = new ImagePanel("src/main/resources/LoginUI/background-pizza.jpg", 680, 580);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("LOGIN");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(800, 500));
        jPanel1.setLayout(null);

        Left.setBackground(new java.awt.Color(255, 255, 255));
        Left.setMinimumSize(new java.awt.Dimension(400, 500));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 52)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 235, 246));
        jLabel1.setText("LOGIN");
        jLabel1.setPreferredSize(new java.awt.Dimension(200, 100));

        jLabel2.setBackground(new java.awt.Color(102, 102, 102));
        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel2.setText("Email");

        tfUsername.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        tfUsername.setForeground(new java.awt.Color(102, 102, 102));
        tfUsername.setPreferredSize(new java.awt.Dimension(64, 40));

        jLabel3.setBackground(new java.awt.Color(102, 102, 102));
        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel3.setText("Password");

        pfPassword.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        pfPassword.setPreferredSize(new java.awt.Dimension(64, 40));

        BtnLogin.setBackground(new java.awt.Color(215, 0, 243));
        BtnLogin.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        BtnLogin.setForeground(new java.awt.Color(255, 255, 255));
        BtnLogin.setText("Login");
        BtnLogin.setAlignmentY(0.0F);
        BtnLogin.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        BtnLogin.setPreferredSize(new java.awt.Dimension(118, 56));
        BtnLogin.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                BtnLoginActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout LeftLayout = new javax.swing.GroupLayout(Left);
        Left.setLayout(LeftLayout);
        LeftLayout.setHorizontalGroup(
            LeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, LeftLayout.createSequentialGroup()
                .addContainerGap(101, Short.MAX_VALUE)
                .addComponent(BtnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(113, 113, 113))
            .addGroup(LeftLayout.createSequentialGroup()
                .addGroup(LeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(LeftLayout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(LeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3)
                            .addComponent(pfPassword, javax.swing.GroupLayout.DEFAULT_SIZE, 343, Short.MAX_VALUE)
                            .addComponent(tfUsername, javax.swing.GroupLayout.DEFAULT_SIZE, 343, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(LeftLayout.createSequentialGroup()
                        .addGap(106, 106, 106)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        LeftLayout.setVerticalGroup(
            LeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LeftLayout.createSequentialGroup()
                .addContainerGap(53, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(tfUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(pfPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(BtnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(108, 108, 108))
        );

        jPanel1.add(Left);
        Left.setBounds(680, 0, 390, 580);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 680, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 580, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel2);
        jPanel2.setBounds(0, 0, 680, 580);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1071, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 577, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnLoginActionPerformed(java.awt.event.ActionEvent evt)                                         
    {                                             
        // TODO add your handling code here:
        String username = tfUsername.getText();
        char[] password = pfPassword.getPassword();
        String pass = new String(password);
        if(username.isBlank() && pass.isBlank())
        {
            JOptionPane.showMessageDialog(null, "username và password không dược để trống", "Lỗi", JOptionPane.ERROR_MESSAGE );
        } else if(username.isBlank())
        {
            JOptionPane.showMessageDialog(null, "username không được để trống", "Lỗi", JOptionPane.ERROR_MESSAGE );            
        } else if(pass.isBlank()){
            JOptionPane.showMessageDialog(null, "password không được để trống", "Lỗi", JOptionPane.ERROR_MESSAGE );                      
        } else{
            LoginBUS a = new LoginBUS();
            int kiemtra= a.check_user_log_in(username, pass);
            if (kiemtra < 0)
            {
                JOptionPane.showMessageDialog(null, String.format("Tài khoản %s không tồn tại",username), "Lỗi", JOptionPane.ERROR_MESSAGE );                      
            }else if (kiemtra == 0){
                JOptionPane.showMessageDialog(null, String.format("Mật khẩu của tài khoản %s không đúng",username), "Lỗi", JOptionPane.ERROR_MESSAGE );                                      
            }else{
                JOptionPane.showMessageDialog(null, String.format("Đăng nhập tài khoản %s thành công",username), "Thông Báo", JOptionPane.INFORMATION_MESSAGE );                                      
                TaiKhoanDAO c = new TaiKhoanDAO();
                ArrayList<Object> ttdn = c.getInforAccount(username);
                MainGUI b = new MainGUI(ttdn);
                this.dispose();
                b.setVisible(true);
            }
        }

    
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnLogin;
    private javax.swing.JPanel Left;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPasswordField pfPassword;
    private javax.swing.JTextField tfUsername;
    // End of variables declaration//GEN-END:variables
}
