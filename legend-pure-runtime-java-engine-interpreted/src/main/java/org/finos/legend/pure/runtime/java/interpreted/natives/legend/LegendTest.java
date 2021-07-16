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

package org.finos.legend.pure.runtime.java.interpreted.natives.legend;

import org.eclipse.collections.api.list.ListIterable;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.map.MutableMap;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.finos.legend.pure.m3.compiler.Context;
import org.finos.legend.pure.m3.coreinstance.meta.pure.metamodel.function.FunctionCoreInstanceWrapper;
import org.finos.legend.pure.m3.exception.PureExecutionException;
import org.finos.legend.pure.m3.navigation.Instance;
import org.finos.legend.pure.m3.navigation.M3Properties;
import org.finos.legend.pure.m3.navigation.ProcessorSupport;
import org.finos.legend.pure.m3.navigation.ValueSpecificationBootstrap;
import org.finos.legend.pure.m4.ModelRepository;
import org.finos.legend.pure.m4.coreinstance.CoreInstance;
//import org.finos.legend.pure.m4.logs.PureLogger;
import org.finos.legend.pure.runtime.java.interpreted.ExecutionSupport;
import org.finos.legend.pure.runtime.java.interpreted.FunctionExecutionInterpreted;
import org.finos.legend.pure.runtime.java.interpreted.VariableContext;
import org.finos.legend.pure.runtime.java.interpreted.natives.core.InstantiationContext;
import org.finos.legend.pure.runtime.java.interpreted.natives.core.NativeFunction;
import org.finos.legend.pure.runtime.java.interpreted.profiler.Profiler;

import java.util.Stack;
import java.util.logging.Logger;

public class LegendTest extends NativeFunction
{
    private final FunctionExecutionInterpreted functionExecution;
    private final ModelRepository repository;
    private static final Logger logger = Logger.getLogger(String.valueOf(LegendTest.class));


    public LegendTest(ModelRepository repository, FunctionExecutionInterpreted functionExecution)
    {
        this.functionExecution = functionExecution;
        this.repository = repository;


    }

    @Override
    public CoreInstance execute(ListIterable<? extends CoreInstance> params, Stack<MutableMap<String, CoreInstance>> resolvedTypeParameters, Stack<MutableMap<String, CoreInstance>> resolvedMultiplicityParameters, VariableContext variableContext, CoreInstance functionExpressionToUseInStack, Profiler profiler, InstantiationContext instantiationContext, ExecutionSupport executionSupport, Context context, final ProcessorSupport processorSupport) throws PureExecutionException
    {
        logger.info("qwerty: testing execute");
        String clientVersion = System.getProperty("legend.test.clientVersion");
        String serverVersion = System.getProperty("legend.test.serverVersion");
        String serializationKind = System.getProperty("legend.test.serializationKind");
        String databaseType = System.getProperty("legend.test.databaseType");

        System.out.println("qwerty: databaseType - " + databaseType);

        String host = System.getProperty("legend.test.server.host");
        int port = System.getProperty("legend.test.server.port") == null ? -1 : Integer.parseInt(System.getProperty("legend.test.server.port"));

        if (host != null)
        {
            if (port == -1)
            {
                throw new PureExecutionException(functionExpressionToUseInStack.getSourceInformation(), "The system variable 'legend.test.server.host' is set to '"+host+"' however 'legend.test.server.port' has not been set!");
            }
            if (databaseType == null || databaseType.equals("h2defaulttest"))
            {
                logger.info("qwerty: database type was null going to set to h2defaulttest");
                databaseType="h2defaulttest";
            }
            if (serializationKind == null || !(serializationKind.equals("text") || serializationKind.equals("json")))
            {
                //qwerty changes likeso
                serializationKind="json";
            }
            if (clientVersion == null)
            {
                throw new PureExecutionException(functionExpressionToUseInStack.getSourceInformation(), "The system variable 'legend.test.clientVersion' should be set");
            }
            if (serverVersion == null)
            {
                throw new PureExecutionException(functionExpressionToUseInStack.getSourceInformation(), "The system variable 'legend.test.serverVersion' should be set");
            }
            MutableList<CoreInstance> fParams = FastList.<CoreInstance>newListWith(
                    ValueSpecificationBootstrap.newStringLiteral(this.repository, clientVersion, this.functionExecution.getProcessorSupport()),
                    ValueSpecificationBootstrap.newStringLiteral(this.repository, serverVersion, this.functionExecution.getProcessorSupport()),
                    ValueSpecificationBootstrap.newStringLiteral(this.repository, serializationKind, this.functionExecution.getProcessorSupport()),
                    ValueSpecificationBootstrap.newStringLiteral(this.repository, host, this.functionExecution.getProcessorSupport()),
                    ValueSpecificationBootstrap.newIntegerLiteral(this.repository, port, this.functionExecution.getProcessorSupport()),
                    ValueSpecificationBootstrap.newStringLiteral(this.repository, databaseType, this.functionExecution.getProcessorSupport())
            );

            logger.info("test database type is " + databaseType + ", returning alloy executioning");

            return this.functionExecution.executeFunctionExecuteParams(FunctionCoreInstanceWrapper.toFunction(Instance.getValueForMetaPropertyToOneResolved(params.get(0), M3Properties.values, processorSupport)),
                    fParams,
                    resolvedTypeParameters,
                    resolvedMultiplicityParameters,
                    getParentOrEmptyVariableContext(variableContext),
                    functionExpressionToUseInStack,
                    profiler,
                    instantiationContext,
                    executionSupport);
        }
        else
        {
            logger.info("qwerty: function alloy executioning part 2");


            return this.functionExecution.executeFunctionExecuteParams(FunctionCoreInstanceWrapper.toFunction(Instance.getValueForMetaPropertyToOneResolved(params.get(1), M3Properties.values, processorSupport)),
                    FastList.<CoreInstance>newList(),
                    resolvedTypeParameters,
                    resolvedMultiplicityParameters,
                    getParentOrEmptyVariableContext(variableContext),
                    functionExpressionToUseInStack,
                    profiler,
                    instantiationContext,
                    executionSupport);

        }
    }

}
