package bcn.alten.altenappmanagement.mvp.model;

/**
 * Created by alten on 9/1/18.
 */

public class WeekRange {

    private int week;
    private int year;

    public WeekRange(int week) {
        this.week = week;
    }

    public WeekRange(int week, int year) {
        this.week = week;
        this.year = year;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
