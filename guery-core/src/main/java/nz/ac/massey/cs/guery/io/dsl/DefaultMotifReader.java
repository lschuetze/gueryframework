/*
 * Copyright 2015 Jens Dietrich
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package nz.ac.massey.cs.guery.io.dsl;

import nz.ac.massey.cs.guery.Motif;
import nz.ac.massey.cs.guery.MotifReader;
import nz.ac.massey.cs.guery.MotifReaderException;
import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;

import java.io.IOException;
import java.io.InputStream;

/**
 * Reads motifs from scripts.
 *
 * @author jens dietrich
 */
public class DefaultMotifReader<V, E> implements MotifReader<V, E> {
    @Override
    public Motif<V, E> read(InputStream source) throws MotifReaderException {

        gueryLexer lexer;
        try {
            lexer = new gueryLexer(new ANTLRInputStream(source));
        } catch (IOException e) {
            throw new MotifReaderException(e);
        }
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        gueryParser parser = new gueryParser(tokens);
        try {
            gueryParser.query_return r = parser.query();
            return parser.getMotif();

        } catch (Exception e) {
            throw new MotifReaderException(e);
        }


    }
}
