package com.teliolabs.tejas.ptn.util;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * TapiCommonNameAndValue
 */

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TapiCommonNameAndValue {
    @JsonProperty("value-name")
    private String valueName;

    @JsonProperty("value")
    private String value;
}

