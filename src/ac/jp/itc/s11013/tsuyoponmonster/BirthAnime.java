
package ac.jp.itc.s11013.tsuyoponmonster;

import java.io.ByteArrayOutputStream;
import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BirthAnime extends Activity implements AnimationListener {

    private final int FP = ViewGroup.LayoutParams.FILL_PARENT;
    private final int WC = ViewGroup.LayoutParams.WRAP_CONTENT;
    ViewGroup relativeLayout;
    ViewGroup frontLayout;
    ViewGroup backLayout;
    TextView tv;
    // Button nextButton;
    Intent intent;
    Drawable draw;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        setContentView(R.layout.birth_anime);

        frontLayout = (ViewGroup) findViewById(R.id.front_layout);
        backLayout = (ViewGroup) findViewById(R.id.back_layout);
        relativeLayout = (ViewGroup) findViewById(R.id.relative_layout);
        tv = (TextView) findViewById(R.id.text_view);// テキストセット

        // 画像を呼び出す
        ImageView image1 = new ImageView(this);
        image1.setImageResource(R.drawable.egg);
        ImageView image2 = new ImageView(this);
        // 画像をランダムで取得
        TypedArray images = getResources().obtainTypedArray(R.array.monster_image);
        Random rand = new Random();
        draw = images.getDrawable(rand.nextInt(14));
        image2.setImageDrawable(draw);

        // linearLayoutに画像をセット
        frontLayout.addView(image1, createParam(WC, WC));
        backLayout.addView(image2, createParam(WC, WC));

        Animation anim_alpha = AnimationUtils.loadAnimation(this, R.anim.alpha);// alphaアニメーションを呼び出す
        image1.startAnimation(anim_alpha);// アニメーションを画像に適用

        Animation anim_zoom = AnimationUtils.loadAnimation(this, R.anim.zoom);// zoomアニメーションを呼び出す
        image2.startAnimation(anim_zoom);

        anim_zoom.setAnimationListener(this);

    }

    // ImageViewの属性設定
    private LinearLayout.LayoutParams createParam(int w, int h) {
        return new LinearLayout.LayoutParams(w, h);
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    // zoomアニメーション終了時の処理
    public void onAnimationEnd(Animation animation) {

        tv.setText("モンスターが生まれました");
        relativeLayout.setClickable(true);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
        }
        intent = new Intent(this, Birth.class);
        // drawをいったんBitmapに変換
        Bitmap bitmap = ((BitmapDrawable) draw).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        intent.putExtra("image", b);
        startActivity(intent);
        finish();
    }
}
