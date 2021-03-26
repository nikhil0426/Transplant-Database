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
     * @param donorFile saved file of list of donors
     * @param recipientFile saved file of list of recipients
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
     * @return ArrayList
     */
    public ArrayList<Patient> getRecipients() {
        return recipients;
    }

    /**
     * basic setter for array list of recipients
     *
     * @param recipients arraylist of patients
     */
    public void setRecipients(ArrayList<Patient> recipients) {
        this.recipients = recipients;
    }

    /**
     * basic setter for adjacency matrix of connections between donors and recipients
     *
     * @param connections donor-recipient connections found stored as 2d boolean array
     */
    public void setConnections(boolean[][] connections) {
        this.connections = connections;
    }
}