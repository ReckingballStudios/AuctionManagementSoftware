import front.Frame;
import javax.swing.*;

public class ResourceAuction {
    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run(){
                new Frame();
            }
        });
    }
}
