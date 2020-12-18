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

public class WrapperLocalExtractorParam1 extends WrapperLocalExtractorImpl {

	public WrapperLocalExtractorParam1(WrapperLocalExtractorImpl next) {
		super(next);
		this.methods.add(Constants.ANDROID_CONTENT_PM_PACKAGEINSTALLER_UNINSTALL_1);
		this.methods.add(Constants.ANDROID_CONTENT_PM_PACKAGEINSTALLER_UNINSTALL_2);
		this.methods.add(Constants.ANDROID_APP_ACTIVITY_STARTINTENTSENDERFROMCHILD_1);
		this.methods.add(Constants.ANDROID_APP_ACTIVITY_STARTINTENTSENDERFROMCHILD_2);
		this.methods.add(Constants.ANDROID_SERVICE_AUTOFILL_FILLRESPONSE$BUILDER_SETAUTHENTICATION_2);
		this.methods.add(Constants.ANDROIDX_CORE_CONTENT_PM_SHORTCUTMANAGERCOMPAT_REQUESTPINSHORTCUT);
		this.methods.add(Constants.ANDROID_APP_FRAGMENTHOSTCALLBACK_ONSTARTINTENTSENDERFROMFRAGMENT);
		this.methods.add(Constants.ANDROID_APP_ALARMMANAGER_SETALARMCLOCK);
		this.methods.add(Constants.ANDROID_LOCATION_LOCATIONMANAGER_REQUESTSINGLEUPDATE_1);
		this.methods.add(Constants.ANDROID_LOCATION_LOCATIONMANAGER_REQUESTSINGLEUPDATE_2);
		this.methods.add(Constants.ANDROID_NET_CONNECTIVITYMANAGER_REGISTERNETWORKCALLBACK);
		this.methods.add(Constants.ANDROID_NET_CONNECTIVITYMANAGER_REQUESTNETWORK);
		this.methods.add(Constants.ANDROID_NET_SIP_SIPMANAGER_OPEN);
		this.methods.add(Constants.ANDROID_NFC_NFCADAPTER_ENABLEFOREGROUNDDISPATCH);
		this.methods.add(Constants.ANDROID_SUPPORT_V4_APP_NOTIFICATIONCOMPAT$ACTION$BUILDER_1);
		this.methods.add(Constants.ANDROID_TELEPHONY_EUICC_EUICCMANAGER_SWITCHTOSUBSCRIPTION);
		this.methods.add(Constants.ANDROID_TELEPHONY_SMSMANAGER_CREATEAPPSPECIFICSMSTOKENWITHPACKAGEINFO);
		this.methods.add(Constants.ANDROID_WIDGET_REMOTEVIEWS_SETONCLICKPENDINGINTENT);
		this.methods.add(Constants.ANDROID_WIDGET_REMOTEVIEWS_SETPENDINGINTENTTEMPLATE);
		this.methods.add(Constants.ANDROIDX_BROWSER_BROWSERACTIONS_BROWSERACTIONITEM);
		this.methods.add(Constants.COM_GOOGLE_ANDROID_GMS_LOCATION_FUSEDLOCATIONPROVIDERCLIENT_REQUESTLOCATIONUPDATES);
		this.methods.add(Constants.COM_GOOGLE_ANDROID_GMS_LOCATION_LOCATIONCLIENT_ADDGEOFENCES);
		this.methods.add(Constants.COM_GOOGLE_ANDROID_VENDING_EXPANSION_DOWNLOADER_DOWNLOADERCLIENTMARSHALLER_STARTDOWNLOADSERVICEIFREQUIRED);
		this.methods.add(Constants.COM_GOOGLE_VR_NDK_BASE_DAYDREAMAPI_LAUNCHINVR);
		this.methods.add(Constants.COM_GOOGLE_VR_NDK_BASE_DAYDREAMAPI_LAUNCHINVRFORRESULT);
	}

	@Override
	protected int getIndexHandled() {
		return 1;
	}
}
