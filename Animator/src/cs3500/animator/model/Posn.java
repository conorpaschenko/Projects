package cs3500.animator.model;

/**
 * A posn class that represents location using x and y coordinates.
 */
public class Posn {
  private float x;
  private float y;



  /**
   * Initializes a Posn's x and y coordinates.
   * @param x the x coordinate
   * @param y the y coordinate
   */
  public Posn(float x, float y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Returns a new Posn with an x value of this x value increased by deltaX, and
   * the y value of this y value is increased by deltaY.
   * @param deltaX the amount to increase this x value by
   * @param deltaY the amount to increase this y value by
   * @return the new Posn
   */
  public Posn plus(float deltaX, float deltaY) {
    return new Posn(this.x + deltaX, this.y + deltaY);
  }


  /**
   * Formats the contents of the Posn into a string
   * Represents them as a common coordinate: (x,y) where x is this Posn's x value,
   * and y is this Posn's y value.
   * @return the formatted String
   */
  public String format() {
    return String.format("(%.2f, %.2f)", this.x, this.y);
  }

  /**
   * Returns a deep copy of this Posn.
   * @return a deep copy of this Posn
   */
  public Posn copy() {
    return new Posn(this.x, this.y);
  }

  /**
   * A getter for the x coordinate.
   * @return the x coordinate
   */
  public float getX() {
    return x;
  }

  /**
   * A getter for the y coordinate.
   * @return the y coordinate
   */
  public float getY() {
    return y;
  }

  @Override
  public boolean equals(Object o1) {
    if (this == o1) {
      return true;
    }
    else if (o1 == null) {
      return false;
    }
    else if (o1 instanceof Posn) {
      Posn pos = (Posn) o1;
      return (pos.x == this.x && pos.y == this.y);
    }
    else {
      return false;
    }
  }

  @Override
  public int hashCode() {
    return ("(" + this.x + this.y + ")").hashCode();
  }
}
