package com.theironyard.minicraft;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Minicraft extends ApplicationAdapter {
    final int WIDTH = 100;
    final int HEIGHT = 100;
    final float MAX_VELOCITY = 300;
    //final float WORLD_WIDTH = 560;
    //final float WORLD_HEIGHT = 380;

    FitViewport viewport;

    SpriteBatch batch;
    TextureRegion down, up, right, left;

    float x = 0;
    float y = 0;
    float xv, yv;

    int direction = 3;


    @Override
    public void create () {
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        batch = new SpriteBatch();

        Texture tiles = new Texture("tiles.png");
        TextureRegion[][] grid = TextureRegion.split(tiles, 16, 16);
        down = grid[6][0];
        up = grid[6][1];
        right = grid[6][3];
        left = new TextureRegion(right);
        left.flip(true, false);
    }

    @Override
    public void render () {

        move();

        Gdx.gl.glClearColor(0, 0.5f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        if (direction == 1) {
            batch.draw(up, x, y, WIDTH, HEIGHT);
        }

        else if (direction == 2) {
            batch.draw(right, x, y, WIDTH, HEIGHT);
        }

        else if (direction == 3) {
            batch.draw(down, x, y, WIDTH, HEIGHT);
        }

        else if (direction == 4){
            batch.draw(left, x, y, WIDTH, HEIGHT);
        }
        batch.end();
    }

    float decelerate(float velocity) {
        float deceleration = 0.9f; //the closer to one the slower the deceleration
        velocity *= deceleration;
        if (Math.abs(velocity) < 1) {
            velocity = 0;
        }
        return velocity;
    }

    void move(){
        if (Gdx.input.isKeyPressed(Input.Keys.UP)){
            yv = MAX_VELOCITY;
            direction = 1;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            yv = MAX_VELOCITY * -1;
            direction = 3;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            xv = MAX_VELOCITY;
            direction = 2;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            xv = MAX_VELOCITY * -1;
            direction = 4;
        }

        y += yv * Gdx.graphics.getDeltaTime();
        x += xv * Gdx.graphics.getDeltaTime();


//        if (y < 0){
//            y = 0;
//        }
//
//        else if (y > WORLD_HEIGHT){
//            y = WORLD_HEIGHT;
//        }


//        if (y < -100){
//            y = WORLD_HEIGHT + 100;
//        }
//
//        else if (y > WORLD_HEIGHT + 100){
//            y = -100;
//        }

        if (y < -100){
            y = viewport.getWorldHeight() + 50;
        }

        else if (y > viewport.getWorldHeight() + 50){
            y = -100;
        }

//        if (x < -10) {
//            x = -10;
//        }
//
//        else if (x > WORLD_WIDTH) {
//            x = WORLD_WIDTH;
//        }

        if (x < -100) {
            x = viewport.getWorldWidth();
        }

        else if (x > viewport.getWorldWidth()) {
            x = -100;
        }


//        if (x < -90) {
//            x = WORLD_WIDTH + 70;
//        }
//
//        else if (x > WORLD_WIDTH + 70){
//            x = -90;
//        }


        yv = decelerate(yv);
        xv = decelerate(xv);
    }
}
