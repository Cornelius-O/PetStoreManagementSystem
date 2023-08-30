/*
  CMPT 270 Course Material
  Copyright (c) 2022
  All rights reserved.

  This document contains resources for homework assigned to students of
  of CMPT 270 and shall not be distributed without permission.  Posting this
  file to a public or private website, or providing this file to any person
  not registered in CMPT 270 constitutes Academic Misconduct according to
  to the University of Saskatchewan Policy on Academic Misconduct.

  Synopsis: Starter file for Assignment 5
 */

/**
 * Name: Feranmi Oluwasikun
 * NSID: CSB904
 * Student Number: 11315767
 * Class: CMPT 270 03
 */

import java.util.*;


/**
 * A simple Pet Store management system with only one store.  Animals and staff can be created,
 * and animals assigned to a staff/managers and/or assigned to a kennel within the store.
 */
public class PetStoreSystema5q3 {
    /**
     * One Scanner for all methods
     */
    private static Scanner consoleIn = new Scanner(System.in);

    /**
     * The keyed dictionary of all animals in the system
     */
    private Map<String, Animal> animals;

    /**
     * The keyed dictionary of all staff in the system
     */
    private Map<String, StaffMember> staff;

    /**
     * The pet store that will be managed
     */
    private PetStore petStore;

    private static DialogIO dialogIO = new DialogIO();

    /**
     * Initialize an instance of the store management system - relies on user-input
     */
    public PetStoreSystema5q3() {

        animals = new TreeMap<String, Animal>();
        staff = new TreeMap<String, StaffMember>();

        // get the required pet store info
        //Scanner consoleIn = new Scanner(System.in);
        dialogIO.outputString("-------Getting Pet Store information-------");
        String name = dialogIO.readString("Enter the name of the store: ");

        //String name = consoleIn.nextLine();
        dialogIO.outputString("Entered: " + name);
        int firstKennelNumber = dialogIO.readInt("Enter the integer label for the first bed: ");


        int lastKennelNumber = dialogIO.readInt("Enter the integer label for the last bed: ");

        petStore = new PetStore(name, firstKennelNumber, lastKennelNumber);
    }

    /**
     * Collects information on a new animal, then adds the animal to the dictionary of all animals
     */
    public void addAnimal() {
        dialogIO.outputString("-------Adding Animal to Residence-------");
       String name = dialogIO.readString("Enter the name of the animal: ");
       String animalID = dialogIO.readString("Enter the animal ID of the animal: ");



       String animalType =  dialogIO.readString("Enter the type of animal: ");

        dialogIO.outputString("Entered: " + animalID);

        if (animals.containsKey(animalID)) {
            throw new IllegalStateException("Animal with ID " + animalID + " already exists");
        }

        Animal anim = new Animal(name, animalID, animalType);
        Animal result = animals.put(animalID, anim);

        // checking to make sure insertion into the Map worked
        if (result != null) {
            animals.put(animalID, result);  // put the original animal back in
            throw new IllegalStateException("Animal was already in dictionary, even though containsKey failed. Animal " + name + " not entered.");
        }
    }

    /**
     * Read info for a new staff member, and add them to dictionary of all staff
     */
    public void addStaff() {
        dialogIO.outputString("-------Adding Staff to Store-------");
       String fName =  dialogIO.readString("Enter the staff member's first name: ");

       String lName = dialogIO.readString("Enter the staff member's last name: ");

       String staffSIN = dialogIO.readString("Enter the staff member's SIN: ");

       String staffID = dialogIO.readString("Enter the staff member's employee ID: ");

        if (staff.containsKey(staffID)) {
            throw new IllegalStateException("Staff not added. Already have staff with employee number " + staffID);
        }

       String response = dialogIO.readString("Is the staff member a manager? (yes or no): ");

        StaffMember newStaff;
        if (response.charAt(0) == 'y' || response.charAt(0) == 'Y') {
            newStaff = new Manager(fName, lName, staffSIN, staffID);
        } else {
            newStaff = new StaffMember(fName, lName, staffSIN, staffID);
        }

        // check to make sure the staff employee ID doesn't already exist
        StaffMember result = staff.put(staffID, newStaff);
        if (result != null) {
            // if put() returns a reference, then a manager was already stored with the same EN,
            // so put it back, and signal an error.
            staff.put(staffID, result); // put the original manager back
            throw new IllegalStateException("Staff was already in dictionary, even though containsKey failed. Staff " + fName + " " + lName + " not entered.");
        }
    }

    /**
     * Assign a staff member to an animal, and the animal to the staff member
     */
    public void assignStaffToAnimal() {
        dialogIO.outputString("-------Assigning staff to an animal-------");
       String animalID = dialogIO.readString("Enter the animalID of the animal: ");


        Animal anim = animals.get(animalID);
        if (anim == null) {
            throw new NoSuchElementException("There is no such animal with ID  " + animalID);
        }

      String staffID =  dialogIO.readString("Enter the employee number of the staff member: ");
        StaffMember staffMember = this.staff.get(staffID);
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
        dialogIO.outputString("Empty Kennels: ");
        LinkedList<Integer> kennel = petStore.availableKennels();

        if (kennel.size() == 0){
            dialogIO.outputString("No available kennels");
        }else{
            for (int i = 0; i < kennel.size(); i++){
                dialogIO.outputString(i + " ");
            }
        }

    }

