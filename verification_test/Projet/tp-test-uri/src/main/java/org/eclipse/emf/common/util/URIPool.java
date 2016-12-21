package org.eclipse.emf.common.util;

import java.io.File;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/**
 * A pool for caching URIs.
 */
class URIPool extends Pool<URI> {
    protected static final long serialVersionUID = 1L;

    /**
     * A reference queue for managing the {@link URI#toString} values.
     */
    protected final ReferenceQueue<String> cachedToStrings;

    public URIPool(ReferenceQueue<Object> queue) {
        super(1031, null, queue);

        // The string cache will be managed by either an internal or external cache as appropriate.
        //
        cachedToStrings = externalQueue == null ? new ReferenceQueue<String>() : null;
    }

    /**
     * A based access unit for this pool.
     */
    protected static class URIPoolAccessUnitBase extends AccessUnit<URI> {
        /**
         * A local access unit for exclusive use in {@link #intern(char[], int, int)}.
         */
        protected CommonUtil.StringPool.CharactersAccessUnit charactersAccessUnit = new CommonUtil.StringPool.CharactersAccessUnit(null);

        /**
         * A local access unit for exclusive for normalizing the scheme in {@link #intern(String)}, {@link #intern(boolean, String)}, and {@link StringAccessUnit#parseIntoURI(String)}.
         */
        protected CommonUtil.StringPool.StringAccessUnit stringAccessUnit = new CommonUtil.StringPool.StringAccessUnit(CommonUtil.STRING_POOL, null);

        /**
         * The string pool entry found during the most recent use of {@link #substringAccessUnit}.
         */
        protected CommonUtil.StringPool.StringPoolEntry stringPoolEntry;

        /**
         * A local access unit for exclusive use in {@link #intern(String, int, int)} and {@link #intern(String, int, int, int)}.
         * It {@link #stringPoolEntry} the string pool entry that was matched when {@link CommonUtil.StringPool.SubstringAccessUnit#reset(boolean)} is called.
         */
        protected CommonUtil.StringPool.SubstringAccessUnit substringAccessUnit =
                new CommonUtil.StringPool.SubstringAccessUnit(null) {
                    @Override
                    public void reset(boolean isExclusive) {
                        stringPoolEntry = (CommonUtil.StringPool.StringPoolEntry) getEntry();
                        super.reset(isExclusive);
                    }
                };

        /**
         * An access unit for exclusive use in {@link #internArray(String[], int, int, int)}.
         */
        protected SegmentSequence.StringArrayPool.SegmentsAndSegmentCountAccessUnit stringArraySegmentsAndSegmentCountAccessUnit = new SegmentSequence.StringArrayPool.SegmentsAndSegmentCountAccessUnit(null);

        protected URIPoolAccessUnitBase(Queue<URI> queue) {
            super(queue);
        }

        @Override
        protected URI getValue() {
            throw new UnsupportedOperationException();
        }

        @Override
        protected void setValue(URI value) {
            throw new UnsupportedOperationException();
        }

        @Override
        protected boolean setArbitraryValue(Object value) {
            throw new UnsupportedOperationException();
        }

        protected String intern(String string) {
            stringAccessUnit.setValue(string);
            return CommonUtil.STRING_POOL.doIntern(false, stringAccessUnit);
        }

        protected String intern(boolean toLowerCase, String string) {
            stringAccessUnit.setValue(toLowerCase, string);
            return CommonUtil.STRING_POOL.doIntern(false, stringAccessUnit);
        }

        protected String intern(String string, int offset, int count, int hashCode) {
            substringAccessUnit.setValue(string, offset, count, hashCode);
            return CommonUtil.STRING_POOL.doIntern(false, substringAccessUnit);
        }

        protected String intern(String string, int offset, int count) {
            substringAccessUnit.setValue(string, offset, count);
            return CommonUtil.STRING_POOL.doIntern(false, substringAccessUnit);
        }

        protected String intern(char[] characters, int offset, int count) {
            charactersAccessUnit.setValue(characters, offset, count);
            return CommonUtil.STRING_POOL.doIntern(false, charactersAccessUnit);
        }

        protected String intern(char[] characters, int offset, int count, int hashCode) {
            charactersAccessUnit.setValue(characters, offset, count, hashCode);
            return CommonUtil.STRING_POOL.doIntern(false, charactersAccessUnit);
        }

        protected String[] internArray(String[] segments, int offset, int segmentCount, int hashCode) {
            stringArraySegmentsAndSegmentCountAccessUnit.setValue(segments, offset, segmentCount, hashCode);
            return SegmentSequence.STRING_ARRAY_POOL.doIntern(false, stringArraySegmentsAndSegmentCountAccessUnit);
        }

        @Override
        public void reset(boolean isExclusive) {
            stringPoolEntry = null;
            super.reset(isExclusive);
        }
    }

    /**
     * Access units for basic string access.
     */
    protected final StringAccessUnit.Queue stringAccessUnits = new StringAccessUnit.Queue(this);

    /**
     * An access unit for basic string access.
     */
    protected static class StringAccessUnit extends URIPoolAccessUnitBase {
        protected static class Queue extends AccessUnit.Queue<URI> {
            private static final long serialVersionUID = 1L;

            final protected URIPool pool;

            public Queue(URIPool pool) {
                this.pool = pool;
            }

            @Override
            public StringAccessUnit pop(boolean isExclusive) {
                return (StringAccessUnit) super.pop(isExclusive);
            }

            @Override
            protected AccessUnit<URI> newAccessUnit() {
                return new StringAccessUnit(this, pool);
            }
        }

        /**
         * This unit's pool.
         */
        protected final URIPool pool;

        /**
         * The value being accessed.
         */
        protected String value;

        /**
         * The cached hash code computed by {@link #findMajorSeparator(int, String, int)} and {@link #findSegmentEnd(int, String, int)}.
         */
        protected int findHashCode;

        /**
         * The cached terminating character computed by {@link #findMajorSeparator(int, String, int)} and {@link #findSegmentEnd(int, String, int)}.
         */
        protected char findTerminatingCharacter;

        /**
         * An access unit for exclusive use in {@link #internArray(String, int, int, int)}.
         */
        protected SegmentSequence.StringArrayPool.SubstringAccessUnit stringArraySubstringAccessUnit = new SegmentSequence.StringArrayPool.SubstringAccessUnit(null);

        /**
         * An access unit for exclusive use in {@link #internArray(int, String[], int, String, int, int, int)}.
         */
        protected SegmentSequence.StringArrayPool.SegmentsAndSubsegmentAccessUnit stringArraySegmentsAndSubsegmentAccessUnit = new SegmentSequence.StringArrayPool.SegmentsAndSubsegmentAccessUnit(null);

        protected String[] internArray(String segment, int offset, int count, int hashCode) {
            stringArraySubstringAccessUnit.setValue(segment, offset, count, hashCode);
            return SegmentSequence.STRING_ARRAY_POOL.doIntern(false, stringArraySubstringAccessUnit);
        }

