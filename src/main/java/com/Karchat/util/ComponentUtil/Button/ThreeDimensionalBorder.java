package com.Karchat.util.ComponentUtil.Button;

import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.RoundRectangle2D;

/**
 * 三位立体按钮，通过Border完成，去掉背景需要用.setContentAreaFill(false)
 */
public class ThreeDimensionalBorder extends AbstractBorder {

    Color color;

    int thickness = 8;

    int radius = 8;

    Insets insets = null;

    BasicStroke stroke = null;

    int strokePad;

    RenderingHints hints;

    int shadowPad = 3;

    public ThreeDimensionalBorder(Color color) {

        this(color, 128, 8);

    }

    /**
     * 创建三维按钮,注意让其背景
     * @param color 默认BLACK
     * @param transparency 默认200
     * @param shadowWidth 默认5
     */
    ThreeDimensionalBorder(Color color, int transparency, int shadowWidth) {

        this.color = color;

        shadowPad = shadowWidth;

        stroke = new BasicStroke(thickness);

        strokePad = thickness / 2;

        hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING,

                RenderingHints.VALUE_ANTIALIAS_ON);

        int pad = radius + strokePad;

        int bottomPad = pad + strokePad + shadowPad;

        int rightPad = pad + strokePad + shadowPad;

        insets = new Insets(pad, pad, bottomPad + shadowPad, rightPad);

    }

    @Override

    public Insets getBorderInsets(Component c) {

        return insets;

    }

    @Override

    public Insets getBorderInsets(Component c, Insets insets) {

        return getBorderInsets(c);

    }

    @Override

    public void paintBorder(Component c, Graphics g, int x, int y, int width,

                            int height) {

        Graphics2D g2 = (Graphics2D) g;

        int bottomLineY = height - thickness - shadowPad;

        RoundRectangle2D.Double bubble = new RoundRectangle2D.Double(0 + strokePad,

                0 + strokePad, width - thickness - shadowPad, bottomLineY, radius, radius);

        Area area = new Area(bubble);

        g2.setRenderingHints(hints);

        g2.setColor(color);

        g2.setStroke(stroke);

        g2.draw(area);

        Area shadowArea = new Area(new Rectangle(0, 0, width, height));

        shadowArea.subtract(area);

        g.setClip(shadowArea);

        Color shadow = new Color(color.getRed(), color.getGreen(), color.getBlue(),

                128);

        g2.setColor(shadow);

        g2.translate(shadowPad, shadowPad);

        g2.draw(area);

    }

}
