/**
 * Name: Feranmi Oluwasikun
 * NSID: CSB904
 * Student Number: 11315767
 * Class: CMPT 270 03
 */


public class PetStoreSystema5q6 {

    public static void main(String[] args) {
        Command[] commands = new Command[9];
        String[] options = new String[9];

        IOAccess.getInstance().outputString("-------Initializing-------");


        while (true) {
            try {
                String storeName = IOAccess.getInstance().readString("Enter the name of the store: ");
                int firstKennelNumber = IOAccess.getInstance().readInt("Enter the integer label for the first bed: ");
                int lastKennelNumber = IOAccess.getInstance().readInt("Enter the integer label for the last bed: ");

                petStoreAccess.initialize(storeName, firstKennelNumber, lastKennelNumber);
                break;
            } catch (RuntimeException e) {
                IOAccess.getInstance().outputString(e.getMessage());
            }
        }


        commands[0] = new SystemState();
        options[0] = "0:Quit";

        commands[1] = new AddAnimal();
        options[1] =  "1:Add a new animal member to system";

        commands[2] = new AddStaff();
        options[2] = "2:Add a new staff member to system";

        commands[3] = new AssignStaffToAnimal();
        options[3] = "3:Assign a staff member to an animal";

        commands[4] = new ShowEmptyKennels();
        options[4] = "4:Display empty beds";

        commands[5] = new AssignKennel();
        options[5] = "5:Assign an animal to a kennel";

        commands[6] = new ReleaseAnimal();
        options[6] = "6:Release an animal";

        commands[7] = new DropAssociation();
        options[7] = "7:Remove staff-animal association";

        commands[8] = new SystemState();
        options[8] = "8:Show Current system state";

        int systemCmd = -1;
        while (systemCmd != 0){
            try {
                systemCmd = IOAccess.getInstance().readChoice(options);
                commands[systemCmd].execute();
            } catch (RuntimeException e){
                // No matter what other exception is thrown, this catches it
                // Dealing with it means discarding whatever went wrong
                // and starting the loop over.  Easy for the programmer,
                // tedious for the user
                IOAccess.getInstance().outputString(e.getMessage());
            }
        }

    }

}
