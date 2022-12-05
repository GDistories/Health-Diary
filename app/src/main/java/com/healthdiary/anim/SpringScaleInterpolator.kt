package com.healthdiary.anim

import android.view.animation.Interpolator

class SpringScaleInterpolator(
    //弹性因数
    private var factor: Float
) : Interpolator {

    override fun getInterpolation(input: Float): Float {
        return (Math.pow(
            2.0,
            (-10 * input).toDouble()
        ) * Math.sin((input - factor / 4) * (2 * Math.PI) / factor) + 1).toFloat()
    }

}