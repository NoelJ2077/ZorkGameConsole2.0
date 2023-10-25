package org.game;
import java.util.ArrayList;
import java.util.List;

public class ConsoleGameMap {
    private final Room[][] roomArray;
    private Room currentRoom;
    private String ANSI_RED = "\u001B[31m[-]\u001B[0m"; // color red [-]
    private String ANSI_GREENw = "\u001B[32m[\u001B[0m"; // color green [
    private String ANSI_GREENe = "\u001B[32m]\u001B[0m"; // color green ]
    private String ANSI_BLUEw = "\u001B[34m[\u001B[0m"; // color blue [
    private String ANSI_BLUEe = "\u001B[34m]\u001B[0m"; // color blue ]
    private String USER = "\u001B[32mX\u001B[0m"; // color purple X
    private String BLANK = " ";

    public ConsoleGameMap(Room[][] roomArray) {
        // import roomArray from Game.java
        this.roomArray = roomArray;
        this.currentRoom = null;
    }

    public void setCurrentRoom(Room room) {
        this.currentRoom = room;
    }

    public void displayMap() {
        for (Room[] rooms : roomArray) {
            for (Room room : rooms) {
                if (room == currentRoom && room.isTaskCompleted()) {
                    System.out.print(ANSI_BLUEw + USER + ANSI_BLUEe); // Mark completed current room as blue but with green X
                } else if (room == currentRoom) {
                    System.out.print(ANSI_GREENw + USER + ANSI_GREENe); // Mark current room as green X
                } else if (room == null) {
                    System.out.print(ANSI_RED); // Mark null room as red with a blank space
                } else if (room.isTaskCompleted()) {
                    System.out.print(ANSI_BLUEw + BLANK + ANSI_BLUEe); // Mark completed room as blue
                } else {
                    System.out.print("[ ]"); // Mark other rooms as blank spaces
                }
            }
            System.out.println();
        }

        // Display possible exits for current room
        System.out.print("Mögliche Ausgänge:  ");
        if (currentRoom != null) {
            List<String> exits = new ArrayList<>();
            if (currentRoom.getNorthExit() != null) {
                exits.add("↑"); // ↑ n
            }
            if (currentRoom.getEastExit() != null) {
                exits.add("→"); // → e
            }
            if (currentRoom.getSouthExit() != null) {
                exits.add("↓"); // ↓ s
            }
            if (currentRoom.getWestExit() != null) {
                exits.add("←"); // ← w
            }
            for (String exit : exits) {
                System.out.print(exit + "\t");
            }
        }
        System.out.println();
    } // end method
} // end class