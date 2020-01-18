/*
 * Copyright (C) 2007 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package org.codehaus.plexus.metadata;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.codehaus.plexus.component.repository.ComponentDescriptor;
import org.codehaus.plexus.metadata.gleaner.QDoxComponentGleaner;
import org.codehaus.plexus.metadata.gleaner.SourceComponentGleaner;
import org.codehaus.plexus.util.StringUtils;

import com.thoughtworks.qdox.JavaDocBuilder;
import com.thoughtworks.qdox.model.JavaClass;

/**
 * Extracts {@link ComponentDescriptor} from source files.
 * 
 * @version $Rev: 8174 $ $Date: 2009-05-11 17:15:23 +0200 (Mon, 11 May 2009) $
 */
public class SourceComponentDescriptorExtractor
    extends ComponentDescriptorExtractorSupport
{
    private SourceComponentGleaner gleaner;

    public SourceComponentDescriptorExtractor()
    {
    }

    public SourceComponentDescriptorExtractor( final SourceComponentGleaner gleaner )
    {
        this.gleaner = gleaner;
    }

    public List<ComponentDescriptor<?>> extract( MetadataGenerationRequest configuration, final ComponentDescriptor<?>[] roleDefaults )
        throws Exception
    {
        if ( gleaner == null )
        {
            gleaner = new QDoxComponentGleaner();
        }

        return extract( configuration.sourceDirectories, configuration.sourceEncoding, getDefaultsByRole( roleDefaults ) );
    }

    private List<ComponentDescriptor<?>> extract( final List<String> sourceDirectories, final String sourceEncoding, final Map<String, ComponentDescriptor<?>> defaultsByRole )
        throws Exception
    {
        assert sourceDirectories != null;
        assert defaultsByRole != null;

        List<ComponentDescriptor<?>> descriptors = new ArrayList<ComponentDescriptor<?>>();

        // Scan the sources
        JavaDocBuilder builder = new JavaDocBuilder();

        if ( StringUtils.isNotEmpty( sourceEncoding ) )
        {
            builder.setEncoding( sourceEncoding );
        }

        for ( String sourceDirectory : sourceDirectories )
        {
            File dir = new File( sourceDirectory );

            builder.addSourceTree( dir );
        }

        JavaClass[] classes = builder.getClasses();

        // For each class we find, try to glean off a descriptor
        for ( int i = 0; i < classes.length; i++ )
        {
            ComponentDescriptor<?> descriptor = gleaner.glean( builder, classes[i] );

            if ( descriptor != null )
            {
                applyDefaults( descriptor, defaultsByRole );

                descriptors.add( descriptor );
            }
        }

        return descriptors;
    }
}
