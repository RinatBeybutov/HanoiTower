package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class InfoScreen implements Screen {

    MyGdxGame game;
    Texture textureFon;
    SpriteBatch batch;
    BitmapFont font;
    Sound SoundButton;

    public InfoScreen(MyGdxGame game)
    {
        this.game = game;
        textureFon = new Texture(Gdx.files.internal("assets/Fon.png"));
        batch = new SpriteBatch();
        font = new BitmapFont();
        SoundButton = Gdx.audio.newSound(Gdx.files.internal("assets/SoundButton.mp3"));
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(textureFon,0,0);
        //font.draw(batch,"X" + Gdx.input.getX(),650,135);
        //font.draw(batch,"Y" + Gdx.input.getY(),650,150);
        batch.end();


        if(Gdx.input.isTouched()&&Gdx.input.getX()>670&&Gdx.input.getX()<770 && Gdx.input.getY()<90&&Gdx.input.getY()>40)
        {
            game.setScreen(new MainMenuScreen(game));
            SoundButton.play();
            dispose();
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        textureFon.dispose();
         batch.dispose();
         font.dispose();
        SoundButton.dispose();
    }
}
