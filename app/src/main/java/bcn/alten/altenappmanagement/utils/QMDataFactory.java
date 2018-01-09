package bcn.alten.altenappmanagement.utils;

import android.content.res.Resources;
import android.support.annotation.StringRes;

import org.joda.time.DateTimeConstants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import bcn.alten.altenappmanagement.R;
import bcn.alten.altenappmanagement.application.AltenApplication;
import bcn.alten.altenappmanagement.expandable.groupmodel.QMCategory;
import bcn.alten.altenappmanagement.mvp.model.QMItem;

import static bcn.alten.altenappmanagement.utils.QMCalendarController.QMCalendarInstance;

public class QMDataFactory {

    private final String TAG = CategoryDataFactory.class.getSimpleName();

    public static final int QM_HEADER_ARROW_NEXT_WEEK_ACTION = 5;
    public static final int QM_HEADER_ARROW_PREVIOUS_WEEK_ACTION = 4;

    private final int WEEK_NEXT_TO_CURRENT_WEEK = 3;
    private final int CURRENT_WEEK = 2;
    private final int WEEK_PREV_TO_CURRENT_WEEK = 1;

    private int currentWeek = 0;
    private int currentYear;

    private static QMDataFactory instance;

    public static QMDataFactory FactoryInstance() {
        if (instance == null) {
            instance = new QMDataFactory();
        }

        return instance;
    }

    //TODO add year param to filter qm items with same week num. but different year
    public List<QMCategory> getSelectedWeek(final List<QMItem> list, final int week) {
        List<QMItem> nextToCurrentWeekList = new ArrayList<>();
        List<QMItem> currentWeekList = new ArrayList<>();
        List<QMItem> previousToCurrentWeekList = new ArrayList<>();

        currentWeek = week;

        for (QMItem qm : list) {
            int weekMatchResult = setWeekMatchResult(week, qm);

            switch (weekMatchResult) {
                case WEEK_NEXT_TO_CURRENT_WEEK :
                    nextToCurrentWeekList.add(qm);
                    break;
                case CURRENT_WEEK :
                    currentWeekList.add(qm);
                    break;
                case WEEK_PREV_TO_CURRENT_WEEK :
                    previousToCurrentWeekList.add(qm);
                    break;
                default:
                    break;
            }
        }

        List<QMCategory> filteredList = new ArrayList<>();
        StringFormatter formatter = new StringFormatter();

        filteredList.add(new QMCategory(formatter
                .formatParentGroupTitle(R.string.qm_category_week_generic_title, QMCalendarInstance()
                                .returnFollowingWeek(currentWeek, currentYear) ,
                        QMCalendarInstance().returnFollowingWeekYear(currentWeek, currentYear)),
                nextToCurrentWeekList, R.drawable.ic_qm_calendar_icon_category_black_32dp));

        filteredList.add(new QMCategory(formatter
                .formatParentGroupTitle(R.string.qm_category_week_generic_title, week, currentYear),
                currentWeekList, R.drawable.ic_qm_calendar_icon_category_black_32dp));

        filteredList.add(new QMCategory(formatter
                .formatParentGroupTitle(R.string.qm_category_week_generic_title, week - 1, currentYear),
                previousToCurrentWeekList, R.drawable.ic_qm_calendar_icon_category_black_32dp));

        return filteredList;
    }

    public List<QMCategory> getCurrentWeeks(final List<QMItem> list) {
        List<QMItem> nextToCurrentWeekList = new ArrayList<>();
        List<QMItem> currentWeekList = new ArrayList<>();
        List<QMItem> previousToCurrentWeekList = new ArrayList<>();

        currentWeek = JodaTimeConverter.getInstance().getCurrentWeekOfYear();
        currentYear = JodaTimeConverter.getInstance().getCurrentYear();

        for (QMItem qm : list) {

            int weekMatchResult = setWeekMatchResult(currentWeek, qm);

            switch (weekMatchResult) {
                case WEEK_NEXT_TO_CURRENT_WEEK :
                    nextToCurrentWeekList.add(qm);
                    break;
                case CURRENT_WEEK :
                    currentWeekList.add(qm);
                    break;
                case WEEK_PREV_TO_CURRENT_WEEK :
                    previousToCurrentWeekList.add(qm);
                    break;
                default:
                    break;
            }
        }

        List<QMCategory> filteredList = new ArrayList<>();
        StringFormatter formatter = new StringFormatter();

        filteredList.add(new QMCategory(formatter
                .formatParentGroupTitle(R.string.qm_category_next_week_title, QMCalendarInstance()
                        .returnFollowingWeek(currentWeek, currentYear) ,
                        QMCalendarInstance().returnFollowingWeekYear(currentWeek, currentYear)),
                nextToCurrentWeekList, R.drawable.ic_qm_calendar_icon_category_black_32dp));

        filteredList.add(new QMCategory(formatter
                .formatParentGroupTitle(R.string.qm_category_current_week_title, currentWeek, currentYear),
                currentWeekList, R.drawable.ic_qm_calendar_icon_category_black_32dp));

        filteredList.add(new QMCategory(formatter
                .formatParentGroupTitle(R.string.qm_category_previous_week_title,
                        currentWeek - 1, currentYear),
                previousToCurrentWeekList, R.drawable.ic_qm_calendar_icon_category_black_32dp));

        return filteredList;
    }

