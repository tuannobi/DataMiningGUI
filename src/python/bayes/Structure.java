package python.bayes;

import java.util.List;
import java.util.Map;

public class Structure {
    private String tenCot;
    private List<String> thuocTinh;
    private List<Map<String,String>> thuocTinhKetQua;
    private List<Integer> xacSuat;
    private List<Double> tiLe;

    public String getTenCot() {
        return tenCot;
    }

    public void setTenCot(String tenCot) {
        this.tenCot = tenCot;
    }

    public List<String> getThuocTinh() {
        return thuocTinh;
    }

    public void setThuocTinh(List<String> thuocTinh) {
        this.thuocTinh = thuocTinh;
    }

    public List<Map<String, String>> getThuocTinhKetQua() {
        return thuocTinhKetQua;
    }

    public void setThuocTinhKetQua(List<Map<String, String>> thuocTinhKetQua) {
        this.thuocTinhKetQua = thuocTinhKetQua;
    }

    public List<Integer> getXacSuat() {
        return xacSuat;
    }

    public void setXacSuat(List<Integer> xacSuat) {
        this.xacSuat = xacSuat;
    }

    public List<Double> getTiLe() {
        return tiLe;
    }

    public void setTiLe(List<Double> tiLe) {
        this.tiLe = tiLe;
    }

    @Override
    public String toString() {
        return "Structure{" +
                "tenCot='" + tenCot + '\'' +
                ", thuocTinh=" + thuocTinh +
                ", thuocTinhKetQua=" + thuocTinhKetQua +
                ", xacSuat=" + xacSuat +
                ", tiLe=" + tiLe +
                '}';
    }
}
