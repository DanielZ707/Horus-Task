import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Wall implements Structure {
    private final List<Block> blocks;

    public Wall(List<Block> blocks) {
        this.blocks = blocks;
    }


    @Override
    public Optional<Block> findBlockByColor(String color) {
        for (Block block : blocks) {
            if (block.getColor().equals(color)) {
                return Optional.of(block);
            }
            if (block instanceof CompositeBlock) {
                Optional<Block> colorBlock = findByColorInCompositeBlock(((CompositeBlock) block).getBlocks(), color);
                if (colorBlock.isPresent()) {
                    return colorBlock;
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Block> findBlocksByMaterial(String material) {
        List<Block> materialBlocks = new ArrayList<>();
        for (Block block : blocks) {
            if (block.getMaterial().equals(material)) {
                materialBlocks.add(block);
            }
            if (block instanceof CompositeBlock) {
                materialBlocks.addAll(findByMaterialInCompositeBlock(((CompositeBlock) block).getBlocks(), material));
            }
        }
        return materialBlocks;
    }

    @Override
    public int count() {
        int count = 0;
        for (Block block : blocks) {
            count++;
            if (block instanceof CompositeBlock) {
                count += countInCompositeBlock(((CompositeBlock) block).getBlocks());
            }
        }
        return count;
    }

    private Optional<Block> findByColorInCompositeBlock(List<Block> compositeBlocks, String color) {
        for (Block block : compositeBlocks) {
            if (block.getColor().equals(color)) {
                return Optional.of(block);
            }
            if (block instanceof CompositeBlock) {
                Optional<Block> colorBlock = findByColorInCompositeBlock(((CompositeBlock) block).getBlocks(), color);
                if (colorBlock.isPresent()) {
                    return colorBlock;
                }
            }
        }
        return Optional.empty();
    }

    private List<Block> findByMaterialInCompositeBlock(List<Block> compositeBlocks, String material) {
        List<Block> materialBlocks = new ArrayList<>();
        for (Block block : compositeBlocks) {
            if (block.getMaterial().equals(material)) {
                materialBlocks.add(block);
            }
            if (block instanceof CompositeBlock) {
                materialBlocks.addAll(findByMaterialInCompositeBlock(((CompositeBlock) block).getBlocks(), material));
            }
        }
        return materialBlocks;
    }

    private int countInCompositeBlock(List<Block> compositeBlocks) {
        int counter = 0;
        for (Block block : compositeBlocks) {
            counter++;
            if (block instanceof CompositeBlock) {
                counter += countInCompositeBlock(((CompositeBlock) block).getBlocks());
            }
        }
        return counter;
    }
}
