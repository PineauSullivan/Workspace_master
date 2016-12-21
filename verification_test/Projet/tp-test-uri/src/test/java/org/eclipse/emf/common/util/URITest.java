package org.eclipse.emf.common.util;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Collections;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

/**
 * Created by sullivan and Sebastien on 02/12/16.
 */
public class URITest {

    @Before
    public void setUp() {

    }

    @After
    public void tearDown() {

    }

    /////////////////////////////////////////////////////////////////
    ////////////TEST createGenericURI///////////////////////////////
    /////////////////////////////////////////////////////////////////
    @Test(expected = IllegalArgumentException.class)
    public void testcreateGenericURISchemeNull() {
        String scheme = null;
        String opaquePart = null;
        String fragment = null;
        URI.createGenericURI(scheme, opaquePart, fragment);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testcreateGenericURIIsArchiveScheme() {
        String scheme = "jar";
        String opaquePart = null;
        String fragment = null;
        URI.createGenericURI(scheme, opaquePart, fragment);
    }

    @Test
    public void testcreateGenericURI() {
        String scheme = "test";
        String opaquePart = "test";
        String fragment = "test";
        assertEquals(URI.createGenericURI(scheme, opaquePart, fragment), URI_CONST.POOL.intern(false, URIPool.URIComponentsAccessUnit.VALIDATE_ALL, false, scheme, opaquePart, null, false, null, null).appendFragment(fragment));
    }

    /////////////////////////////////////////////////////////////////
    ////////////TEST createHierarchicalURI5//////////////////////////
    /////////////////////////////////////////////////////////////////
    @Test(expected = IllegalArgumentException.class)
    public void createHierarchicalURIDiffNull() {
        String scheme = "test";
        String authority = "test";
        String device = "test";
        String query = "test";
        String fragment = "test";
        URI.createHierarchicalURI(scheme, authority, device, query, fragment);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createHierarchicalURIFirstIf() {
        String scheme = "test";
        String authority = null;
        String device = null;
        String query = "test";
        String fragment = "test";
        URI.createHierarchicalURI(scheme, authority, device, query, fragment);
    }


    @Test(expected = IllegalArgumentException.class)
    public void createHierarchicalURIIsArchive() {
        String scheme = "jar";
        String authority = null;
        String device = "test";
        String query = "test";
        String fragment = "test";
        URI.createHierarchicalURI(scheme, authority, device, query, fragment);
    }

    @Test
    public void createHierarchicalURISchemeNull() {
        String scheme = null;
        String authority = "test";
        String device = ":";
        String query = "test";
        String fragment = "test";
        assertEquals(URI.createHierarchicalURI(scheme, authority, device, query, fragment), URI_CONST.POOL.intern(false, URIPool.URIComponentsAccessUnit.VALIDATE_ALL, true, scheme, authority, device, false, URI_CONST.NO_SEGMENTS, query).appendFragment(fragment));
    }

    @Test
    public void createHierarchicalURI() {
        String scheme = "jarr";
        String authority = "test";
        String device = null;
        String query = "test";
        String fragment = "test";
        assertEquals(URI.createHierarchicalURI(scheme, authority, device, query, fragment), URI_CONST.POOL.intern(false, URIPool.URIComponentsAccessUnit.VALIDATE_ALL, true, scheme, authority, device, false, URI_CONST.NO_SEGMENTS, query).appendFragment(fragment));
    }

    /////////////////////////////////////////////////////////////////
    ////////////TEST createHierarchicalURI6//////////////////////////
    /////////////////////////////////////////////////////////////////
    @Test(expected = IllegalArgumentException.class)
    public void createHierarchicalURI1IsScheme() throws Exception {
        String scheme = "jar";
        String authority = null;
        String device = "test";
        String[] segments = null;
        String query = null;
        String fragment = null;
        URI.createHierarchicalURI(scheme, authority, device, segments, query, fragment);
    }

    @Test(expected = IllegalArgumentException.class)
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
        assertEquals(URI.createHierarchicalURI(scheme, authority, device, segments, query, fragment), URI_CONST.POOL.intern(false, URIPool.URIComponentsAccessUnit.VALIDATE_ALL, true, scheme, authority, device, true, segments, query).appendFragment(fragment));
    }

    /////////////////////////////////////////////////////////////////
    ////////////TEST createHierarchicalURI3//////////////////////////
    /////////////////////////////////////////////////////////////////
    @Test
    public void createHierarchicalURI2() throws Exception {
        String[] segments = null;
        String query = null;
        String fragment = null;
        assertEquals(URI.createHierarchicalURI(segments, query, fragment), URI_CONST.POOL.intern(false, URIPool.URIComponentsAccessUnit.VALIDATE_ALL, true, null, null, null, false, segments, query).appendFragment(fragment));
    }

    /////////////////////////////////////////////////////////////////
    ////////////TEST createURI///////////////////////////////////////
    /////////////////////////////////////////////////////////////////
    @Test
    public void createURI() throws Exception {
        String value = "foo://test/test/test";
        URI uri = URI.createURI(value);
        assertEquals(uri.toString(), "foo://test/test/test");
    }

    @Test
    public void createURIdiese() throws Exception {
        String uri = "te#st";
        assertEquals(URI.createURI(uri), URI_CONST.POOL.intern(uri.substring(0, 2)).appendFragment(uri.substring(2 + 1)));
    }


    /////////////////////////////////////////////////////////////////
    ////////////TEST createURI1//////////////////////////////////////
    /////////////////////////////////////////////////////////////////
    @Test
    public void createURI1() throws Exception {
        String uri = "test";
        boolean ignoreEscaped = true;
        assertEquals(URI.createURI(uri, ignoreEscaped), URI.createURIWithCache(URI.encodeURI(uri, ignoreEscaped, URI_CONST.FRAGMENT_LAST_SEPARATOR)));
    }

    /////////////////////////////////////////////////////////////////
    ////////////TEST createURI2//////////////////////////////////////
    /////////////////////////////////////////////////////////////////
    @Test
    public void createURI2() throws Exception {
        String uri = "test";
        boolean ignoreEscaped = true;
        int fragmentLocationStyle = 5;
        assertEquals(URI.createURI(uri, ignoreEscaped, fragmentLocationStyle), URI.createURIWithCache(URI.encodeURI(uri, ignoreEscaped, fragmentLocationStyle)));
    }

    /////////////////////////////////////////////////////////////////
    ////////////TEST createDeviceURI/////////////////////////////////
    /////////////////////////////////////////////////////////////////
    @Test
    public void createDeviceURI() throws Exception {
        String uri = "test";
        assertEquals(URI.createDeviceURI(uri), URI.createURIWithCache(uri));
    }

    /////////////////////////////////////////////////////////////////
    ////////////TEST createURIWithCache//////////////////////////////
    /////////////////////////////////////////////////////////////////
    @Test
    public void createURIWithCacheMoinsUn() throws Exception {
        String uri = "testdgxgfdfgdgfd";
        assertEquals(URI_CONST.POOL.intern(uri), URI.createURIWithCache(uri));
    }

    @Test(expected = IllegalArgumentException.class)
    public void createURIWithCachePlusUn() {
        String uri = "te#stdgxgfdfgdgfd";
        assertEquals(URI_CONST.POOL.intern(uri), URI_CONST.POOL.intern(uri.substring(0, 2)).appendFragment(uri.substring(2 + 1)));
    }

    /////////////////////////////////////////////////////////////////
    ////////////TEST createFileURI///////////////////////////////////
    /////////////////////////////////////////////////////////////////
    @Test
    public void createFileURI() throws Exception {
        String pathname = "test";
        assertEquals(URI.createFileURI(pathname), URI_CONST.POOL.internFile(pathname));
    }


    /////////////////////////////////////////////////////////////////
    ////////////TEST createPlatformResourceURI///////////////////////
    /////////////////////////////////////////////////////////////////
    @Test
    public void createPlatformResourceURI() throws Exception {
        String pathName = "test";
        assertEquals(URI.createPlatformResourceURI(pathName), URI.createPlatformResourceURI(pathName, URI_CONST.ENCODE_PLATFORM_RESOURCE_URIS));
    }

    /////////////////////////////////////////////////////////////////
    ////////////TEST createPlatformResourceURI 2 parametre///////////
    /////////////////////////////////////////////////////////////////
    @Test
    public void createPlatformResourceURI1() throws Exception {
        String pathName = "test";
        boolean encode = false;
        assertEquals(URI.createPlatformResourceURI(pathName, encode), URI_CONST.POOL.intern(URI_CONST.SEGMENT_RESOURCE, pathName, encode));
    }


    /////////////////////////////////////////////////////////////////
    ////////////TEST createPlatformPluginURI ////////////////////////
    /////////////////////////////////////////////////////////////////
    @Test
    public void createPlatformPluginURI() throws Exception {
        String pathName = "test";
        boolean encode = true;
        assertEquals(URI.createPlatformPluginURI(pathName, encode), URI_CONST.POOL.intern(URI_CONST.SEGMENT_PLUGIN, pathName, encode));
    }

    /////////////////////////////////////////////////////////////////
    ////////////TEST validScheme ////////////////////////////////////
    /////////////////////////////////////////////////////////////////
    @Test
    public void validSchemeValueNull() throws Exception {
        assertTrue(URI.validScheme(null));
    }

    @Test
    public void validSchemeValueNotNull() throws Exception {
        String value = "test";
        assertTrue(URI.validScheme(value));
    }

    @Test
    public void validSchemeValueNotNullFalse() throws Exception {
        String value = ":/?#";
        assertFalse(URI.validScheme(value));
    }

    /////////////////////////////////////////////////////////////////
    ////////////TEST validOpaquePart ////////////////////////////////
    /////////////////////////////////////////////////////////////////
    @Test
    public void validOpaquePart() throws Exception {
        assertFalse(URI.validOpaquePart(null));
    }

    @Test
    public void validOpaquePartIndexDiese() throws Exception {
        assertFalse(URI.validOpaquePart("test#test"));
    }

    @Test
    public void validOpaquePartLengthZero() throws Exception {
        assertFalse(URI.validOpaquePart(""));
    }

    @Test
    public void validOpaquePartChatAtZero() throws Exception {
        assertFalse(URI.validOpaquePart("#test"));
    }

    @Test
    public void validOpaquePartNotNull() throws Exception {
        assertTrue(URI.validOpaquePart("test"));
    }

    @Test
    public void validOpaquePartContain() throws Exception {
        assertFalse(URI.validOpaquePart("/?#"));
    }

    @Test
    public void validOpaquePartContainFalse() throws Exception {
        assertFalse(URI.validOpaquePart("/"));
    }

    /////////////////////////////////////////////////////////////////
    ////////////TEST validAuthority  ////////////////////////////////
    /////////////////////////////////////////////////////////////////
    @Test
    public void validAuthorityNull() throws Exception {
        assertTrue(URI.validAuthority(null));
    }

    @Test
    public void validAuthoritynotnullAndNotCointain() throws Exception {
        assertTrue(URI.validAuthority("test"));
    }

    @Test
    public void validAuthorityNotNullAndContain() throws Exception {
        assertFalse(URI.validAuthority("/?#"));
    }


    /////////////////////////////////////////////////////////////////
    ////////////TEST validArchive    ////////////////////////////////
    /////////////////////////////////////////////////////////////////
    @Test
    public void validArchiveAuthorityNull() throws Exception {
        assertFalse(URI.validArchiveAuthority(null));
    }

    @Test
    public void validArchiveAuthorityLenghtNull() throws Exception {
        assertFalse(URI.validArchiveAuthority(""));
    }

    @Test
    public void validArchiveAuthorityCharAtFalse() throws Exception {
        assertFalse(URI.validArchiveAuthority("!+"));
    }

    @Test
    public void validArchiveAuthorityCharAtTrue() throws Exception {
        String value = "+!";
        URI archiveURI = URI.createURI(value.substring(0, value.length() - 1));
        assertEquals(!archiveURI.hasFragment(), URI.validArchiveAuthority("+!"));
    }

    @Test
    public void validArchiveAuthorityCharAtFalse2() throws Exception {
        String value = "/?#!";
        URI archiveURI = URI.createURI(value.substring(0, value.length() - 1));
        assertEquals(!archiveURI.hasFragment(), URI.validArchiveAuthority("/?#!"));
    }

    /////////////////////////////////////////////////////////////////
    ////////////TEST validJarAuthority //////////////////////////////
    /////////////////////////////////////////////////////////////////
    @Test
    public void validJarAuthority() throws Exception {
        assertEquals(URI.validJarAuthority("test"), URI.validArchiveAuthority("test"));
    }

    /////////////////////////////////////////////////////////////////
    ////////////TEST validDevice  ///////////////////////////////////
    /////////////////////////////////////////////////////////////////
    @Test
    public void validDeviceNull() throws Exception {
        assertTrue(URI.validDevice(null));
    }

    @Test
    public void validDeviceLength0() throws Exception {
        assertFalse(URI.validDevice(""));
    }

    @Test
    public void validDeviceDeviceidenFalse() throws Exception {
        assertFalse(URI.validDevice(":o"));
    }

    @Test
    public void validDeviceDeviceContainTrue() throws Exception {
        assertEquals(false, URI.validDevice("/?#"));
    }

    @Test
    public void validDevice() throws Exception {
        assertTrue(URI.validDevice(":"));
    }

    @Test
    public void validDevicefalse() throws Exception {
        assertFalse(URI.validDevice("/?#:::::::::::::"));
    }

    /////////////////////////////////////////////////////////////////
    ////////////TEST validSegment ///////////////////////////////////
    /////////////////////////////////////////////////////////////////
    @Test
    public void validSegmentNull() throws Exception {
        assertFalse(URI.validSegment(null));
    }

    @Test
    public void validSegmentValidateFalse() throws Exception {
        assertFalse(URI.validSegment("/?#"));
    }

    @Test
    public void validSegment() throws Exception {
        assertTrue(URI.validSegment("test"));
    }


    /////////////////////////////////////////////////////////////////
    ////////////TEST validSegments //////////////////////////////////
    /////////////////////////////////////////////////////////////////
    @Test
    public void validSegmentsNull() throws Exception {
        assertFalse(URI.validSegments(null));
    }

    @Test
    public void validSegmentsLengthZero() throws Exception {
        assertTrue(URI.validSegments(new String[0]));
    }

    @Test
    public void validSegmentsForFalse() throws Exception {
        String[] values = new String[1];
        values[0] = "/?#";
        assertFalse(URI.validSegments(values));
    }

    /////////////////////////////////////////////////////////////////
    ////////////TEST validQuery /////////////////////////////////////
    /////////////////////////////////////////////////////////////////
    @Test
    public void validQueryNull() throws Exception {
        assertTrue(URI.validQuery(null));
    }

    @Test
    public void validQueryOIndexofmoins1() throws Exception {
        assertTrue(URI.validQuery("test"));
    }

    @Test
    public void validQueryOIndexofplus1() throws Exception {
        assertFalse(URI.validQuery("test#test"));
    }

    /////////////////////////////////////////////////////////////////
    ////////////TEST validFragment //////////////////////////////////
    /////////////////////////////////////////////////////////////////
    @Test
    public void validFragment() throws Exception {
        assertTrue(URI.validFragment(""));
    }

    /////////////////////////////////////////////////////////////////
    ////////////TEST isHierarchical /////////////////////////////////
    /////////////////////////////////////////////////////////////////
    @Test
    public void isHierarchicalTestHierarchicalURI() throws Exception {
        URI monUriTest = URI.createURI("test");
        assertTrue(monUriTest.isHierarchical());
    }


    @Test
    public void isHierarchicalTestGenericForm() throws Exception {
        URI monUriTest = URI.createGenericURI("test", "test", "test");
        assertFalse(monUriTest.isHierarchical());
    }

    ///////////////////////////////////////////////////////////////
    ////////////TEST hasAuthority /////////////////////////////////
    ///////////////////////////////////////////////////////////////
    @Test
    public void hasAuthorityOtherwise() throws Exception {
        URI monUriTest = URI.createURI("test");
        assertFalse(monUriTest.hasAuthority());
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
        assertTrue(monUriTest.hasAuthority());
    }

    @Test
    public void testHasAuthority() {
        String[] segments = { "s1", "s2" };
        String query = "query";
        String fragment = "fragment";
        URI uri = URI.createHierarchicalURI(segments, query, fragment);
        assertFalse(uri.hasAuthority());
    }
    ////////////////////////////////////////////////////////////////
    ////////////TEST hasOpaquePart /////////////////////////////////
    ////////////////////////////////////////////////////////////////
    @Test
    public void hasOpaquePartOtherwise() throws Exception {
        URI monUriTest = URI.createURI("test");
        assertFalse(monUriTest.hasOpaquePart());
    }

    @Test
    public void hasOpaquePartNonHierarchical() throws Exception {
        URI monUriTest = URI.createGenericURI("test", "test", "test");
        assertTrue(monUriTest.hasOpaquePart());
    }

    ////////////////////////////////////////////////////////////////
    ////////////TEST hasDevice /////////////////////////////////////
    ////////////////////////////////////////////////////////////////
    @Test
    public void hasDeviceTrue() throws Exception {
        URI monUriTest;
        String scheme = "jarr";
        String authority = "test";
        String device = ":";
        String query = "test";
        String fragment = "test";
        monUriTest = URI.createHierarchicalURI(scheme, authority, device, query, fragment);
        assertTrue(monUriTest.hasDevice());
    }


    @Test
    public void hasDeviceFalse() throws Exception {
        URI monUriTest = URI.createURI("test");

        assertFalse(monUriTest.hasDevice());
    }

    ////////////////////////////////////////////////////////////////
    ////////////TEST hasPath ///////////////////////////////////////
    ////////////////////////////////////////////////////////////////
    @Test
    public void hasPathTrue() throws Exception {
        URI monUriTest = URI.createPlatformPluginURI("pathName", true);
        assertTrue( monUriTest.hasPath());
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
        assertFalse(monUriTest.hasPath());
    }

    ////////////////////////////////////////////////////////////////
    ////////////TEST hasAbsolutePath ///////////////////////////////
    ////////////////////////////////////////////////////////////////
    @Test
    public void hasAbsolutePathHierarchicalWithAbsolutePath() throws Exception {
        URI monUriTest = URI.createHierarchicalURI("test", "test", ":", null, "test", "test");
        assertTrue(monUriTest.hasAbsolutePath());
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
        assertFalse(monUriTest.hasAbsolutePath());
    }

    ////////////////////////////////////////////////////////////////
    ////////////TEST hasRelativePath ///////////////////////////////
    ////////////////////////////////////////////////////////////////
    @Test
    public void hasRelativePathHierarchicalWithRelativePath() throws Exception {
        URI monUriTest = URI.createFileURI("pathName");
        assertTrue(monUriTest.hasRelativePath());
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
        assertFalse(monUriTest.hasRelativePath());
    }


    ////////////////////////////////////////////////////////////////
    ////////////TEST hasEmptyPath //////////////////////////////////
    ////////////////////////////////////////////////////////////////
    @Test
    public void hasEmptyPathFalse() throws Exception {
        URI monUriTest = URI.createPlatformPluginURI("pathName", true);
        assertFalse(monUriTest.hasEmptyPath());
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
        URI monUriTest = URI.createHierarchicalURI(null, "test", "test");
        assertTrue(monUriTest.hasEmptyPath());
    }


    ////////////////////////////////////////////////////////////////
    ////////////TEST hasQuery //////////////////////////////////////
    ////////////////////////////////////////////////////////////////
    @Test
    public void hasQueryTrue() throws Exception {
        URI monUriTest;
        String scheme = "jarr";
        String authority = "test";
        String device = ":";
        String query = "test";
        String fragment = "test";
        monUriTest = URI.createHierarchicalURI(scheme, authority, device, query, fragment);
        assertTrue(monUriTest.hasQuery());
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
        assertFalse(monUriTest.hasQuery());
    }

    ////////////////////////////////////////////////////////////////
    ////////////TEST hasFragment ///////////////////////////////////
    ////////////////////////////////////////////////////////////////
    @Test
    public void hasFragmentTrue() throws Exception {
        URI monUriTest;
        String scheme = "jarr";
        String authority = "test";
        String device = ":";
        String query = "test";
        String fragment = "test";
        monUriTest = URI.createHierarchicalURI(scheme, authority, device, query, fragment);
        assertTrue(monUriTest.hasFragment());
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
        assertFalse(monUriTest.hasFragment());
    }

    ////////////////////////////////////////////////////////////////
    ////////////TEST isCurrentDocumentReference ////////////////////
    ////////////////////////////////////////////////////////////////
    @Test
    public void isCurrentDocumentReferenceTrue() throws Exception {
        URI monUriTest;
        monUriTest = URI.createHierarchicalURI(null, null, null);
        assertTrue(monUriTest.isCurrentDocumentReference());
    }

    @Test
    public void isCurrentDocumentReferenceFalse() throws Exception {
        URI monUriTest = URI.createURI("test");
        assertFalse(monUriTest.isCurrentDocumentReference());
    }

    ////////////////////////////////////////////////////////////////
    ////////////TEST isEmpty ///////////////////////////////////////
    ////////////////////////////////////////////////////////////////
    @Test
    public void isEmptyTrue() throws Exception {
        URI monUriTest;
        monUriTest = URI.createHierarchicalURI(null, null, null);
        assertTrue(monUriTest.isEmpty());
    }

    @Test
    public void isEmptyFalse() throws Exception {
        URI monUriTest = URI.createURI("test");
        assertFalse(monUriTest.isEmpty());
    }


    ////////////////////////////////////////////////////////////////
    ////////////TEST isFile ////////////////////////////////////////
    ////////////////////////////////////////////////////////////////
    @Test
    public void isFileFalse() throws Exception {
        URI monUriTest;
        String scheme = "jarr";
        String authority = "test";
        String device = null;
        String query = "test";
        String fragment = "test";
        monUriTest = URI.createHierarchicalURI(scheme, authority, device, query, fragment);
        assertFalse(monUriTest.isFile());
    }

    @Test
    public void isFileTrue() throws Exception {
        URI monUriTest = URI.createFileURI("test");
        assertTrue(monUriTest.isFile());
    }

    ////////////////////////////////////////////////////////////////
    ////////////TEST isPlatform ////////////////////////////////////
    ////////////////////////////////////////////////////////////////
    @Test
    public void isPlatformTrue() throws Exception {
        URI monUriTest = URI.createPlatformResourceURI("test", true);
        assertTrue(monUriTest.isPlatform());
    }


    @Test
    public void isPlatformFalse() throws Exception {
        URI monUriTest = URI.createURI("test");
        assertFalse(monUriTest.isPlatform());
    }

    ////////////////////////////////////////////////////////////////
    ////////////TEST isPlatformResource ////////////////////////////
    ////////////////////////////////////////////////////////////////
    @Test
    public void isPlatformResourceTrue() throws Exception {
        URI monUriTest = URI.createPlatformResourceURI("test", true);
        assertTrue(monUriTest.isPlatformResource());
    }

    @Test
    public void isPlatformResourceFalse() throws Exception {
        URI monUriTest = URI.createURI("test");
        assertFalse(monUriTest.isPlatformResource());
    }

    @Test
    public void isPlatformResourceFalse2() throws Exception {
        URI monUriTest = URI.createPlatformPluginURI("test", false);
        assertFalse(monUriTest.isPlatformResource());
    }

    ////////////////////////////////////////////////////////////////
    ////////////TEST isPlatformPlugin //////////////////////////////
    ////////////////////////////////////////////////////////////////
    @Test
    public void isPlatformPluginTrue() throws Exception {
        URI monUriTest = URI.createPlatformPluginURI("test", true);
        assertTrue(monUriTest.isPlatformPlugin());
    }

    @Test
    public void isPlatformPluginFalse() throws Exception {
        URI monUriTest = URI.createURI("test");
        assertFalse( monUriTest.isPlatformPlugin());
    }

    @Test
    public void isPlatformPluginFalse2() throws Exception {
        URI monUriTest = URI.createPlatformResourceURI("test");
        assertFalse(monUriTest.isPlatformPlugin());
    }

    ////////////////////////////////////////////////////////////////
    ////////////TEST isArchive /////////////////////////////////////
    ////////////////////////////////////////////////////////////////
    @Test
    public void isArchiveTrue() throws Exception {
        String scheme = "jar";
        String authority = "test!";
        String device = null;
        String[] segments = { "test", "test" };
        String query = "query";
        String fragment = "notnull";
        URI uri = URI.createHierarchicalURI(scheme, authority, device,
                segments, query, fragment);
        assertTrue(uri.isArchive());
     }


    @Test
    public void isArchiveFalse() throws Exception {
        URI monUriTest = URI.createURI("test");
        assertEquals(false, monUriTest.isArchive());
    }


    ////////////////////////////////////////////////////////////////
    ////////////TEST isArchiveScheme ///////////////////////////////
    ////////////////////////////////////////////////////////////////
    @Test
    public void isArchiveSchemeTrue() throws Exception {
        assertEquals(true, URI.isArchiveScheme("archive"));
    }

    @Test
    public void isArchiveSchemeFalse() throws Exception {
        assertEquals(false, URI.isArchiveScheme("test"));
    }


    ////////////////////////////////////////////////////////////////
    ////////////TEST hashCode //////////////////////////////////////
    ////////////////////////////////////////////////////////////////
    @Test
    public void hashCodeTest() throws Exception {
        URI monUriTest = URI.createURI("test");
        assertEquals(3556498, monUriTest.hashCode());
    }

    ////////////////////////////////////////////////////////////////
    ////////////TEST scheme ////////////////////////////////////////
    ////////////////////////////////////////////////////////////////
    @Test
    public void schemeTest() throws Exception {
        URI monUriTest;
        String scheme = "jarr";
        String authority = "test";
        String device = null;
        String query = "test";
        String fragment = "test";
        monUriTest = URI.createHierarchicalURI(scheme, authority, device, query, fragment);
        assertEquals("jarr", monUriTest.scheme());
    }


    @Test
    public void schemeNull() throws Exception {
        URI monUriTest = URI.createURI("test");
        assertEquals(null, monUriTest.scheme());
    }

    ////////////////////////////////////////////////////////////////
    ////////////TEST opaquePart ////////////////////////////////////
    ////////////////////////////////////////////////////////////////
    @Test
    public void opaquePartNull() throws Exception {
        URI monUriTest = URI.createURI("test");
        assertEquals(null, monUriTest.opaquePart());
    }

    @Test
    public void opaquePartNotNull() throws Exception {
        URI monUriTest;
        monUriTest = URI.createGenericURI("test", "test", "test");
        assertNotEquals(null, monUriTest.opaquePart());
    }

    ////////////////////////////////////////////////////////////////
    ////////////TEST authority /////////////////////////////////////
    ////////////////////////////////////////////////////////////////
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
        assertEquals("test", monUriTest.authority());
    }

