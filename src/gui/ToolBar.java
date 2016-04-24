/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JSeparator;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

/**
 *
 * @author amr
 */
public class ToolBar extends JToolBar {

    public JButton newNode = new JButton(
            new ImageIcon("res/images/new-node.png"));
    public JButton deleteNode = new JButton(
            new ImageIcon("res/images/delete-node.png"));

    public JButton addPath = new JButton(
            new ImageIcon("res/images/new-path.png"));

    public JButton selectSourceNode = new JButton(
            new ImageIcon("res/images/source-node.png"));
    public JButton selectSinkNode = new JButton(
            new ImageIcon("res/images/sink-node.png"));

    public JButton evaluate = new JButton(
            new ImageIcon("res/images/calculate-icon.png"));

    public ToolBar() {
        super();

        setName("SFG - Toolbar");

        setFloatable(true);

        newNode.setToolTipText("Add New Node");
        deleteNode.setToolTipText("Delete Node");

        addPath.setToolTipText("Add Path");

        selectSourceNode.setToolTipText("Select A Source Node");
        selectSinkNode.setToolTipText("Select A Sink Node");

        evaluate.setToolTipText("Evaluate the graph");

        add(newNode);
        add(deleteNode);

        add(new JSeparator(SwingConstants.VERTICAL));

        add(addPath);

        add(new JSeparator(SwingConstants.VERTICAL));

        add(selectSourceNode);
        add(selectSinkNode);

        add(new JSeparator(SwingConstants.VERTICAL));

        add(evaluate);

    }
}
