package org.rspeer.ui.debug.explorer.itf;

import org.rspeer.commons.AWTUtil;
import org.rspeer.environment.Environment;
import org.rspeer.game.Game;
import org.rspeer.game.component.Interfaces;
import org.rspeer.game.event.RenderEvent;
import org.rspeer.game.event.listener.RenderListener;
import org.rspeer.ui.locale.Message;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreeModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.function.Supplier;

public class InterfaceExplorer extends JFrame implements RenderListener {

    private final JTree tree;
    private Object render = null;
    private Supplier<TreeModel> provider = new DefaultModelProvider();

    public InterfaceExplorer(Environment environment) {
        super(Message.INTERFACE_EXPLORER.getActive(environment.getPreferences()));
        this.tree = new JTree();
        setLayout(new BorderLayout());

        JCheckBoxMenuItem hierarchical = new JCheckBoxMenuItem("Hierarchical View", false);
        add(hierarchical, BorderLayout.NORTH);
        hierarchical.addActionListener(x -> {
            provider = hierarchical.isSelected() ? new HierarchicalModelProvider() : new DefaultModelProvider();
            tree.setModel(provider.get());
        });

        JScrollPane viewport = new JScrollPane(tree, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        JPanel info = buildInfoPanel(tree);
        add(viewport, BorderLayout.WEST);
        add(info, BorderLayout.EAST);

        tree.setModel(provider.get());

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                Game.getEventDispatcher().subscribe(InterfaceExplorer.this);
            }

            @Override
            public void windowClosing(WindowEvent e) {
                Game.getEventDispatcher().unsubscribe(InterfaceExplorer.this);
            }
        });

        pack();
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(environment.getBotContext().getFrame());
        setVisible(true);
    }

    private JPanel buildInfoPanel(JTree tree) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        tree.getSelectionModel().addTreeSelectionListener(new SelectionListener(panel));
        return panel;
    }

    @Override
    public void notify(RenderEvent e) {
        Graphics g = e.getSource();
        if (render instanceof ComponentNode) {
            ComponentNode node = (ComponentNode) render;
            draw(g, node);
        } else if (render instanceof InterfaceNode) {
            InterfaceNode node = (InterfaceNode) render;
            for (ComponentNode child : node.children) {
                draw(g, child);
            }
        }
    }

    private void draw(Graphics g, ComponentNode node) {
        AWTUtil.drawBorderedRectangle(g, node.component.getBounds());
        for (ComponentNode component : node.children) {
            draw(g, component);
        }
    }

    private class SelectionListener implements TreeSelectionListener {

        private final JPanel panel;

        private SelectionListener(JPanel panel) {
            this.panel = panel;
        }

        @Override
        public void valueChanged(TreeSelectionEvent e) {
            panel.removeAll();

            Object selected = tree.getLastSelectedPathComponent();
            render = selected;

            if (selected instanceof InterfaceNode) {
                InterfaceNode node = (InterfaceNode) selected;
                panel.add(new JLabel("Address: " + node.group));
                panel.add(new JLabel("Component Count: " + Interfaces.query().groups(node.group).results().size()));
            } else if (selected instanceof ComponentNode) {
                ComponentNode node = (ComponentNode) selected;
                panel.add(new JLabel("Address: " + node.component.toAddress().toShortString()));
                panel.add(new JLabel("UID: " + node.component.getUid()));
                panel.add(new JLabel("Bounds: " + node.component.getBounds()));
                panel.add(new JLabel("Item: " + node.component.getItem()));
                panel.add(new JLabel("Type: " + node.component.getType()));
                panel.add(new JLabel("Material: " + node.component.getMaterialId()));
                panel.add(new JLabel("Sprite: " + node.component.getSpriteId()));
                panel.add(new JLabel("Foreground: " + node.component.getForeground()));
                panel.add(new JLabel("Shadow: " + node.component.getShadowColor()));
                panel.add(new JLabel("Model (type): " + node.component.getModelId() + " (" + node.component.getModelType() + ")"));
                panel.add(new JLabel("Config: " + node.component.getConfig()));
                panel.add(new JLabel("Text: " + node.component.getText()));
                panel.add(new JLabel("Name: " + node.component.getName()));
                panel.add(new JLabel("Actions: " + node.component.getActions()));
                panel.add(new JLabel("Tooltip: " + node.component.getToolTip()));
            }

            panel.repaint();
            panel.revalidate();
            pack();
        }
    }
}
