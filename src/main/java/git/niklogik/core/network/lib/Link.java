package git.niklogik.core.network.lib;

import java.math.BigDecimal;
import java.util.UUID;

public interface Link {
    UUID id();

    Node toNode();

    Node fromNode();

    Integer timeFlow();

    BigDecimal linkSpeed();
}
