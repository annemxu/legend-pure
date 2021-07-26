package org.finos.legend.pure.m3.tests.function.base.coreRelationalDatabase;

//package org.finos.legend.pure.m2.relational;

import org.finos.legend.pure.m3.AbstractPureTestWithCoreCompiled;
import org.finos.legend.pure.m3.exception.PureExecutionException;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;


public class AbstractTestQwerty extends AbstractPureTestWithCoreCompiled {

    //resource -function for test -> not junit not@test, databasetype paramter in agument


    @Test
    public void testRelyOnFinallyTempTableFlow2()
    {
        compileTestSource(
                "import meta::relational::runtime::*;\n" +
                        "import meta::relational::metamodel::*;\n" +
                        "import meta::relational::metamodel::execute::*;" +
                        "function test():Any[0..1]\n" +
                        "{\n" +
                        "   let dbConnection = ^TestDatabaseConnection(element = mydb, type = DatabaseType.H2);" +
                        "   createTempTable('tt', ^Column(name='col', type=^meta::relational::metamodel::datatype::Integer()), " +
                        "   {ttName:String[1], cols: Column[*], dbType: DatabaseType[1]| 'Create LOCAL TEMPORARY TABLE tt (col INT)'}, true," +
                        "   $dbConnection);" +
                        "   let res = executeInDb('select * from tt', $dbConnection, 0, 1000);" +
                        "   let columnNames = $res.columnNames;" +
                        "   print($columnNames, 1);" +
                        "   assert('COL' == $columnNames, |'');" +
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
            Assert.assertEquals("'COL'", this.functionExecution.getConsole().getLine(0));
            throw ex;
        }
    }


    //    ->testModel <-blank at first

}