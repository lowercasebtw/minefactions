package io.github.lowercasebtw.minefactions.items;

import io.github.lowercasebtw.minefactions.items.item.cooldown.*;
import io.github.lowercasebtw.minefactions.items.item.normal.ClaimWandItem;
import io.github.lowercasebtw.minefactions.items.item.normal.JakeItem;

public class Items {
	public static final CallbackItem CALLBACK = new CallbackItem();
	public static final InfinityPearlItem INFINITY_PEARL = new InfinityPearlItem();
	public static final SwapperItem SWAPPER = new SwapperItem();
	public static final GrapplingHookItem GRAPPLING_HOOK = new GrapplingHookItem();
	public static final AntiPlaceItem ANTI_PLACE = new AntiPlaceItem();
	public static final BlinderItem BLINDER = new BlinderItem();
	
	public static final JakeItem JAKE = new JakeItem(false, false);
	public static final JakeItem GOLDEN_JAKE = new JakeItem(true, false);
	public static final JakeItem ENCHANTED_GOLDEN_JAKE = new JakeItem(true, true);
	public static final ClaimWandItem CLAIM_WAND = new ClaimWandItem();
	
	public static void initialize() {}
}
