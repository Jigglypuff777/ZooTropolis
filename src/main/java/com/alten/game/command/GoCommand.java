package com.alten.game.command;

import com.alten.game.controller.GameController;
import com.alten.game.controller.InputController;
import com.alten.game.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

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
                    System.out.println("The door is locked: would you like to use an item to unlock it?");
                    System.out.println("The required item is : " + requiredItem.getName());
                    System.out.println("The answer must be Y or N");
                    String answer = InputController.readString();

                    if (Objects.equals(answer, "Y")) {
                        System.out.println("Type the name of the chosen item");
                        String answerItemName = InputController.readString();

                        if (requiredItem.toString() == answerItemName || player.getItemsFromBag().contains(requiredItem)) {
                            currentRoom.openDoor(directionName, requiredItem);
                            gameController.getPlayer().removeItemFromBag(requiredItem);
                            Room nextRoom = currentRoom.move(direction);
                            System.out.println("You unlocked the door!");
                            gameController.setCurrentRoom(nextRoom);
                            gameController.getCurrentRoom().getInformation();
                        }else{
                            System.out.println("The item you chose is wrong or you don't have it");
                        }
                    } else if(Objects.equals(answer, "N")) {
                        System.out.println("You don't move from the position");
                    } else{
                        System.out.println("The answer must be Y or N");
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