package git.niklogik.layers;

import java.util.Collection;
import java.util.function.Function;

public class TileArray {
  private final LayerObject[] array;

  public TileArray(LayerObject[] array) {
    this.array = array;
  }

  public LayerObject[] get(){ return this.array; }

  public <T extends Collection<LayerObject>> T map(Function<LayerObject[], T> mapFunction) {
    return mapFunction.apply( array );
  }
}
