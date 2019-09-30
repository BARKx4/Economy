package com.barkx4.economy.blocks;

import com.barkx4.economy.gui.BankChestGui;
import com.barkx4.economy.gui.BankChestScreen;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateFactory;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
 
public class BankChest extends Block
{
    public BankChest(Settings settings)
    {
        super(settings);
        setDefaultState(getStateFactory().getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.NORTH));
    }
   
    @Override
    public boolean activate(BlockState blockState, World world, BlockPos blockPos, PlayerEntity playerEntity, Hand hand, BlockHitResult blockHitResult)
    {
    	MinecraftClient.getInstance().openScreen(new BankChestScreen(new BankChestGui(playerEntity, world, blockPos)));
        return true;
    }
 
    @Override
    public BlockState rotate(BlockState blockState, BlockRotation blockRotation)
    {
        return (BlockState)blockState.with(Properties.HORIZONTAL_FACING, blockRotation.rotate((Direction)blockState.get(Properties.HORIZONTAL_FACING)));
    }
 
    @Override
    public BlockState mirror(BlockState blockState, BlockMirror blockMirror)
    {
        return blockState.rotate(blockMirror.getRotation((Direction)blockState.get(Properties.HORIZONTAL_FACING)));
    }
 
    @Override
    protected void appendProperties(StateFactory.Builder<Block, BlockState> builder)
    {
        builder.add(Properties.HORIZONTAL_FACING);
    }
    
    @Override
    public BlockState getPlacementState(ItemPlacementContext itemPlacementContext) 
    {
        return (BlockState)this.getDefaultState().with(Properties.HORIZONTAL_FACING, itemPlacementContext.getPlayerFacing().getOpposite());
    }
}