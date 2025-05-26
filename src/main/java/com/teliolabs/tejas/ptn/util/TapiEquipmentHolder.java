package com.teliolabs.tejas.ptn.util;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * TapiEquipmentHolder
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TapiEquipmentHolder {
    @JsonProperty("name")
    private List<TapiCommonNameAndValue> name;

    @JsonProperty("uuid")
    private String uuid;

    @JsonProperty("actual-holder")
    private TapiEquipmentActualHolder actualHolder;

    @JsonProperty("occupying-fru")
    private TapiEquipmentEquipmentRef occupyingFru;

}
