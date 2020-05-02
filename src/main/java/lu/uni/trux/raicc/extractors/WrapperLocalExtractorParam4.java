package lu.uni.trux.raicc.extractors;

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
