package BUS;

import MyCustom.QueryCondition;
import DAO.*;
import DTO.*;
import java.util.ArrayList;


public class HoaDonBUS
{
    private final HoaDonDAO hddao;
    private final CTHDDAO cthddao;
    public HoaDonBUS(){
       hddao = new HoaDonDAO();
       cthddao = new CTHDDAO();
    }
    public ArrayList<SanPhamDTO> hienDSSP(){
        SanPhamDAO sp = new SanPhamDAO();
        return sp.layDSSP();
    }
    
    public boolean addReceipt(HoaDonDTO hd){      
        return hddao.themHD(hd);
    }
    
    public boolean addDetailReceipt(ChiTietHoaDonDTO cthd){
        return cthddao.themCTHD(cthd);
    }
    
    public ArrayList<HoaDonDTO> hienDSHD(){
        return hddao.layDSHD();
    }
    
    public ArrayList<ChiTietHoaDonDTO> hienDSCTHD(){
        return cthddao.layDSCTHD();
    }
     
    public String timAnhChoSanPham(String maSP){
       SanPhamDAO sp = new SanPhamDAO();
       String anh = null;
       ArrayList<SanPhamDTO> list = sp.timKiemSanPham(maSP);
       for (SanPhamDTO spdto: list){
           anh = spdto.getAnhSP();
       }
       return anh;
    }
    
    public ArrayList<HoaDonDTO> TimHDTheoNFieldTheoDau(ArrayList<QueryCondition> conditions){
        return hddao.layHDTheoNFieldTheoDau(conditions);
    }
}
