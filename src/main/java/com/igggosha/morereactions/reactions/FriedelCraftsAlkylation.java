package com.igggosha.morereactions.reactions;

import com.igggosha.morereactions.ExampleMod;
import com.igggosha.morereactions.molecules.MoreMolecules;
import com.mojang.logging.LogUtils;
import com.petrolpark.destroy.DestroyItems;
import com.petrolpark.destroy.chemistry.legacy.*;
import com.petrolpark.destroy.chemistry.legacy.genericreaction.GenericReactant;
import com.petrolpark.destroy.chemistry.legacy.genericreaction.SingleGroupGenericReaction;
import com.petrolpark.destroy.chemistry.legacy.index.DestroyGroupTypes;
import com.petrolpark.destroy.chemistry.legacy.index.DestroyMolecules;
import com.petrolpark.destroy.chemistry.legacy.index.group.AlcoholGroup;
import com.petrolpark.destroy.chemistry.legacy.index.group.CarboxylicAcidGroup;
import com.petrolpark.destroy.chemistry.legacy.index.group.HalideGroup;
import com.petrolpark.destroy.chemistry.legacy.index.genericreaction.HalideSubstitution;
import org.slf4j.Logger;

import java.lang.annotation.ElementType;
import java.util.List;
import java.util.Set;


public class FriedelCraftsAlkylation extends SingleGroupGenericReaction<HalideGroup> {
    private static final Logger LOGGER = LogUtils.getLogger();

    public FriedelCraftsAlkylation()
    {
        super(ExampleMod.asResource("friedel_crafts_alkylation"), DestroyGroupTypes.HALIDE);
    }

    @Override
    public boolean isPossibleIn(ReadOnlyMixture mixture)
    {
        return mixture.getConcentrationOf(DestroyMolecules.BENZENE) > 0f;
        //mixture.getConcentrationOf(MoreMolecules.ALUMINIUM_CHLORIDE) > 0f;
    }

    @Override
    public LegacyReaction generateReaction(GenericReactant<HalideGroup> first)
    {

        LegacyMolecularStructure aromaticStructure = DestroyMolecules.BENZENE.shallowCopyStructure();

//
//
//        List<LegacyAtom> aromAtoms = aromaticStructure.getAllAtoms().stream().toList();
//        for (int i = 0; i < aromAtoms.size(); i++) {
//            aromaticStructure.moveTo(aromAtoms.get(i));
//            List<LegacyAtom> boundAtoms =aromaticStructure.getBondedAtomsOfElement(LegacyElement.CARBON);
//            if (boundAtoms.size() == 1) // if only bound to 1 carbon, this must be the side chain
//            {
//                break;
//            }
//        }
//
//        LegacyAtom carbon = aromaticStructure.getBondedAtomsOfElement(LegacyElement.CARBON).get(0);
//        aromaticStructure.moveTo(carbon);
//        LegacyAtom boundHydrogen = aromaticStructure.getBondedAtomsOfElement(LegacyElement.HYDROGEN).get(0);
//
//        aromaticStructure.remove(boundHydrogen);
//
//
        LegacyMolecularStructure halideStructure = first.getMolecule().shallowCopyStructure();
        HalideGroup firstGroup = first.getGroup();

        halideStructure.moveTo(firstGroup.carbon)
                .remove(firstGroup.halogen);
//                .moveTo(firstGroup.carbon);

        halideStructure.setStartingAtom(firstGroup.carbon);

        aromaticStructure.addGroupToPosition(halideStructure, 0);


//        LegacySpecies product = moleculeBuilder().structure(LegacyMolecularStructure.joinFormulae(
//                aromaticStructure, halideStructure, LegacyBond.BondType.SINGLE
//        )).build();

//        LegacySpecies hydrogenHalide = moleculeBuilder().structure(LegacyMolecularStructure.joinFormulae(
//                LegacyMolecularStructure.atom(firstGroup.halogen.getElement()),
//                LegacyMolecularStructure.atom(LegacyElement.HYDROGEN),
//                LegacyBond.BondType.SINGLE
//        )).build();

        LegacySpecies product = moleculeBuilder().structure(aromaticStructure).build();

        return reactionBuilder()
                .addReactant(DestroyMolecules.BENZENE, 1, 1)
                .addReactant(first.getMolecule())
//                .addCatalyst(MoreMolecules.ALUMINIUM_CHLORIDE, 1)
                .addSimpleItemCatalyst(DestroyItems.ZEOLITE::get, 1f) // technically also used, good luck with side reactions ig
                .addProduct(product)
                .addProduct(getIon(firstGroup.halogen))
                .addProduct(DestroyMolecules.PROTON)
//                .addProduct(hydrogenHalide)
                .build();
    }

    public static LegacySpecies getIon(LegacyAtom atom) {
        switch (atom.getElement()) {
            case FLUORINE:
                return DestroyMolecules.FLUORIDE;
            case CHLORINE:
                return DestroyMolecules.CHLORIDE;
            case IODINE:
                return DestroyMolecules.IODIDE;
            default:
                return null;
        }
    };

}
