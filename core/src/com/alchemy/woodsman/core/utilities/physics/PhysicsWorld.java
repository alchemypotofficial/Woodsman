package com.alchemy.woodsman.core.utilities.physics;

import com.alchemy.woodsman.Game;
import com.alchemy.woodsman.core.utilities.BlockPosition;
import com.alchemy.woodsman.core.utilities.UsefulMath;
import com.alchemy.woodsman.core.world.World;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class PhysicsWorld {

    private World world;
    private ArrayList<PhysicsBody> bodies;

    public PhysicsWorld(World world) {
        this.world = world;

        bodies = new ArrayList<>();
    }

    public final void tickPhysics() {
        for (PhysicsBody body : bodies) {
            applyBodyPhysics(body);
        }

        bodies.clear();
    }

    private void applyBodyPhysics(PhysicsBody body) {
        //* Copy physicsBody variables.
        Vector2 netForce = body.getNetForce();

        //* Add netForce to velocity affected by mass.
        Vector2 deltaVelocity = new Vector2((netForce.x * 10f) / body.mass, (netForce.y * 10f) / body.mass);
        body.velocity.x += deltaVelocity.x;
        body.velocity.y += deltaVelocity.y;

        //* Set netForce to zero.
        body.setNetForce(new Vector2(0f, 0f));

        //* Check and resolve collisions and move physicsBody.
        stepBodyPhysics(body);

        if (body.friction > 1f) {
            body.friction = 1f;
        }
        else if (body.friction < 0f) {
            body.friction = 0f;
        }

        //* Reduce velocity due to friction.
        body.velocity.x -= (body.friction * body.velocity.x * 10f) * Game.getDeltaTime();
        body.velocity.y -= (body.friction * body.velocity.y * 10f) * Game.getDeltaTime();

        //* If velocity is small enough, set it to 0f.
        if (body.velocity.x > -0.001f && body.velocity.x < 0.001f) {
            body.velocity.x = 0f;
        }
        if (body.velocity.y > -0.001f && body.velocity.y < 0.001f) {
            body.velocity.y = 0f;
        }

        //* Wrap positional coordinates.
        int min = (-(world.getWorldSize() / 2)) * 16;
        int max = (world.getWorldSize() / 2) * 16;

        if (body.position.x < min) { body.position.x += max * 2; }
        if (body.position.x > max) { body.position.x -= max * 2; }

        if (body.position.y < min) { body.position.y += max * 2; }
        if (body.position.y > max) { body.position.y -= max * 2; }
    }

    private void stepBodyPhysics(PhysicsBody body) {
        //* Reset isColliding flag.
        body.isColliding = false;

        //* Get destination positions X for body after physics.
        float deltaPositionX = body.velocity.x * Game.getDeltaTime();

        //* Adjust collider to new body position X.
        Box bodyCollider = body.getCollider().translate(deltaPositionX, 0f);

        //* Check each corner of box for collisions.
        boolean isBottomLeftColliding = world.getBlockCollision(bodyCollider, new BlockPosition(bodyCollider.getBottomLeft().x, bodyCollider.getBottomLeft().y));
        boolean isBottomRightColliding = world.getBlockCollision(bodyCollider, new BlockPosition(bodyCollider.getBottomRight().x, bodyCollider.getBottomRight().y));
        boolean isTopLeftColliding = world.getBlockCollision(bodyCollider, new BlockPosition(bodyCollider.getTopLeft().x, bodyCollider.getTopLeft().y));
        boolean isTopRightColliding = world.getBlockCollision(bodyCollider, new BlockPosition(bodyCollider.getTopRight().x, bodyCollider.getTopRight().y));

        if (!isBottomLeftColliding && !isBottomRightColliding && !isTopLeftColliding && !isTopRightColliding) {
            //* Move body to new body position.
            body.position.x += deltaPositionX;
        } else {
            //* Set isColliding flag.
            body.isColliding = true;
        }

        //* Get destination position Y for body after physics.
        float deltaPositionY = body.velocity.y * Game.getDeltaTime();

        //* Adjust collider to new body position Y.
        bodyCollider = body.getCollider().translate(0f, deltaPositionY);

        //* Check each corner of box for collisions.
        isBottomLeftColliding = world.getBlockCollision(bodyCollider, new BlockPosition(bodyCollider.getBottomLeft().x, bodyCollider.getBottomLeft().y));
        isBottomRightColliding = world.getBlockCollision(bodyCollider, new BlockPosition(bodyCollider.getBottomRight().x, bodyCollider.getBottomRight().y));
        isTopLeftColliding = world.getBlockCollision(bodyCollider, new BlockPosition(bodyCollider.getTopLeft().x, bodyCollider.getTopLeft().y));
        isTopRightColliding = world.getBlockCollision(bodyCollider, new BlockPosition(bodyCollider.getTopRight().x, bodyCollider.getTopRight().y));

        if (!isBottomLeftColliding && !isBottomRightColliding && !isTopLeftColliding && !isTopRightColliding) {
            //* Move body to new body position.
            body.position.y += deltaPositionY;
        } else {
            //* Set isColliding flag.
            body.isColliding = true;
        }

        if (body.hasBodyCollision) {
            //* Check if body is colliding with other bodies, and if so resolve collision.
            for (PhysicsBody otherBody : bodies) {
                if (otherBody != body && UsefulMath.distance(body.position, otherBody.position) < 10f) {
                    Box otherBodyCollider = otherBody.getCollider();

                    if (bodyCollider.collides(otherBodyCollider) && otherBody.hasBodyCollision) {
                        //* Adjust collider to body position.
                        bodyCollider = body.getCollider();

                        Vector2 rejectionDirection = UsefulMath.direction(bodyCollider.getCenter(), otherBodyCollider.getCenter());
                        otherBody.addForce(rejectionDirection);

                        //* Set isColliding flag.
                        body.isColliding = true;
                        otherBody.isColliding = true;
                    }
                }
            }
        }
    }

    public final void addBody(PhysicsBody body) {
        bodies.add(body);
    }
}
