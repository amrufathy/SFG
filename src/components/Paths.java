/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package components;

import java.util.ArrayList;

/**
 *
 * @author amr
 */
public class Paths extends ArrayList<Path> {

    public Paths() {
        super();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Paths)) {
            return false;
        }

        for (Path path1 : this) {
            for (Path path2 : (Paths) obj) {
                if (!path1.equals(path2)) {
                    return false;
                }
            }
        }

        return true;
    }
}
