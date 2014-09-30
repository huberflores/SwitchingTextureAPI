package com.jucyzhang.flappybatta;

import java.util.Random;

import android.graphics.Canvas;
import android.graphics.Paint;

public interface Sprite {

  public static final Random RANDOM = new Random();
  public static final String TAG = "Sprite";
  public static final int STATUS_NORMAL = 0;
  public static final int STATUS_NOT_STARTED = 1;
  public static final int STATUS_GAME_OVER = 2;

  void onDraw(Canvas canvas, Paint globalPaint, int status);

  boolean isAlive();

  boolean isHit(Sprite sprite);

  int getScore();
}
