package com.mygdx.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;


import java.awt.*;
import java.text.DecimalFormat;
import java.util.Random;

public class GameScreen implements Screen {

	MyGdxGame game;
	SpriteBatch batch;
	Texture pillar1;
	Texture buttonMenu;
	OrthographicCamera camera;

    LightBox light = new LightBox();
    Box array[] = new Box[8];
    Music BackMusic;
    Sound Success1; //какая прелесть
    Sound Success2; // yeeeehaaaa
	Music Success3; // mybeat
	Sound Success4; // ohhh eeee
	Sound Success5; //  eeee ээээ ееее
	Sound UpAndDown;

	Sound Ow1,Ow2,Ow3,Ow4,Ow5;

	Box active;
    BitmapFont font;

    int NumberOfStep=0;
    int NumberOfDisks;
	boolean uped=false;
	long lastMoveTime;
	long HeroTime;
    float timeBetweenPressed=0.2f;
    Random random = new Random();
	long HeroTimeEnd=0;
	Sound SoundButton;

	public GameScreen(MyGdxGame game, int NumberOfDisks) {

		this.game = game;
		this.NumberOfDisks=NumberOfDisks;
		batch = new SpriteBatch();
		pillar1 = new Texture(Gdx.files.internal("assets/pillar.png"));
		buttonMenu = new Texture(Gdx.files.internal("assets/buttonMenu.png"));

		font = new BitmapFont();
		font.setColor(1f, 0f, 0f, 1f);

		BackMusic = Gdx.audio.newMusic(Gdx.files.internal("assets/BackSound.mp3"));
		BackMusic.setLooping(true);
		BackMusic.setVolume(0.3f);
		BackMusic.play(); // ВКЛЮЧИ МУЗЫКУ!!!!

		Success1 = Gdx.audio.newSound(Gdx.files.internal("assets/Good1.mp3"));
        Success2 = Gdx.audio.newSound(Gdx.files.internal("assets/Good2.mp3"));
        Success3 = Gdx.audio.newMusic(Gdx.files.internal("assets/Good3.mp3"));
		Success4 = Gdx.audio.newSound(Gdx.files.internal("assets/Good4.mp3"));
		Success5 = Gdx.audio.newSound(Gdx.files.internal("assets/Good5.mp3"));
		UpAndDown = Gdx.audio.newSound(Gdx.files.internal("assets/UpAndDown.mp3"));

		Ow1 = Gdx.audio.newSound(Gdx.files.internal("assets/Ow1.mp3"));
		Ow2 = Gdx.audio.newSound(Gdx.files.internal("assets/Ow2.mp3"));
		Ow3 = Gdx.audio.newSound(Gdx.files.internal("assets/Ow3.mp3"));
		Ow4 = Gdx.audio.newSound(Gdx.files.internal("assets/Ow4.mp3"));
		Ow5 = Gdx.audio.newSound(Gdx.files.internal("assets/Ow5.mp3"));
		SoundButton = Gdx.audio.newSound(Gdx.files.internal("assets/SoundButton.mp3"));
		camera = new OrthographicCamera();
		camera.setToOrtho(true, 800, 480);

		light.perimetr = new Rectangle();                    //инициализирую прямоугольник
		light.perimetr.setLocation(100+72/2-15/2,85);   // устанавливаю положение прямоугольника
		light.perimetr.setSize(15,10);            //устанавливаю размеры
		light.picture = new Texture(Gdx.files.internal("assets/x0.png"));      //задаю текстуру

		active = new Box();
		active.perimetr = new Rectangle();
        active = null;

        for(int i = 0; i<NumberOfDisks;i++) {//инициализимрую элементы массива классов всех дисков
			array[i] = new Box();
			array[i].perimetr = new Rectangle();
			String temp ="assets/x"+(i+1)+".png"; //x1.png
			array[i].picture = new Texture(temp);
		    array[i].perimetr.setLocation(100+ 72/2 -85/2 + i*5,100 + 15 +i*15);
			array[i].perimetr.setSize(85-i*10,10);
		}
         HeroTime = TimeUtils.nanoTime();

	}

    public class LightBox {
		boolean first=true, second=false, third=false;// first - true тк все сначала в левом столбце
		Rectangle perimetr;
		Texture picture;
	}

	public class  Box extends LightBox{

