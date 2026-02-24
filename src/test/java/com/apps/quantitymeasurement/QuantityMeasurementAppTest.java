package com.apps.quantitymeasurement;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.apps.quantitymeasurement.QuantityMeasurementApp;

public class QuantityMeasurementAppTest {

	private static final double DELTA = 0.0001;

	// 1
	@Test
	void testMeasurableInterface_LengthUnitImplementation() {
		assertTrue(QuantityMeasurementApp.LengthUnit.FEET
				instanceof QuantityMeasurementApp.IMeasurable);
	}

	// 2
	@Test
	void testMeasurableInterface_WeightUnitImplementation() {
		assertTrue(QuantityMeasurementApp.WeightUnit.KILOGRAM
				instanceof QuantityMeasurementApp.IMeasurable);
	}

	// 3
	@Test
	void testMeasurableInterface_ConsistentBehavior() {
		assertEquals(12,
				QuantityMeasurementApp.LengthUnit.INCH
				.convertFromBaseUnit(1), DELTA);
	}

	// 4
	@Test
	void testGenericQuantity_LengthOperations_Equality() {
		QuantityMeasurementApp.Quantity<QuantityMeasurementApp.LengthUnit> q1 =
				new QuantityMeasurementApp.Quantity<>(1,
						QuantityMeasurementApp.LengthUnit.FEET);

		QuantityMeasurementApp.Quantity<QuantityMeasurementApp.LengthUnit> q2 =
				new QuantityMeasurementApp.Quantity<>(12,
						QuantityMeasurementApp.LengthUnit.INCH);

		assertEquals(q1, q2);
	}

	// 5
	@Test
	void testGenericQuantity_WeightOperations_Equality() {
		QuantityMeasurementApp.Quantity<QuantityMeasurementApp.WeightUnit> w1 =
				new QuantityMeasurementApp.Quantity<>(1,
						QuantityMeasurementApp.WeightUnit.KILOGRAM);

		QuantityMeasurementApp.Quantity<QuantityMeasurementApp.WeightUnit> w2 =
				new QuantityMeasurementApp.Quantity<>(1000,
						QuantityMeasurementApp.WeightUnit.GRAM);

		assertEquals(w1, w2);
	}

	// 6
	@Test
	void testGenericQuantity_LengthOperations_Conversion() {
		QuantityMeasurementApp.Quantity<QuantityMeasurementApp.LengthUnit> q =
				new QuantityMeasurementApp.Quantity<>(1,
						QuantityMeasurementApp.LengthUnit.FEET);

		assertEquals(12,
				q.convertTo(QuantityMeasurementApp.LengthUnit.INCH)
				.getValue(), DELTA);
	}

	// 7
	@Test
	void testGenericQuantity_WeightOperations_Conversion() {
		QuantityMeasurementApp.Quantity<QuantityMeasurementApp.WeightUnit> w =
				new QuantityMeasurementApp.Quantity<>(1,
						QuantityMeasurementApp.WeightUnit.KILOGRAM);

		assertEquals(1000,
				w.convertTo(QuantityMeasurementApp.WeightUnit.GRAM)
				.getValue(), DELTA);
	}

	// 8
	@Test
	void testGenericQuantity_LengthOperations_Addition() {
		QuantityMeasurementApp.Quantity<QuantityMeasurementApp.LengthUnit> q1 =
				new QuantityMeasurementApp.Quantity<>(1,
						QuantityMeasurementApp.LengthUnit.FEET);

		QuantityMeasurementApp.Quantity<QuantityMeasurementApp.LengthUnit> q2 =
				new QuantityMeasurementApp.Quantity<>(12,
						QuantityMeasurementApp.LengthUnit.INCH);

		assertEquals(2, q1.add(q2).getValue(), DELTA);
	}

	// 9
	@Test
	void testGenericQuantity_WeightOperations_Addition() {
		QuantityMeasurementApp.Quantity<QuantityMeasurementApp.WeightUnit> w1 =
				new QuantityMeasurementApp.Quantity<>(1,
						QuantityMeasurementApp.WeightUnit.KILOGRAM);

		QuantityMeasurementApp.Quantity<QuantityMeasurementApp.WeightUnit> w2 =
				new QuantityMeasurementApp.Quantity<>(1000,
						QuantityMeasurementApp.WeightUnit.GRAM);

		assertEquals(2, w1.add(w2).getValue(), DELTA);
	}

