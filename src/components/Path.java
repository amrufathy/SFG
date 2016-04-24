/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package components;

import java.util.Stack;

/**
 *
 * @author amr
 */
public class Path extends Stack<GraphNode> {

    public Path() {
        super();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Path)) {
            return false;
        }

        for (int i = 0, j = 0; i < this.size() && j < ((Path) obj).size(); i++, j++) {
            if (this.get(i) != ((Path) obj).get(j)) {
                return false;
            }
        }

        return true;
    }

}