    ////////////////////////////////////////////////////////////////
    ////////////TEST userInfo //////////////////////////////////////
    ////////////////////////////////////////////////////////////////
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


    ////////////////////////////////////////////////////////////////
    ////////////TEST host //////////////////////////////////////////
    ////////////////////////////////////////////////////////////////
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

    ////////////////////////////////////////////////////////////////
    ////////////TEST port //////////////////////////////////////////
    ////////////////////////////////////////////////////////////////
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

    ////////////////////////////////////////////////////////////////
    ////////////TEST device ////////////////////////////////////////
    ////////////////////////////////////////////////////////////////
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

    ////////////////////////////////////////////////////////////////
    ////////////TEST segments //////////////////////////////////////
    ////////////////////////////////////////////////////////////////
    @Test
    public void segmentsNull() throws Exception {
        URI monUriTest = URI.createURI("test");
        assertEquals(monUriTest.segments(), monUriTest.segments());
    }

    @Test
    public void segmentsNotNull() throws Exception {
        String[] segments = new String[1];
        segments[0] = "test";
        URI monUriTest = URI.createHierarchicalURI(segments, "test", "test");
        assertNotEquals(null, monUriTest.segments());
    }

    ////////////////////////////////////////////////////////////////
    ////////////TEST segmentsList //////////////////////////////////
    ////////////////////////////////////////////////////////////////
    @Test
    public void segmentsListNull() throws Exception {
        URI monUriTest;
        String scheme = "jarr";
        String authority = "te@s:t";
        String device = ":";
        String query = "test";
        String fragment = "test";
        monUriTest = URI.createHierarchicalURI(scheme, authority, device, query, fragment);
        assertEquals(Collections.emptyList(), monUriTest.segmentsList());
    }

