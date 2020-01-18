/*
 * Copyright (C) 2007 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package testing;

import org.codehaus.plexus.component.annotations.*;

/**
 * ???
 *
 * @version $Id: MyComponent.java 6922 2007-10-01 04:21:46Z user57 $
 */
@Component(role=MyComponent.class, hint="foo")
public class MyComponent
{
    @Requirement
    private ChildComponent child;

    
    @Configuration("bar")
    private String foo;
}
