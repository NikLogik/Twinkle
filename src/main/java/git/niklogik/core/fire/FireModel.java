package git.niklogik.core.fire;

import git.niklogik.db.entities.fire.FireDAO;
import lombok.Getter;
import org.locationtech.jts.geom.Geometry;

@Getter
public class FireModel {

    private final Long fireId;
    private final Double direction;
    private final Double fireSpeed;
    private final Integer fireClass;
    private final Integer iterAmount;
    private final Geometry center;

    public FireModel(FireDAO fireDAO, int iterAmount){
        this.fireId = fireDAO.getId();
        this.direction = fireDAO.getFireInfo().getDirection();
        this.fireSpeed = fireDAO.getFireInfo().getFireSpeed();
        this.fireClass = fireDAO.getFireInfo().getFireClass();
        this.iterAmount = iterAmount;
        this.center = fireDAO.getFireInfo().getCenter();
    }
}
