package bcn.alten.altenappmanagement.utils.factory;

import android.content.res.Resources;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import bcn.alten.altenappmanagement.R;
import bcn.alten.altenappmanagement.ui.adapter.expandable.groupmodel.FollowUpCategory;
import bcn.alten.altenappmanagement.AltenApplication;
import bcn.alten.altenappmanagement.model.FollowUp;
import bcn.alten.altenappmanagement.utils.JodaTimeConverter;

public class FollowUpFactory {

    private final String TAG = FollowUpFactory.class.getSimpleName();

    private static FollowUpFactory instance;

    public static FollowUpFactory getInstance() {
        if (instance == null) {
            instance = new FollowUpFactory();
        }

        return instance;
    }

    public List<FollowUpCategory> getDataFilteredByCategory(final List<FollowUp> list) {

        Resources resources = AltenApplication.getInstance().getResources();
        List<FollowUp> red = new ArrayList<>();
        List<FollowUp> yellow = new ArrayList<>();
        List<FollowUp> green = new ArrayList<>();

        for (FollowUp follow : list) {
            int months = JodaTimeConverter.getInstance()
                    .getMonthsOfDifferenceWithCurrentDate(follow.getDateLastFollow());

            if (months < 1) {
                green.add(follow);
            } else if (months >= 1 && months < 3) {
                yellow.add(follow);
            } else if (months >= 3) {
                red.add(follow);
            }
        }

        List<FollowUpCategory> filteredList = new ArrayList<>();
        filteredList.add(new FollowUpCategory(resources.getString(R.string.category_urgent),
                red, R.drawable.ic_error_black_24dp));
        filteredList.add(new FollowUpCategory(resources.getString(R.string.category_warning), yellow,
                R.drawable.ic_warning_black_24dp));
        filteredList.add(new FollowUpCategory(resources.getString(R.string.category_up_to_day), green,
                R.drawable.ic_thumb_up_black_24dp));

        return filteredList;
    }
    
    /*
     *  TESTING MOCK METHODS, NOT USE FOR PRODUCTION
     */
    //TODO from this points all code is for testing purpose
    public static List<FollowUpCategory> makeGenres() {
        return Arrays.asList(makeredCategory(),
                makeYellowCategory(),
                makeGreenCategory());
    }

    public static FollowUpCategory makeredCategory() {
        return new FollowUpCategory("URGENTE", makeRedFollows(), R.drawable.ic_error_black_24dp);
    }

    public static List<FollowUp> makeRedFollows() {
        FollowUp fup1 = new FollowUp("1","Ruben Pla Ferrero", "1","Sabadell", "1476193462083", "", "");
        FollowUp fup2 = new FollowUp("1","Jorge Aviario Sole", "1","Caixa", "1049459419", "", "");
        FollowUp fup3 = new FollowUp("1","Ignacio Ferror Planalta", "1","La Caixa", "1049459419", "", "");
        FollowUp fup4 = new FollowUp("1","David Jardi Gil", "1","Banco Sabadell", "1049459419", "", "");
        FollowUp fup5 = new FollowUp("1","Adrian de Miguel Serrano", "1","Sabadell", "1049459419", "", "");

        return Arrays.asList(fup1, fup2, fup3, fup4, fup5);
    }

    public static FollowUpCategory makeYellowCategory() {
        return new FollowUpCategory("Atención", makeYellowFollows(), R.drawable.ic_warning_black_24dp);
    }

    public static List<FollowUp> makeYellowFollows() {
        FollowUp fup1 = new FollowUp("1","Yvette Hernandez Alonso", "1","Opentrends", "1497102810067", "", "");
        FollowUp fup2 = new FollowUp("1","Cristian Garcia Aran", "1","Seat", "1049459419", "", "");


        return Arrays.asList(fup1, fup2);
    }

    public static FollowUpCategory makeGreenCategory() {
        return new FollowUpCategory("Up to Day", makeGreenFollows(), R.drawable.ic_thumb_up_black_24dp);
    }

