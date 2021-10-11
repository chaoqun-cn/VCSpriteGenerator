package cn.chaoqun.sfg.graphic;

import cn.chaoqun.sfg.config.SpriteOptions;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author chaoqun
 * @date 2021/10/4 08:09
 */
public class Canvas {

    private BufferedImage bg;
    private Graphics2D g2d;

    private AffineTransform savedX;

    public Canvas(BufferedImage bg) {
        this.bg = bg;
        this.g2d = (Graphics2D) bg.getGraphics();
    }

    public Canvas(SpriteOptions spriteOptions, long totalPic) {
        int w, h;
        if (totalPic < spriteOptions.getPrimaryAxisMaxN()) {
            if (spriteOptions.getLayoutDirection() == SpriteOptions.LayoutDirection.ROW_FIRST) {
                w = (spriteOptions.getPipWeight() + spriteOptions.getHGap()) * (int) totalPic- spriteOptions.getHGap();
                h = spriteOptions.getPipHeight();
            }else {
                h = (spriteOptions.getPipHeight() + spriteOptions.getVGap()) *  (int) totalPic - spriteOptions.getVGap();
                w = spriteOptions.getPipWeight();
            }
        } else {
            if (spriteOptions.getLayoutDirection() == SpriteOptions.LayoutDirection.ROW_FIRST) {
                w = (spriteOptions.getPipWeight() + spriteOptions.getHGap()) * spriteOptions.getPrimaryAxisMaxN() - spriteOptions.getHGap();
                h = (int) Math.ceil((double) totalPic / (double) spriteOptions.getPrimaryAxisMaxN()) * (spriteOptions.getPipHeight() + spriteOptions.getVGap()) - spriteOptions.getVGap();
            }else {
                h = (spriteOptions.getPipHeight() + spriteOptions.getVGap()) * spriteOptions.getPrimaryAxisMaxN() - spriteOptions.getVGap();
                w = (int) Math.ceil((double) totalPic / (double) spriteOptions.getPrimaryAxisMaxN()) * (spriteOptions.getPipWeight() + spriteOptions.getHGap()) - spriteOptions.getHGap();

            }
        }

        this.bg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        this.g2d = (Graphics2D) bg.getGraphics();
    }

    public Canvas(int w, int h) {
        this.bg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        this.g2d = (Graphics2D) bg.getGraphics();
    }

    public void drawImage(Image image, int x, int y) {
//        image.getScaledInstance()
        g2d.drawImage(image, x, y, null);
    }

    public void drawColor(Color bgColor) {
        g2d.setColor(bgColor);
        g2d.fillRect(0, 0, bg.getWidth(), bg.getHeight());
    }

    public void arbitrayDraw(Scratch scratch) {
        scratch.draw(g2d);
    }

    public void save() {
        savedX= g2d.getTransform();
    }

    public void restore() {
        g2d.setTransform(savedX);
    }

    public BufferedImage finish() {
        g2d.dispose();
        return bg;
    }

    public Path export(Path dstDir, String name, String formatName) throws IOException {

        Path dstPath = Paths.get(dstDir.toString(), name + "." + formatName);
        ImageIO.write(finish(), formatName, dstPath.toFile());

        return dstPath;
    }


}
