/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiendat.wsm;

import tiendat.generatedObject.Guitar;

/**
 *
 * @author DATTTSE62330
 */
public class Alternative {
    private Guitar guitar; 
    /* Criterion */
    private double price; // COST NON-BENEFICIAL
    private double wood;  // WOOD LEVEL BENEFICIAL
    private double brand; // BRAND LEVEL BENEFICIAL 
    private double origin; // BENEFICIAL 
    private double fitSearch; // BENEFICIAL    

    public Alternative(Guitar guitar, double price, double wood, double brand, double origin, double fitSearch) {
        this.guitar = guitar;
        this.price = price;
        this.wood = wood;
        this.brand = brand;
        this.origin = origin;
        this.fitSearch = fitSearch;        
    }

    public Guitar getGuitar() {
        return guitar;
    }

    public void setGuitar(Guitar guitar) {
        this.guitar = guitar;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getWood() {
        return wood;
    }

    public void setWood(double wood) {
        this.wood = wood;
    }

    public double getBrand() {
        return brand;
    }

    public void setBrand(double brand) {
        this.brand = brand;
    }

    public double getOrigin() {
        return origin;
    }

    public void setOrigin(double origin) {
        this.origin = origin;
    }

    public double getFitSearch() {
        return fitSearch;
    }

    public void setFitSearch(double fitSearch) {
        this.fitSearch = fitSearch;
    }
    
}
