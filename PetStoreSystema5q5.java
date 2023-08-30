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

import java.util.TreeMap;
import java.util.Collection;
import java.util.*;
import java.util.NoSuchElementException;


/**
 * A simple Pet Store management system with only one store.  Animals and staff can be created,
 * and animals assigned to a staff/managers and/or assigned to a kennel within the store.
 */
public class PetStoreSystema5q5{
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



    public PetStoreSystema5q5() {


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
        Command cmd = new AddAnimal();
        cmd.execute();
    }

    /**
     * Read info for a new staff member, and add them to dictionary of all staff
     */
    public void addStaff() {
        Command cmd = new AddStaff();
        cmd.execute();
    }

    /**
     * Assign a staff member to an animal, and the animal to the staff member
     */
    public void assignStaffToAnimal() {
        Command cmd = new AssignStaffToAnimal();
        cmd.execute();
    }

    /**
     * Display all empty kennels in the store
     */
    public void showEmptyKennels() {
        Command cmd = new ShowEmptyKennels();
        cmd.execute();

    }

    /**
     * Assign an animal to a kennel
     */
    public void assignKennel() {
        Command cmd = new AssignKennel();
        cmd.execute();
    }


    /**
     * Release an animal from the store
     */
    public void releaseAnimal() {
        Command cmd = new AddStaff();
        cmd.execute();

    }


    /**
     * Remove the animal-staff association
     */
    public void dropAssociation() {
        Command cmd = new DropAssociation();
        cmd.execute();
    }

    /**
     * Display the current state of the system
     */
    public void systemState() {
        Command cmd = new SystemState();
        cmd.execute();
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

               if(systemCmd == 1) sys.systemState();
                else if (systemCmd == 2) {
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


