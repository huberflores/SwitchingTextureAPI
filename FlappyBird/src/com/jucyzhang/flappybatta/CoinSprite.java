package com.jucyzhang.flappybatta;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;

public class CoinSprite implements Sprite {

  private static final int MIN_ALPHA = 150;
  private Drawable coin;
  private int coinWidth;
  private int width;
  private int currentY;
  private float currentX;
  private float speed;
  private boolean getPoint = false; 
  private int ALPHA_SPEED = 3;
  private int currentAlpha = MIN_ALPHA;

  public CoinSprite(Context context, Drawable coin) {
    this.coin = coin;
    Resources res = context.getResources();
    speed = res.getDimension(R.dimen.coin_speed);
    coinWidth = (int) res.getDimension(R.dimen.coin_width);
    int height = ViewUtil.getScreenHeight(context);
    width = ViewUtil.getScreenWidth(context);
    currentY = height / 4 + RANDOM.nextInt(height / 2) - coinWidth / 2;
    currentX = width + coinWidth;
  }

  @Override
  public void onDraw(Canvas canvas, Paint globalPaint, int status) {
    if (status == STATUS_NOT_STARTED) {
      return;
    }
    if (status == STATUS_NORMAL) {
      currentX -= speed;
      currentAlpha += ALPHA_SPEED;
      if (currentAlpha > 255) {
        currentAlpha = 255;
        ALPHA_SPEED = -ALPHA_SPEED;
      } else if (currentAlpha < MIN_ALPHA) {
        currentAlpha = MIN_ALPHA;
        ALPHA_SPEED = -ALPHA_SPEED;
      }
    }
    coin.setAlpha(currentAlpha);
    coin.setBounds((int) currentX, currentY, (int) (currentX + coinWidth),
        currentY + coinWidth);
    coin.draw(canvas);
  }

  @Override
  public boolean isAlive() {
    return !getPoint && (currentX + coinWidth > 0);
  }

  @Override
  public boolean isHit(Sprite sprite) {
    if (!getPoint && sprite instanceof BattaSprite) {
      BattaSprite b = (BattaSprite) sprite;
      int bTop = b.getHitTop();
      int bBottom = b.getHitBottom();
      int bRight = b.getHitRight();
      int bLeft = b.getHitLeft();
      int left = (int) currentX;
      int right = (int) (currentX + coinWidth);
      int top = currentY;
      int bottom = currentY + coinWidth;
      getPoint = ((bRight > left && bRight < right) || (bLeft > left && bLeft < right))
          && ((bTop > top && bTop < bottom) || (bBottom > top && bBottom < bottom));
    }
    return false;
  }

  @Override
  public int getScore() {
    return getPoint ? 1 : 0;
  }

}
