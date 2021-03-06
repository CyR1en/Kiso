package com.cyr1en.kiso.diagnosis.reporter;

import com.cyr1en.kiso.diagnosis.Diagnostics;
import com.cyr1en.kiso.diagnosis.IReporter;

public class OSReporter implements IReporter {

    private String name;
    private int priority;

    public OSReporter() {
        this.name = "OS Reporter";
        this.priority = 1;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String report() {
        return "OS name: " + System.getProperty("os.name") + Diagnostics.LINE_SEPARATOR +
                "OS arch: " + System.getProperty("os.arch") + Diagnostics.LINE_SEPARATOR +
                "OS version: " + System.getProperty("os.version");
    }

    @Override
    public int getPriority() {
        return priority;
    }

}
