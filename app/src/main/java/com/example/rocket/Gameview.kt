package com.example.rocket

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import android.view.View

class Gameview(var c: Context, var gameTask: GameTask) : View(c) {
    private var mypaint: Paint? = null
    private var speed = 1
    private var time = 0
    private var score = 0
    private var myrocktPosition = 0
    private val otherrockts = ArrayList<HashMap<String, Any>>()

    var viewWidth = 0
    var viewHeight = 0

    init {
        mypaint = Paint()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        viewWidth = this.measuredWidth
        viewHeight = this.measuredHeight

        if (time % 800 < 10 + speed) {
            val map = HashMap<String,Any>()
            map["lane"] = (0..2).random()
            map["startTime"] = time
            otherrockts.add(map)
        }
        time += 10 + speed
        val rocktWidth = viewWidth / 5
        val rocktHeight = rocktWidth + 10
        mypaint!!.style = Paint.Style.FILL
        val d = resources.getDrawable(R.drawable.rocket_3,null)

        d.setBounds(
            myrocktPosition * viewWidth / 3 + viewWidth / 15 + 25,
            viewHeight-2- rocktHeight,
            myrocktPosition * viewWidth / 3 + viewWidth / 15 + rocktWidth -25,
            viewHeight -2
        )

        d.draw(canvas!!)
        mypaint!!.color = Color.GREEN
        var highScore = 0

        for (i in otherrockts.indices) {
            try {
                val rocktx =
                    otherrockts[i]["lane"] as Int * viewWidth / 3 + viewWidth / 15
                var rocktY = time - (otherrockts[i]["startTime"] as Int)
                if (rocktY > viewHeight + rocktHeight) {
                    otherrockts.removeAt(i)
                    score++
                    speed = 1 + Math.abs(score / 8)
                } else {
                    val d = resources.getDrawable(R.drawable.planet, null)

                    d.setBounds(
                        rocktx + 25, rocktY - rocktHeight, rocktx + rocktWidth - 25, rocktY
                    )


                    d.draw(canvas)
                    if (otherrockts[i]["lane"] as Int == myrocktPosition) {
                        if (rocktY > viewHeight - 2 - rocktHeight && rocktY < viewHeight - 2) {
                            gameTask.closeGame(score)
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        mypaint!!.color = Color.WHITE
        mypaint!!.textSize = 40f
        canvas.drawText("Score : $score", 80f, 80f, mypaint!!)
        canvas.drawText("Speed : $speed", 80f, 160f, mypaint!!)
        invalidate()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event!!.action) {
            MotionEvent.ACTION_DOWN -> {
                val x1 = event.x
                if (x1 < viewWidth / 2) {
                    if (myrocktPosition > 0) {
                        myrocktPosition--
                    }
                }
                if (x1 > viewWidth / 2) {
                    if (myrocktPosition < 2) {
                        myrocktPosition++
                    }
                }
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
            }
        }
        return true
    }



    fun pauseGame() {
    }

    fun resumeGame() {


    }
}
