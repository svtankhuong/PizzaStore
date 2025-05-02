package BUS;

import DAO.*;
import DTO.*;
import java.util.ArrayList;


public class HoaDonBUS
{
    public ArrayList<SanPhamDTO> hienDSSP(){
        SanPhamDAO sp = new SanPhamDAO();
        return sp.layDSSP();
    }
    
    public boolean addReceipt(HoaDonDTO hd){
        HoaDonDAO hddao = new HoaDonDAO();
        return hddao.themHD(hd);
    }
    
    public boolean addDetailReceipt(ChiTietHoaDonDTO cthd){
        CTHDDAO cthddao = new CTHDDAO();
        return cthddao.themCTHD(cthd);
    }
    
    public ArrayList<HoaDonDTO> hienDSHD(){
        HoaDonDAO hd = new HoaDonDAO();
        return hd.layDSHD();
    }
    
    public ArrayList<ThongTinKMDTO> data_InforDiscount(){
        ArrayList<ThongTinKMDTO> list_data = new ArrayList<>();
        KhuyenMaiDAO a = new KhuyenMaiDAO();
        ChiTietKMDAO b = new ChiTietKMDAO();
        ArrayList<KhuyenMaiDTO> dskm = a.layDSKM();
        ArrayList<ChiTietKMDTO> dsctkm = b.layDSCTKM();
        for (KhuyenMaiDTO km : dskm) {
            for (ChiTietKMDTO ctkm : dsctkm) {
                if (ctkm.getMaKM() == km.getMaKM()) {
                    list_data.add(new ThongTinKMDTO(
                        km.getMaKM(),
                        km.getTenKM(),
                        ctkm.getTenCTKM(),
                        ctkm.getPhanTramGiam(),
                        km.getNgayKetThuc()
                    ));
                }
            }
        }
        return list_data;
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
}
