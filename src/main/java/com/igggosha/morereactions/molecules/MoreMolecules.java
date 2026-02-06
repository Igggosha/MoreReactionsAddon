package com.igggosha.morereactions.molecules;

import com.igggosha.morereactions.ExampleMod;
import com.petrolpark.destroy.chemistry.legacy.LegacyMolecularStructure;
import com.petrolpark.destroy.chemistry.legacy.LegacySpecies;
import com.petrolpark.destroy.chemistry.legacy.LegacySpecies.MoleculeBuilder;
import com.petrolpark.destroy.chemistry.legacy.index.DestroyMolecules;

public class MoreMolecules {

    private static final MoleculeBuilder builder() {
        return new MoleculeBuilder(ExampleMod.MODID);
    };

    // aluminium chloride CANCELLED because aluminium doesnt exist in destroy...
//    public static final LegacySpecies

//    ALUMINIUM_CHLORIDE = builder()
//            .id("aluminium_chloride")
//            .structure(LegacyMolecularStructure.deserialize("destroy:linear:ClAl(Cl)Cl"))
//            .boilingPoint(180f)
//            .density(2480f)
//            .molarHeatCapacity(91.1f)
//            .tag(DestroyMolecules.Tags.ACUTELY_TOXIC)
//            .build();

    public static void register() {};
}