    @Test
    public void segmentsListNotNull() throws Exception {
        String[] segments = new String[1];
        segments[0] = "test";
        URI monUriTest = URI.createHierarchicalURI(segments, "test", "test");
        assertNotEquals(null, monUriTest.segmentsList());
    }

    ////////////////////////////////////////////////////////////////
    ////////////TEST segmentCount //////////////////////////////////
    ////////////////////////////////////////////////////////////////
    @Test
    public void segmentCountZero() throws Exception {
        URI monUriTest;
        String scheme = "jarr";
        String authority = "te@s:t";
        String device = ":";
        String query = "test";
        String fragment = "test";
        monUriTest = URI.createHierarchicalURI(scheme, authority, device, query, fragment);
        assertEquals(0, monUriTest.segmentCount());
    }

    @Test
    public void segmentCountUn() throws Exception {
        String[] segments = new String[1];
        segments[0] = "test";
        URI monUriTest = URI.createHierarchicalURI(segments, "test", "test");
        assertEquals(1, monUriTest.segmentCount());
    }

    ////////////////////////////////////////////////////////////////
    ////////////TEST segment ///////////////////////////////////////
    ////////////////////////////////////////////////////////////////
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void segmentTest() {
        exception.expect(IndexOutOfBoundsException.class);
        URI monUriTest = URI.createURI("test");
        monUriTest.segment(0);
    }

