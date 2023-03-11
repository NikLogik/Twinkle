package git.niklogik.layers;

import git.niklogik.calc.Position;

public interface Layer {
  LayerObject findTile(Position position);
}
