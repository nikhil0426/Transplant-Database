import java.util.Comparator;

public class OrganComparator implements Comparator<Patient> {
    /**
     * compares organs needed by patients
     *
     * @param p1 patient 1 for comparison
     * @param p2 patient 2 for comparison
     * @return 0 if equal, negative if first string is lexicographically greater, positive if lexicographically lesser
     */
    public int compare(Patient p1, Patient p2) {
        return p1.getOrgan().toUpperCase().compareTo(p2.getOrgan().toUpperCase());
    }
}