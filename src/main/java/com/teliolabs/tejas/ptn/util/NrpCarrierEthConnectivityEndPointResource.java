package com.teliolabs.tejas.ptn.util;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NrpCarrierEthConnectivityEndPointResource {
@JsonProperty("ce-vlan-id-list-and-untag")
public CeVlanIdListAndUntag ceVlanIdListAndUntag;
@JsonProperty("cos-identifier-list")
    public ArrayList<CosIdentifierList> cosIdentifierList;
    @JsonProperty("s-vlan-id-list")
    public SVlanIdList sVlanIdList;
}
