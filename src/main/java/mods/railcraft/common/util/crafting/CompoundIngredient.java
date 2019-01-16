/*------------------------------------------------------------------------------
 Copyright (c) CovertJaguar, 2011-2019
 http://railcraft.info

 This code is the property of CovertJaguar
 and may only be used with explicit written
 permission unless otherwise specified on the
 license page at http://railcraft.info/wiki/info:license.
 -----------------------------------------------------------------------------*/

package mods.railcraft.common.util.crafting;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntComparators;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class CompoundIngredient extends Ingredient {
    private Collection<Ingredient> children;
    private ItemStack[] stacks;
    private IntList itemIds;
    private final boolean isSimple;

    protected CompoundIngredient(Collection<Ingredient> children) {
        super(0);
        this.children = children;

        boolean simple = true;
        for (Ingredient child : children)
            simple &= child.isSimple();
        this.isSimple = simple;
    }

    @Override
    @Nonnull
    public ItemStack[] getMatchingStacks() {
        if (stacks == null) {
            List<ItemStack> tmp = Lists.newArrayList();
            for (Ingredient child : children)
                Collections.addAll(tmp, child.getMatchingStacks());
            stacks = tmp.toArray(new ItemStack[0]);

        }
        return stacks;
    }

    @Override
    @Nonnull
    public IntList getValidItemStacksPacked() {
        //TODO: Add a child.isInvalid()?
        if (this.itemIds == null) {
            this.itemIds = new IntArrayList();
            for (Ingredient child : children)
                this.itemIds.addAll(child.getValidItemStacksPacked());
            this.itemIds.sort(IntComparators.NATURAL_COMPARATOR);
        }

        return this.itemIds;
    }

    @Override
    public boolean apply(@Nullable ItemStack target) {
        if (target == null)
            return false;

        for (Ingredient child : children)
            if (child.apply(target))
                return true;

        return false;
    }

    @Override
    protected void invalidate() {
        this.itemIds = null;
        this.stacks = null;
        //Shouldn't need to invalidate children as this is only called form invalidateAll..
    }

    @Override
    public boolean isSimple() {
        return isSimple;
    }

    @Nonnull
    public Collection<Ingredient> getChildren() {
        return Collections.unmodifiableCollection(this.children);
    }
}