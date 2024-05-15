package UISwing.recursos;

import javax.swing.*;
import java.awt.*;

public class CustomPanelOpaco extends JPanel {
    public CustomPanelOpaco() {
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setComposite(AlphaComposite.SrcOver.derive(0.5f)); 
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
        g2.dispose();
        
    }
}
