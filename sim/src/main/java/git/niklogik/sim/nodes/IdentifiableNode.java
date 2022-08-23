package git.niklogik.sim.nodes;

import git.niklogik.sim.Identifiable;
import git.niklogik.sim.entities.EntityID;

public class IdentifiableNode implements Identifiable {
  protected final EntityID id;

  public IdentifiableNode(EntityID id) {
    this.id = id;
  }

  @Override
  public EntityID getId() {
    return this.id;
  }
}
