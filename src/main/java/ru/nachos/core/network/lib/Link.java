package ru.nachos.core.network.lib;

import ru.nachos.core.Id;
import ru.nachos.core.controller.lib.HasID;

public interface Link extends HasID {
    @Override
    Id<Link> getId();

    Node getToNode();

    Node getFromNode();

    boolean setToNode(Node node);

    boolean setFromNode(Node node);

    double getLength();
}
