package su.sa1zer.diversemodlib.models;

import lombok.Getter;
import net.minecraft.core.BlockPos;

@Getter
public class Cuboid {

    private int xMin;
    private int xMax;
    private int yMin;
    private int yMax;
    private int zMin;
    private int zMax;

    public Cuboid(BlockPos pos1, BlockPos pos2) {
        this(pos1.getX(), pos1.getY(), pos1.getZ(),
                pos2.getX(), pos2.getY(), pos2.getZ());
    }

    public Cuboid(int x1, int y1, int z1, int x2, int y2, int z2) {
        normalize(x1, y1, z1, x2, y2, z2);
    }

    private void normalize(int x1, int y1, int z1, int x2, int y2, int z2) {
        this.xMin = Math.min(x1, x2);
        this.xMax = Math.max(x1, x2);
        this.yMin = Math.min(y1, y2);
        this.yMax = Math.max(y1, y2);
        this.zMin = Math.min(z1, z2);
        this.zMax = Math.max(z1, z2);
    }

    public boolean isContains(BlockPos pos) {
        return isContains(pos.getX(), pos.getY(), pos.getY());
    }

    public boolean isContains(int x, int y, int z) {
        return x >= xMin && x <= xMax && y >= yMin && y <= yMax && z >= zMin && z <= zMax;
    }

    public long getVolume() {
        return getWidth() * getHeight() * getDepth();
    }

    public long getSquare() {
        return getWidth() * getDepth();
    }

    public boolean isOneLayer() {
        return yMax - yMin + 1 > 1;
    }

    public int getWidth() {
        return xMax - xMin + 1;
    }

    public int getHeight() {
        return yMax - yMin + 1;
    }

    public int getDepth() {
        return zMax - zMin + 1;
    }
}
