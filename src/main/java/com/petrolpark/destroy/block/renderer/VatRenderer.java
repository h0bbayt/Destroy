package com.petrolpark.destroy.block.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.petrolpark.destroy.block.entity.VatControllerBlockEntity;
import com.petrolpark.destroy.util.vat.Vat;
import com.simibubi.create.foundation.blockEntity.renderer.SafeBlockEntityRenderer;
import com.simibubi.create.foundation.fluid.FluidRenderer;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fluids.FluidStack;

public class VatRenderer extends SafeBlockEntityRenderer<VatControllerBlockEntity> {

    public VatRenderer(BlockEntityRendererProvider.Context context) {};

    @Override
    protected void renderSafe(VatControllerBlockEntity controller, float partialTicks, PoseStack ms, MultiBufferSource bufferSource, int light, int overlay) {
        if (!controller.getVatOptional().isPresent()) return;
        Vat vat = controller.getVatOptional().get();
        
        Vec3 relativeInternalLowerCorner = Vec3.atLowerCornerOf(vat.getInternalLowerCorner().subtract(controller.getBlockPos()));
        Vec3 relativeInternalUpperCorner = Vec3.atLowerCornerOf(vat.getUpperCorner().subtract(controller.getBlockPos()));

        FluidStack fluidStack = controller.getTank().getFluid();
        if (fluidStack.isEmpty()) return;
        controller.getRenderedFluidLevel(partialTicks);
        FluidRenderer.renderFluidBox(fluidStack,
            (float)relativeInternalLowerCorner.x, (float)relativeInternalLowerCorner.y, (float)relativeInternalLowerCorner.z,
            (float)relativeInternalUpperCorner.x, controller.getRenderedFluidLevel(partialTicks), (float)relativeInternalUpperCorner.z,
            bufferSource, ms, light, true); // TODO stop light being so dark
    };

    @Override
    public boolean shouldRenderOffScreen(VatControllerBlockEntity controller) {
        return true;
    };
    
};
