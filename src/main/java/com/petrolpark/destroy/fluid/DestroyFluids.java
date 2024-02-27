package com.petrolpark.destroy.fluid;

import static com.petrolpark.destroy.Destroy.REGISTRATE;

import com.petrolpark.destroy.Destroy;
import com.petrolpark.destroy.fluid.MixtureFluid.MixtureFluidType;
import com.petrolpark.destroy.util.DestroyTags.DestroyFluidTags;
import com.simibubi.create.AllTags;
import com.simibubi.create.Create;
import com.simibubi.create.content.fluids.VirtualFluid;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.builders.FluidBuilder;
import com.tterrag.registrate.util.entry.FluidEntry;

import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class DestroyFluids {

    public static FluidStack air(int amount, float temperature) {
        return MixtureFluid.of(amount, MixtureFluid.airMixture(temperature), "fluid.destroy.air");
    };
    
    public static final FluidEntry<MixtureFluid> MIXTURE = REGISTRATE.virtualFluid("mixture",
        new ResourceLocation("destroy", "fluid/mixture_still"),
        new ResourceLocation("destroy", "fluid/mixture_flow"),
        MixtureFluidType::new,
        MixtureFluid::new
    )
        .lang("Mixture")
        .register();

    public static final FluidEntry<MixtureFluid> GAS_MIXTURE = REGISTRATE.virtualFluid("gas", // FOr display purposes only
        new ResourceLocation("destroy", "fluid/gas"),
        new ResourceLocation("destroy", "fluid/gas"),
        MixtureFluidType::new,
        MixtureFluid::new
    )
        .register();
        
    public static final FluidEntry<MoltenStainlessSteelFluid> MOLTEN_STAINLESS_STEEL = REGISTRATE.fluid("molten_stainless_steel", Destroy.asResource("block/molten_stainless_steel"), Destroy.asResource("block/molten_stainless_steel"), CreateRegistrate::defaultFluidType, MoltenStainlessSteelFluid::new)
    .properties(p -> p
        .lightLevel(15)
    )
    //TODO tags and bucket
    .register();

    public static final FluidEntry<VirtualFluid>
    
    URINE = virtualFluid("urine")
        .tag(AllTags.forgeFluidTag("urine"))
        .register(),
    CHORUS_WINE = coloredSwirlingFluid("chorus_wine", 0x808000c0)
        .register(),
    CREAM = virtualFluid("cream")
        .register(),
    CRUDE_OIL = virtualFluid("crude_oil")
        .tag(AllTags.forgeFluidTag("crude_oil"), DestroyFluidTags.AMPLIFIES_SMOG.tag)
        .bucket()
        .tag(AllTags.forgeItemTag("buckets/crude_oil"))
        .build()
        .register(),
    MOLTEN_CINNABAR = virtualFluid("molten_cinnabar")
        .properties(p -> p
            .lightLevel(10)
        ).register(),
    NAPALM_SUNDAE = virtualFluid("napalm_sundae")
        .tag(DestroyFluidTags.AMPLIFIES_SMOG.tag)
        .register(),
    ONCE_DISTILLED_MOONSHINE = coloredWaterFluid("once_distilled_moonshine", 0xE0684F31)
        .register(),
    PERFUME = coloredSwirlingFluid("perfume", 0x80ffcff7)
        .register(),
    SKIMMED_MILK = coloredWaterFluid("skimmed_milk", 0x00000000)
        .register(),
    THRICE_DISTILLED_MOONSHINE = coloredWaterFluid("thrice_distilled_moonshine", 0xC0A18666)
        .register(),
    TWICE_DISTILLED_MOONSHINE = coloredWaterFluid("twice_distilled_moonshine", 0xD08C6B46)
        .register(),
    UNDISTILLED_MOONSHINE = coloredWaterFluid("undistilled_moonshine", 0xF053330D)
        .register(),
    
    // Potions

    LONG_POTION = coloredPotionFluid("long_potion", 0xffff0000)
        .register(),
    STRONG_POTION = coloredPotionFluid("strong_potion", 0xffffff00)
        .register(),
    SPLASH_POTION = coloredPotionFluid("splash_potion", 0xffd00000)
        .register(),
    LINGERING_POTION = coloredPotionFluid("lingering_potion", 0xffd000d0)
        .register(),
    CORRUPTING_POTION = coloredPotionFluid("corrupting_potion", 0xff00d000)
        .register();

    private static FluidBuilder<VirtualFluid, CreateRegistrate> virtualFluid(String name) {
        return REGISTRATE.virtualFluid(name, Destroy.asResource("fluid/"+name), Destroy.asResource("fluid/"+name));
    };

    private static FluidBuilder<VirtualFluid, CreateRegistrate> coloredWaterFluid(String name, int color) {
        return coloredFluid(name, color, new ResourceLocation("minecraft", "block/water_still"), new ResourceLocation("minecraft", "block/water_flow"));
    };

    private static FluidBuilder<VirtualFluid, CreateRegistrate> coloredSwirlingFluid(String name, int color) {
        return coloredFluid(name, color, Destroy.asResource("fluid/swirling"), Destroy.asResource("fluid/swirling"));
    };

    private static FluidBuilder<VirtualFluid, CreateRegistrate> coloredPotionFluid(String name, int color) {
        return coloredFluid(name, color, Create.asResource("fluid/potion_still"), Create.asResource("fluid/potion_flow"));
    };

    private static FluidBuilder<VirtualFluid, CreateRegistrate> coloredFluid(String name, int color, ResourceLocation stillTexture, ResourceLocation flowingTexture) {
        return REGISTRATE.virtualFluid(name, stillTexture, flowingTexture, (properties, st, ft) -> new ColoredFluidType(properties, st, ft, color), VirtualFluid::new);
    };

    public static boolean isMixture(FluidStack stack) {
        return stack != null && !stack.isEmpty() && isMixture(stack.getFluid()) && stack.getOrCreateTag().contains("Mixture", Tag.TAG_COMPOUND);
    };

    public static boolean isMixture(Fluid fluid) {
        return fluid.isSame(MIXTURE.get());
    };

    public static void register() {}

};
