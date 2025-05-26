package com.teliolabs.tejas.ptn.util;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CeVlanIdListAndUntag {
    @JsonProperty("vlan-id")
    public ArrayList<VlanId> vlanId;
    @JsonProperty("vlan-id-mapping-type")
    public String vlanIdMappingType;
    @JsonProperty("untagged-and-prio-tagged-included")
    public boolean untaggedAndPrioTaggedIncluded;
}
