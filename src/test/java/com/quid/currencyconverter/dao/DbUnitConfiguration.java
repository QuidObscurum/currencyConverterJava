package com.quid.currencyconverter.dao;

import org.dbunit.DataSourceDatabaseTester;
import org.dbunit.DefaultPrepAndExpectedTestCase;
import org.dbunit.IDatabaseTester;
import org.dbunit.PrepAndExpectedTestCase;
import org.dbunit.util.fileloader.DataFileLoader;
import org.dbunit.util.fileloader.FlatXmlDataFileLoader;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.validation.annotation.Validated;

import javax.sql.DataSource;

@TestConfiguration
@Validated
public class DbUnitConfiguration
{
    /**
     * Extend DefaultPrepAndExpectedTestCase to customize DatabaseConfig.
     */
    private static class MyPrepAndExpectedTestCase extends DefaultPrepAndExpectedTestCase {

        public MyPrepAndExpectedTestCase (
                final DataFileLoader dataFileLoader,
                final IDatabaseTester databaseTester
        ) {
            super(dataFileLoader, databaseTester);
        }
    }

    /**
     * Create dbUnit {@link PrepAndExpectedTestCase} for running dbUnit database
     * tests.
     *
     * @param dataFileLoader
     *            The {@link DataFileLoader} used to load the test's specified
     *            data files.
     * @param databaseTester
     *            The {@link IDatabaseTester} used to run the tests against the
     *            database.
     * @return Configured dbUnit {@link PrepAndExpectedTestCase} for running
     *         dbUnit database tests.
     */
    @Bean
    public PrepAndExpectedTestCase prepAndExpectedTestCase(
            final DataFileLoader dataFileLoader,
            final IDatabaseTester databaseTester)
    {
        return new MyPrepAndExpectedTestCase(dataFileLoader, databaseTester);
    }

    /**
     * Create dbUnit {@link DataFileLoader} for loading the test's dbUnit data
     * files.
     *
//     * @param ddr
     *            Your local class containing the replacement definitions.
     * @return Configured dbUnit {@link DataFileLoader} for loading the test's
     *         dbUnit data files.
     */
    @Bean
    public DataFileLoader dataFileLoader() //(final DbunitDataReplacement ddr)
    {
//        final Map<String, Object> replacementObjects = ddr.getReplacementObjects();
//        final Map<String, Object> replacementSubstrings = ddr.getReplacementSubstrings();
//        return new FlatXmlDataFileLoader(replacementObjects, replacementSubstrings);
        return new FlatXmlDataFileLoader();
    }

    /**
     * Create dbUnit {@link IDatabaseTester}.
     *
     * @param dataSource
     *            The {@link DataSource} for the dbUnit test to use.
     * @return Configured dbUnit {@link IDatabaseTester}.
     */
    @Bean
    public IDatabaseTester databaseTester(final DataSource dataSource)
    {
        final DataSource dataSourceProxy = new TransactionAwareDataSourceProxy(dataSource);

        final IDatabaseTester databaseTester = new DataSourceDatabaseTester(dataSourceProxy);
//        databaseTester.setSetUpOperation(DatabaseOperation.INSERT);
//        databaseTester.setTearDownOperation(DatabaseOperation.DELETE_ALL);

        return databaseTester;
    }
}