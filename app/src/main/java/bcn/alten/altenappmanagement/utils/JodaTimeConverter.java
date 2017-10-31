package bcn.alten.altenappmanagement.utils;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.Months;

public class JodaTimeConverter {

/*    public static final String ONE_MONTH_INTERVAL = "2629746000";
    public static final String THREE_MONTH_INTERVAL = "7889238000";
    public static final String SIX_MONTH_INTERVAL = "15778476000";*/

    private final String DATE_PATTERN = "dd MM yyyy";

    //jodaTimeConverter class singleton instance
    private static JodaTimeConverter instance = null;

    public static JodaTimeConverter getInstance() {
        if (instance == null) {
            instance = new JodaTimeConverter();
        }

        return instance;
    }

    public String parsefromDatePicker(final int month,final int day,final int year) {
        LocalDate dateTime = new LocalDate(year, month + 1, day);
        DateTime dt = dateTime.toDateTimeAtCurrentTime();
        String dateInMillis = String.valueOf(dt.getMillis());

        return dateInMillis;
    }

    public Long parseDateFromMillisStringFormat(final String millis) {
        DateTime finalDateTime = new DateTime(Long.valueOf(millis));

        return finalDateTime.getMillis();
    }

    public String getDateInStringFormat(final Long millis) {
        String date = new DateTime(Long.valueOf(millis)).toString(DATE_PATTERN);

        return date;
    }

    public int getMonthsOfDifferenceWithCurrentDate(final Long millis) {
        DateTime fromDateTime = new DateTime(Long.valueOf(millis));
        DateTime currentDate = DateTime.now();

        int difference = Months.monthsBetween(fromDateTime, currentDate).getMonths();

        return difference;
    }

    public int getMonthsOfDifferenceWithCurrentDate(final String millis) {
        DateTime fromDateTime = new DateTime(Long.valueOf(millis));
        DateTime currentDate = DateTime.now();

        int difference = Months.monthsBetween(fromDateTime, currentDate).getMonths();

        return difference;
    }
}
