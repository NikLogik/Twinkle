package ru.nachos.core.exceptions;

public class AgentMapSequenceException extends AgentMapCollectionException {

    public AgentMapSequenceException(Code code) {
        super(code.getParam());
    }

    public AgentMapSequenceException(Code code, Throwable throwable){
        super(code.getParam(), throwable);
    }

    public enum Code{
        BAD_HEAD("Map does not contain head agent."),
        BAD_SEQUENCE("Sequence unit order is broken. It has agents without neighbour mapping.");
        private final String param;
        Code(String param){
            this.param = param;
        }
        public String getParam() { return param; }
    }
}
