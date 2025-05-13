package BUS;

import MyCustom.QueryCondition;
import DAO.*;
import DTO.*;
import java.util.ArrayList;


public class HoaDonBUS
{
    private final HoaDonDAO hddao;
    private final CTHDDAO cthddao;
    public final ArrayList<HoaDonDTO> dshd;
    public final ArrayList<ChiTietHoaDonDTO> dsCTHD;
    public HoaDonBUS(){
       hddao = new HoaDonDAO();
       cthddao = new CTHDDAO();
       dshd = new ArrayList<>();
       dsCTHD = new ArrayList<>();
    }
    
    public ArrayList<SanPhamDTO> hienDSSP(){
        SanPhamDAO sp = new SanPhamDAO();
        return sp.layDSSP();
    }
    
    public boolean addReceipt(HoaDonDTO hd){    
        dshd.add(hd);
        return hddao.themHD(hd);
    }
    
    public boolean addArrayListDR(ArrayList<ChiTietHoaDonDTO> dscthd){
        dsCTHD.addAll(dscthd);
        return cthddao.themArrayListCTHD(dscthd);
    }
    
    public void hienDSHD(){
        dshd.addAll(hddao.layDSHD());
    }
    
    public void hienDSCTHD(){
        dsCTHD.addAll(cthddao.layDSCTHD());
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
