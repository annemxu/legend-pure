// Copyright 2020 Goldman Sachs
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

package org.finos.legend.pure.runtime.java.compiled;

import org.finos.legend.pure.m3.execution.FunctionExecution;
import org.finos.legend.pure.m3.tests.function.base.coreRelationalDatabase.AbstractTestQwerty;
import org.finos.legend.pure.m3.tests.function.base.measure.AbstractTestMeasure;
import org.finos.legend.pure.m4.exception.PureCompilationException;
import org.finos.legend.pure.runtime.java.compiled.execution.FunctionExecutionCompiledBuilder;
import org.junit.After;
import org.junit.BeforeClass;

public class TestQwertyRedshiftCompiled extends AbstractTestQwerty
{
    @BeforeClass
    public static void setUp() {
        setUpRuntime(getFunctionExecution());
    }
    @After
    public void clearRuntime() {
//        runtime.delete("testModel.pure");
//        runtime.delete("testFunc.pure");
    }
    protected static FunctionExecution getFunctionExecution()
    {
        return new FunctionExecutionCompiledBuilder().build();
    }
}
