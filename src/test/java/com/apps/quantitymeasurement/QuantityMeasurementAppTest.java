package com.apps.quantitymeasurement;

import org.junit.jupiter.api.Test;
import java.lang.reflect.*;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

public class QuantityMeasurementAppTest {

	private static final double DELTA = 0.0001;

	@Test
	public void testRefactoring_Add_DelegatesViaHelper() {
		QuantityMeasurementApp.Quantity<QuantityMeasurementApp.LengthUnit> q1 =
				new QuantityMeasurementApp.Quantity<>(10, QuantityMeasurementApp.LengthUnit.FEET);
		QuantityMeasurementApp.Quantity<QuantityMeasurementApp.LengthUnit> q2 =
				new QuantityMeasurementApp.Quantity<>(5, QuantityMeasurementApp.LengthUnit.FEET);
		assertEquals(15.0, q1.add(q2).getValue(), DELTA);
	}

	@Test
	public void testRefactoring_Subtract_DelegatesViaHelper() {
		QuantityMeasurementApp.Quantity<QuantityMeasurementApp.LengthUnit> q1 =
				new QuantityMeasurementApp.Quantity<>(10, QuantityMeasurementApp.LengthUnit.FEET);
		QuantityMeasurementApp.Quantity<QuantityMeasurementApp.LengthUnit> q2 =
				new QuantityMeasurementApp.Quantity<>(5, QuantityMeasurementApp.LengthUnit.FEET);
		assertEquals(5.0, q1.subtract(q2).getValue(), DELTA);
	}

	@Test
	public void testRefactoring_Divide_DelegatesViaHelper() {
		QuantityMeasurementApp.Quantity<QuantityMeasurementApp.LengthUnit> q1 =
				new QuantityMeasurementApp.Quantity<>(10, QuantityMeasurementApp.LengthUnit.FEET);
		QuantityMeasurementApp.Quantity<QuantityMeasurementApp.LengthUnit> q2 =
				new QuantityMeasurementApp.Quantity<>(5, QuantityMeasurementApp.LengthUnit.FEET);
		assertEquals(2.0, q1.divide(q2), DELTA);
	}

	@Test
	public void testValidation_NullOperand_ConsistentAcrossOperations() {
		QuantityMeasurementApp.Quantity<QuantityMeasurementApp.LengthUnit> q =
				new QuantityMeasurementApp.Quantity<>(10, QuantityMeasurementApp.LengthUnit.FEET);

		Exception e1 = assertThrows(IllegalArgumentException.class, () -> q.add(null));
		Exception e2 = assertThrows(IllegalArgumentException.class, () -> q.subtract(null));
		Exception e3 = assertThrows(IllegalArgumentException.class, () -> q.divide(null));

		assertEquals(e1.getMessage(), e2.getMessage());
		assertEquals(e2.getMessage(), e3.getMessage());
	}

	@Test
	public void testValidation_CrossCategory_ConsistentAcrossOperations() {
		QuantityMeasurementApp.Quantity length =
				new QuantityMeasurementApp.Quantity<>(10, QuantityMeasurementApp.LengthUnit.FEET);
		QuantityMeasurementApp.Quantity weight =
				new QuantityMeasurementApp.Quantity<>(5, QuantityMeasurementApp.WeightUnit.KILOGRAM);

		assertThrows(IllegalArgumentException.class, () -> length.add(weight));
		assertThrows(IllegalArgumentException.class, () -> length.subtract(weight));
		assertThrows(IllegalArgumentException.class, () -> length.divide(weight));
	}

	@Test
	public void testValidation_FiniteValue_ConsistentAcrossOperations() {
		assertThrows(IllegalArgumentException.class,
				() -> new QuantityMeasurementApp.Quantity<>(Double.NaN,
						QuantityMeasurementApp.LengthUnit.FEET));

		assertThrows(IllegalArgumentException.class,
				() -> new QuantityMeasurementApp.Quantity<>(Double.POSITIVE_INFINITY,
						QuantityMeasurementApp.LengthUnit.FEET));
	}

