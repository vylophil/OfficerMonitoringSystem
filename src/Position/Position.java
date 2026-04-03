/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Position;
/**
 *
 * @author mariane
 */
public class Position {
    private int positionID;
    private String name;
    private String description;

    // Constructors
    public Position() {}

    public Position(int positionID, String name, String description) {
        this.positionID = positionID;
        this.name = name;
        this.description = description;
    }

    // Getters and Setters
    public int getPositionID() {
        return positionID;
    }

    public void setPositionID(int positionID) {
        this.positionID = positionID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
