package GUI;


import MyCustom.QueryCondition;
import MyCustom.SelectCustomerOrDiscount;
import BUS.HoaDonBUS;
import BUS.KhachHangBUS;
import DAO.*;
import DTO.*;
import MyCustom.*;
import be.quodlibet.boxable.BaseTable;
import be.quodlibet.boxable.Cell;
import be.quodlibet.boxable.Row;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.TableCellRenderer;
import javax.swing.text.NumberFormatter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import javax.swing.JOptionPane;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicMenuUI;

import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.swing.FontIcon;

public class PnQLBH extends JPanel {

    private JPanel panelHoaDon;
    private JPanel panelBanHang;
    
    // Biến cho tab Hoá Đơn
    private JTable tableHoaDon;
    private JTable tableChiTietHoaDon;
    private JComboBox cbbMaHD;
    private JTextField tfMaKH;
    private JButton btnKH;
    private DatePicker dpStartDate;
    private DatePicker dpEndDate;
    private JFormattedTextField tfMinMoney;
    private JFormattedTextField tfMaxMoney;
    private JTextField tfChiTietKM;
    private JButton btnCTKM;
    private JButton btnTimKiem;
    private JTextField tfNameDiscount;
    private JTextField tfDetailDiscountName;
    private JButton btnXuatExcelHoaDon;
    private JButton btnXuatExcelChiTiet;

    // Biến cho tab Bán Hàng
    private JTable tableSanPham;
    private JTable tableGioHang;
    private JLabel lblAnhSanPham;
    private JComponent[] fields;
    private JTextField ttKH;
    private JTextField ttKM;
    private JButton btnThemVaoGio;
    private JButton btnXoaKhoiGio;
    private JButton btnTaoHoaDon;
    private JButton btnXuatHoaDon;
    private JButton btnRefreshChiTietSP;
    private JCheckBox moneyCB1;
    private JCheckBox moneyCB;
    private JCheckBox dateCB1;
    private JCheckBox dateCB;
    private JRadioButton moreMoneyBtn;
    private JRadioButton rangeMoneyBtn;
    private JRadioButton lessMoneyBtn;
    private JRadioButton beforeDateBtn;
    private JRadioButton betweenDateBtn;
    private JRadioButton afterDateBtn;
    private JComboBox<String> cbMaNV;
    private ButtonGroup groupMoney;
    private ButtonGroup groupDate;
    
    private int slTD;
    private final String DEFAULT_IMAGE_PATH = "src/main/resources/SanPham/default.png";
    private final int IMAGE_WIDTH = 200;
    private final int IMAGE_HEIGHT = 200;
    private ArrayList<String[]> list_bought_pizza;
    private ArrayList<ChiTietHoaDonDTO> dsCTHD;
    private int tong_tien_hd;
    private DecimalFormat currencyFormat;
    private NumberFormatter numberFormatter;
    private String ma_hd_cantim="";
    private String ma_nv_cantim="";
    private Boolean co_loc_tien = false;
    private String cot_loc_tien="";