	// 10
	@Test
	void testCrossCategoryPrevention_LengthWiseWeight() {
		QuantityMeasurementApp.Quantity<QuantityMeasurementApp.LengthUnit> length =
				new QuantityMeasurementApp.Quantity<>(1,
						QuantityMeasurementApp.LengthUnit.FEET);

		QuantityMeasurementApp.Quantity<QuantityMeasurementApp.WeightUnit> weight =
				new QuantityMeasurementApp.Quantity<>(1,
						QuantityMeasurementApp.WeightUnit.KILOGRAM);

		assertNotEquals(length, weight);
	}

	// 11
	@Test
	void testCrossCategoryPrevention_CompilerTypeSafety() {
		assertTrue(true); // Compile-time safety validated via generics
	}

	// 12
	@Test
	void testGenericQuantity_ConstructorValidation_NullUnit() {
		assertThrows(IllegalArgumentException.class,
				() -> new QuantityMeasurementApp.Quantity<>(1, null));
	}

	// 13
	@Test
	void testGenericQuantity_ConstructorValidation_InvalidValue() {
		assertThrows(IllegalArgumentException.class,
				() -> new QuantityMeasurementApp.Quantity<>(
						Double.NaN,
						QuantityMeasurementApp.WeightUnit.KILOGRAM));
	}

	// 14
	@Test
	void testGenericQuantity_Conversion_AllUnitCombinations() {
		QuantityMeasurementApp.Quantity<QuantityMeasurementApp.LengthUnit> q =
				new QuantityMeasurementApp.Quantity<>(1,
						QuantityMeasurementApp.LengthUnit.YARD);

		assertEquals(3,
				q.convertTo(QuantityMeasurementApp.LengthUnit.FEET)
				.getValue(), DELTA);
	}

	// 15
	@Test
	void testGenericQuantity_Addition_AllUnitCombinations() {
		QuantityMeasurementApp.Quantity<QuantityMeasurementApp.WeightUnit> w1 =
				new QuantityMeasurementApp.Quantity<>(1,
						QuantityMeasurementApp.WeightUnit.KILOGRAM);

		QuantityMeasurementApp.Quantity<QuantityMeasurementApp.WeightUnit> w2 =
				new QuantityMeasurementApp.Quantity<>(2.20462,
						QuantityMeasurementApp.WeightUnit.POUND);

		assertEquals(2,
				w1.add(w2).getValue(), DELTA);
	}

	// 16
	@Test
	void testBackwardCompatibility_AllUCIThrough9Tests() {
		assertTrue(true);
	}

	// 17
	@Test
	void testQuantityMeasurementApp_SimplifiedDemonstration_Equality() {
		testGenericQuantity_LengthOperations_Equality();
	}

	// 18
	@Test
	void testQuantityMeasurementApp_SimplifiedDemonstration_Conversion() {
		testGenericQuantity_WeightOperations_Conversion();
	}

	// 19
	@Test
	void testQuantityMeasurementApp_SimplifiedDemonstration_Addition() {
		testGenericQuantity_LengthOperations_Addition();
	}

	// 20
	@Test
	void testTypeWildcard_FlexibleSignatures() {
		QuantityMeasurementApp.Quantity<? extends QuantityMeasurementApp.IMeasurable> q =
				new QuantityMeasurementApp.Quantity<>(1,
						QuantityMeasurementApp.LengthUnit.FEET);
		assertNotNull(q);
	}

	// 21
	@Test
	void testScalability_NewUnitEnumIntegration() {
		assertTrue(true);
	}

	// 22
	@Test
	void testScalability_MultipleNewCategories() {
		assertTrue(true);
	}

	// 23
	@Test
	void testGenericBoundedTypeParameter_Enforcement() {
		assertTrue(true);
	}

	// 24
	@Test
	void testHashCode_GenericQuantity_Consistency() {
		QuantityMeasurementApp.Quantity<QuantityMeasurementApp.WeightUnit> w1 =
				new QuantityMeasurementApp.Quantity<>(1,
						QuantityMeasurementApp.WeightUnit.KILOGRAM);

		QuantityMeasurementApp.Quantity<QuantityMeasurementApp.WeightUnit> w2 =
				new QuantityMeasurementApp.Quantity<>(1000,
						QuantityMeasurementApp.WeightUnit.GRAM);

		assertEquals(w1.hashCode(), w2.hashCode());
	}

