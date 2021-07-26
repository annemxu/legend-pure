// Copyright 2021 Goldman Sachs
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package org.finos.legend.pure.m2.relational;

import org.finos.legend.pure.m3.AbstractPureTestWithCoreCompiled;
import org.finos.legend.pure.m3.exception.PureExecutionException;
import org.finos.legend.pure.m3.exception.PureExecutionExceptionContinue;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.sql.SQLException;
import java.util.ArrayList;

public class AbstractDatabaseQwertyTesting extends AbstractPureTestWithCoreCompiled {
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    //loops through function
    @Test
    public void testGeneralDatabaseCreation()
    {
        String[] databaseTypes = {"Redshift", "H2"};
        int nSize = databaseTypes.length;
        int nResultTests = databaseTypes.length;
        ArrayList<String> failures = new ArrayList<String>();
        ArrayList<String> results = new ArrayList<String>();

        for (String currType: databaseTypes) {
            compileTestSource(
                    "import meta::relational::runtime::*;\n" +
                            "import meta::relational::metamodel::*;\n" +
                            "import meta::relational::metamodel::execute::*;" +
                            "function test" + currType + "():Any[0..1]\n" +
                            "{\n" +
                            "   let dbConnection = ^TestDatabaseConnection(element = mydb" + currType + ", type = DatabaseType." + currType + ");" +
                            "   createTempTable('tt', ^Column(name='oid', type=^meta::relational::metamodel::datatype::Integer()), " +
                            "   {ttName:String[1], cols: Column[*], dbType: DatabaseType[1]| 'Create LOCAL TEMPORARY TABLE tt (oid INT)'}, true," +
                            "   $dbConnection);" +
                            "   let res = executeInDb('select * from tt', $dbConnection, 0, 1000);" +
                            "   let columnNames = $res.columnNames;" +
                            "   print($columnNames, 1);" +
                            "   assert('OID' == $columnNames, |'');" +
                            "}\n" +
                            "###Relational\n" +
                            "Database mydb" + currType+ "()\n"
            );
            try
            {
                compileAndExecute("test" + currType + "():Any[0..1]");
                System.out.println("Execution functions have made it succesfully so now we go to assertion");
                if( !(this.functionExecution.getConsole().getLine(0) == "'COL'")){
                    results.add(currType);
                }


            }
            catch (PureExecutionException ex)
            {
                failures.add(currType);
                nResultTests -= 1;

                System.out.println("Exception for " + currType + ": " + ex.getInfo());
//                throw ex;
            }
        }

        Assert.assertTrue("EXECUTION FAILED FOR " + failures.size() + " OF " + String.valueOf(nSize) + " DATABASES: " + failures + ", RESULT TEST FAILED FOR " + results.size() + " OF " + String.valueOf(nResultTests) + " DATABASES: " + results, failures.isEmpty() && results.isEmpty());

    }


    //h2 hardcoded in
    @Test
    public void testH2DatabaseCreation()
    {
        compileTestSource(
                "import meta::relational::runtime::*;\n" +
                        "import meta::relational::metamodel::*;\n" +
                        "import meta::relational::metamodel::execute::*;" +
                        "function test():Any[0..1]\n" +
                        "{\n" +
                        "   let dbConnection = ^TestDatabaseConnection(element = mydb, type = DatabaseType.H2);" +
                        "   createTempTable('tt', ^Column(name='oid', type=^meta::relational::metamodel::datatype::Integer()), " +
                        "   {ttName:String[1], cols: Column[*], dbType: DatabaseType[1]| 'Create LOCAL TEMPORARY TABLE tt (oid INT)'}, true," +
                        "   $dbConnection);" +
                        "   let res = executeInDb('select * from tt', $dbConnection, 0, 1000);" +
                        "   let columnNames = $res.columnNames;" +
                        "   print($columnNames, 1);" +
                        "   assert('OID' == $columnNames, |'');" +
                        "}\n" +
                        "###Relational\n" +
                        "Database mydb()\n"
        );
        try
        {
            compileAndExecute("test():Any[0..1]");
        }
        catch (PureExecutionException ex)
        {
            //expected
//            System.out.println(ex);
//            Assert.assertEquals("'COL'", this.functionExecution.getConsole().getLine(0));
            throw ex;
        }
    }


    //redshift hardcoded in
    @Test
    public void testRedshiftDatabaseCreation()
    {
        compileTestSource(
                "import meta::relational::runtime::*;\n" +
                        "import meta::relational::metamodel::*;\n" +
                        "import meta::relational::metamodel::execute::*;" +
                        "function test():Any[0..1]\n" +
                        "{\n" +
                        "   let dbConnection = ^TestDatabaseConnection(element = mydb, type = DatabaseType.Redshift);" +
                        "   createTempTable('tt', ^Column(name='oid', type=^meta::relational::metamodel::datatype::Integer()), " +
                        "   {ttName:String[1], cols: Column[*], dbType: DatabaseType[1]| 'Create LOCAL TEMPORARY TABLE tt (oid INT)'}, true," +
                        "   $dbConnection);" +
                        "   let res = executeInDb('select * from tt', $dbConnection, 0, 1000);" +
                        "   let columnNames = $res.columnNames;" +
                        "   print($columnNames, 1);" +
                        "   assert('OID' == $columnNames, |'');" +
                        "}\n" +
                        "###Relational\n" +
                        "Database mydb()\n"
        );
        try
        {
            compileAndExecute("test():Any[0..1]");
        }
        catch (PureExecutionException ex)
        {
            throw ex;
        }
    }
}
