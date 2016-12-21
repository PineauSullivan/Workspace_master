package org.eclipse.emf.common.util;

/**
 * Created by Sebastien and Sullivan
 */
public class URI_CONST {
    /**
     * When specified as the last argument to , indicates that there is no fragment, so any <code>#</code> characters
     * should be encoded.
     */
    public static final int FRAGMENT_NONE = 0;
    /**
     * When specified as the last argument to , indicates that the first <code>#</code> character should be taken as
     * the fragment separator, and any others should be encoded.
     */
    public static final int FRAGMENT_FIRST_SEPARATOR = 1;
    /**
     * When specified as the last argument to , indicates that the last <code>#</code> character should be taken as
     * the fragment separator, and any others should be encoded.
     */
    public static final int FRAGMENT_LAST_SEPARATOR = 2;
    protected static final boolean DEBUG = false;
    /**
     * A pool for managing {@link URI} instances.
     */
    protected static final URIPool POOL = new URIPool(CommonUtil.REFERENCE_CLEARING_QUEUE);
    // The lower-cased schemes that will be used to identify archive URIs.
    protected static String[] ARCHIVE_SCHEMES;
    // Identifies a file-type absolute URI.
    protected static final String SCHEME_FILE = "file";
    protected static final int SCHEME_FILE_HASH_CODE = SCHEME_FILE.hashCode();
    protected static final String SCHEME_JAR = "jar";
    protected static final int SCHEME_JAR_HASH_CODE = SCHEME_JAR.hashCode();
    protected static final String SCHEME_ZIP = "zip";
    protected static final int SCHEME_ZIP_HASH_CODE = SCHEME_ZIP.hashCode();
    protected static final String SCHEME_ARCHIVE = "archive";
    protected static final int SCHEME_ARCHIVE_HASH_CODE = SCHEME_ARCHIVE.hashCode();
    protected static final String SCHEME_PLATFORM = "platform";
    protected static final int SCHEME_PLATFORM_HASH_CODE = SCHEME_PLATFORM.hashCode();
    protected static final String SCHEME_HTTP = "http";
    protected static final int SCHEME_HTTP_HASH_CODE = SCHEME_HTTP.hashCode();
    // Special segment values interpreted at resolve and resolve time.
    protected static final String SEGMENT_EMPTY = "";
    protected static final String SEGMENT_SELF = ".";
    protected static final String[] ONE_SELF_SEGMENT = SegmentSequence.STRING_ARRAY_POOL.intern(SEGMENT_SELF, false);
    protected static final String SEGMENT_PARENT = "..";
    // Special segments used for platform URIs.
    protected static final String SEGMENT_PLUGIN = "plugin";
    protected static final String SEGMENT_RESOURCE = "resource";
    // Special arrays uses for segments
    protected static final String[] NO_SEGMENTS = SegmentSequence.EMPTY_ARRAY;
    protected static final String[] ONE_EMPTY_SEGMENT = SegmentSequence.EMPTY_STRING_ARRAY;
    // Separators for parsing a URI string.
    protected static final char SCHEME_SEPARATOR = ':';
    protected static final String AUTHORITY_SEPARATOR = "//";
    protected static final int AUTHORITY_SEPARATOR_HASH_CODE = AUTHORITY_SEPARATOR.hashCode();
    protected static final char DEVICE_IDENTIFIER = ':';
    protected static final char SEGMENT_SEPARATOR = '/';
    protected static final char QUERY_SEPARATOR = '?';
    protected static final char FRAGMENT_SEPARATOR = '#';
    protected static final char USER_INFO_SEPARATOR = '@';
    protected static final char PORT_SEPARATOR = ':';
    protected static final char FILE_EXTENSION_SEPARATOR = '.';
    protected static final char ARCHIVE_IDENTIFIER = '!';
    protected static final String ARCHIVE_SEPARATOR = "!/";
    // Characters to use in escaping.
    protected static final char ESCAPE = '%';
    protected static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    // Some character classes, as defined in RFC 2396's BNF for URI.
    // These are 128-bit bitmasks, stored as two longs, where the Nth bit is set
    // iff the ASCII character with value N is included in the set.  These are
    // created with the highBitmask() and lowBitmask() methods defined below,
    // and a character is tested against them using matches().
    //
    protected static final long ALPHA_HI = URI.highBitmask('a', 'z') | URI.highBitmask('A', 'Z');
    protected static final long ALPHA_LO = URI.lowBitmask('a', 'z') | URI.lowBitmask('A', 'Z');
    protected static final long DIGIT_HI = URI.highBitmask('0', '9');
    protected static final long HEX_HI = DIGIT_HI | URI.highBitmask('A', 'F') | URI.highBitmask('a', 'f');
    protected static final long ALPHANUM_HI = ALPHA_HI | DIGIT_HI;
    protected static final long UNRESERVED_HI = ALPHANUM_HI | URI.highBitmask("-_.!~*'()");
    // Additional useful character classes, including characters valid in certain
    // URI components and separators used in parsing them out of a string.
    //
    protected static final long SEGMENT_CHAR_HI = UNRESERVED_HI | URI.highBitmask(";:@&=+$,");  // | ucschar | escaped
    protected static final long PATH_CHAR_HI = SEGMENT_CHAR_HI | URI.highBitmask('/');  // | ucschar | escaped
    protected static final long DIGIT_LO = URI.lowBitmask('0', '9');
    protected static final long HEX_LO = DIGIT_LO | URI.lowBitmask('A', 'F') | URI.lowBitmask('a', 'f');
    protected static final long ALPHANUM_LO = ALPHA_LO | DIGIT_LO;
    protected static final long UNRESERVED_LO = ALPHANUM_LO | URI.lowBitmask("-_.!~*'()");
    protected static final long SEGMENT_CHAR_LO = UNRESERVED_LO | URI.lowBitmask(";:@&=+$,");
    protected static final long PATH_CHAR_LO = SEGMENT_CHAR_LO | URI.lowBitmask('/');
    protected static final long RESERVED_HI = URI.highBitmask(";/?:@&=+$,");
    protected static final long URIC_HI = RESERVED_HI | UNRESERVED_HI;  // | ucschar | escaped
    protected static final long RESERVED_LO = URI.lowBitmask(";/?:@&=+$,");
    protected static final long URIC_LO = RESERVED_LO | UNRESERVED_LO;
    protected static final long MAJOR_SEPARATOR_HI = URI.highBitmask(":/?#");
    protected static final long MAJOR_SEPARATOR_LO = URI.lowBitmask(":/?#");
    protected static final long SEGMENT_END_HI = URI.highBitmask("/?#");
    protected static final long SEGMENT_END_LO = URI.lowBitmask("/?#");
    protected static final long PLATFORM_SEGMENT_RESERVED_HI = URI.highBitmask("/?#\\");
    protected static final long PLATFORM_SEGMENT_RESERVED_LO = URI.lowBitmask("/?#\\");
    // The intent of this was to switch over to encoding platform resource URIs
    // by default, but allow people to use a system property to avoid this.
    // However, that caused problems for people and we had to go back to not
    // encoding and introduce yet another factory method that explicitly enables
    // encoding.
    //
    protected static final boolean ENCODE_PLATFORM_RESOURCE_URIS =
            System.getProperty("URI.encodePlatformResourceURIs") != null &&
                    !"false".equalsIgnoreCase(System.getProperty("URI.encodePlatformResourceURIs"));
}
