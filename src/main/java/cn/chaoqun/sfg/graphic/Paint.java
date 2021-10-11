package cn.chaoqun.sfg.graphic;

import java.awt.*;

/**
 * @author chaoqun
 * @date 2021/10/4 08:17
 */
public class Paint {

    private Color color;
    private Font font;

    private boolean antiAlias = false;
    private Style style = Style.FILL;
    private int strokeWidth = 1;

    public int getStrokeWidth() {
        return strokeWidth;
    }

    public void setStrokeWidth(int strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

    public Style getStyle() {
        return style;
    }

    public void setStyle(Style style) {
        this.style = style;
    }


    public enum Style{
        /**
         * FILL and STROKE mode
         */
        FILL, STROKE
    }

    public void setARGB(int r, int g, int b, int a) {
        color = new Color(r, g, b, a);
    }

    public Color getColor() {
        return color;
    }

    public Font getFont() {
        return font;
    }

    public void setFont(String fontFamily, int fontStyle, int fontSize) {
        this.font = new Font(fontFamily, fontStyle, fontSize);
    }


    public boolean isAntiAlias() {
        return antiAlias;
    }

    public void setAntiAlias(boolean antiAlias) {
        this.antiAlias = antiAlias;
    }
}
