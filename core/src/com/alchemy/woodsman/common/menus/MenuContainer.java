package com.alchemy.woodsman.common.menus;

import com.alchemy.woodsman.Game;
import com.alchemy.woodsman.common.entities.EntityPlayer;
import com.alchemy.woodsman.common.items.Inventory.Container;
import com.alchemy.woodsman.common.items.Inventory.ItemStack;
import com.alchemy.woodsman.common.items.Item;
import com.alchemy.woodsman.common.menus.elements.ElementItemStack;
import com.alchemy.woodsman.common.menus.elements.ElementResizeable;
import com.alchemy.woodsman.common.menus.elements.ElementSlot;
import com.alchemy.woodsman.core.graphics.Renderer;
import com.alchemy.woodsman.core.graphics.Text;
import com.alchemy.woodsman.core.handlers.InputHandler;
import com.alchemy.woodsman.core.utilities.physics.Box;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class MenuContainer extends Menu {

    protected EntityPlayer player;
    protected ItemStack heldItemStack;
    protected boolean shiftHeld;

    private float timeOpen;
    protected ArrayList<ElementSlot> slots = new ArrayList<>();

    public MenuContainer(String id) {
        super(id);

        heldItemStack = null;
    }

    @Override
    public void tick() {
        if (timeOpen < 0.125f) {
            timeOpen += Game.getDeltaTime();
        }
    }

    @Override
    public void render(Renderer renderer, Vector2 mousePosition) {
        for (ElementSlot slot : slots) {
            slot.render(renderer, 21, mousePosition);

            if (slot.getItemStack() != null && slot.getCollider().isPointWithin(mousePosition)) {
                ItemStack itemStack = slot.getItemStack();
                int stringWidth = itemStack.getItem().getName().length();

                if (stringWidth > 6) {
                    stringWidth -= 6;
                }
                else {
                    stringWidth = 0;
                }

                ElementResizeable namePlate = new ElementResizeable(new Box(mousePosition.x, mousePosition.y, 88 + (stringWidth * 4), 48), Renderer.getTexture("sprites/menus/system/Menu_Border.png"));

                namePlate.render(renderer, 50, mousePosition);
                renderer.addMenuText(new Text(slot.getItemStack().getItem().getName(), new Vector2(namePlate.getCollider().x + 20, namePlate.getCollider().y + 28), 51));
            }
        }

        if (heldItemStack != null) {
            if (heldItemStack.getItem() != null) {
                ElementItemStack elementItemStack = new ElementItemStack(new Box(mousePosition.x - 16f, mousePosition.y - 16f, 1f, 1f), heldItemStack);
                elementItemStack.render(renderer, 40, mousePosition);
            }
        }

        slots.clear();
    }

    @Override
    public void onShow() {
        timeOpen = 0f;
    }

    @Override
    public void onHide() {
        if (player != null && heldItemStack != null) {
            player.drop(heldItemStack, new Vector2(0, 10.5f));
            heldItemStack = null;
        }

        timeOpen = 0f;
    }

    @Override
    public void onMouseLeftDown(Vector2 mousePosition) {
        if (player != null) {
            for (ElementSlot slot : slots) {
                if (slot.isHovered(mousePosition) == true) {
                    ItemStack pressedItemStack = slot.getItemStack();

                    if (!shiftHeld) {
                        if (heldItemStack != null) {
                            if (pressedItemStack != null) {
                                Item pressedItem = pressedItemStack.getItem();

                                if (pressedItem == heldItemStack.getItem() && pressedItemStack.getAmount() < pressedItem.getMaxAmount()) {
                                    //* Stack itemStacks.
                                    if (pressedItemStack.getAmount() + heldItemStack.getAmount() <= heldItemStack.getItem().getMaxAmount()) {
                                        pressedItemStack.setAmount(pressedItemStack.getAmount() + heldItemStack.getAmount());
                                        heldItemStack = null;
                                    }
                                    else {
                                        int leftoverAmount = pressedItemStack.getAmount() + heldItemStack.getAmount() - pressedItem.getMaxAmount();

                                        pressedItemStack.setAmount(pressedItem.getMaxAmount());
                                        heldItemStack.setAmount(leftoverAmount);
                                    }
                                }
                                else {
                                    //* Swap itemStacks.
                                    ItemStack tempItemStack = heldItemStack;
                                    heldItemStack = pressedItemStack;
                                    slot.getContainer().setItemStack(tempItemStack, slot.getSlotNumber());
                                }
                            }
                            else {
                                //* Place heldItemStack at slot.
                                slot.getContainer().setItemStack(heldItemStack, slot.getSlotNumber());
                                heldItemStack = null;
                            }
                        }
                        else {
                            heldItemStack = pressedItemStack;
                            slot.getContainer().setItemStack(null, slot.getSlotNumber());
                        }
                    }
                    else if (pressedItemStack != null) {
                        quickMoveItem(slot);
                    }
                }
            }
        }
    }

    @Override
    public void onMouseLeftUp(Vector2 mousePosition) {

    }

    @Override
    public void onMouseRightDown(Vector2 mousePosition) {
        if (player != null && timeOpen >= 0.125f) {
            for (ElementSlot slot : slots) {
                if (slot.isHovered(mousePosition) == true) {
                    ItemStack pressedItemStack = slot.getItemStack();

                    if (heldItemStack == null && pressedItemStack != null) {
                        if (pressedItemStack.getAmount() > 1) {
                            heldItemStack = pressedItemStack.split();
                        }
                        else {
                            heldItemStack = pressedItemStack;
                            slot.getContainer().setItemStack(null, slot.getSlotNumber());
                        }
                    } else if (heldItemStack != null && pressedItemStack == null) {
                        heldItemStack.decrease(1);
                        slot.getContainer().setItemStack(new ItemStack(heldItemStack.getItem(), 1), slot.getSlotNumber());

                        if (heldItemStack.getAmount() <= 0) {
                            heldItemStack = null;
                        }
                    } else if (heldItemStack != null && pressedItemStack != null && pressedItemStack.getItem() == heldItemStack.getItem() && pressedItemStack.getAmount() < heldItemStack.getItem().getMaxAmount()) {
                        heldItemStack.decrease(1);
                        pressedItemStack.increase(1);

                        if (heldItemStack.getAmount() <= 0) {
                            heldItemStack = null;
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onMouseRightUp(Vector2 mousePosition) {

    }

    @Override
    public void onButtonDown(InputHandler.Button button, Vector2 mousePosition) {
        if (button == InputHandler.Shift) {
            shiftHeld = true;
        }

        if (button == InputHandler.Drop) {
            if (player != null) {
                for (ElementSlot slot : slots) {
                    if (slot.isHovered(mousePosition) == true) {
                        ItemStack selectedItemStack = slot.getItemStack();

                        player.drop(new ItemStack(selectedItemStack.getItem(), 1), new Vector2(0, 10.5f));
                        selectedItemStack.decrease(1);
                    }
                }
            }
        }
    }

    @Override
    public void onButtonUp(InputHandler.Button button, Vector2 mousePosition) {
        if (button == InputHandler.Shift) {
            shiftHeld = false;
        }
    }

    public void quickMoveItem(ElementSlot slot) {

    }

    public final void addSlot(ElementSlot slot) {
        slots.add(slot);
    }

    public final void setPlayer(EntityPlayer player) {
        this.player = player;
    }
}