        protected String[] internArray(int hashCode, String[] segments, int segmentCount, String segment, int offset, int count, int segmentHashCode) {
            if (segmentCount == 0) {
                return internArray(segment, offset, count, segmentHashCode);
            } else {
                stringArraySegmentsAndSubsegmentAccessUnit.setValue(hashCode, segments, segmentCount, segment, offset, count, segmentHashCode);
                return SegmentSequence.STRING_ARRAY_POOL.doIntern(false, stringArraySegmentsAndSubsegmentAccessUnit);
            }
        }

        /**
         * Creates an instance managed by this queue and pool.
         */
        protected StringAccessUnit(StringAccessUnit.Queue queue, URIPool pool) {
            super(queue);
            this.pool = pool;
        }

        /**
         * Caches the parameters.
         */
        protected void setValue(String value) {
            this.value = value;
            this.hashCode = value.hashCode();
        }

        /**
         * Caches the parameters.
         */
        protected void setValue(String value, int hashCode) {
            this.value = value;
            this.hashCode = hashCode;
        }

        @Override
        protected boolean matches(URI value) {
            return value.matches(this.value);
        }

        @Override
        public URI match() {
            // If we fail to match, use getInternalizedValue to parse and cache an instance.
            //
            URI result = super.match();
            return result == null ? getInternalizedValue() : result;
        }

        @Override
        public URI getInternalizedValue() {
            return parseIntoURI(value);
        }

        /**
         * A string-parsing implementation.
         * This method creates instances in the pool as a side-effect.
         * Note that we never pass in a string with a fragment separator to this method.
         */
        protected URI parseIntoURI(String uri) {
            // The initial values for what we'll compute.
            //
            boolean hasExpectedHashCode = true;
            boolean isSchemeNormal = true;
            String scheme = null;
            String authority = null;
            String device = null;
            boolean absolutePath = false;
            String[] segments = URI_CONST.NO_SEGMENTS;
            int segmentsHashCode = 1;
            String query = null;
            boolean isArchiveScheme = false;
            boolean isPlatformScheme = false;

            // Look for the major separator, i.e., one of ":/?"
            //
            int length = uri.length();
            int i = 0;
            int j = findMajorSeparator(length, uri, i);

            // If we've found the scheme separator...
            //
            if (findTerminatingCharacter == URI_CONST.SCHEME_SEPARATOR) {
                // Look if the scheme's hash code matches one of the most likely schemes we expect to find...
                //
                int findHashCode = this.findHashCode;
                if (findHashCode == URI_CONST.SCHEME_PLATFORM_HASH_CODE) {
                    scheme = URI_CONST.SCHEME_PLATFORM;
                    isPlatformScheme = true;
                } else if (findHashCode == URI_CONST.SCHEME_FILE_HASH_CODE) {
                    scheme = URI_CONST.SCHEME_FILE;
                } else if (findHashCode == URI_CONST.SCHEME_HTTP_HASH_CODE) {
                    scheme = URI_CONST.SCHEME_HTTP;
                } else if (findHashCode == URI_CONST.SCHEME_JAR_HASH_CODE) {
                    scheme = URI_CONST.SCHEME_JAR;
                    isArchiveScheme = true;
                } else if (findHashCode == URI_CONST.SCHEME_ARCHIVE_HASH_CODE) {
                    scheme = URI_CONST.SCHEME_ARCHIVE;
                    isArchiveScheme = true;
                } else if (findHashCode == URI_CONST.SCHEME_ZIP_HASH_CODE) {
                    scheme = URI_CONST.SCHEME_ZIP;
                    isArchiveScheme = true;
                }

                // If it isn't one of the expected schemes, or it is, then we need to make sure it's really equal to what's in the URI, not an accidential hash code collision...
                //
                if (scheme == null || !scheme.regionMatches(0, uri, 0, j)) {
                    // Intern the provided version of the scheme.
                    //
                    String unnormalizedScheme = intern(uri, 0, j, findHashCode);

                    // Intern the lower case version of the scheme.
                    //
                    stringAccessUnit.setValue(true, unnormalizedScheme);
                    stringAccessUnit.add(unnormalizedScheme, stringPoolEntry);
                    scheme = stringAccessUnit.match();
                    stringAccessUnit.reset(false);

                    // Determine if the provided version is in normal form, i.e., already lower cased.
                    //
                    isSchemeNormal = unnormalizedScheme == scheme;

                    // Check whether it's a different hash code; we'll need to compute the right hash code if we've lower cased the scheme.
                    //
                    hasExpectedHashCode = scheme.hashCode() == findHashCode;

                    // Check if it's an archive scheme...
                    //
                    for (String archiveScheme : URI_CONST.ARCHIVE_SCHEMES) {
                        if (scheme == archiveScheme) {
                            isArchiveScheme = true;
                            break;
                        }
                    }

                    isPlatformScheme = scheme == URI_CONST.SCHEME_PLATFORM;
                }

                // Look for the end of the following segment.
                //
                i = j + 1;
                j = findSegmentEnd(length, uri, i);
            }

            if (isArchiveScheme) {
                // Look for the archive separator, which must be present.
                //
                j = uri.lastIndexOf(URI_CONST.ARCHIVE_SEPARATOR);
                if (j == -1) {
                    throw new IllegalArgumentException("no archive separator");
                }

                // In that case it's an absolute path and the authority is everything up to and including the ! of the archive separator.
                //
                absolutePath = true;
                authority = intern(uri, i, ++j - i);

                // Look for the end of the following segment starting after the / in the archive separator.
                //
                i = j + 1;
                j = findSegmentEnd(length, uri, i);
            } else if (i == j && findTerminatingCharacter == URI_CONST.SEGMENT_SEPARATOR) {
                // If we're starting with a / so it's definitely hierarchical.
                // Look for the next segment end, and if we find a / as the next character...
                //
                j = findSegmentEnd(length, uri, ++i);
                if (j == i && findTerminatingCharacter == URI_CONST.SEGMENT_SEPARATOR) {
                    // Look for the segment that follows; it's the authority, even if it's empty.
                    //
                    j = findSegmentEnd(length, uri, ++i);
                    authority = intern(uri, i, j - i, findHashCode);
                    i = j;

                    // If the authority is followed by a /...
                    //
                    if (findTerminatingCharacter == URI_CONST.SEGMENT_SEPARATOR) {
                        // Then it's an absolute path so look for the end of the following segment.
                        //
                        absolutePath = true;
                        j = findSegmentEnd(length, uri, ++i);
                    }
                } else {
                    // Because it started with a /, the current segment, which we'll capcture below, is the start of an absolute path.
                    //
                    absolutePath = true;
                }
            } else if (scheme != null) {
                // There's a scheme, but it's not followed immediately by a /, so it's an opaque URI.
                //
                authority = intern(uri, i, length - i);
                URI resultURI = pool.intern(false, URIComponentsAccessUnit.VALIDATE_NONE, false, scheme, authority, null, false, null, null);

                // If something tries to add an entry for this access unit, we'd better be sure that the hash code is that of the transformed URI.
                //
                this.hashCode = resultURI.hashCode();

                return resultURI;
            }

            // Start analyzing the first segment...
            //
            boolean segmentsRemain = false;
            int start = i;
            int len = j - i;
            i = j;
            if (len == 0) {
                // If we found a /, then we have one single empty segment so far.
                //
                if (findTerminatingCharacter != URI_CONST.QUERY_SEPARATOR) {
                    segments = URI_CONST.ONE_EMPTY_SEGMENT;
                    segmentsHashCode = 31;

                    // Look for the next segment. There is one even if it's empty.
                    //
                    j = findSegmentEnd(length, uri, ++i);
                    segmentsRemain = true;
                }
            }
            // If this first segment ends with a : and we're not processing an archive URI, then treat it as the device...
            //
            else if (!isArchiveScheme && !isPlatformScheme && uri.charAt(j - 1) == URI_CONST.DEVICE_IDENTIFIER) {
                device = intern(uri, start, len, findHashCode);

                // If the device is at the end of the URI...
                //
                if (findTerminatingCharacter == URI_CONST.QUERY_SEPARATOR) {
                    // Then there's no absolute path and no segments remain.
                    //
                    absolutePath = false;
                } else {
                    // Look for the segment that follows.
                    //
                    j = findSegmentEnd(length, uri, ++i);

                    // If it's empty, then we ignore it because the empty segment is implicit from this being an absolute path.
                    // Or, if there is another /, then we have another segment to process.
                    //
                    segmentsRemain = i != j || findTerminatingCharacter == URI_CONST.SEGMENT_SEPARATOR;
                }
            } else {
                // Append the segment...
                //
                segments = internArray(uri, start, j - start, findHashCode);
                segmentsHashCode = 31 * segmentsHashCode + findHashCode;

                // If we're not already at the end...
                //
                if (findTerminatingCharacter != URI_CONST.QUERY_SEPARATOR) {
                    // Find the end of the following segment, and indicate that we should process it.
                    //
                    j = findSegmentEnd(length, uri, ++i);
                    segmentsRemain = true;
                }
            }

            // If we have more segments to process...
            //
            if (segmentsRemain) {
                for (; ; ) {
                    // Append that segment...
                    //
                    segments = internArray(segmentsHashCode, segments, segments.length, uri, i, j - i, findHashCode);
                    segmentsHashCode = 31 * segmentsHashCode + findHashCode;
                    i = j;

                    // If the current segment is terminated by a /...
                    //
                    if (findTerminatingCharacter == URI_CONST.SEGMENT_SEPARATOR) {
                        // Find the end of the following segment.
                        //
                        j = findSegmentEnd(length, uri, ++i);
                    } else {
                        // Otherwise, we're done.
                        //
                        break;
                    }
                }
            }

            // If we're not yet past the end of the string, what remains must be the query string.
            //
            if (i++ < length)  // implies uri.charAt(i) == QUERY_SEPARATOR
            {
                // Intern what's left to the end of the string.
                //
                query = intern(uri, i, length - i);
            }

            // If we're sure we have the right hash code (the scheme was not lower cased), we can use it, otherwise, we must compute a hash code.
            //
            URI resultURI;
            if (hasExpectedHashCode) {
                resultURI = pool.intern(true, true, scheme, authority, device, absolutePath, segments, query, hashCode);
            } else {
                resultURI = pool.intern(true, URIComponentsAccessUnit.VALIDATE_NONE, true, scheme, authority, device, absolutePath, segments, query);

                // If something tries to add an entry for this access unit, we'd better be sure that the hash code is that of the transformed URI.
                //
                this.hashCode = resultURI.hashCode();
            }

            if (isSchemeNormal) {
                resultURI.cacheString(uri);
            }

            return resultURI;
        }

