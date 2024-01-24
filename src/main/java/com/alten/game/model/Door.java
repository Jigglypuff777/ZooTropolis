package com.alten.game.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;


@Getter
@AllArgsConstructor
@Data
public class Door {
    private boolean locked = true;
    private  Item key;

    public void unlock() {
        locked = false;
    }

}
