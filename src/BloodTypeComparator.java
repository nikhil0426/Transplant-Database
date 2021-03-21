import java.util.Comparator;

public class BloodTypeComparator implements Comparator<Patient> {

    /**
     * compares blood types of 2 patients
     *
     * @param p1 patient 1 for comparison
     * @param p2 patient 2 for comparison
     * @return 0 if equal, negative if first string is lexicographically greater, positive if lexicographically lesser
     */
    public int compare(Patient p1, Patient p2) {
        return p1.getBloodType().getBloodType().toUpperCase().compareTo(p2.getBloodType().getBloodType().toUpperCase());
    }
}