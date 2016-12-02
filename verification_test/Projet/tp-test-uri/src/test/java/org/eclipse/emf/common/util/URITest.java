package org.eclipse.emf.common.util;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

/**
 * Created by sullivan on 02/12/16.
 */
public class URITest {

    @Before
    public void setUp(){

    }

    @After
    public void tearDown(){

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

    //test methode createHierarchicalURI
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

    //test methode createHierarchicalURI
    @Test(expected=IllegalArgumentException.class)
    public void createHierarchicalURI1isscheme() throws Exception {
        String scheme = "jar";
        String authority = null;
        String device = "test";
        String[] segments = null;
        String query = null;
        String fragment = null;
        URI.createHierarchicalURI(scheme, authority, device, segments, query, fragment);
    }

    @Test(expected=IllegalArgumentException.class)
    public void createHierarchicalURI1equals() throws Exception {
        String scheme = "platform";
        String authority = null;
        String device = "test";
        String[] segments = null;
        String query = null;
        String fragment = null;
        URI.createHierarchicalURI(scheme, authority, device, segments, query, fragment);
    }

    @Test
    public void createHierarchicalURI1devicenull() throws Exception {
        String scheme = null;
        String authority = null;
        String device = null;
        String[] segments = null;
        String query = null;
        String fragment = null;
        assertEquals(URI.createHierarchicalURI(scheme, authority, device, segments, query, fragment),URI.POOL.intern(false, URI.URIPool.URIComponentsAccessUnit.VALIDATE_ALL, true, scheme, authority, device, true, segments, query).appendFragment(fragment));
    }

    //test méthode createHierarchicalURI2
    @Test
    public void createHierarchicalURI2() throws Exception {
        String[] segments =null;
        String query=null;
        String fragment=null;
        assertEquals(URI.createHierarchicalURI(segments, query, fragment), URI.POOL.intern(false, URI.URIPool.URIComponentsAccessUnit.VALIDATE_ALL, true, null, null, null, false, segments, query).appendFragment(fragment));
    }

    //test méthode createURI
    @Test
    public void createURI() throws Exception {
        String uri ="test";
        assertEquals(URI.createURI(uri),URI.createURIWithCache(uri));
    }

    //test méthode createURI1
    @Test
    public void createURI1() throws Exception {
        String uri="test";
        boolean ignoreEscaped=true;
        assertEquals(URI.createURI(uri,ignoreEscaped),URI.createURIWithCache(URI.encodeURI(uri, ignoreEscaped, URI.FRAGMENT_LAST_SEPARATOR)));
    }

    //test méthode createURI2
    @Test
    public void createURI2() throws Exception {
        String uri="test";
        boolean ignoreEscaped=true;
        int fragmentLocationStyle=5;
        assertEquals(URI.createURI(uri,ignoreEscaped,fragmentLocationStyle),URI.createURIWithCache(URI.encodeURI(uri, ignoreEscaped, fragmentLocationStyle)));
    }

    //test méthode createDeviceURI
    @Test
    public void createDeviceURI() throws Exception {
        String uri ="test";
        assertEquals(URI.createDeviceURI(uri),URI.createURIWithCache(uri));
    }

    //test méthode createURIWithCache
    @Test
    public void createURIWithCachemoinsun() throws Exception {
        String uri="testdgxgfdfgdgfd";
        assertEquals(URI.POOL.intern(uri),URI.createURIWithCache(uri));
    }

    @Test(expected=IllegalArgumentException.class)
    public void createURIWithCacheplusun(){
        String uri="te#stdgxgfdfgdgfd";
        assertEquals(URI.POOL.intern(uri),URI.POOL.intern(uri.substring(0, 2)).appendFragment(uri.substring(2 + 1)));
    }

    //test méthode createFileURI
    @Test
    public void createFileURI() throws Exception {
        String pathname="test";
        assertEquals(URI.createFileURI(pathname),URI.POOL.internFile(pathname));
    }

    //test méthode createPlatformResourceURI
    @Test
    public void createPlatformResourceURI() throws Exception {
        String pathName = "test";
        assertEquals(URI.createPlatformResourceURI(pathName),URI.createPlatformResourceURI(pathName, URI.ENCODE_PLATFORM_RESOURCE_URIS));
    }

    //test méthode createPlatformResourceURI 2 parametre
    @Test
    public void createPlatformResourceURI1() throws Exception {
        String pathName="test";
        boolean encode=false;
        assertEquals(URI.createPlatformResourceURI(pathName,encode),URI.POOL.intern(URI.SEGMENT_RESOURCE, pathName, encode));
    }

    //test méthode createPlatformPluginURI
    @Test
    public void createPlatformPluginURI() throws Exception {
        String pathName="test";
        boolean encode = true;
        assertEquals(URI.createPlatformPluginURI(pathName,encode),URI.POOL.intern(URI.SEGMENT_PLUGIN, pathName, encode));
    }

    //test méthode validScheme
    @Test
    public void validSchemevaluenull() throws Exception {
        String value = null;
        assertEquals(true, URI.validScheme(null));
    }

    @Test
    public void validSchemevaluenotnull() throws Exception {
        String value = "test";
        assertEquals(true, URI.validScheme(null));
    }

    //test méthode validOpaquePart
    @Test
    public void validOpaquePart() throws Exception {
        assertEquals(false,URI.validOpaquePart(null));
    }

    @Test
    public void validOpaquePartindexdiese() throws Exception {
        assertEquals(false,URI.validOpaquePart("test#test"));
    }

    @Test
    public void validOpaquePartlengthzero() throws Exception {
        assertEquals(false,URI.validOpaquePart(""));
    }

    @Test
    public void validOpaquePartchatatzero() throws Exception {
        assertEquals(false,URI.validOpaquePart("#test"));
    }

    @Test
    public void validOpaquePartnotnull() throws Exception {
        assertEquals(true,URI.validOpaquePart("test"));
    }



/*



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