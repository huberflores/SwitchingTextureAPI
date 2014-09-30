package com.jucyzhang.flappybatta;

import android.content.Context;
import android.text.TextPaint;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

public class ViewUtil {

  private static int screenWidth;
  private static int screenHeight;
  private static float density;
  private static int densityDpi;

  private ViewUtil() {
  }

  public static int dipToPx(Context context, float dp) {
    return (int) (dp * ViewUtil.getScreenDensity(context) + 0.5f);
  }

  public static int dipResourceToPx(Context context, int resourceId) {
    return (int) context.getResources().getDimension(resourceId);
  }

  public static float dipResourceToFloat(Context context, int resourceId) {
    return context.getResources().getDimension(resourceId);
  }

  public static float dipFractionToFloat(Context context, int resourceId) {
    return context.getResources().getFraction(resourceId, 1, 1);
  }

  public static void moveToNextFocus(View view) {
    if (view != null) {
      View next = view.focusSearch(View.FOCUS_FORWARD);
      if (next != null) {
        next.requestFocus();
      }
    }
  }

  public static float getTextWidth(float textSize, String text) {
    TextPaint paint = new TextPaint();
    paint.setTextSize(textSize);
    return paint.measureText(text);
  }

  public static int getScreenDensityDpi(Context context) {
    if (densityDpi > 0) {
      return densityDpi;
    } else {
      initWindowProp(context);
      return densityDpi;
    }
  }

  public static float getScreenDensity(Context context) {
    if (density > 0) {
      return density;
    } else {
      initWindowProp(context);
      return density;
    }
  }

  public static int getScreenWidth(Context context) {
    if (screenWidth > 0) {
      return screenWidth;
    } else {
      initWindowProp(context);
      return screenWidth;
    }
  }

  public static int getScreenHeight(Context context) {
    if (screenHeight > 0) {
      return screenHeight;
    } else {
      initWindowProp(context);
      return screenHeight;
    }
  }

  private static void initWindowProp(Context context) {
    WindowManager manager = (WindowManager) context
        .getSystemService(Context.WINDOW_SERVICE);
    DisplayMetrics metric = new DisplayMetrics();
    manager.getDefaultDisplay().getMetrics(metric);
    screenWidth = metric.widthPixels;
    screenHeight = metric.heightPixels;
    density = metric.density;
    densityDpi = metric.densityDpi;
  }
}
