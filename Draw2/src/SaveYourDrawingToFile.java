import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Panel;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;

public class SaveYourDrawingToFile extends JFrame implements MouseListener, ActionListener {
  List<Point> displayList = new ArrayList<Point>();
  String pathname = "data.dat";
  JButton clearBtn = new JButton("Clear");
  JButton saveBtn = new JButton("Save");
  JButton restoreBtn = new JButton("Restore");
  JButton quitBtn = new JButton("Quit");

  public static void main(String args[]) {
    SaveYourDrawingToFile that = new SaveYourDrawingToFile();
    that.setVisible(true);
  }

  public SaveYourDrawingToFile() {
    addMouseListener(this);

    setLayout(new BorderLayout());
    Panel pan = new Panel();
    clearBtn.addActionListener(this);
    pan.add(clearBtn);
    saveBtn.addActionListener(this);
    pan.add(saveBtn);
    restoreBtn.addActionListener(this);
    pan.add(restoreBtn);
    quitBtn.addActionListener(this);
    pan.add(quitBtn);
    add("North", pan);
    setSize(350, 200);
  }

  public void paint(Graphics g) {
    g.setColor(Color.white);
    g.fillRect(0, 0, getSize().width, getSize().height);

    g.setColor(Color.black);
    int i = 0;
    while (i < displayList.size()) {
      Point p0 = (Point) (displayList.get(i++));
      Point p1 = (Point) (displayList.get(i++));
      int x = Math.min(p0.x, p1.x);
      int y = Math.min(p0.y, p1.y);
      int w = Math.abs(p0.x - p1.x);
      int h = Math.abs(p0.y - p1.y);
      g.drawRect(x, y, w, h);
    }
  }

  public void mousePressed(MouseEvent e) {
    Point p = new Point(e.getX(), e.getY());
    displayList.add(p);
  }

  public void mouseReleased(MouseEvent e) {
    Point p = new Point(e.getX(), e.getY());
    displayList.add(p);
    repaint();
  }

  public void mouseClicked(MouseEvent e) {
  }

  public void mouseEntered(MouseEvent e) {
  }

  public void mouseExited(MouseEvent e) {
  }

  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == clearBtn) {
      displayList = new Vector();
      repaint();
    } else if (e.getSource() == saveBtn) {
      try {
        FileOutputStream fos = new FileOutputStream(pathname);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(displayList);
        oos.flush();
        oos.close();
        fos.close();
      } catch (Exception ex) {
        System.out.println("Trouble writing display list vector");
      }
    } else if (e.getSource() == restoreBtn) {
      try {
        FileInputStream fis = new FileInputStream(pathname);
        ObjectInputStream ois = new ObjectInputStream(fis);
        displayList = (Vector) (ois.readObject());
        ois.close();
        fis.close();
        repaint();
      } catch (Exception ex) {
        System.out.println("Trouble reading display list vector");
      }
    } else if (e.getSource() == quitBtn) {
      setVisible(false);
      dispose();
      System.exit(0);
    }
  }
}