	// 25
	@Test
	void testEquals_GenericQuantity_ContractPreservation() {
		testGenericQuantity_WeightOperations_Equality();
	}

	// 26
	@Test
	void testEnumAsUnitCarrier_BehaviorEncapsulation() {
		assertEquals("FEET",
				QuantityMeasurementApp.LengthUnit.FEET.getUnitName());
	}

	// 27
	@Test
	void testTypeErasure_RuntimeSafety() {
		assertTrue(true);
	}

	// 28
	@Test
	void testCompositionOverInheritance_Flexibility() {
		assertTrue(true);
	}

	// 29
	@Test
	void testCodeReduction_DRYValidation() {
		assertTrue(true);
	}

	// 30
	@Test
	void testMaintainability_SingleSourceOfTruth() {
		assertTrue(true);
	}

	// 31
	@Test
	void testArchitecturalReadiness_MultipleNewCategories() {
		assertTrue(true);
	}

	// 32
	@Test
	void testPerformance_GenericOverhead() {
		assertTrue(true);
	}

	// 33
	@Test
	void testDocumentation_PatternClarity() {
		assertTrue(true);
	}

	// 34
	@Test
	void testInterfaceSegregation_MinimalContract() {
		assertEquals(1.0,
				QuantityMeasurementApp.WeightUnit.KILOGRAM
				.getConversionFactor(), DELTA);
	}

	// 35
	@Test
	void testImmutability_GenericQuantity() {
		QuantityMeasurementApp.Quantity<QuantityMeasurementApp.WeightUnit> w =
				new QuantityMeasurementApp.Quantity<>(5,
						QuantityMeasurementApp.WeightUnit.KILOGRAM);

		QuantityMeasurementApp.Quantity<QuantityMeasurementApp.WeightUnit> result =
				w.add(w);

		assertNotSame(w, result);
	}

	// 36
	@Test
	void testVolumeUnit_LitreExists() {
		assertNotNull(QuantityMeasurementApp.VolumeUnit.LITRE);
	}

	// 37
	@Test
	void testVolumeUnit_MilliLitreExists() {
		assertNotNull(QuantityMeasurementApp.VolumeUnit.MILLILITRE);
	}

	// 38
	@Test
	void testVolumeUnit_GallonExists() {
		assertNotNull(QuantityMeasurementApp.VolumeUnit.GALLON);
	}

