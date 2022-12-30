package git.niklogik.core.network.lib;

import git.niklogik.core.Id;
import git.niklogik.core.controller.lib.HasID;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LinearRing;
import org.locationtech.jts.geom.Polygon;
import git.niklogik.core.utils.PolygonType;

public abstract class PolygonV2 extends Polygon implements HasID {

    @Override
    public abstract Id<PolygonV2> getId();

    public PolygonV2(LinearRing shell, LinearRing[] holes, GeometryFactory factory) {
        super(shell, holes, factory);
    }

    public abstract int getHeight();

    public abstract void setHeight(int height);

    public abstract void setType(PolygonType typeName);

    public abstract PolygonType getPolygonType();

    public abstract boolean addHole(LinearRing hole);
}
