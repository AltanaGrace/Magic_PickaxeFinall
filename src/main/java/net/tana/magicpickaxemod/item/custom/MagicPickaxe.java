package net.tana.magicpickaxemod.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class MagicPickaxe extends Item {
    public MagicPickaxe(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext pContext) {
        if (!pContext.getLevel().isClientSide()) {
            Player player = pContext.getPlayer();
            Level level = pContext.getLevel();
            assert player != null;
            // Позиция блока, на который кликнули
            BlockPos clickedPos = pContext.getClickedPos();
            // Получаем координаты поля зрения игрока
            BlockPos dropPosition = BlockPos.containing(player.getEyePosition());

            // Проверяем блоки в радиусе 5
            for (int x = -5; x <= 5; x++) {
                for (int y = -5; y <= 5; y++) {
                    for (int z = -5; z <= 5; z++) {
                        BlockPos currentPos = clickedPos.offset(x, y, z);
                        BlockState state = level.getBlockState(currentPos);

                        if (isValuableBlock(state)) {
                            // Дропаем руду
                            dropOre(level, currentPos, dropPosition); //Передаем позицию игрока
                            level.setBlock(currentPos, Blocks.AIR.defaultBlockState(), 3); // Удаляем блок
                        }
                    }
                }
            }
           ;
        }

        return InteractionResult.SUCCESS;
    }

    private void dropOre(Level level, BlockPos orePos, BlockPos dropPosition) {
        Block block = level.getBlockState(orePos).getBlock();
        ItemStack dropStack = new ItemStack(block.asItem());
        ItemEntity itemEntity = new ItemEntity(level, dropPosition.getX(), dropPosition.getY(), dropPosition.getZ(), dropStack);
        level.addFreshEntity(itemEntity);
    }


    private boolean isValuableBlock(BlockState state) {
        return state.is(Blocks.IRON_ORE) || state.is(Blocks.DIAMOND_ORE) || state.is(Blocks.COAL_ORE) ||
                state.is(Blocks.COPPER_ORE) || state.is(Blocks.GOLD_ORE) || state.is(Blocks.REDSTONE_ORE) ||
                state.is(Blocks.EMERALD_ORE) || state.is(Blocks.LAPIS_ORE);
    }
}
