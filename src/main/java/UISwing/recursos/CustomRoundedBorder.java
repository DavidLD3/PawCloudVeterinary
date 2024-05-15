package UISwing.recursos;

import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class CustomRoundedBorder extends AbstractBorder {
    private final Color borderColor;
    private final int borderThickness;
    private final int cornerRadius;

    public CustomRoundedBorder(Color color, int thickness, int radius) {
        this.borderColor = color;
        this.borderThickness = thickness;
        this.cornerRadius = radius;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(borderColor);
        g2d.setStroke(new BasicStroke(borderThickness));
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Modificar aquí para ajustar el dibujo del borde
        int offset = borderThickness;
        int inset = 1; 
        g2d.drawRoundRect(x + offset / 2, y + offset / 2, width - offset - inset, height - offset - inset, cornerRadius, cornerRadius);
        g2d.dispose();
    }

    @Override
    public Insets getBorderInsets(Component c, Insets insets) {
        insets.set(borderThickness, borderThickness, borderThickness, borderThickness);
        return insets;
    }

    @Override
    public boolean isBorderOpaque() {
        return false;
    }
}
