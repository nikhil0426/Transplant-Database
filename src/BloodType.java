import java.io.Serializable;
import java.util.Objects;

public class BloodType implements Serializable {

    private String bloodType;

    /**
     * Default no-arg constructor
     */
    public BloodType() {
    }

    /**
     * determines if blood types are compatible
     *
     * @param recipient person to receive transplant
     * @param donor     person to give transplant
     * @return boolean
     */
    public static boolean isCompatible(BloodType recipient, BloodType donor) {
        if (Objects.equals(recipient.bloodType.trim().toUpperCase(), "O")) {
            return Objects.equals(donor.bloodType.trim().toUpperCase(), "O");
        } else if (Objects.equals(recipient.bloodType.trim().toUpperCase(), "A")) {
            return (Objects.equals(recipient.bloodType.trim().toUpperCase(), "O") || Objects.equals(recipient.bloodType.trim().toUpperCase(), "A"));
        } else if (Objects.equals(recipient.bloodType.trim().toUpperCase(), "B")) {
            return (Objects.equals(recipient.bloodType.trim().toUpperCase(), "O") || Objects.equals(recipient.bloodType.trim().toUpperCase(), "B"));
        } else {
            return true;
        }
    }

    /**
     * default getter for bloodType
     *
     * @return String
     */
    public String getBloodType() {
        return bloodType;
    }

    /**
     * default setter for bloodType
     *
     * @param bloodType input blood type as string to set bloodType
     */
    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }
}