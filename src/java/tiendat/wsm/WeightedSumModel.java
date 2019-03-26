/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiendat.wsm;

import java.util.ArrayList;
import java.util.List;
import tiendat.generatedObject.Guitar;

/**
 *
 * @author DATTTSE62330
 */
public class WeightedSumModel {

    List<Alternative> alternatives;
    /* WEIGHTAGE */
    /* OLD 1.0/5 */
    private double wPrice = 0.175;
    private double wWood = 0.175;
    private double wBrand = 0.175;
    private double wOrigin = 0.175;
    private double wFitSearch = 0.3;
            
    private double minPrice = Double.MAX_VALUE; 
    private double maxWood = 8;
    private double maxBrand = 3;
    private double maxOrigin = 3;
    private double maxFitSeach = 3;
    
    public WeightedSumModel(List<Alternative> alternatives) {
        this.alternatives = alternatives;
    }

    // NORMALIZE USING LINEAR NORMALIATION ( MIN / MAX ) 
    private void normalize() {
        // GET MAX PRICE                 
        for (Alternative alt : alternatives) {
            if (alt.getPrice() < minPrice) {
                minPrice = alt.getPrice();
            }
        }

        for (Alternative alt : alternatives) {
            // NORMALIZE PRICE - NON BENIFICIAL using max - min 
            double price = alt.getPrice(); 
            double value = minPrice / price;
            alt.setPrice(value);
            // BENITIFICIAL v[i,j] = x[i,j] / x[max,j]
            double wood = alt.getWood(); 
            value = wood / maxWood; 
            alt.setWood(value);
            
            double brand = alt.getBrand();
            value = wood / maxBrand;
            alt.setBrand(value);
            
            double origin = alt.getOrigin(); 
            value = origin / maxOrigin;
            alt.setOrigin(value);
            
            double fitSearch = alt.getFitSearch();
            value = fitSearch / maxFitSeach;
            alt.setFitSearch(value);
        }
    }

    public void calculate() {        
        List<Guitar> guitars = new ArrayList<>();
        normalize();
        for (Alternative alt : alternatives) {
            double score = alt.getPrice()*wPrice 
                    + alt.getWood()*wWood 
                    + alt.getBrand()*wBrand 
                    + alt.getOrigin()*wOrigin
                    + alt.getFitSearch()*wFitSearch;
            
            Guitar guitar = alt.getGuitar();
            score = score*10;
            guitar.setWeightedScore(score);
        }                
    }

    public List<Alternative> getAlternatives() {
        return alternatives;
    }

    public void setAlternatives(List<Alternative> alternatives) {
        this.alternatives = alternatives;
    }
}
