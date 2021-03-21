import java.io.Serializable;

public class Patient implements Comparable, Serializable {

    private String name = "";
    private String organ = "";
    private int age = -1;
    private BloodType bloodType;
    private int ID = -1;
    private boolean isDonor;
    private String connections = "";

    /**
     * Default no-arg constructor
     */
    public Patient() {
    }

    /**
     * Patient constructor
     *
     * @param name      name of patient
     * @param organ     organ of patient to be received or donated
     * @param age       age of patient
     * @param bloodType bloodtype of patient
     * @param ID        ID of patient
     * @param isDonor   marker for if patient is donor or recipient
     */
    public Patient(String name, String organ, int age, BloodType bloodType, int ID, boolean isDonor) {
        this.name = name;
        this.organ = organ;
        this.age = age;
        this.bloodType = bloodType;
        this.ID = ID;
        this.isDonor = isDonor;
    }

    /**
     * compares patient to object using ID numbers
     *
     * @param o object to be compared with of type patient
     * @return int
     */
    public int compareTo(Object o) {
        Patient p1 = (Patient) o;
        return Integer.compare(this.ID, p1.getID());
    }


    /**
     * String representation of patient object
     *
     * @return String
     */
    public String toString() {
        String line = "|";
        return String.format("%3s%4s%-20s%s%3s%3s%6s%10s%6s%7s%2s", getID(), line, getName(), line, getAge(), line, getOrgan(), line, getBloodType().getBloodType(), line, getConnections());
    }

    /**
     * default getter for name
     *
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * default setter for name
     *
     * @param name name of patient
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * defualt getter for organ
     *
     * @return String
     */
    public String getOrgan() {
        return organ;
    }

    /**
     * default setter for organ
     *
     * @param organ corresponding organ of patient
     */
    public void setOrgan(String organ) {
        this.organ = organ;
    }

    /**
     * default getter for age
     *
     * @return int
     */
    public int getAge() {
        return age;
    }

    /**
     * default setter for age
     *
     * @param age age of patient
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * default getter for bloodType
     *
     * @return BloodType
     */
    public BloodType getBloodType() {
        return bloodType;
    }

    /**
     * default setter for bloodType
     *
     * @param bloodType blood type of patient
     */
    public void setBloodType(BloodType bloodType) {
        this.bloodType = bloodType;
    }

    /**
     * default getter for ID
     *
     * @return int
     */
    public int getID() {
        return ID;
    }

    /**
     * default setter for ID
     *
     * @param ID id of patient
     */
    public void setID(int ID) {
        this.ID = ID;
    }

    /**
     * default getter for isDonor
     *
     * @return boolean
     */
    public boolean isDonor() {
        return isDonor;
    }

    /**
     * default setter for isDonor
     *
     * @param donor for checking if patient is a donor or recipient
     */
    public void setDonor(boolean donor) {
        isDonor = donor;
    }

    /**
     * Creates string representation of connections found
     *
     * @return String
     */
    public String getConnections() {
        return connections;
    }

    /**
     * Sets string representation of connections found
     *
     * @param connections number of connections a patient has
     */
    public void setConnections(String connections) {
        this.connections = connections;
    }
}