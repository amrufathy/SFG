/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package components;

/**
 *
 * @author amr
 */
public class GraphEdge {

    private String gain;
    private int fromID;
    private int toID;

    public GraphEdge(int fromID, int toID) {
        this.fromID = fromID;
        this.toID = toID;
    }
    
    public GraphEdge(int fromID, int toID, String gain) {
        this.fromID = fromID;
        this.toID = toID;
        this.gain = gain;
    }

    /**
     * @return the gain
     */
    public String getGain() {
        return gain;
    }

    /**
     * @param gain the gain to set
     */
    public void setGain(String gain) {
        this.gain = gain;
    }

    /**
     * @return the fromID
     */
    public int getFromID() {
        return fromID;
    }

    /**
     * @param fromID the fromID to set
     */
    public void setFromID(int fromID) {
        this.fromID = fromID;
    }

    /**
     * @return the toID
     */
    public int getToID() {
        return toID;
    }

    /**
     * @param toID the toID to set
     */
    public void setToID(int toID) {
        this.toID = toID;
    }

}
