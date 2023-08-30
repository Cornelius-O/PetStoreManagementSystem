

import javax.swing.JOptionPane;



public class DialogIO extends AbstractDialogIO {
    public static void main (String[] args){}
    @Override
    public String readString(String prompt) {
        String input = JOptionPane.showInputDialog(prompt);
        return input;
    }

    @Override
    public int readInt(String prompt) {
        String input = JOptionPane.showInputDialog(prompt);
        int asn = 0;
        try {
            asn = Integer.parseInt(input);
        } catch(Exception e){
            System.out.println("Sorry, please enter an integer");
        }

        return asn;
    }

    @Override
    public void outputString(String outString) {
        JOptionPane.showMessageDialog(null,outString);

    }
}
