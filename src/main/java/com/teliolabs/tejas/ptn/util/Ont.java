package com.teliolabs.tejas.ptn.util;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Ont{
    public ArrayList<TapiCommonNameAndValue> name;
    public String uuid;
    @JsonProperty("contained-holder")
    public ArrayList<TapiEquipmentHolder> equipmentHolder;
    @JsonProperty("actual-equipment")
    public ActualEquipment actualEquipment;
    @JsonProperty("is-expected-actual-mismatch")
    public boolean isExpectedActualMismatch;
    @JsonProperty("equipment-location")
    public String equipmentLocation;
    public String category;
    @JsonProperty("additional-information")
    public ArrayList<AdditionalInformation> additionalInformation;
}