    ////////////////////////////////////////////////////////////////
    ////////////TEST lastSegment ///////////////////////////////////
    ////////////////////////////////////////////////////////////////
    @Test
    public void lastSegmentTest() throws Exception {
        URI monUriTest = URI.createURI("test");
        assertEquals("test", monUriTest.lastSegment());
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
        assertEquals(null, monUriTest.lastSegment());
    }

    ////////////////////////////////////////////////////////////////
    ////////////TEST path //////////////////////////////////////////
    ////////////////////////////////////////////////////////////////
    @Test
    public void pathNotNull() throws Exception {
        URI monUriTest = URI.createPlatformResourceURI("test", false);
        assertEquals("/resource/test", monUriTest.path());
    }

    @Test
    public void path() throws Exception {
        String scheme = "jarr";
        String authority = "te@s:t";
        String device = ":";
        String query = "test";
        String fragment = "test";
        URI monUriTest = URI.createHierarchicalURI(scheme, authority, device, query, fragment);
        assertEquals(null, monUriTest.path());
    }

    ////////////////////////////////////////////////////////////////
    ////////////TEST devicePath ////////////////////////////////////
    ////////////////////////////////////////////////////////////////
    @Test
    public void devicePathNull() throws Exception {
        URI monUriTest;
        String scheme = "jarr";
        String authority = "te@s:t";
        String device = ":";
        String query = "test";
        String fragment = "test";
        monUriTest = URI.createHierarchicalURI(scheme, authority, device, query, fragment);
        assertEquals(null, monUriTest.devicePath());
    }

    @Test
    public void devicePathNotNull() throws Exception {
        URI monUriTest = URI.createPlatformResourceURI("test", false);
        assertEquals("/resource/test", monUriTest.devicePath());
    }

    ////////////////////////////////////////////////////////////////
    ////////////TEST query /////////////////////////////////////////
    ////////////////////////////////////////////////////////////////
    @Test
    public void queryNotNull() throws Exception {
        URI monUriTest = URI.createURI("test");
        assertNotEquals(null, monUriTest.devicePath());
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
        assertEquals(null, monUriTest.devicePath());
    }

    ////////////////////////////////////////////////////////////////
    ////////////TEST appendQuery ///////////////////////////////////
    ////////////////////////////////////////////////////////////////
    @Test(expected = IllegalArgumentException.class)
    public void appendQueryNotValidQuery() throws Exception {
        URI monUriTest = URI.createURI("test");
        monUriTest.appendQuery("#");
    }


    @Test
    public void appendQueryValidQuery() throws Exception {
        URI monUriTest = URI.createURI("test");
        assertEquals(monUriTest, monUriTest.appendQuery(null));
    }

    ////////////////////////////////////////////////////////////////
    ////////////TEST trimQuery /////////////////////////////////////
    ////////////////////////////////////////////////////////////////
    @Test
    public void trimQueryTest() throws Exception {
        URI monUriTest = URI.createURI("test");
        assertEquals(monUriTest, monUriTest.trimQuery());
    }

    ////////////////////////////////////////////////////////////////
    ////////////TEST fragment //////////////////////////////////////
    ////////////////////////////////////////////////////////////////
    @Test
    public void fragmentNotNull() throws Exception {
        URI monUriTest;
        String scheme = "jarr";
        String authority = "te@s:t";
        String device = ":";
        String query = null;
        String fragment = "test";
        monUriTest = URI.createHierarchicalURI(scheme, authority, device, query, fragment);
        assertEquals("test", monUriTest.fragment());
    }

