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

public class WrapperLocalExtractorParam2 extends WrapperLocalExtractorImpl {

	public WrapperLocalExtractorParam2(WrapperLocalExtractorImpl next) {
		super(next);
		this.methods.add(Constants.ANDROID_CONTENT_PM_PACKAGEINSTALLER_INSTALLEXISTINGPACKAGE);
		this.methods.add(Constants.ANDROID_APP_ALARMMANAGER_SETANDALLOWWHILEIDLE);
		this.methods.add(Constants.ANDROID_APP_ALARMMANAGER_SET);
		this.methods.add(Constants.ANDROID_APP_ALARMMANAGER_SETEXACTANDALLOWWHILEIDLE);
		this.methods.add(Constants.ANDROID_APP_ALARMMANAGER_SETEXACT);
		this.methods.add(Constants.ANDROID_APP_NOTIFICATION$BUILDER_ADDACTION);
		this.methods.add(Constants.ANDROID_APP_NOTIFICATION$ACTION$BUILDER_1);
		this.methods.add(Constants.ANDROID_APP_NOTIFICATION$ACTION$BUILDER_2);
		this.methods.add(Constants.ANDROID_BLUETOOTH_LE_BLUETOOTHLESCANNER_STARTSCAN);
		this.methods.add(Constants.ANDROID_SUPPORT_V4_APP_NOTIFICATIONCOMPAT$BUILDER_ADDACTION);
		this.methods.add(Constants.ANDROID_TELEPHONY_EUICC_EUICCMANAGER_DOWNLOADSUBSCRIPTION);
		this.methods.add(Constants.ANDROID_TELEPHONY_EUICC_EUICCMANAGER_UPDATESUBSCRIPTIONNICKNAME);
		this.methods.add(Constants.ANDROID_TELEPHONY_SMSMANAGER_INJECTSMSPDU);
		this.methods.add(Constants.ANDROIDX_CORE_APP_NOTIFICATIONCOMPAT$ACTION$BUILDER_1);
		this.methods.add(Constants.ANDROIDX_CORE_APP_NOTIFICATIONCOMPAT$ACTION$BUILDER_2);
		this.methods.add(Constants.ANDROIDX_CORE_APP_NOTIFICATIONCOMPAT$ACTION$BUILDER_3);
		this.methods.add(Constants.ANDROIDX_CORE_APP_NOTIFICATIONCOMPAT$ACTION$BUILDER_4);
		this.methods.add(Constants.ANDROIDX_CORE_APP_NOTIFICATIONCOMPAT$ACTION$BUILDER_1);
		this.methods.add(Constants.ANDROIDX_CORE_APP_NOTIFICATIONCOMPAT$ACTION$BUILDER_2);
		this.methods.add(Constants.ANDROIDX_CORE_APP_NOTIFICATIONCOMPAT$ACTION$BUILDER_3);
		this.methods.add(Constants.ANDROIDX_CORE_APP_NOTIFICATIONCOMPAT$ACTION$BUILDER_4);
		this.methods.add(Constants.ANDROID_SUPPORT_V4_APP_NOTIFICATIONCOMPAT$ACTION_1);
		this.methods.add(Constants.ANDROIDX_CORE_APP_NOTIFICATIONCOMPAT$BUILDER_ADDACTION);
		this.methods.add(Constants.ANDROIDX_CORE_APP_NOTIFICATIONCOMPAT$CAREXTENDER$UNREADCONVERSATION);
		this.methods.add(Constants.COM_GOOGLE_ANDROID_GMS_LOCATION_ACTIVITYRECOGNITIONAPI_REQUESTACTIVITYUPDATES);
		this.methods.add(Constants.COM_GOOGLE_ANDROID_GMS_LOCATION_FUSEDLOCATIONPROVIDERAPI_REQUESTLOCATIONUPDATES);
		this.methods.add(Constants.COM_GOOGLE_ANDROID_GMS_LOCATION_GEOFENCINGAPI_ADDGEOFENCES);
	}

	@Override
	protected int getIndexHandled() {
		return 2;
	}
}
