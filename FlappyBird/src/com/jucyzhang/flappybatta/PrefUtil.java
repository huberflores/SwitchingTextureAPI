package com.jucyzhang.flappybatta;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class PrefUtil {

  private static final String PREF_DEFAULT = "com.jucyzhang.flappybatta.PREF_DEFAULT";
  private static final String HIGHEST_SCORE = "highest_score";

  public static int getHighestScore(Context context) {
    SharedPreferences p = context.getSharedPreferences(PREF_DEFAULT,
        Context.MODE_PRIVATE);
    return p.getInt(HIGHEST_SCORE, 0);
  }

  public static void setHighestScore(Context context, int score) {
    SharedPreferences p = context.getSharedPreferences(PREF_DEFAULT,
        Context.MODE_PRIVATE);
    Editor e = p.edit();
    e.putInt(HIGHEST_SCORE, score);
    e.commit();
  }
}