        /**
         * Looks for a '/', ':', or '?', computing the {@link #findHashCode hash code} and {@link #findTerminatingCharacter setting the character} that terminated the scan.
         */
        protected int findMajorSeparator(int length, String s, int i) {
            findTerminatingCharacter = URI_CONST.QUERY_SEPARATOR;
            int hashCode = 0;
            for (; i < length; i++) {
                char c = s.charAt(i);
                if (c == URI_CONST.SEGMENT_SEPARATOR || c == URI_CONST.SCHEME_SEPARATOR || c == URI_CONST.QUERY_SEPARATOR) {
                    findTerminatingCharacter = c;
                    break;
                }
                hashCode = 31 * hashCode + c;
            }
            findHashCode = hashCode;
            return i;
        }

        /**
         * Looks for a '/', or '?', computing the {@link #findHashCode hash code} and {@link #findTerminatingCharacter setting the character} that terminated the scan.
         */
        protected int findSegmentEnd(int length, String s, int i) {
            findTerminatingCharacter = URI_CONST.QUERY_SEPARATOR;
            int hashCode = 0;
            for (; i < length; i++) {
                char c = s.charAt(i);
                if (c == URI_CONST.SEGMENT_SEPARATOR || c == URI_CONST.QUERY_SEPARATOR) {
                    findTerminatingCharacter = c;
                    break;
                }
                hashCode = 31 * hashCode + c;
            }
            findHashCode = hashCode;
            return i;
        }

        @Override
        public void reset(boolean isExclusive) {
            value = null;
            super.reset(isExclusive);
        }
    }

    /**
     * Access units for platform URI string-based access.
     */
    protected final PlatformAccessUnit.Queue platformAccessUnits = new PlatformAccessUnit.Queue();

    /**
     * An access units for platform URI string-based access.
     */
    protected static class PlatformAccessUnit extends URIPoolAccessUnitBase {
        protected static class Queue extends AccessUnit.Queue<URI> {
            private static final long serialVersionUID = 1L;

            @Override
            public PlatformAccessUnit pop(boolean isExclusive) {
                return (PlatformAccessUnit) super.pop(isExclusive);
            }

            @Override
            protected AccessUnit<URI> newAccessUnit() {
                return new PlatformAccessUnit(this);
            }
        }

        /**
         * The hash code of <code>"platform:/resource/"</code>.
         */
        protected static final int PLATFORM_RESOURCE_BASE_FULL_HASH_CODE = "platform:/resource/".hashCode();

        /**
         * The hash code of <code>"platform:/plugin/"</code>.
         */
        protected static final int PLATFORM_PLUGIN_BASE_FULL_HASH_CODE = "platform:/plugin/".hashCode();

        /**
         * The hash code of <code>"platform:/resource"</code>.
         */
        protected static final int PLATFORM_RESOURCE_BASE_INITIAL_HASH_CODE = "platform:/resource".hashCode();

        /**
         * The hash code of <code>"platform:/plugin/"</code>.
         */
        protected static final int PLATFORM_PLUGIN_BASE_INITIAL_HASH_CODE = "platform:/plugin".hashCode();

