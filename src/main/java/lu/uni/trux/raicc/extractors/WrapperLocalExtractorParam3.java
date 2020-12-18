package lu.uni.trux.raicc.extractors;

import lu.uni.trux.raicc.utils.Constants;

/*-
 * #%L
 * RAICC
 * 
 * %%
 * Copyright (C) 2020 Jordan Samhi
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

public class WrapperLocalExtractorParam3 extends WrapperLocalExtractorImpl {

	public WrapperLocalExtractorParam3(WrapperLocalExtractorImpl next) {
		super(next);
		this.methods.add(Constants.ANDROID_APP_ALARMMANAGER_SETINEXACTREPEATING);
		this.methods.add(Constants.ANDROID_APP_ALARMMANAGER_SETREPEATING);
		this.methods.add(Constants.ANDROID_APP_ALARMMANAGER_SETWINDOW);
		this.methods.add(Constants.ANDROID_LOCATION_LOCATIONMANAGER_REQUESTLOCATIONUPDATES_1);
		this.methods.add(Constants.ANDROID_LOCATION_LOCATIONMANAGER_REQUESTLOCATIONUPDATES_2);
		this.methods.add(Constants.ANDROID_TELEPHONY_EUICC_EUICCMANAGER_STARTRESOLUTIONACTIVITY);
		this.methods.add(Constants.ANDROID_TELEPHONY_SMSMANAGER_SENDTEXTMESSAGE_1);
		this.methods.add(Constants.ANDROID_TELEPHONY_SMSMANAGER_SENDTEXTMESSAGE_2);
		this.methods.add(Constants.ANDROID_TELEPHONY_SMSMANAGER_SENDTEXTMESSAGEWITHOUTPERSISTING);
		this.methods.add(Constants.ANDROIDX_CORE_APP_ALARMMANAGERCOMPAT_SETEXACT);
		this.methods.add(Constants.ANDROID_APP_NOTIFICATION_SETLATESTEVENTINFO);
	}

	@Override
	protected int getIndexHandled() {
		return 3;
	}
}
