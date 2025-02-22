package git.niklogik.core.network;

import git.niklogik.core.network.lib.Link;
import git.niklogik.core.network.lib.Node;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.locationtech.jts.geom.Coordinate;

import java.util.UUID;

@Setter
@Getter
@RequiredArgsConstructor
public class NodeImpl implements Node {
    private final UUID id;
    private final Double elevation;
    private final Coordinate coordinate;
    private Link inLink;
    private Link outLink;
    private int tripTime = 0;
}
