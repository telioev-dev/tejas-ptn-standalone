package com.teliolabs.tejas.ptn.util;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;


/**
 * TapiEquipmentCommonHolderProperties
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TapiEquipmentCommonHolderProperties {
    @JsonProperty("holder-location")
    private String holderLocation;

    @JsonProperty("is-guided")
    private Boolean isGuided;

    @JsonProperty("holder-category")
    private String holderCategory;
}
