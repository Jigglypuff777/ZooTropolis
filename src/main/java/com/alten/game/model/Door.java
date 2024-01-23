package com.alten.game.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;


@Getter
@AllArgsConstructor
@Data
public class Door {
    private boolean locked;
    private final Item key;

    public Door(Item key) {
        this.locked = true;
        this.key = key;
    }

    public void unlock() {
        locked = false;
    }

}
