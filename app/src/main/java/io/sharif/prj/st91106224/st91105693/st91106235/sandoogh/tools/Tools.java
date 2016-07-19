package io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.tools;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class Tools {

    public static <T> List<T> iteratorToList(Iterator<T> iterator) {
        List<T> copy = new ArrayList<>();
        while (iterator.hasNext())
            copy.add(iterator.next());
        return copy;
    }

    public static String getCurrentShamsidate() {
        Locale loc = new Locale("en_US");
        SolarCalendar sc = new SolarCalendar();
        return String.valueOf(sc.year) + "/" + String.format(loc, "%02d",
                sc.month) + "/" + String.format(loc, "%02d", sc.date);
    }


}
