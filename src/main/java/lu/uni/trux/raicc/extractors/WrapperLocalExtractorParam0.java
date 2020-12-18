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

public class WrapperLocalExtractorParam0 extends WrapperLocalExtractorImpl {

	public WrapperLocalExtractorParam0(WrapperLocalExtractorImpl next) {
		super(next);
		this.methods.add(Constants.ANDROID_APP_ACTIVITYOPTIONS_REQUESTUSAGETIMEREPORT);
		this.methods.add(Constants.ANDROID_APP_NOTIFICATION$BUBBLEMETADATA$BUILDER_SETDELETEINTENT);
		this.methods.add(Constants.ANDROID_APP_NOTIFICATION$BUBBLEMETADATA$BUILDER_SETINTENT);
		this.methods.add(Constants.ANDROID_APP_NOTIFICATION$BUILDER_SETDELETEINTENT);
		this.methods.add(Constants.ANDROID_APP_NOTIFICATION$BUILDER_SETFULLSCREENINTENT);
		this.methods.add(Constants.ANDROID_APP_NOTIFICATION$CAREXTENDER$BUILDER_SETREADPENDINGINTENT);
		this.methods.add(Constants.ANDROID_APP_NOTIFICATION$CAREXTENDER$BUILDER_SETREPLYACTION);
		this.methods.add(Constants.ANDROID_APP_NOTIFICATION$WEARABLEEXTENDER_SETDISPLAYINTENT);
		this.methods.add(Constants.ANDROID_APP_SLICE_SLICE$BUILDER_ADDACTION);
		this.methods.add(Constants.ANDROID_MEDIA_AUDIOMANAGER_REGISTERMEDIABUTTONEVENTRECEIVER);
		this.methods.add(Constants.ANDROID_MEDIA_SESSION_MEDIASESSION_SETMEDIABUTTONRECEIVER);
		this.methods.add(Constants.ANDROID_MEDIA_SESSION_MEDIASESSION_SETSESSIONACTIVITY);
		this.methods.add(Constants.ANDROID_NET_VPNSERVICE_BUILDER_SETCONFIGUREINTENT);
		this.methods.add(Constants.ANDROID_PRINT_PRINTERINFO_BUILDER_SETINFOINTENT);
		this.methods.add(Constants.ANDROID_SUPPORT_V4_APP_ACTIVITYOPTIONSCOMPAT_REQUESTUSAGETIMEREPORT);
		this.methods.add(Constants.ANDROID_SUPPORT_V4_APP_NOTIFICATIONCOMPAT$BUILDER_SETFULLSCREENINTENT);
		this.methods.add(Constants.ANDROID_SUPPORT_V4_MEDIA_APP_NOTIFICATIONCOMPAT$MEDIASTYLE_SETCANCELBUTTONINTENT);
		this.methods.add(Constants.ANDROID_SUPPORT_V4_MEDIA_SESSION_MEDIASESSIONCOMPAT_SETMEDIABUTTONRECEIVER);
		this.methods.add(Constants.ANDROID_SUPPORT_V4_MEDIA_SESSION_MEDIASESSIONCOMPAT_SETSESSIONACTIVITY);
		this.methods.add(Constants.ANDROIDX_BROWSER_BROWSERACTIONS_BROWSERACTIONSINTENT$BUILDER_SETONITEMSELECTEDACTION);
		this.methods.add(Constants.ANDROIDX_CORE_APP_NOTIFICATIONCOMPAT$BUBBLEMETADATA$BUILDER_SETDELETEINTENT);
		this.methods.add(Constants.ANDROIDX_CORE_APP_NOTIFICATIONCOMPAT$BUBBLEMETADATA$BUILDER_SETINTENT);
		this.methods.add(Constants.ANDROIDX_CORE_APP_NOTIFICATIONCOMPAT$BUBBLEMETADATA_1);
		this.methods.add(Constants.ANDROIDX_CORE_APP_NOTIFICATIONCOMPAT$BUBBLEMETADATA_2);
		this.methods.add(Constants.ANDROIDX_CORE_APP_NOTIFICATIONCOMPAT$BUILDER_SETCONTENTINTENT);
		this.methods.add(Constants.ANDROIDX_CORE_APP_NOTIFICATIONCOMPAT$BUILDER_SETDELETEINTENT);
		this.methods.add(Constants.ANDROID_CONTENT_PM_PACKAGEINSTALLER$SESSION_COMMIT);
		this.methods.add(Constants.ANDROID_COMPANION_COMPANIONDEVICEMANAGER$CALLBACK_ONDEVICEFOUND);
		this.methods.add(Constants.ANDROID_CONTENT_INTENTSENDER$ONFINISHED_ONSENDFINISHED);
		this.methods.add(Constants.ANDROID_SERVICE_AUTOFILL_SAVECALLBACK_ONSUCCESS);
		this.methods.add(Constants.ANDROID_SERVICE_AUTOFILL_FILLRESPONSE$BUILDER_SETAUTHENTICATION_1);
		this.methods.add(Constants.ANDROID_CONTENT_CONTEXT_STARTINTENTSENDER_1);
		this.methods.add(Constants.ANDROID_CONTENT_CONTEXT_STARTINTENTSENDER_2);
		this.methods.add(Constants.ANDROID_APP_ACTIVITY_STARTINTENTSENDERFORRESULT_1);
		this.methods.add(Constants.ANDROID_APP_ACTIVITY_STARTINTENTSENDERFORRESULT_2);
	}

	@Override
	protected int getIndexHandled() {
		return 0;
	}
}
