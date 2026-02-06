package com.igggosha.morereactions;
import com.igggosha.morereactions.reactions.AmideCondensation;
import com.igggosha.morereactions.reactions.AmmoniaAmideCondensation;
import com.igggosha.morereactions.reactions.FriedelCraftsAlkylation;

public class RegisterReactions {
    public static final AmideCondensation AMIDE_CONDENSATION = new AmideCondensation();
    public static final AmmoniaAmideCondensation AMMONIA_AMIDE_CONDENSATION = new AmmoniaAmideCondensation();

    public static final FriedelCraftsAlkylation FRIEDEL_CRAFTS_ALKYLATION = new FriedelCraftsAlkylation();

    public static void register() {}
}
