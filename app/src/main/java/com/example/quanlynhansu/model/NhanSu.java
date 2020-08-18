package com.example.quanlynhansu.model;

import androidx.annotation.NonNull;

public class NhanSu {
    private String id = "null";
    private String name = "null";
    private String degree = "null";
    private String hoppies = "null";
    private Boolean isChecked = false;

    public NhanSu(){}

    public NhanSu(String name, String degree, String hoppies) {
        this.name = name;
        this.degree = degree;
        this.hoppies = hoppies;
    }

//    Getter
    public String getName() {
        return name;
    }

    public String getDegree() {
        return degree;
    }

    public String getHoppies() {
        return hoppies;
    }

    public Boolean isChecked() { return isChecked; }

    public String getID() { return id; }

    //    Setter
    public void setName(String name) {
        this.name = name;
    }

    public void setIsChecked(Boolean isChecked) {
        this.isChecked = isChecked;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public void setHoppies(String hoppies) {
        this.hoppies = hoppies;
    }

    public void setID(String id) { this.id = id; }

//    Method
    @NonNull
    @Override
    public String toString() {
        return String.format("Họ tên: %s, Bằng cấp: %s, Sở thích: %s", this.name, this.degree, this.hoppies);
    }


    public void setChecked(Boolean checked) {
        isChecked = checked;
    }
}