        /**
         * The base that implicitly precedes the {@link #path}.
         */
        protected String base;

        /**
         * The path being accessed.
         */
        protected String path;

        /**
         * Whether the pathName needs encoding.
         */
        protected boolean encode;

        /**
         * A buffer uses for processing the path.
         */
        protected char[] characters = new char[100];

        /**
         * The accumulated segments pulled from the path.
         */
        protected String[] segments = new String[20];

        /**
         * The number of {@link #segments}.
         */
        protected int segmentCount;

        /**
         * The number of segments populated with strings during intern that need to be nulled during reset.
         */
        protected int usedSegmentCount;

        /**
         * The boundaries of the path segments.
         */
        protected int[] segmentBoundaries = new int[100];

        /**
         * The hash code of the path segments.
         */
        protected int[] segmentHashCodes = new int[100];

        /**
         * The path after it's been encoded.
         */
        protected String encodedPath;

        /**
         * Creates and instance managed by the given queue.
         */
        protected PlatformAccessUnit(PlatformAccessUnit.Queue queue) {
            super(queue);
        }

        /**
         * Caches the parameters and computes the hash code, which can involve encoding the path.
         */
        protected void setValue(String base, String path, boolean encode) {
            this.base = base;
            this.path = path;
            this.encode = encode;

            int length = path.length();
            if (length == 0) {
                encodedPath = "/";
                segmentBoundaries[segmentCount] = 0;
                segmentBoundaries[segmentCount++] = 1;
                this.hashCode = base == URI_CONST.SEGMENT_RESOURCE ? PLATFORM_RESOURCE_BASE_FULL_HASH_CODE : PLATFORM_PLUGIN_BASE_FULL_HASH_CODE;
            } else {
                // At most each character could need encoding and that would triple the length.
                // Even when not encoding, we still check for the ? and # and encode those.
                //
                int maxEncodedLength = 3 * length;
                if (characters.length <= maxEncodedLength) {
                    // Leave room for one more character, i.e., the leading / that may need to be added.
                    //
                    characters = new char[maxEncodedLength + 1];
                }

                // There can be at most as many segments as characters.
                //
                if (segmentBoundaries.length < length) {
                    segmentBoundaries = new int[length];
                    segmentHashCodes = new int[length];
                }

                // Keep track of whether any characters were encoded.
                //
                boolean isModified = false;

                // Treat this character the same as a /.  In fact, on non-Wwindows systems this will be a / anyway.
                //
                char separatorchar = File.separatorChar;

                char character = path.charAt(0);
                if (character == URI_CONST.SEGMENT_SEPARATOR) {
                    // If the path starts with a /, copy over all the characters into the buffer.
                    //
                    path.getChars(0, length, characters, 0);
                } else if (character == separatorchar) {
                    // If the path starts with a \, put a / at the start and copy over all the characters except the first into the buffer.
                    //
                    characters[0] = URI_CONST.SEGMENT_SEPARATOR;
                    if (length != 1) {
                        path.getChars(1, length, characters, 1);
                    }
                    // Indicate that we've modified the original path.
                    //
                    isModified = true;
                } else {
                    // It doesn't start with a separator character so put a / at the start and copy all the characters into the buffer after that.
                    //
                    characters[0] = URI_CONST.SEGMENT_SEPARATOR;
                    path.getChars(0, length, characters, 1);

                    // The string is now one character longer and we've modified the path.
                    //
                    ++length;
                    isModified = true;
                }

                // The first character in the buffer is a /, so that's the initial hash code.
                //
                int hashCode = URI_CONST.SEGMENT_SEPARATOR;
                int segmentHashCode = 0;

                // Iterate over all the characters...
                //
                for (int i = 1; i < length; ++i) {
                    // If the character is one that needs encoding, including the path separators or special characters.
                    //
                    character = characters[i];
                    if (encode ? character < 160 && !URI.matches(character, URI_CONST.SEGMENT_CHAR_HI, URI_CONST.SEGMENT_CHAR_LO) : URI.matches(character, URI_CONST.PLATFORM_SEGMENT_RESERVED_HI, URI_CONST.PLATFORM_SEGMENT_RESERVED_LO)) {
                        if (character == URI_CONST.SEGMENT_SEPARATOR) {
                            // If it's a /, cache the segment hash code, and boundary, reset the segment hash code, and compose the complete hash code.
                            //
                            segmentHashCodes[segmentCount] = segmentHashCode;
                            segmentBoundaries[segmentCount++] = i;
                            segmentHashCode = 0;
                            hashCode = 31 * hashCode + URI_CONST.SEGMENT_SEPARATOR;
                        } else if (character == separatorchar) {
                            // If it's a \, convert it to a /, cache the segment hash code, and boundary, reset the segment hash code, and compose the complete hash code, and indicate we've modified the original path.
                            //
                            characters[i] = URI_CONST.SEGMENT_SEPARATOR;
                            segmentHashCodes[segmentCount] = segmentHashCode;
                            segmentBoundaries[segmentCount++] = i;
                            segmentHashCode = 0;
                            hashCode = 31 * hashCode + URI_CONST.SEGMENT_SEPARATOR;
                            isModified = true;
                        } else {
                            // Escape the character.
                            //
                            isModified = true;

                            // Shift the buffer to the right 3 characters.
                            //
                            System.arraycopy(characters, i + 1, characters, i + 3, length - i - 1);

                            // Add a % and compose the segment hashCode and the complete hash code.
                            //
                            characters[i] = URI_CONST.ESCAPE;
                            hashCode = 31 * hashCode + URI_CONST.ESCAPE;
                            segmentHashCode = 31 * segmentHashCode + URI_CONST.ESCAPE;

                            // Add the first hex digit and compose the segment hashCode and the complete hash code.
                            //
                            char firstHexCharacter = characters[++i] = URI_CONST.HEX_DIGITS[(character >> 4) & 0x0F];
                            hashCode = 31 * hashCode + firstHexCharacter;
                            segmentHashCode = 31 * segmentHashCode + firstHexCharacter;

                            // Add the second hex digit and compose the segment hashCode and the complete hash code.
                            //
                            char secondHexCharacter = characters[++i] = URI_CONST.HEX_DIGITS[character & 0x0F];
                            hashCode = 31 * hashCode + secondHexCharacter;
                            segmentHashCode = 31 * segmentHashCode + secondHexCharacter;

                            // The length is two characters bigger than before.
                            //
                            length += 2;
                        }
                    } else {
                        // No encoding required, so just compose the segment hash code and the complete hash code.
                        //
                        hashCode = 31 * hashCode + character;
                        segmentHashCode = 31 * segmentHashCode + character;
                    }
                }

                // Set cache the final segment's hash code and boundary.
                //
                segmentHashCodes[segmentCount] = segmentHashCode;
                segmentBoundaries[segmentCount++] = length;

                // Compose the overall hash code to include the base's hash code.
                //
                this.hashCode = (base == URI_CONST.SEGMENT_RESOURCE ? PLATFORM_RESOURCE_BASE_INITIAL_HASH_CODE : PLATFORM_PLUGIN_BASE_INITIAL_HASH_CODE) * CommonUtil.powerOf31(length) + hashCode;
                encodedPath = isModified ? intern(characters, 0, length, hashCode) : path;
            }
        }

