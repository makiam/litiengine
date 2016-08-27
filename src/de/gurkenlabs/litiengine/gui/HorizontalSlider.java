package de.gurkenlabs.litiengine.gui;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

import de.gurkenlabs.litiengine.graphics.Spritesheet;
import de.gurkenlabs.litiengine.input.Input;
import de.gurkenlabs.litiengine.sound.Sound;

public class HorizontalSlider extends Slider {
  private double minSliderX, maxSliderX;
  public static Icon ARROW_RIGHT = new Icon(FontLoader.getIconFontThree(), "\uE806");
  public static Icon ARROW_LEFT = new Icon(FontLoader.getIconFontThree(), "\uE805");

  public HorizontalSlider(double x, double y, double width, double height, float minValue, float maxValue, Spritesheet buttonSprite, Spritesheet sliderSprite, Sound hoverSound, boolean showArrowButtons) {
    super(x, y, width, height, minValue, maxValue, buttonSprite, sliderSprite, hoverSound, showArrowButtons);
    this.minSliderX = this.getX() + this.getHeight();
    this.maxSliderX = this.getX() + this.getWidth() - this.getHeight() * 3;
  }

  @Override
  public void prepare() {
    if (this.arrowButtonsShown()) {
      this.setButton1(new ImageComponent(this.getX(), this.getY(), this.getHeight(), this.getHeight(), this.getButtonSprite(), ARROW_LEFT.getText(), null, this.getHoverSound()));
      this.getButton1().setFont(ARROW_LEFT.getFont());
      this.getButton1().setTextColor(this.getTextColor());
      this.setButton2(new ImageComponent(this.getX() + this.getWidth() - this.getHeight(), this.getY(), this.getHeight(), this.getHeight(), this.getButtonSprite(), ARROW_RIGHT.getText(), null, null));
      this.getButton2().setFont(ARROW_RIGHT.getFont());
      this.getButton2().setTextColor(this.getTextColor());
      this.getComponents().add(this.getButton1());
      this.getComponents().add(this.getButton2());
    }
    this.setSlider(new ImageComponent(this.getRelativeSliderPosition().getX(), this.getRelativeSliderPosition().getY(), this.getHeight() * 2, this.getHeight(), this.getSliderSprite(), "", null, null));
    this.getComponents().add(this.getSlider());
    super.prepare();
  }

  @Override
  public void render(Graphics2D g) {
    g.setStroke(new BasicStroke((float) (this.getHeight() / 8)));
    g.setColor(this.getTextColor());
    g.drawLine((int) minSliderX, (int) (this.getY() + this.getHeight() / 2), (int) (this.getX() + this.getWidth() - this.getHeight()), (int) (this.getY() + this.getHeight() / 2));
    super.render(g);

  }

  @Override
  public Point2D getRelativeSliderPosition() {
    return new Point2D.Double(this.minSliderX + (this.getCurrentValue() / ((this.getMaxValue() - this.getMinValue()))) * (this.maxSliderX - minSliderX), this.getY());
  }

  @Override
  protected void initializeComponents() {

  }

  @Override
  public void setValueRelativeToMousePosition() {
    double mouseX = Input.MOUSE.getLocation().getX();
    if (mouseX >= this.minSliderX && mouseX <= this.maxSliderX) {
      double relativeMouseX = mouseX - this.minSliderX;
      double percentage = relativeMouseX / (this.maxSliderX - this.minSliderX);
      this.setCurrentValue((float) (this.getMinValue() + percentage * (this.getMaxValue() - this.getMinValue())));
    }
  }

}