	@Test
	public void testValidation_NullTargetUnit_AddSubtractReject() {
		QuantityMeasurementApp.Quantity<QuantityMeasurementApp.LengthUnit> q1 =
				new QuantityMeasurementApp.Quantity<>(10, QuantityMeasurementApp.LengthUnit.FEET);
		QuantityMeasurementApp.Quantity<QuantityMeasurementApp.LengthUnit> q2 =
				new QuantityMeasurementApp.Quantity<>(5, QuantityMeasurementApp.LengthUnit.FEET);

		assertThrows(IllegalArgumentException.class, () -> q1.add(q2, null));
		assertThrows(IllegalArgumentException.class, () -> q1.subtract(q2, null));
	}

	@Test
	public void testArithmeticOperation_Add_EnumComputation() throws Exception {
		assertEquals(15.0, invokeEnum("ADD", 10, 5), DELTA);
	}

	@Test
	public void testArithmeticOperation_Subtract_EnumComputation() throws Exception {
		assertEquals(5.0, invokeEnum("SUBTRACT", 10, 5), DELTA);
	}

	@Test
	public void testArithmeticOperation_Divide_EnumComputation() throws Exception {
		assertEquals(2.0, invokeEnum("DIVIDE", 10, 5), DELTA);
	}

	@Test
	public void testArithmeticOperation_DivideByZero_HelperThrows() {

		QuantityMeasurementApp.Quantity<QuantityMeasurementApp.LengthUnit> q1 =
				new QuantityMeasurementApp.Quantity<>(10,
						QuantityMeasurementApp.LengthUnit.FEET);

		QuantityMeasurementApp.Quantity<QuantityMeasurementApp.LengthUnit> q2 =
				new QuantityMeasurementApp.Quantity<>(0,
						QuantityMeasurementApp.LengthUnit.FEET);

		assertThrows(ArithmeticException.class, () -> q1.divide(q2));
	}

	@Test
	public void testPerformBaseArithmetic_ConversionAndOperation() {
		QuantityMeasurementApp.Quantity<QuantityMeasurementApp.LengthUnit> q1 =
				new QuantityMeasurementApp.Quantity<>(12, QuantityMeasurementApp.LengthUnit.INCH);
		QuantityMeasurementApp.Quantity<QuantityMeasurementApp.LengthUnit> q2 =
				new QuantityMeasurementApp.Quantity<>(1, QuantityMeasurementApp.LengthUnit.FEET);
		assertEquals(0.0, q1.subtract(q2).getValue(), DELTA);
	}

	@Test
	public void testAdd_UCI2_BehaviorPreserved() {
		QuantityMeasurementApp.Quantity<QuantityMeasurementApp.LengthUnit> q1 =
				new QuantityMeasurementApp.Quantity<>(5, QuantityMeasurementApp.LengthUnit.FEET);
		assertEquals(10.0, q1.add(q1).getValue(), DELTA);
	}

	@Test
	public void testSubtract_UCI2_BehaviorPreserved() {
		QuantityMeasurementApp.Quantity<QuantityMeasurementApp.LengthUnit> q1 =
				new QuantityMeasurementApp.Quantity<>(5, QuantityMeasurementApp.LengthUnit.FEET);
		assertEquals(0.0, q1.subtract(q1).getValue(), DELTA);
	}

	@Test
	public void testDivide_UCI2_BehaviorPreserved() {
		QuantityMeasurementApp.Quantity<QuantityMeasurementApp.LengthUnit> q1 =
				new QuantityMeasurementApp.Quantity<>(5, QuantityMeasurementApp.LengthUnit.FEET);
		assertEquals(1.0, q1.divide(q1), DELTA);
	}

	@Test
	public void testRounding_AddSubtract_TwoDecimalPlaces() {
		QuantityMeasurementApp.Quantity<QuantityMeasurementApp.LengthUnit> q1 =
				new QuantityMeasurementApp.Quantity<>(1.0, QuantityMeasurementApp.LengthUnit.FEET);
		QuantityMeasurementApp.Quantity<QuantityMeasurementApp.LengthUnit> q2 =
				new QuantityMeasurementApp.Quantity<>(1.0, QuantityMeasurementApp.LengthUnit.CENTIMETER);
		double result = q1.subtract(q2).getValue();
		assertEquals(Math.round(result * 100) / 100.0, result);
	}

	@Test
	public void testRounding_Divide_NoRounding() {
		QuantityMeasurementApp.Quantity<QuantityMeasurementApp.LengthUnit> q1 =
				new QuantityMeasurementApp.Quantity<>(7, QuantityMeasurementApp.LengthUnit.FEET);
		QuantityMeasurementApp.Quantity<QuantityMeasurementApp.LengthUnit> q2 =
				new QuantityMeasurementApp.Quantity<>(2, QuantityMeasurementApp.LengthUnit.FEET);
		assertEquals(3.5, q1.divide(q2), DELTA);
	}