        @Override
        protected boolean matches(URI value) {
            return value.matches(base, encodedPath);
        }

        @Override
        public URI getInternalizedValue() {
            // Ensure that there are enough segments to hold the results.
            //
            if (segments.length <= segmentCount) {
                segments = new String[segmentCount + 1];
            }

            // Start with the given base segment.
            //
            segments[0] = base;

            // Compute the hash code of the segments array.
            // The offset is the start of the segment within the character's buffer, which is initially at index 1, i.e., after the leading /.
            //
            int hashCode = 31 + base.hashCode();
            for (int i = 0, offset = 1, segmentCount = this.segmentCount; i < segmentCount; ) {
                // Get the segment's hash code.
                //
                int segmentHashCode = segmentHashCodes[i];

                // Get the terminating boundary for this segment.
                //
                int end = segmentBoundaries[i++];

                // The number of characters in the segment.
                //
                int count = end - offset;

                // Intern that character range with the known segment hash code.
                //
                segments[i] = intern(characters, offset, count, segmentHashCode);

                // Compose the segment's hash code.
                //
                hashCode = 31 * hashCode + segmentHashCode;

                // Set the offset to be one character after the terminating /.
                offset = end + 1;
            }

            // The number of segments populated and needing to be reset to null.
            //
            usedSegmentCount = segmentCount + 1;

            // Create a hierarchical platform-scheme URI from the interned segments.
            //
            return new Hierarchical(this.hashCode, URI_CONST.SCHEME_PLATFORM, null, null, true, internArray(segments, 0, usedSegmentCount, hashCode), null);
        }

        @Override
        public void reset(boolean isExclusive) {
            for (int i = 0; i < usedSegmentCount; ++i) {
                segments[i] = null;
            }
            segmentCount = 0;
            usedSegmentCount = 0;
            encodedPath = null;
            base = null;
            path = null;

            super.reset(isExclusive);
        }
    }

    /**
     * Access units for file URI string-based access.
     */
    protected final FileAccessUnit.Queue fileAccessUnits = new FileAccessUnit.Queue();

    /**
     * An Access unit for file URI string-based access.
     */
    protected static class FileAccessUnit extends URIPoolAccessUnitBase {
        protected static class Queue extends AccessUnit.Queue<URI> {
            private static final long serialVersionUID = 1L;

            @Override
            public FileAccessUnit pop(boolean isExclusive) {
                return (FileAccessUnit) super.pop(isExclusive);
            }

            @Override
            protected AccessUnit<URI> newAccessUnit() {
                return new FileAccessUnit(this);
            }
        }

        /**
         * The base URI for file scheme URIs.
         */
        protected static final String FILE_BASE = "file:/";

        /**
         * The length of the base URI for file scheme URIs.
         */
        protected static final int FILE_BASE_LENGTH = "file:/".length();

        /**
         * The hash code of the base URI for file scheme URIs.
         */
        protected static final int FILE_BASE_HASH_CODE = FILE_BASE.hashCode();

        /**
         * The file path being accessed.
         */
        protected String path;

        /**
         * The buffer for absolute file paths.
         */
        protected char[] absoluteCharacters = new char[100];

        /**
         * The buffer for relative file paths.
         */
        protected char[] relativeCharacters = new char[100];

        /**
         * The segments of the path.
         */
        protected String[] segments = new String[20];

        /**
         * The number of segments in the path.
         */
        protected int segmentCount;

        /**
         * The number of segments populated with strings during intern that need to be nulled during reset.
         */
        protected int usedSegmentCount;

        /**
         * The boundaries of the segments in the path.
         */
        protected int[] segmentBoundaries = new int[100];

        /**
         * The hash codes of the segments in the path.
         */
        protected int[] segmentHashCodes = new int[100];

        /**
         * The final encoded path.
         */
        protected String encodedPath;

        /**
         * Whether the file path represents an absolute file.
         */
        protected boolean isAbsoluteFile;

        /**
         * Whether the path itself is absolute.
         */
        protected boolean isAbsolutePath;

        /**
         * Creates an instance managed by the given queue.
         */
        public FileAccessUnit(FileAccessUnit.Queue queue) {
            super(queue);

            // Caches the base absolute file path characters.
            //
            FILE_BASE.getChars(0, FILE_BASE_LENGTH, absoluteCharacters, 0);
        }

