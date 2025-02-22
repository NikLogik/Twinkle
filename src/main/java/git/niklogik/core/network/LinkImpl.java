package git.niklogik.core.network;

import git.niklogik.core.network.lib.Link;
import git.niklogik.core.network.lib.Node;

import java.math.BigDecimal;
import java.util.UUID;

public record LinkImpl(
    UUID id,
    Node fromNode,
    Node toNode,
    BigDecimal linkSpeed,
    Integer timeFlow
) implements Link {}
