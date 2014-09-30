package com.jucyzhang.flappybatta;

import java.util.Random;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;

public class BattaSprite implements Sprite { 

  private static final int[][] DRAWABLE_BIRD = new int[][] {
      new int[] { R.drawable.img_bird_blue_1, R.drawable.img_bird_blue_2,
          R.drawable.img_bird_blue_3 },
      new int[] { R.drawable.img_bird_yellow_1, R.drawable.img_bird_yellow_2,
          R.drawable.img_bird_yellow_3 },
      new int[] { R.drawable.img_bird_red_1, R.drawable.img_bird_red_2,
          R.drawable.img_bird_red_3 } };
  private static Random random = new Random();
  private static final int FLY_COUNT = 6;
  private int count = 0;
  private Drawable birds[] = new Drawable[4];
  private final int X;
  private int width;
  private int height;
  private int currentHeight;
  private int birdHeight;
  private int birdWidth;
  private float currentSpeed;
  private final float acceleration;
  private final float tapSpeed;
  private int maxHeight;
  private int hitPaddingTop;
  private int hitPaddingBottom;
  private int hitPaddingRight;
  private int hitPaddingLeft;

  public BattaSprite(Context context) {
    Resources res = context.getResources();
    int currentBird = random.nextInt(DRAWABLE_BIRD.length);
    birds[0] = birds[2] = res.getDrawable(DRAWABLE_BIRD[currentBird][0]);
    birds[1] = res.getDrawable(DRAWABLE_BIRD[currentBird][1]);
    birds[3] = res.getDrawable(DRAWABLE_BIRD[currentBird][2]);
    birdHeight = ViewUtil.dipResourceToPx(context, R.dimen.bird_height);
    birdWidth = birdHeight * birds[0].getIntrinsicWidth()
        / birds[0].getIntrinsicHeight();
    width = ViewUtil.getScreenWidth(context);
    height = ViewUtil.getScreenHeight(context);
    int xPosition = ViewUtil.dipResourceToPx(context, R.dimen.bird_position_x);
    X = width / 2 - birdWidth / 2 - xPosition;
    currentHeight = height / 2 - birdHeight / 2;
    acceleration = ViewUtil.dipResourceToFloat(context,
        R.dimen.bird_acceleration);
    tapSpeed = ViewUtil.dipResourceToFloat(context, R.dimen.bird_tap_speed);
    maxHeight = height
        - ViewUtil.dipResourceToPx(context, R.dimen.ground_height);
    hitPaddingBottom = ViewUtil.dipResourceToPx(context,
        R.dimen.bird_hit_padding_bottom);
    hitPaddingTop = ViewUtil.dipResourceToPx(context,
        R.dimen.bird_hit_padding_top);
    hitPaddingLeft = ViewUtil.dipResourceToPx(context,
        R.dimen.bird_hit_padding_left);
    hitPaddingRight = ViewUtil.dipResourceToPx(context,
        R.dimen.bird_hit_padding_right);
    currentSpeed = 0;
  }

  public int getHitLeft() {
    return X + hitPaddingLeft;
  }

  public int getHitTop() {
    return currentHeight + hitPaddingTop;
  }

  public int getHitBottom() {
    return currentHeight + birdHeight - hitPaddingBottom;
  }

  public int getHitRight() {
    return X + birdWidth - hitPaddingRight;
  }

  @Override
  public void onDraw(Canvas canvas, Paint globalPaint, int status) {
    if (count >= 4 * FLY_COUNT) {
      count = 0;
    }
    if (status != Sprite.STATUS_NOT_STARTED) {
      currentHeight += currentSpeed;
      synchronized (this) {
        currentSpeed += acceleration;
      }
    }
    if (currentHeight <= 0) {
      currentHeight = 0;
    }
    if (currentHeight + birdHeight > maxHeight) {
      currentHeight = maxHeight - birdHeight;
    }
    Drawable bird = null;
    if (status == Sprite.STATUS_GAME_OVER) {
      bird = birds[0];
    } else {
      bird = birds[(count++) / FLY_COUNT];
    }
    // Log.d(TAG, "X:" + X + " currentHeight:" + currentHeight + " birdWidth:"
    // + birdWidth + " birdHeight:" + birdHeight);
    bird.setBounds(X, currentHeight, X + birdWidth, currentHeight + birdHeight);
    bird.draw(canvas);
  }

  @Override
  public boolean isAlive() {
    return true;
  }

  @Override
  public boolean isHit(Sprite sprite) {
    return currentHeight + birdHeight >= maxHeight;
  }

  public void onTap() {
    synchronized (this) {
      currentSpeed = tapSpeed;
    }
  }

  @Override
  public int getScore() {
    return 0;
  }

  public int getX() {
    return X;
  }

}
