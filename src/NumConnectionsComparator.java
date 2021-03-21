import java.util.Comparator;

public class NumConnectionsComparator implements Comparator<Patient> {

    /**
     * compares number of connections for 2 patients
     *
     * @param p1 patient 1 for comparison
     * @param p2 patient 2 for comparison
     * @return 0 if equal, negative if p1 is longer, positive if p2 is longer
     */
    public int compare(Patient p1, Patient p2) {
        return Integer.compare(p1.getConnections().length(), p2.getConnections().length());
    }
}