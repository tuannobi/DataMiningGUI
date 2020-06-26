package python.bayes;

import java.util.List;

public class ResultAttribute {
    List<String> name;
    List<Integer> tanSuat;
    List<Double> phanTram;

    public List<String> getName() {
        return name;
    }

    public void setName(List<String> name) {
        this.name = name;
    }

    public List<Integer> getTanSuat() {
        return tanSuat;
    }

    public void setTanSuat(List<Integer> tanSuat) {
        this.tanSuat = tanSuat;
    }

    public List<Double> getPhanTram() {
        return phanTram;
    }

    public void setPhanTram(List<Double> phanTram) {
        this.phanTram = phanTram;
    }

    @Override
    public String toString() {
        return "ResultAttribute{" +
                "name=" + name +
                ", tanSuat=" + tanSuat +
                ", phanTram=" + phanTram +
                '}';
    }
}