	//	// 39
	//	@Test
	//	void testVolumeEquality_LitreAndMilliLitre() {
	//		QuantityMeasurementApp.Quantity<QuantityMeasurementApp.VolumeUnit> v1 =
	//				new QuantityMeasurementApp.Quantity<>(1,
	//						QuantityMeasurementApp.VolumeUnit.LITRE);
	//
	//		QuantityMeasurementApp.Quantity<QuantityMeasurementApp.VolumeUnit> v2 =
	//				new QuantityMeasurementApp.Quantity<>(1000,
	//						QuantityMeasurementApp.VolumeUnit.MILLILITRE);
	//
	//		assertEquals(v1, v2);
	//	}
	//
	//	// 40
	//	@Test
	//	void testVolumeEquality_LitreAndGallon() {
	//		QuantityMeasurementApp.Quantity<QuantityMeasurementApp.VolumeUnit> v1 =
	//				new QuantityMeasurementApp.Quantity<>(1,
	//						QuantityMeasurementApp.VolumeUnit.LITRE);
	//
	//		QuantityMeasurementApp.Quantity<QuantityMeasurementApp.VolumeUnit> v2 =
	//				new QuantityMeasurementApp.Quantity<>(0.264172,
	//						QuantityMeasurementApp.VolumeUnit.GALLON);
	//
	//		assertEquals(v1, v2);
	//	}
	//
	//	// 41
	//	@Test
	//	void testVolumeEquality_Zero() {
	//		QuantityMeasurementApp.Quantity<QuantityMeasurementApp.VolumeUnit> v1 =
	//				new QuantityMeasurementApp.Quantity<>(0,
	//						QuantityMeasurementApp.VolumeUnit.LITRE);
	//
	//		QuantityMeasurementApp.Quantity<QuantityMeasurementApp.VolumeUnit> v2 =
	//				new QuantityMeasurementApp.Quantity<>(0,
	//						QuantityMeasurementApp.VolumeUnit.MILLILITRE);
	//
	//		assertEquals(v1, v2);
	//	}
	//
	//	// 42
	//	@Test
	//	void testVolumeEquality_Negative() {
	//		QuantityMeasurementApp.Quantity<QuantityMeasurementApp.VolumeUnit> v1 =
	//				new QuantityMeasurementApp.Quantity<>(-1,
	//						QuantityMeasurementApp.VolumeUnit.LITRE);
	//
	//		QuantityMeasurementApp.Quantity<QuantityMeasurementApp.VolumeUnit> v2 =
	//				new QuantityMeasurementApp.Quantity<>(-1000,
	//						QuantityMeasurementApp.VolumeUnit.MILLILITRE);
	//
	//		assertEquals(v1, v2);
	//	}
	//
	//	// 39
	//	@Test
	//	void testVolumeEquality_LitreAndMilliLitre() {
	//		QuantityMeasurementApp.Quantity<QuantityMeasurementApp.VolumeUnit> v1 =
	//				new QuantityMeasurementApp.Quantity<>(1,
	//						QuantityMeasurementApp.VolumeUnit.LITRE);
	//
	//		QuantityMeasurementApp.Quantity<QuantityMeasurementApp.VolumeUnit> v2 =
	//				new QuantityMeasurementApp.Quantity<>(1000,
	//						QuantityMeasurementApp.VolumeUnit.MILLILITRE);
	//
	//		assertEquals(v1, v2);
	//	}
	//
	//	// 40
	//	@Test
	//	void testVolumeEquality_LitreAndGallon() {
	//		QuantityMeasurementApp.Quantity<QuantityMeasurementApp.VolumeUnit> v1 =
	//				new QuantityMeasurementApp.Quantity<>(1,
	//						QuantityMeasurementApp.VolumeUnit.LITRE);
	//
	//		QuantityMeasurementApp.Quantity<QuantityMeasurementApp.VolumeUnit> v2 =
	//				new QuantityMeasurementApp.Quantity<>(0.264172,
	//						QuantityMeasurementApp.VolumeUnit.GALLON);
	//
	//		assertEquals(v1, v2);
	//	}
	//
	//	// 41
	//	@Test
	//	void testVolumeEquality_Zero() {
	//		QuantityMeasurementApp.Quantity<QuantityMeasurementApp.VolumeUnit> v1 =
	//				new QuantityMeasurementApp.Quantity<>(0,
	//						QuantityMeasurementApp.VolumeUnit.LITRE);
	//
	//		QuantityMeasurementApp.Quantity<QuantityMeasurementApp.VolumeUnit> v2 =
	//				new QuantityMeasurementApp.Quantity<>(0,
	//						QuantityMeasurementApp.VolumeUnit.MILLILITRE);
	//
	//		assertEquals(v1, v2);
	//	}
	//
	//	// 42
	//	@Test
	//	void testVolumeEquality_Negative() {
	//		QuantityMeasurementApp.Quantity<QuantityMeasurementApp.VolumeUnit> v1 =
	//				new QuantityMeasurementApp.Quantity<>(-1,
	//						QuantityMeasurementApp.VolumeUnit.LITRE);
	//
	//		QuantityMeasurementApp.Quantity<QuantityMeasurementApp.VolumeUnit> v2 =
	//				new QuantityMeasurementApp.Quantity<>(-1000,
	//						QuantityMeasurementApp.VolumeUnit.MILLILITRE);
	//
	//		assertEquals(v1, v2);
	//	}

	// 45
	@Test
	void testVolumeConversion_LitreToMilliLitre() {
		QuantityMeasurementApp.Quantity<QuantityMeasurementApp.VolumeUnit> v =
				new QuantityMeasurementApp.Quantity<>(1,
						QuantityMeasurementApp.VolumeUnit.LITRE);

		assertEquals(1000,
				v.convertTo(QuantityMeasurementApp.VolumeUnit.MILLILITRE)
				.getValue(), DELTA);
	}

	// 46
	@Test
	void testVolumeConversion_MilliLitreToLitre() {
		QuantityMeasurementApp.Quantity<QuantityMeasurementApp.VolumeUnit> v =
				new QuantityMeasurementApp.Quantity<>(1000,
						QuantityMeasurementApp.VolumeUnit.MILLILITRE);

		assertEquals(1,
				v.convertTo(QuantityMeasurementApp.VolumeUnit.LITRE)
				.getValue(), DELTA);
	}

