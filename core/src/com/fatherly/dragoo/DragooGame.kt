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
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.Timer
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.badlogic.gdx.utils.viewport.Viewport
import com.fatherly.dragoo.actors.DragooActor


class DragooGame : ApplicationAdapter() {
    lateinit var stage: Stage


    override fun create() {

        var viewport = ScreenViewport()
        stage = Stage(viewport)

        var dragooActor = DragooActor()

        stage.addActor(dragooActor)
        stage.keyboardFocus = stage.actors.first()

        Gdx.input.setInputProcessor(stage)
    }

    override fun render() {
        Gdx.gl.glClearColor(1f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        stage.act(Gdx.graphics.getDeltaTime())
        stage.draw()
    }

}
