package org.rspeer.game.provider.callback;

import org.rspeer.commons.Time;
import org.rspeer.game.Definitions;
import org.rspeer.game.Game;
import org.rspeer.game.action.Action;
import org.rspeer.game.action.ActionOpcode;
import org.rspeer.game.event.ChatMessageEvent;
import org.rspeer.game.event.RenderEvent;
import jag.game.RSClient;
import jag.graphics.RSGraphicsProvider;
import jag.script.RSScriptEvent;

import java.awt.*;

public class EventMediator {

    private final EngineTasks engineTasks;

    public EventMediator() {
        engineTasks = new EngineTasks();
    }

    public EngineTasks getEngineTasks() {
        return engineTasks;
    }

    //Callbacks

    public void notifyProcessDialogActionPacket(int uid, int subcomponent) {
        try {
            System.out.println("YAHOOOOOOO " + uid + "(" + subcomponent + ")");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void notifyFireScriptEvent(RSScriptEvent event) {
        try {
            /*int[] filter = {3350, 1004, 3174, 839, 3277, 283};
            int id = event.getScriptId();
            for (int spam : filter) {
                if (id == spam) {
                    return;
                }
            }

            System.out.println("Fire " + id);*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void preNotifyXteaKeys(int index, int[] array) {
    }

    public void notifyXteaKeys(int index, int[] array) {
    }

    public void preNotifyItemIds(int index, int[] array) {
    }

    public void notifyItemIds(int index, int[] array) {
    }

    public void notifyProcessLoginResponse(int code) {
        try {
            System.out.println("Login response: " + code);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void notifyMessageReceived(int type, String source, String contents, String channel) {
        try {
            ChatMessageEvent event = new ChatMessageEvent(type, source, contents, channel);
            Game.getEventDispatcher().dispatch(event);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void notifyProcessAction(int sec, int ter, int op, int pri, String action, String target, int crosshairX, int crosshairY) {
        try {

            if (op == ActionOpcode.CANCEL.getId()
                    || op == ActionOpcode.WALK_HERE.getId()) {
                return;
            }

            //if action is Walk Here its callback, else its our own action and needs processing still
            if (op == ActionOpcode.WALK.getId() && action.isEmpty()) {
                Game.getClient().setViewportWalking(true);
                Game.getClient().setPendingDestinationX(sec);
                Game.getClient().setPendingDestinationY(ter);
            }

            Action resolved = Action.valueOf(op, pri, sec, ter);
            if (resolved != null) {
                System.out.println(resolved);
            } else {
                System.out.println("Action[op=" + ActionOpcode.valueOf(op) + ",pri=" + pri + ",sec=" + sec + ",ter=" + ter + "]");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void notifySetGameState(int state) {
        try {
            int oldState = Game.getClient().getGameState();
            if (oldState == 5 && state == 10) {
                Time.sleep(100);
                Definitions.populate();
                Game.getClient().setRedrawMode(420);
            }

            if (oldState != state) {
                System.out.println("Game state: " + oldState + " -> " + state);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void notifyDraw(RSClient ctx, boolean idk) {
        try {
            //used for engine tick
            engineTasks.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void notifyDrawGame(RSGraphicsProvider ctx, Graphics x, int width, int height) {
        try {
            Graphics g = ctx.getImage().getGraphics().create();
            Game.getEventDispatcher().dispatch(new RenderEvent(g));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
