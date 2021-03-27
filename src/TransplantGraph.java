import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class TransplantGraph implements Serializable {

    private ArrayList<Patient> donors;
    private ArrayList<Patient> recipients;
    public static final int MAX_PATIENTS = 100;
    private boolean[][] connections = new boolean[MAX_PATIENTS][MAX_PATIENTS];

    /**
     * default no-arg constructor
     */
    public TransplantGraph() {
    }

    /**
     * creates TransplantGraph object from donor and recipient files
     *
     * @param donorFile file of list of donors
     * @param recipientFile file of list of recipients
     * @return TransplantGraph
     */
    public static TransplantGraph buildFromFiles(String donorFile, String recipientFile) throws FileNotFoundException {
        TransplantGraph temp = new TransplantGraph();
        ArrayList<Patient> d = new ArrayList<>();
        ArrayList<Patient> r = new ArrayList<>();
        Scanner donor = new Scanner(new File(donorFile));
        while (donor.hasNext()) {
            String[] info = donor.nextLine().split(",");
            BloodType tempType = new BloodType();
            tempType.setBloodType(info[4].trim());
            Patient tempDonor = new Patient(info[1].trim(), info[3].trim(), Integer.parseInt(info[2].trim()), tempType, Integer.parseInt(info[0].trim()), true);
            d.add(tempDonor);
        }
        donor.close();
        Scanner recipient = new Scanner(new File(recipientFile));
        while (recipient.hasNext()) {
            String[] info = recipient.nextLine().split(",");
            BloodType tempType = new BloodType();
            tempType.setBloodType(info[4].trim());
            Patient tempRecipient = new Patient(info[1].trim(), info[3].trim(), Integer.parseInt(info[2].trim()), tempType, Integer.parseInt(info[0].trim()), false);
            r.add(tempRecipient);
        }
        recipient.close();
        temp.setDonors(d);
        temp.setRecipients(r);
        return temp;
    }

    /**
     * adds patient to recipients list
     *
     * @param patient patient object
     */
    public void addRecipient(Patient patient) {
        recipients.add(patient);
    }

    /**
     * adds patient to donors list
     *
     * @param patient patient object
     */
    public void addDonor(Patient patient) {
        donors.add(patient);
    }

    /**
     * removes patient from recipient list
     *
     * @param name name of patient
     */
    public void removeRecipient(String name) {
        for (Patient recipient : recipients) {
            if (name.equals(recipient.getName())) {
                recipients.remove(recipient);
                System.out.println(name + " was removed from the organ transplant waitlist.");
                break;
            }
        }
        resetRecipients();
    }

    /**
     * removes patient from donors list
     *
     * @param name name of patient
     */
    public void removeDonor(String name) {
        for (Patient donor : donors) {
            if (name.equals(donor.getName())) {
                donors.remove(donor);
                System.out.println(name + " was removed from the organ donor list.");
                break;
            }
        }
        resetDonors();
    }

    /**
     * determines boolean values of adjacency matrix
     */
    public void determineConnections() {
        boolean[][] temp = new boolean[MAX_PATIENTS][MAX_PATIENTS];
        for (int i = 0; i < donors.size(); i++) {
            for (int k = 0; k < recipients.size(); k++) {
                temp[i][k] = BloodType.isCompatible(donors.get(i).getBloodType(), recipients.get(k).getBloodType())
                        && donors.get(i).getOrgan().trim().toUpperCase().equals(recipients.get(k).getOrgan().trim().toUpperCase());
            }
        }
        setConnections(temp);
        for (int j = 0; j < donors.size(); j++) {
            ArrayList<String> cons = new ArrayList<>();
            for (int h = 0; h < recipients.size(); h++) {
                if (temp[j][h]) {
                    cons.add(String.valueOf(recipients.get(h).getID()));
                }
            }
            donors.get(j).setConnections(cons.toString());
        }
        for (int i = 0; i < recipients.size(); i++) {
            ArrayList<String> cons = new ArrayList<>();
            for (int k = 0; k < donors.size(); k++) {
                if (temp[k][i]) {
                    cons.add(String.valueOf(donors.get(k).getID()));
                }
            }
            recipients.get(i).setConnections(cons.toString());
        }
    }

    /**
     * prints organ recipients as a table
     */
    public void printAllRecipients() {
        determineConnections();
        System.out.println("Index |Donor Name          | Age | Organ Donated | Blood Type | Recipient IDs\n" +
                "=============================================================================");
        for (Patient recipient : recipients) {
            System.out.println(recipient.toString());
        }
    }

    /**
     * prints organ donors as a table
     */
    public void printAllDonors() {
        determineConnections();
        System.out.println("Index |Donor Name          | Age | Organ Donated | Blood Type | Recipient IDs\n" +
                "=============================================================================");
        for (Patient donor : donors) {
            System.out.println(donor.toString());
        }
    }

    /**
     * resets IDs of recipients after events such as removal
     */
    public void resetRecipients() {
        for (int i = 0; i < recipients.size(); i++) {
            recipients.get(i).setID(i);
        }
    }

    /**
     * resets IDs of donors after events such as removal
     */
    public void resetDonors() {
        for (int i = 0; i < donors.size(); i++) {
            donors.get(i).setID(i);
        }
    }

    /**
     * sorts recipient list back to normal (by ID numbers)
     */
    public void resetRecipientSort() {
        recipients.sort(Patient::compareTo);
    }

    /**
     * sorts donor list back to normal (by ID numbers)
     */
    public void resetDonorSort() {
        donors.sort(Patient::compareTo);
    }

    /**
     * sorts recipient list by number of connections, prints it, and returns it to base status
     */
    public void recipientConnectionsSort() {
        recipients.sort(new NumConnectionsComparator());
        printAllRecipients();
        resetRecipientSort();
    }

    /**
     * sorts recipient list by blood type, prints it, and returns it to base status
     */
    public void recipientBloodSort() {
        recipients.sort(new BloodTypeComparator());
        printAllRecipients();
        resetRecipientSort();
    }

    /**
     * sorts recipient list by organs, prints it, and returns it to base status
     */
    public void recipientOrganSort() {
        recipients.sort(new OrganComparator());
        printAllRecipients();
        resetRecipientSort();
    }

    /**
     * sorts donor list by number of connections, prints it, and returns it to base status
     */
    public void donorConnectionsSort() {
        donors.sort(new NumConnectionsComparator());
        printAllDonors();
        resetDonorSort();
    }

    /**
     * sorts donor list by blood type, prints it, and returns it to base status
     */
    public void donorBloodSort() {
        donors.sort(new BloodTypeComparator());
        printAllDonors();
        resetDonorSort();
    }

    /**
     * sorts donor list by organs, prints it, and returns it to base status
     */
    public void donorOrganSort() {
        donors.sort(new OrganComparator());
        printAllDonors();
        resetDonorSort();
    }

    /**
     * basic getter for array list of donors
     *
     * @return ArrayList
     */
    public ArrayList<Patient> getDonors() {
        return donors;
    }

    /**
     * basic setter for array list of donors
     *
     * @param donors arraylist of donors
     */
    public void setDonors(ArrayList<Patient> donors) {
        this.donors = donors;
    }

    /**
     * basic getter for array list of recipients
     *
     * @return ArrayList arraylist of recipients
     */
    public ArrayList<Patient> getRecipients() {
        return recipients;
    }

    /**
     * basic setter for array list of recipients
     *
     * @param recipients arraylist of recipients
     */
    public void setRecipients(ArrayList<Patient> recipients) {
        this.recipients = recipients;
    }

    /**
     * basic setter for adjacency matrix of connections between donors and recipients
     *
     * @param connections donor-recipient connections stores as a 2D boolean array
     */
    public void setConnections(boolean[][] connections) {
        this.connections = connections;
    }
}