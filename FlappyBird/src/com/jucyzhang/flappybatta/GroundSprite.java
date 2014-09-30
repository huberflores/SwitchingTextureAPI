package com.jucyzhang.flappybatta;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;

public class GroundSprite implements Sprite {

  private Drawable groundBottom;
  private Drawable groundTop;
  private int groundHeight;
  private int groundWidth;
  private int groundMargin;
  private int topY;
  private int topHeight;
  private int width;
  private int height;

  private float currentX = 0;
  private final float speed;

  public GroundSprite(Context context) {
    Resources res = context.getResources();
    groundBottom = res.getDrawable(R.drawable.bg_ground_bottom);
    groundTop = res.getDrawable(R.drawable.bg_ground_up);
    width = ViewUtil.getScreenWidth(context);
    height = ViewUtil.getScreenHeight(context);
    groundHeight = ViewUtil.dipResourceToPx(context, R.dimen.ground_height);
    groundWidth = ViewUtil.dipResourceToPx(context, R.dimen.ground_width);
    groundMargin = ViewUtil.dipResourceToPx(context, R.dimen.ground_margin);
    speed = ViewUtil.dipResourceToFloat(context, R.dimen.block_speed);
    topY = height - groundHeight;
    topHeight = groundHeight - groundMargin;
    groundBottom.setBounds(0, topY + topHeight, width, height);
  }

  @Override
  public void onDraw(Canvas canvas, Paint globalPaint, int status) {
    if (status == STATUS_NORMAL || status == STATUS_NOT_STARTED) {
      currentX -= speed;
      while (currentX <= -groundWidth) {
        currentX += groundWidth;
      }
    }
    groundBottom.draw(canvas);
    for (float x = currentX; x < width; x += groundWidth) {
      groundTop.setBounds((int) x, topY, (int) (x + groundWidth), topY
          + topHeight);
      groundTop.draw(canvas);
    }
  }

  @Override
  public boolean isAlive() {
    return true;
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
