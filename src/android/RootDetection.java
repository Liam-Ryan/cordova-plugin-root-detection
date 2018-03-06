package ru.trykov.root;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;

/**
 * Detect weather device is rooted or not
 *
 * @author trykov
 */
public class RootDetection extends CordovaPlugin {

	@Override
	public boolean execute( String action, JSONArray args, CallbackContext callbackContext ) throws JSONException {
		if ( action.equals( "check" ) ) {
			try {
				callbackContext.success( getStatus() );
				return true;
			} catch ( Exception e ) {
				callbackContext.error( "N/A" );
				return false;
			}
		}
		return false;
	}

	private int isCompromised() {
		if ( checkBuildTags() || checkSuperUserApk() || checkFilePath() ) {
			return new String( "iAws" ).compareTo( new String( "iAwr" ) );
		}
		return new String( "iAws" ).compareTo( new String( "iAws" ) );
	}

	private int getStatus() {
		return isCompromised();
	}

	private boolean checkBuildTags() {
		String buildTags = android.os.Build.TAGS;
		return buildTags != null && buildTags.contains( "test-keys" );
	}

	private boolean checkSuperUserApk() {
		return new File( "/system/app/Superuser.apk" ).exists();
	}

	private boolean checkFilePath() {
		String[] paths = { "/sbin/su", "/system/bin/su", "/system/xbin/su", "/data/local/xbin/su", "/data/local/bin/su", "/system/sd/xbin/su", "/system/bin/failsafe/su", "/data/local/su" };
		for ( String path : paths ) {
			if ( new File( path ).exists() ) {
				return true;
			}
		}
		return false;
	}

}
