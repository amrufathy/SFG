/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package signalflowgraph;

import com.alee.laf.WebLookAndFeel;
import gui.MainWindow;

/**
 *
 * @author amr
 */
public class SFG {

    public static void main(String[] args) {

        WebLookAndFeel.install();
        WebLookAndFeel.setDecorateFrames(true);
        WebLookAndFeel.setDecorateDialogs(true);

        MainWindow mainWindow = new MainWindow();

        mainWindow.show();

    }

}
