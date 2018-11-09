import net.jini.space.*;
import java.awt.*;
import javax.swing.*;

public class Putter extends JFrame {
    private static int ONE_MINUTE = 1000 * 60;  //one minute = 1000 miliseconds, multiplied by 60

    private JavaSpace space;

    private JTextField inString, inCount;


    public Putter() {
        space = SpaceUtils.getSpace();
        if (space == null){
            System.err.println("Failed to find the javaspace");
            System.exit(1);
        }

        initComponents ();
        this.setSize(300,150);
    }

    private void initComponents () {
        setTitle ("JavaSpaces Putter");
        addWindowListener (new java.awt.event.WindowAdapter () {
            public void windowClosing (java.awt.event.WindowEvent evt) {
                exitForm (evt);
            }
        }   );

        Container cp = getContentPane();
        cp.setLayout (new BorderLayout ());

        JPanel jPanel1 = new JPanel();
        jPanel1.setLayout (new FlowLayout (FlowLayout.LEFT));

        JLabel textLabel = new JLabel();
        textLabel.setText ("Text to insert: ");
        jPanel1.add (textLabel);

        inString = new JTextField (12);
        inString.setText ("");

        jPanel1.add (inString);


        JPanel jPanel2 = new JPanel();
        jPanel2.setLayout (new FlowLayout (FlowLayout.LEFT));

        JLabel countLabel = new JLabel();
        countLabel.setText("Number to Insert (1 to 5): ");
        jPanel2.add (countLabel);

        inCount = new JTextField (3);
        inCount.setText("");
        jPanel2.add(inCount);


        JPanel jPanel3 = new JPanel();
        jPanel3.setLayout (new FlowLayout ());

        JButton putButton = new JButton();
        putButton.setText ("Insert One");
        putButton.addActionListener (new java.awt.event.ActionListener () {
            public void actionPerformed (java.awt.event.ActionEvent evt) {
                writeSobj (evt);
            }
        }  );

        jPanel3.add (putButton);

        JButton putManyButton = new JButton();
        putManyButton.setText ("Insert  Lots");
        putManyButton.addActionListener (new java.awt.event.ActionListener () {
            public void actionPerformed (java.awt.event.ActionEvent evt) {
                writeMany (evt);
            }
        }  );

        jPanel3.add (putManyButton);


        cp.add (jPanel1, "North");
        cp.add (jPanel2, "Center");
        cp.add (jPanel3, "South");
    }


    private void writeMany (java.awt.event.ActionEvent evt) {
        try {
            Sobj str = new Sobj(inString.getText());
            int count = new Integer(inCount.getText());
            if (count > 20)
                JOptionPane.showMessageDialog(this,
                        "Grow Up!  Count must be 1 to 5",
                        "Error",
                        JOptionPane.ERROR_MESSAGE,
                        null);
            else if (count > 5)
                JOptionPane.showMessageDialog(this,
                        "Count must be 1 to 5",
                        "Error",
                        JOptionPane.ERROR_MESSAGE,
                        null);
            else {
                for (int i = 0; i < count; i++) {
                    space.write(str, null, ONE_MINUTE);
                }
            }

        }catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this,
                    "Count must be a number",
                    "Error",
                    JOptionPane.ERROR_MESSAGE,
                    null);

        } catch ( Exception e) {
            e.printStackTrace();
        }
    }

    private void writeSobj (java.awt.event.ActionEvent evt) {
        try {
            Sobj str = new Sobj(inString.getText());
            space.write( str, null, ONE_MINUTE);
        } catch ( Exception e) {
            e.printStackTrace();
        }
    }

    private void exitForm(java.awt.event.WindowEvent evt) {
        System.exit (0);
    }


    public static void main(String[] args) {
        new Putter().setVisible(true);
    }
}
