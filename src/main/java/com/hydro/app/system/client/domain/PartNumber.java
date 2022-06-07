package com.hydro.app.system.client.domain;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.hydro.common.enums.Environment;

/**
 * Contains the structured alphanumeric part number. This number is generated
 * for each individual hydroponic system so it can be identified.
 * 
 * @author Sam Butler
 * @since May 28, 2022
 */
public class PartNumber {
    private static final String PART_NUMBER_FORMAT = "Required format is \"ppppppEssssss\" where:\n"
            + "- \"pppppp\" is the random six-digit product number\n"
            + "- \"E\" is the alphanumeric single-char environment (D,P)\n"
            + "- \"ssssss\" is the six-digit system id (zero-padded)\n";
    private static final Pattern PART_NUMBER_PATTERN = Pattern
            .compile("(?<productnumber>[0-9]{6})(?<environment>[D|P])(?<system>[0-9]{6})");

    private final String partNumber;
    private final int productNumber;
    private final Environment environment;
    private final int systemId;

    /**
     * Initialize a Part Number Object with the encoded Part Number String
     * 
     * @param partNumber Part Number
     */
    public PartNumber(String partNumber) {
        if (partNumber == null) {
            throw new IllegalArgumentException("Part Number must not be null. " + PART_NUMBER_FORMAT);
        }
        this.partNumber = partNumber;

        var matcher = PART_NUMBER_PATTERN.matcher(partNumber);
        if (!matcher.matches()) {
            throw new IllegalArgumentException(
                    String.format("Part Number \"%s\" is incorrectly formatted. %s", partNumber, PART_NUMBER_FORMAT));
        }

        environment = "P".equals(matcher.group("environment")) ? Environment.PRODUCTION : Environment.DEVELOPMENT;
        productNumber = valueOf(matcher, "productnumber");
        systemId = valueOf(matcher, "system");
    }

    public String getPartNumber() {
        return partNumber;
    }

    public int getProductNumber() {
        return productNumber;
    }

    public Environment getEnvironment() {
        return environment;
    }

    public int getSystemId() {
        return systemId;
    }

    @Override
    public String toString() {
        return partNumber;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        return partNumber.equals(other.toString());
    }

    @Override
    public int hashCode() {
        return partNumber.hashCode();
    }

    private static int valueOf(Matcher matcher, String group) {
        return Integer.valueOf(matcher.group(group));
    }
}