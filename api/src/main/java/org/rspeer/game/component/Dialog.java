package org.rspeer.game.component;

import org.rspeer.commons.Time;
import org.rspeer.game.Game;
import org.rspeer.game.Keyboard;
import org.rspeer.game.adapter.component.InterfaceComponent;
import org.rspeer.game.query.component.ComponentQuery;
import org.rspeer.game.query.results.ComponentQueryResults;
import jag.RSNodeTable;
import jag.game.RSInterfaceComponent;
import jag.game.RSSubInterface;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

public class Dialog {

    private static final InterfaceAddress CHAT_OPTIONS = new InterfaceAddress(() ->
            Interfaces.query().groups(InterfaceComposite.CHAT_OPTIONS.getGroup())
                    .filter(x -> x.getSubComponents().size() > 1).results().first());

    public static InterfaceComponent getPleaseWaitComponent() {
        RSInterfaceComponent component = Game.getClient().getPleaseWaitComponent();
        return component != null ? new InterfaceComponent(component) : null;
    }

    public static InterfaceComponent getContinue() {
        InterfaceComponent cmp = Interfaces.query(true)
                .texts(x -> x.toLowerCase().contains("click here to continue"))
                .results().first();
        return cmp != null
                && (ComponentConfig.isDialogOption(cmp.getConfig()) || cmp.containsAction("Continue")
                || (cmp.getForeground() == 128 && cmp.getFontId() == 496))
                ? cmp : null;
    }

    public static boolean canContinue() {
        return !isViewingChatOptions() && getContinue() != null;
    }

    public static boolean processContinue() {
        InterfaceComponent cmp = getContinue();
        return isProcessing() || cmp != null && cmp.interact("Continue");
    }

    public static boolean isViewingChatOptions() {
        return isOpen(InterfaceComposite.CHAT_OPTIONS.getGroup());
    }

    public static boolean isProcessing() {
        return Game.getClient().getPleaseWaitComponent() != null;
    }

    private static boolean isOpen(int group) {
        for (RSSubInterface wn : getOpen()) {
            if (wn.getId() == group) {
                return true;
            }
        }
        return false;
    }

    private static RSNodeTable<RSSubInterface> getNodes() {
        return Game.getClient().getSubInterfaces();
    }

    private static List<RSSubInterface> getOpen() {
        List<RSSubInterface> open = new ArrayList<>();
        RSNodeTable<RSSubInterface> table = getNodes();
        for (RSSubInterface node : table.toList()) {
            if (node == null || (node.getState() != 0 && node.getState() != 3)) {
                continue;
            }
            open.add(node);
        }
        return open;
    }

    public static boolean process(Predicate<String> predicate) {
        if (isProcessing()) {
            return true;
        } else if (canContinue()) {
            return processContinue();
        }

        ComponentQueryResults options = getChatOptions().results();
        for (int i = 0; i < options.size(); i++) {
            InterfaceComponent option = options.get(i);
            if (predicate.test(option.getText())) {
                return process(i);
            }
        }
        return false;
    }

    public static boolean process(int chatOptionIndex) {
        if (isProcessing()) {
            return true;
        } else if (canContinue()) {
            return processContinue();
        } else if (isViewingChatOptions()) {
            InterfaceComposite open = getOpenType();
            if (open == null) {
                Keyboard.sendKey((char) ('1' + chatOptionIndex));
                return Time.sleepUntil(Dialog::isProcessing, 600);
            }

            InterfaceComponent component = Interfaces.getDirect(open.getGroup(), 1);
            if (component != null) {
                component = component.getSubComponent(1 + chatOptionIndex);
                return component != null && component.interact(x -> true);
            }
        }
        return false;
    }

    static InterfaceComposite getOpenType() {
        if (isOpen(InterfaceComposite.PLAYER_DIALOG.getGroup())) {
            return InterfaceComposite.PLAYER_DIALOG;
        } else if (isOpen(InterfaceComposite.NPC_DIALOG.getGroup())) {
            return InterfaceComposite.NPC_DIALOG;
        } else if (isOpen(InterfaceComposite.LEVEL_UP_DIALOG.getGroup())) {
            return InterfaceComposite.LEVEL_UP_DIALOG;
        } else if (isOpen(InterfaceComposite.ITEM_DIALOG.getGroup())) {
            return InterfaceComposite.ITEM_DIALOG;
        } else if (isOpen(InterfaceComposite.PLAIN_DIALOG.getGroup())) {
            return InterfaceComposite.PLAIN_DIALOG;
        } else if (isOpen(InterfaceComposite.GUIDANCE_DIALOG.getGroup())) {
            return InterfaceComposite.GUIDANCE_DIALOG;
        }
        return null;
    }

    /**
     * @param options Options to select
     * @return {@code true} if an option was successfully interacted with or is already being interacted with
     */
    public static boolean process(String... options) {
        if (isProcessing()) {
            return true;
        } else if (canContinue()) {
            return processContinue();
        }

        for (String option : options) {
            if (process(x -> x.toLowerCase().contains(option.toLowerCase()))) {
                return true;
            }
        }
        return false;
    }

    public static ComponentQuery getChatOptions() {
        if (!isViewingChatOptions() || isProcessing()) {
            return new ComponentQuery(Collections::emptyList);
        }

        InterfaceComponent container = Interfaces.lookup(CHAT_OPTIONS);
        if (container == null) {
            return new ComponentQuery(Collections::emptyList);
        }

        return new ComponentQuery(container::getSubComponents);
    }
}
