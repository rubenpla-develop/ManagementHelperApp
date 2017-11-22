package bcn.alten.altenappmanagement.utils;

import android.content.res.Resources;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import bcn.alten.altenappmanagement.R;
import bcn.alten.altenappmanagement.application.AltenApplication;
import bcn.alten.altenappmanagement.expandable.groupmodel.QMCategory;
import bcn.alten.altenappmanagement.mvp.model.QMItem;

public class QMDataFactory {

    private final String TAG = CategoryDataFactory.class.getSimpleName();

    private final int WEEK_NEXT_TO_CURRENT_WEEK = 3;
    private final int CURRENT_WEEK = 2;
    private final int WEEK_PREV_TO_CURRENT_WEEK = 1;

    private static QMDataFactory instance;

    public static QMDataFactory getInstance() {
        if (instance == null) {
            instance = new QMDataFactory();
        }

        return instance;
    }

    public List<QMCategory> getCurrentWeeks(final List<QMItem> list) {
        Resources resources = AltenApplication.getInstance().getResources();
        List<QMItem> nextToCurrentWeekList = new ArrayList<>();
        List<QMItem> currentWeekList = new ArrayList<>();
        List<QMItem> previousToCurrentWeekList = new ArrayList<>();

        for (QMItem qm : list) {
            int currentWeek = JodaTimeConverter.getInstance().getCurrentWeekOfYear();

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
        filteredList.add(new QMCategory(resources.getString(R.string.qm_category_next_week_title),
                nextToCurrentWeekList, R.drawable.ic_qm_calendar_icon_category_black_32dp));
        filteredList.add(new QMCategory(resources.getString(R.string.qm_category_current_week_title),
                currentWeekList, R.drawable.ic_qm_calendar_icon_category_black_32dp));
        filteredList.add(new QMCategory(resources.getString(R.string.qm_category_previous_week_title),
                previousToCurrentWeekList, R.drawable.ic_qm_calendar_icon_category_black_32dp));

        return filteredList;
    }

    private int setWeekMatchResult(int currentWeek, QMItem item) {
        int weekMatch = 0 ;

        final boolean NEXT_TO_CURRENT_WEEK = item.getWeek() == (currentWeek + 1);
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

    /*
     *  TESTING MOCK METHODS, NOT USE FOR PRODUCTION
     */
    public static List<QMItem> createMockQMItemList() {
        QMItem fup1 = new QMItem(30,"Ruben Pla Ferrero", "", "Sabadell", "", "1501338492096", ""); //29-7-2017
        QMItem fup2 = new QMItem(11,"Laura Hernandez Alonso", "", "Opentrends", "", "1489501854205", "");//14-3-2017
        QMItem fup3 = new QMItem(50, "Ignacio Ferror Planalta", "", "Caixa", "", "1513002724212", ""); //11-12-2017
        QMItem fup4 = new QMItem(48,"Ruben Pla Ferrero", "","Banco Sabadell", "", "1512052470308", "");//30-11-2017
        QMItem fup5 = new QMItem(47,"Jorge Aviario Sole", "", "La Caixa", "", "1511274885640", ""); //21-11-2017
        QMItem fup6 = new QMItem(47,"Jorge Aviario Sole", "", "Caixa", "", "1511361298215", ""); //22-11-2017
        QMItem fup7 = new QMItem(47,"Ignacio Ferror Planalta", "", "La Caixa", "", "1511620509125", ""); //25-11-2017
        QMItem fup8 = new QMItem(46,"David Jardi Gil", "", "Banco Sabadell", "", "1510583738614", "");//13-11-2017
        QMItem fup9 = new QMItem(46,"Cristian Garcia Aran", "", "Seat", "", "1510756571338", ""); //15-11-2017

        List<QMItem> tempList = Arrays.asList(fup1, fup2, fup3, fup4, fup5, fup6, fup7, fup8, fup9);

        return tempList;
    }
}
