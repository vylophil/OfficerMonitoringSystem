 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Elected_To;
/**
 *
 * @author mariane
 */
public class Elected_To {
    private int electID;
    private int officerID;
    private int positionID;
    private String department;
    private String dateFrom;
    private String dateTo;
    
    // Constructors
    public Elected_To() {}
    
    public Elected_To(int officerID, int positionID, String department, String dateFrom, String dateTo) {
        this.officerID = officerID;
        this.positionID = positionID;
        this.department = department;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }
    
    public Elected_To(int electID, int officerID, int positionID, String department, String dateFrom, String dateTo) {
        this.electID = electID;
        this.officerID = officerID;
        this.positionID = positionID;
        this.department = department;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }
    
    // Getters and setters
    public int getElectID() { return electID; }
    public void setElectID(int electID) { this.electID = electID; }
    
    public int getOfficerID() { return officerID; }
    public void setOfficerID(int officerID) { this.officerID = officerID; }
    
    public int getPositionID() { return positionID; }
    public void setPositionID(int positionID) { this.positionID = positionID; }
    
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    
    public String getDateFrom() { return dateFrom; }
    public void setDateFrom(String dateFrom) { this.dateFrom = dateFrom; }
    
    public String getDateTo() { return dateTo; }
    public void setDateTo(String dateTo) { this.dateTo = dateTo; }
}