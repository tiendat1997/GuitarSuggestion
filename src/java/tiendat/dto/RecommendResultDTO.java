/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiendat.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import tiendat.generatedObject.Guitar;

/**
 *
 * @author DATTTSE62330
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"guitar"})
@XmlRootElement(name = "recommend-guitar")
public class RecommendResultDTO implements Serializable{
    private List<Guitar> guitar;

    public List<Guitar> getGuitar() {
        if (guitar == null) {
            guitar = new ArrayList<>();
        }
        return guitar;
    }

    public void setGuitar(List<Guitar> guitar) {
        this.guitar = guitar;
    }
    
}
