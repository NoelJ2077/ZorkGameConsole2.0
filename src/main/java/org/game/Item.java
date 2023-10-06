package org.game;

public class Item {
    private final String name;
    public Item(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    // method type static for add, remove, show
    public static void addItemToRoom(Room room, String itemName) {
        Item newItem = new Item(itemName);
        room.addItem(newItem);
        System.out.println("Gegenstand erfolgreich hinzugefügt.");
    }
    public static void removeItemFromRoom(Room room, String itemName) {
        if (room.getItem(itemName) == null) {
            System.out.println("Gegenstand nicht gefunden.");
            return;
        }
        room.removeItem(itemName);
        System.out.println("Gegenstand erfolgreich entfernt.");
    }
    public static void showItemsInRoom(Room room) {
        System.out.println("Alle Gegenstände im aktuellen Raum:");
        room.showItems();
    }
}