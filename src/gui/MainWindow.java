/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import com.alee.laf.rootpane.WebFrame;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author amr
 */
public class MainWindow extends WebFrame {

    public MainWindow() {
        setTitle("Signal Flow Graph Calculator");

        setSize(
                (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() - 400,
                (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 400);

        setMinimumSize(new Dimension(500, 500));
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        initToolBar();
        initCanvas();
    }

    private void initToolBar() {
        toolBar = new ToolBar();

        add(toolBar, BorderLayout.NORTH);

        toolBar.newNode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                newNode = true;
            }
        });

        toolBar.deleteNode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
            }
        });

        toolBar.addPath.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                newPath = true;
            }
        });

        toolBar.selectSourceNode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
            }
        });

        toolBar.selectSinkNode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
            }
        });

        toolBar.evaluate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
            }
        });

    }

    private void initCanvas() {
        canvas = new Canvas(this);
        add(canvas);
    }

    private ToolBar toolBar;
    private Canvas canvas;
    public boolean newNode = false;
    public boolean newPath = false;

}
