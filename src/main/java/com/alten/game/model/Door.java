package com.alten.game.model;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
@Data
public class Door {
    private boolean locked = true;
    private final Item key;

    public void unlock() {
        locked = false;
    }

}
