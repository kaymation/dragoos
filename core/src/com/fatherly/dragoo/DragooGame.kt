package com.fatherly.dragoo

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.utils.Timer
import javax.xml.soap.Text


class DragooGame : ApplicationAdapter() {
    lateinit var batch: SpriteBatch
    lateinit var spriteSheet: Texture
    lateinit var sprite: Sprite
    lateinit var timer: Timer
    lateinit var dragooAnimation: Animation<TextureRegion>
    lateinit var currentFrame: TextureRegion

    var stepCount: Int = 3
    var stepWidth: Int = 67

    var stateTime = 0f

    var walking = 0


    override fun create() {
        batch = SpriteBatch()
        spriteSheet = Texture("spritesheet_dragoo.png")
        sprite = Sprite(spriteSheet, 0, 0, 67, 81)

        var dragooFrames = com.badlogic.gdx.utils.Array<TextureRegion>()
        dragooFrames.setSize(3)
        var tmpFrames = TextureRegion.split(spriteSheet, 67, 81)[0]
        for (i in 0..2) {
            dragooFrames[i] = tmpFrames[i]
        }

        dragooAnimation = Animation<TextureRegion>(0.25f, dragooFrames)
        timer = Timer()
        timer.start()
        sprite.setPosition(200f, 200f)

        currentFrame = dragooFrames[0]

    }

    override fun render() {
        Gdx.gl.glClearColor(1f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        stateTime += Gdx.graphics.deltaTime

        // atrols
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            sprite.translateX(-3.0f);
            stepSprite()
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            sprite.translateX(3.0f);
            stepSprite()
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            sprite.translateY(3.0f);
            stepSprite()
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            sprite.translateY(-3.0f);
            stepSprite()
        }
        else walking = 0

        if (walking == 1) {
            currentFrame = dragooAnimation.getKeyFrame(stateTime, true)
        }

        batch.begin()
//        batch.draw(currentFrame, 300f, 300f)
        sprite.setRegion(currentFrame)
        sprite.draw(batch)
        batch.end()
    }

    override fun dispose() {
        batch.dispose()
        spriteSheet.dispose()
    }

    fun stepSprite() {
        walking = 1
        var currentX = sprite.regionX
        var nextX = (currentX + stepWidth) % (stepWidth * (stepCount))

        sprite.setRegion(nextX, 0, 67, 81 )
    }

    fun canStep() {

    }


}