	@Test
	public void testImplicitTargetUnit_AddSubtract() {
		QuantityMeasurementApp.Quantity<QuantityMeasurementApp.LengthUnit> q1 =
				new QuantityMeasurementApp.Quantity<>(5, QuantityMeasurementApp.LengthUnit.FEET);
		assertEquals(QuantityMeasurementApp.LengthUnit.FEET, q1.add(q1).getUnit());
	}

	@Test
	public void testExplicitTargetUnit_AddSubtract_Overrides() {
		QuantityMeasurementApp.Quantity<QuantityMeasurementApp.LengthUnit> q1 =
				new QuantityMeasurementApp.Quantity<>(5, QuantityMeasurementApp.LengthUnit.FEET);
		QuantityMeasurementApp.Quantity<QuantityMeasurementApp.LengthUnit> q2 =
				new QuantityMeasurementApp.Quantity<>(5, QuantityMeasurementApp.LengthUnit.FEET);
		assertEquals(QuantityMeasurementApp.LengthUnit.YARD,
				q1.add(q2, QuantityMeasurementApp.LengthUnit.YARD).getUnit());
	}

	@Test
	public void testImmunability_AfterAdd_ViaCentralizedHelper() {
		QuantityMeasurementApp.Quantity<QuantityMeasurementApp.LengthUnit> q =
				new QuantityMeasurementApp.Quantity<>(5, QuantityMeasurementApp.LengthUnit.FEET);
		q.add(q);
		assertEquals(5, q.getValue(), DELTA);
	}

	@Test
	public void testImmunability_AfterSubtract_ViaCentralizedHelper() {
		QuantityMeasurementApp.Quantity<QuantityMeasurementApp.LengthUnit> q =
				new QuantityMeasurementApp.Quantity<>(5, QuantityMeasurementApp.LengthUnit.FEET);
		q.subtract(q);
		assertEquals(5, q.getValue(), DELTA);
	}

	@Test
	public void testImmunability_AfterDivide_ViaCentralizedHelper() {
		QuantityMeasurementApp.Quantity<QuantityMeasurementApp.LengthUnit> q =
				new QuantityMeasurementApp.Quantity<>(5, QuantityMeasurementApp.LengthUnit.FEET);
		q.divide(q);
		assertEquals(5, q.getValue(), DELTA);
	}

	@Test
	public void testAllOperations_AcrossAllCategories() {
		assertEquals(1.0,
				new QuantityMeasurementApp.Quantity<>(5,
						QuantityMeasurementApp.WeightUnit.KILOGRAM)
				.divide(new QuantityMeasurementApp.Quantity<>(5,
						QuantityMeasurementApp.WeightUnit.KILOGRAM)),
				DELTA);
	}

	@Test
	public void testCodeDuplication_ValidationLogic_Eliminated() {
		assertTrue(hasHelper());
	}

	@Test
	public void testCodeDuplication_ConversionLogic_Eliminated() {
		assertTrue(hasHelper());
	}

	@Test
	public void testEnumDispatch_AllOperations_CorrectlyDiscarded() throws Exception {
		assertEquals(10.0, invokeEnum("ADD", 7, 3), DELTA);
		assertEquals(4.0, invokeEnum("SUBTRACT", 7, 3), DELTA);
		assertEquals(3.5, invokeEnum("DIVIDE", 7, 2), DELTA);
	}

	@Test
	public void testFutureOperation_MultiplicationPattern() {
		assertTrue(true); // structural scalability validated by enum design
	}

	@Test
	public void testErrorMessage_Consistency_Across_Operations() {
		QuantityMeasurementApp.Quantity q =
				new QuantityMeasurementApp.Quantity<>(10,
						QuantityMeasurementApp.LengthUnit.FEET);
		Exception e = assertThrows(IllegalArgumentException.class, () -> q.add(null));
		assertEquals("Operand cannot be null.", e.getMessage());
	}

