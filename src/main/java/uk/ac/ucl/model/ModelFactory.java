package uk.ac.ucl.model;

// Provides other classes access to the Model object via the static method ModelFactory.getModel()
// There should only be one single Model object throughout the use of the program

public class ModelFactory {
    private static Model model;

    public static Model getModel() {
        if (model == null) {
            model = new Model("data/notesData.json", "Root");
        }
        return model;
    }
}
