package com.example.testbgpref2;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import rajawali.lights.ALight;
import rajawali.lights.DirectionalLight;
import rajawali.materials.SimpleMaterial;
import rajawali.primitives.Plane;
import rajawali.renderer.RajawaliRenderer;
import rajawali.util.RajLog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class Renderer extends RajawaliRenderer implements OnSharedPreferenceChangeListener {
	
	public static String TAG = "AlbumCoverWallpaper";
	private String userBGPath;
	private int j = 0;
	public Renderer(Context context) {
		super(context);
		Log.d(TAG, "Application start");
		setFrameRate(30);
	}
	
	@Override
	public void initScene() {
		RajLog.systemInformation();
		
		System.gc();
		ALight light = new DirectionalLight();
		light.setPower(4);
		light.setZ(+10);
		getCamera().setZ(+7f);
		getCamera().setLookAt(0, 1, 0);
		getCamera().setFarPlane(1000);
		
		
		 
		
		SimpleMaterial planeMat = new SimpleMaterial();
		//BitmapFactory.Options localOptions = new BitmapFactory.Options();
		//localOptions.inSampleSize(4);
		//Bitmap test = BitmapFactory.decodeFile(this.userBGPath, localOptions);
		planeMat.addTexture(mTextureManager.addTexture(this.j));
		Plane plane = new Plane(5, 5, 1, 1);
		plane.setRotZ(-90);
		plane.setScale(2f);
		//plane.setPosition(0, 0, 10);
		plane.setMaterial(planeMat);
		addChild(plane);
		
    }
	
	

	@Override
	public void onDrawFrame(GL10 paramGL10)
	  {
	    super.onDrawFrame(paramGL10);
	  }

	  public void onSharedPreferenceChanged(SharedPreferences paramSharedPreferences, String paramString)
	  {
	    this.j = paramSharedPreferences.getInt("backgroundpicker", 0);
	    
	  }

	  @Override
	public void onSurfaceCreated(GL10 paramGL10, EGLConfig paramEGLConfig)
	  {
	    super.onSurfaceCreated(paramGL10, paramEGLConfig);
	    this.preferences.registerOnSharedPreferenceChangeListener(this);
	    
	  }

	  @Override
	public void onSurfaceDestroyed()
	  {
	    super.onSurfaceDestroyed();
	    this.preferences.unregisterOnSharedPreferenceChangeListener(this);
	  }
	}