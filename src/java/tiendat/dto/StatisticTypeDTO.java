/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiendat.dto;

import com.sun.tracing.dtrace.Attributes;
import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlList;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author DATTTSE62330
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class StatisticTypeDTO implements Serializable {
    @XmlAttribute
    private String type;

    public StatisticTypeDTO(String type) {
        this.type = type;
    }

    public StatisticTypeDTO() {
    }
    
    private List<StatisticDTO> attribute;

    public StatisticTypeDTO(String type, List<StatisticDTO> attribute) {
        this.type = type;
        this.attribute = attribute;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<StatisticDTO> getItems() {
        return attribute;
    }

    public void setItems(List<StatisticDTO> items) {
        this.attribute = items;
    }
    
    
}
