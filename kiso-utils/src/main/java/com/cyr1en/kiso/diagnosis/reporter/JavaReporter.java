package com.cyr1en.kiso.diagnosis.reporter;

import com.cyr1en.kiso.diagnosis.Diagnostics;
import com.cyr1en.kiso.diagnosis.IReporter;

public class JavaReporter implements IReporter {

    private String name;
    private int priority;

    public JavaReporter() {
        this.name = "Java Reporter";
        this.priority = 3;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String report() {
        String javaProperty = System.getProperty("java.version");
        javaProperty = javaProperty.replaceAll("_", " ");
        String[] jProps = javaProperty.split(" ");
        String javaVersion = jProps[0];
        String buildNumber = jProps[1];
        return "Java Version: " + javaVersion + Diagnostics.LINE_SEPARATOR +
                "Build: " + buildNumber;
    }

    @Override
    public int getPriority() {
        return priority;
    }

}
