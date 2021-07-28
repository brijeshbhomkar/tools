package com.dhs.dhsuiportal.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class DailyRecord extends Record {

    @JsonProperty("ICMR ID")
    private long id;

    @JsonProperty("LAB Name")
    private String labName;

    @JsonProperty("Patient ID")
    private String patientId;

    @JsonProperty("Patient Name")
    private String patientName;

    @JsonProperty("Age")
    private int age;

    @JsonProperty("Gender")
    private String gender;

    @JsonProperty("District of Residence")
    private String districtResidence;

    @JsonProperty("State of Residence")
    private String stateResidence;

    @JsonProperty("Address")
    private String address;

    @JsonProperty("Village/Town")
    private String villageTown;

    @JsonProperty("Contact Number")
    private String contactNumber;

    @JsonProperty("Confirmation Date")
    private String confirmationDate;
}
