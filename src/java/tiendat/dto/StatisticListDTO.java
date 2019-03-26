/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiendat.dto;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author DATTTSE62330
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="statistics")
public class StatisticListDTO implements Serializable{
    private List<StatisticTypeDTO> statistic;     

    public StatisticListDTO() {
    }

    public List<StatisticTypeDTO> getStatistic() {
        return statistic;
    }

    public void setStatistic(List<StatisticTypeDTO> statistic) {
        this.statistic = statistic;
    }
    

    public StatisticListDTO(List<StatisticTypeDTO> statistic) {
        this.statistic = statistic;
    }  
}

