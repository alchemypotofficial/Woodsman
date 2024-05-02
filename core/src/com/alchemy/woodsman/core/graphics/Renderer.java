package com.alchemy.woodsman.core.graphics;

import com.alchemy.woodsman.core.graphics.data.Registry;
import com.alchemy.woodsman.core.graphics.data.TextureAsset;
import com.alchemy.woodsman.core.utilities.BlockPosition;
import com.alchemy.woodsman.core.utilities.physics.Box;
import com.alchemy.woodsman.core.utilities.Debug;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import java.nio.ByteBuffer;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.zip.Deflater;

public class Renderer {

    public static final Color BLACK = new Color(9f / 255f, 10f / 255f, 20f / 255f, 1f);
    public static final Color WHITE = new Color(1f, 1f, 1f, 1f);
    public static final Color DARK_GREY = new Color(32f / 255f, 46f / 255f, 55f / 255f, 1f);
    public static final Color RED = new Color(117f / 255f, 36f / 255f, 56f / 255f, 1f);
    public static final Color DARK_GREEN = new Color(25f / 255f, 51f / 255f, 45f / 255f, 1f);
    public static final Color GREEN = new Color(117f / 255f, 167f / 255f, 67f / 255f, 1f);
    public static final Color BLUE = new Color(79f / 255f, 143f / 255f, 186f / 255f, 1f);
    public static final Color YELLOW = new Color(232f / 255f, 193f / 255f, 112f / 255f, 1f);

    private static ArrayList<Viewable> worldViewables = new ArrayList<>();
    private static ArrayList<Viewable> menuViewables = new ArrayList<>();

    private static ArrayList<Wireframe> worldWireframes = new ArrayList<>();
    private static ArrayList<Wireframe> menuWireframes = new ArrayList<>();

    private static ArrayList<Text> menuText = new ArrayList<>();

    private static Registry<TextureAsset> textures = new Registry<>();

    private static Registry<Animation> animations = new Registry<Animation>();
    private static HashMap<String, Animator> animators = new HashMap<>();

    private static DecimalFormat decimalFormat;

    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private Camera worldCamera;
    private Camera menuCamera;
    private BitmapFont fontKarmaSuture;

    public Renderer() {
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        menuCamera = new Camera(new Vector2(0f, 0f), 1f);

        fontKarmaSuture = new BitmapFont();
        decimalFormat = new DecimalFormat("0.00");
    }

    public final void beginRender() {
        batch.begin();
    }

    public final void enableBlending() {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
    }

