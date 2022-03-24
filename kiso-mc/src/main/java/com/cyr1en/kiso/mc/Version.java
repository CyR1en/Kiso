package com.cyr1en.kiso.mc;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Class for representing the version of the plugin.
 *
 * <p>This class will follow the conventions of Semantic versioning.</p>
 *
 * @see <a href="https://semver.org/">https://semver.org/</a>
 */
public class Version {
    private int major;
    private int minor;
    private int patch;

    /**
     * Default constructor
     * <p>
     * Sets major, minor, and patch to 0
     */
    public Version() {
        this(0, 0, 0);
    }

    /**
     * Multi-arg constructor
     * <p>
     * Use thins constructor for to set a more precise version.
     */
    public Version(int major, int minor, int patch) {
        this.major = major;
        this.minor = minor;
        this.patch = patch;
    }

    /**
     * Get a version from a string.
     *
     * This constructor is deprecated use the static function {@link Version::parse(String)} instead.
     *
     * @param versionString String to parse.
     */
    @Deprecated
    public Version(String versionString) {
        Version ver = Version.parse(versionString);
        this.major = ver.getMajor();
        this.minor = ver.getMinor();
        this.patch = ver.getPatch();
    }

    public int getMajor() {
        return major;
    }

    public void setMajor(int major) {
        this.major = major;
    }

    public int getMinor() {
        return minor;
    }

    public void setMinor(int minor) {
        this.minor = minor;
    }

    public int getPatch() {
        return patch;
    }

    public void setPatch(int patch) {
        this.patch = patch;
    }

    /**
     * Get this object a simple string
     * <p>
     * This is different from {@link Version::toString()}. This concatenates major, minor, and patch (delimited by
     * a period).
     *
     * @return Simple string of this version object
     */
    public String asString() {
        return getMajor() + "." + getMinor() + "." + getPatch();
    }

    public static Version parse(String versionString) {
        String[] split = versionString.split("\\.");
        if (split.length == 0) return new Version();
        List<Integer> parsed = Arrays.stream(split).map(Integer::parseInt).collect(Collectors.toList());
        while (parsed.size() < 3) parsed.add(0);
        return new Version(parsed.get(0), parsed.get(1), parsed.get(2));
    }

    public boolean isNewerThan(Version version) {
        if (getMajor() > version.getMajor())
            return true;
        else if (getMajor() < version.getMajor())
            return false;
        else if (getMinor() > version.getMinor())
            return true;
        else if (getMinor() < version.getMinor())
            return false;
        return getPatch() > version.getPatch();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Version version = (Version) o;
        return major == version.major && minor == version.minor && patch == version.patch;
    }

    @Override
    public int hashCode() {
        return Objects.hash(major, minor, patch);
    }

    private void assertVersionStringSize(String[] strings) {
        if (strings.length != 3)
            throw new IllegalArgumentException("The string of version must contain a major, minor, and patch.");
    }

    @Override
    public String toString() {
        return "Version{" +
                "major=" + major +
                ", minor=" + minor +
                ", patch=" + patch +
                '}';
    }
}
