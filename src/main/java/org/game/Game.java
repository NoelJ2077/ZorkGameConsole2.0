package org.game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Game {
    private Room currentRoom;
    // map here
    private Room selecta, office, lab, entry, outside, garden, toilet;
    private int pickedKeys = 0;
    private final int totalKeys = 7; // one key per room
    private final ConsoleGameMap gameMap;


    public Game() { // constructor
        selecta = new Room("Selecta beim Automaten");
        office = new Room("Büro");
        lab = new Room("Labor");
        entry = new Room("Eingang zum Labor");
        outside = new Room("Aussenbereich (vor Eingang)");
        garden = new Room("Garten hinter dem Eingang");
        toilet = new Room("Toilette oder WC");

        selecta.setMove(null, lab, null, null);
        office.setMove(null, null, null, lab);
        lab.setMove(null, office, entry, selecta);
        entry.setMove(lab, toilet, outside, null);
        outside.setMove(entry, garden, null, null);
        garden.setMove(toilet, null, null, outside);
        toilet.setMove(null, null, garden, entry);

        Room[][] roomArray = {
                { selecta, lab, office },
                { null, entry, toilet },
                { null, outside, garden }
        };
        gameMap = new ConsoleGameMap(roomArray);
    }

    public void play() { // main method
        currentRoom = outside;
        OutputHandler.welcomeMSG();
        System.out.println(currentRoom.longDescription());

        boolean finished = false;
        while (!finished) {
            String command = getUserInput();
            finished = processCommand(command);
        }
        OutputHandler.byeMSG();
    }

    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private String getUserInput() {
        String inputLine = "";
        System.out.print("> ");
        try {
            inputLine = reader.readLine().toLowerCase();
        } catch (IOException e) {
            System.out.println("Error reading input: " + e.getMessage());
        }
        return inputLine;
    }

    private boolean processCommand(String command) {
        String[] words = command.split(" ");

        if (words.length == 0) {
            System.out.println("Was möchtest du tun?");
            return false;
        }

        label:
        switch (words[0]) { // switch on the first word
            case "-h" -> OutputHandler.helpMSG();
            case "help" -> OutputHandler.helpMSG();
            case "quit" -> {
                System.out.println("Spiel wird beendet...");
                return true; // exit with 0
            }
            case "go" -> {
                if (words.length < 2) {
                    System.out.println("Wohin möchtest du gehen?");
                    System.out.println(currentRoom.getExitString());
                } else {
                    String direction = words[1];
                    Room nextRoom = currentRoom.goRoom(direction);
                    if (nextRoom == null) {
                        System.out.println("Falsche Richtung! " + currentRoom.getExitString());
                    } else {
                        currentRoom = nextRoom;
                    }
                }
            }
            case "back" -> {
                if (currentRoom == null) {
                    System.out.println("Am Anfang des Spiels kannst du nicht zurückgehen!");
                } else {
                    currentRoom = currentRoom.goBack();
                }
            }
            case "pickup" -> {
                if (words.length < 2 || !words[1].equals("key")) { // check if the second word is key
                    System.out.println("Ich verstehe nicht, was du aufheben möchtest.");
                } else {
                    String taskResult = currentRoom.performTask(command);
                    if (taskResult.equals("Task erfolgreich abgeschlossen")) {
                        OutputHandler.keyPickedMSG();
                        pickedKeys++;
                        if (pickedKeys == totalKeys) {
                            System.out.println("\u001B[35m" + "Sie haben alle Schlüssel aufgehoben. Gehen sie ins Büro und geben sie 'unlock' ein um die Kiste zu öffnen!" + "\u001B[0m");
                        }
                    } else if (taskResult.equals("Aufgabe wurde bereits erledigt")) {
                        System.out.println("\u001B[33m" + taskResult + "\u001B[0m"); // yellow
                    }
                }
            }
            case "task" -> {
                if (currentRoom.isTaskCompleted()) {
                    OutputHandler.taskDoneMSG();
                } else {
                    System.out.println("Geben sie 'pickup key' ein, um den Schlüssel im Raum aufzuheben.");
                }
            }
            case "unlock" -> {
                if (pickedKeys == totalKeys && currentRoom == office) {
                    OutputHandler.winMSG();
                    return true; // exit game
                } else if (pickedKeys == totalKeys) { // room not = office
                    System.out.println("Sie müssen ins Büro gehen, um die Kiste zu öffnen!");
                } else { // color red
                    OutputHandler.flushMSG();
                    System.out.println("Current room: " + currentRoom.getDescription()); // print the current room
                    System.out.println("Picked keys: " + pickedKeys); // print the number of picked keys
                }
            }
            case "map" -> {
                gameMap.setCurrentRoom(currentRoom);
                gameMap.displayMap();
            }
            case "drop" -> {
                if (words.length < 2 || !words[1].equals("key")) {
                    System.out.println("Ich verstehe nicht, was du ablegen möchtest.");
                } else {
                    if ((pickedKeys > 0 ) && (currentRoom.isTaskCompleted())) {
                        pickedKeys--;
                        currentRoom.DropTask(); // task wird zurückgesetzt
                        System.out.println("\u001B[33m" + "Du hast einen Schlüssel abgelegt." + "\u001B[0m");
                    } else {
                        System.out.println("\u001B[33m"  + "Kein Schlüssel zum Ablegen vorhanden." + "\u001B[0m");
                    }
                }
            }
            case "item" -> {
                System.out.println("Möchten Sie einen Gegenstand hinzufügen, entfernen oder anzeigen?");
                System.out.println("Befehle: add, remove, show");
                String itemCommand = getUserInput();

                switch (itemCommand) {
                    case "add" -> {
                        System.out.println("Geben Sie den Namen des Gegenstands ein:");
                        String itemName = getUserInput();
                        Item.addItemToRoom(currentRoom, itemName);
                    }
                    case "remove" -> {
                        System.out.println("Geben Sie den Namen des Gegenstands ein:");
                        String itemName = getUserInput();
                        Item.removeItemFromRoom(currentRoom, itemName);
                    }
                    case "show" -> {
                        Item.showItemsInRoom(currentRoom);
                    }
                    default -> System.out.println("Unbekannter Befehl!");
                }
            }
            default -> // this msg is printed if the command is not recognized
                    OutputHandler.errorMSG();
        }
        return false; // continue game
    } // end switch
} // end class