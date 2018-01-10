package bcn.alten.altenappmanagement.utils;

public class QMCalendarController {

    private final int WEEK_ONE_OF_YEAR = 1;

    private int yearIn;
    private int weekOfYearIn;

    private static QMCalendarController instance;
    private OnWeekMoveListener onWeekMoveListener;

    private QMCalendarController() {
    }

    public static QMCalendarController QMCalendarInstance() {
        if (instance == null) {
            instance = new QMCalendarController();
        }

        return instance;
    }

    public void saveCurrentWeekAndYear(int week, int year) {
        this.yearIn = year;
        this.weekOfYearIn = week;
    }

    public int returnFollowingWeek(int week, int year) {
        int weekFollowingNumber = week + 1;

        if (isLastWeekOfYear(week, year)) {
            return 1;
        }

        return weekFollowingNumber;
    }

    public int returnPreviousWeek(int week, int year) {
        int weekPreviousNumber = week - 1;

        if (isFirstWeekOfYear(week, year)) {
            weekPreviousNumber = 1;
        }

        return weekPreviousNumber;
    }

    public int returnFollowingWeekYear(int week, int year) {
       if (isLastWeekOfYear(week, year)) {
           return year + 1;
       }

       return year;
    }

    public int returnPreviousWeekYear(int week, int year) {
        if (isFirstWeekOfYear(week, year)) {
            return year - 1;
        }

        return year;
    }

    private boolean isLastWeekOfYear(int week, int year) {
        boolean isLastWeek =  false;

        if (JodaTimeConverter.getInstance().getMaximumWeeksValueOfYear(year) <= week) {
            isLastWeek = true;
        }
        //return week == JodaTimeConverter.FactoryInstance().getMaximumWeeksValueOfYear(year);

        return isLastWeek;
    }

    private boolean isFirstWeekOfYear(int week, int year) {
        boolean isFirstWeek = false;

        if (week == 1) {
            isFirstWeek = true;
        }

        return isFirstWeek;
    }

    public void updateCurrentYear(int year) {
        this.yearIn = year;
    }

    public int getYearForReference() {
        return yearIn;
    }

    public int getWeekForReference() {
        return weekOfYearIn;
    }

    public void setOnWeekYearListener(OnWeekMoveListener listener) {
        if (listener != null) {
            this.onWeekMoveListener = listener;
        }
    }

    public interface OnWeekMoveListener {
        void onNextWeek();
        void onPreviousWeek();
        void onYearMaximumWeeksReached();
        void onYearMinimumWeeksReached();
    }
}