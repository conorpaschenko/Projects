package cs3500.animator.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.ChangeListener;


import cs3500.animator.controller.ButtonListener;
import cs3500.animator.model.EasyAnimatorShape;


/**
 * An implementation of an EasyAnimatorInteractiveView. Capable of displaying a user interface
 * that allows the user to select the speed of the animation, restart the animation, loop the
 * animation, reverse the animation, pause the animation, and export a recreation animation
 * to an svg file.
 */
public class EasyAnimatorInteractiveViewImpl extends JFrame implements EasyAnimatorInteractiveView {
  private ArrayList<EasyAnimatorShape[]> frames;
  private ViewPanel animationPanel;
  private JPanel buttonPanel;
  private JButton  restartButton;
  private JButton exportButton;
  private JButton reverseButton;
  private JButton speedButton;
  private JToggleButton  pauseButton;
  private JToggleButton loopButton;
  private JTextField exportInput;
  private JTextField speedInput;
  private String outputName;
  private ArrayList<EasyAnimatorShape> accuShapes;
  private boolean outputMustChange;
  private Appendable output;
  private int speed;
  private boolean inputFinished;
  private JSlider scrubber;

  /**
   * The constructor. Initializes an implementation. Initializes the buttons, the panels, the frame
   * and every button. Does not initialize the actual ActionListeners of the buttons, as that
   * must be handled by the controller.
   */
  public EasyAnimatorInteractiveViewImpl() {
    super();
    accuShapes = new ArrayList<>();
    frames = new ArrayList<>();
    this.speed = 1;
    this.outputMustChange = true;
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(new BorderLayout());
    this.inputFinished = false;
    animationPanel = new ViewPanel();
    animationPanel.setPreferredSize(new Dimension(1500,1500));
    this.add(animationPanel, BorderLayout.CENTER);
    scrubber = new JSlider(SwingConstants.HORIZONTAL);
    this.setSize(new Dimension(1000, 1000));
    this.initAllButtons();
    this.add(buttonPanel, BorderLayout.NORTH);
    this.setVisible(true);
  }




  @Override
  public void addToView(EasyAnimatorShape[] shapes, int framesPerSecond) {
    frames.add(shapes);
    for (EasyAnimatorShape shape : shapes) {
      int i = 0;
      if (shape != null) {
        for (EasyAnimatorShape containedShape : accuShapes) {
          if (shape.getID() != containedShape.getID()) {
            i++;
          }
        }
        if (i == accuShapes.size()) {
          accuShapes.add(shape.copy());
        }
      }
    }
  }

  @Override
  public void declareInputFinished() {
    this.initScrubber();
    this.inputFinished = true;

  }

