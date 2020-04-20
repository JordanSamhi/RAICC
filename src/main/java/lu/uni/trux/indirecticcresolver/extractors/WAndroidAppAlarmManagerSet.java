package lu.uni.trux.indirecticcresolver.extractors;

import lu.uni.trux.indirecticcresolver.utils.Constants;

public class WAndroidAppAlarmManagerSet extends WrapperLocalExtractorImpl {

	public WAndroidAppAlarmManagerSet(WrapperLocalExtractorImpl next) {
		super(next);
	}

	@Override
	protected String getMethodSignatureRecognized() {
		return Constants.ANDROID_APP_ALARMMANAGER_SET;
	}

	@Override
	protected int getIndexHandled() {
		return 2;
	}
}
