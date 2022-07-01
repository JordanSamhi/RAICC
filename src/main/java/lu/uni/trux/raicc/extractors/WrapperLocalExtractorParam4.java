package lu.uni.trux.raicc.extractors;

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

import lu.uni.trux.raicc.utils.Constants;

public class WrapperLocalExtractorParam4 extends WrapperLocalExtractorImpl {

	public WrapperLocalExtractorParam4(WrapperLocalExtractorImpl next) {
		super(next);
		this.methods.add(Constants.ANDROID_LOCATION_LOCATIONMANAGER_ADDPROXIMITYALERT);
		this.methods.add(Constants.ANDROID_TELEPHONY_SMSMANAGER_DOWNLOADMULTIMEDIAMESSAGE);
		this.methods.add(Constants.ANDROID_TELEPHONY_SMSMANAGER_SENDDATAMESSAGE);
		this.methods.add(Constants.ANDROID_TELEPHONY_SMSMANAGER_SENDMULTIMEDIAMESSAGE);
	}

	@Override
	protected int getIndexHandled() {
		return 4;
	}
}
