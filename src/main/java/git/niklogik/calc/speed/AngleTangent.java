package git.niklogik.calc.speed;

import git.niklogik.calc.geo.Point3D;

public class AngleTangent {
  private final Point3D start;
  private final Point3D end;

  public AngleTangent(Point3D start, Point3D end) {
    this.start = start;
    this.end = end;
  }

  public Double tangentFi(){
    return end.dHeight(start) / start.distance(end);
  }
}