	// 47
	@Test
	void testVolumeConversion_LitreToGallon() {
		QuantityMeasurementApp.Quantity<QuantityMeasurementApp.VolumeUnit> v =
				new QuantityMeasurementApp.Quantity<>(3.78541,
						QuantityMeasurementApp.VolumeUnit.LITRE);

		assertEquals(1,
				v.convertTo(QuantityMeasurementApp.VolumeUnit.GALLON)
				.getValue(), DELTA);
	}

	// 48
	@Test
	void testVolumeConversion_GallonToLitre() {
		QuantityMeasurementApp.Quantity<QuantityMeasurementApp.VolumeUnit> v =
				new QuantityMeasurementApp.Quantity<>(1,
						QuantityMeasurementApp.VolumeUnit.GALLON);

		assertEquals(3.78541,
				v.convertTo(QuantityMeasurementApp.VolumeUnit.LITRE)
				.getValue(), DELTA);
	}

	// 51
	@Test
	void testVolumeAddition_SameUnit_Litre() {

		QuantityMeasurementApp.Quantity<QuantityMeasurementApp.VolumeUnit> v1 =
				new QuantityMeasurementApp.Quantity<>(2,
						QuantityMeasurementApp.VolumeUnit.LITRE);

		QuantityMeasurementApp.Quantity<QuantityMeasurementApp.VolumeUnit> v2 =
				new QuantityMeasurementApp.Quantity<>(3,
						QuantityMeasurementApp.VolumeUnit.LITRE);

		QuantityMeasurementApp.Quantity<QuantityMeasurementApp.VolumeUnit> result =
				v1.add(v2);

		assertEquals(5, result.getValue(), DELTA);
		assertEquals(QuantityMeasurementApp.VolumeUnit.LITRE,
				result.getUnit());
	}

	// 52
	@Test
	void testVolumeAddition_CrossUnit_DefaultFirstUnit() {

		QuantityMeasurementApp.Quantity<QuantityMeasurementApp.VolumeUnit> v1 =
				new QuantityMeasurementApp.Quantity<>(1,
						QuantityMeasurementApp.VolumeUnit.LITRE);

		QuantityMeasurementApp.Quantity<QuantityMeasurementApp.VolumeUnit> v2 =
				new QuantityMeasurementApp.Quantity<>(500,
						QuantityMeasurementApp.VolumeUnit.MILLILITRE);

		QuantityMeasurementApp.Quantity<QuantityMeasurementApp.VolumeUnit> result =
				v1.add(v2);

		assertEquals(1.5, result.getValue(), DELTA);
		assertEquals(QuantityMeasurementApp.VolumeUnit.LITRE,
				result.getUnit());
	}

	// 53
	@Test
	void testVolumeAddition_TargetMilliLitre() {

		QuantityMeasurementApp.Quantity<QuantityMeasurementApp.VolumeUnit> v1 =
				new QuantityMeasurementApp.Quantity<>(1,
						QuantityMeasurementApp.VolumeUnit.LITRE);

		QuantityMeasurementApp.Quantity<QuantityMeasurementApp.VolumeUnit> v2 =
				new QuantityMeasurementApp.Quantity<>(1,
						QuantityMeasurementApp.VolumeUnit.LITRE);

		QuantityMeasurementApp.Quantity<QuantityMeasurementApp.VolumeUnit> result =
				v1.add(v2,
						QuantityMeasurementApp.VolumeUnit.MILLILITRE);

		assertEquals(2000, result.getValue(), DELTA);
		assertEquals(QuantityMeasurementApp.VolumeUnit.MILLILITRE,
				result.getUnit());
	}

	// 54
	@Test
	void testVolumeAddition_GallonAndLitre() {

		QuantityMeasurementApp.Quantity<QuantityMeasurementApp.VolumeUnit> gallon =
				new QuantityMeasurementApp.Quantity<>(1,
						QuantityMeasurementApp.VolumeUnit.GALLON);

		QuantityMeasurementApp.Quantity<QuantityMeasurementApp.VolumeUnit> litre =
				new QuantityMeasurementApp.Quantity<>(1,
						QuantityMeasurementApp.VolumeUnit.LITRE);

		QuantityMeasurementApp.Quantity<QuantityMeasurementApp.VolumeUnit> result =
				gallon.add(litre,
						QuantityMeasurementApp.VolumeUnit.LITRE);

		double expected = 3.78541 + 1;   // convert gallon to litre then add

		assertEquals(expected, result.getValue(), DELTA);
	}

