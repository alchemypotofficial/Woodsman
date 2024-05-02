package com.alchemy.woodsman.core.graphics;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Camera {
    private OrthographicCamera camera;
    private Vector2 position;
    public float zoom;

    public Camera (Vector2 position, float zoom) {
        this.zoom = zoom;

        camera = new OrthographicCamera();

        camera.setToOrtho(false, 896 * zoom, 512 * zoom);
        camera.update();

        setPosition(position);
    }

    public void update() {
        camera.update();
    }

    public void render(SpriteBatch batch) {
        batch.setProjectionMatrix(camera.combined);
    }

    public void render(ShapeRenderer shapeRenderer) {shapeRenderer.setProjectionMatrix(camera.combined);}

    public Vector2 toCameraPosition(Vector2 position) {
        Vector3 worldPosition = camera.unproject(new Vector3(position.x, position.y, 0));

        return new Vector2(worldPosition.x, worldPosition.y);
    }

    public void setPosition(Vector2 position) {
        this.position = position;

        camera.position.set(position.x * 16, position.y, 0);
        camera.update();
    }

    public void setPosition(float x, float y) {
        position.x = x;
        position.y = y;

        camera.position.set(position.x, position.y, 0);
        camera.update();
    }

    public void setZoom(float zoom) {
        this.zoom = zoom;

        camera.setToOrtho(false, 896 * zoom, 512 * zoom);
        camera.update();
    }

    public Vector2 getPosition() { return this.position; }

    public float getZoom() { return this.zoom; }
}