    //TODO filter qm items with same week num. but different year
    private int setWeekMatchResult(int currentWeek, QMItem item) {
        int weekMatch = 0 ;
        currentYear = JodaTimeConverter.getInstance().getCurrentYear();

        final boolean NEXT_TO_CURRENT_WEEK = item.getWeek() == QMCalendarInstance().returnFollowingWeek(currentWeek, currentYear);
        final boolean PREVIOUS_TO_CURRENT_WEEK = item.getWeek() == (currentWeek - 1);
        final boolean EQUALS_TO_CURRENT_WEEK = item.getWeek() == currentWeek;

        if (EQUALS_TO_CURRENT_WEEK || NEXT_TO_CURRENT_WEEK || PREVIOUS_TO_CURRENT_WEEK) {

            if (EQUALS_TO_CURRENT_WEEK){
                weekMatch = CURRENT_WEEK;
            } else if (NEXT_TO_CURRENT_WEEK) {
                weekMatch = WEEK_NEXT_TO_CURRENT_WEEK;
            } else if (PREVIOUS_TO_CURRENT_WEEK) {
                weekMatch = WEEK_PREV_TO_CURRENT_WEEK;
            }
        }

        return weekMatch;
    }

    public int getCurrentWeek() {
        return currentWeek;
    }

    private class StringFormatter {
        private Resources  res;

        public StringFormatter() {
            res = AltenApplication.getInstance().getApplicationContext().getResources();
        }

        public String formatParentGroupTitle(@StringRes int stringId, int week, int year) {

            String startWeek = JodaTimeConverter.getInstance()
                    .getWeekNumberRangeTime(week, DateTimeConstants.MONDAY, year);

            String endWeek = JodaTimeConverter.getInstance()
                    .getWeekNumberRangeTime(week, DateTimeConstants.SUNDAY, year);

             return String.format(res.getString(stringId), startWeek, endWeek);
        }
    }

    /*
     *  TESTING MOCK METHODS, NOT USE FOR PRODUCTION
     */
    public static List<QMItem> createMockQMItemList() {
        QMItem fup1 = new QMItem(30,"Banc Sabadell", "", "Ruben Pla Ferrero", "", "1501338492096", "19:15","Rechazada"); //29-7-2017
        QMItem fup2 = new QMItem(11,"Opentrends", "", "Laura Hernandez Alonso", "", "1489501854205","19:15", "Realizada");//14-3-2017
        QMItem fup3 = new QMItem(50, "La Caixa", "", "Ignacio Ferror Planalta", "", "1513002724212","19:15", "Planificada"); //11-12-2017
        QMItem fup4 = new QMItem(48,"Hays", "","Raul Salomé Gutierrez", "", "1512052470308","19:15", "Planificada");//30-11-2017
        QMItem fup5 = new QMItem(47,"La Caixa", "", "Jorge Aviario Sole", "", "1511274885640","19:15" , "Cancelada"); //21-11-2017
        QMItem fup6 = new QMItem(47,"Microsoft", "", "Amaia Ferrero Sanchez", "", "1511361298215", "19:15","Realizada"); //22-11-2017
        QMItem fup7 = new QMItem(47,"La Caixa", "", "Raul Milito Copón", "", "1511620509125", "19:15","Aceptada"); //25-11-2017
        QMItem fup8 = new QMItem(46,"Deutsche Bank ", "", "David Jardi Gil", "", "1510583738614","19:15", "Cancelada");//13-11-2017
        QMItem fup9 = new QMItem(46,"Seat", "", "Cristian Garcia Aran", "", "1510756571338","19:15","Cancelada"); //15-11-2017

        List<QMItem> tempList = Arrays.asList(fup1, fup2, fup3, fup4, fup5, fup6, fup7, fup8, fup9);

        return tempList;
    }
}