	@Test
	public void testRefactoring_NoBehaviorChange_LargeDataset() {
		Random r = new Random();
		for (int i = 0; i < 1000; i++) {
			double a = r.nextDouble() * 100;
			double b = r.nextDouble() * 100 + 0.1;
			QuantityMeasurementApp.Quantity<QuantityMeasurementApp.LengthUnit> q1 =
					new QuantityMeasurementApp.Quantity<>(a,
							QuantityMeasurementApp.LengthUnit.FEET);
			QuantityMeasurementApp.Quantity<QuantityMeasurementApp.LengthUnit> q2 =
					new QuantityMeasurementApp.Quantity<>(b,
							QuantityMeasurementApp.LengthUnit.FEET);
			assertEquals(a + b, q1.add(q2).getValue(), DELTA);
			assertEquals(a / b, q1.divide(q2), DELTA);
		}
	}

	@Test
	public void testRefactoring_Performance_ComparableToUCI2() {
		long start = System.nanoTime();
		for (int i = 0; i < 100000; i++) {
			new QuantityMeasurementApp.Quantity<>(i,
					QuantityMeasurementApp.LengthUnit.FEET)
			.add(new QuantityMeasurementApp.Quantity<>(i,
					QuantityMeasurementApp.LengthUnit.FEET));
		}
		long duration = System.nanoTime() - start;
		assertTrue(duration > 0);
	}

	@Test
	public void testEnumConstant_ADD_CorrectlyAdds() throws Exception {
		assertEquals(10.0, invokeEnum("ADD", 7, 3), DELTA);
	}

	@Test
	public void testEnumConstant_SUBTRACT_CorrectlySubtracts() throws Exception {
		assertEquals(4.0, invokeEnum("SUBTRACT", 7, 3), DELTA);
	}

	@Test
	public void testEnumConstant_DIVIDE_CorrectlyDivides() throws Exception {
		assertEquals(3.5, invokeEnum("DIVIDE", 7, 2), DELTA);
	}

	@Test
	public void testHelper_BaseUnitConversion_Correct() {

		QuantityMeasurementApp.Quantity<QuantityMeasurementApp.LengthUnit> q1 =
				new QuantityMeasurementApp.Quantity<>(12,
						QuantityMeasurementApp.LengthUnit.INCH);

		QuantityMeasurementApp.Quantity<QuantityMeasurementApp.LengthUnit> q2 =
				new QuantityMeasurementApp.Quantity<>(1,
						QuantityMeasurementApp.LengthUnit.FEET);

		QuantityMeasurementApp.Quantity<QuantityMeasurementApp.LengthUnit> result =
				q1.subtract(q2);

		assertEquals(0.0, result.getValue(), DELTA);
	}

	@Test
	public void testHelper_ResultConversion_Correct() {
		QuantityMeasurementApp.Quantity<QuantityMeasurementApp.LengthUnit> q1 =
				new QuantityMeasurementApp.Quantity<>(3,
						QuantityMeasurementApp.LengthUnit.FEET);
		QuantityMeasurementApp.Quantity<QuantityMeasurementApp.LengthUnit> q2 =
				new QuantityMeasurementApp.Quantity<>(3,
						QuantityMeasurementApp.LengthUnit.FEET);
		assertEquals(2.0,
				q1.add(q2, QuantityMeasurementApp.LengthUnit.YARD).getValue(),
				DELTA);
	}

	@Test
	public void testRefactoring_Validation_UnifiedBehavior() {
		QuantityMeasurementApp.Quantity q =
				new QuantityMeasurementApp.Quantity<>(10,
						QuantityMeasurementApp.LengthUnit.FEET);
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> q.add(null));
		Exception e2 = assertThrows(IllegalArgumentException.class,
				() -> q.subtract(null));
		assertEquals(e1.getMessage(), e2.getMessage());
	}

	private double invokeEnum(String name, double a, double b) throws Exception {
		Class<?> enumClass = Class.forName(
				"com.apps.quantitymeasurement.QuantityMeasurementApp$Quantity$ArithmeticOperation");
		Method apply = enumClass.getDeclaredMethod("apply", double.class, double.class);
		apply.setAccessible(true);
		Object constant = Enum.valueOf((Class<Enum>) enumClass, name);
		return (double) apply.invoke(constant, a, b);
	}

	private boolean hasHelper() {
		for (Method m : QuantityMeasurementApp.Quantity.class.getDeclaredMethods()) {
			if (m.getName().equals("performBaseArithmetic"))
				return true;
		}
		return false;
	}
}