package ru.nachos.core;

import java.io.Serializable;

public final class Coord implements Serializable {
    private static final long serialVersionUID = 1L;
    private double x;
    private double y;
    private double z;

    public Coord() {
        this.z = -1.0D / 0.0;
    }

    public Coord(double x, double y) {
        this.z = -1.0D / 0.0;
        this.x = x;
        this.y = y;
        this.z = -1.0D / 0.0;
    }

    public Coord(double[] coord) {
        this();
        switch (coord.length) {
            case 3:
                this.z = coord[2];
            case 2:
                this.x = coord[0];
                this.y = coord[1];
                return;
            default:
                throw new RuntimeException("double[] of wrong length; cannot be interpreted as coordinate ");
        }
    }

    public Coord(double x, double y, double z) {
        this.z = -1.0D / 0.0;
        if (z == -1.0D / 0.0) {
            throw new IllegalArgumentException("Double.NEGATIVE_INFINITY is an invalid elevation. If you want to ignore elevation, use Coord(x, y) constructor instead.");
        } else {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getZ() {
        if (!this.hasZ()) {
            throw new IllegalStateException("Requesting elevation (z) without having first set it.");
        } else {
            return this.z;
        }
    }

    public boolean hasZ() {
        return this.z != -1.0D / 0.0;
    }

    public boolean equals(Object other) {
        if (!(other instanceof Coord)) {
            return false;
        } else {
            Coord o = (Coord) other;
            if (!this.hasZ()) {
                if (o.hasZ()) {
                    return false;
                } else {
                    return this.x == o.getX() && this.y == o.getY();
                }
            } else if (!o.hasZ()) {
                return false;
            } else {
                return this.x == o.getX() && this.y == o.getY() && this.z == o.getZ();
            }
        }
    }

    public int hashCode() {
        long xbits = Double.doubleToLongBits(this.x);
        long ybits = Double.doubleToLongBits(this.y);
        long zbits = Double.doubleToLongBits(this.z);
        int result = (int) (xbits ^ xbits >>> 32);
        result = 31 * result + (int) (ybits ^ ybits >>> 32);
        result = 31 * result + (int) (zbits ^ zbits >>> 32);
        return result;
    }

    public final String toString() {
        return !this.hasZ() ? "[x=" + this.x + "][y=" + this.y + "]" : "[x=" + this.x + "][y=" + this.y + "][z=" + this.z + "]";
    }
}