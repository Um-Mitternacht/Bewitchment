package com.bewitchment.client.misc;

import com.bewitchment.Bewitchment;
import com.bewitchment.client.model.block.*;
import com.bewitchment.registry.ModObjects;
import net.minecraft.block.Block;
import net.minecraft.client.model.ModelBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class Statues {
    public static Map<ResourceLocation, Statue> statues = new HashMap<>();

    private static final ModelBase lenny = new ModelLeonardStatue();
    public static Statue stone_leonard_statue = new Statue("stone_leonard_statue", Blocks.STONE, new ResourceLocation(Bewitchment.MODID, "textures/blocks/statue/leonard/stone.png"), lenny, 3);
    public static Statue gold_leonard_statue = new Statue("gold_leonard_statue", Blocks.GOLD_BLOCK, new ResourceLocation(Bewitchment.MODID, "textures/blocks/statue/leonard/gold.png"), lenny, 3);
    public static Statue nether_brick_leonard_statue = new Statue("nether_brick_leonard_statue", Blocks.NETHER_BRICK, new ResourceLocation(Bewitchment.MODID, "textures/blocks/statue/leonard/nether_brick.png"), lenny, 3);
    public static Statue scorned_brick_leonard_statue = new Statue("scorned_brick_leonard_statue", ModObjects.scorned_bricks[0], new ResourceLocation(Bewitchment.MODID, "textures/blocks/statue/leonard/scorned_brick.png"), lenny, 3);

    private static final ModelBase lilith = new ModelLilithStatue();
    public static Statue stone_lilith_statue = new Statue("stone_lilith_statue", Blocks.STONE, new ResourceLocation(Bewitchment.MODID, "textures/blocks/statue/lilith/stone.png"), lilith, 4);
    public static Statue gold_lilith_statue = new Statue("gold_lilith_statue", Blocks.GOLD_BLOCK, new ResourceLocation(Bewitchment.MODID, "textures/blocks/statue/lilith/gold.png"), lilith, 4);
    public static Statue nether_brick_lilith_statue = new Statue("nether_brick_lilith_statue", Blocks.NETHER_BRICK, new ResourceLocation(Bewitchment.MODID, "textures/blocks/statue/lilith/nether_brick.png"), lilith, 4);
    public static Statue scorned_brick_lilith_statue = new Statue("scorned_brick_lilith_statue", ModObjects.scorned_bricks[0], new ResourceLocation(Bewitchment.MODID, "textures/blocks/statue/lilith/scorned_brick.png"), lilith, 4);

    private static final ModelBase baphomet = new ModelBaphometStatue();
    public static Statue stone_baphomet_statue = new Statue("stone_baphomet_statue", Blocks.STONE, new ResourceLocation(Bewitchment.MODID, "textures/blocks/statue/baphomet/stone.png"), baphomet, 2);
    public static Statue gold_baphomet_statue = new Statue("gold_baphomet_statue", Blocks.GOLD_BLOCK, new ResourceLocation(Bewitchment.MODID, "textures/blocks/statue/baphomet/gold.png"), baphomet, 2);
    public static Statue nether_brick_baphomet_statue = new Statue("nether_brick_baphomet_statue", Blocks.NETHER_BRICK, new ResourceLocation(Bewitchment.MODID, "textures/blocks/statue/baphomet/nether_brick.png"), baphomet, 2);
    public static Statue scorned_brick_baphomet_statue = new Statue("scorned_brick_baphomet_statue", ModObjects.scorned_bricks[0], new ResourceLocation(Bewitchment.MODID, "textures/blocks/statue/baphomet/scorned_brick.png"), baphomet, 2);

    private static final ModelBase herne = new ModelHerneStatue();
    public static Statue stone_herne_statue = new Statue("stone_herne_statue", Blocks.STONE, new ResourceLocation(Bewitchment.MODID, "textures/blocks/statue/herne/stone.png"), herne, 4);
    public static Statue gold_herne_statue = new Statue("gold_herne_statue", Blocks.GOLD_BLOCK, new ResourceLocation(Bewitchment.MODID, "textures/blocks/statue/herne/gold.png"), herne, 4);
    public static Statue nether_brick_herne_statue = new Statue("nether_brick_herne_statue", Blocks.NETHER_BRICK, new ResourceLocation(Bewitchment.MODID, "textures/blocks/statue/herne/nether_brick.png"), herne, 4);
    public static Statue scorned_brick_herne_statue = new Statue("scorned_brick_herne_statue", ModObjects.scorned_bricks[0], new ResourceLocation(Bewitchment.MODID, "textures/blocks/statue/herne/scorned_brick.png"), herne, 4);

    private static final ModelBase moloch = new ModelMolochStatue();
    public static Statue stone_moloch_statue = new Statue("stone_moloch_statue", Blocks.STONE, new ResourceLocation(Bewitchment.MODID, "textures/blocks/statue/moloch/stone.png"), moloch, 3);
    public static Statue gold_moloch_statue = new Statue("gold_moloch_statue", Blocks.GOLD_BLOCK, new ResourceLocation(Bewitchment.MODID, "textures/blocks/statue/moloch/gold.png"), moloch, 3);
    public static Statue nether_brick_moloch_statue = new Statue("nether_brick_moloch_statue", Blocks.NETHER_BRICK, new ResourceLocation(Bewitchment.MODID, "textures/blocks/statue/moloch/nether_brick.png"), moloch, 3);
    public static Statue scorned_brick_moloch_statue = new Statue("scorned_brick_moloch_statue", ModObjects.scorned_bricks[0], new ResourceLocation(Bewitchment.MODID, "textures/blocks/statue/moloch/scorned_brick.png"), moloch, 3);

    private static final ModelBase lenny_idol = new ModelLeonardIdol();
    public static Statue stone_leonard_idol = new Statue("stone_leonard_idol", Blocks.STONE, new ResourceLocation(Bewitchment.MODID, "textures/blocks/idol/idol_leonard_stone.png"), lenny_idol, 2);
    public static Statue gold_leonard_idol = new Statue("gold_leonard_idol", Blocks.GOLD_BLOCK, new ResourceLocation(Bewitchment.MODID, "textures/blocks/idol/idol_leonard_gold.png"), lenny_idol, 2);
    public static Statue nether_brick_leonard_idol = new Statue("nether_brick_leonard_idol", Blocks.NETHER_BRICK, new ResourceLocation(Bewitchment.MODID, "textures/blocks/idol/idol_leonard_netherbrick.png"), lenny_idol, 2);
    public static Statue scorned_brick_leonard_idol = new Statue("scorned_brick_leonard_idol", ModObjects.scorned_bricks[0], new ResourceLocation(Bewitchment.MODID, "textures/blocks/idol/idol_leonard_scorned.png"), lenny_idol, 2);

    private static final ModelBase lilith_idol = new ModelLilithIdol();
    public static Statue stone_lilith_idol = new Statue("stone_lilith_idol", Blocks.STONE, new ResourceLocation(Bewitchment.MODID, "textures/blocks/idol/idol_lilith_stone.png"), lilith_idol, 2);
    public static Statue gold_lilith_idol = new Statue("gold_lilith_idol", Blocks.GOLD_BLOCK, new ResourceLocation(Bewitchment.MODID, "textures/blocks/idol/idol_lilith_gold.png"), lilith_idol, 2);
    public static Statue nether_brick_lilith_idol = new Statue("nether_brick_lilith_idol", Blocks.NETHER_BRICK, new ResourceLocation(Bewitchment.MODID, "textures/blocks/idol/idol_lilith_netherbrick.png"), lilith_idol, 2);
    public static Statue scorned_brick_lilith_idol = new Statue("scorned_brick_lilith_idol", ModObjects.scorned_bricks[0], new ResourceLocation(Bewitchment.MODID, "textures/blocks/idol/idol_lilith_scorned.png"), lilith_idol, 2);

    private static final ModelBase herne_idol = new ModelHerneIdol();
    public static Statue stone_herne_idol = new Statue("stone_herne_idol", Blocks.STONE, new ResourceLocation(Bewitchment.MODID, "textures/blocks/idol/idol_herne_stone.png"), herne_idol, 2);
    public static Statue gold_herne_idol = new Statue("gold_herne_idol", Blocks.GOLD_BLOCK, new ResourceLocation(Bewitchment.MODID, "textures/blocks/idol/idol_herne_gold.png"), herne_idol, 2);
    public static Statue nether_brick_herne_idol = new Statue("nether_brick_herne_idol", Blocks.NETHER_BRICK, new ResourceLocation(Bewitchment.MODID, "textures/blocks/idol/idol_herne_netherbrick.png"), herne_idol, 2);
    public static Statue scorned_brick_herne_idol = new Statue("scorned_brick_herne_idol", ModObjects.scorned_bricks[0], new ResourceLocation(Bewitchment.MODID, "textures/blocks/idol/idol_herne_scorned.png"), herne_idol, 2);

    private static final ModelBase baphomet_idol = new ModelBaphometIdol();
    public static Statue stone_baphomet_idol = new Statue("stone_baphomet_idol", Blocks.STONE, new ResourceLocation(Bewitchment.MODID, "textures/blocks/idol/idol_baphomet_stone.png"), baphomet_idol, 1);
    public static Statue gold_baphomet_idol = new Statue("gold_baphomet_idol", Blocks.GOLD_BLOCK, new ResourceLocation(Bewitchment.MODID, "textures/blocks/idol/idol_baphomet_gold.png"), baphomet_idol, 1);
    public static Statue nether_brick_baphomet_idol = new Statue("nether_brick_baphomet_idol", Blocks.NETHER_BRICK, new ResourceLocation(Bewitchment.MODID, "textures/blocks/idol/idol_baphomet_netherbrick.png"), baphomet_idol, 1);
    public static Statue scorned_brick_baphomet_idol = new Statue("scorned_brick_baphomet_idol", ModObjects.scorned_bricks[0], new ResourceLocation(Bewitchment.MODID, "textures/blocks/idol/idol_baphomet_scorned.png"), baphomet_idol, 1);

    private static final ModelBase moloch_idol = new ModelMolochIdol();
    public static Statue stone_moloch_idol = new Statue("stone_moloch_idol", Blocks.STONE, new ResourceLocation(Bewitchment.MODID, "textures/blocks/idol/idol_moloch_stone.png"), moloch_idol, 1);
    public static Statue gold_moloch_idol = new Statue("gold_moloch_idol", Blocks.GOLD_BLOCK, new ResourceLocation(Bewitchment.MODID, "textures/blocks/idol/idol_moloch_gold.png"), moloch_idol, 1);
    public static Statue nether_brick_moloch_idol = new Statue("nether_brick_moloch_idol", Blocks.NETHER_BRICK, new ResourceLocation(Bewitchment.MODID, "textures/blocks/idol/idol_moloch_netherbrick.png"), moloch_idol, 1);
    public static Statue scorned_brick_moloch_idol = new Statue("scorned_brick_moloch_idol", ModObjects.scorned_bricks[0], new ResourceLocation(Bewitchment.MODID, "textures/blocks/idol/idol_moloch_scorned.png"), moloch_idol, 1);

    private static final ModelBase goddess_statue = new ModelStatueGoddess();
    public static Statue stone_goddess_statue = new Statue("goddess_statue", Blocks.STONE, new ResourceLocation(Bewitchment.MODID, "textures/blocks/statue_purifying.png"), goddess_statue, 2);

    public static class Statue {
        private final String name;
        private final Block base;
        private final ResourceLocation loc;
        private final ModelBase model;
        private final int height;

        public Statue(String name, Block base, ResourceLocation loc, ModelBase model, int height) {
            this.name = name;
            this.base = base;
            this.loc = loc;
            this.model = model;
            this.height = height;
            statues.put(new ResourceLocation(Bewitchment.MODID, name), this);
        }

        public String getName() {
            return name;
        }

        public Block getBase() {
            return base;
        }

        public ResourceLocation getLoc() {
            return loc;
        }

        public ModelBase getModel() {
            return model;
        }

        public int getHeight() {
            return height;
        }
    }
}
