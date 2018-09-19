package org.abondar.experimental.cmdbot.commands;

public enum Emoji {

    SMILING_FACE_WITH_SMILING_EYES('\uD83D', '\uDE0A'),
    UNAMUSED_FACE('\uD83D', '\uDE12');



    private Character first;
    private Character second;

    Emoji(Character first,Character second){
        this.first = first;
        this.second = second;
    }

    public Character getFirst() {
        return first;
    }

    public Character getSecond() {
        return second;
    }
}