        /**
         * Caches the parameter and computes the hash code.
         */
        protected void setValue(String path) {
            this.path = path;

            int length = path.length();
            if (length == 0) {
                // It's just the empty string with the zero hash code.
                //
                encodedPath = "";
                this.hashCode = 0;
            } else {
                // Is this path considered an absolute file by the file system implementation?
                //
                isAbsoluteFile = new File(path).isAbsolute();

                // This will use either the absoluteCharacters or the relativeCharacters...
                //
                char[] characters;

                // Check the first character.
                //
                char character = path.charAt(0);

                // We're convert this character to a /.
                //
                char separatorchar = File.separatorChar;

                // Compose the hash code.
                //
                int hashCode;

                // Walk the path segments...
                //
                int i;

                // There can be at most as many boundaries as characters.
                //
                if (segmentBoundaries.length < length) {
                    segmentBoundaries = new int[length];
                    segmentHashCodes = new int[length];
                }

                if (isAbsoluteFile) {
                    // If it's an absolute file then it must be an absolute path.
                    //
                    isAbsolutePath = true;

                    // There can be at most as many encoded characters as three times the length, plus we need to account for the characters in the base.
                    //
                    int maxEncodedLength = 3 * length + FILE_BASE_LENGTH;
                    if (absoluteCharacters.length <= maxEncodedLength) {
                        // Allocate one slightly larger and copy in the base path.
                        //
                        absoluteCharacters = new char[maxEncodedLength + 1];
                        FILE_BASE.getChars(0, FILE_BASE_LENGTH, absoluteCharacters, 0);
                    }

                    // Process the absolute characters.
                    //
                    characters = absoluteCharacters;

                    if (character == URI_CONST.SEGMENT_SEPARATOR || character == separatorchar) {
                        // If the path starts with a separator, copy over the characters after that / to the buffer after the base.
                        //
                        path.getChars(1, length, characters, FILE_BASE_LENGTH);

                        // The length is larger by one less than the base.
                        //
                        length += FILE_BASE_LENGTH - 1;
                    } else {
                        // If the path doesn't start with a /, copy over all the characters into the buffer after the base.
                        //
                        path.getChars(0, length, characters, FILE_BASE_LENGTH);

                        // The length is larger by the base.
                        //
                        length += FILE_BASE_LENGTH;
                    }

                    // The first boundary is after the base and that's where we start processing the characters.
                    //
                    segmentBoundaries[0] = i = FILE_BASE_LENGTH;

                    // The hash code so far is the base's hash code.
                    //
                    hashCode = FILE_BASE_HASH_CODE;
                } else {
                    // There can be at most as many encoded characters as three times the length.
                    //
                    int maxEncodedLength = 3 * length;
                    if (relativeCharacters.length <= maxEncodedLength) {
                        // Allocate one slightly larger.
                        //
                        relativeCharacters = new char[maxEncodedLength + 1];
                    }

                    // Process the relative characters.
                    //
                    characters = relativeCharacters;

                    if (character == URI_CONST.SEGMENT_SEPARATOR || character == separatorchar) {
                        // If the path starts with a separator, then it's an absolute path.
                        //
                        isAbsolutePath = true;

                        // Set the leading / and   copy over the characters after the leading / or \ to the buffer after that.
                        //
                        characters[0] = URI_CONST.SEGMENT_SEPARATOR;
                        path.getChars(1, length, characters, 1);

                        // The first boundary is after the / and that's where we start processing the characters.
                        //
                        segmentBoundaries[0] = i = 1;

                        // The hash code so far is the /'s hash code.
                        //
                        hashCode = URI_CONST.SEGMENT_SEPARATOR;
                    } else {
                        // No leading separator so it's a relative path.
                        //
                        isAbsolutePath = false;

                        //  Copy over all the characters in the bufffer.
                        //
                        path.getChars(0, length, characters, 0);

                        // The first boundary is at the start and that's where we start processing the characters.
                        //
                        segmentBoundaries[0] = i = 0;

                        // The hash code so far is zero.
                        //
                        hashCode = 0;
                    }
                }

                // Compose the segment hash code as we scan the characters.
                //
                int segmentHashCode = 0;
                for (; i < length; ++i) {
                    // If the current character needs encoding (including the separator characters) or is the device identifier and we're processing the first segment of an absolute path...
                    //
                    character = characters[i];
                    if (character < 160 && (!URI.matches(character, URI_CONST.SEGMENT_CHAR_HI, URI_CONST.SEGMENT_CHAR_LO) || character == URI_CONST.DEVICE_IDENTIFIER && !isAbsolutePath && segmentCount == 0)) {
                        if (character == URI_CONST.SEGMENT_SEPARATOR) {
                            // If it's a /, cache the segment hash code and the segment boundary, reset the segment hash code, and compose the segments hash code.
                            //
                            segmentHashCodes[segmentCount] = segmentHashCode;
                            segmentBoundaries[++segmentCount] = i;
                            segmentHashCode = 0;
                            hashCode = 31 * hashCode + URI_CONST.SEGMENT_SEPARATOR;
                        } else if (character == separatorchar) {
                            // If it's a \, change it to a /, cache the segment hash code and the segment boundary, reset the segment hash code, and compose the segments hash code.
                            //
                            characters[i] = URI_CONST.SEGMENT_SEPARATOR;
                            segmentHashCodes[segmentCount] = segmentHashCode;
                            segmentBoundaries[++segmentCount] = i;
                            segmentHashCode = 0;
                            hashCode = 31 * hashCode + URI_CONST.SEGMENT_SEPARATOR;
                        } else {
                            // Shift the buffer to the right 3 characters.
                            //
                            System.arraycopy(characters, i + 1, characters, i + 3, length - i - 1);

                            // Add a % and compose the segment hashCode and the complete hash code.
                            //
                            characters[i] = URI_CONST.ESCAPE;
                            hashCode = 31 * hashCode + URI_CONST.ESCAPE;
                            segmentHashCode = 31 * segmentHashCode + URI_CONST.ESCAPE;

                            // Add the first hex digit and compose the segment hashCode and the complete hash code.
                            //
                            char firstHexCharacter = characters[++i] = URI_CONST.HEX_DIGITS[(character >> 4) & 0x0F];
                            hashCode = 31 * hashCode + firstHexCharacter;
                            segmentHashCode = 31 * segmentHashCode + firstHexCharacter;

                            // Add the second hex digit and compose the segment hashCode and the complete hash code.
                            //
                            char secondHexCharacter = characters[++i] = URI_CONST.HEX_DIGITS[character & 0x0F];
                            hashCode = 31 * hashCode + secondHexCharacter;
                            segmentHashCode = 31 * segmentHashCode + secondHexCharacter;

                            // The length is two characters bigger than before.
                            //
                            length += 2;
                        }
                    } else {
                        // No encoding required, so just compose the segment hash code and the complete hash code.
                        //
                        hashCode = 31 * hashCode + character;
                        segmentHashCode = 31 * segmentHashCode + character;
                    }
                }

                // Set cache the final segment's hash code and boundary.
                //
                segmentHashCodes[segmentCount] = segmentHashCode;
                segmentBoundaries[++segmentCount] = length;

                // Compose the overall hash code to include the base's hash code.
                //
                this.hashCode = hashCode;

                // Cache the encoded path.
                //
                encodedPath = intern(characters, 0, length, hashCode);
            }
        }

        @Override
        protected boolean matches(URI value) {
            return value.matches(encodedPath);
        }

