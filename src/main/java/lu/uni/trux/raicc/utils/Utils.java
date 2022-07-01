package lu.uni.trux.raicc.utils;

import lu.uni.trux.raicc.extractors.*;
import soot.SootClass;
import soot.SootMethod;
import soot.Value;
import soot.jimple.InvokeExpr;

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

public class Utils {

    public static boolean isPendingIntentCreationMethod(SootMethod m) {
        String sig = m.getSignature();
        return sig.equals(Constants.ANDROID_APP_PENDING_INTENT_GET_ACTIVITY) ||
                sig.equals(Constants.ANDROID_APP_PENDING_INTENT_GET_BROADCAST) ||
                sig.equals(Constants.ANDROID_APP_PENDING_INTENT_GET_SERVICE) ||
                sig.equals(Constants.ANDROID_APP_PENDING_INTENT_GET_ACTIVITY_BUNDLE);
    }

    public static boolean isSystemClass(SootClass clazz) {
        String className = clazz.getName();
        return (className.startsWith("android.") || className.startsWith("java.") || className.startsWith("javax.")
                || className.startsWith("sun.") || className.startsWith("org.omg.")
                || className.startsWith("org.w3c.dom.") || className.startsWith("com.google.")
                || className.startsWith("com.android.") || className.startsWith("androidx."));
    }

    public static Value getIntentWrapper(InvokeExpr ie) {
        WrapperLocalExtractorImpl wle = new WrapperLocalExtractorParam0(null);
        wle = new WrapperLocalExtractorParam1(wle);
        wle = new WrapperLocalExtractorParam2(wle);
        wle = new WrapperLocalExtractorParam3(wle);
        wle = new WrapperLocalExtractorParam4(wle);
        return wle.extractWrapperLocal(ie);
    }
}
