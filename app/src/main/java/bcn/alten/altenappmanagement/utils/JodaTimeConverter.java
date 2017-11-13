package bcn.alten.altenappmanagement.utils;

import android.util.Log;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.Months;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Calendar;

import bcn.alten.altenappmanagement.ui.dialog.FollowUpDialog;

public class JodaTimeConverter {

    private final String TAG = JodaTimeConverter.class.getSimpleName();

    private static final String DATE_PATTERN = "dd-MM-yyyy";

    public static final int PREVIOUS_DATE = 0;
    public static final int NEWER_DATE = 1;
    public static final int CURRENT_DATE = 2;

    //jodaTimeConverter class singleton instance
    private static JodaTimeConverter instance = null;

    public static JodaTimeConverter getInstance() {
        if (instance == null) {
            instance = new JodaTimeConverter();
        }

        return instance;
    }

    public String getCurrenDate() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        LocalDate dateTime = new LocalDate(year, month + 1, day);
        DateTime dt = dateTime.toDateTimeAtCurrentTime();
        String dateInMillis = String.valueOf(dt.getMillis());

        return dateInMillis;
    }

    public int compareDates(String dateInMillis) {
        Long chosenDate =  parseDateFromMillisStringFormat(dateInMillis);
        Long currentDate = Long.valueOf(getCurrenDate());

        int result = 0;

        if ( chosenDate >= currentDate ) {
            result = NEWER_DATE;
        } else if (chosenDate  < currentDate) {
            result = PREVIOUS_DATE;
        }

        return result;
    }

    public boolean isAValidDate(String date) {
        boolean isValidDate = false;

        if (!FollowUpDialog.NO_DATE.equalsIgnoreCase(date)) {
            isValidDate = true;
        }

        return isValidDate;
    }

    public String parsefromDatePicker(final int month,final int day,final int year) {
        LocalDate dateTime = new LocalDate(year, month + 1, day);
        DateTime dt = dateTime.toDateTimeAtCurrentTime();
        String dateInMillis = String.valueOf(dt.getMillis());

        return dateInMillis;
    }

    public Long parseDateFromMillisStringFormat(final String millis) {
        LocalDate dateTime = new LocalDate(Long.valueOf(millis));
        DateTime finalDateTime = dateTime.toDateTimeAtCurrentTime();

        return finalDateTime.getMillis();
    }

    public String parseDateFromStringPatternToMillis(String date) {
        String dateInMillis = null;

        try {
            DateTimeFormatter formatter = DateTimeFormat.forPattern(DATE_PATTERN);
            DateTime dt = formatter.parseDateTime(date);

            int month = dt.getMonthOfYear();
            int day = dt.getDayOfMonth();
            int year = dt.getYear();

            LocalDate localDate = new LocalDate(year, month, day);
            dt = localDate.toDateTimeAtCurrentTime();
            dateInMillis = String.valueOf(dt.getMillis());
        } catch (IllegalArgumentException illegalArgumentException) {
            dateInMillis = "";
            Log.e(TAG, "IllegalArgumentException : "
                    + illegalArgumentException.getLocalizedMessage());

        } finally {
            return dateInMillis;
        }
    }

    public String getDateInStringFormat(final String millis) {
        String date = "";

        try {
            date = new DateTime(Long.valueOf(millis)).toString(DATE_PATTERN);
            Log.i(TAG, "Parse Date successful, RESULT : " + date);
        } catch (NumberFormatException formatException) {
            Log.e(TAG, "ERROR NumberformatException : " + formatException.getLocalizedMessage());
        } finally {
            return date;
        }
    }

    public int getMonthsOfDifferenceWithCurrentDate(final String millis) {
        int difference = 0;

        try {
            DateTime fromDateTime = new DateTime(Long.valueOf(millis));
            DateTime currentDate = DateTime.now();

            difference = Months.monthsBetween(fromDateTime, currentDate).getMonths();
        } catch (NumberFormatException formatException) {
            Log.e(TAG, "getMonthsOfDifferenceWIthCurrentDate() // NumberFormatException : " +
                    formatException.getLocalizedMessage());
        } finally {
            return difference;
        }
    }
}