		boolean isTopNotCatched=false;  //является ли верхним неподнятым?
		boolean isActive;               //поднят ли бокс для перетаскивания
	}

	public Box HigestFirst(Box ... temp)
	{
		Box Max = new Box();
		Max = null;
		int max=0;
		for(int i=0; i<NumberOfDisks;i++)
		{
			if(!temp[i].isActive && temp[i].first && temp[i].perimetr.y>max) //если верхний и находится в первом стобце
			{
				max = temp[i].perimetr.y;
				Max = temp[i];
			}
		}
	return Max;
	}

	public Box HigestSecond(Box ... temp)
	{
		Box Max = new Box();
		Max = null;
		int max=0;
		for(int i=0; i<NumberOfDisks;i++)
		{
			if(!temp[i].isActive && temp[i].second && temp[i].perimetr.y>max) //если верхний и находится в первом стобце
			{
				max = temp[i].perimetr.y;
				Max = temp[i];
			}
		}
		return Max;
	}

	public Box HigestThird(Box ... temp)
	{
		Box Max = new Box();
		Max = null;
		int max=0;
		for(int i=0; i<NumberOfDisks;i++)
		{
			if(!temp[i].isActive && temp[i].third && temp[i].perimetr.y>max) //если верхний и находится в первом стобце
			{
				max = temp[i].perimetr.y;
				Max = temp[i];
			}
		}
		return Max;
	}

	public boolean IfPrintCongritulations(Box ... temp)
	{
		boolean SecondFull=true,ThirdFull=true;
		for (int i=0; i<NumberOfDisks;i++)
		{
			if (!temp[i].second ) SecondFull=false;
			if (!temp[i].third ) ThirdFull = false;
			//if (temp[i].first){SecondFull=false; ThirdFull = false;}
		}

		if((SecondFull || ThirdFull)&&active ==null)
		{
			return true;
			//font.draw(batch,"MY CONGRATULATIONS", 200,400);
			//BackMusic.pause();
			//Success.play(1.0f);
		}
		return false;
	}

    private void IfUpKeyPressed()
	{
		if(Gdx.input.isKeyPressed(Input.Keys.UP) && !uped) // при нажатии стрелки вверх
		{
			if(light.first && !uped) active = HigestFirst(array);

			if(light.second&& !uped) active = HigestSecond(array);

			if(light.third && !uped) active = HigestThird(array);
			if (active != null) {
				active.perimetr.y = 300;
				active.isActive = true;
				uped = true;
				UpAndDown.play();
			}
		}
	}

