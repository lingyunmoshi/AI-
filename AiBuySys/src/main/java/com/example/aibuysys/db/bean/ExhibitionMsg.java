package com.example.aibuysys.db.bean;

public class ExhibitionMsg {
    public Integer id;
    public String exhibition;
    public int price;
    public String startdate;
    public String enddate;
    public String description;
    public ExhibitionMsg(){
    }
    public ExhibitionMsg(Integer id,String exhibition, int price, String startdate, String enddate, String description) {
        this.id=id;
        this.exhibition = exhibition;
        this.price = price;
        this.startdate = startdate;
        this.enddate = enddate;
        this.description = description;
    }


    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getExhibition() {
        return exhibition;
    }

    public void setExhibition(String exhibition) {
        this.exhibition = exhibition;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
