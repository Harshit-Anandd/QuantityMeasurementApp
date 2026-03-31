package com.app.quantitymeasurement.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import com.app.quantitymeasurement.model.QuantityMeasurementEntity;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

@DataJpaTest
class QuantityMeasurementRepositoryTests {

    @Autowired
    private QuantityMeasurementRepository repository;

    @Autowired
    private PlatformTransactionManager transactionManager;

    private QuantityMeasurementEntity buildEntity(String operation, boolean isError) {
        QuantityMeasurementEntity entity = new QuantityMeasurementEntity();
        entity.thisValue = 1.0;
        entity.thisUnit = "FEET";
        entity.thisMeasurementType = "LengthUnit";
        entity.thatValue = 12.0;
        entity.thatUnit = "INCHES";
        entity.thatMeasurementType = "LengthUnit";
        entity.operation = operation;
        entity.isError = isError;
        entity.resultValue = 1.0;
        entity.resultUnit = "FEET";
        entity.resultMeasurementType = "LengthUnit";
        entity.resultString = "true";
        if (isError) {
            entity.errorMessage = "Simulated error";
        }
        return entity;
    }

    @Test
    @DisplayName("findByOperation returns only matching operation records")
    void testJPARepositoryFindByOperation() {
        repository.save(buildEntity("COMPARE", false));
        repository.save(buildEntity("ADD", false));

        List<QuantityMeasurementEntity> result = repository.findByOperation("COMPARE");

        assertThat(result).hasSize(1);
        assertThat(result.get(0).operation).isEqualTo("COMPARE");
    }

    @Test
    @DisplayName("findSuccessfulOperations returns matching non-error records")
    void testJPARepositoryCustomQuery() {
        repository.save(buildEntity("ADD", false));
        repository.save(buildEntity("ADD", true));
        repository.save(buildEntity("SUBTRACT", false));

        List<QuantityMeasurementEntity> successfulAdds = repository.findSuccessfulOperations("ADD");

        assertThat(successfulAdds).hasSize(1);
        assertThat(successfulAdds.get(0).operation).isEqualTo("ADD");
        assertThat(successfulAdds.get(0).isError).isFalse();
    }

    @Test
    @DisplayName("transaction rolls back repository writes when exception occurs")
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    void testTransactionalRollback() {
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);

        Throwable thrown = catchThrowable(() ->
                transactionTemplate.executeWithoutResult(status -> {
                    repository.saveAndFlush(buildEntity("ROLLBACK_TEST", false));
                    throw new IllegalStateException("Force rollback");
                }));

        assertThat(thrown).isInstanceOf(IllegalStateException.class);
        assertThat(repository.findByOperation("ROLLBACK_TEST")).isEmpty();
    }
}
