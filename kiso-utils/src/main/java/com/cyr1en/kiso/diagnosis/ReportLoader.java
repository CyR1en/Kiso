package com.cyr1en.kiso.diagnosis;

import com.cyr1en.kiso.diagnosis.reporter.Header;
import com.cyr1en.kiso.diagnosis.reporter.JavaReporter;
import com.cyr1en.kiso.diagnosis.reporter.MemoryReporter;
import com.cyr1en.kiso.diagnosis.reporter.OSReporter;

import java.util.HashMap;

public class ReportLoader {

    private static HashMap<Class<? extends IReporter>, IReporter> reporters = new HashMap<>();

    static {
        reporters.put(Header.class, new Header());
        reporters.put(JavaReporter.class, new JavaReporter());
        reporters.put(MemoryReporter.class, new MemoryReporter());
        reporters.put(OSReporter.class, new OSReporter());
    }

    public static void addReporter(Class<? extends IReporter> clazz) {
        try {
            reporters.put(clazz, clazz.newInstance());
        } catch (InstantiationException | IllegalAccessException e) {
            //todo: impl logger
            //Logger.err("Error instantiating reporter class; is it private? Offender: " + clazz.getName());
        }
    }

    public static  HashMap<Class<? extends IReporter>, IReporter> getReporters() {
        return reporters;
    }

    public static IReporter get(Class clazz) {
        return reporters.get(clazz);
    }
}
