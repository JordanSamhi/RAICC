package lu.uni.trux.raicc.extractors;

import lu.uni.trux.raicc.utils.Constants;

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
