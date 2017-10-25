package bcn.alten.altenappmanagement.utils;

import java.util.Arrays;
import java.util.List;

import bcn.alten.altenappmanagement.R;
import bcn.alten.altenappmanagement.expandable.groupmodel.Category;
import bcn.alten.altenappmanagement.mvp.model.FollowUpModel;

public class CategoryDataFactory {

  public static List<Category> makeGenres() {
    return Arrays.asList(makeredCategory(),
        makeYellowCategory(),
        makeGreenCategory());
  }

  public static Category makeredCategory() {
    return new Category("URGENTE", makeRedFollows(), R.drawable.ic_error_black_24dp);
  }

  public static List<FollowUpModel> makeRedFollows() {
      FollowUpModel fup1 = new FollowUpModel("Ruben Pla Ferrero", "Sabadell", 1049459419,
            "Correcto", "No hay comentarios");
      FollowUpModel fup2 = new FollowUpModel("Jorge Aviario Sole", "Caixa", 1079596395,
            "Correcto", "No hay comentarios");
      FollowUpModel fup3 = new FollowUpModel("Ignacio Ferror Planalta", "La Caixa", 1049945439,
            "Correcto", "No hay comentarios");
      FollowUpModel fup4 = new FollowUpModel("David Jardi Gil", "Banco Sabadell", 804959459,
            "Correcto", "No hay comentarios");
      FollowUpModel fup5 = new FollowUpModel("Adrian de Miguel Serrano", "Sabadell", 749679459,
            "Correcto", "No hay comentarios");
      
      return Arrays.asList(fup1, fup2, fup3, fup4, fup5);
  }

  public static Category makeYellowCategory() {
    return new Category("Atención", makeYellowFollows(), R.drawable.ic_warning_black_24dp);
  }

  public static List<FollowUpModel> makeYellowFollows() {
      FollowUpModel fup1 = new FollowUpModel("Yvette Hernandez Alonso", "Opentrends", 1049459419,
              "Correcto", "No hay comentarios");
      FollowUpModel fup2 = new FollowUpModel("Cristian Garcia Aran", "Seat", 1079596395,
              "Correcto", "No hay comentarios");
      

    return Arrays.asList(fup1, fup2);
  }

  public static Category makeGreenCategory() {
    return new Category("Up to Day", makeGreenFollows(), R.drawable.ic_thumb_up_black_24dp);
  }

  public static List<FollowUpModel> makeGreenFollows() {
      FollowUpModel fup1 = new FollowUpModel("Ruben Pla Ferrero", "Banco Sabadell", 1049459419,
              "Correcto", "No hay comentarios");
      FollowUpModel fup2 = new FollowUpModel("Jorge Aviario Sole", "La Caixa", 1079596395,
              "Correcto", "No hay comentarios");
      FollowUpModel fup3 = new FollowUpModel("Ignacio Ferror Planalta", "Caixa", 1049945439,
              "Correcto", "No hay comentarios");

    return Arrays.asList(fup1, fup2, fup3);
  }
}