    public static List<FollowUp> makeGreenFollows() {
        FollowUp fup1 = new FollowUp("1","Ruben Pla Ferrero", "1","Banco Sabadell", "1049459419", "", "");
        FollowUp fup2 = new FollowUp("1","Jorge Aviario Sole", "1","La Caixa", "1049459419", "", "");
        FollowUp fup3 = new FollowUp("1","Ignacio Ferror Planalta", "1","Caixa", "1505915671853", "", "");

        return Arrays.asList(fup1, fup2, fup3);
    }

    public static List<FollowUp> createMockFollowUpList() {
        FollowUp fup1 = new FollowUp("1","Ruben Pla Ferrero", "1","Sabadell", "1476197961067", "", ""); //urgent
        FollowUp fup2 = new FollowUp("1","Yvette Hernandez Alonso", "1","Opentrends", "1497102810067", "", "");//warning
        FollowUp fup3 = new FollowUp("1","Ignacio Ferror Planalta", "1","Caixa", "1505915671853", "", ""); //OK
        FollowUp fup4 = new FollowUp("1","Ruben Pla Ferrero", "1","Banco Sabadell", "1491653595412", "", "");//warning
        FollowUp fup5 = new FollowUp("1","Jorge Aviario Sole", "1","La Caixa", "1501330346854", "", ""); //urgent
        FollowUp fup6 = new FollowUp("1","Jorge Aviario Sole", "1","Caixa", "1505915671853", "", ""); //OK
        FollowUp fup7 = new FollowUp("1","Ignacio Ferror Planalta", "1","La Caixa", "1505915671853", "", ""); //OK
        FollowUp fup8 = new FollowUp("1","David Jardi Gil", "1","Banco Sabadell", "1503058305898", "", "");//warning
        FollowUp fup9 = new FollowUp("1","Cristian Garcia Aran", "1","Seat", "1505915671853", "", ""); //OK

        List<FollowUp> tempList = Arrays.asList(fup1, fup2, fup3, fup4, fup5, fup6, fup7, fup8,
                fup9);

        return tempList;
    }

    public static List<FollowUpCategory> createMockFilteredCategories() {
        FollowUp fup1 = new FollowUp("1","Ruben Pla Ferrero", "1","Sabadell", "1476197961067", "", ""); //urgent
        FollowUp fup2 = new FollowUp("1","Yvette Hernandez Alonso", "1","Opentrends", "1497102810067", "", "");//warning
        FollowUp fup3 = new FollowUp("1","Ignacio Ferror Planalta", "1","Caixa", "1505915671853", "", ""); //OK
        FollowUp fup4 = new FollowUp("1","Ruben Pla Ferrero", "1","Banco Sabadell", "1497102810067", "", "");//warning
        FollowUp fup5 = new FollowUp("1","Jorge Aviario Sole", "1","La Caixa", "1476197961067", "", ""); //urgent
        FollowUp fup6 = new FollowUp("1","Jorge Aviario Sole", "1","Caixa", "1505915671853", "", ""); //OK
        FollowUp fup7 = new FollowUp("1","Ignacio Ferror Planalta", "1","La Caixa", "1505915671853", "", ""); //OK
        FollowUp fup8 = new FollowUp("1","David Jardi Gil", "1","Banco Sabadell", "1497102810067", "", "");//warning
        FollowUp fup9 = new FollowUp("1","Cristian Garcia Aran", "1","Seat", "1505915671853", "", ""); //OK

        List<FollowUp> tempList = Arrays.asList(fup1, fup2, fup3);
        List<FollowUp> red = new ArrayList<>();
        List<FollowUp> yellow = new ArrayList<>();
        List<FollowUp> green = new ArrayList<>();

        for (FollowUp follow : tempList) {
            int months = JodaTimeConverter.getInstance()
                    .getMonthsOfDifferenceWithCurrentDate(follow.getDateLastFollow());

            if (months > 0 && months < 3) {
                green.add(follow);
            } else if (months >= 3 && months < 6) {
                yellow.add(follow);
            } else if (months >= 6) {
                red.add(follow);
            }
        }

        List<FollowUpCategory> catList = new ArrayList<>();
        catList.add(new FollowUpCategory("URGENTE", red, R.drawable.ic_error_black_24dp));
        catList.add(new FollowUpCategory("Atención", yellow, R.drawable.ic_warning_black_24dp));
        catList.add(new FollowUpCategory("Up to Day", green, R.drawable.ic_thumb_up_black_24dp));

        return catList;
    }
}

