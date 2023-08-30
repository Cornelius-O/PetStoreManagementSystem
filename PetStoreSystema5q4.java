import java.util.TreeMap;
import java.util.Collection;
import java.util.*;
import java.util.NoSuchElementException;


/**
 * A simple Pet Store management system with only one store.  Animals and staff can be created,
 * and animals assigned to a staff/managers and/or assigned to a kennel within the store.
 */
public class PetStoreSystema5q4 {
    /**
     * One Scanner for all methods
     */
   private static InputOutputInterface io = IOAccess.getInstance();


    /**
     * The main menu
     */
    private static String[] menu = {
            "1:quit",
            "2:Add a new animal to system",
            "3:Add a new staff member to system",
            "4:Assign a staff member to an animal",
            "5:Display the empty kennels in the store",
            "6:Assign an animal to a kennel",
            "7:Release an animal",
            "8:Remove staff-animal association",
            "9:Show current system state"};



    public PetStoreSystema5q4() {


        // get the required pet store info
        io.outputString("-------Getting Pet Store information-------");
        String name = io.readString("Enter the name of the store: ");

        //String name = consoleIn.nextLine();
        int firstKennelNumber = io.readInt("Enter the integer label for the first bed: ");
        int lastKennelNumber = io.readInt("Enter the integer label for the last bed: ");

         petStoreAccess.initialize(name, firstKennelNumber, lastKennelNumber);
    }

    /**
     * Collects information on a new animal, then adds the animal to the dictionary of all animals
     */
    public void addAnimal() {
        io.outputString("-------Adding Animal to Residence-------");
       String name = io.readString("Enter the name of the animal: ");
       String animalID = io.readString("Enter the animal ID of the animal: ");
       String animalType =  io.readString("Enter the type of animal: ");

        if (AnimalMapAccess.getInstance().containsKey(animalID)) {
            throw new IllegalStateException("Animal with ID " + animalID + " already exists");
        }

        Animal anim = new Animal(name, animalID, animalType);
        Animal result = AnimalMapAccess.getInstance().put(animalID,anim);

        // checking to make sure insertion into the Map worked
        if (result != null) {
            AnimalMapAccess.getInstance().put(animalID,anim);  // put the original animal back in
            throw new IllegalStateException("Animal was already in dictionary, even though containsKey failed. Animal " + name + " not entered.");
        }
    }

    /**
     * Read info for a new staff member, and add them to dictionary of all staff
     */
    public void addStaff() {
        io.outputString("-------Adding Staff to Store-------");
       String fName =  io.readString("Enter the staff member's first name: ");
       String lName = io.readString("Enter the staff member's last name: ");
       String staffSIN = io.readString("Enter the staff member's SIN: ");
       String staffID = io.readString("Enter the staff member's employee ID: ");

        if (staffMapAccess.getInstance().containsKey(staffID)) {
            throw new IllegalStateException("Staff not added. Already have staff with employee number " + staffID);
        }

       String response = io.readString("Is the staff member a manager? (yes or no): ");


        StaffMember newStaff = null;
        if (response.charAt(0) == 'y' || response.charAt(0) == 'Y') {
            newStaff = new Manager(fName, lName, staffSIN, staffID);
        } else {
            newStaff = new StaffMember(fName, lName, staffSIN, staffID);
        }

        // check to make sure the staff employee ID doesn't already exist
        StaffMember result = staffMapAccess.getInstance().put(fName,newStaff);
        if (result != null) {
            // if put() returns a reference, then a manager was already stored with the same EN,
            // so put it back, and signal an error.
            staffMapAccess.getInstance().put(fName,newStaff); // put the original manager back
            throw new IllegalStateException("Staff was already in dictionary, even though containsKey failed. Staff " + fName + " " + lName + " not entered.");
        }
    }

    /**
     * Assign a staff member to an animal, and the animal to the staff member
     */
    public void assignStaffToAnimal() {
        io.outputString("-------Assigning staff to an animal-------");
       String animalID = io.readString("Enter the animalID of the animal: ");


        Animal anim = AnimalMapAccess.getInstance().get(animalID);
        if (anim == null) {
            throw new NoSuchElementException("There is no such animal with ID  " + animalID);
        }

      String staffID =  io.readString("Enter the employee number of the staff member: ");
        StaffMember staffMember = staffMapAccess.getInstance().get(staffID);
        if (staffMember == null) {
            throw new NoSuchElementException("There is no staff with employee ID " + staffID);
        } else {
            anim.addStaff(staffMember);
            staffMember.assignAnimal(anim);
        }
    }

    /**
     * Display all empty kennels in the store
     */
    public void showEmptyKennels() {
        io.outputString("Empty Kennels: ");
        LinkedList<Integer> kennel = petStoreAccess.getStoreInstance().availableKennels();

        if (kennel.size() == 0){
            io.outputString("No available kennels");
        }else{
            for (int i = 0; i < kennel.size(); i++){
                io.outputString(  "Available kennels:  " + petStoreAccess.getStoreInstance().availableKennels());
            }
        }

    }

