package org.codehaus.plexus.component.manager;

import org.codehaus.plexus.PlexusTestCase;

/**
 * @author Ben Walding
 * @version $Id: ClassicSingletonComponentManagerTest.java 7828 2008-11-14 22:07:56Z dain $
 */
public class ClassicSingletonComponentManagerTest
    extends PlexusTestCase
{
    public void testSequentialLookupsReturnTheSameInstance()
        throws Exception
    {
        Component a = lookup( Component.class );

        Component b = lookup( Component.class );

        Component c = lookup( Component.class );

        Component d = lookup( Component.class );

        assertTrue( a == b );

        assertTrue( a == c );

        assertTrue( a == d );
    }
}
