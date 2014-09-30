package com.jucyzhang.flappybatta;

import android.graphics.Canvas;
import android.graphics.Paint;

public class SplashSprite implements Sprite {
  private static final int LIFE = 25;
  private int currentLife = -1;
  private static final int COLOR = 255;

  public SplashSprite() {
  }

  @Override
  public void onDraw(Canvas canvas, Paint globalPaint, int status) {
    currentLife += 1;
    if (currentLife > LIFE) {
      return;
    }
    canvas.drawARGB((int) ((1 - currentLife / (float) LIFE) * 180), COLOR,
        COLOR, COLOR);
  }

  @Override
  public boolean isAlive() {
    return currentLife <= LIFE;
  }

  @Override
  public boolean isHit(Sprite sprite) {
    return false;
  }

  @Override
  public int getScore() {
    return 0;
  }

}
