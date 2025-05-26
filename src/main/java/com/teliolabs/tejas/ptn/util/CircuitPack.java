package com.teliolabs.tejas.ptn.util;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CircuitPack{
    public ArrayList<TapiCommonNameAndValue> name;
    public String uuid;
    @JsonProperty("actual-equipment")
    public ActualEquipment actualEquipment;
    @JsonProperty("is-expected-actual-mismatch")
    public boolean isExpectedActualMismatch;
    public String category;
    @JsonProperty("additional-information")
    public ArrayList<AdditionalInformation> additionalInformation;
}

