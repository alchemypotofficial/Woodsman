package com.alchemy.woodsman.core.utilities.physics;

import com.alchemy.woodsman.Game;
import com.alchemy.woodsman.core.Settings;
import com.alchemy.woodsman.core.graphics.Renderer;
import com.alchemy.woodsman.core.graphics.Wireframe;
import com.badlogic.gdx.math.Vector2;

public class PhysicsBody {

    public Vector2 position;
    public Vector2 velocity;
    public float mass;

    private Vector2 netForce;

    public float friction;

    private Box collider;

    public boolean hasWorldCollision;
    public boolean hasBodyCollision;
    public boolean isColliding;

    public PhysicsBody(Box collider, float mass, Vector2 position) {
        this.collider = collider;
        this.mass = mass;
        this.position = position;
        this.friction = 1f;

        velocity = new Vector2();
        netForce = new Vector2();

        hasWorldCollision = true;
        hasBodyCollision = true;

        isColliding = false;
    }

    public PhysicsBody(Box collider, float mass, Vector2 position, float friction) {
        this.collider = collider;
        this.mass = mass;
        this.position = position;
        this.friction = friction;

        velocity = new Vector2();
        netForce = new Vector2();

        hasWorldCollision = true;
        hasBodyCollision = true;

        isColliding = false;
    }

    public final void render(Renderer renderer) {
        Settings settings = Game.getSettings();

        if (settings.isHitboxShown) {
            Box bodyOrigin = new Box(position.x - 0.0625f, position.y - 0.0625f, 0.125f, 0.125f);
            Box bodyCollider = getCollider();

            renderer.addWorldWireframe(new Wireframe(bodyOrigin, Wireframe.WHITE));

            if (isColliding) {
                renderer.addWorldWireframe(new Wireframe(bodyCollider, Wireframe.GREEN));
            }
            else {
                renderer.addWorldWireframe(new Wireframe(bodyCollider, Wireframe.YELLOW));
            }
        }
    }

    public final void addForce(Vector2 force) {
        netForce.x += force.x * Game.getDeltaTime();
        netForce.y += force.y * Game.getDeltaTime();
    }

    public final void setCollider(Box collider) {
        this.collider = collider;
    }

    public final void setNetForce(Vector2 netForce) {
        this.netForce = netForce;
    }

    public final Box getCollider() {
        return new Box(new Box(((position.x + collider.x) - (collider.width / 2)), ((position.y + collider.y) - (collider.height / 2)), collider.width, collider.height));
    }

    public final Box getRawCollider() {
        return this.collider;
    }

    public final Vector2 getNetForce() {
        return new Vector2(this.netForce);
    }
}
