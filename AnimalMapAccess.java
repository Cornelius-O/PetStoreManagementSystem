/**
 * Name: Feranmi Oluwasikun
 * NSID: CSB904
 * Student Number: 11315767
 * Class: CMPT 270 03
 */

import java.util.TreeMap;

    /**
     * Singleton design for Animal map
     */
public class AnimalMapAccess {
    private static TreeMap<String, Animal> animals = null;
    private AnimalMapAccess(){}

        /**
     * Returns animal map
     */
    public static TreeMap<String, Animal> getInstance() {
        if (animals == null){
            animals = new TreeMap<String, Animal>();
        }
        return animals;
    }
}

