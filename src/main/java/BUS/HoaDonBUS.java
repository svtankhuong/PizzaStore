package BUS;

import DAO.SanPhamDAO;
import DTO.SanPhamDTO;
import java.util.ArrayList;


public class HoaDonBUS
{
    public ArrayList<SanPhamDTO> showDSSP(){
        SanPhamDAO sp = new SanPhamDAO();
        return sp.layDSSP();
    }
    
    public void addReceipt(SanPhamDTO sp){
        
    }
}
