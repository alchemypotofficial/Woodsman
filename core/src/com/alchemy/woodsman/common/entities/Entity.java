package com.alchemy.woodsman.common.entities;

import com.alchemy.woodsman.common.items.Inventory.ItemStack;
import com.alchemy.woodsman.core.graphics.Text;
import com.alchemy.woodsman.core.graphics.data.Registerable;
import com.alchemy.woodsman.core.graphics.Renderer;
import com.alchemy.woodsman.core.handlers.MessageHandler;
import com.alchemy.woodsman.core.utilities.physics.Box;
import com.alchemy.woodsman.core.utilities.UsefulMath;
import com.alchemy.woodsman.core.utilities.physics.PhysicsBody;
import com.alchemy.woodsman.core.utilities.physics.PhysicsWorld;
import com.alchemy.woodsman.core.world.World;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class Entity extends Registerable {

    private World world;
    protected PhysicsBody body;
    private String name = "Entity";
    private int light = 0;

    private boolean isDestroyed = false;

    public Entity(World world, Vector2 position) {
        super("woodsman.entity");

        this.world = world;

        setBody(new PhysicsBody(new Box(0, 0, 0.75f, 0.75f), 50f, position));
    }

    public void tick() {

    }

    public void tickPhysics(PhysicsWorld physicsWorld) {
        physicsWorld.addBody(body);
    }

    public void render(Renderer renderer, World world, Color color) {
        body.render(renderer);

        if (light > 0) {
            world.getChunk(getPosition().x, getPosition().y);
        }
    }

    public void renderInfo(Renderer renderer, World world) {
        renderer.addMenuText(new Text(getName(), new Vector2(280, 200), 100, 1f, Renderer.WHITE, Renderer.BLACK));
        renderer.addMenuText(new Text("(" + getID() + ")", new Vector2(280, 176), 100, 1f, Renderer.WHITE, Renderer.BLACK));
        renderer.addMenuText(new Text("Position: " + MessageHandler.formatVector(getPosition()), new Vector2(280, 152), 100, 1f, Renderer.WHITE, Renderer.BLACK));
    }

    public final void drop(ItemStack itemStack, Vector2 force) {
        if (itemStack != null) {
            world.dropItem(itemStack, getPosition(), force);
        }
    }

    public Entity spawn(World world, Vector2 position) {
        return new Entity(world, position);
    }

    public final void destroy() {
        isDestroyed = true;
    }

    public final void setPosition(Vector2 position) {
        body.position = position;
    }

    public final void addForce(Vector2 force) {
        body.addForce(force);
    }
    public final void setBody(PhysicsBody body) {
        this.body = body;
    }

    public final void setName(String name) {
        this.name = name;
    }

    public final World getWorld() {
        return this.world;
    }

    public final Vector2 getPosition() {
        return new Vector2(body.position);
    }

    public final Vector2 getVelocity() {
        return new Vector2(body.velocity);
    }

    public final String getName() {
        return this.name;
    }

    public final double getDistanceFrom(Vector2 position) {
        return UsefulMath.distance(body.position, position);
    }

    public final boolean isDestroyed() {
        return this.isDestroyed;
    }

    public final Box getCollider() {
        return this.body.getCollider();
    }
}