    /**
     * Assign an animal to a kennel
     */
    public void assignKennel() {
        io.outputString("-------Assigning a kennel to an animal-------");
        String animalID = io.readString("Enter the animalID of the animal: ");


        Animal anim = AnimalMapAccess.getInstance().get(animalID);
        if (anim == null) {
            throw new NoSuchElementException("There is such animal with ID " + animalID);
        }

        if (anim.getAssignedKennel() != -1) {
            throw new IllegalStateException("Animal " + animalID + " is already assigned a kennel");
        }

        int kennelNumber = io.readInt("Enter the kennel label for the animal: ");



        if (kennelNumber < petStoreAccess.getStoreInstance().getMinKennelLabel() || kennelNumber > petStoreAccess.getStoreInstance().getMaxKennelLabel()) {
            throw new IllegalArgumentException(kennelNumber + " is not a valid value. Must be between " + petStoreAccess.getStoreInstance().getMinKennelLabel() + " and " + petStoreAccess.getStoreInstance().getMaxKennelLabel());
        }

        if (petStoreAccess.getStoreInstance().isOccupied(kennelNumber)) {
            throw new IllegalStateException("Kennel is already assigned to an animal");
        } else {
            anim.setAssignedKennel(kennelNumber);
            petStoreAccess.getStoreInstance().assignAnimalToKennel(anim, kennelNumber);
        }
    }


    /**
     * Release an animal from the store
     */
    public void releaseAnimal() {
        // TODO: implement
        io.outputString("-------Releasing an animal in a kennel-------");
      String animalID =  io.readString("Enter the animalID of the animal: ");


        Animal anim = AnimalMapAccess.getInstance().get(animalID);
        if (anim == null) {
            throw new NoSuchElementException("There is such animal with ID " + animalID);
        }

        int kennelNumber = anim.getAssignedKennel();

        if (kennelNumber == -1){
            throw new NoSuchElementException("No kennel was assigned to animal" + animalID);

        }
        if (petStoreAccess.getStoreInstance().getAnimal(kennelNumber) != anim){
            throw new IllegalStateException("Animal " + animalID + " recorded in kennel " + kennelNumber + ", but animal has animal " + petStoreAccess.getStoreInstance().getAnimal(kennelNumber).getAnimalID() + " there.");
        }

        petStoreAccess.getStoreInstance().freeKennel(kennelNumber);
        anim.setAssignedKennel(-1);

    }


    /**
     * Remove the animal-staff association
     */
    public void dropAssociation() {
        io.outputString("-------Removing staff-animal association-------");
        String animalID = io.readString("Enter the animalID of the animal: ");

        // check if animal exists in system
        Animal anim = AnimalMapAccess.getInstance().get(animalID);
        if (anim == null) {
            throw new NoSuchElementException("No such animal with ID " + animalID);
        }

        String staffID = io.readString("Enter the employee number of the staff member: ");


        // check if the staff exists in the system
        StaffMember staffMember = staffMapAccess.getInstance().get(staffID);
        if (staffMember == null) {
            throw new NoSuchElementException("There is no staff member with ID " + staffID);
        }

        // make sure returned animal's ID matches input animal ID
        String returnedAnimalsID = anim.getAnimalID();
        if (!animalID.equals(returnedAnimalsID)) {
            throw new IllegalStateException("Animal IDs are not equal: " + animalID + " " + returnedAnimalsID);
        }

        // check if the staff member is assigned to the animal
        if (!staffMember.hasAnimal(returnedAnimalsID)) {
            throw new IllegalStateException("Staff Member is not associated with animal:" + returnedAnimalsID);
        }

        // check if the animal is assigned to the staff member
        if (!anim.hasStaff(staffID)) {
            throw new IllegalStateException("Animal is not associated with staff member:" + staffID);
        }

        // Animal and staff member are both properly associated, so remove the association
        anim.removeStaff(staffID);
        staffMember.removeAnimal(animalID);
    }

    /**
     * Display the current state of the system
     */
    public void systemState() {
        io.outputString(this.toString());
    }


    /**
     * Return a string representation of the PetStoreSystem
     *
     * @return a string representation of the PetStoreSystem
     */
    public String toString() {
        String result = "\nThe Animals in the system are:";
        Collection<Animal> animalsCollection = AnimalMapAccess.getInstance().values();
        for (Animal anim : animalsCollection) {
            result = result + anim;
        }

        result = result + "\n-------\nThe staff members in the system are:";
        Collection<StaffMember> staffCollection = staffMapAccess.getInstance().values();
        for (StaffMember stf : staffCollection) {
            result = result + stf;
        }
        result = result + "\n-------\nThe store is " + petStoreAccess.getStoreInstance();
        return result;
    }

    /**
     * Run the residence management system.
     *
     * @param args not used
     */
    public static void main(String[] args) {
        int systemCmd = -1;
        PetStoreSystema5q1 sys;

        io.outputString("-------Initializing-------");

        while (true) {
            // keep collection input until entered corectly
            try {
                sys = new PetStoreSystema5q1();
                break;
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
        }

       io.outputString("-------System running-------");
        while (systemCmd != 0) {
            try {
                systemCmd = io.readChoice(menu);

               if(systemCmd == 0) sys.systemState();
                else if (systemCmd == 1) {
                    sys.addAnimal();
                } else if (systemCmd == 2) {
                    sys.addStaff();
                } else if (systemCmd == 3) {
                    sys.assignStaffToAnimal();
                } else if (systemCmd == 4) {
                    sys.showEmptyKennels();
                } else if (systemCmd == 5) {
                    sys.assignKennel();
                } else if (systemCmd == 6) {
                    sys.releaseAnimal();
                } else if (systemCmd == 7) {
                    sys.dropAssociation();
                } else if (systemCmd == 8) {
                    sys.systemState();
                } else {
                    io.outputString("Invalid option, try again.");
                }
            } catch (InputMismatchException e) {
                // thrown by the Scanner if the user types something unexpected
                io.readString("Use an integer to choose a menu item!");
                // get rid of the unexpected something

            } catch (RuntimeException e) {
                // No matter what other exception is thrown, this catches it
                // Dealing with it means discarding whatever went wrong
                // and starting the loop over.  Easy for the programmer,
                // tedious for the user.
                io.outputString(e.getMessage());
            }
        }
        io.outputString("-------System terminated-------");
    }

}


