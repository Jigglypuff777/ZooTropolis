package com.alten.game.command;

import com.alten.game.controller.GameController;
import com.alten.game.controller.InputController;
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
                handleValidDirection(directionName);
            } else {
                System.out.println("There is no room in that direction");
            }
        }
    }

    private void handleValidDirection(Direction directionName){
        Room currentRoom = gameController.getCurrentRoom();
        Door door = currentRoom.getDoors().get(directionName);

        if (door != null && door.isLocked()){
            controlLockedDoor(directionName, door);
        }else {
            moveWithoutUnlocking(directionName);
        }
    }

    public void controlLockedDoor(Direction directionName, Door door){
        Item requiredItem = door.getKey();
        Player player = gameController.getPlayer();

        System.out.println("The door is locked: would you like to use an item to unlock it?");
        System.out.println("The required item is : " + requiredItem.getName());
        System.out.println("The answer must be Y or N");

        String answer = InputController.readString();
        if(answer.equalsIgnoreCase("y")){
            unlockDoor(directionName, requiredItem, player);
        } else if (answer.equalsIgnoreCase("n")) {
            System.out.println("you won't move from here");
        }else {
            System.out.println("The answer must be Y or N");
        }
    }

    public void unlockDoor(Direction directionName, Item requiredItem, Player player){
        System.out.println("Type the name of the chosen item");
        String answerItemName = InputController.readString();

        if (requiredItem.getName().equalsIgnoreCase(answerItemName) && player.getItemsFromBag().contains(requiredItem)) {
            gameController.getCurrentRoom().openDoor(directionName, requiredItem);
            gameController.getPlayer().removeItemFromBag(requiredItem);
            Room nextRoom = gameController.getCurrentRoom().move(directionName.toString());
            System.out.println("You unlocked the door!");
            gameController.setCurrentRoom(nextRoom);
            gameController.getCurrentRoom().getInformation();
        }else{
            System.out.println("The item you chose is wrong or you don't have it");
        }
    }

    public void moveWithoutUnlocking(Direction directionName){
        Room nextRoom = gameController.getCurrentRoom().move(directionName.toString());
        gameController.setCurrentRoom(nextRoom);
        gameController.getCurrentRoom().getInformation();
    }

}