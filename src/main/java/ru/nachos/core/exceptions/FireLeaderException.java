package ru.nachos.core.exceptions;

public class FireLeaderException extends IllegalArgumentException{
    public FireLeaderException(Code code) {
        super(code.getString());
    }

    public enum Code{
        TOO_MANY("Firefront contains too much head agents. Must be one."),
        MISSING("Firefront does not contain head agent. Must be one.");

        private final String string;

        Code(String string){
            this.string = string;
        }

        public String getString() {return string;}
    }
}
