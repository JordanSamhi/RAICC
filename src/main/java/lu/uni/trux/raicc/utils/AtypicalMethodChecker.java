package lu.uni.trux.raicc.utils;

/*-
 * #%L
 * RAICC
 *
 * %%
 * Copyright (C) 2022 Jordan Samhi
 * University of Luxembourg - Interdisciplinary Centre for
 * Security Reliability and Trust (SnT) - TruX - All rights reserved
 *
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 2.1 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 *
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-2.1.html>.
 * #L%
 */

import soot.SootMethod;
import soot.toolkits.scalar.Pair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class AtypicalMethodChecker {

    private static AtypicalMethodChecker instance;
    private final Map<String, Pair<Boolean, String>> methods;

    private AtypicalMethodChecker() {
        this.methods = new HashMap<>();
        this.loadMethods();
    }

    public static AtypicalMethodChecker v() {
        if (instance == null) {
            instance = new AtypicalMethodChecker();
        }
        return instance;
    }

    private void loadMethods() {
        InputStream fis = null;
        BufferedReader br = null;
        String line;
        try {
            fis = Utils.class.getResourceAsStream(Constants.ATYPICAL_ICC_METHODS);
            br = new BufferedReader(new InputStreamReader(fis));
            String method;
            boolean forResult;
            String type;
            Pair<Boolean, String> v;
            while ((line = br.readLine()) != null) {
                String[] split = line.split("\\|");
                method = split[0];
                forResult = split[1].equals("1");
                type = split[2];
                v = new Pair<>(forResult, type);
                this.methods.put(method, v);
            }
        } catch (IOException e) {
            Writer.v().perror(e.getMessage());
        }
        try {
            br.close();
            fis.close();
        } catch (IOException e) {
            Writer.v().perror(e.getMessage());
        }
    }

    public boolean isAtypicalMethod(SootMethod sm) {
        return this.methods.containsKey(sm.getSignature());
    }

    public boolean isForResultMethod(SootMethod sm) {
        Pair<Boolean, String> v = this.methods.get(sm.getSignature());
        if (v != null) {
            return v.getO1();
        }
        return false;
    }
}