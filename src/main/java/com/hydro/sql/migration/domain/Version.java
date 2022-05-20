package com.hydro.sql.migration.domain;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.util.Assert;

/**
 * Version object for holding a string version value.
 * 
 * @author Sam Butler
 * @since May 20, 2022
 */
public class Version implements Comparable<Version> {
    private final String VERSION_REGEX = "[Vv](\\d+)\\.(\\d+)\\.(\\d+)\\.*(\\d*)";

    private String version;

    private int major;

    private int minor;

    private int fix;

    private int build;

    public final String get() {
        return this.version;
    }

    public int getMajor() {
        return major;
    }

    public int getMinor() {
        return minor;
    }

    public int getFix() {
        return fix;
    }

    public int getBuild() {
        return build;
    }

    public Version(String version) {
        Assert.notNull(version, "Version can not be null!");

        version = version.matches("[Vv]") ? version : "V" + version;
        Pattern pattern = Pattern.compile(VERSION_REGEX);
        Matcher m = pattern.matcher(version);

        if (!m.find())
            throw new IllegalArgumentException(
                    "Invalid version format! Format Required is '<MAJOR>.<MINOR>.<FIX>'. Invalid Version of: "
                            + version);

        this.version = m.group(0).replaceAll("[Vv]", "");
        this.major = Integer.parseInt(m.group(1));
        this.minor = Integer.parseInt(m.group(2));
        this.fix = Integer.parseInt(m.group(3));
        this.build = Integer.parseInt("".equals(m.group(4)) ? "0" : m.group(4));
    }

    @Override
    public int compareTo(Version that) {
        if (that == null)
            return 1;
        String[] thisParts = this.get().split("\\.");
        String[] thatParts = that.get().split("\\.");
        int length = Math.max(thisParts.length, thatParts.length);
        for (int i = 0; i < length; i++) {
            int thisPart = i < thisParts.length ? Integer.parseInt(thisParts[i]) : 0;
            int thatPart = i < thatParts.length ? Integer.parseInt(thatParts[i]) : 0;
            if (thisPart < thatPart)
                return -1;
            if (thisPart > thatPart)
                return 1;
        }
        return 0;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that)
            return true;
        if (that == null)
            return false;
        if (this.getClass() != that.getClass())
            return false;
        return this.compareTo((Version) that) == 0;
    }

}
