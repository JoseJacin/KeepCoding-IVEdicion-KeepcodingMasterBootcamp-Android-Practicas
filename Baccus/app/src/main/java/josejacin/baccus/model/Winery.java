package josejacin.baccus.model;

import java.util.Arrays;
import java.util.List;

import josejacin.baccus.R;

public class Winery {
    // Se utiliza un singleton
    private static Winery sInstance = null;
    // Se crea la lista de vinos
    private List<Wine> mWines = null;

    public static Winery getInstance() {
        // Valida si sInstance es null para comprobar si ya existe una instancia de sInstance
        if (sInstance == null) {
            // No hay instancia creada de sInstance, por lo que se procede a crear una
            sInstance = new Winery();
        }

        // Ya hay una instancia creada de sInstance, por lo que se procede a devolver dicha instancia
        return sInstance;
    }

    // Constructor
    public Winery() {
        Wine bembibre = new Wine(
                "Bembibre",
                "Tinto",
                R.drawable.bembibre,
                "Dominio de Tares",
                "http://www.dominiodetares.com/portfolio/bembibre/",
                "Este vino muestra toda la complejidad y la elegancia de la variedad Mencía. En fase visual luce un color rojo picota muy cubierto con tonalidades violáceas en el menisco. En nariz aparecen recuerdos frutales muy intensos de frutas rojas (frambuesa, cereza) y una potente ciruela negra, así como tonos florales de la gama de las rosas y violetas, vegetales muy elegantes y complementarios, hojarasca verde, tabaco y maderas aromáticas (sándalo) que le brindan un toque ciertamente perfumado.",
                "El Bierzo",
                5);
        bembibre.addGrape("Mencía");

        Wine vegaval = new Wine(
                "Vegaval",
                "Tinto",
                R.drawable.vegaval,
                "Miguel de Calatayud",
                "http://www.vegaval.com/es",
                "Blah blah blah",
                "Valdepeñas",
                4);
        vegaval.addGrape("tempranillo");

        Wine zarate = new Wine(
                "Zárate",
                "Blanco",
                R.drawable.zarate,
                "Miguel Calatayud",
                "http://bodegas-zarate.com/productos/vinos/albarino-zarate/",
                "El albariño Zarate es un vino blanco monovarietal que pertenece a la Denominación de Origen Rías Baixas. Considerado por la crítica especializada como uno de los grandes vinos blancos del mundo, el albariño ya es todo un mito.",
                "Rías bajas",
                4);
        zarate.addGrape("Albariño");

        Wine champagne = new Wine(
                "Champagne",
                "Otros",
                R.drawable.champagne,
                "Champagne Taittinger",
                "http://bodegas-zarate.com/productos/vinos/albarino-zarate/",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed ac nunc purus. Curabitur eu velit mauris. Curabitur magna nisi, ullamcorper ac bibendum ac, laoreet et justo. Praesent vitae tortor quis diam luctus condimentum. Suspendisse potenti. In magna elit, interdum sit amet facilisis dictum, bibendum nec libero. Maecenas pellentesque posuere vehicula. Vivamus eget nisl urna, quis egestas sem. Vivamus at venenatis quam. Sed eu nulla a orci fringilla pulvinar ut eu diam. Morbi nibh nibh, bibendum at laoreet egestas, scelerisque et nisi. Donec ligula quam, semper nec bibendum in, semper eget dolor. In hac habitasse platea dictumst. Maecenas adipiscing semper rutrum. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae;",
                "Comtes de Champagne",
                5);
        champagne.addGrape("Chardonnay");

        // Se añaden los vinos
        mWines = Arrays.asList(new Wine[]{bembibre, vegaval, zarate, champagne});
    }

    // Método que permite obtener un vino del array de vinos a través de un índice
    public Wine getWine(int index) {
        return mWines.get(index);
    }

    // Método que retorna el número de vinos que se encuentran en el array de vinos
    public int getWineCount() {
        return mWines.size();
    }
}