	private void IfDownKeyPressed()
	{
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN) && uped&&TimeUtils.nanoTime()-lastMoveTime>timeBetweenPressed*Math.pow(10,9))//при нажатии стрелки вниз
		{
			lastMoveTime=TimeUtils.nanoTime();
			Box temp2 = new Box();
			temp2.perimetr = new Rectangle();

			if(light.first && uped) temp2 = HigestFirst(array);

			if(light.second && uped) temp2 = HigestSecond(array);

			if(light.third && uped) temp2 = HigestThird(array);

			if(temp2 == null)
			{
				active.perimetr.y = 100+15;
				active.perimetr.x = light.perimetr.x + 15/2-active.perimetr.width/2;
				active.isActive = false;
				active = null;
				uped = false;
				UpAndDown.play();
				NumberOfStep++;
			}
			else if(active.perimetr.width<temp2.perimetr.width)
			{
				active.perimetr.y = temp2.perimetr.y + 15;
				active.perimetr.x = temp2.perimetr.x + temp2.perimetr.width / 2 - active.perimetr.width / 2;
				active.isActive = false;
				active = null;
				uped = false;
				UpAndDown.play();
				NumberOfStep++;
			}
			else if(active.perimetr.width>temp2.perimetr.width)
			{
				switch (random.nextInt(5)+1) {
					case 1:
						Ow1.play(1.0f);
						break;
					case 2:
						Ow2.play(1.0f);
						break;
					case 3:
						Ow3.play();
						break;
					case 4:
						Ow4.play();
						break;
					case 5:
						Ow5.play();
						break;

				}
			}

			if(IfPrintCongritulations(array))
			{
				HeroTimeEnd = TimeUtils.nanoTime()-HeroTime;
				BackMusic.pause();
				switch (random.nextInt(5)+1) {
					case 1:
						Success1.play(1.0f);
						break;
					case 2:
						Success2.play(1.0f);
						break;
					case 3:
						Success3.play();
						break;
					case 4:
						Success4.play();
						break;
					case 5:
						Success5.play();
						break;

				}
			}
		}
	}

	private void IfRightKeyPressed()
	{
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)&&TimeUtils.nanoTime()-lastMoveTime>timeBetweenPressed*Math.pow(10,9))
		{
			lastMoveTime=TimeUtils.nanoTime();
			if (active !=null && active.second && light.second)
			{
				active.perimetr.x += 200;
				active.second=false;
				active.third=true;

				light.perimetr.x+=200;
				light.second=false;
				light.third=true;

			}
			else if (light.second)
			{
				light.perimetr.x+=200;
				light.second=false;
				light.third=true;
			}

			if (active !=null && active.first && light.first) {
				active.perimetr.x += 200;
				active.first=false;
				active.second=true;

				light.perimetr.x+=200;
				light.first=false;
				light.second=true;

			}
			else if(light.first)
			{
				light.perimetr.x+=200;
				light.first=false;
				light.second=true;
			}
		}
	}

	private void IfLeftKeyPressed()
	{
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)&&TimeUtils.nanoTime()-lastMoveTime>timeBetweenPressed*Math.pow(10,9))  //InputAdapter.keyUp(Input.Keys.RIGHT))
		{
			lastMoveTime=TimeUtils.nanoTime();
			if (active !=null && active.second && light.second)
			{
				active.perimetr.x -= 200;
				active.second=false;
				active.first=true;

				light.perimetr.x -= 200;
				light.second=false;
				light.first=true;

			}
			else if(light.second)
			{
				light.perimetr.x -= 200;
				light.second=false;
				light.first=true;
			}

			if (active!=null && active.third && light.third) {
				active.perimetr.x -= 200;
				active.third=false;
				active.second=true;

				light.perimetr.x -= 200;
				light.third=false;
				light.second=true;

			}
			else if(light.third)
			{
				light.perimetr.x -= 200;
				light.third=false;
				light.second=true;
			}
		}
	}

	private void DrawEvethingOnScreen()
	{
		batch.draw(pillar1,100,100); //game.
		batch.draw(pillar1,300,100);  //game.
		batch.draw(pillar1,500,100); //game.
        batch.draw(buttonMenu,250,20);
		DecimalFormat format = new DecimalFormat("#000.0");
		if (HeroTimeEnd/Math.pow(10,9)==0)
			font.draw(batch,"Real Time =  " + format.format((TimeUtils.nanoTime()-HeroTime)/Math.pow(10,9)) + " cek ",550,380);
		else
			font.draw(batch,"Real Time =  " + format.format((HeroTimeEnd/Math.pow(10,9))) + " cek ",550,380);
		font.draw(batch,"End Time =   " +  format.format((HeroTimeEnd/Math.pow(10,9))),550,360);
		font.draw(batch, "Step Number = " + NumberOfStep, 550,340);  //%.10f
         //game. game. game.
		if(IfPrintCongritulations(array))
		{
			font.draw(batch,"MY CONGRATULATIONS", 200,400);

		}

		batch.draw(light.picture,light.perimetr.x,light.perimetr.y);
		for (int i=0; i<NumberOfDisks; i++)
		{
			batch.draw(array[i].picture,array[i].perimetr.x,array[i].perimetr.y);
		}
	}

	@Override
	public void show() {
		//здесь должна быть музыка
	}

	@Override
	public void render (float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();//сообщает камере, что нужно обновить матрицы

		batch.begin();  ///  НАЧАЛО ВЫВОДА
		DrawEvethingOnScreen();
		batch.end();    ///  КОНЕЦ ВЫВОДА

        IfUpKeyPressed();
        IfDownKeyPressed();
        IfRightKeyPressed();
        IfLeftKeyPressed();

        if(Gdx.input.isTouched()&&Gdx.input.getX()>250&&Gdx.input.getX()<420 && Gdx.input.getY()<460&&Gdx.input.getY()>407)
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
	public void dispose () {
		batch.dispose();//дописать диспозу всех
        BackMusic.dispose();
        font.dispose();
	}
}
