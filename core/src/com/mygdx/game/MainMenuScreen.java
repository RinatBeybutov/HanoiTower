package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.utils.TimeUtils;

public class MainMenuScreen implements Screen {

    final MyGdxGame game;
    OrthographicCamera camera;
    Texture textureButtonStart;
    Texture textureButtonExit;
    Texture textureButtonInfo;
    Texture Alex;
    Texture ChooseFive;
    Texture ChooseSix;
    Texture ChooseSeven;
    Texture ChooseEight;
    Texture ChooseNumberDisk;

    int returnX;
    int returnY;
    long timeBetween=0;
    Music hello;
    Music EditPiaf;
    Sound SoundButton;
    int NumberOfDisks = 5;
    long lastClickTime;
    float timeBetweenPressed=0.2f;
    int LeftCornerExitButtonX = 600, LeftCornerExitButtonY = 0,
            LeftCornerInfoButtonX=600,LeftCornerInfoButtonY=100;


    public MainMenuScreen(MyGdxGame game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false,800,600);

        textureButtonExit = new Texture(Gdx.files.internal("assets/buttonExit.png"));
        textureButtonInfo = new Texture(Gdx.files.internal("assets/buttonInfo.png"));
        textureButtonStart = new Texture(Gdx.files.internal("assets/buttonStart.png"));
        Alex = new Texture(Gdx.files.internal("assets/Alex.jpg"));
        ChooseFive = new Texture(Gdx.files.internal("assets/Choosefive.png"));
        ChooseSix = new Texture(Gdx.files.internal("assets/ChooseSix.png"));
        ChooseSeven = new Texture(Gdx.files.internal("assets/ChooseSeven.png"));
        ChooseEight = new Texture(Gdx.files.internal("assets/ChooseEight.png"));

        ChooseNumberDisk = new Texture(Gdx.files.internal("assets/ChooseNumberDisk.png"));

        hello = Gdx.audio.newMusic(Gdx.files.internal("assets/IamAlex.mp3"));
        EditPiaf = Gdx.audio.newMusic(Gdx.files.internal("assets/EditPiaf.mp3"));
        SoundButton = Gdx.audio.newSound(Gdx.files.internal("assets/SoundButton.mp3"));
        hello.play();

        EditPiaf.setLooping(true);
        timeBetween = TimeUtils.nanoTime();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();//сообщает камере, что нужно обновить матрицы
        returnX = Gdx.input.getX();
        returnY = Gdx.input.getY();
        game.batch.begin();  ///  НАЧАЛО ВЫВОДА

        game.font.setColor(Color.BLACK);
        game.batch.draw(textureButtonStart,600,200);
        game.batch.draw(textureButtonExit,LeftCornerExitButtonX,LeftCornerExitButtonY);
        game.batch.draw(textureButtonInfo,LeftCornerInfoButtonX,LeftCornerInfoButtonY);
        game.batch.draw(Alex,0,-5);
        game.font.draw(game.batch,"N = " + NumberOfDisks, 670,320);
        //game.font.draw(game.batch,"getX  "+returnX,650,150);
        //game.font.draw(game.batch,"getY  "+returnY,650,135);


        game.batch.draw(ChooseFive,600,400);
        game.batch.draw(ChooseSix,700,400);
        game.batch.draw(ChooseSeven,600,350);
        game.batch.draw(ChooseEight,700,350);

        game.batch.draw(ChooseNumberDisk,450,430);

        //if(TimeUtils.nanoTime() -timeBetween>2*Math.pow(10,9))
        //{
          //  game.font.draw(game.batch,"getX  "+returnX,50,50);
         //   game.font.draw(game.batch,"getY  "+returnY,50,35);
        //}

        if(   !hello.isPlaying() && !EditPiaf.isPlaying() &&  TimeUtils.nanoTime() -timeBetween>0.2*Math.pow(10,9) )
        {
            EditPiaf.play();
        }

        game.batch.end();  ///  конец вывода

        if(Gdx.input.isTouched()&&returnX>600&&returnX<680 && returnY<80&&returnY>50)
        {
            NumberOfDisks = 5;
            if(TimeUtils.nanoTime()-lastClickTime>timeBetweenPressed*Math.pow(10,9))
            {
                lastClickTime=TimeUtils.nanoTime();
                SoundButton.play();
            }
        }

        if(Gdx.input.isTouched()&&returnX>700&&returnX<780 && returnY<80&&returnY>50)
        {
            NumberOfDisks = 6;
            if(TimeUtils.nanoTime()-lastClickTime>timeBetweenPressed*Math.pow(10,9))
            {
                lastClickTime=TimeUtils.nanoTime();
                SoundButton.play();
            }
        }

        if(Gdx.input.isTouched()&&returnX>600&&returnX<680 && returnY<130&&returnY>100) {
            NumberOfDisks = 7;
            if(TimeUtils.nanoTime()-lastClickTime>timeBetweenPressed*Math.pow(10,9))
            {
                lastClickTime=TimeUtils.nanoTime();
                SoundButton.play();
            }
        }

        if(Gdx.input.isTouched()&&returnX>700&&returnX<780 && returnY<130&&returnY>100)
        {
            NumberOfDisks = 8;
            if(TimeUtils.nanoTime()-lastClickTime>timeBetweenPressed*Math.pow(10,9))
            {
                lastClickTime=TimeUtils.nanoTime();
                SoundButton.play();
            }
        }

        if(Gdx.input.isTouched()&&returnX>600&&returnX<770 && returnY<275&&returnY>200)
        {
           EditPiaf.stop();
           hello.stop();
           SoundButton.play();
           dispose();
            game.setScreen(new GameScreen(game, NumberOfDisks));

        }

        if(Gdx.input.isTouched()&&returnX>LeftCornerExitButtonX&&returnX<LeftCornerExitButtonX+170 && returnY<480 - LeftCornerExitButtonY  && returnY> 480 - LeftCornerExitButtonY -77)
        {
            dispose();
            Gdx.app.exit();
        }

        if(Gdx.input.isTouched()&&returnX>LeftCornerInfoButtonX&&returnX<LeftCornerInfoButtonX+173 && returnY<480 - LeftCornerInfoButtonY  && returnY> 480 - LeftCornerInfoButtonY -77)
        {
            EditPiaf.stop();
            hello.stop();
            SoundButton.play();
            dispose();
            game.setScreen(new InfoScreen(game));
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

   //System.out.println("Dispose menu");

   textureButtonStart.dispose();
      textureButtonExit.dispose();
      Alex.dispose();
      ChooseFive.dispose();
      ChooseSix.dispose();
      ChooseSeven.dispose();
      ChooseEight.dispose();
      ChooseNumberDisk.dispose();

      hello.dispose();
      EditPiaf.dispose();
      SoundButton.dispose();
      //game.batch.dispose();
      //*/


      /*
        final MyGdxGame game;
        Texture textureButtonStart;
    Texture textureButtonExit;
    Texture Alex;
    Texture ChooseFive;
    Texture ChooseSix;
    Texture ChooseSeven;
    Texture ChooseEight;
    Texture ChooseNumberDisk;
    SpriteBatch batch;
    Music hello;
    Music EditPiaf;
    Sound SoundButton;
    BitmapFont font;
         */
    }
}
