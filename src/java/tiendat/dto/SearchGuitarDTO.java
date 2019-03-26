/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiendat.dto;

import java.io.Serializable;

public class SearchGuitarDTO implements Serializable{
    private int SearchGuitar; 
    private String MusicGenre; 
    private String BodyStyle; 
    private String PriceLevel; 
    private String Origin;
    private int UserId;

    public SearchGuitarDTO(int SearchGuitar, String MusicGenre, String BodyStyle, String PriceLevel, String Origin, int UserId) {
        this.SearchGuitar = SearchGuitar;
        this.MusicGenre = MusicGenre;
        this.BodyStyle = BodyStyle;
        this.PriceLevel = PriceLevel;
        this.Origin = Origin;
        this.UserId = UserId;
    }
    

    public SearchGuitarDTO(String MusicGenre, String BodyStyle, String PriceLevel, String Origin) {
        this.SearchGuitar = SearchGuitar;
        this.MusicGenre = MusicGenre;
        this.BodyStyle = BodyStyle;
        this.PriceLevel = PriceLevel;
        this.Origin = Origin;
    }

    public SearchGuitarDTO(String MusicGenre, String BodyStyle, String PriceLevel, String Origin, int UserId) {
        this.SearchGuitar = SearchGuitar;
        this.MusicGenre = MusicGenre;
        this.BodyStyle = BodyStyle;
        this.PriceLevel = PriceLevel;
        this.Origin = Origin;
        this.UserId = UserId;
    }

    public SearchGuitarDTO() {
    }

    public int getSearchGuitar() {
        return SearchGuitar;
    }

    public void setSearchGuitar(int SearchGuitar) {
        this.SearchGuitar = SearchGuitar;
    }

    public String getMusicGenre() {
        return MusicGenre;
    }

    public void setMusicGenre(String MusicGenre) {
        this.MusicGenre = MusicGenre;
    }

    public String getBodyStyle() {
        return BodyStyle;
    }

    public void setBodyStyle(String BodyStyle) {
        this.BodyStyle = BodyStyle;
    }

    public String getPriceLevel() {
        return PriceLevel;
    }

    public void setPriceLevel(String PriceLevel) {
        this.PriceLevel = PriceLevel;
    }

    public String getOrigin() {
        return Origin;
    }

    public void setOrigin(String Origin) {
        this.Origin = Origin;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int UserId) {
        this.UserId = UserId;
    }
    
}
