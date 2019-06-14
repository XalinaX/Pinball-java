
package ICS3U;
 
import javax.swing.*;
import java.beans.*;
import java.awt.*;
import java.io.File;
 
/**
 * set the view of file chooser
 * show the image that user clicked in file chooser
 */
public class ImagePreview extends JComponent
                          implements PropertyChangeListener {
    ImageIcon thumbnail = null;
    File file = null;
 
    public ImagePreview(JFileChooser fc) {
        setPreferredSize(new Dimension(200, 100));
        fc.addPropertyChangeListener(this);
    }
 
    /**
     * load the image from the file
     */
    public void loadImage() {
        if (file == null) {
            thumbnail = null;
            return;
        }
 
        ImageIcon tmpIcon = new ImageIcon(file.getPath());
        if (tmpIcon != null) {
            if (tmpIcon.getIconWidth() > 180) {
                thumbnail = new ImageIcon(tmpIcon.getImage().
                                          getScaledInstance(180, -1,
                                                      Image.SCALE_DEFAULT));
            }else if (tmpIcon.getIconWidth() < 120) {
                thumbnail = new ImageIcon(tmpIcon.getImage().
                        getScaledInstance(120, -1,
                                    Image.SCALE_DEFAULT));
            } else { 
                thumbnail = tmpIcon;
            }
        }
    }
 
    /**
     * check if the image file change
     */
    public void propertyChange(PropertyChangeEvent e) {
        boolean update = false;
        String prop = e.getPropertyName();
 
        //If the directory changed, don't show an image.
        if (JFileChooser.DIRECTORY_CHANGED_PROPERTY.equals(prop)) {
            file = null;
            update = true;
 
        //If a file became selected, find out which one.
        } else if (JFileChooser.SELECTED_FILE_CHANGED_PROPERTY.equals(prop)) {
            file = (File) e.getNewValue();
            update = true;
        }
 
        //Update the preview accordingly.
        if (update) {
            thumbnail = null;
            if (isShowing()) {
                loadImage();
                repaint();
            }
        }
    }
 
    /**
     * paint the image 
     */
    protected void paintComponent(Graphics g) {
        if (thumbnail == null) {
            loadImage();
        }
        if (thumbnail != null) {
            int x = getWidth()/2 - thumbnail.getIconWidth()/2;
            int y = getHeight()/2 - thumbnail.getIconHeight()/2;
 
            if (y < 0) {
                y = 0;
            }
 
            if (x < 5) {
                x = 5;
            }
            thumbnail.paintIcon(this, g, x, y);
        }
    }
}
