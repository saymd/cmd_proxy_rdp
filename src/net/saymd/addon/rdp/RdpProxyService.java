package net.saymd.addon.rdp;

import java.lang.reflect.Method;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import net.saymd.addon.AddonService;
import net.saymd.addon.WindowsMachine;
import net.saymd.addon.rdp.protocol.RdesktopSession;

public class RdpProxyService extends AddonService {
	
	public RdpProxyService(String name) {
		super(name);
	}

	public RdpProxyService(){
		super(RdpProxyService.class.getSimpleName());
	}
	

	@Override
	protected void onPreExecute(Intent intent, Bundle param) {
		// put pod to each intent
		param.putSerializable( RdpProxyService.class.getSimpleName() + ".machine",
							  new WindowsMachine(intent));
		
		// overwrite parameters
		intent.putExtra("param", param);
		
		// add RDP class
		intent.putExtra("class", "protocol." + RdesktopSession.class.getSimpleName() );
		intent.putExtra("method", "execute");
		
		super.onPreExecute(intent, param);
	}
	
	@Override
	protected void dispatchMethod(Class<?> clazz, Method requestedMethod,
			Bundle param) throws Exception {
		WindowsMachine machine = (WindowsMachine) param.getSerializable(
				RdpProxyService.class.getSimpleName() +".machine");
		
		RdesktopSession session = new RdesktopSession(machine, param.getString("command"));
		
		session.execute();

		onPostExecute(param, Activity.RESULT_OK, null);
	}

}
