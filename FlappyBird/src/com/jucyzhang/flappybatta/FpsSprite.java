package com.jucyzhang.flappybatta;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;

public class FpsSprite implements Sprite {

  private static final int REFRESHCOUNT = 50;
  private int currentCount = 0;
  private int currentFrameCount = 0;
  private int currentFps = 0;
  private int x;
  private int y;

  private long startTime = 0;
  private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
  {
    paint.setColor(0xFFFFFFFF);
    paint.setShadowLayer(1, 1, 1, 0xFF232323);
    paint.setTextAlign(Align.RIGHT);
  }

  public FpsSprite(Context context) {
    float textSize = ViewUtil.dipResourceToFloat(context, R.dimen.fps_size);
    paint.setTextSize(textSize);
    int marginBottom = ViewUtil.dipResourceToPx(context,
        R.dimen.fps_margin_bottom);
    int marginRight = ViewUtil.dipResourceToPx(context,
        R.dimen.fps_margin_right);
    x = ViewUtil.getScreenWidth(context) - marginRight;
    y = ViewUtil.getScreenHeight(context) - marginBottom;
  }

  @Override
  public void onDraw(Canvas canvas, Paint globalPaint, int status) {
    if (startTime <= 0) {
      startTime = System.currentTimeMillis();
    }
    currentCount++;
    if (currentCount > REFRESHCOUNT) {
      currentCount = 0;
      long currentTime = System.currentTimeMillis();
      currentFps = (int) (currentFrameCount * 1000 / (currentTime - startTime));
      currentFrameCount = 0;
      startTime = currentTime;
    } else {
      currentFrameCount++;
    }
    // Log.d(TAG, "currentFps:" + currentFps);
    if (currentFps > 0) {
      canvas.drawText("fps:" + currentFps, x, y, paint);
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