  @Override
  public void exportToSVG(boolean loop) {
    if (inputFinished) {
      if (!checkExportInput(outputName)) {
        exportInput.setText("Enter file in \"fileName.svg\" format");
        exportInput.setBackground(new Color(255, 0,0));
        return;
      }
      else {
        this.outputMustChange = true;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<svg width=\"1000\" height=\"1000\" version=\"1.1\"\n" +
                "     xmlns=\"http://www.w3.org/2000/svg\">\n");
        if (loop) {
          stringBuilder.append("<rect>\n" +
                  "   <animate id=\"base\" begin=\"0;base.end\" dur=\""
                  + (Math.ceil(frames.size() / speed) * 1000 + 2000)
                  + "ms\" attributeName=\"visibility\" from=\"hide\" to=\"hide\"/>\n" +
                  "</rect>");
        }
        for (EasyAnimatorShape shape : accuShapes) {
          stringBuilder.append(shape.formatAsSVG(speed, loop) + "\n");
        }
        stringBuilder.append("</svg>");
        try {
          output.append(stringBuilder.toString());
        } catch (IOException e) {
          exportInput.setText("Select new file");
          exportInput.setBackground(new Color(255, 0, 0));
        } catch (NullPointerException e) {
          exportInput.setText("Enter file in \"fileName.svg\" format");
        }
        try {
          ((BufferedWriter) output).close();
          exportInput.setText( outputName + " Exported");
          exportInput.setBackground(new Color(255, 255, 255));
        } catch (IOException e) {
          e.printStackTrace();
        } catch (NullPointerException r) {
          exportInput.setText("Enter file in \"fileName.svg\" format");
        }
      }
    }
  }


  /**
   * Initializes the JSlider with the relevant fields for scrubbing.
   */
  private void initScrubber() {
    scrubber.setMinimum(0);
    scrubber.setMaximum(frames.size() - 1);
    scrubber.setValue(0);
    scrubber.setMinorTickSpacing(1);
    scrubber.setMajorTickSpacing(10);
    scrubber.setPaintTicks(true);
    scrubber.setPaintLabels(true);
    this.add(scrubber, BorderLayout.SOUTH);
  }

  @Override
  public void setOutput(Appendable output) {
    this.output = output;
  }

  /**
   * Checks to see that the desired name of the svg file contains the correct
   * ".svg" extension
   * @param input the desired name of the svg file
   * @return a boolean regarding whether or not the string ends in ".svg"
   */
  private static boolean checkExportInput(String input) {
    if (input == null) {
      return false;
    }
    if (input.length() > 4) {
      return (input.substring(input.length() - 4).equals(".svg"));
    }
    else {
      return false;
    }
  }

  @Override
  public String getOutputName() {
    return this.outputName;
  }

  @Override
  public int getSpeed() {
    return this.speed;
  }

  @Override
  public void draw(EasyAnimatorShape[] shapes) {
    animationPanel = new ViewPanel();
    animationPanel.setAccuShapes(shapes);
    this.add(animationPanel,  BorderLayout.CENTER);
    this.revalidate();
    this.setVisible(true);
  }

  @Override
  public ButtonListener getButtonListener() {
    return (ButtonListener) this.exportButton.getActionListeners()[0];
  }

  @Override
  public void setScrubber(int i) {
    scrubber.setValue(i);
  }

  @Override
  public void addScrubListener(ChangeListener changeListener) {
    scrubber.addChangeListener(changeListener);

  }

  @Override
  public void addActionListener(ActionListener l) {
    this.exportButton.addActionListener(l);
    this.restartButton.addActionListener(l);
    this.pauseButton.addActionListener(l);
    this.reverseButton.addActionListener(l);
    this.loopButton.addActionListener(l);
    this.speedButton.addActionListener(l);
  }

  /**
   * Initializes all buttons in the user interface. Initializes action listeners for the buttons
   * that need them and sets the button commands for the buttons that need them.
   */
  private void initAllButtons() {
    buttonPanel = new JPanel();
    buttonPanel.setLayout(new FlowLayout());
    buttonPanel.setPreferredSize(new Dimension(100, 70));

    pauseButton = new JToggleButton("Play");
    pauseButton.setActionCommand("Play/Pause Button");
    buttonPanel.add(pauseButton);


    reverseButton = new JButton("Rewind");
    reverseButton.setActionCommand("Rewind Button");
    buttonPanel.add(reverseButton);



    loopButton = new JToggleButton("loop");
    loopButton.setActionCommand("Loop Button");


    buttonPanel.add(loopButton);

    restartButton = new JButton("restart");
    restartButton.setActionCommand("Restart Button");

    buttonPanel.add(restartButton);


    speedInput = new JTextField(15);
    speedInput.addActionListener((ActionEvent e) -> {
      try {
        speed = Integer.parseInt(speedInput.getText());
        if (speed < 1) {
          speed = 1;
        } else if (speed > 1000) {
          speed = 1000;
        }
        speedInput.setText(Integer.toString(speed));
        speedInput.setBackground(new Color(0,255,0));
      } catch (NumberFormatException d) {
        speedInput.setText("Enter integer");
        speedInput.setBackground(new Color(255,0,0));
      }
    });

    speedButton = new JButton("Enter Speed");
    speedButton.setActionCommand("Speed Button");

    JLabel label = new JLabel("Speed:");
    label.setLabelFor(speedInput);
    buttonPanel.add(label);
    buttonPanel.add(speedInput);
    buttonPanel.add(speedButton);


    JLabel svgLabel = new JLabel("SVG file name:");
    exportInput = new JTextField(15);
    exportInput.addActionListener((ActionEvent s) -> {
      try {
        if (!checkExportInput(exportInput.getText())) {
          exportInput.setText("\"fileName.svg\"");
          exportInput.setBackground(new Color(255,0,0));
        }
        else {
          if (!outputMustChange || !exportInput.getText().equals(outputName)) {
            outputName = exportInput.getText();
            exportInput.setBackground(new Color(0, 255, 0));
          }
          else {
            exportInput.setText("Enter new file");
            exportInput.setBackground(new Color(255,0,0));
          }
        }
      } catch (Exception a) {
        exportInput.setBackground(new Color(255,0,0));
        exportInput.setText("\"fileName.svg\"");
      }
    });


    exportInput.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        exportButton.setBackground(new Color(255,255,255));
      }
    });


    svgLabel.setLabelFor(exportInput);
    exportButton = new JButton("Export SVG File");
    exportButton.setActionCommand("Export Button");


    buttonPanel.add(svgLabel);
    buttonPanel.add(exportInput);
    buttonPanel.add(exportButton);
  }
}





