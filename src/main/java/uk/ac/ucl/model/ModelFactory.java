package uk.ac.ucl.model;

public class ModelFactory {
    // Provides access to the Model object, of which there must only be one throughout the program
    private static Model model;

    public static Model getModel() {
        if (model == null) {
            model = new Model("data/notesData.json", "Root");
        }
        return model;
    }
}