    @Test
    public void fragmentNull() throws Exception {
        URI monUriTest = URI.createURI("test");
        assertEquals(null, monUriTest.fragment());
    }

    ////////////////////////////////////////////////////////////////
    ////////////TEST appendFragment ////////////////////////////////
    ////////////////////////////////////////////////////////////////
    @Test
    public void appendFragmentNull() throws Exception {
        URI monUriTest;
        String scheme = "jarr";
        String authority = "te@s:t";
        String device = ":";
        String query = null;
        String fragment = null;
        monUriTest = URI.createHierarchicalURI(scheme, authority, device, query, fragment);
        assertEquals(monUriTest, monUriTest.appendFragment(null));
    }


    @Test
    public void appendFragmentNotNull() throws Exception {
        URI monUriTest;
        String scheme = "jarr";
        String authority = "te@s:t";
        String device = ":";
        String[] segments = {"foo", "bar"};
        String query = null;
        String fragment = "notnull";
        monUriTest = URI.createHierarchicalURI(scheme, authority, device,
                segments, query, fragment);
        monUriTest = monUriTest.appendFragment("fragment");
        assertEquals("fragment", monUriTest.fragment());
    }


    @Test
    public void trimFragmentNull() throws Exception {
        URI monUriTest;
        String scheme = "jarr";
        String authority = "te@s:t";
        String device = ":";
        String[] segments = {"bar", "foo"};
        String query = null;
        String fragment = "notnull";
        monUriTest = URI.createHierarchicalURI(scheme, authority, device,
                segments, query, fragment);
        URI monUriTestBis = monUriTest.trimQuery();
        assertEquals(monUriTest, monUriTestBis);
    }

    @Test
    public void trimFragmentNotNull() throws Exception {
        URI monUriTest;
        String scheme = "jarr";
        String authority = "test!";
        String device = "test:";
        String[] segments = {"bar", "foo"};
        String query = "query";
        String fragment = "notnull";
        monUriTest = URI.createHierarchicalURI(scheme, authority, device,
                segments, query, fragment);
        URI monUriTestBis = monUriTest.trimQuery();
        assertEquals("jarr://test!/test:/bar/foo#notnull", monUriTestBis.toString());
    }

    ////////////////////////////////////////////////////////////////
    ////////////TEST resolve un parametre //////////////////////////
    ////////////////////////////////////////////////////////////////
    @Test(expected = IllegalArgumentException.class)
    public void resolveUnParamException() throws Exception {
        URI monUriTest;
        String scheme = "jarr";
        String authority = "test!";
        String device = "test:";
        String[] segments = {"bar", "foo"};
        String query = "query";
        String fragment = "notnull";
        monUriTest = URI.createHierarchicalURI(scheme, authority, device, segments, query, fragment);
        URI base = URI.createURI("basebase");
        monUriTest.resolve(base);
    }

    @Test(expected = IllegalArgumentException.class)
    public void resolveUnParamNotRelative() throws Exception {
        URI monUriTest;
        String scheme = "bla";
        String authority = "test!";
        String device = "test:";
        String[] segments = {"bar", "foo"};
        String query = "query";
        String fragment = "notnull";
        monUriTest = URI.createHierarchicalURI(scheme, authority, device, segments, query, fragment);
        URI base = monUriTest.createHierarchicalURI(segments, query, fragment);
        monUriTest.resolve(base);
    }

    @Test
    public void resolveUnParamTest() throws Exception {
        String scheme = "scheme";
        String authority = "authority!";
        String device = "device:";
        String[] segments = {"foo", "bar"};
        String query = "query";
        String fragment = "fragment";
        URI base = URI.createHierarchicalURI(scheme, authority, device,
                segments, query, fragment);
        URI uri = URI.createHierarchicalURI(segments, query, fragment);
        assertEquals("scheme://authority!/device:/foo/foo/bar?query#fragment",
                uri.resolve(base).toString());
    }

