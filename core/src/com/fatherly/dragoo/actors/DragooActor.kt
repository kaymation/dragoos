package com.fatherly.dragoo.actors

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.*
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.Input

import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction

/**
 * Created by kevinquigley on 11/3/17.
 * Actor Class for Dragoo
 */

class DragooActor : Actor() {
    var batch = SpriteBatch()
    var spriteSheet = Texture("spritesheet_dragoo.png")
    var sprite: Sprite
    var dragooAnimations =  HashMap<Facing, Animation<TextureRegion>>()
    var currentFrame: TextureRegion
    var deltaX = 0f
    var deltaY = 0f

    var stateTime = 0f

    enum class Facing {
        LEFT, RIGHT, DOWN, UP
    }

    var moving = false

    var facing = Facing.DOWN

    init {
        sprite = Sprite(spriteSheet, 0, 0, 67, 81)

        var tmpFrames = TextureRegion.split(spriteSheet, 67, 81)
        var flatFrames = com.badlogic.gdx.utils.Array<TextureRegion>()
        for (row in tmpFrames) {
            for (frame in row) {
                flatFrames.add(frame)
            }
        }

        var dragooDownAnimation = createAnim(flatFrames, 0, 2)
        var dragooUpAnimation = createAnim(flatFrames, 3, 5)
        var dragooLeftAnimation = createAnim(flatFrames, 6, 7)
        var dragooRightAnimation = createAnim(flatFrames, 8, 9)

        dragooAnimations.put(Facing.DOWN, dragooDownAnimation)
        dragooAnimations.put(Facing.UP, dragooUpAnimation)
        dragooAnimations.put(Facing.LEFT, dragooLeftAnimation)
        dragooAnimations.put(Facing.RIGHT, dragooRightAnimation)

        sprite.setPosition(200f, 200f)
        setPosition(200f, 200f)


        currentFrame = dragooDownAnimation.getKeyFrame(0f)

        class KeyListen : InputListener() {

            override fun keyDown(event: InputEvent, keycode: Int): Boolean {
                moving = true

                when(keycode) {
                    Input.Keys.LEFT -> {
                        facing = Facing.LEFT
                        deltaX =  -5f

                    }
                    Input.Keys.RIGHT -> {
                        facing = Facing.RIGHT
                        deltaX = 5f

                    }
                    Input.Keys.UP -> {
                        facing = Facing.UP
                        deltaY = 5f

                    }
                    Input.Keys.DOWN -> {
                        facing = Facing.DOWN
                        deltaY = -5f
                    }
                    else -> {
                        moving = false

                    }
                }

                return true
            }

            override fun keyUp(event: InputEvent?, keycode: Int): Boolean {
                moving = false
                when(keycode) {
                    Input.Keys.LEFT-> {
                        deltaX -=  -5f

                    }
                    Input.Keys.RIGHT -> {
                        deltaX -= 5f

                    }
                    Input.Keys.UP -> {
                        deltaY -= 5f

                    }
                    Input.Keys.DOWN -> {
                        deltaY -= -5f
                    }
                }

                return true
            }
        }

        addListener(KeyListen())

    }

    override fun act(delta: Float) {
        var animation = dragooAnimations[facing]

        if (moving) {
            var moveAction = MoveByAction()
            moveAction.setAmount(deltaX, deltaY)
            this@DragooActor.addAction(moveAction)

            stateTime += Gdx.graphics.deltaTime

            if (animation != null) {
                currentFrame = animation.getKeyFrame(stateTime, true)
            }
        } else if (animation != null) {
            currentFrame = animation.getKeyFrame(0f)
        }

        sprite.setRegion(currentFrame)

        super.act(delta)
    }

    override fun draw(batch: Batch?, parentAlpha: Float) {
        sprite.draw(batch)
    }

    fun createAnim(tmpFrames: com.badlogic.gdx.utils.Array<TextureRegion>, start: Int, end: Int) : Animation<TextureRegion> {
        var frames = com.badlogic.gdx.utils.Array<TextureRegion>()

        frames.setSize(end - start + 1)

        for (i in 0 until frames.size) {
            frames[i] = tmpFrames[i + start]
        }
        print("**** creating anim with %d frames".format(frames.size))

        return Animation(0.25f, frames)
    }

    override fun positionChanged() {
        setPosition(x, y)
        sprite.setPosition(x,y)
        super.positionChanged()
    }
}