    /**
     * Assign an animal to a kennel
     */
    public void assignKennel() {
        dialogIO.outputString("-------Assigning a kennel to an animal-------");
        String animalID = dialogIO.readString("Enter the animalID of the animal: ");


        Animal anim = animals.get(animalID);
        if (anim == null) {
            throw new NoSuchElementException("There is such animal with ID " + animalID);
        }

        if (anim.getAssignedKennel() != -1) {
            throw new IllegalStateException("Animal " + animalID + " is already assigned a kennel");
        }

        int kennelNumber = dialogIO.readInt("Enter the kennel label for the animal: ");



        if (kennelNumber < petStore.getMinKennelLabel() || kennelNumber > petStore.getMaxKennelLabel()) {
            throw new IllegalArgumentException(kennelNumber + " is not a valid value. Must be between " + petStore.getMinKennelLabel() + " and " + petStore.getMaxKennelLabel());
        }

        if (petStore.isOccupied(kennelNumber)) {
            throw new IllegalStateException("Kennel is already assigned to an animal");
        } else {
            anim.setAssignedKennel(kennelNumber);
            petStore.assignAnimalToKennel(anim, kennelNumber);
        }
    }


    /**
     * Release an animal from the store
     */
    public void releaseAnimal() {
        // TODO: implement
        dialogIO.outputString("-------Releasing an animal in a kennel-------");
      String animalID =  dialogIO.readString("Enter the animalID of the animal: ");


        Animal anim = animals.get(animalID);
        if (anim == null) {
            throw new NoSuchElementException("There is such animal with ID " + animalID);
        }

        int kennelNumber = dialogIO.readInt("Enter the kennel label for the animal: ");


        if (kennelNumber < petStore.getMinKennelLabel() || kennelNumber > petStore.getMaxKennelLabel()) {
            throw new IllegalArgumentException(kennelNumber + " is not a valid value. Must be between " + petStore.getMinKennelLabel() + " and " + petStore.getMaxKennelLabel());

        }
        if (!(petStore.isOccupied(kennelNumber))) {
            throw new IllegalStateException("Kennel is already free");
        } else {
            petStore.freeKennel(kennelNumber);
        }

    }


    /**
     * Remove the animal-staff association
     */
    public void dropAssociation() {
        dialogIO.outputString("-------Removing staff-animal association-------");
        String animalID = dialogIO.readString("Enter the animalID of the animal: ");

        // check if animal exists in system
        Animal anim = animals.get(animalID);
        if (anim == null) {
            throw new NoSuchElementException("No such animal with ID " + animalID);
        }

        dialogIO.outputString("Enter the employee number of the staff member: ");
        String staffID = consoleIn.next();

        // check if the staff exists in the system
        StaffMember staffMember = staff.get(staffID);
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
        dialogIO.outputString(this.toString());
    }


    /**
     * Return a string representation of the PetStoreSystem
     *
     * @return a string representation of the PetStoreSystem
     */
    public String toString() {
        String result = "\nThe students in the system are:";
        Collection<Animal> animalsCollection = animals.values();
        for (Animal anim : animalsCollection) {
            result = result + anim;
        }

        result = result + "\n-------\nThe staff members in the system are:";
        Collection<StaffMember> staffCollection = staff.values();
        for (StaffMember stf : staffCollection) {
            result = result + stf;
        }
        result = result + "\n-------\nThe store is " + petStore;
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

        dialogIO.outputString("-------Initializing-------");

        while (true) {
            // keep collection input until entered corectly
            try {
                sys = new PetStoreSystema5q1();
                break;
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
        }

       dialogIO.outputString("-------System running-------");
        while (systemCmd != 1) {
            try {
                String choice = dialogIO.readString("Options:"
                        + "\n\t1: Quit"
                        + "\n\t2: Add a new animal to system"
                        + "\n\t3: Add a new staff member to system"
                        + "\n\t4: Assign a staff member to an animal"
                        + "\n\t5: Display the empty kennels in the store"
                        + "\n\t6: Assign an animal to a kennel"
                        + "\n\t7: Release an animal"
                        + "\n\t8: Remove staff-animal association"
                        + "\n\t9: Show current system state"
                        + "\nEnter your selection {1-9}: ");

               if(choice.charAt(0)=='#') continue;
               systemCmd = Integer.parseInt(choice);
                if (systemCmd == 1) {
                    sys.systemState();
                } else if (systemCmd == 2) {
                    sys.addAnimal();
                } else if (systemCmd == 3) {
                    sys.addStaff();
                } else if (systemCmd == 4) {
                    sys.assignStaffToAnimal();
                } else if (systemCmd == 5) {
                    sys.showEmptyKennels();
                } else if (systemCmd == 6) {
                    sys.assignKennel();
                } else if (systemCmd == 7) {
                    sys.releaseAnimal();
                } else if (systemCmd == 8) {
                    sys.dropAssociation();
                } else if (systemCmd == 9) {
                    sys.systemState();
                } else {
                    dialogIO.outputString("Invalid option, try again.");
                }
            } catch (InputMismatchException e) {
                // thrown by the Scanner if the user types something unexpected
                dialogIO.readString("Use an integer to choose a menu item!");
                // get rid of the unexpected something

            } catch (RuntimeException e) {
                // No matter what other exception is thrown, this catches it
                // Dealing with it means discarding whatever went wrong
                // and starting the loop over.  Easy for the programmer,
                // tedious for the user.
                dialogIO.readString(e.getMessage());
            }
        }

        consoleIn.close();
        dialogIO.outputString("-------System terminated-------");
    }

}


