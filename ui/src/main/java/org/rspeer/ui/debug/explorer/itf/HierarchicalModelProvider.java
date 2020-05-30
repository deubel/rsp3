package org.rspeer.ui.debug.explorer.itf;

import org.rspeer.game.Game;
import org.rspeer.game.adapter.component.InterfaceComponent;
import org.rspeer.game.component.Interfaces;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import java.util.function.Supplier;

public class HierarchicalModelProvider implements Supplier<TreeModel> {

    @Override
    public TreeModel get() {
        int root = Game.getClient().getRootInterfaceIndex();
        InterfaceNode top = new InterfaceNode(root);

        for (InterfaceComponent c : Interfaces.query().groups(root).filter(x -> x.getParentUid() == -1).results()) {
            top.add(build("", c));
        }
        return new DefaultTreeModel(top);
    }

    private ComponentNode build(String prefix, InterfaceComponent component) {
        ComponentNode node = new ComponentNode(prefix, component);
        for (InterfaceComponent.TraversalOption option : InterfaceComponent.TraversalOption.natural()) {
            build(component, option, node);
        }

        return node;
    }

    private void build(InterfaceComponent cmp, InterfaceComponent.TraversalOption opt, ComponentNode node) {
        for (InterfaceComponent sub : cmp.getSubComponents(opt)) {
            ComponentNode child = build(String.valueOf(opt.name().charAt(0)), sub);
            node.add(child);
        }
    }
}
