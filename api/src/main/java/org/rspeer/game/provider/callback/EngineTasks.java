package org.rspeer.game.provider.callback;

import org.rspeer.commons.Random;
import org.rspeer.game.Game;
import org.rspeer.game.action.Action;
import jag.game.RSClient;

import java.awt.*;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class EngineTasks {

    private final Actions actions;
    private final Executions executions;

    public EngineTasks() {
        actions = new Actions();
        executions = new Executions();
    }

    public void flush() {
        actions.flush();
        executions.flush();
    }

    public void offer(Runnable execution) {
        executions.offer(execution);
    }

    public void offer(Action action) {
        actions.offer(action);
    }

    private abstract class QueueTask<T> {

        protected final Queue<T> pending;

        private QueueTask(Queue<T> collection) {
            this.pending = collection;
        }

        private QueueTask() {
            this(new ArrayBlockingQueue<>(512));
        }

        abstract void offer(T element);

        abstract void flush();
    }

    private class Executions extends QueueTask<Runnable> {

        @Override
        void offer(Runnable element) {
            pending.offer(element);
        }

        @Override
        void flush() {
            pending.forEach(Runnable::run);
        }
    }

    private class Actions extends QueueTask<Action> {

        private Action lastAction;
        private long lastProcess;
        private long lastClickTime;

        @Override
        void offer(Action element) {
            pending.offer(element);
        }

        @Override
        void flush() {
            Iterator<Action> iterator = pending.iterator();
            while (iterator.hasNext()) {
                Action cur = iterator.next();
                boolean fire = true;
                if (lastAction != null && System.currentTimeMillis() - lastProcess < 600) {
                    if (lastAction.getOpcode() == cur.getOpcode()
                            && lastAction.getSecondary() == cur.getSecondary()
                            && lastAction.getTertiary() == cur.getTertiary()) {
                        fire = false;
                    }
                }
                if (fire) {
                    processMenuAction(cur);
                    lastProcess = System.currentTimeMillis();
                    lastAction = cur;
                }
                iterator.remove();
            }
        }

        private Point processMouseMovement(RSClient client) {
            long mouseMoveTime = System.currentTimeMillis() - Random.nextInt(5, 15);
            int moveX = 0;
            int moveY = 0;
            client.setMouseX(moveX);
            client.setMouseY(moveY);
            client.setMouseMoveTime(mouseMoveTime);
            client.getMouseRecorder().snapshot(moveX, moveY, mouseMoveTime);
            return new Point(moveX, moveY);
        }

        private void processMouseClick(RSClient client, Point dst) {
            long time = System.currentTimeMillis();
            client.setTimeOfClick(time);
            client.setPreviousTimeOfClick(lastClickTime);
            client.setClickMeta(2);
            client.setClickX(dst.x);
            client.setClickY(dst.y);
            lastClickTime = time;
        }

        private void processMenuAction(Action action) {
            //this is temporary until i can publicize the rest
            RSClient client = Game.getClient();
            Point dst = processMouseMovement(client);
            processMouseClick(client, dst);

            client.processAction(
                    action.getSecondary(),
                    action.getTertiary(),
                    action.getOpcode().getId(),
                    action.getPrimary(),
                    "",
                    "",
                    20,
                    20
            );
        }
    }
}
