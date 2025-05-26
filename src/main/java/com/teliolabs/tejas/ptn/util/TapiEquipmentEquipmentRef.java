package com.teliolabs.tejas.ptn.util;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;


/**
 * TapiEquipmentEquipmentRef
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TapiEquipmentEquipmentRef {
    @JsonProperty("device-uuid")
    private String deviceUuid;

    @JsonProperty("equipment-uuid")
    private String equipmentUuid;
}