    public final void disableBlending() {
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    public final void endRender() {
        batch.end();
    }

    public void addWorldViewable(Viewable viewable) {
        if (viewable != null) {
            worldViewables.add(viewable);
        }
    }

    public void addMenuViewable(Viewable viewable) {
        if (viewable != null) {
            menuViewables.add(viewable);
        }
    }

    public void addMenuText(Text text) {
        menuText.add(text);
    }

    public void addWorldWireframe(Wireframe wireframe) {
        worldWireframes.add(wireframe);
    }

    public void addMenuWireframe(Wireframe wireframe) {
        menuWireframes.add(wireframe);
    }

    public void render() {
        batch.enableBlending();

        if (worldCamera != null) {
            worldCamera.render(batch);
            worldCamera.render(shapeRenderer);

            beginRender();

            for (Animator animator : animators.values()) {
                animator.animate();
            }

            //* Render worldViewables.
            Collections.sort(worldViewables);

            for (Viewable viewable : worldViewables) {
                if (viewable.getTexture() != null) {
                    Texture rawTexture = viewable.getTexture().rawTexture;
                    Box region = viewable.getRegion();

                    if (rawTexture != null && region != null) {
                        int regionX = (int)region.x;
                        int regionY = (int)region.y;

                        int regionWidth = (int)region.width;
                        int regionHeight = (int)region.height;

                        TextureRegion textureRegion = new TextureRegion(rawTexture, regionX, regionY, regionWidth, regionHeight);

                        if (viewable.getColor() != null) {
                            batch.setColor(viewable.getColor());
                        }

                        batch.draw(textureRegion, viewable.getPosition().x * 16, viewable.getPosition().y * 16, regionWidth * viewable.getScale(), regionHeight * viewable.getScale());
                    }

                    batch.setColor(new Color(1, 1, 1, 1));
                }
            }

            worldViewables.clear();

            endRender();

            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

            //* Render worldWireframes.
            for (Wireframe wireframe : worldWireframes) {
                Box box = wireframe.getBox();

                shapeRenderer.setColor(wireframe.getColor());
                shapeRenderer.rect(box.x * 16, box.y * 16, box.width * 16, box.height * 16);
            }

            worldWireframes.clear();

            shapeRenderer.end();
        }

        if (menuCamera != null) {
            menuCamera.render(batch);
            menuCamera.render(shapeRenderer);

            beginRender();

            //* Render menuRenderables.
            ArrayList<Renderable> menuRenderables = new ArrayList<>();
            menuRenderables.addAll(menuViewables);
            menuRenderables.addAll(menuText);

            Collections.sort(menuRenderables);

            for (Renderable renderable : menuRenderables) {
                if (renderable instanceof Viewable) {
                    Viewable viewable = (Viewable)renderable;

                    if (viewable.getTexture() != null) {
                        Texture rawTexture = viewable.getTexture().rawTexture;
                        Box region = viewable.getRegion();

                        if (rawTexture != null && region != null) {
                            int regionX = (int)region.x;
                            int regionY = (int)region.y;

                            int regionWidth = (int)region.width;
                            int regionHeight = (int)region.height;

                            TextureRegion textureRegion = new TextureRegion(rawTexture, regionX, regionY, regionWidth, regionHeight);

                            if (viewable.getColor() != null) {
                                batch.setColor(viewable.getColor());
                            }

                            batch.draw(textureRegion, viewable.getPosition().x, viewable.getPosition().y, regionWidth * viewable.getScale(), regionHeight * viewable.getScale());
                        }

                        batch.setColor(new Color(1, 1, 1, 1));
                    }
                }
                else if (renderable instanceof Text) {
                    Text text = (Text)renderable;

                    //* Render textBackground.
                    if (text.getBehindColor() != null) {
                        endRender();

                        String string = text.getString();
                        Color behindColor = text.getBehindColor();

                        enableBlending();

                        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

                        shapeRenderer.setColor(new Color(behindColor.r, behindColor.g, behindColor.b, 0.75f));
                        shapeRenderer.rect(text.position.x - 4, text.position.y - getStringHeight(string) - 4, getStringWidth(string) + 8, getStringHeight(string) + 8);

                        shapeRenderer.end();

                        disableBlending();

                        beginRender();
                    }

                    //* Render menuText.
                    fontKarmaSuture.setColor(text.getColor());
                    fontKarmaSuture.getData().setScale(text.getScale());
                    fontKarmaSuture.getRegion().getTexture().setFilter(Texture.TextureFilter.Nearest , Texture.TextureFilter.Nearest);
                    fontKarmaSuture.draw(batch, text.getString(), text.getPosition().x, text.getPosition().y, text.getWidth(), -1, text.getIsWrapped());
                    fontKarmaSuture.setColor(new Color(1, 1, 1, 1));
                }
            }

            menuViewables.clear();
            menuText.clear();

            endRender();

            //* Render menuWireframes.
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

            for (Wireframe wireframe : menuWireframes) {
                Box box = wireframe.getBox();

                shapeRenderer.setColor(wireframe.getColor());
                shapeRenderer.rect(box.x, box.y, box.width, box.height);
            }

            menuWireframes.clear();

            shapeRenderer.end();
        }

        batch.disableBlending();
    }

    public void load() {
        TextureAsset cursorTexture = Renderer.getTexture("sprites/system/System_Cursor.png");
        if (cursorTexture != null) {
            if (!cursorTexture.isEmpty()) {
                Pixmap cursorPixmap = new Pixmap(Gdx.files.internal("sprites/system/System_Cursor.png"));
                Cursor cursor = Gdx.graphics.newCursor(cursorPixmap, 4, 4);
                Gdx.graphics.setCursor(cursor);
            }
        }
    }

    public final void takeScreenshot() {
        Pixmap pixmap = Pixmap.createFromFrameBuffer(0, 0, Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight());
        ByteBuffer pixels = pixmap.getPixels();

        //* Make sure the whole screenshot is opaque and looks exactly like what is seen when played.
        int size = Gdx.graphics.getBackBufferWidth() * Gdx.graphics.getBackBufferHeight() * 4;
        for (int i = 3; i < size; i += 4) {
            pixels.put(i, (byte) 255);
        }

        PixmapIO.writePNG(Gdx.files.external("woodsman_screenshot.png"), pixmap, Deflater.DEFAULT_COMPRESSION, true);
        pixmap.dispose();
    }

    public final Vector2 toWorldPosition(Vector2 position) {
        Vector2 cameraPosition = worldCamera.toCameraPosition(position);

        float worldPositionX = cameraPosition.x / 16;
        float worldPositionY = cameraPosition.y / 16;

        return new Vector2(worldPositionX, worldPositionY);
    }

    public final BlockPosition toBlockPosition(Vector2 position) {
        Vector2 cameraPosition = worldCamera.toCameraPosition(position);

        int blockPositionX = (int)Math.floor(cameraPosition.x / 16);
        int blockPositionY = (int)Math.floor(cameraPosition.y / 16);

        return new BlockPosition(blockPositionX, blockPositionY);
    }

    public final Vector2 toMenuPosition(Vector2 position) {
        return menuCamera.toCameraPosition(position);
    }

    public final float getStringHeight(String string) {
        GlyphLayout glyphLayout = new GlyphLayout(fontKarmaSuture, string);

        return glyphLayout.height;
    }

    public final float getStringWidth(String string) {
        GlyphLayout glyphLayout = new GlyphLayout(fontKarmaSuture, string);

        return glyphLayout.width;
    }

    public final void setCamera(Camera camera) {
        worldCamera = camera;

        worldCamera.render(batch);
        worldCamera.render(shapeRenderer);
    }

    public final static TextureAsset getTexture(String texturePath) {
        if (textures.getEntry(texturePath) != null) {
            return textures.getEntry(texturePath);
        }
        else {
            Texture rawTexture;

            try {
                FileHandle file = Gdx.files.internal(texturePath);
                rawTexture = new Texture(file);
            }
            catch (Exception exception) {
                Debug.logError("Could not find texture \"" + texturePath + "\".");

                return Renderer.getTexture("sprites/system/System_Null.png");
            }

            if (rawTexture != null) {
                Debug.logNormal("Texture", "Registering texture at " + texturePath + ".");

                TextureAsset textureAsset = new TextureAsset(texturePath, rawTexture);
                textures.register(textureAsset);

                return textureAsset;
            }
        }

        Debug.logError("Could not register texture \"" + texturePath + "\".");

        return Renderer.getTexture("sprites/system/System_Null.png");
    }

    public final static Frame registerAnimation(Animation animation) {
        if (animations.getEntry(animation.getID()) == null) {
            Animator animator = new Animator();
            animator.setAnimation(animation, true);

            animations.register(animation);
            animators.put(animation.getID(), animator);

            return animator.getFrame();
        }
        else {
            return getFrame(animation.getID());
        }
    }

    public final static Frame getFrame(String id) {
        if (animators.containsKey(id)) {
            Animator animator = animators.get(id);
            return animator.getFrame();
        }

        Debug.logError("Could not find animation \"" + id + "\".");

        return null;
    }

    public Vector2 unproject(Vector2 position) {
        return menuCamera.toCameraPosition(new Vector2(position.x, position.y));
    }

    public Camera getWorldCamera() {
        return this.worldCamera;
    }

    public Camera getMenuCamera() {
        return this.menuCamera;
    }

    public static DecimalFormat getDecimalFormat() {
        return decimalFormat;
    }

    public void dispose() {
        batch.dispose();

        ArrayList<TextureAsset> texturesAssets = textures.getEntries();
        for (TextureAsset textureAsset : texturesAssets) {
            textureAsset.rawTexture.dispose();
        }
    }
}
