package su.sa1zer.diversemodlib.models;

import lombok.Getter;
import net.minecraft.core.BlockPos;

@Getter
public class Rectangle {

        private int xMin;
        private int xMax;
        private int zMin;
        private int zMax;

        public Rectangle(BlockPos pos1, BlockPos pos2) {
            this(pos1.getX(), pos1.getZ(),
                    pos2.getX(), pos2.getZ());
        }

        public Rectangle(int x1, int z1, int x2, int z2) {
            normalize(x1, z1, x2, z2);
        }

        private void normalize(int x1, int z1, int x2, int z2) {
            this.xMin = Math.min(x1, x2);
            this.xMax = Math.max(x1, x2);
            this.zMin = Math.min(z1, z2);
            this.zMax = Math.max(z1, z2);
        }

        public boolean isContains(BlockPos pos) {
            return isContains(pos.getX(), pos.getY(), pos.getZ());
        }

        public boolean isContains(int x, int y, int z) {
            return x >= xMin && x <= xMax && z >= zMin && z <= zMax;
        }

        public long getSquare() {
            return getWidth() * getDepth();
        }

        public int getWidth() {
            return xMax - xMin + 1;
        }

        public int getDepth() {
            return zMax - zMin + 1;
        }
}