        @Override
        public URI getInternalizedValue() {
            // Ensure that we have enough room for all the segments.
            //
            int segmentCount = this.segmentCount;
            if (segments.length <= segmentCount) {
                segments = new String[segmentCount + 1];
            }

            // Process the appropriate characters.
            //
            char[] characters = isAbsoluteFile ? absoluteCharacters : relativeCharacters;

            // Parse out the device and the authority...
            //
            String device = null;
            String authority = null;

            // The initial hash code for the over all final segments.
            //
            int segmentsHashCode = 1;

            // Where we expect the special device segment to appear.
            //
            int deviceIndex = 0;

            // An empty segment at this index will be igored.
            //
            int ignoredEmptySegmentIndex = -1;

            // Whether we ignored an empty segment.
            //
            boolean ignoredEmptySegment = false;

            // Process all the segments...
            //
            for (int i = 0, segmentIndex = 0, offset = segmentBoundaries[0]; segmentIndex < segmentCount; ++i) {
                // The end of the current segment's boundary.
                //
                int end = segmentBoundaries[i + 1];

                // The number of characters of the current segment.
                //
                int count = end - offset;

                // If this is an empty segment we wish to ignore...
                //
                if (i == ignoredEmptySegmentIndex && count == 0) {
                    // Ignore it and indicate that we ignored a leading empty segment.
                    //
                    --segmentCount;
                    ignoredEmptySegment = true;
                } else {
                    // Retrieve the segment's hash code.
                    //
                    int segmentHashCode = segmentHashCodes[i];

                    // Intern the segment characters...
                    //
                    String segment = intern(characters, offset, count, segmentHashCode);

                    // If we're at a device index while processing an absolute file, and we have an empty segment that's not the only segment or the last character of the segment is the device identifier...
                    //
                    if (i == deviceIndex && isAbsoluteFile && (count == 0 && segmentCount > 1 || characters[end - 1] == URI_CONST.DEVICE_IDENTIFIER)) {
                        // If the segment has zero length...
                        //
                        if (count == 0) {
                            // Proceed to the next segment; there must be one because of the guard...
                            //
                            offset = end + 1;
                            segmentHashCode = segmentHashCodes[++i];
                            end = segmentBoundaries[i + 1];
                            count = end - offset;

                            // This segment is really the authority...
                            //
                            authority = intern(characters, offset, count, segmentHashCode);

                            // There are now two fewer segments.
                            //
                            segmentCount -= 2;

                            // We should still check for a device at index 2.
                            //
                            deviceIndex = 2;

                            // We should still consider ignoring an empty segment if it's at index 2.
                            //
                            ignoredEmptySegmentIndex = 2;
                        } else {
                            // Otherwise the segment must end with a :, so it must be the device.
                            //
                            device = segment;

                            // There's one fewer real segment.
                            //
                            --segmentCount;

                            // We should ignore an empty segment if it comes next.
                            //
                            ignoredEmptySegmentIndex = deviceIndex + 1;
                        }
                    } else {
                        // It's a normal segment so include it and compose the overall segments hash code.
                        //
                        segments[segmentIndex++] = segment;
                        segmentsHashCode = 31 * segmentsHashCode + segmentHashCode;
                    }
                }

                // Continue with the characters after the current segment's closing boundary.
                //
                offset = end + 1;
            }

            // Remember which segments need to be cleared in reset.
            //
            usedSegmentCount = segmentCount;

            // Intern the segments array itself.
            //
            String[] internedSegments = internArray(segments, 0, segmentCount, segmentsHashCode);
            if (isAbsoluteFile) {
                // If it's absolute, we include the file scheme, and it has an absolute path, if there is one or more segments, or if we ignored an empty segment.
                //
                return new Hierarchical(this.hashCode, URI_CONST.SCHEME_FILE, authority, device, segmentCount > 0 || ignoredEmptySegment, internedSegments, null);
            } else {
                // It's a relative URI...
                //
                return new Hierarchical(this.hashCode, null, null, null, isAbsolutePath, internedSegments, null);
            }
        }

        @Override
        public void reset(boolean isExclusive) {
            for (int i = 0; i < usedSegmentCount; ++i) {
                segments[i] = null;
            }
            usedSegmentCount = 0;
            segmentCount = 0;
            encodedPath = null;
            path = null;

            super.reset(isExclusive);
        }
    }

    /**
     * Access units for component-based access.
     */
    protected final URIComponentsAccessUnit.Queue uriComponentsAccessUnits = new URIComponentsAccessUnit.Queue();

    /**
     * An Access unit for component-based access.
     */
    protected static class URIComponentsAccessUnit extends URIPoolAccessUnitBase {
        /**
         * A value for {@link #validate} that implies no checking or interning of components is required.
         */
        protected static final int VALIDATE_NONE = -1;

        /**
         * A value for {@link #validate} that implies all components need to be validated.
         */
        protected static final int VALIDATE_ALL = -2;

        /**
         * A value for {@link #validate} that implies only the query componet needs validating.
         */
        protected static final int VALIDATE_QUERY = -3;

        protected static class Queue extends AccessUnit.Queue<URI> {
            private static final long serialVersionUID = 1L;

            @Override
            public URIComponentsAccessUnit pop(boolean isExclusive) {
                return (URIComponentsAccessUnit) super.pop(isExclusive);
            }

            @Override
            protected AccessUnit<URI> newAccessUnit() {
                return new URIComponentsAccessUnit(this);
            }
        }

        /**
         * One of the special values {@link #VALIDATE_NONE}, {@link #VALIDATE_ALL}, or {@link #VALIDATE_QUERY}, or the index in the {@link #segments} that need validation.
         */
        int validate;

        /**
         * Whether the components being accesses are for a hierarchical URI
         */
        boolean hierarchical;

        /**
         * The scheme being accessed.
         */
        String scheme;

        /**
         * The authority (or opaque part) being accessed.
         */
        String authority;

        /**
         * The device being accessed.
         */
        String device;
        /**
         * Whether the components being accesses are for an absolute path.
         */
        boolean absolutePath;

        /**
         * The segments being accessed.
         */
        String[] segments;

        /**
         * The query being accessed.
         */
        String query;

        /**
         * An access unit for exclusive use in {@link #internArray(String[], int)}.
         */
        SegmentSequence.StringArrayPool.SegmentsAccessUnit stringArraySegmentsAccessUnit = new SegmentSequence.StringArrayPool.SegmentsAccessUnit(null);

        /**
         * Creates an instance managed by the given queue.
         * @param queue
         */
        protected URIComponentsAccessUnit(URIComponentsAccessUnit.Queue queue) {
            super(queue);
        }

        protected String[] internArray(String[] segments, int count) {
            if (segments == null) {
                return SegmentSequence.EMPTY_ARRAY;
            } else {
                stringArraySegmentsAccessUnit.setValue(true, true, segments, count);
                return SegmentSequence.STRING_ARRAY_POOL.doIntern(false, stringArraySegmentsAccessUnit);
            }
        }

        /**
         * Caches the parameters.
         */
        protected void setValue(boolean hierarchical, String scheme, String authority, String device, boolean absolutePath, String[] segments, String query, int hashCode) {
            this.validate = VALIDATE_NONE;
            this.hierarchical = hierarchical;
            this.scheme = scheme;
            this.authority = authority;
            this.device = device;
            this.absolutePath = absolutePath;
            this.segments = segments;
            this.query = query;
            this.hashCode = hashCode;
        }

        /**
         * Caches the parameters and computes the hash code.
         */
        protected void setValue(int validate, boolean hierarchical, String scheme, String authority, String device, boolean absolutePath, String[] segments, String query) {
            int hashCode = 0;
            if (scheme != null) {
                if (validate == VALIDATE_ALL) {
                    scheme = intern(true, scheme);
                }
                hashCode = scheme.hashCode() * 31 + URI_CONST.SCHEME_SEPARATOR;
            }

            this.validate = validate;
            this.hierarchical = hierarchical;
            this.scheme = scheme;
            this.authority = authority;
            this.device = device;
            this.absolutePath = absolutePath;
            this.segments = segments;
            this.query = query;

            if (hierarchical) {
                if (segments == null) {
                    segments = URI_CONST.NO_SEGMENTS;
                }

                this.segments = segments;

                if (authority != null) {
                    if (!URI.isArchiveScheme(scheme)) hashCode = hashCode * 961 + URI_CONST.AUTHORITY_SEPARATOR_HASH_CODE;
                    hashCode = hashCode * CommonUtil.powerOf31(authority.length()) + authority.hashCode();
                }

                if (device != null) {
                    hashCode = hashCode * 31 + URI_CONST.SEGMENT_SEPARATOR;
                    hashCode = hashCode * CommonUtil.powerOf31(device.length()) + device.hashCode();
                }

                if (absolutePath) hashCode = hashCode * 31 + URI_CONST.SEGMENT_SEPARATOR;

                for (int i = 0, len = segments.length; i < len; i++) {
                    if (i != 0) hashCode = hashCode * 31 + URI_CONST.SEGMENT_SEPARATOR;
                    String segment = segments[i];
                    if (segment == null) {
                        throw new IllegalArgumentException("invalid segment: null");
                    }
                    hashCode = hashCode * CommonUtil.powerOf31(segment.length()) + segment.hashCode();
                }

                if (query != null) {
                    hashCode = hashCode * 31 + URI_CONST.QUERY_SEPARATOR;
                    hashCode = hashCode * CommonUtil.powerOf31(query.length()) + query.hashCode();
                }
            } else {
                hashCode = hashCode * CommonUtil.powerOf31(authority.length()) + authority.hashCode();
            }

            this.hashCode = hashCode;
        }

