package com.dhs.dhsuiportal.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
public class FilteredRecord implements Serializable {

    private String icmrId;
    //private String srfId;
    private String labName;
    private String labId;
    private String patientId;
    private String patientName;
    private int age;
    private String gender;
    private String stateOfResidence;
    private String district;
    private String phc;
    private String address;
    private String village;
    //private String email;
    private String contactNo;
    private String confirmationDate;

    public void setPhc(String phc) {
        if (phc != null && !phc.isEmpty()) {
            this.phc = phc.toUpperCase();
        }
    }

    @Override
    public String toString() {
        return "FilteredRecord{" +
                "icmrId='" + icmrId + '\'' +
                ", labName='" + labName + '\'' +
                ", labId='" + labId + '\'' +
                ", patientId='" + patientId + '\'' +
                ", patientName='" + patientName + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                ", district='" + district + '\'' +
                ", phc='" + phc + '\'' +
                ", address='" + address + '\'' +
                ", village='" + village + '\'' +
                ", contactNo='" + contactNo + '\'' +
                ", confirmationDate='" + confirmationDate + '\'' +
                '}';
    }
}
