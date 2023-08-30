

import java.util.TreeMap;

/**
 * Singleton design from staff Map
 */
public class staffMapAccess {
    private static TreeMap<String, StaffMember> staff = null;

    private staffMapAccess(){}

    /**
     *
     * @return the staff map
     */
    public static TreeMap<String, StaffMember> getInstance() {
        if (staff == null){
            staff = new TreeMap<String, StaffMember>();
        }
        return staff;
    }
}

