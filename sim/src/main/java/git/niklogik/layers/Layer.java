package git.niklogik.layers;

import git.niklogik.Position;

public interface Layer {
  LayerObject findTile(Position position);

}
