package com.teliolabs.tejas.ptn.util;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;


/**
 * TapiEquipmentActualHolder
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TapiEquipmentActualHolder {
    @JsonProperty("common-holder-properties")
    private TapiEquipmentCommonHolderProperties commonHolderProperties;
}
