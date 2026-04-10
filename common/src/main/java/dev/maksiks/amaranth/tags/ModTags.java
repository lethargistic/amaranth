package dev.maksiks.amaranth.tags;

import dev.maksiks.amaranth.Constants;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class ModTags {
    public static class Blocks {
        public static final TagKey<Block> NEEDS_SKEWER_TOOL =
                tag("needs_skewer_tool");
        public static final TagKey<Block> INCORRECT_FOR_SKEWER_TOOL =
                tag("incorrect_for_skewer_tool");

        /**
        * separate tag from vanilla because the vanilla one
        * would allow placing bamboo on top of this block and vice versa
         **/
        public static final TagKey<Block> ALIEN_PHYLLOSTACHYS_PLANTABLE_ON =
                tag("alien_phyllostachys_plantable_on");

        private static TagKey<Block> tag(String name) {
            return TagKey.create(Registries.BLOCK,
                    ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, name));
        }
    }
}