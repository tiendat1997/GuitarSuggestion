/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiendat.wsm;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import tiendat.generatedObject.Guitar;

/**
 *
 * @author DATTTSE62330
 */
public class WeightedProductModel {

    List<Alternative> alternatives;
    /* WEIGHTAGE */
    private double wPrice = 1.0 / 4;
    private double wWood = 1.0 / 4;
    private double wBrand = 1.0 / 4;
    private double wOrigin = 1.0 / 4;

    private double minPrice = Double.MAX_VALUE;
    private double maxWood = 8;
    private double maxBrand = 3;
    private double maxOrigin = 3;

    public WeightedProductModel(List<Alternative> alternatives) {
        this.alternatives = alternatives;
    }

    // NORMALIZE USING LINEAR NORMALIATION ( MIN / MAX ) 
    private void normalize() {
        // GET MAX PRICE                 
        for (Alternative alt : alternatives) {
            if (alt.getPrice() > 0 && alt.getPrice() < minPrice) {
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

        }
    }

    public double roundAvoid(double value, int places) {
        double scale = Math.pow(10, places);
        return Math.round(value * scale) / scale;
    }

    public void calculate() {
        List<Guitar> guitars = new ArrayList<>();
        normalize();
        for (Alternative alt : alternatives) {
            double score = Math.pow(alt.getPrice(), wPrice)
                    * Math.pow(alt.getWood(), wWood)
                    * Math.pow(alt.getBrand(), wBrand)
                    * Math.pow(alt.getOrigin(), wOrigin);

            Guitar guitar = alt.getGuitar();
            score = score * 10;
            score = roundAvoid(score, 2);
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
