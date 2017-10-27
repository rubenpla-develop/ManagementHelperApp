package bcn.alten.altenappmanagement.utils;

import java.util.Arrays;
import java.util.List;

import bcn.alten.altenappmanagement.R;
import bcn.alten.altenappmanagement.expandable.groupmodel.Category;
import bcn.alten.altenappmanagement.mvp.model.FollowUp;

public class CategoryDataFactory {

  public static List<Category> makeGenres() {
    return Arrays.asList(makeredCategory(),
        makeYellowCategory(),
        makeGreenCategory());
  }

  public static Category makeredCategory() {
    return new Category("URGENTE", makeRedFollows(), R.drawable.ic_error_black_24dp);
  }

  public static List<FollowUp> makeRedFollows() {
      FollowUp fup1 = new FollowUp("Ruben Pla Ferrero", "Sabadell", "1049459419");
      FollowUp fup2 = new FollowUp("Jorge Aviario Sole", "Caixa", "1049459419");
      FollowUp fup3 = new FollowUp("Ignacio Ferror Planalta", "La Caixa", "1049459419");
      FollowUp fup4 = new FollowUp("David Jardi Gil", "Banco Sabadell", "1049459419");
      FollowUp fup5 = new FollowUp("Adrian de Miguel Serrano", "Sabadell", "1049459419");
      
      return Arrays.asList(fup1, fup2, fup3, fup4, fup5);
  }

  public static Category makeYellowCategory() {
    return new Category("Atención", makeYellowFollows(), R.drawable.ic_warning_black_24dp);
  }

  public static List<FollowUp> makeYellowFollows() {
      FollowUp fup1 = new FollowUp("Yvette Hernandez Alonso", "Opentrends", "1049459419");
      FollowUp fup2 = new FollowUp("Cristian Garcia Aran", "Seat", "1049459419");
      

    return Arrays.asList(fup1, fup2);
  }

  public static Category makeGreenCategory() {
    return new Category("Up to Day", makeGreenFollows(), R.drawable.ic_thumb_up_black_24dp);
  }

  public static List<FollowUp> makeGreenFollows() {
      FollowUp fup1 = new FollowUp("Ruben Pla Ferrero", "Banco Sabadell", "1049459419");
      FollowUp fup2 = new FollowUp("Jorge Aviario Sole", "La Caixa", "1049459419");
      FollowUp fup3 = new FollowUp("Ignacio Ferror Planalta", "Caixa", "1049459419");

    return Arrays.asList(fup1, fup2, fup3);
  }
}

