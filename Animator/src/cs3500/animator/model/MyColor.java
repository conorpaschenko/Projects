package cs3500.animator.model;

import java.awt.Color;

/**
 * A model of the java.awt.Color class. This model is used because the java.awt.Color class
 * cannot be used to model.
 */
public class MyColor {
  private final float r;
  private final float g;
  private final float b;

  /**
   * Initializes a MyColor by setting the individual values of the rgb.
   * @param r the red value
   * @param g the green value
   * @param b the blue value
   * @throws IllegalArgumentException if any of the arguments are out of bounds of the max or min
   *        color values
   */
  MyColor(float r, float g, float b) throws IllegalArgumentException {
    if (r < 0 || r > 1 || g < 0 || g > 1 || b < 0 || b > 1) {
      throw new IllegalArgumentException("Illegal Color");
    }
    this.r = r;
    this.g = g;
    this.b = b;
  }

  /**
   * Initializes a MyColor by accepting and setting the rgb.
   * @param rgb the rgb value of the color
   */
  public MyColor(float[] rgb) {
    if (rgb.length != 3) {
      throw new IllegalArgumentException("RGB must be of size 3");
    }
    for (float num : rgb) {
      if (num > 1 || num < 0) {
        throw new IllegalArgumentException("Illegal Color");
      }
    }
    this.r = rgb[0];
    this.g = rgb[1];
    this.b = rgb[2];

  }

  public float getR() {
    return r;
  }

  public float getG() {
    return g;
  }

  public float getB() {
    return b;
  }

  /**
   * Adds each individual int in the changeArray to the rgb value at the corresponding index
   * value.
   * @param changeArray array of ints to add to the corresponding index value of the rgb
   * @return the new MyColor
   */
  public MyColor plus(float[] changeArray) throws IllegalArgumentException {
    if (changeArray.length < 3) {
      throw new IllegalArgumentException("Array parameter must be length of at least 3");
    }
    float[] newArray = new float[3];
    newArray[0] = r + changeArray[0];
    newArray[1] = g + changeArray[1];
    newArray[2] = b + changeArray[2];
    return new MyColor(newArray);
  }

  /**
   * Creates a deep copy of this MyColor.
   * @return a deep copy of this MyColor
   */
  public MyColor copy() {
    float[] newArray = {this.r, this.g, this.b};
    return new MyColor(newArray);
  }

  /**
   * Formats the MyColor in the same fashion of a java.awt.Color is formatted in its toString()
   * method.
   * @return the formatted String
   */
  public String toString() {
    return String.format("[r=%.2f,g=%.2f,b=%.2f]", this.r, this.g, this.b);
  }

  public Color initializeColor() {
    return new Color(this.r, this.g, this.b);
  }


}
