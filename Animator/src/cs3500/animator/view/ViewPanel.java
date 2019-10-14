package cs3500.animator.view;

//import java.awt.;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import javax.swing.JPanel;

import cs3500.animator.model.EasyAnimatorShape;
import cs3500.animator.model.Oval;
import cs3500.animator.model.Rectangle;

/**
 * class that represents a JPanel that will be added to EasyAnimatorVisualViewImpl. Panel is
 * manipulated by adding current graphical representations of the shapes to it.
 */
public class ViewPanel extends JPanel {
  private EasyAnimatorShape[] accuShapes;

  /**
   * Creates a ViewPanel with accuShapes set to a EasyAnimatorShape[] with length of 1.
   */
  public ViewPanel() {

    this.accuShapes = new EasyAnimatorShape[1];
  }

  /**
   * Sets accuShapes in the class to the given Array of EasyAnimatorShapes.
   *
   * @param newShapes list of given shapes read from the controller with their commands.
   */
  protected void setAccuShapes(EasyAnimatorShape[] newShapes) {
    this.accuShapes = newShapes;
  }

  /**
   * Iterates through accuShapes checking if the object is an instance of either a Rectangle or a
   * Oval. If the shape is, it creates corresponding graphical representation of it with its
   * characteristics (color, length, width, location) set to that EasyAnimatorShapes
   * variables which is added to g.
   *
   * @param g graphics of ViewPanel.
   */
  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    this.sortByLayer();
    Graphics2D g2d = (Graphics2D) g.create();
    for (EasyAnimatorShape shape : accuShapes) {
      if (shape != null) {
        java.awt.Shape drawnShape = null;
        g2d.setColor(shape.getShape().getColor().initializeColor());
        if (shape.getShape() instanceof Rectangle) {
          drawnShape = new java.awt.Rectangle ((int) (shape.getLocation().getX()),
                  (int) shape.getLocation().getY(),
                  ((int) (shape.getShape().getXDim())),
                  ((int) (shape.getShape().getYDim())));
          AffineTransform old = g2d.getTransform();
          g2d.rotate(Math.toRadians(shape.getRotationDegree()),( (shape.getShape().getXDim() / 2 )
                          + shape.getLocation().getX()),
                  (( shape.getShape().getYDim() / 2 ) + shape.getLocation().getY()) );
          g2d.draw(drawnShape);
          g2d.fill(drawnShape);
          g2d.setTransform(old);
        } else if (shape.getShape() instanceof Oval) {
          drawnShape = new java.awt.geom.Ellipse2D.Double ((int) (shape.getLocation().getX()),
                  (int) shape.getLocation().getY(),
                  ((int) (shape.getShape().getXDim())),
                  ((int) (shape.getShape().getYDim())));
          AffineTransform old = g2d.getTransform();
          g2d.rotate(Math.toRadians(shape.getRotationDegree()),shape.getLocation().getX() + (shape.getShape().getXDim() / 2 ),
                    shape.getLocation().getY() + (shape.getShape().getYDim()  / 2 ));
          g2d.draw(drawnShape);
          g2d.fill(drawnShape);
          g2d.setTransform(old);
        }
      }
    }
  }


  private void sortByLayer() {
    ArrayList<EasyAnimatorShape> nonNulls = new ArrayList<>();
    for (EasyAnimatorShape shape : accuShapes) {
      if (shape != null) {
        nonNulls.add(shape);
      }
    }
    accuShapes = nonNulls.toArray(new EasyAnimatorShape[nonNulls.size()]);
    EasyAnimatorShape reverse;
    for (int i = 1; i < accuShapes.length; i++) {
      for (int j = i; j > 0; j--) {
          if (accuShapes[j].getLayer() > accuShapes[j - 1].getLayer()) {
            reverse = accuShapes[j];
            accuShapes[j] = accuShapes[j - 1];
            accuShapes[j - 1] = reverse;
          }
        }
    }
  }
}
