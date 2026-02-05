package com.igggosha.morereactions;
import com.petrolpark.destroy.chemistry.legacy.*;
import com.petrolpark.destroy.chemistry.legacy.genericreaction.GenericReactant;
import com.petrolpark.destroy.chemistry.legacy.genericreaction.GenericReaction;
import net.minecraft.resources.ResourceLocation;

// based on DoubleGroupGenericReaction
public abstract class TripleGroupGenericReaction<
        FirstGroup extends LegacyFunctionalGroup<FirstGroup>,
        SecondGroup extends LegacyFunctionalGroup<SecondGroup>,
        ThirdGroup extends LegacyFunctionalGroup<ThirdGroup>
        > extends GenericReaction
{
    @Override
    public boolean involvesSingleGroup() { return false; }

    private int i;

    protected final LegacyFunctionalGroupType<FirstGroup> firstType;
    protected final LegacyFunctionalGroupType<SecondGroup> secondType;
    protected final LegacyFunctionalGroupType<ThirdGroup> thirdType;

    public TripleGroupGenericReaction(ResourceLocation id, LegacyFunctionalGroupType<FirstGroup> firstType, LegacyFunctionalGroupType<SecondGroup> secondType, LegacyFunctionalGroupType<ThirdGroup> thirdType) {
        super(id);
        this.firstType = firstType;
        this.secondType = secondType;
        this.thirdType = thirdType;
        LegacyFunctionalGroup.groupTypesAndReactions.get(firstType).add(this);
        LegacyFunctionalGroup.groupTypesAndReactions.get(secondType).add(this);
        GENERIC_REACTIONS.add(this);
    };

    public abstract LegacyReaction generateReaction(GenericReactant<FirstGroup> firstReactant, GenericReactant<SecondGroup> secondReactant, GenericReactant<ThirdGroup> thirdReactant);


    public final LegacyFunctionalGroupType<FirstGroup> getFirstGroupType() {
        return firstType;
    };
    public final LegacyFunctionalGroupType<SecondGroup> getSecondGroupType() {
        return secondType;
    };
    public final LegacyFunctionalGroupType<ThirdGroup> getThirdGroupType() {
        return thirdType;
    };


    private LegacySpecies copyAndNumberRGroups(LegacySpecies molecule) {
        LegacyMolecularStructure copiedStructure = molecule.shallowCopyStructure();
        for (LegacyAtom atom : molecule.getAtoms()) {
            if (atom.getElement() == LegacyElement.R_GROUP) {
                LegacyAtom newAtom = new LegacyAtom(LegacyElement.R_GROUP);
                newAtom.rGroupNumber = i;
                copiedStructure.replace(atom, newAtom);
                i++;
            };
        };
        return moleculeBuilder()
                .structure(copiedStructure)
                .build();
    };

    @Override
    @SuppressWarnings("unchecked")
    public LegacyReaction generateExampleReaction() {

        i = 1;
        LegacySpecies exampleMolecule1 = copyAndNumberRGroups(getFirstGroupType().getExampleMolecule());
        LegacySpecies exampleMolecule2 = copyAndNumberRGroups(getSecondGroupType().getExampleMolecule());
        LegacySpecies exampleMolecule3 = copyAndNumberRGroups(getThirdGroupType().getExampleMolecule());

        GenericReactant<FirstGroup> reactant1 = null;
        GenericReactant<SecondGroup> reactant2 = null;
        GenericReactant<ThirdGroup> reactant3 = null;

        for (LegacyFunctionalGroup<?> group : exampleMolecule1.getFunctionalGroups()) { // Just in case the example Molecule has multiple functional groups (which it shouldn't ideally)
            if (group.getType() == getFirstGroupType()) reactant1 = new GenericReactant<>(exampleMolecule1, (FirstGroup)group);
        };
        for (LegacyFunctionalGroup<?> group : exampleMolecule2.getFunctionalGroups()) { // Just in case the example Molecule has multiple functional groups (which it shouldn't ideally)
            if (group.getType() == getSecondGroupType()) reactant2 = new GenericReactant<>(exampleMolecule2, (SecondGroup)group);
        };
        for (LegacyFunctionalGroup<?> group : exampleMolecule3.getFunctionalGroups()) { // Just in case the example Molecule has multiple functional groups (which it shouldn't ideally)
            if (group.getType() == getSecondGroupType()) reactant3 = new GenericReactant<>(exampleMolecule3, (ThirdGroup)group);
        };

        if (reactant1 == null || reactant2 == null || reactant3 == null) throw new IllegalStateException("Couldn't generate example Reaction for Generic Reaction "+id.toString());

        return generateReaction(reactant1, reactant2, reactant3); // Unchecked conversion

    };
}
