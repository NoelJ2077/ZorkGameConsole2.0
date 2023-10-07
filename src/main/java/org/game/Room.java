package org.game;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

public class Room {
    private final String description;
    private boolean taskCompleted;
    private final Map<String, Room> roomExits;
    private final Map<String, String> exitNames = new HashMap<>();
    private Room previousRoom; // used for: goBack()
    private final ArrayList<Item> items;

    public Room(String description) { // constructor
        this.description = description;
        this.roomExits = new HashMap<>();
        this.taskCompleted = false;
        items = new ArrayList<>();
    }
    // methods here
    public void setMove(Room north, Room east, Room south, Room west) {
        roomExits.put("n", north);
        roomExits.put("e", east);
        roomExits.put("s", south);
        roomExits.put("w", west);

        exitNames.put("n", "Norden");
        exitNames.put("e", "Osten");
        exitNames.put("s", "Süden");
        exitNames.put("w", "Westen");
    }

    public String longDescription() {
        return "Aktuelle Position: " +
                "\u001B[35m" + // color purple
                description +
                "\u001B[0m" + // reset color
                ".\n" +
                exitString();
    }

    public String getDescription() {
        return description;
    }

    private String exitString() {
        List<String> sortedExits = roomExits.keySet().stream()
                .filter(exit -> roomExits.get(exit) != null)
                .map(exitNames::get)
                .sorted() // Sortieren Sie die Richtungsnamen
                .collect(Collectors.toList());

        return "Mögliche Ausgänge sind: " + String.join(", ", sortedExits);
    }

    public Room nextRoom(String direction) {
        return roomExits.get(direction);
    }

    public String performTask(String taskName) {
        if (!taskCompleted && taskName.equals("pickup key")) {
            taskCompleted = true;
            return "Task erfolgreich abgeschlossen"; // return a string when the task is successfully performed
        } else if (taskCompleted) {
            return "Aufgabe wurde bereits erledigt"; // return a different string when the task has already been completed
        } else { // should never happen!
            return "Diese Aufgabe existiert nicht in diesem Raum.";
        }
    }

    public void DropTask() {
        taskCompleted = false;
    }

    public boolean isTaskCompleted() {
        return taskCompleted;
    }

    public String getExitString() {
        return exitString();
    }

    public Room goBack() {
        if (previousRoom == null) {
            System.out.println("Am Anfang des Spiels kannst du nicht zurückgehen.");
            return this;
        } else {
            System.out.println("Zurückgehen von Raum: " + "\u001B[35m" + description +
                    " ---> " + previousRoom.getDescription() + "\u001B[0m");
            System.out.println(previousRoom.exitString());
            return previousRoom;
        }
    }

    public Room goRoom(String direction) {
        Room nextRoom = nextRoom(direction);
        if (nextRoom == null) {
            System.out.println("Hier kannst du nicht hingehen!");
        } else {
            nextRoom.previousRoom = this; // Set the previous room to the current room
            System.out.println(nextRoom.longDescription());
        }
        return nextRoom;
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void removeItem(String itemName) {
        Item itemToRemove = null;
        for (Item item : items) {
            if (item.getName().equals(itemName)) {
                itemToRemove = item;
                break;
            }
        }
        if (itemToRemove != null) {
            items.remove(itemToRemove);
        }
    }

    public void showItems() {
        for (Item item : items) { // for each all Items
            System.out.println(item.getName());
        }
    }

    public Item getItem(String itemName) {
        for (Item item : items) {
            if (item.getName().equals(itemName)) {
                return item;
            }
        }
        return null;
    }

    // For the Map Class
    public Room getNorthExit() {
        return roomExits.get("n");
    }
    public Room getEastExit() {
        return roomExits.get("e");
    }
    public Room getSouthExit() {
        return roomExits.get("s");
    }
    public Room getWestExit() {
        return roomExits.get("w");
    }
} // end class