    public PnQLBH(ArrayList<Object> tk) {
        HoaDonBUS hdbus = new HoaDonBUS();
        dsCTHD = hdbus.hienDSCTHD();
        initComponents();
        setupTableModels(tk);
        loadDataFromSource();
        loadProductImage(DEFAULT_IMAGE_PATH);
        JButton a = (JButton) fields[6];
        a.addActionListener((e) -> showTableCustomer());
        JButton b = (JButton) fields[7];
        b.addActionListener((e) -> showTableDiscount(false));
        btnThemVaoGio.addActionListener((e) -> ThemVaoGioHang());
        btnTaoHoaDon.addActionListener((e) -> TaoHoaDon(tk));
        btnXoaKhoiGio.addActionListener((e) -> XoaKhoiGioHang());
        btnXuatHoaDon.addActionListener((e) -> {
            if (list_bought_pizza == null)
            {
                JOptionPane.showMessageDialog(this, "Bạn phải nhấn tạo hoá đơn trước khi xuất hoá đơn", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }else{
                XuatPDFHoaDon(tk);
            }         
        });
        btnKH.addActionListener((ActionEvent e) -> showTableCustomer());
        btnCTKM.addActionListener((ActionEvent e) -> showTableDiscount(true));
        btnTimKiem.addActionListener((ActionEvent e) -> TimKiemHoaDon());
        cbbMaHD.addItemListener(new ItemListener()
        {
            @Override
            public void itemStateChanged(ItemEvent e)
            {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                  if(e.getItem().toString().matches("\\d+")){
                    System.out.println("Item được chọn: " + e.getItem());
                    ma_hd_cantim= e.getItem().toString(); 
                  } else{
                      ma_hd_cantim="";
                  }
                }                  
            }
        });
        cbMaNV.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e)
            {
                if(e.getStateChange() == ItemEvent.SELECTED){
                  if(e.getItem().toString().matches("\\d+")){
                    System.out.println("Item được chọn: " + e.getItem());
                    ma_nv_cantim= e.getItem().toString(); 
                  } else{
                      ma_nv_cantim="";
                  }         
                }
            }          
        });
        btnXuatExcelHoaDon.addActionListener((ActionEvent e) -> xuatExcelHoaDon());
        btnXuatExcelChiTiet.addActionListener((ActionEvent e) -> xuatExcelCTHoaDon());
    }
    
    public void xuatExcelHoaDon() {
        try {
            Workbook workbook = new XSSFWorkbook();
            HoaDonBUS hdbus = new HoaDonBUS();
            // Sheet hoá đơn
            Sheet sheetReceipt = workbook.createSheet("Hoá Đơn");
            org.apache.poi.ss.usermodel.Row headerPN = sheetReceipt.createRow(0);
            String[] headersPN = {"Mã Hoá Đơn", "Mã Nhân Viên", "Mã Khách Hàng","Mã Chi Tiết Khuyến Mãi", "Ngày Lập", "Số Tiền Giảm" ,"Tổng Tiền"};
            for (int i = 0; i < headersPN.length; i++) {
                headerPN.createCell(i).setCellValue(headersPN[i]);
            }
            ArrayList<HoaDonDTO> dsHD = hdbus.hienDSHD();
            for (int i = 0; i < dsHD.size(); i++) {
               HoaDonDTO hd = dsHD.get(i);
                 org.apache.poi.ss.usermodel.Row row = sheetReceipt.createRow(i + 1);
                row.createCell(0).setCellValue(hd.getMaHD());
                row.createCell(1).setCellValue(hd.getMaNV());
                row.createCell(2).setCellValue(hd.getMaKH());
                row.createCell(3).setCellValue(hd.getMaCTKM());
                row.createCell(4).setCellValue(InforAccount.formatDate(hd.getNgayLapHD().toString()));
                row.createCell(5).setCellValue(currencyFormat.format(hd.getTongTienHD()));
            }
            // Lưu file
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Chọn nơi lưu file excel hóa đơn");
            fileChooser.setSelectedFile(new File("HoaDon.xlsx"));
            if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try (FileOutputStream fos = new FileOutputStream(file)) {
                    workbook.write(fos);
                    JOptionPane.showMessageDialog(this, "Xuất file Excel thành công tại: " + file.getAbsolutePath());
                }
            }
            workbook.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi xuất Excel: " + e.getMessage());
        }
    }
    public void xuatExcelCTHoaDon() {
        try {
            Workbook workbook = new XSSFWorkbook();
            HoaDonBUS hdbus = new HoaDonBUS();

            // Sheet chi tiết hoá đơn
            Sheet sheetDR = workbook.createSheet("Chi Tiết Hoá Đơn");
            org.apache.poi.ss.usermodel.Row headerDR = sheetDR.createRow(0);
            String[] headersDR = {"Mã Chi Tiết Hoá Đơn","Mã Hoá Đơn" , "Mã Sản Phẩm", "Mã Chi Tiết Khuyến Mãi", "Số Lượng"  ,"Đơn Giá", "Thành Tiền"};
            for (int i = 0; i < headersDR.length; i++) {
                headerDR.createCell(i).setCellValue(headersDR[i]);
            }
            ArrayList<ChiTietHoaDonDTO> listDR = hdbus.hienDSCTHD();
            for (int i = 0; i < listDR.size(); i++) {
                ChiTietHoaDonDTO ct = listDR.get(i);
                org.apache.poi.ss.usermodel.Row row = sheetDR.createRow(i + 1);
                row.createCell(0).setCellValue(ct.getMaCTHD());
                row.createCell(1).setCellValue(ct.getMaHD());
                row.createCell(2).setCellValue(ct.getMaSP());
                row.createCell(3).setCellValue(ct.getMaCTKM());
                row.createCell(4).setCellValue(ct.getSoLuong());
                row.createCell(5).setCellValue(ct.getDonGia());
                row.createCell(6).setCellValue(ct.getThanhTien());
            }

            // Lưu file
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Chon noi lưu file Excel CTHD");
            fileChooser.setSelectedFile(new File("ChiTietHoaDon.xlsx"));
            if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try (FileOutputStream fos = new FileOutputStream(file)) {
                    workbook.write(fos);
                    JOptionPane.showMessageDialog(this, "Xuất file Excel thành công tại: " + file.getAbsolutePath());
                }
            }
            workbook.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi xuất Excel: " + e.getMessage());
        }
    }


    private void TimKiemHoaDon(){
        if(kiem_tra_thong_tin_tim_kiemHD()){
            if (co_loc_tien)
            {
                String[] choices = {"Số Tiền Giảm", "Tổng Tiền"};        
                String input = (String) JOptionPane.showInputDialog(this, "Bạn muốn lọc tiền ở cột nào?", "Chọn cột", JOptionPane.QUESTION_MESSAGE, null, choices, choices[0]);
                if (input != null) {
                    JOptionPane.showMessageDialog(this, "Bạn đã chọn: " + input);
                    cot_loc_tien=input;
                } 
            }

            ArrayList<HoaDonDTO> dshd = null;
            LinkedHashMap<String, String> dstt = chuoi_thongtin_tkhd_khongtrong();
            ArrayList<QueryCondition> qc = new ArrayList<>();
            HoaDonBUS hd = new HoaDonBUS();
            for (Map.Entry<String, String> entry : dstt.entrySet()) {
                String[] tencot = entry.getKey().split(" ",2);
                String bd = entry.getValue();
                if(bd != null){
                    String[] mang_dau_value = bd.split(" ",2);
                    String dau = mang_dau_value[0];
                    Object value = (Object) mang_dau_value[1];
                    qc.add(new QueryCondition(tencot[0], dau, value));
                } else{
                    qc.add(new QueryCondition(tencot[0]));
                }

            }
            dshd = hd.TimHDTheoNFieldTheoDau(qc);
            if(dshd == null){
                JOptionPane.showMessageDialog(this, "Có lỗi, muốn biết thêm chi tiết thì vô trong console log", "Thông Báo", JOptionPane.ERROR_MESSAGE);
            } else{
                if(!dshd.isEmpty()){
                HienHoaDonSauTK(dshd);  
                JOptionPane.showMessageDialog(this, "Tìm Kiếm Hoá Đơn Thành Công!");
                groupDate.clearSelection();
                groupMoney.clearSelection();
                dateCB.setSelected(false);
                dateCB1.setSelected(false);
                groupMoney.clearSelection();
                moneyCB.setSelected(false);
                moneyCB1.setSelected(false);
                cbbMaHD.setSelectedIndex(0);
                cbMaNV.setSelectedIndex(0);
                tfMaKH.setText("");
                tfChiTietKM.setText("");
                tfMinMoney.setText("");
                tfMaxMoney.setText("");
                dpStartDate.setText("");
                dpEndDate.setText("");
                } else{
                    JOptionPane.showMessageDialog(this, "Không tồn tại thông tin này trong CSDL");
                }
            }
        }
    }
    
 
    
    private  LinkedHashMap<String, String> chuoi_thongtin_tkhd_khongtrong(){
        LinkedHashMap<String, String> ds_tt_khong_trong = new LinkedHashMap<>();
        if(!tfMaKH.getText().isBlank()){
            String[] tt = tfMaKH.getText().split("\\s+");
            ds_tt_khong_trong.put("MaKH", String.format("= %s", tt[0]));
        }
        if(!tfChiTietKM.getText().isBlank()){
            if(tfChiTietKM.getText().equals("Không khuyến mãi")){
                ds_tt_khong_trong.put("MaCTKM", null);
            } else{
                String[] array = tfChiTietKM.getText().split("\\s+");
                ds_tt_khong_trong.put("MaCTKM",String.format("= %s", array[0]));
            }
        }
        if(!ma_hd_cantim.isBlank()){
            ds_tt_khong_trong.put("MaHD",String.format("= %s", ma_hd_cantim));
        }
        
        if(!ma_nv_cantim.isBlank()){
            ds_tt_khong_trong.put("MaKH",String.format("= %s", ma_nv_cantim));
        }

        String a1 = dpStartDate.getText();
        String b1 = dpEndDate.getText();
        // Tạo formatter cho định dạng đầu vào
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // Tạo formatter cho định dạng đầu ra
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date, date1;
        date = date1 = null;
        String a,b;
        a = b = "";
        // Chuyển đổi
        if(!a1.isBlank()){
            date = LocalDate.parse(a1, inputFormatter);
            a = date.format(outputFormatter);
        }
        
        if(!b1.isBlank()){
            date1 = LocalDate.parse(b1, inputFormatter);
            b = date1.format(outputFormatter);
        }

        if(beforeDateBtn.isSelected()){
           if(a.isBlank() && !b.isBlank()){
             ds_tt_khong_trong.put("NgayLap",String.format("< %s", b));              
           }else if(!a.isBlank() && b.isBlank()){
             ds_tt_khong_trong.put("NgayLap",String.format("< %s", a));    
           }
        }else if(afterDateBtn.isSelected()){
           if(a.isBlank() && !b.isBlank()){
            ds_tt_khong_trong.put("NgayLap",String.format("> %s", b));              
           }else if(!a.isBlank() && b.isBlank()){
            ds_tt_khong_trong.put("NgayLap",String.format("> %s", a));    
           }            
        }else{
            if(!a.isBlank() && !b.isBlank()){
               if(a.equals(b)){
                   ds_tt_khong_trong.put("NgayLap",String.format("= %s", a));
               }else{

                   ds_tt_khong_trong.put("NgayLap 1",String.format("> %s", a)); 
                   ds_tt_khong_trong.put("NgayLap 2",String.format("< %s", b)); 
               }
            }           
        }
        String c = tfMinMoney.getText();
        String d = tfMaxMoney.getText();
        if(lessMoneyBtn.isSelected()){
           if(c.isBlank() && !d.isBlank()){
               if(cot_loc_tien.equals("Số Tiền Giảm")){
                   ds_tt_khong_trong.put("SoTienGiam",String.format("< %s", d));       
               }else{
                   ds_tt_khong_trong.put("TongTien",String.format("< %s", d));       
               }                    
           }else if(!c.isBlank() && d.isBlank()){
                if(cot_loc_tien.equals("Số Tiền Giảm")){
                   ds_tt_khong_trong.put("SoTienGiam",String.format("< %s", c));       
                }else{
                   ds_tt_khong_trong.put("TongTien",String.format("< %s", c));       
                }     
           }
        }else if(moreMoneyBtn.isSelected()){
           if(c.isBlank() && !d.isBlank()){
                if(cot_loc_tien.equals("Số Tiền Giảm")){
                   ds_tt_khong_trong.put("SoTienGiam",String.format("> %s", d));       
               }else{
                   ds_tt_khong_trong.put("TongTien",String.format("> %s", d));       
               }              
           }else if(!c.isBlank() && d.isBlank()){
                if(cot_loc_tien.equals("Số Tiền Giảm")){
                   ds_tt_khong_trong.put("SoTienGiam",String.format("> %s", tfMinMoney.getText()));       
                }else{
                   ds_tt_khong_trong.put("TongTien",String.format("> %s", tfMinMoney.getText()));       
                }     
           }            
        } else{
            if(!c.isBlank() && !d.isBlank()){
                if(c.equals(d)){
                    if(cot_loc_tien.equals("Số Tiền Giảm")){
                        ds_tt_khong_trong.put("SoTienGiam",String.format("= %s", c));
                    }else{
                        ds_tt_khong_trong.put("TongTien",String.format("= %s", d));                 
                    }  
                }else{
                    if(cot_loc_tien.equals("Số Tiền Giảm")){
                        ds_tt_khong_trong.put("SoTienGiam 1",String.format("> %s", c));
                        ds_tt_khong_trong.put("SoTienGiam 2",String.format("< %s", d));
                    }else{
                        ds_tt_khong_trong.put("TongTien 1",String.format("> %s", c));
                        ds_tt_khong_trong.put("TongTIen 2",String.format("< %s", d));                
                    }                     
                }
            }
        }
        return ds_tt_khong_trong;

    }
    
    private Boolean kiem_tra_thong_tin_tim_kiemHD(){
        Boolean khong_sai = false;
        if(!kiem_tra_thong_tin_co_trong()){
            if (!kt_timkiem_tien_khong_hople() && !kt_timkiem_ngay_khong_hople()){
                khong_sai = true;
            }
        }
        return khong_sai;
    }
    
    public Boolean kiem_tra_thong_tin_co_trong(){
        if(tfMaKH.getText().isBlank() && tfChiTietKM.getText().isBlank() && ma_hd_cantim.isBlank() && ma_nv_cantim.isBlank()){
            if(dpStartDate.getText().isBlank() && dpEndDate.getText().isBlank()){
                if(tfMinMoney.getText().isBlank() && tfMaxMoney.getText().isBlank()){
                    JOptionPane.showMessageDialog(this, "Không được để trống toàn bộ thông tin cần tìm kiếm", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return true;
                }
            }
        }
        return false;
    }
    
    private boolean kt_timkiem_ngay_khong_hople(){
        boolean coLoi = false;
        if(beforeDateBtn.isSelected() || afterDateBtn.isSelected()){           
            if(dpStartDate.getText().isBlank() && dpEndDate.getText().isBlank()){
                JOptionPane.showMessageDialog(this, "Không được để trống một trong hai textbox có chữ ngày", "Lỗi", JOptionPane.ERROR_MESSAGE);
                coLoi=true;
            } else if(!dateCB.isSelected() && !dateCB1.isSelected()){
                JOptionPane.showMessageDialog(this, "Bạn phải chọn 1 trong 2 textbox ngày cần lọc", "Lỗi", JOptionPane.ERROR_MESSAGE);
                coLoi=true;
            }
            if(dateCB.isSelected() && dateCB1.isSelected()){
                JOptionPane.showMessageDialog(this, "Không được chọn hai textbox co chữ ngày ", "Lỗi", JOptionPane.ERROR_MESSAGE);
                coLoi=true;
            }
        }else if(betweenDateBtn.isSelected()){
            if(dpStartDate.getText().isBlank() && dpEndDate.getText().isBlank()){
                JOptionPane.showMessageDialog(this, "Không được để trống hai textbox có chữ ngày", "Lỗi", JOptionPane.ERROR_MESSAGE);
                coLoi=true;
            }
            if(dpStartDate.getText().isBlank() || dpEndDate.getText().isBlank()){
                JOptionPane.showMessageDialog(this, "Không được để trống một textbox có chữ ngày", "Lỗi", JOptionPane.ERROR_MESSAGE);
                coLoi=true;
            }
            if(!dateCB.isSelected() && !dateCB1.isSelected()){
                JOptionPane.showMessageDialog(this, "Thiếu tích hai textbox ngày cần lọc", "Lỗi", JOptionPane.ERROR_MESSAGE);
                coLoi=true;
            }
            if(!dateCB.isSelected() || !dateCB1.isSelected()){
                JOptionPane.showMessageDialog(this, "Thiếu tick một textbox có chữ ngày", "Lỗi", JOptionPane.ERROR_MESSAGE);
                coLoi=true;
            }   
            if(!coLoi){
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");   
                LocalDate startDate = LocalDate.parse(dpStartDate.getText(), formatter);
                System.out.println(startDate);
                LocalDate endDate =  LocalDate.parse(dpEndDate.getText(), formatter);
                System.out.println(endDate);
                if(startDate.compareTo(endDate) > 0){
                    JOptionPane.showMessageDialog(this, "Ngày 1 phải nhỏ hơn ngày 2", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    coLoi=true;
                }
            }
        }
        
        return coLoi;
    }
    
    private boolean kt_timkiem_tien_khong_hople(){
        boolean coLoi = false;
        if(lessMoneyBtn.isSelected() || moreMoneyBtn.isSelected()){           
            if(tfMinMoney.getText().isBlank() && tfMaxMoney.getText().isBlank()){
                JOptionPane.showMessageDialog(this, "Không được để trống một trong hai textbox có chữ tiền", "Lỗi", JOptionPane.ERROR_MESSAGE);
                coLoi=true;
            } else if(!moneyCB.isSelected() && !moneyCB1.isSelected()){
                JOptionPane.showMessageDialog(this, "Bạn phải chọn 1 trong 2 textbox tiền cần lọc", "Lỗi", JOptionPane.ERROR_MESSAGE);
                coLoi=true;
            }
            if(moneyCB.isSelected() && moneyCB1.isSelected()){
                JOptionPane.showMessageDialog(this, "Không được chọn hai textbox co chữ tiền", "Lỗi", JOptionPane.ERROR_MESSAGE);
                coLoi=true;
            }
            co_loc_tien=true;
        }else if(rangeMoneyBtn.isSelected()){
            if(tfMinMoney.getText().isBlank() && tfMaxMoney.getText().isBlank()){
                JOptionPane.showMessageDialog(this, "Không được để trống hai textbox có chữ tiền", "Lỗi", JOptionPane.ERROR_MESSAGE);
                coLoi=true;
            }
            if(tfMinMoney.getText().isBlank() || tfMaxMoney.getText().isBlank()){
                JOptionPane.showMessageDialog(this, "Không được để trống một textbox có chữ tiền", "Lỗi", JOptionPane.ERROR_MESSAGE);
                coLoi=true;
            }
            if(!moneyCB.isSelected() && !moneyCB1.isSelected()){
                JOptionPane.showMessageDialog(this, "Thiếu tích hai textbox tiền cần lọc", "Lỗi", JOptionPane.ERROR_MESSAGE);
                coLoi=true;
            }
            if(!moneyCB.isSelected() || !moneyCB1.isSelected()){
                JOptionPane.showMessageDialog(this, "Thiếu tích một textbox có chữ tiền", "Lỗi", JOptionPane.ERROR_MESSAGE);
                coLoi=true;
            }        
            if(!coLoi){
                Long minMoney = parseCurrencyString(tfMinMoney.getText());
                Long maxMoney = parseCurrencyString(tfMaxMoney.getText());
                if(minMoney > maxMoney){
                    JOptionPane.showMessageDialog(this, "Giá tiền 1 phải nhỏ hơn giá tiền 2", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    coLoi=true;                   
                }
            }
            co_loc_tien=true;
        }
        
        return coLoi;
    }
    
    private int countReceiptPDFs(String folderPath) {
        int count = 0;
        File folder = new File(folderPath);
        File[] files = folder.listFiles();
        Pattern pattern = Pattern.compile("^receipt\\d+\\.pdf$");
        System.out.println("Đọc danh sách file pdf");
        for (File file : files){
            if (file.isFile() && pattern.matcher(file.getName()).matches()) {
                count++;
            }
        }
        if (count > 0){
            System.out.println("Hiện tại có "+count+" file pdf trong duong dan "+folderPath);
        }
        return count;
    }
    
    private void XuatPDFHoaDon(ArrayList<Object> tk) {
       try {
           // Sử dụng JFileChooser để người dùng chọn nơi lưu file
           JFileChooser fileChooser = new JFileChooser();
           fileChooser.setDialogTitle("Chọn nơi lưu pdf hóa đơn");
           fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

           int userSelection = fileChooser.showSaveDialog(null);

           // Nếu người dùng chọn thư mục
           if (userSelection == JFileChooser.APPROVE_OPTION) {
               File selectedDirectory = fileChooser.getSelectedFile();
               String output_path = selectedDirectory.getAbsolutePath() + "\\";

               // Tạo tên file PDF cho hoá đơn
               String tenfile = "";
               int sl = countReceiptPDFs(output_path);
               if (sl == 0) {
                   tenfile = "receipt1.pdf";
               } else {
                   tenfile = String.format("receipt%d.pdf", sl + 1);
               }

                PDDocument document = new PDDocument();
                PDPage page = new PDPage();
                // mặc định kích thước của trang letter là 612 * 792 point
                document.addPage(page);

                PDPageContentStream contentStream = new PDPageContentStream(document, page);

                InputStream fontStream = getClass().getResourceAsStream("/BeVietnamPro-Regular.ttf");
                PDType0Font font = PDType0Font.load(document, fontStream);
                float margin = 72;
                float yPosition = 655;
                float pageWidth = page.getMediaBox().getWidth();

                InputStream logoStream = getClass().getResourceAsStream("/ManagerUI/pizza-brand.png");
                if (logoStream != null) {
                    PDImageXObject logo = PDImageXObject.createFromByteArray(document, logoStream.readAllBytes(), "logo");
                    float logoWidth = 170;
                    float logoHeight = 115;
                    float logoX = (pageWidth - logoWidth) / 2;
                    contentStream.drawImage(logo, logoX, yPosition, logoWidth, logoHeight);
                } else {
                    System.out.println("Không tìm thấy ảnh logo!");
                }


                // Sau khi vẽ logo xong
                yPosition -= 24;

                // Người lập hoá đơn
                String text_taohd = String.format("Người Lập Hoá Đơn: %s", tk.get(3).toString());
                float text_taohd_width = font.getStringWidth(text_taohd) / 1000 * 16;
                contentStream.beginText();
                contentStream.setFont(font, 16);
                contentStream.newLineAtOffset((pageWidth - text_taohd_width) / 2, yPosition);
                contentStream.showText(text_taohd);
                contentStream.endText();
                yPosition -= 24;

                // Mã Nhân Viên
                String text_manv = String.format("Mã Nhân Viên: %s", tk.get(2));
                float text_manv_width = font.getStringWidth(text_manv) / 1000 * 16;
                contentStream.beginText();
                contentStream.setFont(font, 16);
                contentStream.newLineAtOffset((pageWidth - text_manv_width) / 2, yPosition);
                contentStream.showText(text_manv);
                contentStream.endText();
                yPosition -= 24;

                // Người Mua Pizza
                String[] b = ttKH.getText().split("",2); 
                String text_tenkh = String.format("Người Mua Pizza: %s", b[1]);
                float text_tenkh_width = font.getStringWidth(text_tenkh) / 1000 * 16;
                contentStream.beginText();
                contentStream.setFont(font, 16);
                contentStream.newLineAtOffset((pageWidth - text_tenkh_width) / 2, yPosition);
                contentStream.showText(text_tenkh);
                contentStream.endText();
                yPosition -= 24;

                // Mã Khách Hàng
                String text_kh = String.format("Mã Khách Hàng: %s", b[0]);
                float text_kh_width = font.getStringWidth(text_kh) / 1000 * 16;
                contentStream.beginText();
                contentStream.setFont(font, 16);
                contentStream.newLineAtOffset((pageWidth - text_kh_width) / 2, yPosition);
                contentStream.showText(text_kh);
                contentStream.endText();
                yPosition -= 24;

                // Mã Hoá Đơn
                int n = tableHoaDon.getRowCount();    
                String text_hd = String.format("Mã Hoá Đơn:  %d", n);
                float text_hd_width = font.getStringWidth(text_hd) / 1000 * 16;
                contentStream.beginText();
                contentStream.setFont(font, 16);
                contentStream.newLineAtOffset((pageWidth - text_hd_width) / 2, yPosition);
                contentStream.showText(text_hd);
                contentStream.endText();
                yPosition -= 24;

                // Ngày Lập Hoá Đơn
                LocalDate tght = LocalDate.now();
                String text_ngaylap_hd = String.format("Ngày Lập Hoá Đơn: %s", InforAccount.formatDate(tght.toString()));
                float text_ngaylap_hd_width = font.getStringWidth(text_ngaylap_hd) / 1000 * 16;
                contentStream.beginText();
                contentStream.setFont(font, 16);
                contentStream.newLineAtOffset((pageWidth - text_ngaylap_hd_width) / 2, yPosition);
                contentStream.showText(text_ngaylap_hd);
                contentStream.endText();
                yPosition -= 24;


                // Kẻ cái bảng sản phầm có 4 cột, tên sản phẩm, đơn giá, số lượng, thành tiền
                contentStream.beginText();
                contentStream.setFont(font, 16);
                float tableWidth = pageWidth - 2 * margin;
                BaseTable tableSP = new BaseTable(yPosition, yPosition, 20, tableWidth, margin, document, page, true, true);

                Row<PDPage> headerRow = tableSP.createRow(20);
                ArrayList<Float> ds_chieu_rong = new ArrayList<>();
                String a = "Tên Sản Phẩm";
                Cell<PDPage> cell = headerRow.createCell(46, a);
                ds_chieu_rong.add(46f);
                cell.setFont(font);
                cell.setFontSize(12);

                String a2 = "Đơn Giá";
                Cell<PDPage> cell1 = headerRow.createCell(18 , a2);
                ds_chieu_rong.add(18f);
                cell1.setFont(font);
                cell1.setFontSize(12);

                String b1 = "SL";
                Cell<PDPage> cell2 = headerRow.createCell(10 , b1);
                ds_chieu_rong.add(10f);
                cell2.setFont(font);
                cell2.setFontSize(12);

                String c = "Thành Tiền";
                Cell<PDPage> cell3 = headerRow.createCell(26 , c);
                ds_chieu_rong.add(26f);
                cell3.setFont(font);
                cell3.setFontSize(12);

                for ( int k = 0; k < list_bought_pizza.size(); k++) {
                    Row<PDPage> row = tableSP.createRow(20);
                    for(int l = 0; l < list_bought_pizza.get(k).length; l++) {
                        if(l > 0){
                            Cell<PDPage> cella = row.createCell(ds_chieu_rong.get(l - 1), list_bought_pizza.get(k)[l]);
                            cella.setFont(font);
                            cella.setFontSize(12); 
                        }
                    }
                }
                 // Lấy vị trí y của table 
                contentStream.endText();
                contentStream.close();
                float yWrite = tableSP.draw();


                String text_tongcong = String.format("Tổng tiền ban đầu: %s",currencyFormat.format(tong_tien_hd));
                PDPage lastPage = document.getPage(document.getNumberOfPages() - 1);
                PDPageContentStream contentStream2 = new PDPageContentStream(document, lastPage, PDPageContentStream.AppendMode.APPEND, true);
                yWrite -= 24;
                contentStream2.beginText();
                contentStream2.setFont(font, 16);
                float text_tongcong_width = font.getStringWidth(text_tongcong) / 1000 * 16;
                contentStream2.newLineAtOffset((pageWidth - text_tongcong_width) / 2, yWrite);
                contentStream2.showText(text_tongcong);
                contentStream2.endText();
                yWrite -= 24;

                String giamgiatext = ttKM.getText();
                String text = "";
                long sum_with_discount = 0;
                if (giamgiatext.equals("Không khuyến mãi"))
                {
                    text="Giảm Giá: 0VNĐ";
                } else
                {
                    String[] giamgia = ttKM.getText().split("\\s+");
                    KhuyenMaiDAO kmdao = new KhuyenMaiDAO();
                    int MaKM = Integer.parseInt(giamgia[0]);
                    KhuyenMaiDTO km = kmdao.timKiemKM(MaKM);
                    LocalDate now = LocalDate.now();
                    LocalDate hethan = km.getNgayKetThuc(); 
                    if((now.compareTo(hethan)) > 0){
                        text="Giảm Giá: 0VNĐ";
                    }
                    ChiTietKMDAO ctkmdao = new ChiTietKMDAO();
                    ChiTietKMDTO ctkm = ctkmdao.timKiemCTKM(MaKM);
                    if (ctkm != null && (now.compareTo(hethan)) <= 0) {
                        double discount = (double) ctkm.getPhanTramGiam();
                        long min_decrease = ctkm.getToithieugiam();
                        double money = (discount / 100) * (double) tong_tien_hd;
                        if (money <= min_decrease) {
                            sum_with_discount = tong_tien_hd - min_decrease;
                            money = min_decrease;
                        } else {
                            sum_with_discount = tong_tien_hd - (long) money;
                        }
                        text = String.format("Giam gia: %s", currencyFormat.format(money));
                        System.out.println(sum_with_discount);
                    }
                }

                float text_width = font.getStringWidth(text) / 1000 * 16;
                contentStream2.beginText();
                contentStream2.newLineAtOffset((pageWidth - text_width) / 2, yWrite);
                contentStream2.showText(text);     
                contentStream2.endText();
                yWrite -= 24;

                String final_text = null;
                if (sum_with_discount == 0)
                {
                    final_text = String.format("Tổng tiền sau khi trừ đi giảm giá: %s", currencyFormat.format(tong_tien_hd));
                } else
                {
                    final_text = String.format("Tổng tiền sau khi trừ đi giảm giá: %s", currencyFormat.format(sum_with_discount));               
                }

                float final_text_width = font.getStringWidth(final_text) / 1000 * 16;
                contentStream2.beginText();
                contentStream2.newLineAtOffset((pageWidth - final_text_width) / 2, yWrite);
                contentStream2.showText(final_text);
                contentStream2.endText();
                contentStream2.close();
               document.save(output_path + tenfile);
               document.close();
                list_bought_pizza = null;
               JOptionPane.showMessageDialog(this, String.format("Đã tạo xong file %s được lưu ở %s", tenfile, output_path));
           } else {
               System.out.println("Người dùng đã hủy chọn thư mục lưu.");
           }
       } catch (Exception e) {
           e.printStackTrace();
       }
   }
    
    private ArrayList<String[]> convert_table_sang_list(JTable a){
        ArrayList<String[]> data = new ArrayList<>();
        int n = a.getRowCount();
        int m = a.getColumnCount();
        DefaultTableModel tableModel = (DefaultTableModel) a.getModel();
        for(int i = 0; i < n; i++){
            String[] dschuoi = new String[m];
            for (int j = 0; j < m; j++){
                dschuoi[j] = tableModel.getValueAt(i, j).toString();
            }
            data.add(dschuoi);
        }
        return data;
    }
    
    private void XoaKhoiGioHang() {
        int row = tableGioHang.getSelectedRow(); // Sửa lỗi: getSelectedRow() thay vì getRowCount()
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Bạn chưa chọn dòng cần xoá trong bảng giỏ hàng", "Lỗi", JOptionPane.ERROR_MESSAGE);               
        } else {
            int kiem_tra = JOptionPane.showConfirmDialog(this, String.format("Bạn có muốn xoá dòng %d trong bảng giỏ hàng?", row + 1), "Xác Nhận Xoá", JOptionPane.YES_NO_OPTION);
            if (kiem_tra == JOptionPane.YES_OPTION) {
                DefaultTableModel baskModel = (DefaultTableModel) tableGioHang.getModel();
                baskModel.removeRow(row);
                JOptionPane.showMessageDialog(this, "Xoá Dòng Trong Bảng Giỏ Hàng Thành Công");
            }
        }
    }
    
    private void TaoHoaDon(ArrayList<Object> tk) {
        int MaNV = Integer.parseInt(tk.get(2).toString());
        String a = ttKH.getText();
        String f = ttKM.getText();
        HoaDonDTO d = null;
        if (a.isBlank() && f.isBlank()) {
            JOptionPane.showMessageDialog(this, "Bạn không được để trống khách hàng và khuyến mãi", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } else if (a.isBlank()) {
            JOptionPane.showMessageDialog(this, "Bạn không được để trống khách hàng", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } else if (f.isBlank()) {
            JOptionPane.showMessageDialog(this, "Bạn không được để trống khuyến mãi", "Lỗi", JOptionPane.ERROR_MESSAGE);            
        } else if (tableGioHang.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Giỏ hàng không có sản phẩm", "Lỗi", JOptionPane.ERROR_MESSAGE);                
        } else {
            HoaDonBUS hd = new HoaDonBUS();
            String[] b = ttKH.getText().split("",2);  
            int n = hd.hienDSHD().size();
            int o = hd.hienDSCTHD().size();
            KhachHangBUS khbus = new KhachHangBUS();
            String[] holot_ten = b[1].trim().split(" ");
            ArrayList<KhachHangDTO> dskh = khbus.timKiemKhachHang(b[0]); // b[0] là mã khách hàng
            String sdt = dskh.get(0).getSdt();
            String diachi = dskh.get(0).getDiaChi();
            Long tong_ct_cu = dskh.get(0).getTongChiTieu();
            Long tong_ct_moi = null;
            String c = holot_ten[0].concat(" ").concat(holot_ten[1]);
            System.out.println(c);
  
            for (int i = 0; i < tableGioHang.getRowCount(); i++) {
                tong_tien_hd += parseCurrencyString(tableGioHang.getModel().getValueAt(i, 4).toString());
            }
            if (ttKM.getText().equals("Không khuyến mãi")) {
                System.out.println(String.format("Tong tien cua hoa don co ma %d ( khong khuyen mai)", n));
                System.out.println(tong_tien_hd);
                d = new HoaDonDTO(n, MaNV, Integer.parseInt(b[0]), null, 0, tong_tien_hd);
                tong_ct_moi = tong_ct_cu + tong_tien_hd;
                them_hd_va_cthd_vao_csdl(MaNV, o, null, hd, d);
            } else {
                String[] e = ttKM.getText().split("\\s+");
                int MaKM = Integer.parseInt(e[0]);
                KhuyenMaiDAO kmdao = new KhuyenMaiDAO();
                ChiTietKMDAO ctkmdao = new ChiTietKMDAO();
                KhuyenMaiDTO km = kmdao.timKiemKM(MaKM);
                LocalDate now = LocalDate.now();
                LocalDate hethan = km.getNgayKetThuc();
                ChiTietKMDTO ctkm = ctkmdao.timKiemCTKM(MaKM);
                if (ctkm != null && (now.compareTo(hethan)) <= 0) {
                    double discount = (double) ctkm.getPhanTramGiam();
                    long min_decrease = ctkm.getToithieugiam();
                    double money = (discount / 100) * (double) tong_tien_hd;
                    System.out.println(String.format("Tong tien cua hoa don chua them khuyen mai la %d", tong_tien_hd));
                    System.out.println(String.format("So tien giam cua hoa don co ma %d la %.0f", n, money));
                    long sum_with_discount = 0;
                    if (money <= min_decrease) {
                        sum_with_discount = tong_tien_hd - min_decrease;
                    } else {
                        sum_with_discount = tong_tien_hd - (long) money;
                    }
                    System.out.println(String.format("Tong tien cua hoa don co ma %d ( voi ma khuyen mai %d)", n, MaKM));
                    System.out.println(sum_with_discount);
                    d = new HoaDonDTO(n, MaNV, Integer.parseInt(b[0]), MaKM, (long) money, sum_with_discount);
                    tong_ct_moi = tong_ct_cu + sum_with_discount;                   
                    them_hd_va_cthd_vao_csdl(MaNV, o, MaKM, hd, d);
                } 
                if((now.compareTo(hethan)) > 0){
                   System.out.println(String.format("Tong tien cua hoa don co ma %d ( da het han khuyen mai)", n));
                   System.out.println(tong_tien_hd);
                   d = new HoaDonDTO(n, MaNV, Integer.parseInt(b[0]), null, 0, tong_tien_hd);
                   tong_ct_moi = tong_ct_cu + tong_tien_hd;
                   them_hd_va_cthd_vao_csdl(MaNV, o, null, hd, d);                   
                }
            }
            if(khbus.TangTongChiTieu(b[0],c, holot_ten[2], sdt, diachi,tong_ct_moi )){
                updateUI();
                System.out.println("Đã cộng tổng tiền vào chi tiêu của khách hàng có mã là "+b[0]);
            }
        }
    }
    
    private void them_hd_va_cthd_vao_csdl(int MaNV, int o, Integer maCTKM, HoaDonBUS hd, HoaDonDTO d) {
        Integer masp_trong = null;
        HashMap<Integer, Integer> dssp_damua = new HashMap<>();
        ArrayList<ChiTietHoaDonDTO> ds_cthd = new ArrayList<>();
        Boolean kt = hd.addReceipt(d);
        if (!kt) {
            JOptionPane.showMessageDialog(this, "Thêm Hoá Đơn Thất Bại", "Lỗi", JOptionPane.ERROR_MESSAGE);       
        } else {
            boolean suaSP = true;
            JOptionPane.showMessageDialog(this, "Thêm Hoá Đơn Thành Công!");
 
            int n = hd.hienDSHD().size();         
            
            //"Mã SP", "Tên Sản Phẩm", "Đơn Giá", "SL", "Thành Tiền"
            int sodong_giohang = tableGioHang.getRowCount();
            for (int i = 0; i < sodong_giohang; i++) {
                int MaSP = Integer.valueOf(tableGioHang.getModel().getValueAt(i, 0).toString());
                int slSP = Integer.valueOf(tableGioHang.getModel().getValueAt(i, 3).toString());
                long dongiaSP = parseCurrencyString(tableGioHang.getModel().getValueAt(i, 2).toString());
                long thanhtien = parseCurrencyString(tableGioHang.getModel().getValueAt(i, 4).toString());
                int l = o + i;
                ChiTietHoaDonDTO cthd = new ChiTietHoaDonDTO(l, n, MaSP, MaNV, maCTKM, slSP, dongiaSP, thanhtien);
                ds_cthd.add(cthd);
                dssp_damua.put(MaSP, slSP);
            }
            if(hd.addArrayListDR(ds_cthd)){
                JOptionPane.showMessageDialog(this, "Thêm CTHD vào CSDL thành công");
                dsCTHD.addAll(ds_cthd);
                

                System.out.println(dsCTHD);
                
                
                for (int MaSP : dssp_damua.keySet()) {
                    SanPhamDAO spdao = new SanPhamDAO();
                    ArrayList<SanPhamDTO> dssp = spdao.timKiemSanPham(String.valueOf(MaSP));
                    SanPhamDTO sp = dssp.get(0);
                    int slht = sp.getSoLuong();
                    if( slht - dssp_damua.get(MaSP) == 0){
                        masp_trong = MaSP;
                    }
                    sp.setSoLuong(slht - dssp_damua.get(MaSP));
                    if (!spdao.suaSanPham(sp)) {
                        System.out.println("Sua San Pham Bi Loi");
                        suaSP = false;
                        break;
                    }
                }
                if (suaSP) {
                    if (masp_trong != null)
                    {
                        SanPhamDAO sp = new SanPhamDAO();
                        sp.xoaSanPham(String.valueOf(masp_trong));
                    }

                    list_bought_pizza = convert_table_sang_list(tableGioHang);

                    updateUI();

                    loadDataFromSource();
                }
            }        
        }
    }
    
    private void ThemVaoGioHang() {
        int row = tableSanPham.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Bạn chưa chọn dòng trong bảng sản phẩm", "Lỗi", JOptionPane.ERROR_MESSAGE);        
        } else {
            DefaultTableModel modelBasket = (DefaultTableModel) tableGioHang.getModel();
            GioHangDTO a = lay_du_lieu_CTSP();
            Object[] rowData = {
                a.getMaSP(),
                a.getTenSP(),
                a.getDonGia(),
                a.getSoLuong(),
                a.getThanhTien()
            };
            modelBasket.addRow(rowData);
            tableSanPham.clearSelection();
            JOptionPane.showMessageDialog(this, "Thêm thông tin vao bảng giỏ hàng thành công!");
        }
    }
    
    public GioHangDTO lay_du_lieu_CTSP() {
        int MaSP = -1;
        String TenSP = null;
        long DonGia = -1L;
        int SL = 0;
        for (int i = 0; i < fields.length; i++) {
            if (fields[i] instanceof JTextField) {
                JTextField a = (JTextField) fields[i];
                switch (i) {
                    case 0:
                        MaSP = Integer.parseInt(a.getText());
                        break;
                    case 1:
                        TenSP = a.getText();
                        break;
                    case 2:
                        DonGia = Long.parseLong(a.getText());
                        break;
                }
                a.setForeground(Color.blue);
            } else if (fields[i] instanceof JSpinner) {
                JSpinner b = (JSpinner) fields[i];
                SL = (Integer) b.getValue();
            }   
        }
        GioHangDTO a = new GioHangDTO(MaSP, TenSP, DonGia, SL);
        return a;
    }
    
    private void setBackgroundForAllComponents(Component component) {
        component.setBackground(Color.WHITE);
        if (component instanceof Container container) {
            for (Component child : container.getComponents()) {
                setBackgroundForAllComponents(child);
            }
        }
    }

    private void showTableCustomer() {
        InforCustomer a = new InforCustomer();
        a.setSelectCustomerListener(new SelectCustomerOrDiscount() {
            @Override
            public void onCustomerSelected(int maKH, String tenKH) {
                ttKH.setText(String.format("%d  %s", maKH, tenKH));
            }
            
            @Override
            public void onCustomer1Selected(int maKH, String tenKH) {
                
            }

            @Override
            public void onDiscountSelected(Integer maKM, String tenKM) {
                // Không dùng
            }

            @Override
            public void onDiscount1Selected(Integer maKM, String tenKM)
            {
                
            }
        });
        a.setSelectCustomer1Listener(new SelectCustomerOrDiscount() {
            @Override
            public void onCustomerSelected(int maKH, String tenKH)
            {
     
            }

            @Override
            public void onDiscountSelected(Integer maKM, String tenKM)
            {
               
            }

            @Override
            public void onCustomer1Selected(int maKH, String tenKH)
            {
                tfMaKH.setText(String.format("%d  %s", maKH, tenKH));
            }

            @Override
            public void onDiscount1Selected(Integer maKM, String tenKM)
            {
               
            }
            
        });
        a.setVisible(true);
        a.setLocationRelativeTo(null);
        a.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void showTableDiscount(boolean isTabHD) {
        InforDiscount b = new InforDiscount(isTabHD);
        b.setSelectDiscountListener(new SelectCustomerOrDiscount() {
            @Override
            public void onCustomerSelected(int maKH, String tenKH) {
                // Không dùng
            }

            @Override
            public void onDiscountSelected(Integer maKM, String tenKM) {
                if (tenKM == null && maKM == null) {
                    ttKM.setText("Không khuyến mãi");
                } else {
                    ttKM.setText(String.format("%d  %s", maKM, tenKM));
                }
            }

            @Override
            public void onCustomer1Selected(int maKH, String tenKH)
            {
                
            }

            @Override
            public void onDiscount1Selected(Integer maCTKM, String tenCTKM)
            {
           
            }
        });
        b.setSelectDiscount1Listener(new SelectCustomerOrDiscount() {
            @Override
            public void onCustomerSelected(int maKH, String tenKH)
            {
                
            }

            @Override
            public void onDiscountSelected(Integer maKM, String tenKM)
            {
               
            }

            @Override
            public void onCustomer1Selected(int maKH, String tenKH)
            {
                
            }

            @Override
            public void onDiscount1Selected(Integer maCTKM, String tenCTKM)
            {
                if (tenCTKM == null && maCTKM == null) {
                    tfChiTietKM.setText("Không khuyến mãi");
                } else {
                    tfChiTietKM.setText(String.format("%d  %s", maCTKM, tenCTKM));
                }             
            }
            
        });
        b.setVisible(true);
        b.setLocationRelativeTo(null);
        b.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void initComponents() {
        UIManager.put("TabbedPane.selected", Color.black);
        UIManager.put("TabbedPane.selectedForeground", Color.white);
        UIManager.put("TableHeader.background", Color.white);
        UIManager.put("TableHeader.foreground", Color.red);
        

        JTabbedPane tabbedPane = new JTabbedPane();
        panelHoaDon = new JPanel();
        panelBanHang = new JPanel();

        // === Tab Hóa Đơn ===
        panelHoaDon.setLayout(new GridBagLayout());
        GridBagConstraints gbcHoaDonMain = new GridBagConstraints();
        gbcHoaDonMain.insets = new Insets(5, 5, 5, 5);

        // Panel for the left side (filters and HoaDon table)
        JPanel panelHoaDonLeft = new JPanel(new GridBagLayout());
        GridBagConstraints gbcLeft = new GridBagConstraints();
        gbcLeft.insets = new Insets(3, 3, 3, 3);
        gbcLeft.anchor = GridBagConstraints.WEST;

        // Panel for the right side (details and ChiTietHoaDon table)
        JPanel panelHoaDonRight = new JPanel(new GridBagLayout());
        GridBagConstraints gbcRight = new GridBagConstraints();
        gbcRight.insets = new Insets(3, 3, 3, 3);
        gbcRight.anchor = GridBagConstraints.WEST;

        // --- Titles ---
        JLabel lblDanhSachHoaDon = new JLabel("Hoá đơn");
        lblDanhSachHoaDon.setFont(new java.awt.Font("Times New Roman", 1, 18));
        gbcLeft.gridx = 0; gbcLeft.gridy = 0; gbcLeft.gridwidth = 2; gbcLeft.anchor = GridBagConstraints.CENTER;
        panelHoaDonLeft.add(lblDanhSachHoaDon, gbcLeft);
        gbcLeft.gridwidth = 1; gbcLeft.anchor = GridBagConstraints.WEST; // Reset

        JLabel lblChiTietHoaDon = new JLabel("Chi tiết Hoá đơn");
        lblChiTietHoaDon.setFont(new java.awt.Font("Times New Roman", 1, 18));
        gbcRight.gridx = 0; gbcRight.gridy = 0; gbcRight.gridwidth = 2; gbcRight.anchor = GridBagConstraints.CENTER;
        panelHoaDonRight.add(lblChiTietHoaDon, gbcRight);
        gbcRight.gridwidth = 1; gbcRight.anchor = GridBagConstraints.WEST; // Reset

        Font labelFont = new java.awt.Font("Times New Roman", 0, 14);
        Font fieldFont = new java.awt.Font("Times New Roman", 0, 14);
        Dimension fieldMinSize = new Dimension(150, 25); // Minimum size for text fields/combos
        Dimension buttonMinSize = new Dimension(35, 25);

        // --- Left Panel Components (panelHoaDonLeft) ---
        int leftY = 1; // Start Y position for gbcLeft

        // Mã Hoá Đơn
        JLabel lblHD = new JLabel("Mã Hoá Đơn:");
        lblHD.setFont(labelFont);
        gbcLeft.gridx = 0; gbcLeft.gridy = leftY; gbcLeft.fill = GridBagConstraints.NONE; gbcLeft.weightx = 0;
        panelHoaDonLeft.add(lblHD, gbcLeft);

        HoaDonBUS hdbus = new HoaDonBUS();
        ArrayList<HoaDonDTO> dshd = hdbus.hienDSHD();
        String[] ds_mahd = new String[dshd.size() + 1];
        ds_mahd[0] = "Chọn Mã Hoá Đơn";
        for (int i = 0; i < dshd.size(); i++) {
            ds_mahd[i + 1] = String.valueOf(dshd.get(i).getMaHD());
        }
        cbbMaHD = new JComboBox<>(ds_mahd);
        cbbMaHD.setFont(fieldFont);
        cbbMaHD.setPreferredSize(fieldMinSize);
        gbcLeft.gridx = 1; gbcLeft.fill = GridBagConstraints.HORIZONTAL; gbcLeft.weightx = 1.0;
        panelHoaDonLeft.add(cbbMaHD, gbcLeft);
        leftY++;

        // Mã Khách Hàng
        JLabel lblKH = new JLabel("Mã Khách Hàng:");
        lblKH.setFont(labelFont);
        gbcLeft.gridx = 0; gbcLeft.gridy = leftY; gbcLeft.fill = GridBagConstraints.NONE; gbcLeft.weightx = 0;
        panelHoaDonLeft.add(lblKH, gbcLeft);

        JPanel khPanel = new JPanel(new BorderLayout(2,0)); // TextField and Button
        tfMaKH = new JTextField();
        tfMaKH.setFont(fieldFont);
        tfMaKH.setPreferredSize(new Dimension(100, 25)); // Allow button to take some space
        khPanel.add(tfMaKH, BorderLayout.CENTER);
        btnKH = new JButton();
        FontIcon ellipsis = FontIcon.of(FontAwesomeSolid.ELLIPSIS_H, 18, Color.BLACK); // Slightly smaller icon
        btnKH.setIcon(ellipsis);
        btnKH.setPreferredSize(buttonMinSize);
        khPanel.add(btnKH, BorderLayout.EAST);
        gbcLeft.gridx = 1; gbcLeft.fill = GridBagConstraints.HORIZONTAL; gbcLeft.weightx = 1.0;
        panelHoaDonLeft.add(khPanel, gbcLeft);
        leftY++;

        // Date Radio Buttons
        JPanel dateRadioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0)); // Tighter packing
        beforeDateBtn = new JRadioButton("Trước ngày"); beforeDateBtn.setFont(labelFont);
        betweenDateBtn = new JRadioButton("Trong khoảng ngày"); betweenDateBtn.setFont(labelFont);
        afterDateBtn = new JRadioButton("Sau ngày"); afterDateBtn.setFont(labelFont);
        groupDate = new ButtonGroup();
        groupDate.add(beforeDateBtn); groupDate.add(betweenDateBtn); groupDate.add(afterDateBtn);
        dateRadioPanel.add(beforeDateBtn); dateRadioPanel.add(betweenDateBtn); dateRadioPanel.add(afterDateBtn);
        gbcLeft.gridx = 0; gbcLeft.gridy = leftY; gbcLeft.gridwidth = 2; gbcLeft.fill = GridBagConstraints.HORIZONTAL;
        panelHoaDonLeft.add(dateRadioPanel, gbcLeft);
        gbcLeft.gridwidth = 1; // Reset
        leftY++;

        // DatePicker 1
        dateCB = new JCheckBox("Ngày 1"); dateCB.setFont(labelFont);
        gbcLeft.gridx = 0; gbcLeft.gridy = leftY; gbcLeft.fill = GridBagConstraints.NONE; gbcLeft.weightx = 0;
        panelHoaDonLeft.add(dateCB, gbcLeft);
        DatePickerSettings dateSettings = new DatePickerSettings();
        dateSettings.setFormatForDatesCommonEra(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        dpStartDate = new DatePicker(dateSettings);
        dpStartDate.getComponentDateTextField().setFont(fieldFont);
        dpStartDate.getComponentDateTextField().setPreferredSize(fieldMinSize);
        gbcLeft.gridx = 1; gbcLeft.fill = GridBagConstraints.HORIZONTAL; gbcLeft.weightx = 1.0;
        panelHoaDonLeft.add(dpStartDate, gbcLeft);
        leftY++;

        // DatePicker 2
        dateCB1 = new JCheckBox("Ngày 2"); dateCB1.setFont(labelFont);
        gbcLeft.gridx = 0; gbcLeft.gridy = leftY; gbcLeft.fill = GridBagConstraints.NONE; gbcLeft.weightx = 0;
        panelHoaDonLeft.add(dateCB1, gbcLeft);
        DatePickerSettings dateSettings1 = new DatePickerSettings();
        dateSettings1.setFormatForDatesCommonEra(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        dpEndDate = new DatePicker(dateSettings1);
        dpEndDate.getComponentDateTextField().setFont(fieldFont);
        dpEndDate.getComponentDateTextField().setPreferredSize(fieldMinSize);
        gbcLeft.gridx = 1; gbcLeft.fill = GridBagConstraints.HORIZONTAL; gbcLeft.weightx = 1.0;
        panelHoaDonLeft.add(dpEndDate, gbcLeft);
        leftY++;

        // Money Radio Buttons
        JPanel moneyRadioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        lessMoneyBtn = new JRadioButton("nhỏ hơn giá"); lessMoneyBtn.setFont(labelFont);
        rangeMoneyBtn = new JRadioButton("trong khoảng"); rangeMoneyBtn.setFont(labelFont);
        moreMoneyBtn = new JRadioButton("lớn hơn giá"); moreMoneyBtn.setFont(labelFont);
        groupMoney = new ButtonGroup();
        groupMoney.add(lessMoneyBtn); groupMoney.add(rangeMoneyBtn); groupMoney.add(moreMoneyBtn);
        moneyRadioPanel.add(lessMoneyBtn); moneyRadioPanel.add(rangeMoneyBtn); moneyRadioPanel.add(moreMoneyBtn);
        gbcLeft.gridx = 0; gbcLeft.gridy = leftY; gbcLeft.gridwidth = 2; gbcLeft.fill = GridBagConstraints.HORIZONTAL;
        panelHoaDonLeft.add(moneyRadioPanel, gbcLeft);
        gbcLeft.gridwidth = 1; // Reset
        leftY++;

        // Min Money
        moneyCB = new JCheckBox("Giá tiền 1"); moneyCB.setFont(labelFont);
        gbcLeft.gridx = 0; gbcLeft.gridy = leftY; gbcLeft.fill = GridBagConstraints.NONE; gbcLeft.weightx = 0;
        panelHoaDonLeft.add(moneyCB, gbcLeft);
        currencyFormat = new DecimalFormat("#,### VNĐ");
        numberFormatter = new NumberFormatter(currencyFormat);
        numberFormatter.setValueClass(Long.class);
        numberFormatter.setAllowsInvalid(false);
        numberFormatter.setCommitsOnValidEdit(true);
        numberFormatter.setAllowsInvalid(true); // Cho phép nhập giá trị không hợp lệ
        numberFormatter.setCommitsOnValidEdit(false); // Không cam kết ngay lập tức
        tfMinMoney = new JFormattedTextField(numberFormatter);
        tfMinMoney.setFont(fieldFont); tfMinMoney.setPreferredSize(fieldMinSize);
        tfMinMoney.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                try {
      
                    Long value = (Long) numberFormatter.stringToValue(tfMinMoney.getText());
                    // Nếu giá trị hợp lệ, không làm gì cả
                } catch (Exception ex) {
                    // Hiển thị thông báo lỗi
                    JOptionPane.showMessageDialog(panelHoaDon, "Tui chỉ nhận giá tiền theo định dạng #,### VNĐ.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    tfMinMoney.requestFocus(); // Quay lại trường nhập liệu
                }
            }
        });
        gbcLeft.gridx = 1; gbcLeft.fill = GridBagConstraints.HORIZONTAL; gbcLeft.weightx = 1.0;
        panelHoaDonLeft.add(tfMinMoney, gbcLeft);
        leftY++;

        // Max Money
        moneyCB1 = new JCheckBox("Giá tiền 2"); moneyCB1.setFont(labelFont);
        gbcLeft.gridx = 0; gbcLeft.gridy = leftY; gbcLeft.fill = GridBagConstraints.NONE; gbcLeft.weightx = 0;
        panelHoaDonLeft.add(moneyCB1, gbcLeft);
        tfMaxMoney = new JFormattedTextField(numberFormatter);
        tfMaxMoney.setFont(fieldFont); tfMaxMoney.setPreferredSize(fieldMinSize);
        tfMaxMoney.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                try {
                    Long value = (Long) numberFormatter.stringToValue(tfMaxMoney.getText());
                    // Nếu giá trị hợp lệ, không làm gì cả
                } catch (Exception ex) {
                    // Hiển thị thông báo lỗi
                    JOptionPane.showMessageDialog(panelHoaDon, "Tui chỉ nhận giá tiền theo định dạng #,### VNĐ.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    tfMaxMoney.requestFocus(); // Quay lại trường nhập liệu
                }
            }
        });
        gbcLeft.gridx = 1; gbcLeft.fill = GridBagConstraints.HORIZONTAL; gbcLeft.weightx = 1.0;
        panelHoaDonLeft.add(tfMaxMoney, gbcLeft);
        leftY++;

        // Mã Khuyến Mãi (Filter)
        JLabel lblKMFilter = new JLabel("Mã Chi Tiết Khuyến Mãi:");
        lblKMFilter.setFont(labelFont);
        gbcLeft.gridx = 0; gbcLeft.gridy = leftY; gbcLeft.fill = GridBagConstraints.NONE; gbcLeft.weightx = 0;
        panelHoaDonLeft.add(lblKMFilter, gbcLeft);
        JPanel kmFilterPanel = new JPanel(new BorderLayout(2, 0));
        tfChiTietKM = new JTextField();
        tfChiTietKM.setFont(fieldFont);
        tfChiTietKM.setPreferredSize(new Dimension(100, 25));
        kmFilterPanel.add(tfChiTietKM, BorderLayout.CENTER);
        btnCTKM = new JButton(FontIcon.of(FontAwesomeSolid.ELLIPSIS_H, 18, Color.BLACK));
        btnCTKM.setPreferredSize(buttonMinSize);
        kmFilterPanel.add(btnCTKM, BorderLayout.EAST);
        gbcLeft.gridx = 1; gbcLeft.fill = GridBagConstraints.HORIZONTAL; gbcLeft.weightx = 1.0;
        panelHoaDonLeft.add(kmFilterPanel, gbcLeft);
        leftY++;
        // Mã Nhân Viên
        JLabel lblMaNV = new JLabel("Mã Nhân Viên:");
        lblMaNV.setFont(labelFont);
        gbcLeft.gridx = 0; gbcLeft.gridy = leftY; gbcLeft.fill = GridBagConstraints.NONE; gbcLeft.weightx = 0;
        panelHoaDonLeft.add(lblMaNV, gbcLeft);
        
        NhanVienDAO nvdao = new NhanVienDAO();
        ArrayList<NhanVienDTO> dsnv= nvdao.getDanhSachNhanVien();
        String[] maNVOptions = new String[dsnv.size()+ 1];
        maNVOptions[0] = "Chọn Mã Nhân Viên";
        for(int i = 0; i < dsnv.size(); i++){
            maNVOptions[i + 1] = String.valueOf(dsnv.get(i).getMaNV());
        }
        cbMaNV = new JComboBox<>(maNVOptions);
        cbMaNV.setFont(fieldFont);
        gbcLeft.gridx = 1; gbcLeft.fill = GridBagConstraints.HORIZONTAL; gbcLeft.weightx = 1.0;
        panelHoaDonLeft.add(cbMaNV, gbcLeft);
        leftY++;
        // Tìm Kiếm Button
        btnTimKiem = new JButton("Tìm", FontIcon.of(FontAwesomeSolid.SEARCH, 14, Color.BLACK));
        btnTimKiem.setFont(labelFont);
        gbcLeft.gridx = 0; gbcLeft.gridy = leftY; gbcLeft.gridwidth = 2;
        gbcLeft.anchor = GridBagConstraints.CENTER; gbcLeft.fill = GridBagConstraints.NONE;
        panelHoaDonLeft.add(btnTimKiem, gbcLeft);
        gbcLeft.gridwidth = 1; gbcLeft.anchor = GridBagConstraints.WEST; // Reset
        leftY++;

        // Table HoaDon
        tableHoaDon = new JTable()
        {
            @Override
            public Component prepareRenderer(
                TableCellRenderer renderer, int row, int column)
            {
                Component c = super.prepareRenderer(renderer, row, column);

                //  Alternate row color 

                if (!isRowSelected(row)){
                    c.setBackground(row % 2 == 0 ? Color.yellow : Color.orange);
                    c.setForeground(row % 2 == 0 ? Color.black : Color.white);
                }else{
                    c.setBackground(Color.blue);
                    c.setForeground(Color.white);
                }  

                return c;
            }
        };
        // Tạo JScrollPane và thêm bảng vào nó
        JScrollPane scrollHoaDon = new JScrollPane(tableHoaDon);
        scrollHoaDon.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        gbcLeft.gridx = 0; gbcLeft.gridy = leftY; gbcLeft.gridwidth = 2;
        gbcLeft.fill = GridBagConstraints.BOTH; gbcLeft.weightx = 1.0; gbcLeft.weighty = 1.0; // Table takes up remaining space
        panelHoaDonLeft.add(scrollHoaDon, gbcLeft);
        leftY++;

        // Export Excel HoaDon Button
        btnXuatExcelHoaDon = new JButton("Xuất Excel Hoá Đơn", FontIcon.of(FontAwesomeSolid.FILE_EXCEL, 30, Color.BLACK));
        btnXuatExcelHoaDon.setFont(labelFont);
        gbcLeft.gridx = 0; gbcLeft.gridy = leftY; gbcLeft.gridwidth = 2;
        gbcLeft.anchor = GridBagConstraints.CENTER; gbcLeft.fill = GridBagConstraints.NONE;
        gbcLeft.weighty = 0; // Button doesn't stretch vertically
        panelHoaDonLeft.add(btnXuatExcelHoaDon, gbcLeft);


        // --- Right Panel Components (panelHoaDonRight) ---
        int rightY = 1; // Start Y for gbcRight

        JLabel LblDiscountName = new JLabel("Tên Khuyến Mãi:"); LblDiscountName.setFont(labelFont);
        gbcRight.gridx = 0; gbcRight.gridy = rightY; gbcRight.fill = GridBagConstraints.NONE; gbcRight.weightx = 0;
        panelHoaDonRight.add(LblDiscountName, gbcRight);
        tfNameDiscount = new JTextField(); tfNameDiscount.setFont(fieldFont); tfNameDiscount.setEditable(false); tfNameDiscount.setPreferredSize(fieldMinSize);
        gbcRight.gridx = 1; gbcRight.fill = GridBagConstraints.HORIZONTAL; gbcRight.weightx = 1.0;
        panelHoaDonRight.add(tfNameDiscount, gbcRight);
        rightY++;

        JLabel LblDetailDiscountName = new JLabel("Tên Chi Tiết Khuyến Mãi:"); LblDetailDiscountName.setFont(labelFont);
        gbcRight.gridx = 0; gbcRight.gridy = rightY; gbcRight.fill = GridBagConstraints.NONE; gbcRight.weightx = 0;
        panelHoaDonRight.add(LblDetailDiscountName, gbcRight);
        tfDetailDiscountName = new JTextField(); tfDetailDiscountName.setFont(fieldFont); tfDetailDiscountName.setEditable(false); tfDetailDiscountName.setPreferredSize(fieldMinSize);
        gbcRight.gridx = 1; gbcRight.fill = GridBagConstraints.HORIZONTAL; gbcRight.weightx = 1.0;
        panelHoaDonRight.add(tfDetailDiscountName, gbcRight);
        rightY++;

//        JLabel lblTongCong = new JLabel("Tổng Cộng:"); lblTongCong.setFont(labelFont);
//        gbcRight.gridx = 0; gbcRight.gridy = rightY; gbcRight.fill = GridBagConstraints.NONE; gbcRight.weightx = 0;
//        panelHoaDonRight.add(lblTongCong, gbcRight);
//        tfTongCong = new JTextField(); tfTongCong.setFont(fieldFont); tfTongCong.setEditable(false); tfTongCong.setPreferredSize(fieldMinSize);
//        gbcRight.gridx = 1; gbcRight.fill = GridBagConstraints.HORIZONTAL; gbcRight.weightx = 1.0;
//        panelHoaDonRight.add(tfTongCong, gbcRight);
//        rightY++;
//
//        JLabel lblNgayLap = new JLabel("Ngày Lập Hoá Đơn:"); lblNgayLap.setFont(labelFont);
//        gbcRight.gridx = 0; gbcRight.gridy = rightY; gbcRight.fill = GridBagConstraints.NONE; gbcRight.weightx = 0;
//        panelHoaDonRight.add(lblNgayLap, gbcRight);
//        ftNgayLap = new JFormattedTextField(); ftNgayLap.setFont(fieldFont); ftNgayLap.setEnabled(false); ftNgayLap.setEditable(false); ftNgayLap.setPreferredSize(fieldMinSize);
//        gbcRight.gridx = 1; gbcRight.fill = GridBagConstraints.HORIZONTAL; gbcRight.weightx = 1.0;
//        panelHoaDonRight.add(ftNgayLap, gbcRight);
//        rightY++;

        // Table ChiTietHoaDon
        tableChiTietHoaDon = new JTable()
        {
            @Override
            public Component prepareRenderer(
                TableCellRenderer renderer, int row, int column)
            {
                Component c = super.prepareRenderer(renderer, row, column);

                //  Alternate row color 

                if (!isRowSelected(row)){
                    c.setBackground(row % 2 == 0 ? Color.yellow : Color.orange);
                    c.setForeground(row % 2 == 0 ? Color.black : Color.white);
                }else{
                    c.setBackground(Color.blue);
                    c.setForeground(Color.white);
                }  

                return c;
            }
        };
        JScrollPane scrollChiTietHoaDon = new JScrollPane(tableChiTietHoaDon);
        scrollChiTietHoaDon.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        gbcRight.gridx = 0; gbcRight.gridy = rightY; gbcRight.gridwidth = 2;
        gbcRight.fill = GridBagConstraints.BOTH; gbcRight.weightx = 1.0; gbcRight.weighty = 1.0; // Table takes up remaining space
        panelHoaDonRight.add(scrollChiTietHoaDon, gbcRight);
        rightY++;

        // Export Excel ChiTiet Button
        btnXuatExcelChiTiet = new JButton("Xuất Excel Chi Tiết Hoá Đơn", FontIcon.of(FontAwesomeSolid.FILE_EXCEL, 30, Color.BLACK));
        btnXuatExcelChiTiet.setFont(labelFont);
        gbcRight.gridx = 0; gbcRight.gridy = rightY; gbcRight.gridwidth = 2;
        gbcRight.anchor = GridBagConstraints.CENTER; gbcRight.fill = GridBagConstraints.NONE;
        gbcRight.weighty = 0; // Button doesn't stretch
        panelHoaDonRight.add(btnXuatExcelChiTiet, gbcRight);

        // Add left and right panels to panelHoaDon using a JSplitPane
        JSplitPane hoaDonSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelHoaDonLeft, panelHoaDonRight);
        hoaDonSplitPane.setResizeWeight(0.5); // Adjust the split ratio as needed (e.g., 0.4 for 40% to left)
        gbcHoaDonMain.gridx = 0; gbcHoaDonMain.gridy = 0;
        gbcHoaDonMain.weightx = 1.0; gbcHoaDonMain.weighty = 1.0;
        gbcHoaDonMain.fill = GridBagConstraints.BOTH;
        panelHoaDon.add(hoaDonSplitPane, gbcHoaDonMain);


        // === Tab Bán Hàng ===
        panelBanHang.setLayout(new BorderLayout(10, 10)); // Main layout for BanHang tab

        // --- Left side of BanHang: Product List and Cart ---
        JPanel banHangLeftOuterPanel = new JPanel(new BorderLayout(5,5));

        JLabel lblDanhSachSP = new JLabel("Danh Sách Sản Phẩm", SwingConstants.CENTER);
        lblDanhSachSP.setFont(new java.awt.Font("Times New Roman", 1, 18));
        tableSanPham = new JTable()
        {
            @Override
            public Component prepareRenderer(
                TableCellRenderer renderer, int row, int column)
            {
                Component c = super.prepareRenderer(renderer, row, column);

                //  Alternate row color 

                if (!isRowSelected(row)){
                    c.setBackground(row % 2 == 0 ? Color.yellow : Color.orange);
                    c.setForeground(row % 2 == 0 ? Color.black : Color.white);
                }else{
                    c.setBackground(Color.blue);
                    c.setForeground(Color.white);
                }  
                return c;
            }
        };
        JScrollPane scrollSanPham = new JScrollPane(tableSanPham);
        scrollSanPham.setPreferredSize(new Dimension(400, 200)); // Give it a preferred starting size

        JPanel productListPanel = new JPanel(new BorderLayout());
        productListPanel.add(lblDanhSachSP, BorderLayout.NORTH);
        productListPanel.add(scrollSanPham, BorderLayout.CENTER);

        JLabel lblGioHang = new JLabel("Giỏ Hàng", SwingConstants.CENTER);
        lblGioHang.setFont(new java.awt.Font("Times New Roman", 1, 18));
        tableGioHang = new JTable()
        {
            @Override
            public Component prepareRenderer(
                TableCellRenderer renderer, int row, int column)
            {
                Component c = super.prepareRenderer(renderer, row, column);

                //  Alternate row color 

                if (!isRowSelected(row)){
                    c.setBackground(row % 2 == 0 ? Color.yellow : Color.orange);
                    c.setForeground(row % 2 == 0 ? Color.black : Color.white);
                }else{
                    c.setBackground(Color.blue);
                    c.setForeground(Color.white);
                }  

                return c;
            }
        };
        tableHoaDon.setPreferredSize(new Dimension(600,400));
        JScrollPane scrollGioHang = new JScrollPane(tableGioHang);
        scrollGioHang.setPreferredSize(new Dimension(400, 150));

        JPanel cartPanel = new JPanel(new BorderLayout());
        cartPanel.add(lblGioHang, BorderLayout.NORTH);
        cartPanel.add(scrollGioHang, BorderLayout.CENTER);

        JSplitPane banHangLeftSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, productListPanel, cartPanel);
        banHangLeftSplit.setResizeWeight(0.6); // Products list gets a bit more space
        banHangLeftOuterPanel.add(banHangLeftSplit, BorderLayout.CENTER);


        // --- Right side of BanHang: Product Details and Actions ---
        JPanel banHangRightOuterPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbcBanHangRight = new GridBagConstraints();
        gbcBanHangRight.insets = new Insets(5, 10, 5, 10); // More horizontal padding
        gbcBanHangRight.anchor = GridBagConstraints.WEST;

        int bhRightY = 0;

        // Title and Refresh Button for Product Details
        JPanel chiTietSPHeaderPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        JLabel lblChiTietSP = new JLabel("Chi Tiết Sản Phẩm");
        lblChiTietSP.setFont(new java.awt.Font("Times New Roman", 1, 20));
        chiTietSPHeaderPanel.add(lblChiTietSP);

        // Assuming Refresh-icon.png is in the specified path relative to the execution directory or classpath
        ImageIcon refreshIcon = null;
        try {
            refreshIcon = new ImageIcon(getClass().getResource("/image/Refresh-icon.png")); // For resources in JAR
             if (refreshIcon.getImage().getWidth(null) < 0) { // Fallback for running from IDE
                refreshIcon = new ImageIcon("src/main/resources/image/Refresh-icon.png");
            }
        } catch (Exception e) { System.err.println("Refresh icon not found: " + e.getMessage());}

        if (refreshIcon != null && refreshIcon.getImage().getWidth(null) > 0) {
            Image scaledRefresh = refreshIcon.getImage().getScaledInstance(28, 28, Image.SCALE_SMOOTH);
            btnRefreshChiTietSP = new JButton(new ImageIcon(scaledRefresh));
        } else {
            btnRefreshChiTietSP = new JButton("R"); // Fallback text
        }
        btnRefreshChiTietSP.setToolTipText("Làm mới Chi Tiết SP");
        btnRefreshChiTietSP.setPreferredSize(new Dimension(32, 32));
        chiTietSPHeaderPanel.add(btnRefreshChiTietSP);
        btnRefreshChiTietSP.addActionListener(e -> reset_toan_bo_field());

        gbcBanHangRight.gridx = 0; gbcBanHangRight.gridy = bhRightY++; gbcBanHangRight.gridwidth = 2;
        gbcBanHangRight.fill = GridBagConstraints.HORIZONTAL; gbcBanHangRight.anchor = GridBagConstraints.CENTER;
        banHangRightOuterPanel.add(chiTietSPHeaderPanel, gbcBanHangRight);
        gbcBanHangRight.gridwidth = 1; gbcBanHangRight.fill = GridBagConstraints.NONE; gbcBanHangRight.anchor = GridBagConstraints.WEST; // Reset

        // Product Detail Fields
        String[] labels = {"Mã SP:", "Tên SP:", "Đơn Giá:", "Số Lượng Mua:", "Loại SP:", "Nhân Viên Lập:", "Khách Hàng:", "Khuyến Mãi:"};
        fields = new JComponent[labels.length]; // Ensure fields array is initialized

        for (int i = 0; i < labels.length; i++) {
            JLabel label = new JLabel(labels[i]);
            label.setFont(labelFont);
            gbcBanHangRight.gridx = 0; gbcBanHangRight.gridy = bhRightY; gbcBanHangRight.weightx = 0.1; gbcBanHangRight.fill = GridBagConstraints.NONE;
            banHangRightOuterPanel.add(label, gbcBanHangRight);

            gbcBanHangRight.gridx = 1; gbcBanHangRight.weightx = 0.9; gbcBanHangRight.fill = GridBagConstraints.HORIZONTAL;
            JComponent fieldComponent;
            if (labels[i].equals("Số Lượng Mua:")) {
                fields[i] = new JSpinner(new SpinnerNumberModel(0, 0, slTD, 1)); // slTD is max quantity
                ((JSpinner) fields[i]).setEnabled(false); // Initially disabled
                fieldComponent = fields[i];
            } else if (labels[i].equals("Khách Hàng:")) {
                JPanel khDetailPanel = new JPanel(new BorderLayout(3,0));
                ttKH = new JTextField(); ttKH.setFont(fieldFont); ttKH.setEditable(false);
                khDetailPanel.add(ttKH, BorderLayout.CENTER);
                fields[i] = new JButton(FontIcon.of(FontAwesomeSolid.ELLIPSIS_H, 18, Color.BLACK));
                ((JButton)fields[i]).setPreferredSize(new Dimension(35,28));
                khDetailPanel.add(fields[i], BorderLayout.EAST);
                fieldComponent = khDetailPanel;
            } else if (labels[i].equals("Khuyến Mãi:")) {
                JPanel kmDetailPanel = new JPanel(new BorderLayout(3,0));
                ttKM = new JTextField(); ttKM.setFont(fieldFont); ttKM.setEditable(false);
                kmDetailPanel.add(ttKM, BorderLayout.CENTER);
                fields[i] = new JButton(FontIcon.of(FontAwesomeSolid.ELLIPSIS_H, 18, Color.BLACK));
                ((JButton)fields[i]).setPreferredSize(new Dimension(35,28));
                kmDetailPanel.add(fields[i], BorderLayout.EAST);
                fieldComponent = kmDetailPanel;
            } else {
                fields[i] = new JTextField();
                ((JTextField) fields[i]).setEditable(false); // Most fields are display-only initially
                fieldComponent = fields[i];
            }
            fieldComponent.setFont(fieldFont);
            fieldComponent.setPreferredSize(new Dimension(180, 28)); // Preferred width for fields
            banHangRightOuterPanel.add(fieldComponent, gbcBanHangRight);
            bhRightY++;
        }

        // Product Image
        lblAnhSanPham = new JLabel("Ảnh SP", SwingConstants.CENTER);
        lblAnhSanPham.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        lblAnhSanPham.setPreferredSize(new Dimension(IMAGE_WIDTH, IMAGE_HEIGHT));
        gbcBanHangRight.gridx = 0; gbcBanHangRight.gridy = bhRightY++; gbcBanHangRight.gridwidth = 2;
        gbcBanHangRight.anchor = GridBagConstraints.CENTER; gbcBanHangRight.fill = GridBagConstraints.NONE;
        gbcBanHangRight.weighty = 0.3; // Give some weight for vertical distribution if space
        banHangRightOuterPanel.add(lblAnhSanPham, gbcBanHangRight);
        gbcBanHangRight.gridwidth = 1; gbcBanHangRight.anchor = GridBagConstraints.WEST; gbcBanHangRight.weighty = 0; // Reset

        // Action Buttons Panel
        JPanel actionButtonPanel = new JPanel(new GridLayout(2, 2, 10, 10)); // 2x2 grid for buttons
        Dimension actionButtonSize = new Dimension(160, 35);

        btnThemVaoGio = new JButton("Thêm vào Giỏ", FontIcon.of(FontAwesomeSolid.SHOPPING_BASKET, 30, Color.GREEN));
        btnThemVaoGio.setFont(labelFont); btnThemVaoGio.setPreferredSize(actionButtonSize);
        actionButtonPanel.add(btnThemVaoGio);

        btnXoaKhoiGio = new JButton("Xóa khỏi Giỏ", FontIcon.of(FontAwesomeSolid.TIMES, 30, Color.RED));
        btnXoaKhoiGio.setFont(labelFont); btnXoaKhoiGio.setPreferredSize(actionButtonSize);
        actionButtonPanel.add(btnXoaKhoiGio);

        btnTaoHoaDon = new JButton("Tạo Hoá Đơn", FontIcon.of(FontAwesomeSolid.RECEIPT, 30, Color.BLUE));
        btnTaoHoaDon.setFont(labelFont); btnTaoHoaDon.setPreferredSize(actionButtonSize);
        actionButtonPanel.add(btnTaoHoaDon);

        btnXuatHoaDon = new JButton("Xuất PDF", FontIcon.of(FontAwesomeSolid.FILE_PDF, 30, Color.DARK_GRAY));
        btnXuatHoaDon.setFont(labelFont); btnXuatHoaDon.setToolTipText("Xuất PDF Hoá Đơn"); btnXuatHoaDon.setPreferredSize(actionButtonSize);
        actionButtonPanel.add(btnXuatHoaDon);

        gbcBanHangRight.gridx = 0; gbcBanHangRight.gridy = bhRightY++; gbcBanHangRight.gridwidth = 2;
        gbcBanHangRight.fill = GridBagConstraints.HORIZONTAL; gbcBanHangRight.anchor = GridBagConstraints.SOUTH; // Buttons at the bottom
        gbcBanHangRight.weighty = 1.0; // Push buttons to bottom if there's vertical space
        banHangRightOuterPanel.add(actionButtonPanel, gbcBanHangRight);

        // Main SplitPane for BanHang Tab
        JSplitPane banHangMainSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, banHangLeftOuterPanel, banHangRightOuterPanel);
        banHangMainSplit.setResizeWeight(0.45); // Give a bit more space to the right panel for details
        panelBanHang.add(banHangMainSplit, BorderLayout.CENTER);


        // Add tabs to TabbedPane
        tabbedPane.addTab("Bán Hàng", panelBanHang);
        tabbedPane.addTab("Hoá Đơn", panelHoaDon);

        // Set main layout for this JPanel
        setLayout(new BorderLayout());
        add(tabbedPane, BorderLayout.CENTER);

        setPreferredSize(new Dimension(1250, 750)); // This is a good hint for pack() when used in a JFrame

        loadProductImage(DEFAULT_IMAGE_PATH); // Load default image
        setBackgroundForAllComponents(this);
    }

    private void reset_toan_bo_field() {
        int kiem_tra = JOptionPane.showConfirmDialog(this, "Bạn có muốn reset toàn bộ textbox trong CTSP?", "Xác Nhận Xoá", JOptionPane.YES_NO_OPTION);
        if (kiem_tra == JOptionPane.YES_OPTION) {
            String c = "";
            for (int i = 0; i < fields.length; i++) {
                if (fields[i] instanceof JTextField) {
                    JTextField a = (JTextField) fields[i];
                    switch (i) {
                        case 0:
                            a.setText(c);
                            break;
                        case 1:
                            a.setText(c);
                            break;
                        case 2:
                            a.setText(c);
                            break;
                        case 4:
                            a.setText(c);
                            break;
                        case 5:
                            a.setText(c);
                            break;
                    }
                    a.setForeground(Color.blue);
                } else if (fields[i] instanceof JSpinner) {
                    JSpinner b = (JSpinner) fields[i];
                    b.setModel(new SpinnerNumberModel(0, 0, slTD, 1));
                    b.setEnabled(false);
                }   
            }
            ttKH.setText(c);
            ttKM.setText(c);
            loadProductImage(DEFAULT_IMAGE_PATH);
            JOptionPane.showMessageDialog(this, "Đã làm mới chi tiết sản phẩm!");
        }
    }
    
    private void setupTableModels(ArrayList<Object> tk) {
        tableHoaDon.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tableChiTietHoaDon.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tableHoaDon.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Mã Hoá Đơn", "Mã Nhân Viên", "Mã Khách Hàng","Mã Chi Tiết Khuyến Mãi", "Ngày Lập", "Số Tiền Giảm" ,"Tổng Tiền"}
        ) {
             @Override
             public boolean isCellEditable(int row, int column) { return false; } 
        });
        tableHoaDon.setFont(new java.awt.Font("Times New Roman", 0, 18));
        tableHoaDon.setRowHeight(25);
        // Đặt chiều rộng cho các cột
        tableHoaDon.setPreferredSize(new Dimension(1200, 200));
        tableHoaDon.getColumnModel().getColumn(0).setPreferredWidth(200); // Mã Hoá Đơn
        tableHoaDon.getColumnModel().getColumn(1).setPreferredWidth(200); // Mã Nhân Viên
        tableHoaDon.getColumnModel().getColumn(2).setPreferredWidth(200); // Mã Khách Hàng
        tableHoaDon.getColumnModel().getColumn(3).setPreferredWidth(200); // Mã Chi Tiết Khuyến Mãi
        tableHoaDon.getColumnModel().getColumn(4).setPreferredWidth(200); // Ngày Lập
        tableHoaDon.getColumnModel().getColumn(5).setPreferredWidth(200); // Số Tiền Giảm
        tableHoaDon.getColumnModel().getColumn(6).setPreferredWidth(200); // Tổng Tiền
        tableHoaDon.getColumnModel().getColumn(5).setCellRenderer(new CellCurrencyRenderer(currencyFormat));
        tableHoaDon.getColumnModel().getColumn(6).setCellRenderer(new CellCurrencyRenderer(currencyFormat));
        tableHoaDon.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && tableHoaDon.getSelectedRow() != -1) {
                String maHD = tableHoaDon.getValueAt(tableHoaDon.getSelectedRow(), 0).toString();
                loadChiTietHoaDon(maHD, dsCTHD);
                // tfNameDiscount, tfDetailDiscountName
               Integer maCTKM = Integer.valueOf(tableHoaDon.getValueAt(tableHoaDon.getSelectedRow(), 3).toString());
               ChiTietKMDAO ctkmdao = new ChiTietKMDAO();
               ChiTietKMDTO ctkm = ctkmdao.timKiemCTKM(maCTKM);
               KhuyenMaiDAO kmdao = new KhuyenMaiDAO();
               KhuyenMaiDTO km = kmdao.timKiemKM(maCTKM);
               if(km != null){
                    tfNameDiscount.setText(km.getTenKM());
                    tfDetailDiscountName.setText(ctkm.getTenCTKM()); 
               }else{
                    tfNameDiscount.setText(null);
                    tfDetailDiscountName.setText(null); 
               }
            }
        });

        tableChiTietHoaDon.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Mã Chi Tiết Hoá Đơn","Mã Hoá Đơn" , "Mã Sản Phẩm", "Mã Chi Tiết Khuyến Mãi", "Số Lượng"  ,"Đơn Giá", "Thành Tiền"}
        ) {
             @Override
             public boolean isCellEditable(int row, int column) { return false; } 
        });
        tableChiTietHoaDon.setPreferredSize(new Dimension(1200,200));
        tableChiTietHoaDon.setFont(new java.awt.Font("Times New Roman", 0, 18));
        tableChiTietHoaDon.setRowHeight(25);
        tableChiTietHoaDon.getColumnModel().getColumn(5).setCellRenderer(new CellCurrencyRenderer(currencyFormat));
        tableChiTietHoaDon.getColumnModel().getColumn(6).setCellRenderer(new CellCurrencyRenderer(currencyFormat));
        tableChiTietHoaDon.getColumnModel().getColumn(0).setPreferredWidth(200); // Mã Hoá Đơn
        tableChiTietHoaDon.getColumnModel().getColumn(1).setPreferredWidth(200); // Mã Nhân Viên
        tableChiTietHoaDon.getColumnModel().getColumn(2).setPreferredWidth(200); // Mã Khách Hàng
        tableChiTietHoaDon.getColumnModel().getColumn(3).setPreferredWidth(200); // Mã Chi Tiết Khuyến Mãi
        tableChiTietHoaDon.getColumnModel().getColumn(4).setPreferredWidth(200); // Ngày Lập
        tableChiTietHoaDon.getColumnModel().getColumn(5).setPreferredWidth(200); // Số Tiền Giảm
        tableChiTietHoaDon.getColumnModel().getColumn(6).setPreferredWidth(200);      

        tableSanPham.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Mã SP", "Tên Sản Phẩm", "Đơn Giá", "SL", "Loại SP"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        });

        tableSanPham.setFont(new java.awt.Font("Times New Roman", 0, 18));
        tableSanPham.setRowHeight(25);
        tableSanPham.getColumnModel().getColumn(0).setPreferredWidth(20);
        tableSanPham.getColumnModel().getColumn(1).setPreferredWidth(250);
        tableSanPham.getColumnModel().getColumn(3).setPreferredWidth(2);
        tableSanPham.getColumnModel().getColumn(2).setCellRenderer(new CellCurrencyRenderer(currencyFormat));
        tableSanPham.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int row = tableSanPham.rowAtPoint(e.getPoint());
                if (row != -1) {
                    xuly_chonhang_bangsanpham(tk, row);
                } 
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                int row = tableSanPham.rowAtPoint(e.getPoint());
                if (row != -1) {
                    tableGioHang.clearSelection();
                } 
            }
        });

        tableGioHang.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Mã SP", "Tên Sản Phẩm", "Đơn Giá", "SL", "Thành Tiền"}
        ) {
             @Override
             public boolean isCellEditable(int row, int column) { return false; } 
        });
        tableGioHang.setFont(new java.awt.Font("Times New Roman", 0, 18));
        tableGioHang.setRowHeight(25);
        tableGioHang.getColumnModel().getColumn(0).setPreferredWidth(20);
        tableGioHang.getColumnModel().getColumn(1).setPreferredWidth(250);
        tableGioHang.getColumnModel().getColumn(3).setPreferredWidth(2);
        tableGioHang.getColumnModel().getColumn(2).setCellRenderer(new CellCurrencyRenderer(currencyFormat));
        tableGioHang.getColumnModel().getColumn(4).setCellRenderer(new CellCurrencyRenderer(currencyFormat));
        tableGioHang.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = tableGioHang.rowAtPoint(e.getPoint());
                if (row != -1) {
                    xuly_chonhang_banggiohang(tk, row);
                }
            }
            
            @Override
            public void mouseReleased(MouseEvent e) {
                int row = tableGioHang.rowAtPoint(e.getPoint());
                if (row != -1) {
                    tableSanPham.clearSelection();
                }
            }
        });
    }

        // Hàm chuyển đổi chuỗi tiền tệ thành số
    public static long parseCurrencyString(String currency) {
        try {
            String cleaned = currency.replaceAll("[^0-9]", "");
            return Long.parseLong(cleaned);
        } catch (NumberFormatException e) {
            System.err.println("Lỗi chuyển đổi chuỗi tiền tệ: " + currency);
            return 0;
        }
    }
    
    private void HienHoaDonSauTK(ArrayList<HoaDonDTO> dsHD){
        DefaultTableModel modelHoaDon = (DefaultTableModel) tableHoaDon.getModel();
        modelHoaDon.setRowCount(0);
        try {
            if (dsHD != null) {
                for (HoaDonDTO hd : dsHD) {
                    Object[] rowData = {
                        hd.getMaHD(),
                        hd.getMaNV(),
                        hd.getMaKH(),
                        hd.getMaCTKM(),
                        InforAccount.formatDate(hd.getNgayLapHD().toString()),
                        hd.getSoTienGiam(),
                        hd.getTongTienHD()
                    };
                    modelHoaDon.addRow(rowData);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách hóa đơn: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void loadDataFromSource() {
        DefaultTableModel modelBasket = (DefaultTableModel) tableGioHang.getModel();
        modelBasket.setRowCount(0);
        DefaultTableModel modelHoaDon = (DefaultTableModel) tableHoaDon.getModel();
        modelHoaDon.setRowCount(0);
        try {
            HoaDonBUS localHdBUS = new HoaDonBUS();
            ArrayList<HoaDonDTO> dsHD = localHdBUS.hienDSHD();
            if (dsHD != null) {
                for (HoaDonDTO hd : dsHD) {
                    Object[] rowData = {
                        hd.getMaHD(),
                        hd.getMaNV(),
                        hd.getMaKH(),
                        hd.getMaCTKM(),
                        InforAccount.formatDate(hd.getNgayLapHD().toString()),
                        hd.getSoTienGiam(),
                        hd.getTongTienHD()
                    };
                    modelHoaDon.addRow(rowData);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách hóa đơn: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

        DefaultTableModel modelSanPham = (DefaultTableModel) tableSanPham.getModel();
        modelSanPham.setRowCount(0);
        try {
            HoaDonBUS localHdBUS = new HoaDonBUS();
            ArrayList<SanPhamDTO> dsSP = localHdBUS.hienDSSP();
            if (dsSP != null) {
                for (SanPhamDTO sp : dsSP) {
                    Object[] rowData = {
                        sp.getMaSP(),
                        sp.getTenSP(),
                        sp.getDonGia(),
                        sp.getSoLuong(),
                        sp.getLoai()
                    };
                    modelSanPham.addRow(rowData);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách sản phẩm: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

        DefaultTableModel modelChiTiet = (DefaultTableModel) tableChiTietHoaDon.getModel();
        modelChiTiet.setRowCount(0);
    }
    
    private void xuly_chonhang_bangsanpham(ArrayList<Object> tk, int row) {
        int maSP = (Integer) tableSanPham.getModel().getValueAt(row, 0);
        String tenSP = (String) tableSanPham.getModel().getValueAt(row, 1);
        long dongia = (long) tableSanPham.getModel().getValueAt(row, 2);
        int slSP = (Integer) tableSanPham.getModel().getValueAt(row, 3);
        slTD = slSP;
        String loaiSP = (String) tableSanPham.getValueAt(row, 4);
        for (int i = 0; i < fields.length; i++) {
            if (fields[i] instanceof JTextField) {
                JTextField a = (JTextField) fields[i];
                switch (i) {
                    case 0:
                        a.setText(String.valueOf(maSP));
                        break;
                    case 1:
                        a.setText(tenSP);
                        break;
                    case 2:
                        a.setText(String.valueOf(dongia));
                        break;
                    case 4:
                        a.setText(loaiSP);
                        break;
                    case 5:
                        a.setText(tk.get(3).toString());
                        break;
                }
                a.setForeground(Color.blue);
            } else if (fields[i] instanceof JSpinner) {
                JSpinner b = (JSpinner) fields[i];
                b.setBorder(new LineBorder(Color.BLACK, 1));
                b.setEnabled(true);
                b.setModel(new SpinnerNumberModel(1, 1, slTD, 1));
                b.setInputVerifier(new SpinnerInputVerifier(b, slTD, 1));
                b.addChangeListener(e -> warn(b, slTD, 1));
                JComponent editor = b.getEditor();
                JFormattedTextField textField = ((JSpinner.DefaultEditor) editor).getTextField();
                textField.setBorder(new EmptyBorder(0, 2, 0, 0));
                textField.setHorizontalAlignment(JTextField.LEFT);
                textField.setFocusLostBehavior(JFormattedTextField.PERSIST);
                textField.addKeyListener(new KeyAdapter() {
                    //Needed to inform user if they type something wrong:
                    @Override public void keyReleased(final KeyEvent kevt) { warn(b, slTD, 1); }
                });
                textField.addFocusListener(new FocusAdapter() {
                    //Needed to inform user if the text field loses focus and has a wrong value:
                    @Override public void focusLost(final FocusEvent fevt) { warn(b, slTD, 1); }
                });

                for (Component component : b.getComponents()) {
                    if (component instanceof JButton) {
                        JButton button = (JButton) component;
                        button.setBorder(new LineBorder(Color.lightGray, 1));
                        button.setMargin(new Insets(0, 0, 0, 0));
                        int textFieldHeight = textField.getPreferredSize().height;
                        button.setPreferredSize(new Dimension(button.getPreferredSize().width, textFieldHeight));
                    }
                }
            } 
        }
        HoaDonBUS hd = new HoaDonBUS();
        loadProductImage(hd.timAnhChoSanPham(String.valueOf(maSP)));
    }

    public static void warn(final JSpinner spin, int max, int min) {
        final JFormattedTextField jftf = ((DefaultEditor) spin.getEditor()).getTextField();
        try {
            spin.commitEdit(); //Try to commit given value.
            
        }
        catch (final ParseException px) {
            if (!jftf.getText().isBlank())
            {           
                jftf.setText("");
                JOptionPane.showMessageDialog(spin, "Dữ liệu không hợp lệ");
            }
            //else if the background is already red, means we already checked previously,
            //so we do not inform the user again. We inform him only once.
        }
    }
    
    private void xuly_chonhang_banggiohang(ArrayList<Object> tk, int row) {
        int maSP = (Integer) tableGioHang.getModel().getValueAt(row, 0);
        String tenSP = (String) tableGioHang.getModel().getValueAt(row, 1);
        String loaiSP = null;
        long dongia = (long) tableGioHang.getModel().getValueAt(row, 2);
        int slSP = (Integer) tableGioHang.getModel().getValueAt(row, 3);
        slTD = slSP;
        SanPhamDAO e = new SanPhamDAO();
        ArrayList<SanPhamDTO> dsSP = e.timKiemSanPham(String.valueOf(maSP));
        for (SanPhamDTO spdto : dsSP) {
            loaiSP = spdto.getLoai();
        }
        
        for (int i = 0; i < fields.length; i++) {
            if (fields[i] instanceof JTextField) {
                JTextField a = (JTextField) fields[i];
                switch (i) {
                    case 0:
                        a.setText(String.valueOf(maSP));
                        break;
                    case 1:
                        a.setText(tenSP);
                        break;
                    case 2:
                        a.setText(String.valueOf(dongia));
                        break;
                    case 4:
                        a.setText(loaiSP);
                        break;
                    case 5:
                        a.setText(tk.get(3).toString());
                        break;
                }
                a.setForeground(Color.blue);
            } else if (fields[i] instanceof JSpinner) {
                JSpinner b = (JSpinner) fields[i];
                b.setValue(slSP);
                b.setEnabled(false);
            } 
        }
        HoaDonBUS hd = new HoaDonBUS();
        loadProductImage(hd.timAnhChoSanPham(String.valueOf(maSP)));
    }

    private void loadProductImage(String imagePath) {
        if (imagePath == null || imagePath.trim().isEmpty()) {
            imagePath = DEFAULT_IMAGE_PATH;
        }

        try {
            File imgFile = new File(imagePath);
            if (!imgFile.exists()) {
                System.err.println("Ảnh không tồn tại: " + imagePath + ". Sử dụng ảnh mặc định.");
                imgFile = new File(DEFAULT_IMAGE_PATH);
                if (!imgFile.exists()) {
                    System.err.println("Ảnh mặc định cũng không tồn tại: " + DEFAULT_IMAGE_PATH);
                    lblAnhSanPham.setIcon(null);
                    lblAnhSanPham.setText("Không có ảnh");
                    return;
                }
            }

            ImageIcon originalIcon = new ImageIcon(imgFile.getAbsolutePath());
            Image originalImage = originalIcon.getImage();
            Image scaledImage = originalImage.getScaledInstance(IMAGE_WIDTH, IMAGE_HEIGHT, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImage);

            lblAnhSanPham.setIcon(scaledIcon);
            lblAnhSanPham.setText(null);

        } catch (Exception e) {
            System.err.println("Lỗi khi tải ảnh: " + imagePath);
            lblAnhSanPham.setIcon(null);
            lblAnhSanPham.setText("Lỗi tải ảnh");
        }
    }

    private void loadChiTietHoaDon(String maHD, ArrayList<ChiTietHoaDonDTO> dsCTHD) {
        DefaultTableModel modelChiTiet = (DefaultTableModel) tableChiTietHoaDon.getModel();
        modelChiTiet.setRowCount(0);
         //"Mã Chi Tiết Hoá Đơn","Mã Hoá Đơn" , "Mã Sản Phẩm", "Mã Chi Tiết Khuyến Mãi", "Số Lượng"  ,"Đơn Giá", "Thành Tiền"
        try {
            if (dsCTHD != null) {
                for (ChiTietHoaDonDTO ct : dsCTHD) {
                    if(ct.getMaHD() == Integer.valueOf(maHD)){
                        Object[] rowData = {
                        ct.getMaCTHD(),
                        ct.getMaHD(),
                        ct.getMaSP(),
                        ct.getMaCTKM(),
                        ct.getSoLuong(),
                        ct.getDonGia(),
                        ct.getThanhTien()
                        };
                        modelChiTiet.addRow(rowData);
                    }
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tải chi tiết hóa đơn " + maHD + ": " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}