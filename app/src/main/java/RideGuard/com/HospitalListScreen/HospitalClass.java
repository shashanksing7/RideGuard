package RideGuard.com.HospitalListScreen;

public class HospitalClass
{
    private  String HospitalName;
    private String HospitalNumber;

    public String getHospitalName() {
        return HospitalName;
    }

    public void setHospitalName(String hospitalName) {
        HospitalName = hospitalName;
    }

    public String getHospitalNumber() {
        return HospitalNumber;
    }

    public void setHospitalNumber(String hospitalNumber) {
        HospitalNumber = hospitalNumber;
    }

    public HospitalClass(String hospitalName, String hospitalNumber) {
        HospitalName = hospitalName;
        HospitalNumber = hospitalNumber;
    }
}
