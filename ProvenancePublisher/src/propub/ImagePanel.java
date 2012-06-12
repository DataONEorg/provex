package propub;

import java.io.*;
import java.awt.*;
import javax.swing.*;
import javax.imageio.*;

public class ImagePanel extends JPanel {

    private Image image;
    Image scaledImage;
    private int imageWidth = 0;
    private int imageHeight = 0;
    
    public ImagePanel() {
        super();
    }

    public void loadImage(String file) throws IOException {
        image = ImageIO.read(new File(file));
        //might be a situation where image isn't fully loaded, and
        //  should check for that before setting...
        imageWidth = image.getWidth(this);
        imageHeight = image.getHeight(this);
        setScaledImage();
    }

    //e.g., containing frame might call this from formComponentResized
    public void scaleImage() {
        setScaledImage();
    }

    //override paintComponent
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if ( scaledImage != null ) {
            //System.out.println("ImagePanel paintComponent " + ++paintCount);
            g.drawImage(scaledImage, 0, 0, this);
        }
    }

    private void setScaledImage() {
        if ( image != null ) {

            //use floats so division below won't round
            float iw = imageWidth;
            float ih = imageHeight;
            float pw = this.getWidth();   //panel width
            float ph = this.getHeight();  //panel height

            if ( pw < iw || ph < ih ) {

                /* compare some ratios and then decide which side of image to anchor to panel
                   and scale the other side
                   (this is all based on empirical observations and not at all grounded in theory)*/

                //System.out.println("pw/ph=" + pw/ph + ", iw/ih=" + iw/ih);

                if ( (pw / ph) > (iw / ih) ) {
                    iw = -1;
                    ih = ph;
                } else {
                    iw = pw;
                    ih = -1;
                }

                //prevent errors if panel is 0 wide or high
                if (iw == 0) {
                    iw = -1;
                }
                if (ih == 0) {
                    ih = -1;
                }

                scaledImage = image.getScaledInstance(
                            new Float(iw).intValue(), new Float(ih).intValue(), Image.SCALE_DEFAULT);

            } else {
                scaledImage = image;
            }

            //System.out.println("iw = " + iw + ", ih = " + ih + ", pw = " + pw + ", ph = " + ph);
        }
    }

}
