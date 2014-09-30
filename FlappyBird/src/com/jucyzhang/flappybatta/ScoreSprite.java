package com.jucyzhang.flappybatta;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;

public class ScoreSprite implements Sprite {

  private float textSize;
  private static int textColor = 0xFFFFFFFF;
  private static int shadowColor = 0xFF222222;
  private int currentScore;
  private int width;
  private int marginTop;
  private volatile static Paint sharedPaint;

  private static void initSharedPaint(float textSize) {
    if (sharedPaint == null) {
      synchronized (ScoreSprite.class) {
        if (sharedPaint == null) {
          sharedPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
          sharedPaint.setTextAlign(Align.CENTER);
          sharedPaint.setTextSize(textSize);
          sharedPaint.setColor(textColor);
          sharedPaint.setShadowLayer(2, 2, 2, shadowColor);
        }
      }
    }
  }

  public ScoreSprite(Context context) {
    Resources res = context.getResources();
    textSize = res.getDimension(R.dimen.score_size);
    width = ViewUtil.getScreenWidth(context);
    marginTop = ViewUtil.dipResourceToPx(context, R.dimen.score_margin);
    initSharedPaint(textSize);
  }

  @Override
  public void onDraw(Canvas canvas, Paint globalPaint, int status) {
    if (status == STATUS_NOT_STARTED) {
      return;
    }
    canvas.drawText(Integer.toString(currentScore), width / 2, marginTop,
        sharedPaint);
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

  public int getCurrentScore() {
    return currentScore;
  }

  public void setCurrentScore(int currentScore) {
    this.currentScore = currentScore;
  }

}
