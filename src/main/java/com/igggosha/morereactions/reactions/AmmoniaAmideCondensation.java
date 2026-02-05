package com.igggosha.morereactions.reactions;

import com.igggosha.morereactions.ExampleMod;
import com.mojang.logging.LogUtils;
import com.petrolpark.destroy.Destroy;
import com.petrolpark.destroy.chemistry.legacy.*;
import com.petrolpark.destroy.chemistry.legacy.genericreaction.DoubleGroupGenericReaction;
import com.petrolpark.destroy.chemistry.legacy.genericreaction.GenericReactant;
import com.petrolpark.destroy.chemistry.legacy.genericreaction.SingleGroupGenericReaction;
import com.petrolpark.destroy.chemistry.legacy.index.DestroyGroupTypes;
import com.petrolpark.destroy.chemistry.legacy.index.DestroyMolecules;
import com.petrolpark.destroy.chemistry.legacy.index.group.CarboxylicAcidGroup;
import com.petrolpark.destroy.chemistry.legacy.index.group.PrimaryAmineGroup;


public class AmmoniaAmideCondensation extends SingleGroupGenericReaction<CarboxylicAcidGroup>
{

    public AmmoniaAmideCondensation()
    {
        super(ExampleMod.asResource("ammonia_amide_condensation"), DestroyGroupTypes.CARBOXYLIC_ACID);
    }

    @Override
    public boolean isPossibleIn(ReadOnlyMixture mixture)
    {
        return mixture.getTemperature() >= 100 && mixture.getConcentrationOf(DestroyMolecules.AMMONIA) > 0f;
    }

    @Override
    public LegacyReaction generateReaction(GenericReactant<CarboxylicAcidGroup> first)
    {
        LegacyMolecularStructure ammoniaStructure = LegacyMolecularStructure.atom(LegacyElement.NITROGEN)
                .addAtom(LegacyElement.HYDROGEN)
                .addAtom(LegacyElement.HYDROGEN);

        LegacyMolecularStructure acidStructure = first.getMolecule().shallowCopyStructure();
        CarboxylicAcidGroup acidGroup = first.getGroup();

        acidStructure.moveTo(acidGroup.carbon)
                .remove(acidGroup.proton)
//        acidStructure.moveTo(acidGroup.carbon)
                .remove(acidGroup.alcoholOxygen);

        LegacySpecies amide = moleculeBuilder().structure(LegacyMolecularStructure.joinFormulae(
                acidStructure, ammoniaStructure, LegacyBond.BondType.SINGLE
        )).build();

        return reactionBuilder()
                .addReactant(first.getMolecule(), 1, 1)
                .addReactant(DestroyMolecules.AMMONIA, 1, 1)
                .addProduct(amide)
                .addProduct(DestroyMolecules.WATER)
                .build();
    }
}
