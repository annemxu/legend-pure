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

package org.finos.legend.pure.m3.exception;

import org.finos.legend.pure.m4.exception.PureException;
import org.finos.legend.pure.m4.coreinstance.SourceInformation;

/**
 * An exception raised when something goes wrong during Pure execution.
 */
public class PureExecutionExceptionContinue extends PureException
{
    public PureExecutionExceptionContinue(SourceInformation sourceInformation, String info, Throwable cause)
    {
        super(sourceInformation, info, cause);
        System.out.println("Exceptions are happenin! ");
        System.out.println(info);
        System.out.println(cause);

    }

    public PureExecutionExceptionContinue(SourceInformation sourceInformation, String info)
    {
        super(sourceInformation, info, null);
    }

    public PureExecutionExceptionContinue(SourceInformation sourceInformation, Throwable cause)
    {
        super(sourceInformation, null, cause);
    }

    public PureExecutionExceptionContinue(String info, Throwable cause)
    {
        super(info, cause);
    }

    public PureExecutionExceptionContinue(SourceInformation sourceInformation)
    {
        super(sourceInformation);
    }

    public PureExecutionExceptionContinue(String info)
    {
        super(info);
    }

    public PureExecutionExceptionContinue(Throwable cause)
    {
        super(cause);
    }

    public PureExecutionExceptionContinue()
    {
        super();
    }

    @Override
    public String getExceptionName()
    {
        return "A type of execution error";
    }
}
