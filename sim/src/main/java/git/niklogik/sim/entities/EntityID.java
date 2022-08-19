package git.niklogik.sim.entities;

import com.google.common.base.Objects;

import java.util.UUID;

public class EntityID {
    public final UUID uuid;

    public EntityID() {
        this.uuid = UUID.randomUUID();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof EntityID)) return false;

        EntityID other = (EntityID) obj;
        return this.uuid.equals(other.uuid);
    }
}