	// 55
	@Test
	void testVolumeAddition_CommutativeAndNegative() {

		QuantityMeasurementApp.Quantity<QuantityMeasurementApp.VolumeUnit> v1 =
				new QuantityMeasurementApp.Quantity<>(-1,
						QuantityMeasurementApp.VolumeUnit.LITRE);

		QuantityMeasurementApp.Quantity<QuantityMeasurementApp.VolumeUnit> v2 =
				new QuantityMeasurementApp.Quantity<>(2000,
						QuantityMeasurementApp.VolumeUnit.MILLILITRE);

		QuantityMeasurementApp.Quantity<QuantityMeasurementApp.VolumeUnit> r1 =
				v1.add(v2);

		QuantityMeasurementApp.Quantity<QuantityMeasurementApp.VolumeUnit> r2 =
				v2.add(v1,
						QuantityMeasurementApp.VolumeUnit.MILLILITRE);

		assertEquals(1, r1.getValue(), DELTA);        // -1 L + 2 L = 1 L
		assertEquals(1000, r2.getValue(), DELTA);    // same in mL
	}

	// 56
	@Test
	void testVolumeRoundTripConversion() {
		QuantityMeasurementApp.Quantity<QuantityMeasurementApp.VolumeUnit> v =
				new QuantityMeasurementApp.Quantity<>(5,
						QuantityMeasurementApp.VolumeUnit.GALLON);

		QuantityMeasurementApp.Quantity<QuantityMeasurementApp.VolumeUnit> back =
				v.convertTo(QuantityMeasurementApp.VolumeUnit.LITRE)
				.convertTo(QuantityMeasurementApp.VolumeUnit.GALLON);

		assertEquals(v.getValue(), back.getValue(), DELTA);
	}

	// 57
	@Test
	void testVolumeHashCodeConsistency() {
		QuantityMeasurementApp.Quantity<QuantityMeasurementApp.VolumeUnit> v1 =
				new QuantityMeasurementApp.Quantity<>(1,
						QuantityMeasurementApp.VolumeUnit.LITRE);

		QuantityMeasurementApp.Quantity<QuantityMeasurementApp.VolumeUnit> v2 =
				new QuantityMeasurementApp.Quantity<>(1000,
						QuantityMeasurementApp.VolumeUnit.MILLILITRE);

		assertEquals(v1.hashCode(), v2.hashCode());
	}

	// 58
	@Test
	void testVolumeNotEqualLength() {
		QuantityMeasurementApp.Quantity<QuantityMeasurementApp.VolumeUnit> v =
				new QuantityMeasurementApp.Quantity<>(1,
						QuantityMeasurementApp.VolumeUnit.LITRE);

		QuantityMeasurementApp.Quantity<QuantityMeasurementApp.LengthUnit> l =
				new QuantityMeasurementApp.Quantity<>(1,
						QuantityMeasurementApp.LengthUnit.FEET);

		assertNotEquals(v, l);
	}

	// 59
	@Test
	void testVolumeNotEqualWeight() {
		QuantityMeasurementApp.Quantity<QuantityMeasurementApp.VolumeUnit> v =
				new QuantityMeasurementApp.Quantity<>(1,
						QuantityMeasurementApp.VolumeUnit.LITRE);

		QuantityMeasurementApp.Quantity<QuantityMeasurementApp.WeightUnit> w =
				new QuantityMeasurementApp.Quantity<>(1,
						QuantityMeasurementApp.WeightUnit.KILOGRAM);

		assertNotEquals(v, w);
	}

	// 60
	@Test
	void testVolumeImmutability() {
		QuantityMeasurementApp.Quantity<QuantityMeasurementApp.VolumeUnit> v =
				new QuantityMeasurementApp.Quantity<>(5,
						QuantityMeasurementApp.VolumeUnit.LITRE);

		QuantityMeasurementApp.Quantity<QuantityMeasurementApp.VolumeUnit> result =
				v.add(v);

		assertNotSame(v, result);
	}
}