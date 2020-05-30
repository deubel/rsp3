package org.rspeer.game.script;

import org.rspeer.commons.Time;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class TaskScript extends Script {

    private final List<Task> tasks = new CopyOnWriteArrayList<>();

    public final void submit(Task... tasks) {
        Collections.addAll(this.tasks, tasks);
        Collections.sort(this.tasks);
    }

    @Override
    public int loop() {
        for (Task task : tasks) {
            if (task.validate()) {
                int sleep = task.execute();
                if (sleep < 0) {
                    return -1;
                }

                Time.sleep(sleep);
                if (task.isBlocking()) {
                    break;
                }
            }
        }

        return 20;
    }
}
