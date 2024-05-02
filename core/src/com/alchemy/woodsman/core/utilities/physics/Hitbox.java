package com.alchemy.woodsman.core.utilities.physics;

import com.alchemy.woodsman.Game;
import com.alchemy.woodsman.core.Settings;
import com.alchemy.woodsman.core.graphics.Renderer;
import com.alchemy.woodsman.core.graphics.Wireframe;
import com.badlogic.gdx.math.Vector2;

public class Hitbox {

    public Vector2 position;
    private Box collider;
    public boolean isColliding;

    public Hitbox(Box collider, Vector2 position) {
        this.collider = collider;
        this.position = position;
    }

    public final void render(Renderer renderer) {
        Settings settings = Game.getSettings();

        if (settings.isHitboxShown) {
            if (isColliding) {
                renderer.addWorldWireframe(new Wireframe(getCollider(), Wireframe.RED));
            }
            else {
                renderer.addWorldWireframe(new Wireframe(getCollider(), Wireframe.BLUE));
            }
        }
    }

    public final boolean collides(Hitbox hitbox) {
        if (getCollider().collides(hitbox.getCollider())) {
            return true;
        }

        return false;
    }

    public final void setCollider(Box collider) {
        this.collider = collider;
    }

    public final Box getCollider() {
        return new Box(new Box(((position.x + collider.x) - (collider.width / 2)), ((position.y + collider.y) - (collider.height / 2)), collider.width, collider.height));
    }
}