    ////////////////////////////////////////////////////////////////
    ////////////TEST resolve deux parametres ///////////////////////
    ////////////////////////////////////////////////////////////////
    @Test(expected = IllegalArgumentException.class)
    public void resolveDeuxParamException() throws Exception {
        URI monUriTest;
        String scheme = "jarr";
        String authority = "test!";
        String device = "test:";
        String[] segments = {"bar", "foo"};
        String query = "query";
        String fragment = "notnull";
        monUriTest = URI.createHierarchicalURI(scheme, authority, device, segments, query, fragment);
        URI base = URI.createHierarchicalURI(segments, query, fragment);
        ;
        monUriTest.resolve(base, true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void resolveDeuxParamNotHierarchical() throws Exception {
        URI monUriTest;
        String scheme = "jarr";
        String authority = "test!";
        String device = "test:";
        String[] segments = {"bar", "foo"};
        String query = "query";
        String fragment = "notnull";
        monUriTest = URI.createHierarchicalURI(scheme, authority, device, segments, query, fragment);
        URI base = URI.createURI("basebase");
        monUriTest.resolve(base, true);
    }

    @Test
    public void resolveDeuxParamTrue() throws Exception {
        String scheme = "jarr";
        String authority = "test!";
        String device = "test:";
        String[] segments = {"foo", "bar"};
        String query = "query";
        String fragment = "notnull";
        URI base = URI.createHierarchicalURI(scheme, authority, device,
                segments, query, fragment);
        URI uri = URI.createHierarchicalURI(segments, query, fragment);
        assertEquals("jarr://test!/test:/foo/foo/bar?query#notnull",
                uri.resolve(base, true).toString());
    }

    ////////////////////////////////////////////////////////////////
    ////////////TEST deresolve un parametre ////////////////////////
    ////////////////////////////////////////////////////////////////
    @Test
    public void deresolveUnParamNotHi() throws Exception {
        URI monUriTest = URI
                .createURI("jarr://test!/test:/foo/foo/bar?query#notnull");
        URI base = monUriTest.createURI("here");
        assertEquals(monUriTest.toString(), monUriTest.deresolve(base).toString());
    }

    @Test
    public void deresolveUnParamRelative() throws Exception {
        String[] segments = {"test", "bar"};
        String query = "query";
        String fragment = "notnull";
        URI monUriTest = URI
                .createURI("jarr://test!/test:/test/test/bar?query#notnull");
        URI base = URI.createHierarchicalURI(segments, query, fragment);
        assertEquals(monUriTest.toString(), monUriTest.deresolve(base).toString());
    }

    @Test
    public void deresolveUnParamExp() throws Exception {
        String[] segments = {"test", "bar"};
        String query = "query";
        String fragment = "notnull";
        String scheme = "jarr";
        String authority = "test!";
        String device = "test:";
        URI monUriTest = URI.createURI("jarr://test!/test:/test/test/bar?query#notnull");
        URI base = URI.createHierarchicalURI(scheme, authority, device, segments, query, fragment);
        URI exp_uri = URI.createHierarchicalURI(segments, query, fragment);
        assertEquals(exp_uri.toString(), monUriTest.deresolve(base).toString());
    }

    ////////////////////////////////////////////////////////////////
    ////////////TEST deresolve quatre parametres ///////////////////
    ////////////////////////////////////////////////////////////////
    @Test
    public void deresolve1QuatreParamNotHi() throws Exception {
        URI monUriTest = URI
                .createURI("jarr://test!/test:/foo/foo/bar?query#notnull");
        URI base = monUriTest.createURI("here");
        assertEquals(monUriTest.toString(), monUriTest.deresolve(base, true, true, true).toString());
    }


    @Test
    public void deresolve1QuatreParamRelative() throws Exception {
        String[] segments = {"test", "bar"};
        String query = "query";
        String fragment = "notnull";
        URI monUriTest = URI
                .createURI("jarr://test!/test:/test/test/bar?query#notnull");
        URI base = URI.createHierarchicalURI(segments, query, fragment);
        assertEquals(monUriTest.toString(), monUriTest.deresolve(base, true, true, true).toString());
    }

    @Test
    public void deresolveQuatreParamShortFalse() throws Exception {
        String[] segments = {"test", "bar"};
        String query = "query";
        String fragment = "notnull";
        String scheme = "jarr";
        String authority = "test!";
        String device = "test:";
        URI monUriTest = URI.createURI("jarr://test!/test:/test/test/bar?query#notnull");
        URI base = URI.createHierarchicalURI(scheme, authority, device, segments, query, fragment);
        URI exp_uri = URI.createHierarchicalURI(segments, query, fragment);
        assertTrue(monUriTest.deresolve(base, true, true, false).hasRelativePath());
    }

    @Test
    public void deresolveQuatreParamShortFalseAnyFalse() throws Exception {
        String[] segments = {"test", "bar"};
        String query = "query";
        String fragment = "notnull";
        String scheme = "jarr";
        String authority = "test!";
        String device = "test:";
        URI monUriTest = URI.createURI("jarr://test!/test:/test/test/bar?query#notnull");
        URI base = URI.createHierarchicalURI(scheme, authority, device, segments, query, fragment);
        assertTrue(monUriTest.deresolve(base, true, false, false).hasAbsolutePath());
    }

    @Test
    public void deresolveQuatreParamAnyFalse() throws Exception {
        String[] segments = {"test", "bar"};
        String query = "query";
        String fragment = "notnull";
        String scheme = "jarr";
        String authority = "test!";
        String device = "test:";
        URI monUriTest = URI.createURI("jarr://test!/test:/test/test/bar?query#notnull");
        URI base = URI.createHierarchicalURI(scheme, authority, device, segments, query, fragment);
        URI exp_uri = URI.createHierarchicalURI(segments, query, fragment);
        assertEquals(exp_uri.toString(), monUriTest.deresolve(base, true, false, true).toString());
    }

    ////////////////////////////////////////////////////////////////
    ////////////TEST toFileString //////////////////////////////////
    ////////////////////////////////////////////////////////////////
    @Test
    public void toFileStringNull() throws Exception {
        URI monUriTest = URI.createHierarchicalURI(null, "query", "notnull");
        assertEquals(null, monUriTest.toFileString());
    }
 //Fonction de Test UNIQUEMENT pour un OS Unix
/*    @Test
    public void toFileStringNotNullLinux() throws Exception {
        String[] segments = {"test1", "test2"};
        URI monUriTest = URI.createHierarchicalURI(segments, null, "notnull");
        assertEquals("test1/test2", monUriTest.toFileString());
    }
*/
/*Fonction de test UNIQUEMENT pour un OS Windows
    @Test
    public void toFileStringNotNullWindows() throws Exception {
        String[] segments = {"test1", "test2"};
        URI monUriTest = URI.createHierarchicalURI(segments, null, "notnull");
        assertEquals("test1\\test2", monUriTest.toFileString());
    }
*/

    ////////////////////////////////////////////////////////////////
    ////////////TEST toPlatformString //////////////////////////////
    ////////////////////////////////////////////////////////////////
    @Test
    public void toPlatformStringDecodeTrueNull() throws Exception {
        URI monUriTest = URI.createURI("platform:/test");
        assertEquals(null, monUriTest.toPlatformString(true));
    }


    @Test
    public void toPlatformStringDecodeTrueNotNull() throws Exception {
        URI monUriTest = URI.createURI("platform:/test/path%23");
        assertEquals("/path#", monUriTest.toPlatformString(true));
    }

    @Test
    public void toPlatformStringDecodeFalseNotNull() throws Exception {
        URI monUriTest = URI.createURI("platform:/test/path%23");
        assertEquals("/path%23", monUriTest.toPlatformString(false));
    }

    ////////////////////////////////////////////////////////////////
    ////////////TEST isRelative ////////////////////////////////////
    ////////////////////////////////////////////////////////////////
    @Test
    public void testIsRelative1() {
        String value = "foo://truc/bidule/machin";
        URI uri = URI.createURI(value);
        assertFalse(uri.isRelative());
    }

    @Test
    public void testIsRelative2() {
        String value = "truc/bidule/machin";
        URI uri = URI.createURI(value);
        assertTrue(uri.isRelative());
    }

    ////////////////////////////////////////////////////////////////
    ////////////TEST appendSegment /////////////////////////////////
    ////////////////////////////////////////////////////////////////
    @Test(expected = IllegalArgumentException.class)
    public void appendSegmentException() throws Exception {
        URI monUriTest = URI.createURI("jarr://test!/test:/test/test/bar?query#notnull");
        monUriTest = monUriTest.appendSegment("segment/");
    }

    @Test
    public void appendSegmentEquals() throws Exception {
        String scheme = "test";
        String authority = "test!";
        String device = "test:";
        String[] segments = { "foo", "bar" };
        String query = "query";
        String fragment = "notNull";
        URI monUriTest = URI.createHierarchicalURI(scheme, authority, device,
                segments, query, fragment);
        monUriTest = monUriTest.appendSegment("segment");
        String[] s = { "foo", "bar", "segment" };
        assertArrayEquals(s, monUriTest.segments());
    }

    ////////////////////////////////////////////////////////////////
    ////////////TEST appendSegments ////////////////////////////////
    ////////////////////////////////////////////////////////////////
    @Test(expected = IllegalArgumentException.class)
    public void appendSegments() throws Exception {
        String[] s = { "segment1", "segment2", "segment/" };
        URI monUriTest = URI.createURI("jarr://test!/test:/test/test/bar?query#notnull");
        monUriTest = monUriTest.appendSegments(s);
    }

    @Test
    public void appendSegmentsEquals() throws Exception {
        String scheme = "test";
        String authority = "test!";
        String device = "test:";
        String[] segments = { "foo", "bar" };
        String query = "query";
        String fragment = "notNull";
        String[] new_s = { "segment1", "segment2" };
        URI monUriTest = URI.createHierarchicalURI(scheme, authority, device,
                segments, query, fragment);
        monUriTest = monUriTest.appendSegments(new_s);
        String[] s = { "foo", "bar", "segment1", "segment2" };
        assertArrayEquals(s, monUriTest.segments());
    }

    /////////////////////////////////////////////////////////////////
    ////////////TEST trimSegments(int)////////////////////////
    /////////////////////////////////////////////////////////////////
    @Test
    public void trimSegmentsWithNegativeInt() throws Exception {
        String[] segments = { "unsegment", "deuxsegment","troissegment"};
        String query = "unequery";
        String fragment = "unfragment";
        URI uri = URI.createHierarchicalURI(segments, query, fragment);
        URI uriTrim = uri.trimSegments(-1);
        assertEquals(uri.toString(),uriTrim.toString());
    }

    @Test
    public void trimSegmentsWith0Int() throws Exception {
        String[] segments = { "unsegment", "deuxsegment","troissegment"};
        String query = "unequery";
        String fragment = "unfragment";
        URI uri = URI.createHierarchicalURI(segments, query, fragment);
        URI uriTrim = uri.trimSegments(0);
        assertEquals(uri.toString(),uriTrim.toString());
    }

    @Test
    public void trimSegmentsWithIntInferieurCountSegment() throws Exception {
        String[] segments = { "unsegment", "deuxsegment","troissegment","quatresegment"};
        String[] segments2 = { "unsegment", "deuxsegment"};
        String query = "unequery";
        String fragment = "unfragment";
        URI uri = URI.createHierarchicalURI(segments, query, fragment);
        uri = uri.trimSegments(2);

        URI uri2 = URI.createHierarchicalURI(segments2, query, fragment);
        assertEquals(uri2.toString(),uri.toString());
    }

    @Test
    public void trimSegmentsWithIntSuperieurCountSegment() throws Exception {
        String[] segments = { "unsegment", "deuxsegment","troissegment","quatresegment"};
        String[] segments2 = { };
        String query = "unequery";
        String fragment = "unfragment";
        URI uri = URI.createHierarchicalURI(segments, query, fragment);
        uri = uri.trimSegments(5);

        URI uri2 = URI.createHierarchicalURI(segments2, query, fragment);
        assertEquals(uri2.toString(),uri.toString());
    }
    @Test
    public void trimSegmentsWithEqualsCountSegment() throws Exception {
        String[] segments = { "unsegment",  "deuxsegment","troissegment","quatresegment"};
        String[] segments2 = {  };
        String query = "unequery";
        String fragment = "unfragment";
        URI uri = URI.createHierarchicalURI(segments, query, fragment);
        uri = uri.trimSegments(4);

        URI uri2 = URI.createHierarchicalURI(segments2, query, fragment);
        assertEquals(uri2.toString(),uri.toString());
    }

    @Test
    public void trimSegmentsWithPathSeparator() throws Exception {
        String[] segments = { "unsegment", "", "deuxsegment","troissegment","quatresegment"};
        String[] segments2 = { "unsegment", "", "deuxsegment" };
        String query = "unequery";
        String fragment = "unfragment";
        URI uri = URI.createHierarchicalURI(segments, query, fragment);
        uri = uri.trimSegments(2);

        URI uri2 = URI.createHierarchicalURI(segments2, query, fragment);
        assertEquals(uri2.toString(),uri.toString());
    }
    /////////////////////////////////////////////////////////////////
    ////////////TEST HasTrailingPathSeparator()////////////////////////
    /////////////////////////////////////////////////////////////////
    @Test
    public void HasTrailingPathSeparatorWhithEmptySegment1() {
        String[] segments = { "unsegment", "deuxsegment","" ,"troissegment"};
        String query = "unequery";
        String fragment = "unfragment";
        URI uri = URI.createHierarchicalURI(segments, query, fragment);
        assertFalse(uri.hasTrailingPathSeparator());
    }

    @Test
    public void HasTrailingPathSeparatorWhithEmptySegment2() {
        String[] segments = { "unsegment", "deuxsegment",""};
        String query = "unequery";
        String fragment = "unfragment";
        URI uri = URI.createHierarchicalURI(segments, query, fragment);
        assertTrue(uri.hasTrailingPathSeparator());
    }

    @Test
    public void HasTrailingPathSeparatorWithoutEmptySegment() {
        String[] segments = { "unsegment", "deuxsegment"};
        String query = "unequery";
        String fragment = "unfragment";
        URI uri = URI.createHierarchicalURI(segments, query, fragment);
        assertFalse(uri.hasTrailingPathSeparator());
    }

    @Test
    public void HasTrailingPathSeparatorWithoutHierachicalURI() {
        String opaquePart = "opaque";
        String scheme = "unscheme";
        String fragment = "unfragment";
        URI uri = URI.createGenericURI(scheme, opaquePart, fragment);
        assertFalse(uri.hasTrailingPathSeparator());
    }

    /////////////////////////////////////////////////////////////////
    ////////////TEST fileExtension()////////////////////////
    /////////////////////////////////////////////////////////////////
    @Test
    public void fileExtensionWithHierachicalURIAndExtension() throws Exception {
        String authority = "authority!";
        String scheme = "unscheme";
        String device = "device:";
        String[] segments = { "alice", "bob.txt" };
        String query = "unequery";
        String fragment = "unfragment";
        URI uri = URI.createHierarchicalURI(scheme, authority, device,
                segments, query, fragment);
        assertEquals("txt",uri.fileExtension());
    }
    @Test
    public void fileExtensionWithHierachicalURIAndExtensionOnlyPoint() throws Exception {
        String authority = "authority!";
        String scheme = "unscheme";
        String device = "device:";
        String[] segments = { "alice", "bob." };
        String query = "unequery";
        String fragment = "unfragment";
        URI uri = URI.createHierarchicalURI(scheme, authority, device,
                segments, query, fragment);
        assertEquals("",uri.fileExtension());
    }
    @Test
    public void fileExtensionWithHierachicalURIWithoutExtension() throws Exception {
        String authority = "authority!";
        String scheme = "unscheme";
        String device = "device:";
        String[] segments = { "alice", "" };
        String query = "unequery";
        String fragment = "unfragment";
        URI uri = URI.createHierarchicalURI(scheme, authority, device,
                segments, query, fragment);
        assertNull(uri.fileExtension());
    }

    @Test
    public void fileExtensionWithGenericURIWithAllExtension() throws Exception {
        String opaquePart = "opaque.css";
        String scheme = "unscheme.html";
        String fragment = "unfragment.txt";
        URI uri = URI.createGenericURI(scheme, opaquePart, fragment);
        assertNull(uri.fileExtension());
    }
    /////////////////////////////////////////////////////////////////
    ////////////TEST appendFileExtension(String)////////////////////////
    /////////////////////////////////////////////////////////////////

    @Test
    public void AppendFileExtensionWithHierachicalURIAndExtension() {
        String authority = "authority!";
        String scheme = "unscheme";
        String device = "device:";
        String[] segments = { "alice", "bob.txt" };
        String query = "unequery";
        String fragment = "unfragment";
        URI uri = URI.createHierarchicalURI(scheme, authority, device,
                segments, query, fragment);
        uri = uri.appendFileExtension("html");
        assertEquals("html", uri.fileExtension());
    }
    @Test
    public void AppendFileExtensionWithHierachicalURIWithoutExtension() {
        String authority = "authority!";
        String scheme = "unscheme";
        String device = "device:";
        String[] segments = { "alice", "bob" };
        String query = "unequery";
        String fragment = "unfragment";
        URI uri = URI.createHierarchicalURI(scheme, authority, device,
                segments, query, fragment);
        uri = uri.appendFileExtension("html");
        assertEquals("html", uri.fileExtension());
    }

    @Test
    public void AppendFileExtensionWithoutExtention() {
        String opaquePart = "opaque";
        String scheme = "unscheme";
        String fragment = "unfragment";
        URI uri = URI.createGenericURI(scheme, opaquePart, fragment);
        URI new_uri = uri.appendFileExtension("html");
        assertEquals(new_uri.toString(), uri.toString());
    }

    /////////////////////////////////////////////////////////////////
    ////////////TEST trimFileExtension()////////////////////////
    /////////////////////////////////////////////////////////////////
    @Test
    public void trimFileExtensionWithfichierComplet() throws Exception {
        URI uri = URI.createURI("alice/bob/monfichier.ex");
        uri = uri.trimFileExtension();

        assertEquals("alice/bob/monfichier",uri.toString());
    }
    @Test
    public void trimFileExtensionWhithFichierOnlyPoint() throws Exception {
        URI uri = URI.createURI("alice/bob/monfichier.");
        uri = uri.trimFileExtension();

        assertEquals("alice/bob/monfichier",uri.toString());
    }
    @Test
    public void trimFileExtensionWhithFichierWhithoutType() throws Exception {
        URI uri = URI.createURI("alice/bob/monfichier");
        uri = uri.trimFileExtension();

        assertEquals("alice/bob/monfichier",uri.toString());
    }
    @Test
    public void trimFileExtensionWhithoutFichier() throws Exception {
        URI uri = URI.createURI("alice.com/bob/");
        uri = uri.trimFileExtension();

        assertEquals("alice.com/bob/",uri.toString());
    }

    /////////////////////////////////////////////////////////////////
    ////////////TEST isPrefix()////////////////////////
    /////////////////////////////////////////////////////////////////

    @Test
    public void IsPrefixTrueNoFragmentNoQuery() {
        String[] segments = { "alice", "bob", "" };
        String query = null;
        String fragment = null;
        URI uri = URI.createHierarchicalURI(segments, query, fragment);
        assertTrue(uri.isPrefix());
    }

    @Test
    public void IsPrefixFalseWithFragment() {
        String[] segments = { "alice", "bob", "" };
        String query = null;
        String fragment = "unfragment";
        URI uri = URI.createHierarchicalURI(segments, query, fragment);
        assertFalse(uri.isPrefix());
    }

    @Test
    public void IsPrefixFalseWithQuery() {
        String[] segments = { "alice", "bob", "" };
        String query = "unequery";
        String fragment = null;
        URI uri = URI.createHierarchicalURI(segments, query, fragment);
        assertFalse(uri.isPrefix());
    }



    @Test
    public void IsPrefixFalseWithQueryAndFragment() {
        String[] segments = { "alice", "bob" };
        String query = "unequery";
        String fragment = "unfragment";
        URI uri = URI.createHierarchicalURI(segments, query, fragment);
        assertFalse(uri.isPrefix());
    }

    /////////////////////////////////////////////////////////////////
    ////////////TEST replacePrefix(URI,URI)////////////////////////
    /////////////////////////////////////////////////////////////////

    @Test(expected = IllegalArgumentException.class)
    public void ReplacePrefixException() {
        String[] segments = { "alice", "bob", "" };
        String[] new_segments = { "bob", "bob", "" };
        String query = "unequery";
        String fragment = "unfragment";
        String old_query = "unequery";
        String old_fragment = null;
        String new_query = null;
        String new_fragment = null;
        URI uri = URI.createHierarchicalURI(segments, query, fragment);
        URI old_prefix = URI.createHierarchicalURI(segments, old_query,
                old_fragment);
        URI new_prefix = URI.createHierarchicalURI(new_segments, new_query,
                new_fragment);
        uri.replacePrefix(old_prefix, new_prefix);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ReplacePrefixException2() {
        String[] segments = { "alice", "bob", "" };
        String[] new_segments = { "bob", "bob", "" };
        String query = "unequery";
        String fragment = "unfragment";
        String old_query = null;
        String old_fragment = null;
        String new_query = null;
        String new_fragment = "unfragment";
        URI uri = URI.createHierarchicalURI(segments, query, fragment);
        URI old_prefix = URI.createHierarchicalURI(segments, old_query,
                old_fragment);
        URI new_prefix = URI.createHierarchicalURI(new_segments, new_query,
                new_fragment);
        uri.replacePrefix(old_prefix, new_prefix);
    }

    @Test
    public void ReplacePrefixEqualsNull() {
        String[] segments = { "alice", "bob", "" };
        String[] new_segments = { "bob", "bob", "" };
        String query = "unequery";
        String fragment = "unfragment";
        String old_query = null;
        String old_fragment = null;
        String new_query = null;
        String new_fragment = null;
        URI uri = URI.createHierarchicalURI(segments, query, fragment);
        URI old_prefix = URI.createHierarchicalURI(new_segments, old_query,
                old_fragment);
        URI new_prefix = URI.createHierarchicalURI(new_segments, new_query,
                new_fragment);

        assertNull(uri.replacePrefix(old_prefix, new_prefix));
    }

    @Test
    public void ReplacePrefixEquals() {
        String[] segments = { "alice", "bob", "" };
        String[] new_segments = { "alice", "bob", "" };
        String query = "unequery";
        String fragment = "unfragment";
        String old_query = null;
        String old_fragment = null;
        String new_query = null;
        String new_fragment = null;
        URI uri = URI.createHierarchicalURI(segments, query, fragment);
        URI old_prefix = URI.createHierarchicalURI(segments, old_query,
                old_fragment);
        URI new_prefix = URI.createHierarchicalURI(new_segments, new_query,
                new_fragment);

        assertEquals("alice/bob/#unfragment",
                uri.replacePrefix(old_prefix, new_prefix).toString());
    }
    /////////////////////////////////////////////////////////////////
    ////////////TEST encodeOpaquePart(string,bool)////////////////////////
    /////////////////////////////////////////////////////////////////
    @Test
    public void encodeOpaquePartIgnoreTrueAndNotEscapedHex() {
        assertEquals("abc%7056;",URI.encodeOpaquePart("abc%7056;",true));
    }
    @Test
    public void encodeOpaquePartIgnoreTrueAndEscapedHex() {
        assertEquals("abc%25gh56",URI.encodeOpaquePart("abc%gh56",true));
    }
    @Test
    public void encodeOpaquePartIgnoreTrueAndEscapedChar() {
        assertEquals("abc%2056%2388?ty-",URI.encodeOpaquePart("abc 56#88?ty-",true));
    }
    @Test
    public void encodeOpaquePartIgnoreTrueAndNotEscapedChar() {
        assertEquals("abc?56-",URI.encodeOpaquePart("abc?56-",true));
    }
    @Test
    public void encodeOpaquePartIgnoreFalseAndNotEscapedHex() {
        assertEquals("abc%257056",URI.encodeOpaquePart("abc%7056",false));
    }
    @Test
    public void encodeOpaquePartIgnoreFalseAndEscapedHex() {
        assertEquals("abc%25gh56",URI.encodeOpaquePart("abc%gh56",false));
    }
    @Test
    public void encodeOpaquePartIgnoreFalseAndEscapedChar() {
        assertEquals("abc?56-",URI.encodeOpaquePart("abc?56-",false));
    }
    @Test
    public void encodeOpaquePartIgnoreFalseAndNotEscapedChar() {
        assertEquals("abc%2056%23",URI.encodeOpaquePart("abc 56#",false));
    }
    /////////////////////////////////////////////////////////////////
    ////////////TEST encodeAuthority(string,bool)////////////////////////
    /////////////////////////////////////////////////////////////////
    @Test
    public void encodeAuthorityIgnoreTrueAndNotEscapedHex() {
        assertEquals("abc%7056;",URI.encodeAuthority("abc%7056;",true));
    }
    @Test
    public void encodeAuthorityIgnoreTrueAndEscapedHex() {
        assertEquals("abc%25gh56",URI.encodeAuthority("abc%gh56",true));
    }
    @Test
    public void encodeAuthorityIgnoreTrueAndEscapedChar() {
        assertEquals("abc%2056%2388%3Fty-",URI.encodeAuthority("abc 56#88?ty-",true));
    }
    @Test
    public void encodeAuthorityIgnoreTrueAndNotEscapedChar() {
        assertEquals("abc%3F56-",URI.encodeAuthority("abc?56-",true));
    }
    @Test
    public void encodeAuthorityIgnoreFalseAndNotEscapedHex() {
        assertEquals("abc%257056",URI.encodeAuthority("abc%7056",false));
    }
    @Test
    public void encodeAuthorityIgnoreFalseAndEscapedHex() {
        assertEquals("abc%25gh56",URI.encodeAuthority("abc%gh56",false));
    }
    @Test
    public void encodeAuthorityIgnoreFalseAndEscapedChar() {
        assertEquals("abc%3F56-",URI.encodeAuthority("abc?56-",false));
    }
    @Test
    public void encodeAuthorityIgnoreFalseAndNotEscapedChar() {
        assertEquals("abc%2056%23",URI.encodeAuthority("abc 56#",false));
    }
    /////////////////////////////////////////////////////////////////
    ////////////TEST encodeSegment(string,bool)////////////////////////
    /////////////////////////////////////////////////////////////////

    @Test
    public void encodeSegmentIgnoreTrueAndNotEscapedHex() {
        assertEquals("abc%7056;",URI.encodeSegment("abc%7056;",true));
    }
    @Test
    public void encodeSegmentIgnoreTrueAndEscapedHex() {
        assertEquals("abc%25gh56",URI.encodeSegment("abc%gh56",true));
    }
    @Test
    public void encodeSegmentIgnoreTrueAndEscapedChar() {
        assertEquals("abc%2056%2388%3Fty-",URI.encodeSegment("abc 56#88?ty-",true));
    }
    @Test
    public void encodeSegmentIgnoreTrueAndNotEscapedChar() {
        assertEquals("abc%3F56-",URI.encodeSegment("abc?56-",true));
    }
    @Test
    public void encodeSegmentIgnoreFalseAndNotEscapedHex() {
        assertEquals("abc%257056",URI.encodeSegment("abc%7056",false));
    }
    @Test
    public void encodeSegmentIgnoreFalseAndEscapedHex() {
        assertEquals("abc%25gh56",URI.encodeSegment("abc%gh56",false));
    }
    @Test
    public void encodeSegmentIgnoreFalseAndEscapedChar() {
        assertEquals("abc%3F56-",URI.encodeSegment("abc?56-",false));
    }
    @Test
    public void encodeSegmentIgnoreFalseAndNotEscapedChar() {
        assertEquals("abc%2056%23",URI.encodeSegment("abc 56#",false));
    }

    /////////////////////////////////////////////////////////////////
    ////////////TEST encodeQuery(string,bool)////////////////////////
    /////////////////////////////////////////////////////////////////
    @Test
    public void encodeQueryIgnoreTrueAndNotEscapedHex() {
        assertEquals("abc%7056;",URI.encodeQuery("abc%7056;",true));
    }
    @Test
    public void encodeQueryIgnoreTrueAndEscapedHex() {

        assertEquals("abc%25gh56",URI.encodeQuery("abc%gh56",true));
    }
    @Test
    public void encodeQueryIgnoreTrueAndEscapedChar() {
        assertEquals("abc%2056%23",URI.encodeQuery("abc 56#",true));
    }
    @Test
    public void encodeQueryIgnoreTrueAndNotEscapedChar() {

        assertEquals("abc?56-",URI.encodeQuery("abc?56-",true));
    }
    @Test
    public void encodeQueryIgnoreFalseAndNotEscapedHex() {
        assertEquals("abc%257056",URI.encodeQuery("abc%7056",false));
    }
    @Test
    public void encodeQueryIgnoreFalseAndEscapedHex() {
        assertEquals("abc%25gh56",URI.encodeQuery("abc%gh56",false));
    }
    @Test
    public void encodeQueryIgnoreFalseAndEscapedChar() {
        assertEquals("abc?56-",URI.encodeQuery("abc?56-",false));
    }
    @Test
    public void encodeQueryIgnoreFalseAndNotEscapedChar() {
        assertEquals("abc%2056%23",URI.encodeQuery("abc 56#",false));
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