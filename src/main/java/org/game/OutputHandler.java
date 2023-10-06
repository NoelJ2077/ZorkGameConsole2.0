package org.game;

public class OutputHandler {
    public static void welcomeMSG() {
        System.out.println("*************************************************************");
        System.out.println("Willkommen bei Zork 2.0!");
        System.out.println("Klicken Sie 'help' für Hilfe.");
        System.out.println("In jedem Raum müssen sie einen Schlüssel aufheben. Ziel ist, alle Schlüssel aufzuheben um den Aktenkoffer im Büro zu öffnen!");
        System.out.println("*************************************************************");
    }
    public static void helpMSG() {
        System.out.println("****************************************************************************");
        System.out.println("Verfügbare Commands:");
        System.out.println("   help -> Zeigt diese Nachricht");
        System.out.println("   go <Richtung> -> Bewegen z.B: 'go n' oder 'go north'");
        System.out.println("   back -> Gehen Sie zurück in den vorherigen Raum, falls möglich");
        System.out.println("   task -> Zeigt die Aufgabe des aktuellen Raums an");
        System.out.println("   map -> Zeigt eine Karte des aktuellen Raums an");
        System.out.println("   drop & pickup 'key' -> Lässt Sie einen Schlüssel ablegen oder aufheben");
        System.out.println("   item -> Interaktion mit den Gegenständen im Raum");
        System.out.println("   quit -> Beendet das Spiel");
        System.out.println("****************************************************************************");
    }

    public static void errorMSG() {
        System.out.println("\u001B[31m" + "Unbekannter Befehl..." + "\u001B[0m");
    }

    public static void byeMSG() {
        System.out.println("Danke fürs Spielen. Auf Wiedersehen!");
    }

    public static void taskDoneMSG() {
        System.out.println("\u001B[33m" + "Die Aufgabe in diesem Raum wurde bereits erledigt." + "\u001B[0m");
    }
    public static void keyPickedMSG() {
        System.out.println("\u001B[33m" + "Sie haben einen Schlüssel aufgehoben." + "\u001B[0m");
    }
    public static void winMSG() {
        System.out.println("\u001B[34m" + "Glückwunsch! Sie haben das Spiel gewonnen!" + "\u001B[0m");
    }
    public static void flushMSG() {
        System.out.println("\u001B[31m" + "Woher kennst du den Befehl 'flush'?");
        System.out.println("Dieser Befehl ist noch nicht verfügbar!" + "\u001B[0m");
    }
}