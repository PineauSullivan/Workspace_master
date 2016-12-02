package org.eclipse.emf.common.util;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by sullivan on 02/12/16.
 */
public class URITest {

    @before
    public void setUp(){
        super.setUp();

    }

    @after
    public void tearDown(){
        super.tearDown();
    }
    //test methode createGenericURI
    @Test(expected=IllegalArgumentException.class)
    public void testcreateGenericURISchemeNull(){
        String scheme = null;
        String opaquePart = null;
        String fragment = null;
        URI.createGenericURI(scheme, opaquePart,fragment);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testcreateGenericURIisArchiveScheme(){
        String scheme = "jar";
        String opaquePart = null;
        String fragment = null;
        URI.createGenericURI(scheme, opaquePart,fragment);
    }

    @Test
    public void testcreateGenericURI(){
        String scheme = "test";
        String opaquePart = "test";
        String fragment = "test";
        assertEquals(URI.createGenericURI(scheme, opaquePart,fragment),URI.POOL.intern(false, URI.URIPool.URIComponentsAccessUnit.VALIDATE_ALL, false, scheme, opaquePart, null, false, null, null).appendFragment(fragment));
    }

    @Test(expected=IllegalArgumentException.class)
    public void createHierarchicalURIdiffnull(){
        String scheme ="test";
        String authority = "test";
        String device = "test";
        String query="test";
        String fragment="test";
        URI.createHierarchicalURI(scheme, authority, device, query, fragment);
    }

    @Test(expected=IllegalArgumentException.class)
    public void createHierarchicalURIisarchive(){
        String scheme ="jar";
        String authority = null;
        String device = "test";
        String query="test";
        String fragment="test";
        URI.createHierarchicalURI(scheme, authority, device, query, fragment);
    }

    @Test
    public void createHierarchicalURI(){
        String scheme ="jarr";
        String authority = "test";
        String device = null;
        String query="test";
        String fragment="test";
        assertEquals(URI.createHierarchicalURI(scheme, authority, device, query, fragment),URI.POOL.intern(false, URI.URIPool.URIComponentsAccessUnit.VALIDATE_ALL, true, scheme, authority, device, false, URI.NO_SEGMENTS, query).appendFragment(fragment));
    }

    /*

    @Test
    public void createHierarchicalURI1() throws Exception {

    }

    @Test
    public void createHierarchicalURI2() throws Exception {

    }

    @Test
    public void createURI() throws Exception {

    }

    @Test
    public void createURI1() throws Exception {

    }

    @Test
    public void createURI2() throws Exception {

    }

    @Test
    public void createDeviceURI() throws Exception {

    }

    @Test
    public void createURIWithCache() throws Exception {

    }

    @Test
    public void createFileURI() throws Exception {

    }

    @Test
    public void createPlatformResourceURI() throws Exception {

    }

    @Test
    public void createPlatformResourceURI1() throws Exception {

    }

    @Test
    public void createPlatformPluginURI() throws Exception {

    }

    @Test
    public void validScheme() throws Exception {

    }

    @Test
    public void validOpaquePart() throws Exception {

    }

    @Test
    public void validAuthority() throws Exception {

    }

    @Test
    public void validArchiveAuthority() throws Exception {

    }

    @Test
    public void validJarAuthority() throws Exception {

    }

    @Test
    public void validDevice() throws Exception {

    }

    @Test
    public void validSegment() throws Exception {

    }

    @Test
    public void validSegments() throws Exception {

    }

    @Test
    public void validQuery() throws Exception {

    }

    @Test
    public void validFragment() throws Exception {

    }

    @Test
    public void isRelative() throws Exception {

    }

    @Test
    public void isHierarchical() throws Exception {

    }

    @Test
    public void hasAuthority() throws Exception {

    }

    @Test
    public void hasOpaquePart() throws Exception {

    }

    @Test
    public void hasDevice() throws Exception {

    }

    @Test
    public void hasPath() throws Exception {

    }

    @Test
    public void hasAbsolutePath() throws Exception {

    }

    @Test
    public void hasRelativePath() throws Exception {

    }

    @Test
    public void hasEmptyPath() throws Exception {

    }

    @Test
    public void hasQuery() throws Exception {

    }

    @Test
    public void hasFragment() throws Exception {

    }

    @Test
    public void isCurrentDocumentReference() throws Exception {

    }

    @Test
    public void isEmpty() throws Exception {

    }

    @Test
    public void isFile() throws Exception {

    }

    @Test
    public void isPlatform() throws Exception {

    }

    @Test
    public void isPlatformResource() throws Exception {

    }

    @Test
    public void isPlatformPlugin() throws Exception {

    }

    @Test
    public void isArchive() throws Exception {

    }

    @Test
    public void isArchiveScheme() throws Exception {

    }

    @Test
    public void hashCode() throws Exception {

    }

    @Test
    public void scheme() throws Exception {

    }

    @Test
    public void opaquePart() throws Exception {

    }

    @Test
    public void authority() throws Exception {

    }

    @Test
    public void userInfo() throws Exception {

    }

    @Test
    public void host() throws Exception {

    }

    @Test
    public void port() throws Exception {

    }

    @Test
    public void device() throws Exception {

    }

    @Test
    public void segments() throws Exception {

    }

    @Test
    public void segmentsList() throws Exception {

    }

    @Test
    public void segmentCount() throws Exception {

    }

    @Test
    public void segment() throws Exception {

    }

    @Test
    public void lastSegment() throws Exception {

    }

    @Test
    public void path() throws Exception {

    }

    @Test
    public void devicePath() throws Exception {

    }

    @Test
    public void query() throws Exception {

    }

    @Test
    public void appendQuery() throws Exception {

    }

    @Test
    public void trimQuery() throws Exception {

    }

    @Test
    public void fragment() throws Exception {

    }

    @Test
    public void appendFragment() throws Exception {

    }

    @Test
    public void trimFragment() throws Exception {

    }

    @Test
    public void resolve() throws Exception {

    }

    @Test
    public void resolve1() throws Exception {

    }

    @Test
    public void deresolve() throws Exception {

    }

    @Test
    public void deresolve1() throws Exception {

    }

    @Test
    public void toFileString() throws Exception {

    }

    @Test
    public void toPlatformString() throws Exception {

    }

    @Test
    public void appendSegment() throws Exception {

    }

    @Test
    public void appendSegments() throws Exception {

    }

    @Test
    public void trimSegments() throws Exception {

    }

    @Test
    public void hasTrailingPathSeparator() throws Exception {

    }

    @Test
    public void fileExtension() throws Exception {

    }

    @Test
    public void appendFileExtension() throws Exception {

    }

    @Test
    public void trimFileExtension() throws Exception {

    }

    @Test
    public void isPrefix() throws Exception {

    }

    @Test
    public void replacePrefix() throws Exception {

    }

    @Test
    public void encodeOpaquePart() throws Exception {

    }

    @Test
    public void encodeAuthority() throws Exception {

    }

    @Test
    public void encodeSegment() throws Exception {

    }

    @Test
    public void encodeQuery() throws Exception {

    }

    @Test
    public void encodeFragment() throws Exception {

    }

    @Test
    public void decode() throws Exception {

    }
    */

}