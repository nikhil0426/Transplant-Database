import java.io.*;
import java.util.Objects;
import java.util.Scanner;

public class TransplantDriver implements Serializable {

    public static final String DONOR_FILE = "donors.txt";
    public static final String RECIPIENT_FILE = "recipients.txt";

    /**
     * Main class to run program
     *
     * @param args basic pass thru arguments
     */
    public static void main(String[] args) {
        boolean keepGoing = true;
        String quit = "";
        do {
            try {
                boolean running = true;
                Scanner scan = new Scanner(System.in);
                TransplantGraph transplantGraph;
                try {
                    FileInputStream fileInputStream = new FileInputStream("transplant.obj");
                    ObjectInputStream fin = new ObjectInputStream(fileInputStream);
                    transplantGraph = (TransplantGraph) fin.readObject();
                    fin.close();
                    System.out.println("TransplantGraph is loaded from transplant.obj.");
                } catch (FileNotFoundException fileNotFoundException) {
                    transplantGraph = TransplantGraph.buildFromFiles(DONOR_FILE, RECIPIENT_FILE);
                    System.out.println("Transplant.obj does not exist. Creating new TransplantGraph object...");
                }
                do {
                    try {
                        System.out.println("Menu:\n" +
                                "    (LR) - List all recipients\n" +
                                "    (LO) - List all donors\n" +
                                "    (AO) - Add new donor\n" +
                                "    (AR) - Add new recipient\n" +
                                "    (RO) - Remove donor\n" +
                                "    (RR) - Remove recipient\n" +
                                "    (SR) - Sort recipients\n" +
                                "    (SO) - Sort donors\n" +
                                "    (Q) - Quit");
                        System.out.print("Please select an option: ");
                        String in = scan.nextLine().trim().toUpperCase();
                        switch (in) {
                            case "LR":
                                transplantGraph.printAllRecipients();
                                break;
                            case "LO":
                                transplantGraph.printAllDonors();
                                break;
                            case "AO":
                                System.out.print("Please enter the organ donor name: ");
                                String donorName = scan.nextLine().trim();
                                System.out.print("Please enter the organs John Doe is donating: ");
                                String donorOrgans = scan.nextLine().trim();
                                System.out.print("Please enter the blood type of John Doe: ");
                                String donorBloodType = scan.nextLine().trim();
                                System.out.print("Please enter the age of John Doe: ");
                                int donorAge = scan.nextInt();
                                scan.nextLine();
                                BloodType donorType = new BloodType();
                                donorType.setBloodType(donorBloodType);
                                Patient tempDonor = new Patient(donorName, donorOrgans, donorAge, donorType, transplantGraph.getDonors().size(), true);
                                transplantGraph.addDonor(tempDonor);
                                System.out.println("The organ donor with ID " + tempDonor.getID() + " was successfully added to the donor list!");
                                break;
                            case "AR":
                                System.out.print("Please enter new recipient's name: ");
                                String recipientName = scan.nextLine().trim();
                                System.out.print("Please enter the recipient's blood type: ");
                                String recipientBloodType = scan.nextLine().trim();
                                System.out.print("Please enter the recipient's age: ");
                                int recipientAge = scan.nextInt();
                                scan.nextLine();
                                System.out.print("Please enter the organ needed: ");
                                String recipientOrgans = scan.nextLine().trim();
                                BloodType recipientType = new BloodType();
                                recipientType.setBloodType(recipientBloodType);
                                Patient tempRecipient = new Patient(recipientName, recipientOrgans, recipientAge, recipientType, transplantGraph.getRecipients().size(), false);
                                transplantGraph.addRecipient(tempRecipient);
                                System.out.println(tempRecipient.getName() + " is now on the organ transplant waitlist!");
                                break;
                            case "RO":
                                System.out.print("Please enter the name of the organ donor to remove: ");
                                String donorSearch = scan.nextLine();
                                transplantGraph.removeDonor(donorSearch);
                                break;
                            case "RR":
                                System.out.print("Please enter the name of the recipient to remove: ");
                                String recipientSearch = scan.nextLine();
                                transplantGraph.removeRecipient(recipientSearch);
                                break;
                            case "SR":
                                System.out.println("    (I) Sort by ID\n" +
                                        "    (N) Sort by Number of Donors\n" +
                                        "    (B) Sort by Blood Type\n" +
                                        "    (O) Sort by Organ Needed\n" +
                                        "    (Q) Back to Main Menu");
                                boolean runningRSort = true;
                                do {
                                    System.out.print("Please select an option: ");
                                    Scanner opt1 = new Scanner(System.in);
                                    char choice1 = opt1.nextLine().toUpperCase().charAt(0);
                                    switch (choice1) {
                                        case 'I':
                                            transplantGraph.printAllRecipients();
                                            break;
                                        case 'N':
                                            transplantGraph.recipientConnectionsSort();
                                            break;
                                        case 'B':
                                            transplantGraph.recipientBloodSort();
                                            break;
                                        case 'O':
                                            transplantGraph.recipientOrganSort();
                                            break;
                                        case 'Q':
                                            System.out.println("Returning to main menu.");
                                            runningRSort = false;
                                            break;
                                        default:
                                            System.out.println("Not an option. Try again!");
                                    }
                                } while (runningRSort);
                                break;
                            case "SO":
                                System.out.println("    (I) Sort by ID\n" +
                                        "    (N) Sort by Number of Recipients\n" +
                                        "    (B) Sort by Blood Type\n" +
                                        "    (O) Sort by Organ Donated\n" +
                                        "    (Q) Back to Main Menu");
                                boolean runningDSort = true;
                                do {
                                    System.out.print("Please select an option: ");
                                    Scanner opt2 = new Scanner(System.in);
                                    char choice2 = opt2.nextLine().toUpperCase().charAt(0);
                                    switch (choice2) {
                                        case 'I':
                                            transplantGraph.printAllDonors();
                                            break;
                                        case 'N':
                                            transplantGraph.donorConnectionsSort();
                                            break;
                                        case 'B':
                                            transplantGraph.donorBloodSort();
                                            break;
                                        case 'O':
                                            transplantGraph.donorOrganSort();
                                            break;
                                        case 'Q':
                                            System.out.println("Returning to main menu.");
                                            runningDSort = false;
                                            break;
                                        default:
                                            System.out.println("Not an option. Try again!");
                                    }
                                } while (runningDSort);
                                break;
                            case "Q":
                                quit = in;
                                try {
                                    FileOutputStream file = new FileOutputStream("transplant.obj");
                                    ObjectOutputStream fout = new ObjectOutputStream(file);
                                    fout.writeObject(transplantGraph);
                                    fout.close();
                                } catch (NotSerializableException ignored) {

                                }
                                System.out.println("TransplantGraph is saved in transplant.obj.");
                                running = false;
                                break;
                            default:
                                System.out.println("Not an option. Try again!");
                        }
                    } catch (Exception innerEx) {
                        System.out.println("Error! Try again!");
                        System.out.println(innerEx.getLocalizedMessage());
                    }
                } while (running);
            } catch (Exception outerEx) {
                System.out.println("Uh oh! Something went wrong!");
                System.out.println(outerEx.getLocalizedMessage());
            }
            if (Objects.equals(quit, "Q")) {
                keepGoing = false;
            }
        } while (keepGoing);
    }
}