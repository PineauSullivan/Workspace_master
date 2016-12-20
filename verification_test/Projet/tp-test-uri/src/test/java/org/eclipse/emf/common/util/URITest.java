package org.eclipse.emf.common.util;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.util.Collections;

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
    public void testcreateGenericURIIsArchiveScheme(){
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
    public void createHierarchicalURIDiffNull(){
        String scheme ="test";
        String authority = "test";
        String device = "test";
        String query="test";
        String fragment="test";
        URI.createHierarchicalURI(scheme, authority, device, query, fragment);
    }

    @Test(expected=IllegalArgumentException.class)
    public void createHierarchicalURIFirstIf(){
        String scheme ="test";
        String authority = null;
        String device = null;
        String query="test";
        String fragment="test";
        URI.createHierarchicalURI(scheme, authority, device, query, fragment);
    }


    @Test(expected=IllegalArgumentException.class)
    public void createHierarchicalURIIsArchive(){
        String scheme ="jar";
        String authority = null;
        String device = "test";
        String query="test";
        String fragment="test";
        URI.createHierarchicalURI(scheme, authority, device, query, fragment);
    }

    @Test
    public void createHierarchicalURISchemeNull(){
        String scheme =null;
        String authority = "test";
        String device = ":";
        String query="test";
        String fragment="test";
        assertEquals(URI.createHierarchicalURI(scheme, authority, device, query, fragment),URI.POOL.intern(false, URI.URIPool.URIComponentsAccessUnit.VALIDATE_ALL, true, scheme, authority, device, false, URI.NO_SEGMENTS, query).appendFragment(fragment));
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
    public void createHierarchicalURI1IsScheme() throws Exception {
        String scheme = "jar";
        String authority = null;
        String device = "test";
        String[] segments = null;
        String query = null;
        String fragment = null;
        URI.createHierarchicalURI(scheme, authority, device, segments, query, fragment);
    }

    @Test(expected=IllegalArgumentException.class)
    public void createHierarchicalURI1Equals() throws Exception {
        String scheme = "platform";
        String authority = null;
        String device = "test";
        String[] segments = null;
        String query = null;
        String fragment = null;
        URI.createHierarchicalURI(scheme, authority, device, segments, query, fragment);
    }


    @Test
    public void createHierarchicalURI1DeviceNull() throws Exception {
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

    @Test
    public void createURIdiese() throws Exception {
        String uri ="te#st";
        assertEquals(URI.createURI(uri),URI.POOL.intern(uri.substring(0, 2)).appendFragment(uri.substring(2 + 1)));
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
    public void createURIWithCacheMoinsUn() throws Exception {
        String uri="testdgxgfdfgdgfd";
        assertEquals(URI.POOL.intern(uri),URI.createURIWithCache(uri));
    }

    @Test(expected=IllegalArgumentException.class)
    public void createURIWithCachePlusUn(){
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
    public void validSchemeValueNull() throws Exception {
        assertEquals(true, URI.validScheme(null));
    }

    @Test
    public void validSchemeValueNotNull() throws Exception {
        String value = "test";
        assertEquals(true, URI.validScheme(value));
    }

    @Test
    public void validSchemeValueNotNullFalse() throws Exception {
        String value = ":/?#";
        assertEquals(false, URI.validScheme(value));
    }

    //test méthode validOpaquePart
    @Test
    public void validOpaquePart() throws Exception {
        assertEquals(false,URI.validOpaquePart(null));
    }

    @Test
    public void validOpaquePartIndexDiese() throws Exception {
        assertEquals(false,URI.validOpaquePart("test#test"));
    }

    @Test
    public void validOpaquePartLengthZero() throws Exception {
        assertEquals(false,URI.validOpaquePart(""));
    }

    @Test
    public void validOpaquePartChatAtZero() throws Exception {
        assertEquals(false,URI.validOpaquePart("#test"));
    }

    @Test
    public void validOpaquePartNotNull() throws Exception {
        assertEquals(true,URI.validOpaquePart("test"));
    }

    @Test
    public void validOpaquePartContain() throws Exception {
        assertEquals(false,URI.validOpaquePart("/?#"));
    }

    @Test
    public void validOpaquePartContainFalse() throws Exception {
        assertEquals(false,URI.validOpaquePart("/"));
    }

    //test méthode validAuthority
    @Test
    public void validAuthorityNull() throws Exception {
        assertEquals(true,URI.validAuthority(null));
    }

    @Test
    public void validAuthoritynotnullAndNotCointain() throws Exception {
        assertEquals(true,URI.validAuthority("test"));
    }

    @Test
    public void validAuthorityNotNullAndContain() throws Exception {
        assertEquals(false,URI.validAuthority("/?#"));
    }

    //test méthode validArchive
    @Test
    public void validArchiveAuthorityNull() throws Exception {
        assertEquals(false, URI.validArchiveAuthority(null));
    }

    @Test
    public void validArchiveAuthorityLenghtNull() throws Exception {
        assertEquals(false, URI.validArchiveAuthority(""));
    }

    @Test
    public void validArchiveAuthorityCharAtFalse() throws Exception {
        assertEquals(false, URI.validArchiveAuthority("!+"));
    }

    @Test
    public void validArchiveAuthorityCharAtTrue() throws Exception {
        String value ="+!";
        URI archiveURI = URI.createURI(value.substring(0, value.length() - 1));
        assertEquals(!archiveURI.hasFragment(), URI.validArchiveAuthority("+!"));
    }

    @Test
    public void validArchiveAuthorityCharAtFalse2() throws Exception {
        String value ="/?#!";
        URI archiveURI = URI.createURI(value.substring(0, value.length() - 1));
        assertEquals(!archiveURI.hasFragment(), URI.validArchiveAuthority("/?#!"));
    }

    //tests méthode validJarAuthority
    @Test
    public void validJarAuthority() throws Exception {
        assertEquals(URI.validJarAuthority("test"),URI.validArchiveAuthority("test"));
    }

    //tests méthode validDevice
    @Test
    public void validDeviceNull() throws Exception {
        assertEquals(true, URI.validDevice(null));
    }

    @Test
    public void validDeviceLength0() throws Exception {
        assertEquals(false, URI.validDevice(""));
    }

    @Test
    public void validDeviceDeviceidenFalse() throws Exception {
        assertEquals(false, URI.validDevice(":o"));
    }

    @Test
    public void validDeviceDeviceContainTrue() throws Exception {
        assertEquals(false, URI.validDevice("/?#"));
    }

    @Test
    public void validDevice() throws Exception {
        assertEquals(true, URI.validDevice(":"));
    }

    @Test
    public void validDevicefalse() throws Exception {
        assertEquals(false, URI.validDevice("/?#:::::::::::::"));
    }

    //tests méthode validSegment
    @Test
    public void validSegmentNull() throws Exception {
        assertEquals(false,URI.validSegment(null));
    }

    @Test
    public void validSegmentValidateFalse() throws Exception {
        assertEquals(false,URI.validSegment("/?#"));
    }

    @Test
    public void validSegment() throws Exception {
        assertEquals(true,URI.validSegment("test"));
    }

    //tests méthode validSegments
    @Test
    public void validSegmentsNull() throws Exception {
        assertEquals(false, URI.validSegments(null) );
    }

    @Test
    public void validSegmentsLengthZero() throws Exception {
        assertEquals(true, URI.validSegments(new String[0]));
    }

    @Test
    public void validSegmentsForFalse() throws Exception {
        String[] values = new String[1];
        values[0] = "/?#";
        assertEquals(false, URI.validSegments(values));
    }

    //tests méthode validQuery
    @Test
    public void validQueryNull() throws Exception {
        assertEquals(true,URI.validQuery(null));
    }

    @Test
    public void validQueryOIndexofmoins1() throws Exception {
        assertEquals(true,URI.validQuery("test"));
    }

    @Test
    public void validQueryOIndexofplus1() throws Exception {
        assertEquals(false,URI.validQuery("test#test"));
    }

    //tests méthode validFragment
    @Test
    public void validFragment() throws Exception {
        assertEquals(true,URI.validFragment(""));
    }


    //tests méthode isHierarchical
    @Test
    public void isHierarchicalTestHierarchicalURI() throws Exception {
        URI monUriTest = URI.createURI("test");
        assertEquals(true,monUriTest.isHierarchical());
    }


    @Test
    public void isHierarchicalTestGenericForm() throws Exception {
        URI monUriTest = URI.createGenericURI("test", "test","test");
        assertEquals(false,monUriTest.isHierarchical());
    }

    //tests méthode hasAuthority
    @Test
    public void hasAuthorityOtherwise() throws Exception {
        URI monUriTest = URI.createURI("test");
        assertEquals(false,monUriTest.hasAuthority());
    }

    @Test
    public void hasAuthorityHierarchical() throws Exception {
        URI monUriTest;
        String scheme = "jarr";
        String authority = "test";
        String device = null;
        String query = "test";
        String fragment = "test";
        monUriTest = URI.createHierarchicalURI(scheme, authority, device, query, fragment);
        assertEquals(true,monUriTest.hasAuthority());
    }

    //tests méthode hasOpaquePart
    @Test
    public void hasOpaquePartOtherwise() throws Exception {
        URI monUriTest = URI.createURI("test");
        assertEquals(false,monUriTest.hasOpaquePart());
    }

    @Test
    public void hasOpaquePartNonHierarchical() throws Exception {
        URI monUriTest = URI.createGenericURI("test", "test","test");
        assertEquals(true,monUriTest.hasOpaquePart());
    }

    //tests méthode hasDevice
    @Test
    public void hasDeviceTrue() throws Exception {
        URI monUriTest;
        String scheme = "jarr";
        String authority = "test";
        String device = ":";
        String query = "test";
        String fragment = "test";
        monUriTest = URI.createHierarchicalURI(scheme, authority, device, query, fragment);
        assertEquals(true,monUriTest.hasDevice());
    }


    @Test
    public void hasDeviceFalse() throws Exception {
        URI monUriTest = URI.createURI("test");

        assertEquals(false,monUriTest.hasDevice());
    }

    //tests méthode hasPath
    @Test
    public void hasPathTrue() throws Exception {
        URI monUriTest = URI.createPlatformPluginURI("pathName",true);
        assertEquals(true,monUriTest.hasPath());
    }


    @Test
    public void hasPathFalse() throws Exception {
        URI monUriTest;
        String scheme = "jarr";
        String authority = "test";
        String device = ":";
        String query = "test";
        String fragment = "test";
        monUriTest = URI.createHierarchicalURI(scheme, authority, device, query, fragment);
        assertEquals(false,monUriTest.hasPath());
    }

    //tests méthode hasAbsolutePath
    @Test
    public void hasAbsolutePathHierarchicalWithAbsolutePath() throws Exception {
        URI monUriTest = URI.createHierarchicalURI("test", "test", ":", null, "test", "test");
        assertEquals(true,monUriTest.hasAbsolutePath());
    }


    @Test
    public void hasAbsolutePathNoHierarchical() throws Exception {
        URI monUriTest;
        String scheme = "jarr";
        String authority = "test";
        String device = ":";
        String query = "test";
        String fragment = "test";
        monUriTest = URI.createHierarchicalURI(scheme, authority, device, query, fragment);
        assertEquals(false,monUriTest.hasAbsolutePath());
    }


    //tests méthode hasRelativePath
    @Test
    public void hasRelativePathHierarchicalWithRelativePath() throws Exception {
        URI monUriTest = URI.createFileURI("pathName");
        assertEquals(true,monUriTest.hasRelativePath());
    }

    @Test
    public void hasRelativePathNoHierarchical() throws Exception {
        URI monUriTest;
        String scheme = "jarr";
        String authority = "test";
        String device = ":";
        String query = "test";
        String fragment = "test";
        monUriTest = URI.createHierarchicalURI(scheme, authority, device, query, fragment);
        assertEquals(false,monUriTest.hasRelativePath());
    }


    //tests méthode hasEmptyPath
    @Test
    public void hasEmptyPathFalse() throws Exception {
        URI monUriTest = URI.createPlatformPluginURI("pathName",true);
        assertEquals(false,monUriTest.hasEmptyPath());
    }

    @Test
    public void hasEmptyPathTrue() throws Exception {
        URI monUriTest;
        String scheme = "jarr";
        String authority = "test";
        String device = ":";
        String query = "test";
        String fragment = "test";
        monUriTest = URI.createHierarchicalURI(scheme, authority, device, query, fragment);
    }

    @Test
    public void hasEmptyPathAuthorityNull() throws Exception {
        URI monUriTest = URI.createHierarchicalURI(null, "test","test");
        assertEquals(true,monUriTest.hasEmptyPath());
    }


    //tests méthode hasQuery
    @Test
    public void hasQueryTrue() throws Exception {
        URI monUriTest;
        String scheme = "jarr";
        String authority = "test";
        String device = ":";
        String query = "test";
        String fragment = "test";
        monUriTest = URI.createHierarchicalURI(scheme, authority, device, query, fragment);
        assertEquals(true,monUriTest.hasQuery());
    }


    @Test
    public void hasQueryFalse() throws Exception {
        URI monUriTest;
        String scheme = "jarr";
        String authority = "test";
        String device = ":";
        String query = null;
        String fragment = "test";
        monUriTest = URI.createHierarchicalURI(scheme, authority, device, query, fragment);
        assertEquals(false,monUriTest.hasQuery());
    }

    //tests méthode hasFragment
    @Test
    public void hasFragmentTrue() throws Exception {
        URI monUriTest;
        String scheme = "jarr";
        String authority = "test";
        String device = ":";
        String query = "test";
        String fragment = "test";
        monUriTest = URI.createHierarchicalURI(scheme, authority, device, query, fragment);
        assertEquals(true,monUriTest.hasFragment());
    }

    @Test
    public void hasFragmentFalse() throws Exception {
        URI monUriTest;
        String scheme = "jarr";
        String authority = "test";
        String device = ":";
        String query = "test";
        String fragment = null;
        monUriTest = URI.createHierarchicalURI(scheme, authority, device, query, fragment);
        assertEquals(false,monUriTest.hasFragment());
    }

    //tests méthode isCurrentDocumentReference
    @Test
    public void isCurrentDocumentReferenceTrue() throws Exception {
        URI monUriTest;
        monUriTest = URI.createHierarchicalURI(null, null, null);
        assertEquals(true, monUriTest.isCurrentDocumentReference());
    }

    @Test
    public void isCurrentDocumentReferenceFalse() throws Exception {
        URI monUriTest = URI.createURI("test");
        assertEquals(false, monUriTest.isCurrentDocumentReference());
    }


    //tests méthode isEmpty
    @Test
    public void isEmptyTrue() throws Exception {
        URI monUriTest;
        monUriTest = URI.createHierarchicalURI(null, null, null);
        assertEquals(true, monUriTest.isEmpty());
    }

    @Test
    public void isEmptyFalse() throws Exception {
        URI monUriTest = URI.createURI("test");
        assertEquals(false, monUriTest.isEmpty());
    }


    //tests méthode isFile
    @Test
    public void isFileFalse() throws Exception {
        URI monUriTest;
        String scheme = "jarr";
        String authority = "test";
        String device = null;
        String query = "test";
        String fragment = "test";
        monUriTest = URI.createHierarchicalURI(scheme, authority, device, query, fragment);
        assertEquals(false,monUriTest.isFile());
    }

    @Test
    public void isFileTrue() throws Exception {
        URI monUriTest = URI.createFileURI("test");
        assertEquals(true,monUriTest.isFile());
    }

    //tests méthode isPlatform
    @Test
    public void isPlatformTrue() throws Exception {
        URI monUriTest = URI.createPlatformResourceURI("test",true);
        assertEquals(true,monUriTest.isPlatform());
    }


    @Test
    public void isPlatformFalse() throws Exception {
        URI monUriTest = URI.createURI("test");
        assertEquals(false,monUriTest.isPlatform());
    }

    //tests méthode isPlatformResource
    @Test
    public void isPlatformResourceTrue() throws Exception {
        URI monUriTest = URI.createPlatformResourceURI("test",true);
        assertEquals(true,monUriTest.isPlatformResource());
    }

    @Test
    public void isPlatformResourceFalse() throws Exception {
        URI monUriTest = URI.createURI("test");
        assertEquals(false,monUriTest.isPlatformResource());
    }

    @Test
    public void isPlatformResourceFalse2() throws Exception {
        URI monUriTest = URI.createPlatformPluginURI("test",false);
        assertEquals(false,monUriTest.isPlatformResource());
    }

    //tests méthode isPlatformPlugin
    @Test
    public void isPlatformPluginTrue() throws Exception {
        URI monUriTest = URI.createPlatformPluginURI("test",true);
        assertEquals(true,monUriTest.isPlatformPlugin());
    }

    @Test
    public void isPlatformPluginFalse() throws Exception {
        URI monUriTest = URI.createURI("test");
        assertEquals(false,monUriTest.isPlatformPlugin());
    }

    @Test
    public void isPlatformPluginFalse2() throws Exception {
        URI monUriTest = URI.createPlatformResourceURI("test");
        assertEquals(false,monUriTest.isPlatformPlugin());
    }

    //tests méthode isArchive
    //Réchercher test en dessous ... ...
//    @Test
//    public void isArchiveTrue() throws Exception {
//        URI monUriTest;
//
//        System.out.println( monUriTest.isArchive());
//        //assertEquals(true, monUriTest.isArchive());
//    }


    @Test
    public void isArchiveFalse() throws Exception {
        URI monUriTest = URI.createURI("test");
        assertEquals(false, monUriTest.isArchive());
    }


    //tests méthode isArchiveScheme
    @Test
    public void isArchiveSchemeTrue() throws Exception {
        assertEquals(true,URI.isArchiveScheme("archive"));
    }

    @Test
    public void isArchiveSchemeFalse() throws Exception {
        assertEquals(false,URI.isArchiveScheme("test"));
    }


    //tests méthode hashCode
    @Test
    public void hashCodeTest() throws Exception {
        URI monUriTest = URI.createURI("test");
        assertEquals(3556498,monUriTest.hashCode());
    }

    //tests méthode scheme
    @Test
    public void schemeTest() throws Exception {
        URI monUriTest;
        String scheme = "jarr";
        String authority = "test";
        String device = null;
        String query = "test";
        String fragment = "test";
        monUriTest = URI.createHierarchicalURI(scheme, authority, device, query, fragment);
        assertEquals("jarr",monUriTest.scheme());
    }


    @Test
    public void schemeNull() throws Exception {
        URI monUriTest = URI.createURI("test");
        assertEquals(null,monUriTest.scheme());
    }

    //tests méthode opaquePart
    @Test
    public void opaquePartNull() throws Exception {
        URI monUriTest = URI.createURI("test");
        assertEquals(null, monUriTest.opaquePart());
    }

    @Test
    public void opaquePartNotNull() throws Exception {
        URI monUriTest;
        monUriTest = URI.createGenericURI("test","test","test");
        assertNotEquals(null, monUriTest.opaquePart());
    }

    //test méthode authority
    @Test
    public void authorityNULL() throws Exception {
        URI monUriTest = URI.createURI("test");
        assertEquals(null, monUriTest.authority());
    }

    @Test
    public void authorityNotNull() throws Exception {
        URI monUriTest;
        String scheme = "jarr";
        String authority = "test";
        String device = null;
        String query = "test";
        String fragment = "test";
        monUriTest = URI.createHierarchicalURI(scheme, authority, device, query, fragment);
        assertEquals("test",monUriTest.authority());
    }

    //test méthode userInfo
    @Test
    public void userInfoNull() throws Exception {
        URI monUriTest = URI.createURI("test");
        assertEquals(null, monUriTest.userInfo());
    }

    @Test
    public void userInfoNotNull() throws Exception {
        URI monUriTest;
        String scheme = "jarr";
        String authority = "te@st";
        String device = null;
        String query = "test";
        String fragment = "test";
        monUriTest = URI.createHierarchicalURI(scheme, authority, device, query, fragment);
        assertNotEquals(null, monUriTest.userInfo());
    }


    @Test
    public void userInfoNotNull2() throws Exception {
        URI monUriTest;
        String scheme = "jarr";
        String authority = "test";
        String device = null;
        String query = "test";
        String fragment = "test";
        monUriTest = URI.createHierarchicalURI(scheme, authority, device, query, fragment);
        assertEquals(null, monUriTest.userInfo());
    }

    //tests méthode host
    @Test
    public void hostNull() throws Exception {
        URI monUriTest = URI.createURI("test");
        assertEquals(null, monUriTest.host());
    }

    @Test
    public void hostNotNull() throws Exception {
        URI monUriTest;
        String scheme = "jarr";
        String authority = "te@s:t";
        String device = null;
        String query = "test";
        String fragment = "test";
        monUriTest = URI.createHierarchicalURI(scheme, authority, device, query, fragment);
        assertNotEquals(null, monUriTest.host());
    }

    @Test
    public void hostNotNull2() throws Exception {
        URI monUriTest;
        String scheme = "jarr";
        String authority = "test";
        String device = null;
        String query = "test";
        String fragment = "test";
        monUriTest = URI.createHierarchicalURI(scheme, authority, device, query, fragment);
        assertNotEquals(null, monUriTest.host());
    }

    //tests méthode port
    @Test
    public void portNull() throws Exception {
        URI monUriTest = URI.createURI("test");
        assertEquals(null, monUriTest.port());
    }

    @Test
    public void portNotNull() throws Exception {
        URI monUriTest;
        String scheme = "jarr";
        String authority = "te@s:t";
        String device = null;
        String query = "test";
        String fragment = "test";
        monUriTest = URI.createHierarchicalURI(scheme, authority, device, query, fragment);
        assertNotEquals(null, monUriTest.port());
    }



    @Test
    public void portNull2() throws Exception {
        URI monUriTest;
        String scheme = "jarr";
        String authority = "test";
        String device = null;
        String query = "test";
        String fragment = "test";
        monUriTest = URI.createHierarchicalURI(scheme, authority, device, query, fragment);
        assertEquals(null, monUriTest.port());
    }


    //tests méthode device
    @Test
    public void deviceNull() throws Exception {
        URI monUriTest = URI.createURI("test");
        assertEquals(null, monUriTest.device());
    }

    @Test
    public void deviceNotNull() throws Exception {
        URI monUriTest;
        String scheme = "jarr";
        String authority = "te@s:t";
        String device = ":";
        String query = "test";
        String fragment = "test";
        monUriTest = URI.createHierarchicalURI(scheme, authority, device, query, fragment);
        assertNotEquals(null, monUriTest.device());
    }

    //tests méthode segments
    @Test
    public void segmentsNull() throws Exception {
        URI monUriTest = URI.createURI("test");
        assertEquals(monUriTest.segments(),monUriTest.segments());
    }

    @Test
    public void segmentsNotNull() throws Exception {
        String[] segments = new String[1];
        segments[0] = "test";
        URI monUriTest = URI.createHierarchicalURI(segments,"test", "test");
        assertNotEquals(null, monUriTest.segments());
    }

    //tests méthode segmentsList
    @Test
    public void segmentsListNull() throws Exception {
        URI monUriTest;
        String scheme = "jarr";
        String authority = "te@s:t";
        String device = ":";
        String query = "test";
        String fragment = "test";
        monUriTest = URI.createHierarchicalURI(scheme, authority, device, query, fragment);
        assertEquals(Collections.emptyList(),monUriTest.segmentsList());
    }

    @Test
    public void segmentsListNotNull() throws Exception {
        String[] segments = new String[1];
        segments[0] = "test";
        URI monUriTest = URI.createHierarchicalURI(segments,"test", "test");
        assertNotEquals(null, monUriTest.segmentsList());
    }


    //tests méthode segmentCount
    @Test
    public void segmentCountZero() throws Exception {
        URI monUriTest;
        String scheme = "jarr";
        String authority = "te@s:t";
        String device = ":";
        String query = "test";
        String fragment = "test";
        monUriTest = URI.createHierarchicalURI(scheme, authority, device, query, fragment);
        assertEquals(0,monUriTest.segmentCount());
    }

    @Test
    public void segmentCountUn() throws Exception {
        String[] segments = new String[1];
        segments[0] = "test";
        URI monUriTest = URI.createHierarchicalURI(segments,"test", "test");
        assertEquals(1,monUriTest.segmentCount());
    }


    //test méthode segment
    public final ExpectedException exception = ExpectedException.none();
    @Test
    public void segmentTest(){
        exception.expect(IndexOutOfBoundsException.class);
        URI monUriTest = URI.createURI("test");
        monUriTest.segment(0);
    }

    //tests méthode lastSegment
    @Test
    public void lastSegmentTest() throws Exception {
        URI monUriTest = URI.createURI("test");
        assertEquals("test",monUriTest.lastSegment());
    }

    @Test
    public void lastSegmentNull() throws Exception {
        URI monUriTest;
        String scheme = "jarr";
        String authority = "te@s:t";
        String device = ":";
        String query = "test";
        String fragment = "test";
        monUriTest = URI.createHierarchicalURI(scheme, authority, device, query, fragment);
        assertEquals(null,monUriTest.lastSegment());
    }

    //tests méthode path
    @Test
    public void pathNotNull() throws Exception {
        URI monUriTest = URI.createPlatformResourceURI("test",false);
        assertEquals("/resource/test",monUriTest.path());
    }

    @Test
    public void path() throws Exception {
        String scheme = "jarr";
        String authority = "te@s:t";
        String device = ":";
        String query = "test";
        String fragment = "test";
        URI monUriTest = URI.createHierarchicalURI(scheme, authority, device, query, fragment);
        assertEquals(null,monUriTest.path());
    }



    //tests méthode devicePath
    @Test
    public void devicePathNull() throws Exception {
        URI monUriTest;
        String scheme = "jarr";
        String authority = "te@s:t";
        String device = ":";
        String query = "test";
        String fragment = "test";
        monUriTest = URI.createHierarchicalURI(scheme, authority, device, query, fragment);
        assertEquals(null,monUriTest.devicePath());
    }

    @Test
    public void devicePathNotNull() throws Exception {
        URI monUriTest = URI.createPlatformResourceURI("test",false);
        assertEquals("/resource/test",monUriTest.devicePath());
    }

    //tests méthode query
    @Test
    public void queryNotNull() throws Exception {
        URI monUriTest = URI.createURI("test");
        assertNotEquals(null,monUriTest.devicePath());
    }

    @Test
    public void queryNull() throws Exception {
        URI monUriTest;
        String scheme = "jarr";
        String authority = "te@s:t";
        String device = ":";
        String query = null;
        String fragment = "test";
        monUriTest = URI.createHierarchicalURI(scheme, authority, device, query, fragment);
        assertEquals(null,monUriTest.devicePath());
    }

    //test appendQuery
    @Test(expected=IllegalArgumentException.class)
    public void appendQueryNotValidQuery() throws Exception {
        URI monUriTest = URI.createURI("test");
        monUriTest.appendQuery("#");
    }


    @Test
    public void appendQueryValidQuery() throws Exception {
        URI monUriTest = URI.createURI("test");
        assertEquals(monUriTest,monUriTest.appendQuery(null));
    }


    //test méthode trimQuery
    @Test
    public void trimQueryTest() throws Exception {
        URI monUriTest = URI.createURI("test");
        assertEquals(monUriTest,monUriTest.trimQuery());
    }



    //test méthode fragment
    @Test
    public void fragmentNotNull() throws Exception {
        URI monUriTest;
        String scheme = "jarr";
        String authority = "te@s:t";
        String device = ":";
        String query = null;
        String fragment = "test";
        monUriTest = URI.createHierarchicalURI(scheme, authority, device, query, fragment);
        assertEquals("test",monUriTest.fragment());
    }

    @Test
    public void fragmentNull() throws Exception {
        URI monUriTest = URI.createURI("test");
        assertEquals(null,monUriTest.fragment());
    }


    //tests méthode appendFragment
    @Test
    public void appendFragmentNull() throws Exception {
        URI monUriTest;
        String scheme = "jarr";
        String authority = "te@s:t";
        String device = ":";
        String query = null;
        String fragment = null;
        monUriTest = URI.createHierarchicalURI(scheme, authority, device, query, fragment);
        assertEquals(monUriTest,monUriTest.appendFragment(null));
    }

    @Test
    public void appendFragmentNotNull() throws Exception {


    }



    /*








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

    }*/
    /////////////////////////////////////////////////////////////////
    ////////////TEST encodeOpaquePart(string,bool)////////////////////////
    /////////////////////////////////////////////////////////////////

    @Test
    public void replacePrefix() throws Exception {

    }
    /////////////////////////////////////////////////////////////////
    ////////////TEST encodeOpaquePart(string,bool)////////////////////////
    /////////////////////////////////////////////////////////////////
    @Test
    public void encodeOpaquePartIgnoreTrueAndNotEscapedHex() {
        assertEquals("abc%7056;",URI.encodeFragment("abc%7056;",true));
    }
    @Test
    public void encodeOpaquePartIgnoreTrueAndEscapedHex() {
        assertEquals("abc%25gh56",URI.encodeFragment("abc%gh56",true));
    }
    @Test
    public void encodeOpaquePartIgnoreTrueAndEscapedChar() {
        assertEquals("abc%2056%2388",URI.encodeFragment("abc 56#88?ty-",true));
    }
    @Test
    public void encodeOpaquePartIgnoreTrueAndNotEscapedChar() {
        assertEquals("abc?56-",URI.encodeFragment("abc?56-",true));
    }
    @Test
    public void encodeOpaquePartIgnoreFalseAndNotEscapedHex() {
        assertEquals("abc%257056",URI.encodeFragment("abc%7056",false));
    }
    @Test
    public void encodeOpaquePartIgnoreFalseAndEscapedHex() {
        assertEquals("abc%25gh56",URI.encodeFragment("abc%gh56",false));
    }
    @Test
    public void encodeOpaquePartIgnoreFalseAndEscapedChar() {
        assertEquals("abc?56-",URI.encodeFragment("abc?56-",false));
    }
    @Test
    public void encodeOpaquePartIgnoreFalseAndNotEscapedChar() {
        assertEquals("abc%2056%23",URI.encodeFragment("abc 56#",false));
    }
    /////////////////////////////////////////////////////////////////
    ////////////TEST encodeAuthority(string,bool)////////////////////////
    /////////////////////////////////////////////////////////////////
    @Test
    public void encodeAuthorityIgnoreTrueAndNotEscapedHex() {
        assertEquals("abc%7056;",URI.encodeFragment("abc%7056;",true));
    }
    @Test
    public void encodeAuthorityIgnoreTrueAndEscapedHex() {
        assertEquals("abc%25gh56",URI.encodeFragment("abc%gh56",true));
    }
    @Test
    public void encodeAuthorityIgnoreTrueAndEscapedChar() {
        assertEquals("abc%2056%2388",URI.encodeFragment("abc 56#88?ty-",true));
    }
    @Test
    public void encodeAuthorityIgnoreTrueAndNotEscapedChar() {
        assertEquals("abc?56-",URI.encodeFragment("abc?56-",true));
    }
    @Test
    public void encodeAuthorityIgnoreFalseAndNotEscapedHex() {
        assertEquals("abc%257056",URI.encodeFragment("abc%7056",false));
    }
    @Test
    public void encodeAuthorityIgnoreFalseAndEscapedHex() {
        assertEquals("abc%25gh56",URI.encodeFragment("abc%gh56",false));
    }
    @Test
    public void encodeAuthorityIgnoreFalseAndEscapedChar() {
        assertEquals("abc?56-",URI.encodeFragment("abc?56-",false));
    }
    @Test
    public void encodeAuthorityIgnoreFalseAndNotEscapedChar() {
        assertEquals("abc%2056%23",URI.encodeFragment("abc 56#",false));
    }
    /////////////////////////////////////////////////////////////////
    ////////////TEST encodeSegment(string,bool)////////////////////////
    /////////////////////////////////////////////////////////////////

    @Test
    public void encodeSegmentIgnoreTrueAndNotEscapedHex() {
        assertEquals("abc%7056;",URI.encodeFragment("abc%7056;",true));
    }
    @Test
    public void encodeSegmentIgnoreTrueAndEscapedHex() {
        assertEquals("abc%25gh56",URI.encodeFragment("abc%gh56",true));
    }
    @Test
    public void encodeSegmentIgnoreTrueAndEscapedChar() {
        assertEquals("abc%2056%2388",URI.encodeFragment("abc 56#88?ty-",true));
    }
    @Test
    public void encodeSegmentIgnoreTrueAndNotEscapedChar() {
        assertEquals("abc?56-",URI.encodeFragment("abc?56-",true));
    }
    @Test
    public void encodeSegmentIgnoreFalseAndNotEscapedHex() {
        assertEquals("abc%257056",URI.encodeFragment("abc%7056",false));
    }
    @Test
    public void encodeSegmentIgnoreFalseAndEscapedHex() {
        assertEquals("abc%25gh56",URI.encodeFragment("abc%gh56",false));
    }
    @Test
    public void encodeSegmentIgnoreFalseAndEscapedChar() {
        assertEquals("abc?56-",URI.encodeFragment("abc?56-",false));
    }
    @Test
    public void encodeSegmentIgnoreFalseAndNotEscapedChar() {
        assertEquals("abc%2056%23",URI.encodeFragment("abc 56#",false));
    }

    /////////////////////////////////////////////////////////////////
    ////////////TEST encodeQuery(string,bool)////////////////////////
    /////////////////////////////////////////////////////////////////
    @Test
    public void encodeQueryIgnoreTrueAndNotEscapedHex() {
        assertEquals("abc%7056;",URI.encodeFragment("abc%7056;",true));
    }
    @Test
    public void encodeQueryIgnoreTrueAndEscapedHex() {
        assertEquals("abc%25gh56",URI.encodeFragment("abc%gh56",true));
    }
    @Test
    public void encodeQueryIgnoreTrueAndEscapedChar() {
        assertEquals("abc%2056%23",URI.encodeFragment("abc 56#",true));
    }
    @Test
    public void encodeQueryIgnoreTrueAndNotEscapedChar() {
        assertEquals("abc?56-",URI.encodeFragment("abc?56-",true));
    }
    @Test
    public void encodeQueryIgnoreFalseAndNotEscapedHex() {
        assertEquals("abc%257056",URI.encodeFragment("abc%7056",false));
    }
    @Test
    public void encodeQueryIgnoreFalseAndEscapedHex() {
        assertEquals("abc%25gh56",URI.encodeFragment("abc%gh56",false));
    }
    @Test
    public void encodeQueryIgnoreFalseAndEscapedChar() {
        assertEquals("abc?56-",URI.encodeFragment("abc?56-",false));
    }
    @Test
    public void encodeQueryIgnoreFalseAndNotEscapedChar() {
        assertEquals("abc%2056%23",URI.encodeFragment("abc 56#",false));
    }
    /////////////////////////////////////////////////////////////////
    ////////////TEST encodeFragment(string,bool)/////////////////////
    /////////////////////////////////////////////////////////////////
    @Test
    public void encodeFragmentIgnoreTrueAndNotEscapedHex() {
        assertEquals("abc%7056",URI.encodeFragment("abc%7056",true));
    }
    @Test
    public void encodeFragmentIgnoreTrueAndEscapedHex() {
        assertEquals("abc%25gh56",URI.encodeFragment("abc%gh56",true));
    }
    @Test
    public void encodeFragmentIgnoreTrueAndEscapedChar() {
        assertEquals("abc%2056%23",URI.encodeFragment("abc 56#",true));
    }
    @Test
    public void encodeFragmentIgnoreTrueAndNotEscapedChar() {
        assertEquals("abc?56-",URI.encodeFragment("abc?56-",true));
    }
    @Test
    public void encodeFragmentIgnoreFalseAndNotEscapedHex() {
        assertEquals("abc%257056",URI.encodeFragment("abc%7056",false));
    }
    @Test
    public void encodeFragmentIgnoreFalseAndEscapedHex() {
        assertEquals("abc%25gh56",URI.encodeFragment("abc%gh56",false));
    }
    @Test
    public void encodeFragmentIgnoreFalseAndEscapedChar() {
        assertEquals("abc?56-",URI.encodeFragment("abc?56-",false));
    }
    @Test
    public void encodeFragmentIgnoreFalseAndNotEscapedChar() {
        assertEquals("abc%2056%23",URI.encodeFragment("abc 56#",false));
    }

    /////////////////////////////////////////////////////////////////
    ////////////TEST decodeValue(string)/////////////////////////////
    /////////////////////////////////////////////////////////////////
    @Test
    public void decodeValueNull() throws Exception {
        assertNull(URI.decode(null));
    }

    @Test
    public void decodeValueWithoutPercent() throws Exception {
        String value = "salut les amis !";
        assertEquals(value,URI.decode(value));
    }

    @Test
    public void decodeValueWithoutFor() throws Exception {
        String value = "fsfdsfdsfdsfsdf%";
        assertEquals(value,URI.decode(value));
    }

    @Test
    public void decodeValueWithIsEscapted1() throws Exception {
        String value = "%aa";
        System.out.println(URI.unescape('0','0'));
        assertEquals("\u0000", URI.decode("%00"));
    }
    @Test
    public void decodeValueWithIsEscapted2() throws Exception {
        assertEquals("\u0020",URI.decode("%20"));
    }
    @Test
    public void decodeValueWithIsEscapted3() throws Exception {
        String value = "dds%100";
        assertEquals("!",URI.decode("%21"));
    }
    @Test
    public void decodeValueWithIsEscapted4() throws Exception {
        String value = "dds%100";
        assertEquals("~",URI.decode("%7E"));
    }
    @Test
    public void decodeValueWithIsEscapted5() throws Exception {
        String value = "dds%100";
        assertEquals("\u007F",URI.decode("%7f"));
    }
    @Test
    public void decodeValueWithIsEscapted6() throws Exception {
        String value = "dds%100";
        assertEquals("%\u00A0",URI.decode("%\u00A0"));
    }
    @Test
    public void decodeValueWithIsEscapted7() throws Exception {
        String value = "dds%100";
        assertEquals("%\u00A1",URI.decode("%\u00A1"));
    }
    @Test
    public void decodeValueWithIsEscapted8() throws Exception {
        String value = "dds%100";
        assertEquals("\u00FF",URI.decode("%ff"));
    }
    @Test
    public void decodeValueWithNoUTF8_value1() throws Exception {
        String value = "dds%100";
        assertEquals("%ga",URI.decode("%ga"));
    }
    @Test
    public void decodeValueWithNoUTF8_value2() throws Exception {
        String value = "dds%100";
        assertEquals("%ag",URI.decode("%ag"));
    }
    @Test
    public void decodeValueWithNoUTF8_value3() throws Exception {
        String value = "dds%100";
        assertEquals("%%",URI.decode("%%"));
    }
    @Test
    public void decodeValueWithNoUTF8_value4() throws Exception {
        String value = "dds%100";
        assertEquals("FF",URI.decode("FF"));
    }
    @Test
    public void decodeValueWithNoUTF8_value5() throws Exception {
        String value = "dds%100";
        assertEquals("%a",URI.decode("%a"));
    }
    @Test
    public void decodeValueWithFor() throws Exception {
        String value = "dds%100";
        assertEquals("Aladin.Robert@mel.com",URI.decode("%41ladin%2e%52obert%40mel%2ecom"));
    }

}