package com.teliolabs.tejas.ptn.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Slf4j
public class ArgumentLogger {

    public static void log(ApplicationArguments args) {
        final List nonOptionArgs = args.getNonOptionArgs();
        final String[] sourceArgs = args.getSourceArgs();
        final Set optionNames = args.getOptionNames();

        nonOptionArgs.forEach(nonOption -> log.info("## Non Option Args : " + nonOption));
        optionNames.forEach(option -> log.info("## Option Names    : " + option));
        Arrays.stream(sourceArgs).forEach(srcArgs -> log.info("## Source Args     : " + srcArgs));
    }
}
