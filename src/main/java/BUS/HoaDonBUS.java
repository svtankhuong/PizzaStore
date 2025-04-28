package BUS;

import DAO.HoaDonDAO;
import DAO.SanPhamDAO;
import DTO.HoaDonDTO;
import DTO.SanPhamDTO;
import java.util.ArrayList;


public class HoaDonBUS
{
    public ArrayList<SanPhamDTO> hienDSSP(){
        SanPhamDAO sp = new SanPhamDAO();
        return sp.layDSSP();
    }
    
    public void addReceipt(SanPhamDTO sp){
        
    }
    public ArrayList<HoaDonDTO> hienDSHD(){
        HoaDonDAO hd = new HoaDonDAO();
        return hd.layDSHD();
    }
}
