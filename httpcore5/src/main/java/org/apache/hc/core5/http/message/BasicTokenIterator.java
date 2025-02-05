/*
 * ====================================================================
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 */

package org.apache.hc.core5.http.message;

import java.util.BitSet;
import java.util.Iterator;

import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.util.TextUtils;
import org.apache.hc.core5.util.Tokenizer;

/**
 * {@link java.util.Iterator} of {@link org.apache.hc.core5.http.Header} tokens..
 *
 * @since 4.0
 */
public class BasicTokenIterator extends AbstractHeaderElementIterator<String> {

    private static final BitSet COMMA = Tokenizer.INIT_BITSET(',');

    private final Tokenizer tokenizer;

    /**
     * Creates a new instance of {@link BasicTokenIterator}.
     *
     * @param headerIterator    the iterator for the headers to tokenize
     */
    public BasicTokenIterator(final Iterator<? extends Header> headerIterator) {
        super(headerIterator);
        this.tokenizer = Tokenizer.INSTANCE;
    }

    @Override
    String parseHeaderElement(final CharSequence buf, final ParserCursor cursor) {
        final String token = this.tokenizer.parseToken(buf, cursor, COMMA);
        if (!cursor.atEnd()) {
            final int pos = cursor.getPos();
            if (buf.charAt(pos) == ',') {
                cursor.updatePos(pos + 1);
            }
        }
        return !TextUtils.isBlank(token) ? token : null;
    }

}

