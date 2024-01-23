package com.alten.game.command;

import com.alten.game.controller.GameController;
import com.alten.game.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class GoCommand implements Command{

    private final GameController gameController;

    @Override
    public void execute(List<String> parameters) {
        if (parameters.isEmpty()) {
            System.out.println("Insert a direction");
        } else {
            String direction = parameters.get(0);
            Direction directionName = Direction.getDirectionFromName(direction);

            if (gameController.getCurrentRoom().checkDirection(direction)) {
                Room currentRoom = gameController.getCurrentRoom();

                Door door = currentRoom.getDoors().get(directionName);

                if (door != null && door.isLocked()) {
                    Item requiredItem = door.getKey();
                    Player player = gameController.getPlayer();

                    if (player.getItemsFromBag().contains(requiredItem)) {
                        currentRoom.openDoor(directionName, requiredItem);
                        gameController.getPlayer().removeItemFromBag(requiredItem);
                        Room nextRoom = currentRoom.move(direction);
                        gameController.setCurrentRoom(nextRoom);
                        gameController.getCurrentRoom().getInformation();
                    } else {
                        System.out.println("You need the required item to open this door : " + requiredItem.getName());
                    }
                } else {
                    Room nextRoom = currentRoom.move(direction);
                    gameController.setCurrentRoom(nextRoom);
                    gameController.getCurrentRoom().getInformation();
                }
            } else {
                System.out.println("Invalid direction");
            }
        }
    }
}