 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Officer;
/**
 *
 * @author mariane
 */
public class Officer {
    private int officerID;
    private String firstName;
    private String middleInitial;
    private String lastName;
    private String gender;
    private String phoneNumber;
    private String birthDate;

    public Officer() {}

    public Officer(int officerID, String firstName, String middleInitial, String lastName,
                   String gender, String phoneNumber, String birthDate) {
        this.officerID = officerID;
        this.firstName = firstName;
        this.middleInitial = middleInitial;
        this.lastName = lastName;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
    }

    // Getters and Setters
    public int getOfficerID() { return officerID; }
    public void setOfficerID(int officerID) { this.officerID = officerID; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getMiddleInitial() { return middleInitial; }
    public void setMiddleInitial(String middleInitial) { this.middleInitial = middleInitial; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getBirthDate() { return birthDate; }
    public void setBirthDate(String birthDate) { this.birthDate = birthDate; }
}
