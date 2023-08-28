package net.nullean.reach.block;

import it.unimi.dsi.fastutil.objects.ObjectArraySet;
import net.minecraft.world.level.block.state.properties.WoodType;

import java.util.Set;
import java.util.stream.Stream;

public class ReachWoodTypes extends WoodType
{
    private static final Set<ReachWoodTypes> VALUES = new ObjectArraySet<>();
    public static final ReachWoodTypes SNAG = register(new ReachWoodTypes("snag"));
    private final String name;

    protected ReachWoodTypes(String p_61842_) {
        super(p_61842_);
        this.name = p_61842_;
    }

    public static ReachWoodTypes register(ReachWoodTypes p_61845_) {
        VALUES.add(p_61845_);
        return p_61845_;
    }

    public String name() {
        return this.name;
    }

    /**
     * Use this to create a new {@link WoodType}. Make sure to register its rendering by enqueuing Atlases.addWoodType(...) during client setup..
     */
    public static ReachWoodTypes create(String name) {
        return new ReachWoodTypes(name);
    }
}
