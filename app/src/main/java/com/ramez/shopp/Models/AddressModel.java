package com.ramez.shopp.Models;

public class AddressModel {
    private int primaryID;

    private int userID = 0, addressID = 0;
    private String addressLat = "", addressLng = "", addressType = "";
    private String addressMark = "", addressNote = "", addressText = "";
    public String addressAttitude = "", physicalAddress = "";
    public String add_area="";
    public int area_id=0;
    public String road_number="",block_numer="",building_number="",flat_numer="";

    public AddressModel(int primaryID, int userID, int addressID, String addressLat, String addressLng, String addressType, String addressMark, String addressNote, String addressText, String addressAttitude, String physicalAddress, String add_area, int area_id, String road_number, String block_numer, String building_number, String flat_numer) {
        this.primaryID = primaryID;
        this.userID = userID;
        this.addressID = addressID;
        this.addressLat = addressLat;
        this.addressLng = addressLng;
        this.addressType = addressType;
        this.addressMark = addressMark;
        this.addressNote = addressNote;
        this.addressText = addressText;
        this.addressAttitude = addressAttitude;
        this.physicalAddress = physicalAddress;
        this.add_area = add_area;
        this.area_id = area_id;
        this.road_number = road_number;
        this.block_numer = block_numer;
        this.building_number = building_number;
        this.flat_numer = flat_numer;
    }

    public int getPrimaryID() {
        return primaryID;
    }

    public void setPrimaryID(int primaryID) {
        this.primaryID = primaryID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getAddressID() {
        return addressID;
    }

    public void setAddressID(int addressID) {
        this.addressID = addressID;
    }

    public String getAddressLat() {
        return addressLat;
    }

    public void setAddressLat(String addressLat) {
        this.addressLat = addressLat;
    }

    public String getAddressLng() {
        return addressLng;
    }

    public void setAddressLng(String addressLng) {
        this.addressLng = addressLng;
    }

    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }

    public String getAddressMark() {
        return addressMark;
    }

    public void setAddressMark(String addressMark) {
        this.addressMark = addressMark;
    }

    public String getAddressNote() {
        return addressNote;
    }

    public void setAddressNote(String addressNote) {
        this.addressNote = addressNote;
    }

    public String getAddressText() {
        return addressText;
    }

    public void setAddressText(String addressText) {
        this.addressText = addressText;
    }

    public String getAddressAttitude() {
        return addressAttitude;
    }

    public void setAddressAttitude(String addressAttitude) {
        this.addressAttitude = addressAttitude;
    }

    public String getPhysicalAddress() {
        return physicalAddress;
    }

    public void setPhysicalAddress(String physicalAddress) {
        this.physicalAddress = physicalAddress;
    }

    public String getAdd_area() {
        return add_area;
    }

    public void setAdd_area(String add_area) {
        this.add_area = add_area;
    }

    public int getArea_id() {
        return area_id;
    }

    public void setArea_id(int area_id) {
        this.area_id = area_id;
    }

    public String getRoad_number() {
        return road_number;
    }

    public void setRoad_number(String road_number) {
        this.road_number = road_number;
    }

    public String getBlock_numer() {
        return block_numer;
    }

    public void setBlock_numer(String block_numer) {
        this.block_numer = block_numer;
    }

    public String getBuilding_number() {
        return building_number;
    }

    public void setBuilding_number(String building_number) {
        this.building_number = building_number;
    }

    public String getFlat_numer() {
        return flat_numer;
    }

    public void setFlat_numer(String flat_numer) {
        this.flat_numer = flat_numer;
    }
}
