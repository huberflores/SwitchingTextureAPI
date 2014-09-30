package com.jucyzhang.flappybatta;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.Log;

public class BlockerSprite implements Sprite {

  private int blockWidth;
  private int width;
  private int height;
  private int gap;
  private int max;
  private float currentX;
  private int currentUpHeight;
  private Drawable up;
  private Drawable down;
  private float speed;
  private final int X;
  private boolean scored = false;

  public static BlockerSprite obtainRandom(Context context, Drawable up,
      Drawable down, int X, int lastBlockY) {
    int height = ViewUtil.getScreenHeight(context);
    int gapMax = ViewUtil.dipResourceToPx(context, R.dimen.block_gap_max);
    int gap = ViewUtil.dipResourceToPx(context, R.dimen.block_gap);
    int min = ViewUtil.dipResourceToPx(context, R.dimen.block_min);
    int groundHeight = ViewUtil.dipResourceToPx(context, R.dimen.ground_height);
    int max = height - min - groundHeight - gap;
    int maxGap = ViewUtil.dipResourceToPx(context, R.dimen.block_gap_max)
        + height / 2;
    max = Math.min(max, maxGap);
    if (lastBlockY > 0) {
      min = Math.max(min, lastBlockY - gapMax);
      max = Math.min(max, lastBlockY + gapMax);
    }
    // Log.d(TAG, "max=" + max + " min=" + min);
    int upHeight = (int) (Math.random() * (max - min + 1)) + min;
    return new BlockerSprite(context, up, down, gap, groundHeight, upHeight, X);
  }

  private BlockerSprite(Context context, Drawable up, Drawable down, int gap,
      int groundHeight, int upHeight, int X) {
    this.up = up;
    this.down = down;
    width = ViewUtil.getScreenWidth(context);
    height = ViewUtil.getScreenHeight(context);
    // Log.d(TAG, "height:" + height);
    // Log.d(TAG, "groundHeight:" + groundHeight);
    this.max = height - groundHeight;
    // Log.d(TAG, "max:" + max);
    this.gap = gap;
    blockWidth = ViewUtil.dipResourceToPx(context, R.dimen.block_width);
    speed = ViewUtil.dipResourceToFloat(context, R.dimen.block_speed);
    currentX = width;
    this.currentUpHeight = upHeight;
    this.X = X;
  }

  @Override
  public void onDraw(Canvas canvas, Paint globalPaint, int status) {
    if (status == STATUS_NOT_STARTED) {
      return;
    }
    if (status == STATUS_NORMAL) {
      currentX -= speed;
    }
    up.setBounds((int) currentX, 0, (int) (currentX + blockWidth),
        currentUpHeight);
    up.draw(canvas);
    down.setBounds((int) currentX, currentUpHeight + gap, (int) currentX
        + blockWidth, max);
    down.draw(canvas);
  }

  @Override
  public boolean isAlive() {
    return currentX + blockWidth > 0;
  }

  public int getUpHeight() {
    return currentUpHeight;
  }

  @Override
  public boolean isHit(Sprite sprite) {
    if (sprite instanceof BattaSprite) {
      BattaSprite b = (BattaSprite) sprite;
      int bTop = b.getHitTop();
      int bBottom = b.getHitBottom();
      int bRight = b.getHitRight();
      int bLeft = b.getHitLeft();
      int left = (int) currentX;
      int right = (int) currentX + blockWidth;
      return (bTop < currentUpHeight || bBottom > currentUpHeight + gap)
          && ((bRight > left && bRight < right) || (bLeft > left && bLeft < right));
    } else {
      return false;
    }
  }

  @Override
  public int getScore() {
    if (!scored && currentX < X) {
      scored = true;
      return 1;
    } else {
      return 0;
    }
  }
}
