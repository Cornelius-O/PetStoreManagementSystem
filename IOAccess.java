
/**
 * Singleton design for IO Interface
 */
public class IOAccess {
    private static InputOutputInterface io;

    private IOAccess(){}

    public static InputOutputInterface getInstance(){
        if (io == null){
            InputOutputInterface dialog = new DialogIO();
            System.out.println("Which IO do you want to use? ");
            String[] option = {"ConsoleIO ","Dialog"};
            int choice = dialog.readChoice(option);
            if (choice == 0){
                io = new ConsoleIO();
            }else{
                io = dialog;
            }
        }
        return io;
    }
}