        @Override
        protected boolean matches(URI value) {
            return value.matches(validate, hierarchical, scheme, authority, device, absolutePath, segments, query);
        }

        @Override
        public URI getInternalizedValue() {
            if (validate == VALIDATE_ALL) {
                // Validate all the components.
                //
                URI.validateURI(hierarchical, scheme, authority, device, absolutePath, segments, query, null);

                // Intern the components.
                //
                if (authority != null) {
                    authority = intern(authority);
                }
                if (device != null) {
                    device = intern(device);
                }
                segments = segments == null ? null : internArray(segments, segments.length);
                if (query != null) {
                    query = intern(query);
                }
            } else if (validate == VALIDATE_QUERY) {
                // Validate just the query.
                //
                if (!URI.validQuery(query)) {
                    throw new IllegalArgumentException("invalid query portion: " + query);
                }

                // Intern the just the query.
                //
                if (query != null) {
                    query = intern(query);
                }
            } else if (validate != VALIDATE_NONE) {
                // Validate the segments that need validation.
                //
                for (int i = validate, length = segments.length; i < length; ++i) {
                    String segment = segments[i];
                    if (!URI.validSegment(segment)) {
                        throw new IllegalArgumentException("invalid segment: " + segment);
                    }
                }
            }

            // Create the appropriate type of URI.
            //
            if (hierarchical) {
                return new Hierarchical(hashCode, scheme, authority, device, absolutePath, segments, query);
            } else {
                return new Opaque(hashCode, scheme, authority);
            }
        }

        @Override
        public void reset(boolean isExclusive) {
            this.scheme = null;
            this.authority = null;
            this.device = null;
            this.segments = null;
            this.query = null;
            super.reset(isExclusive);
        }
    }

    /**
     * Intern a URI from its string representation, parsing if necessary.
     * The string must not contain the fragment separator.
     */
    protected URI intern(String string) {
        if (string == null) {
            return null;
        } else {
            // Iterate over the entries with the matching hash code.
            //
            int hashCode = string.hashCode();
            for (Entry<URI> entry = getEntry(hashCode); entry != null; entry = entry.getNextEntry()) {
                // Check that the referent isn't garbage collected and then compare it.
                //
                URI uri = entry.get();
                if (uri != null && uri.matches(string)) {
                    // Return that already present value.
                    //
                    return uri;
                }
            }

            writeLock.lock();
            try {
                StringAccessUnit accessUnit = stringAccessUnits.pop(true);
                accessUnit.setValue(string, hashCode);

                // The implementation returns an internalized value that's already pooled as a side effect.
                //
                URI result = accessUnit.getInternalizedValue();

                accessUnit.reset(true);
                return result;
            } finally {
                writeLock.unlock();
            }
        }
    }

    /**
     * Intern a platform URI from its path representation, parsing if necessary.
     */
    protected URI intern(String base, String pathName, boolean encode) {
        PlatformAccessUnit accessUnit = platformAccessUnits.pop(false);
        accessUnit.setValue(base, pathName, encode);
        return doIntern(false, accessUnit);
    }

    /**
     * Intern a file URI from its path representation, parsing if necessary.
     */
    protected URI internFile(String pathName) {
        FileAccessUnit accessUnit = fileAccessUnits.pop(false);
        accessUnit.setValue(pathName);
        return doIntern(false, accessUnit);
    }

    /**
     * Intern a URI from its component parts.
     * If <code>isExclusive</code> is true, acquire the {@link #writeLock} first.
     * Use {@link #intern(boolean, boolean, String, String, String, boolean, String[], String, int)} if the hash code is known and no validation is required.
     */
    protected URI intern(boolean isExclusive, int validate, boolean hierarchical, String scheme, String authority, String device, boolean absolutePath, String[] segments, String query) {
        if (isExclusive) {
            writeLock.lock();
        }
        URI uri;
        try {
            URIComponentsAccessUnit accessUnit = uriComponentsAccessUnits.pop(isExclusive);
            accessUnit.setValue(validate, hierarchical, scheme, authority, device, absolutePath, segments, query);
            uri = doIntern(isExclusive, accessUnit);
        } finally {
            if (isExclusive) {
                writeLock.unlock();
            }
        }
        return uri;
    }

    /**
     * Intern a URI from its component parts.
     * If <code>isExclusive</code> is true, acquire the {@link #writeLock} first.
     */
    protected URI intern(boolean isExclusive, boolean hierarchical, String scheme, String authority, String device, boolean absolutePath, String[] segments, String query, int hashCode) {
        if (isExclusive) {
            writeLock.lock();
        }
        URI uri;
        try {
            URIComponentsAccessUnit accessUnit = uriComponentsAccessUnits.pop(isExclusive);
            accessUnit.setValue(hierarchical, scheme, authority, device, absolutePath, segments, query, hashCode);
            uri = doIntern(isExclusive, accessUnit);
        } finally {
            if (isExclusive) {
                writeLock.unlock();
            }
        }
        return uri;
    }

    /**
     * Specialized to manage the {@link #cachedToStrings}.
     */
    @Override
    protected void doCleanup() {
        super.doCleanup();
        for (; ; ) {
            Reference<? extends String> cachedToString = cachedToStrings.poll();
            if (cachedToString == null) {
                return;
            } else {
                cachedToString.clear();
            }
        }
    }

    /**
     * A specialized weak reference used by {@link URI#toString} that removes the URI's reference when {@link #clear()} is called.
     *
     */
    protected static class CachedToString extends WeakReference<String> {
        protected final URI uri;

        public CachedToString(URI uri, String string, ReferenceQueue<? super String> queue) {
            super(string, queue);
            this.uri = uri;
        }

        @Override
        public void clear() {
            uri.flushCachedString();

            super.clear();
        }
    }

    protected WeakReference<String> newCachedToString(URI uri, String string) {
        return
                cachedToStrings == null ?
                        new CachedToString(uri, string, externalQueue) :
                        new CachedToString(uri, string, cachedToStrings);
    }
}
