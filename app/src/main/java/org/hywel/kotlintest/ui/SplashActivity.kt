package org.hywel.kotlintest.ui

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.animation.AnticipateOvershootInterpolator
import kotlinx.android.synthetic.main.activity_splash.*
import org.hywel.kotlintest.R

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val tfAuthorFromAsset = Typeface.createFromAsset(assets, "fonts/Luminari.ttf")!!
        text_name.typeface = tfAuthorFromAsset

        startAnim()
    }

    /**
     * 执行动画
     */
    private fun startAnim() {
        //TextView
        val textAnim: ObjectAnimator = ObjectAnimator.ofFloat(text_name, "alpha", 0f, 1f)

        //ImageView
        val anim: ObjectAnimator = ObjectAnimator.ofFloat(image_avatar, "alpha", 0f, 1f)
        val anim1: ObjectAnimator = ObjectAnimator.ofFloat(image_avatar, "rotationY", 0f, 360f)

        val animSet: AnimatorSet = AnimatorSet()
        animSet.play(anim).with(anim1).with(textAnim)
        animSet.interpolator = AnticipateOvershootInterpolator()
        animSet.setDuration(2000).start()

        animSet.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(p0: Animator?) {
            }

            override fun onAnimationCancel(p0: Animator?) {
            }

            override fun onAnimationStart(p0: Animator?) {
            }

            override fun onAnimationEnd(p0: Animator?) {
                val intent = Intent(this@SplashActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        })
    }

}
