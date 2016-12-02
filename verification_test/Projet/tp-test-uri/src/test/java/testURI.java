package org.eclipse.emf.common.util;


import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Created by sullivan on 02/12/16.
 */
public class testURI {

    URI monUri;
    //test methode createGenericURI
    @Test
    public void testcreateGenericURISchemeNull()
            throws IllegalArgumentException{
        String scheme = null;
        String opaquePart = null;
        String fragment = null;
        URI.createGenericURI(scheme, opaquePart,fragment);
    }

    @Test
    public void testcreateGenericURIisArchiveScheme()
            throws IllegalArgumentException{
        String scheme = "file";
        String opaquePart = null;
        String fragment = null;
        URI.createGenericURI(scheme, opaquePart,fragment);
    }



}
