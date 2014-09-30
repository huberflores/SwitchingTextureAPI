package com.jucyzhang.flappybatta;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;

public class HintSprite implements Sprite {
  private boolean alive = true;
  private Drawable hint;
  private int width;
  private int height;
  private int hintWidth;
  private int hintHeight;

  public HintSprite(Context context) {
    Resources res = context.getResources();
    hint = res.getDrawable(R.drawable.bg_hint);
    height = ViewUtil.getScreenHeight(context);
    width = ViewUtil.getScreenWidth(context);
    hintWidth = ViewUtil.dipResourceToPx(context, R.dimen.hint_width);
    hintHeight = hintWidth * hint.getIntrinsicHeight()
        / hint.getIntrinsicWidth();
    hint.setBounds(width / 2 - hintWidth / 2, height / 2 - hintHeight / 2,
        width / 2 + hintWidth / 2, height / 2 + hintHeight / 2);
  }

  @Override
  public void onDraw(Canvas canvas, Paint globalPaint, int status) {
    alive = (status == STATUS_NOT_STARTED);
    if (alive) {
      hint.draw(canvas);
    }
  }

  @Override
  public boolean isAlive() {
    return alive;
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
