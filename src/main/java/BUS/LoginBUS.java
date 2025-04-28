
package BUS;

import DAO.TaiKhoanDAO;
import DTO.TaiKhoanDTO;
import java.util.ArrayList;


public class LoginBUS
{
    public int check_user_log_in(String tenDN, String tenMK){
        TaiKhoanDAO tkdao = new TaiKhoanDAO();
        ArrayList<TaiKhoanDTO> dsTK = tkdao.getDanhSachTaiKhoan();
        for (TaiKhoanDTO tk: dsTK){
            if (tk.getTenDangNhap().equals(tenDN))
            {
                if (tk.getMatKhau().equals(tenMK)){
                    return 1;
                }else{
                //  nếu người dùng đăng nhập sai mật khẩu                  
                    return 0;
                }
            }
        }
        //  Nếu người dùng gõ tên đăng nhập sai hoặc không tồn tại
        return -1; 
    }
}
