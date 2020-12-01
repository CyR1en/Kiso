package com.cyr1en.kiso.diagnosis.reporter;

import com.cyr1en.kiso.diagnosis.Diagnostics;
import com.cyr1en.kiso.diagnosis.IReporter;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class Header implements IReporter {

    private int priority;

    public Header() {
        this.priority = 0;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String report() {
        StringBuilder sb = new StringBuilder();
        DateTime dt = DateTime.now();
        DateTimeFormatter dTF = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        sb.append(dt.toString(dTF)).append(Diagnostics.DOUBLE_LINE_SEPARATOR);
        return sb.toString();
    }

    @Override
    public int getPriority() {
        return priority;
    }
}
